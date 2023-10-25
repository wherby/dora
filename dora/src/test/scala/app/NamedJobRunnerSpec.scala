package app

import doracore.ActorTestClass
import doracore.core.msg.Job.{JobMeta, JobMsg, JobStatus}
import doracore.core.msg.Job.JobStatus.{JobStatus, TimeOut}
import doracore.util.ProcessService.ProcessResult
import doracore.util.{ProcessService, ProcessServiceSpec}
import doracore.vars.ConstVars
import doradilla.back.BackendServer
import doradilla.conf.TestVars
import org.scalatest.Matchers
import play.api.libs.json.JsResult.Exception

import scala.concurrent.{Await, Future}

/** For app in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/12/14
  */
class NamedJobRunnerSpec extends ActorTestClass with Matchers {
  override protected def beforeAll(): Unit = {
    super.beforeAll()
    ProcessService.nameToClassOpt =
      ProcessServiceSpec.safeProcessServiceNameToClassOpt
  }

  import scala.concurrent.ExecutionContext.Implicits.global

  val timeout = ConstVars.timeout1S * 4
  "Named Job Runner" should {
    "start new driver when name is different" in {
      val job1 = TestVars.sleepProcessJob
      BackendServer.runNamedProcessCommand(
        job1,
        "job11",
        metaOpt = Some(JobMeta("NewNameJob1"))
      )
      val job2 = TestVars.processJob
      val resultFuture = BackendServer.runNamedProcessCommand(
        job2,
        "job12",
        metaOpt = Some(JobMeta("NewNameJob2"))
      )
      val result = Await.ready(resultFuture, timeout)
      println(result)
    }

    "Name Job with Meta" must {
      "run job in sequece the sleep operation will block following operation and time out will go" in {
        val job1 = TestVars.sleepProcessJob
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1"))
        )
        val job2 = TestVars.processJob
        val resultFuture = BackendServer.runNamedProcessCommand(
          job2,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob2"))
        )
        val result =
          try {
            Await.ready(resultFuture, timeout)
          } catch {
            case _: Throwable => Future("TimeOutError")
          }
        result.map { a =>
          a shouldBe ("TimeOutError")
          println(a)
        }
      }
    }

    "Name Job with Meta" must {
      "run job in sequece the sleep operation will not block following operation and time out will go" in {
        val job1 = TestVars.sleepProcessJob
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1")),
          timeout = ConstVars.timeout1S
        )
        //BackendServer.runNamedProcessCommand(job1, "job13",metaOpt = Some(JobMeta("NewNameJob1")),timeout=ConstVars.timeout1S  )
        //BackendServer.runNamedProcessCommand(job1, "job13",metaOpt = Some(JobMeta("NewNameJob1")),timeout=ConstVars.timeout1S  )
        Thread.sleep(3000)
        val job2 = TestVars.processJob
        BackendServer.runNamedProcessCommand(
          job2,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob2"))
        )
        //Thread.sleep(3000)
        Thread.sleep(3000)
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1")),
          timeout = ConstVars.timeout1S
        )
        Thread.sleep(3000)
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1")),
          timeout = ConstVars.timeout1S
        )
        Thread.sleep(3000)
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1")),
          timeout = ConstVars.timeout1S
        )
        Thread.sleep(3000)
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1")),
          timeout = ConstVars.timeout1S
        )
        Thread.sleep(3000)
        BackendServer.runNamedProcessCommand(
          job1,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob1")),
          timeout = ConstVars.timeout1S
        )
        Thread.sleep(3000)
        val resultFuture = BackendServer.runNamedProcessCommand(
          job2,
          "job13",
          metaOpt = Some(JobMeta("NewNameJob2"))
        )
        val result =
          try {
            Await.ready(resultFuture, timeout)
          } catch {
            case _: Throwable => Future("TimeOutError")
          }

        println("s")
        println(result)
        println("XX")

        Thread.sleep(2000)
      }
    }

    "Named Job Runner" should {
      "start new driver when name is different but will failed without fsm " in {
        val job1 = TestVars.sleepProcessJob
        BackendServer.runNamedProcessCommand(job1, "job1")
        val job2 = TestVars.processJob
        BackendServer.changeFSMForNamedJob("job2", -1)
        val resultFuture = BackendServer.runNamedProcessCommand(job2, "job2")
        var timeOut = false
        try {
          val result = Await.ready(resultFuture, timeout)
          println(result)
        } catch {
          case exception: Throwable =>
            timeOut = true
            println(exception)
        }
        timeOut shouldBe (true)
      }
    }

    "use same driver when name same" in {
      println("Start same driver test")
      val job1 = TestVars.sleepProcessJob
      val result1Future = BackendServer.runNamedProcessCommand(job1, "job3")
      val job2 = TestVars.processJob
      val resultFuture = BackendServer.runNamedProcessCommand(job2, "job3")
      var timeOut = false

      try {
        val result2 = Await.result(result1Future, timeout)
        result2.result.asInstanceOf[ProcessResult].jobStatus == JobStatus.Failed
        timeOut = true //For the execution failed for first
        val result = Await.result(resultFuture, timeout)
        println(result)
      } catch {
        case exception: Throwable =>
          timeOut = true
          println(exception)
      }
      println("end same driver test")
      timeOut shouldBe (true)
    }

    "use same driver when name same with increased fsm " in {
      val job1 = TestVars.sleepProcessJob
      BackendServer.changeFSMForNamedJob("job4", 1)
      val result1Future = BackendServer.runNamedProcessCommand(job1, "job4")
      val job2 = TestVars.processJob
      val resultFuture = BackendServer.runNamedProcessCommand(job2, "job4")
      var timeOut = false

      try {
        val result = Await.ready(resultFuture, timeout)
        println(result)
      } catch {
        case exception: Exception =>
          timeOut = true
          println(exception)
      }
      timeOut shouldBe (false)
    }
  }
}
