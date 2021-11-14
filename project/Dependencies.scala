import com.typesafe.sbt.SbtNativePackager.autoImport.maintainer
import sbt.Keys._
import sbt.{url, _}

/** For  in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/31
  */
object Dependencies {
  lazy val akka                  = "2.5.22"
  lazy val scala212              = "2.12.7"
  lazy val scala213              = "2.13.1"
  lazy val supportedScalaVersion = List(scala212, scala213)

  /* dependencies */
  val commonDependencies = Seq(
    // -- Logging --
    "ch.qos.logback" % "logback-classic" % "1.2.6",
    // -- Akka --
    "com.typesafe.akka" %% "akka-actor"   % akka,
    "com.typesafe.akka" %% "akka-slf4j"   % akka,
    "com.typesafe.akka" %% "akka-testkit" % akka,
    // https://mvnrepository.com/artifact/org.scalatest/scalatest
    "org.scalatest" %% "scalatest" % "3.1.0" % Test,
    // https://mvnrepository.com/artifact/com.typesafe.play/play-json
    "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
    // https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
    "com.datastax.oss" % "java-driver-core" % "4.13.0",
    "io.netty"         % "netty-handler"    % "4.1.70.Final"
  )

  lazy val commonSettings = Seq(
    organization := "io.github.wherby",
    crossScalaVersions := supportedScalaVersion,

    resolvers ++= Seq(
      Resolver.sonatypeRepo("releases"),
      Resolver.sonatypeRepo("snapshots")
    ),
    libraryDependencies ++= commonDependencies,
    maintainer := "wherby <187225577@qq.com>",
    licenses := Seq(
      "Apache License 2.0" -> url("https://github.com/wherby/dora/blob/master/LICENSE")
    ),
    //useGpg := true,
    homepage := Some(url("https://github.com/wherby/dora")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/wherby/dora.git"),
        "scm:git@github.com:wherby/dora.git"
      )
    )
    // publishTo := {
    //   val nexus = "https://oss.sonatype.org/"
    //   if (version.value.contains("SNAPSHOT"))
    //     Some("snapshots" at nexus + "content/repositories/snapshots")
    //   else
    //     Some("releases" at nexus + "service/local/staging/deploy/maven2")
    // }
  )

  lazy val settings = Seq(
    parallelExecution in Test := false,
    fork in run := false, //###If the value is true, Ctrl + C may only kill JVM and not kill Akka. Set to false to kill togother.  Set to true for publishing in poor network.
    // These options will be used for *all* versions.
    scalacOptions ++= Seq(
      "-deprecation",
      "-unchecked",
      "-encoding",
      "UTF-8",
      "-Xlint"
    )
  )
}
