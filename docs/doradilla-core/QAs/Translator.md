# Translator

## When Job is sent to driver, how FSMActor know which type of job should be processed

The answer is that, there is a api implementation, when the Job is created, then the Job has
the translator actor's information

From the [code](dora/src/main/scala/doracore/api/ProcessTranApi.scala) 

```scala
trait ProcessTranApi extends AskProcessResult {
  this: SystemApi with DriverApi =>
  val processTranActor = actorSystem.actorOf(               //The translator actor is created in api 
    ProcessTranActor.processTranActorProps,
    CNaming.timebasedName("defaultProcessTranActor")
  )

  def runProcessCommand(processCallMsg: ProcessCallMsg, timeout: Timeout = longTimeout)(implicit
      ex: ExecutionContext
  ): Future[JobResult] = {
    val processJob = JobMsg("SimpleProcess", processCallMsg)
    val receiveActor =
      actorSystem.actorOf(ReceiveActor.receiveActorProps, CNaming.timebasedName("Receive"))
    val processJobRequest = JobRequest(processJob, receiveActor, processTranActor)   // the function will add translator actor to Job request.
    getProcessCommandFutureResult(processJobRequest, defaultDriver, receiveActor, timeout)
  }
}

```

And in [FSMActor](dora/src/main/scala/doracore/core/fsm/FsmActor.scala), the tranActor will start do the job request

```scala
  def hundleRequestList(requestList: RequestList) = {
    if (requestList.requests.length > 0) {
      setTimeOutCheck()
      requestList.requests.map { request =>
        replyToActor = Some(request.replyTo)
        jobMetaOpt = request.jobMetaOpt
        log.info(
          s"{${request.jobMetaOpt}} is started in fsm worker, and will be handled by {${request.tranActor}}"
        )
        log.debug(s"${request.taskMsg} is handled in FSM actor, the task will be start soon")
        request.tranActor ! request
        request.replyTo ! JobStatus.Scheduled
      }
    }
  }
```


## Question

How many translator actor should have? One or many?

It depends on the scenario, if the translator only do the "pure" translate work, one actor is enough for a type of task,
and one actor could also keep the relative order of job request. If we want to translator actor do the blocked thread job,
then maybe more translator actor needs to be reacted.



