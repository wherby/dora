package doracore
import akka.actor.ActorSystem
import doracore.vars.ConstVars
import doradilla.back.BackendServer
//import doradilla.back.BackendServer
import doradilla.conf.DoraConf

/** For doradilla in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/24
  *
  * Create a "Singleton" io.github.wherby.doradilla.test system. while io.github.wherby.doradilla.test start in sequence, then the isolation of the resource will be OK.
  */
object ActorSystemTest {
  lazy val actorSystemOpt: ActorSystem = getActorSystem
  var count                            = 0
  def getActorSystem() = {
    val system = BackendServer.getDefaultBackendServer()
    BackendServer.backendServerMap += (ConstVars.DoraPort -> system)
    system.registerFSMActor()
    system.actorSystemOpt.get
  }

  def shutdowmSystem() = {
    count = count - 1
    if (count == 0) {
      actorSystemOpt.terminate()
    }
  }
}
