package doradilla.back

import akka.actor.ActorSystem
import doracore.api.ActorSystemApi
import doracore.util.AppDebugger

trait ActorSystemLocalTrait extends ActorSystemApi {
  var actorSystemOpt: Option[ActorSystem] = None
  override def getActorSystem(): ActorSystem = {
    if (actorSystemOpt == None) {
      AppDebugger.log("create new server ", Some("getActorSystem"))
      actorSystemOpt = Some(ActorSystem())
    }
    actorSystemOpt.get
  }

}
