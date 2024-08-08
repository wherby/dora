/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.api

import doracore.vars.ConstVars
import scala.concurrent.ExecutionContextExecutor

trait GetBlockIOExecutor {
  this: ActorSystemApi =>
  def getBlockDispatcher(
      dispatcherName: String = ConstVars.blockDispatcherName
  ): ExecutionContextExecutor = {
    val actorSystem = getActorSystem()
    actorSystem.dispatchers.hasDispatcher(dispatcherName) match {
      case true =>
        actorSystem.dispatchers.lookup(dispatcherName)
      case _ => actorSystem.dispatcher
    }
  }
}
