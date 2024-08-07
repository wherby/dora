/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.core.proxy

import akka.actor.{ActorRef, Props}
import doracore.base.BaseActor
import doracore.core.fsm.FsmActor.TranslatedActor
import doracore.core.msg.Job.JobStatus.JobStatus
import doracore.core.msg.Job.{JobEnd, JobRequest, JobResult, JobStatus}
import doracore.core.proxy.ProxyActor.{ProxyTaskResult, QueryProxy}
import doracore.core.queue.QueueActor.RemoveJob
import doracore.tool.receive.ReceiveActor.ProxyControlMsg

/** For doradilla.proxy in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/30
  */
class ProxyActor(queueActor: ActorRef) extends BaseActor {
  var status: JobStatus                 = JobStatus.Unknown
  var replyTo: ActorRef                 = null
  var requestMsgBk: JobRequest          = null
  var fsmActorOpt: Option[ActorRef]     = None
  var result: Option[Any]               = None
  var translatedActorSeq: Seq[ActorRef] = Seq()

  def handleJobRequest(requestMsg: JobRequest): Unit = {
    replyTo = requestMsg.replyTo
    requestMsgBk = requestMsg
    val updatedJobRequest = requestMsg.copy(replyTo = self)
    queueActor ! updatedJobRequest
    status = JobStatus.Queued
  }

  def finishTask() = {
    log.info(s"${requestMsgBk.jobMetaOpt} is finished.")
    fsmActorOpt.map { fsmActor =>
      fsmActor ! JobEnd(requestMsgBk)
    }
  }

  def cancelJob() = {
    log.info(s"${requestMsgBk.jobMetaOpt} is cancelled.")
    queueActor ! RemoveJob(requestMsgBk)
    fsmActorOpt.map { fsmActor =>
      fsmActor ! JobEnd(requestMsgBk)
    }
  }

  override def receive: Receive = {
    case jobRequest: JobRequest =>
      handleJobRequest(jobRequest)
    case jobResult: JobResult =>
      result = Some(jobResult.result)
      replyTo ! jobResult
      self ! JobStatus.Finished
    case JobStatus.Scheduled =>
      fsmActorOpt = Some(sender())
      status = JobStatus.Scheduled
    case JobStatus.Finished | JobStatus.Failed | JobStatus.TimeOut =>
      finishTask()
    case JobStatus.Canceled =>
      cancelJob()
    case _: QueryProxy =>
      sender() ! ProxyTaskResult(
        requestMsgBk,
        status,
        result,
        translatedActorSeq,
        fsmActorOpt.getOrElse(null)
      )
    case translatedActor: TranslatedActor =>
      translatedActorSeq = translatedActorSeq :+ translatedActor.child
    case proxyControlMsg: ProxyControlMsg =>
      self ! proxyControlMsg.proxyControlMsg
  }
}

object ProxyActor {
  def proxyProps(queue: ActorRef): Props = Props(new ProxyActor(queue))

  case class QueryProxy()

  case class ProxyTaskResult(
      requestMsg: JobRequest,
      status: JobStatus,
      result: Option[Any],
      translatedActorSeq: Seq[ActorRef],
      fsmActor: ActorRef
  )

}
