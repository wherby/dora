/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.api

import akka.actor.{ActorRef, PoisonPill}
import akka.event.slf4j.Logger
import akka.util.Timeout
import doracore.core.msg.Job.{JobRequest, JobResult, JobStatus}
import doracore.tool.receive.ReceiveActor.{FetchResult, ProxyControlMsg, ReceiverControlMsg}

import scala.concurrent.{ExecutionContext, Future}
import akka.pattern.ask
import doracore.util.ProcessService.ProcessResult

trait AskProcessResult {
  this: GetBlockIOExecutor =>
  def getProcessCommandFutureResult(
      jobRequest: JobRequest,
      driver: ActorRef,
      receiveActor: ActorRef,
      timeout: Timeout
  ): Future[JobResult] = {
    driver.tell(jobRequest, receiveActor)
    getResult(receiveActor, timeout)
  }
  def getResult(receiveActor: ActorRef, timeout: Timeout): Future[JobResult] = {
    implicit val ex: ExecutionContext  = getBlockDispatcher()
    implicit val timeoutValue: Timeout = timeout
    var result                         = JobResult(JobStatus.Unknown, "Unkonwn").asInstanceOf[Any]
    (receiveActor ? FetchResult())
      .map { resultT =>
        resultT.asInstanceOf[JobResult]
      }
      .recover { case ex: Throwable =>
        val tName = Thread.currentThread.getName
        Logger
          .apply(this.getClass.getName)
          .error(s"$tName=> Job timeout after $timeout")
        result = JobResult(JobStatus.TimeOut, ProcessResult(JobStatus.Failed, ex))
        receiveActor ! ProxyControlMsg(JobStatus.Canceled)
        //receiveActor ! PoisonPill
        result.asInstanceOf[JobResult]
      }
      .map { result =>
        Future {
          Thread.sleep(1000)
          receiveActor ! ProxyControlMsg(PoisonPill)
          Thread.sleep(100)
          receiveActor ! ReceiverControlMsg(PoisonPill)
        }

        result
      }
  }
}
