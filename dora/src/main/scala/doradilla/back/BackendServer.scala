package doradilla.back

import akka.actor.{ActorRef, ActorSystem, Props}
import doracore.core.driver.DriverActor
import doracore.tool.job.process.ProcessTranActor
import doracore.core.fsm.FsmActor
import doracore.core.fsm.FsmActor.RegistToDriver
import doracore.util.{AppDebugger, CNaming, ConfigService}
import doradilla.conf.{Const, DoraConf}
import akka.event.slf4j.Logger
import com.typesafe.config.Config
import doracore.api.JobApi
import doracore.tool.query.QueryActor
import doracore.vars.ConstVars

/** For io.github.wherby.doradilla.back in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/5/11
  */
object BackendServer extends ProcessCommandRunner {
  lazy val backendServerOpt: BackendServer      = getBackendServer
  var backendServerMap: Map[Int, BackendServer] = Map()
  var namedJobApiMap: Map[String, JobApi]       = Map()
  lazy val seedPort = ConfigService
    .getIntOpt(DoraConf.config, "cluster-setting.seed-port")
    .getOrElse(ConstVars.DoraPort)
  var nextPort = seedPort

  def getBackendServer() = {
    BackendServer.backendServerMap.headOption.map(_._2) match {
      case Some(backendServer) => backendServer
      case _ =>
        startup(Some(ConstVars.DoraPort))
    }
  }

  override def getActorSystem(): ActorSystem = {
    if (BackendServer.backendServerMap.headOption == None) {
      AppDebugger.log("create new server ", Some("getActorSystem"))
      createBackendServer(Some(ConstVars.DoraPort))
    }
    BackendServer.backendServerMap.head._2.actorSystemOpt.get
  }

  def startup(
      portConf: Option[Int] = None,
      systemConfigOpt: Option[Config] = None
  ): BackendServer = {
    portConf match {
      case Some(port) =>
        backendServerMap.get(port) match {
          case Some(backendServer) => backendServer
          case _ =>
            AppDebugger.log(s"Start new server $portConf , $systemConfigOpt", Some("start up"))
            createBackendServer(Some(port), systemConfigOpt)
        }
      case _ =>
        AppDebugger.log(s"Start new server $portConf , $systemConfigOpt", Some("start up"))
        createBackendServer(portConf)
    }
  }

  def createBackendServer(
      portConf: Option[Int],
      systemConfigOpt: Option[Config] = None
  ): BackendServer = {
    val backendServer = new BackendServer()
    val port          = portConf.getOrElse(ConstVars.DoraPort)

    val system = ActorSystem()
    backendServer.actorSystemOpt = Some(system)
    backendServerMap += (port -> backendServer)
    backendServer.setUpActors(system)
    backendServer
  }

}

class BackendServer {
  var actorSystemOpt: Option[ActorSystem] = None
  var actorMap: Map[String, ActorRef]     = Map()

  def getActorProxy(actorName: String): Option[ActorRef] = {
    actorMap.get(actorName) match {
      case Some(actorProxy) => Some(actorProxy)
      case _                => None
    }
  }

  def setUpActors(system: ActorSystem) = {
    setUpActorForMap(system, DriverActor.driverActorPropsWithoutFSM(), Const.driverServiceName)
    setUpActorForMap(system, ProcessTranActor.processTranActorProps, Const.procssTranServiceName)
    setUpActorForMap(system, QueryActor.queryActorProps, Const.queryService)
  }

  private def setUpActorForMap(system: ActorSystem, props: Props, name: String) = {
    val actorRef = system.actorOf(props, name)
    actorMap += (name -> actorRef)
  }

  def registFSMActor(): Unit = {
    val fsmNumber = ConfigService.getStringOpt(DoraConf.config, "fsmNumber").getOrElse("1").toInt
    (0 until fsmNumber).map { _ =>
      actorSystemOpt.map { actorSystem =>
        val fsmActorName       = CNaming.timebasedName("FsmActor")
        val fsmActor: ActorRef = actorSystem.actorOf(FsmActor.fsmActorProps, fsmActorName)
        this.getActorProxy(Const.driverServiceName).map { driverProxy =>
          driverProxy.tell(RegistToDriver(fsmActor), fsmActor)
        }
        actorMap += (fsmActorName -> fsmActor)
        Logger.apply(this.getClass.getName).info(s"ActorMap are $actorMap")
      }
    }
  }
}
