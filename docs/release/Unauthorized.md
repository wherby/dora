

##
2024-07-23 09:22:08.510Z  info [SonatypeClient] Reading staging repository profiles...  - (SonatypeClient.scala:102)
2024-07-23 09:22:09.669Z  info [SonatypeClient] Reading staging profiles...  - (SonatypeClient.scala:116)
2024-07-23 09:22:18.114Z error [Sonatype]
wvlet.airframe.http.HttpClientException: [401: Unauthorized]
at wvlet.airframe.http.HttpClientException$.requestFailure(HttpClientException.scala:67)
at wvlet.airframe.http.HttpClientException$.classifyHttpResponse(HttpClientException.scala:113)
at wvlet.airframe.http.HttpClient$.$anonfun$baseHttpClientRetry$1(HttpClient.scala:156)
at wvlet.airframe.control.Retry$RetryContext.classifyResult(Retry.scala:266)
at wvlet.airframe.control.Retry$RetryContext.runInternal(Retry.scala:290)
at wvlet.airframe.control.Retry$RetryContext.runWithContext(Retry.scala:259)
at wvlet.airframe.http.client.SyncClient.send(HttpClients.scala:74)
at wvlet.airframe.http.client.SyncClient.send$(HttpClients.scala:69)
at wvlet.airframe.http.client.SyncClientImpl.send(HttpClients.scala:28)
at wvlet.airframe.http.client.SyncClient.readAsInternal(HttpClients.scala:104)
at wvlet.airframe.http.client.SyncClient.readAsInternal$(HttpClients.scala:100)
at wvlet.airframe.http.client.SyncClientImpl.readAsInternal(HttpClients.scala:28)
at xerial.sbt.sonatype.SonatypeClient.stagingRepositoryProfiles(SonatypeClient.scala:104)
at xerial.sbt.sonatype.SonatypeService.stagingRepositoryProfiles(SonatypeService.scala:118)
at xerial.sbt.sonatype.SonatypeService.findStagingRepositoryProfilesWithKey(SonatypeService.scala:112)
at xerial.sbt.sonatype.SonatypeService.dropIfExistsByKey(SonatypeService.scala:99)
at xerial.sbt.Sonatype$.$anonfun$prepare$2(Sonatype.scala:165)
at scala.concurrent.Future$.$anonfun$apply$1(Future.scala:659)
at scala.util.Success.$anonfun$map$1(Try.scala:255)
at scala.util.Success.map(Try.scala:213)
at scala.concurrent.Future.$anonfun$map$1(Future.scala:292)
at scala.concurrent.impl.Promise.liftedTree1$1(Promise.scala:33)
at scala.concurrent.impl.Promise.$anonfun$transform$1(Promise.scala:33)
at scala.concurrent.impl.CallbackRunnable.run(Promise.scala:64)
at java.base/java.util.concurrent.ForkJoinTask$RunnableExecuteAction.exec(ForkJoinTask.java:1426)
at java.base/java.util.concurrent.ForkJoinTask.doExec(ForkJoinTask.java:290)
at java.base/java.util.concurrent.ForkJoinPool$WorkQueue.topLevelExec(ForkJoinPool.java:1020)
at java.base/java.util.concurrent.ForkJoinPool.scan(ForkJoinPool.java:1656)
at java.base/java.util.concurrent.ForkJoinPool.runWorker(ForkJoinPool.java:1594)
at java.base/java.util.concurrent.ForkJoinWorkerThread.run(ForkJoinWorkerThread.java:183)  - (Sonatype.scala:443)

Error: Process completed with exit code 1.