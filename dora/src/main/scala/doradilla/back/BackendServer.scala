/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

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
object BackendServer extends ProcessCommandRunner with ActorSystemLocalTrait {

  var backendServerMap: Map[Int, BackendServer] = Map()
  var namedJobApiMap: Map[String, JobApi]       = Map()

  def getDefaultBackendServer() = {
    BackendServer.backendServerMap.headOption.map(_._2) match {
      case Some(backendServer) => backendServer
      case _ =>
        val backendServer = new BackendServer()
        val port          = ConstVars.DoraPort

        val system = getActorSystem()
        backendServer.actorSystemOpt = Some(system)
        backendServerMap += (port -> backendServer)
        backendServer.setUpActors(system)
        backendServer
    }
  }

}

class BackendServer extends BackendServerTraitLocal {}
