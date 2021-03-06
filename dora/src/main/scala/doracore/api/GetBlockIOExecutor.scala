package doracore.api

import doracore.vars.ConstVars
import scala.concurrent.ExecutionContextExecutor

trait GetBlockIOExecutor {
  this :ActorSystemApi=>
  def getBlockDispatcher(dispatcherName:String = ConstVars.blockDispatcherName):ExecutionContextExecutor={
    val actorSystem= getActorSystem()
    actorSystem.dispatchers.hasDispatcher(dispatcherName) match {
      case true =>
        actorSystem.dispatchers.lookup(dispatcherName)
      case _ => actorSystem.dispatcher
    }
  }
}
