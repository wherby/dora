import sbt.Keys.{libraryDependencies, version}
import Dependencies._

import scala.sys.process.Process

//ThisBuild / scalaVersion := scala213

publishMavenStyle := true
releaseEarlyWith in Global := SonatypePublisher

pgpPublicRing := file("./travis/local.pubring.asc")
pgpSecretRing := file("./travis/local.secring.asc")

lazy val dora = (project in file("dora"))
  .settings(commonSettings: _*)
  .enablePlugins(JavaAppPackaging)
  .settings(
    name := "dora",
    publishArtifact := true
  )

lazy val root = (project in file("."))
  .enablePlugins(JavaAppPackaging)
  .settings(commonSettings: _*)
  .settings(
    name := "doradilla",
    publishArtifact := false,
    mainClass := Some("io.github.wherby.doradilla.app.SimpleClusterApp") //object with,
  )
  .aggregate(dora)
  .dependsOn(dora)

// Define a special test task which does not fail when any test fails,
// so sequential tasks (like SonarQube analysis) will be performed no matter the test result.
lazy val ciTests = taskKey[Unit]("Run tests for CI")

ciTests := {
  // Capture the test result
  val testResult = (test in Test).result.value
}
coverageEnabled in Test := true

//setup precommit
lazy val installPre = taskKey[Unit]("setup pre-commit")

installPre := {
  val shell: Seq[String] =
    if (sys.props("os.name").contains("Windows")) Seq("cmd", "/c") else Seq("bash", "-c")
  val cmds = Seq(
    "brew install pre-commit",
    "pre-commit install"
  )
  val cmdsExe = cmds.map { cmd =>
    Process(shell :+ cmd).!
  }
}

// https://github.com/djspiewak/sbt-github-actions
ThisBuild / githubWorkflowTargetTags ++= Seq("v*")
ThisBuild / githubWorkflowPublishTargetBranches :=
  Seq(RefPredicate.StartsWith(Ref.Tag("v")))

//ThisBuild / crossScalaVersions := supportedScalaVersion

ThisBuild / githubWorkflowPublish := Seq(WorkflowStep.Sbt(List("ci-release")))

ThisBuild / githubWorkflowPublish := Seq(
  WorkflowStep.Sbt(
    List("ci-release"),
    env = Map(
      "PGP_PASSPHRASE" -> "${{ secrets.PGP_PASSPHRASE }}",
      "PGP_SECRET" -> "${{ secrets.PGP_SECRET }}",
      "SONATYPE_PASSWORD" -> "${{ secrets.SONATYPE_PASSWORD }}",
      "SONATYPE_USERNAME" -> "${{ secrets.SONATYPE_USERNAME }}"
    )
  )
)