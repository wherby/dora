/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doradilla.back

import akka.actor.{ActorRef, ActorSystem}
import akka.event.slf4j.Logger
import com.typesafe.config.Config
import doracore.core.fsm.FsmActor
import doracore.core.fsm.FsmActor.RegistToDriver
import doracore.util.{CNaming, ConfigService}
import doradilla.conf.{Const, DoraConf}

trait BackendServerTrait {
  var actorSystemOpt: Option[ActorSystem] = None

  var actorMap: Map[String, ActorRef] = Map()

  def getActorProxy(actorName: String): Option[ActorRef] = {
    actorMap.get(actorName)
  }

  def registerFSMActor(): Unit = {
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

  def setUpActors(system: ActorSystem): Unit

}
