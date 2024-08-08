/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.api

import akka.actor.ActorSystem
import akka.util.Timeout
import doracore.util.{CNaming, ConfigService, DoraCoreConfig}
import doracore.vars.ConstVars

/** For doradilla.api in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/9
  */
class SystemApi(systemOpt: Option[ActorSystem] = None)
    extends GetBlockIOExecutor
    with ActorSystemApi {
  val longTimeout = Timeout(ConstVars.longTimeOut)

  lazy val doradillaConfig = DoraCoreConfig.getConfig()

  def createDoradillaSystem: ActorSystem = {
    val actorSystemName = ConfigService
      .getStringOpt(doradillaConfig, "doradillaSystem")
      .getOrElse(CNaming.timebasedName("doradilla"))
    ActorSystem(actorSystemName, doradillaConfig)
  }

  override def getActorSystem(): ActorSystem = {
    actorSystem
  }

  val actorSystem: ActorSystem = systemOpt match {
    case Some(system) => system
    case _            => createDoradillaSystem
  }
}
