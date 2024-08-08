/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.tool.job.worker

import akka.actor.Actor
import doracore.vars.ConstVars

trait BlockIODispatcher {
  this: Actor =>
  def GetBlockIODispatcher = {
    context.system.dispatchers.hasDispatcher(ConstVars.blockDispatcherName) match {
      case true => context.system.dispatchers.lookup(ConstVars.blockDispatcherName)
      case _    => context.system.dispatcher
    }
  }
}
