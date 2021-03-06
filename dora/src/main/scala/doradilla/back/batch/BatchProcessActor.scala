/*
package doradilla.back.batch

import akka.actor.{ActorRef, PoisonPill, Props}
import akka.pattern.ask
import akka.util.Timeout
import doracore.base.BaseActor
import doracore.core.msg.Job.{JobMeta, JobMsg, JobRequest, JobResult}
import doracore.tool.job.worker.BlockIODispatcher
import doracore.tool.receive.ReceiveActor
import doracore.tool.receive.ReceiveActor.{ProxyControlMsg, QueryResult}
import doracore.util.{CNaming, MyUUID}
import doracore.vars.ConstVars
import doradilla.back.batch.BatchProcessActor.{BatchJobResult, BatchProcessJob, JobInfo}

import scala.concurrent.{Await, ExecutionContext, Future}

class BatchProcessActor extends BaseActor with BlockIODispatcher {
  implicit val ec = GetBlockIODispatcher
  var jobRecorder: Map[ActorRef, JobInfo] = Map()


  def handleBacthJob(batchProcessJobOrg: BatchProcessJob) = {
    val batchProcessJob = batchProcessJobOrg.jobmetaOpt match {
      case Some(_) => batchProcessJobOrg
      case _ => batchProcessJobOrg.copy(jobmetaOpt = Some(JobMeta(MyUUID.getUUIDString())))
    }
    batchProcessJob.jobs.map {
      processJob =>
        val actorSystem = context.system
        val receiveActor = actorSystem.actorOf(ReceiveActor.receiveActorProps, CNaming.timebasedName("ReceiverForBatch"))
        val jobMetaOpt = batchProcessJob.jobmetaOpt.map {
          jobMeta => jobMeta.copy(jobUUID = jobMeta.jobUUID + "_Extends_" + MyUUID.getUUIDString())
        }
        val processJobRequest = JobRequest(processJob, receiveActor, batchProcessJob.processTranServiceActor, batchProcessJob.priorityOpt, jobMetaOpt)
        batchProcessJob.driverServiceActor.tell(processJobRequest, receiveActor)
        jobRecorder = jobRecorder.updated(receiveActor, JobInfo(processJob, None))
    }
  }

  def handleQueryBatchResult(timeout: Timeout = ConstVars.timeout1S)(implicit ex: ExecutionContext) = {
    val result = Future.sequence(jobRecorder.keys.map {
      actorRef =>
        val jobInfo = jobRecorder(actorRef)
        jobInfo.jobResultOpt match {
          case None => (actorRef ? QueryResult()) (timeout).map {
            resultOpt =>
              resultOpt.asInstanceOf[Option[JobResult]] match {
                case Some(result) =>
                  jobRecorder = jobRecorder.updated(actorRef, JobInfo(jobInfo.jobMsg, Some(result)))
                  actorRef ! ProxyControlMsg(PoisonPill)
                  actorRef ! PoisonPill
                  Some(result)
                case _ => None
              }
          }
          case _ => Future(None)
        }
    })
    Await.result(result, ConstVars.timeout1S)
    sender() ! BatchJobResult(jobRecorder.values.toSeq)
  }

  override def receive: Receive = {
    case batchProcessJob: BatchProcessJob => handleBacthJob(batchProcessJob)
    case queryResult: QueryResult => handleQueryBatchResult()
  }
}

object BatchProcessActor {

  case class BatchProcessJob(jobs: Seq[JobMsg], driverServiceActor: ActorRef, processTranServiceActor: ActorRef, priorityOpt: Option[Int] = None, jobmetaOpt: Option[JobMeta] = None)

  case class JobInfo(jobMsg: JobMsg, jobResultOpt: Option[JobResult])

  case class BatchJobResult(results: Seq[JobInfo])

  def batchProcessActorProp(): Props = {
    Props(new BatchProcessActor())
  }
}
 */
