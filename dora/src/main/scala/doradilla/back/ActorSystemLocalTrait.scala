/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

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
