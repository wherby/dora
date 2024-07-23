import com.typesafe.sbt.SbtNativePackager.autoImport.maintainer
import sbt.Keys._
import sbt.{url, _}

/** For  in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/31
  */
object Dependencies {
  lazy val akka = "2.6.20"
  lazy val scala212 = "2.12.15"
  lazy val scala213 = "2.13.10"
  lazy val supportedScalaVersion = List(scala212, scala213)

  lazy val logback = "1.5.6"
  lazy val scalaTest = "3.1.0"
  lazy val playJson = "2.9.3"
  lazy val javaDriverCore = "4.15.0"
  lazy val nettyHandler = "4.1.84.Final"
  lazy val jacksonDataBind = "2.14.0-rc2"

  /* dependencies */
  val commonDependencies = Seq(
    // -- Logging --
    "ch.qos.logback" % "logback-classic" % logback,
    // -- Akka --
    "com.typesafe.akka" %% "akka-actor" % akka,
    "com.typesafe.akka" %% "akka-slf4j" % akka,
    "com.typesafe.akka" %% "akka-testkit" % akka,
    // https://mvnrepository.com/artifact/org.scalatest/scalatest
    "org.scalatest" %% "scalatest" % scalaTest % Test,
    // https://mvnrepository.com/artifact/com.typesafe.play/play-json
    "com.typesafe.play" %% "play-json" % playJson,
    // https://mvnrepository.com/artifact/com.datastax.cassandra/cassandra-driver-core
    // "com.datastax.oss" % "java-driver-core" % javaDriverCore,
    //"io.netty"         % "netty-handler"    % "4.1.70.Final"
    // fix CVE issues
    // https://mvnrepository.com/artifact/io.netty/netty-handler
    //"io.netty" % "netty-handler" % nettyHandler,
    // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
    "com.fasterxml.jackson.core" % "jackson-databind" % jacksonDataBind
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
      "Apache License 2.0" -> url(
        "https://github.com/wherby/dora/blob/master/LICENSE"
      )
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
