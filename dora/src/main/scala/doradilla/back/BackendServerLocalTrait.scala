package doradilla.back

import akka.actor.{ActorSystem, Props}
import doracore.core.driver.DriverActor
import doracore.tool.job.process.ProcessTranActor
import doracore.tool.query.QueryActor
import doradilla.conf.Const

/** For doradilla.back.BackendServerTrait in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2021/4/27
  */
trait BackendServerTraitLocal extends BackendServerTrait {

  override def setUpActors(system: ActorSystem) = {
    setUpActorForMap(system, DriverActor.driverActorPropsWithoutFSM(), Const.driverServiceName)
    setUpActorForMap(system, ProcessTranActor.processTranActorProps, Const.procssTranServiceName)
    setUpActorForMap(system, QueryActor.queryActorProps, Const.queryService)
  }

  private def setUpActorForMap(system: ActorSystem, props: Props, name: String) = {
    val actorRef = system.actorOf(props, name)
    actorMap += (name -> actorRef)
  }

}
