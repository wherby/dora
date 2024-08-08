/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.tool.receive

import akka.actor.{ActorRef, Props}
import doracore.base.BaseActor
import doracore.core.driver.DriverActor.ProxyActorMsg
import doracore.core.msg.Job.JobResult
import doracore.tool.receive.ReceiveActor.{
  FetchResult,
  ProxyControlMsg,
  QueryResult,
  ReceiverControlMsg
}

/** For doradilla.tool.receive in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/13
  */
class ReceiveActor extends BaseActor {
  var retriverActorOpt: Option[ActorRef] = None
  var jobResultOpt: Option[JobResult]    = None
  var proxyActorOpt: Option[ActorRef]    = None

  def sendBackReuslt() = {
    retriverActorOpt.get ! jobResultOpt.get
  }

  def handleFetchMsg() = {
    retriverActorOpt = Some(sender())
    jobResultOpt match {
      case Some(jobResult) => sendBackReuslt()
      case _               =>
    }
  }

  def handleJobResult(jobResult: JobResult) = {
    jobResultOpt = Some(jobResult)
    retriverActorOpt match {
      case Some(retriverActor) => sendBackReuslt()
      case _                   =>
    }
  }

  def handleProxyActorMsg(msg: ProxyActorMsg) = {
    proxyActorOpt = Some(msg.proxyActor)
  }

  def handleProxyControlMsg(proxyControlMsg: ProxyControlMsg) = {
    proxyActorOpt.map { proxyActor =>
      proxyActor ! proxyControlMsg
    }
  }

  def handleQueryResult() = {
    sender() ! jobResultOpt
  }

  override def receive: Receive = {
    case _: FetchResult                         => handleFetchMsg()
    case jobResult: JobResult                   => handleJobResult(jobResult)
    case proxyActorMsg: ProxyActorMsg           => handleProxyActorMsg(proxyActorMsg)
    case _: QueryResult                         => handleQueryResult()
    case proxyControlMsg: ProxyControlMsg       => handleProxyControlMsg(proxyControlMsg)
    case receiverControlMsg: ReceiverControlMsg => self ! receiverControlMsg.controlMsg
  }
}

object ReceiveActor {
  val receiveActorProps = Props(new ReceiveActor())
  case class FetchResult()
  case class StopProxy()
  case class QueryResult()
  case class ProxyControlMsg(proxyControlMsg: Any)
  case class ReceiverControlMsg(controlMsg: Any)
}
