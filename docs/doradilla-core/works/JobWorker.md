# Job Worker 

## How Job Worker created

From the [code](src/main/scala/doracore/tool/job/process/ProcessTranActor.scala) :
```scala
  private def processSimpleProcessFuture(jobRequest: JobRequest) = {
    val msg = jobRequest.taskMsg.data.asInstanceOf[ProcessCallMsg]
    sender() ! WorkerInfo(classOf[ProcessWorkerActor].getName, None, Some(jobRequest.replyTo))
    sender() ! TranslatedTask(SimpleProcessFutureInit(msg, jobRequest.replyTo))
  }
```

The sender() is FSMActor, when the FSMActor receive 

```scala
  when(Active) {
    case Event(jobEnd: JobEnd, task: Task) =>
      if (jobEnd.requestMsg.jobMetaOpt == jobMetaOpt) {
        log.log(Logging.InfoLevel, s"$jobMetaOpt is end")
        goto(Idle) using (Uninitialized)
      } else {
        stay()
      }
    case Event(workerInfo: WorkerInfo, _) =>                                    // Now create childActor(worker) of FSMActor
      childActorOpt = DeployService.tryToInstanceDeployActor(workerInfo, context)
      if (childActorOpt != None && workerInfo.replyTo != None) {
        workerInfo.replyTo.get ! TranslatedActor(childActorOpt.get)
      }
      stay()
    case Event(translatedTask: TranslatedTask, _) => {  //Send task to worker
      childActorOpt.map { childActor =>
        childActor ! translatedTask.task
      }
      stay()
    }
  }
```

When job is finished, the work will be killed.

```scala
  onTransition { case Active -> Idle =>
    log.info("Finish job and resetting state to start another job...")
    CleanCancelScheduler()
    endChildActor()    //The worker will be killed
    driverActor ! FetchJob()
  }

```


### If no job work is created, does the FSMActor work as expected?

Yes. "WorkerInfo" and "translatedTask" can't change the state of FSMActor, only JobEnd will, so you can finish all task in Translator actor.
