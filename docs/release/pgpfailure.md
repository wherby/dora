
##

[info] Setting Scala version to 2.12.15 on 3 projects.
[info] Reapplying settings...
[info] looking for workflow definition in /home/runner/work/dora/dora/.github/workflows
[info] set current project to dora (in build file:/home/runner/work/dora/dora/)
[info] Wrote /home/runner/work/dora/dora/docs/target/scala-2.12/document-for-dora_2.12-1.8.0.7.010.pom
[info] Wrote /home/runner/work/dora/dora/dora/target/scala-2.12/dora_2.12-1.8.0.7.010.pom
[warn] There may be incompatibilities among your library dependencies; run 'evicted' to see detailed eviction warnings.
[warn] There may be incompatibilities among your library dependencies; run 'evicted' to see detailed eviction warnings.
[info] Main Scala API documentation to /home/runner/work/dora/dora/dora/target/scala-2.12/api...
[info] compiling 55 Scala sources to /home/runner/work/dora/dora/dora/target/scala-2.12/classes ...
[info] Non-compiled module 'compiler-bridge_2.12' for Scala 2.12.15. Compiling...
[info] gpg: no default secret key: No secret key
[info] gpg: signing failed: No secret key
[info]   Compilation completed in 7.936s.
model contains 162 documentable templates
[warn] one deprecation
[warn] three deprecations (since 2.6.0)
[warn] four deprecations in total; re-run with -deprecation for details
[warn] three warnings found
[info] done compiling
[info] Main Scala API documentation successful.
[info] Test Scala API documentation to /home/runner/work/dora/dora/dora/target/scala-2.12/test-api...
[info] compiling 37 Scala sources to /home/runner/work/dora/dora/dora/target/scala-2.12/test-classes ...
model contains 69 documentable templates
[warn] 9 deprecations (since 3.1.0); re-run with -deprecation for details
[warn] one warning found
[info] done compiling
[info] Test Scala API documentation successful.
[info] gpg: no default secret key: No secret key
[info] gpg: signing failed: No secret key
[error] java.lang.RuntimeException: Failure running '/home/runner/work/dora/dora/gpg.sh --batch --pinentry-mode loopback --passphrase *** --detach-sign --armor --use-agent --output /home/runner/work/dora/dora/dora/target/scala-2.12/dora_2.12-1.8.0.7.010-tests-sources.jar.asc /home/runner/work/dora/dora/dora/target/scala-2.12/dora_2.12-1.8.0.7.010-tests-sources.jar'.  Exit code: 2
[error] 	at scala.sys.package$.error(package.scala:30)
[error] 	at com.jsuereth.sbtpgp.CommandLineGpgSigner.sign(PgpSigner.scala:74)
[error] 	at com.jsuereth.sbtpgp.PgpSettings$.$anonfun$signingSettings$2(PgpSettings.scala:151)
[error] 	at scala.collection.TraversableLike.$anonfun$flatMap$1(TraversableLike.scala:292)
[error] 	at scala.collection.immutable.HashMap$HashMap1.foreach(HashMap.scala:394)
[error] 	at scala.collection.immutable.HashMap$HashTrieMap.foreach(HashMap.scala:721)
[error] 	at scala.collection.TraversableLike.flatMap(TraversableLike.scala:292)
[error] 	at scala.collection.TraversableLike.flatMap$(TraversableLike.scala:289)
[error] 	at scala.collection.AbstractTraversable.flatMap(Traversable.scala:108)
[error] 	at com.jsuereth.sbtpgp.PgpSettings$.$anonfun$signingSettings$1(PgpSettings.scala:146)
[error] 	at scala.Function1.$anonfun$compose$1(Function1.scala:49)
[error] 	at sbt.internal.util.$tilde$greater.$anonfun$$u2219$1(TypeFunctions.scala:62)
[error] 	at sbt.std.Transform$$anon$4.work(Transform.scala:68)
[error] 	at sbt.Execute.$anonfun$submit$2(Execute.scala:282)
[error] 	at sbt.internal.util.ErrorHandling$.wideConvert(ErrorHandling.scala:23)
[error] 	at sbt.Execute.work(Execute.scala:291)
[error] 	at sbt.Execute.$anonfun$submit$1(Execute.scala:282)
[error] 	at sbt.ConcurrentRestrictions$$anon$4.$anonfun$submitValid$1(ConcurrentRestrictions.scala:265)
[error] 	at sbt.CompletionService$$anon$2.call(CompletionService.scala:64)
[error] 	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error] 	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
[error] 	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error] 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[error] 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[error] 	at java.base/java.lang.Thread.run(Thread.java:829)
[error] java.lang.RuntimeException: Failure running '/home/runner/work/dora/dora/gpg.sh --batch --pinentry-mode loopback --passphrase *** --detach-sign --armor --use-agent --output /home/runner/work/dora/dora/docs/target/scala-2.12/document-for-dora_2.12-1.8.0.7.010.pom.asc /home/runner/work/dora/dora/docs/target/scala-2.12/document-for-dora_2.12-1.8.0.7.010.pom'.  Exit code: 2
[error] 	at scala.sys.package$.error(package.scala:30)
[error] 	at com.jsuereth.sbtpgp.CommandLineGpgSigner.sign(PgpSigner.scala:74)
[error] 	at com.jsuereth.sbtpgp.PgpSettings$.$anonfun$signingSettings$2(PgpSettings.scala:151)
[error] 	at scala.collection.TraversableLike.$anonfun$flatMap$1(TraversableLike.scala:292)
[error] 	at scala.collection.immutable.Map$Map4.foreach(Map.scala:431)
[error] 	at scala.collection.TraversableLike.flatMap(TraversableLike.scala:292)
[error] 	at scala.collection.TraversableLike.flatMap$(TraversableLike.scala:289)
[error] 	at scala.collection.AbstractTraversable.flatMap(Traversable.scala:108)
[error] 	at com.jsuereth.sbtpgp.PgpSettings$.$anonfun$signingSettings$1(PgpSettings.scala:146)
[error] 	at scala.Function1.$anonfun$compose$1(Function1.scala:49)
[error] 	at sbt.internal.util.$tilde$greater.$anonfun$$u2219$1(TypeFunctions.scala:62)
[error] 	at sbt.std.Transform$$anon$4.work(Transform.scala:68)
[error] 	at sbt.Execute.$anonfun$submit$2(Execute.scala:282)
[error] 	at sbt.internal.util.ErrorHandling$.wideConvert(ErrorHandling.scala:23)
[error] 	at sbt.Execute.work(Execute.scala:291)
[error] 	at sbt.Execute.$anonfun$submit$1(Execute.scala:282)
[error] 	at sbt.ConcurrentRestrictions$$anon$4.$anonfun$submitValid$1(ConcurrentRestrictions.scala:265)
[error] 	at sbt.CompletionService$$anon$2.call(CompletionService.scala:64)
[error] 	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error] 	at java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
[error] 	at java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error] 	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[error] 	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[error] 	at java.base/java.lang.Thread.run(Thread.java:829)
[error] (dora / signedArtifacts) Failure running '/home/runner/work/dora/dora/gpg.sh --batch --pinentry-mode loopback --passphrase *** --detach-sign --armor --use-agent --output /home/runner/work/dora/dora/dora/target/scala-2.12/dora_2.12-1.8.0.7.010-tests-sources.jar.asc /home/runner/work/dora/dora/dora/target/scala-2.12/dora_2.12-1.8.0.7.010-tests-sources.jar'.  Exit code: 2
[error] (docs / signedArtifacts) Failure running '/home/runner/work/dora/dora/gpg.sh --batch --pinentry-mode loopback --passphrase *** --detach-sign --armor --use-agent --output /home/runner/work/dora/dora/docs/target/scala-2.12/document-for-dora_2.12-1.8.0.7.010.pom.asc /home/runner/work/dora/dora/docs/target/scala-2.12/document-for-dora_2.12-1.8.0.7.010.pom'.  Exit code: 2
[error] Total time: 26 s, completed Jul 23, 2024, 6:49:25 AM


