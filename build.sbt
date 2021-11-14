import sbt.Keys.{libraryDependencies, version}
import Dependencies._

import scala.sys.process.Process


//ThisBuild / scalaVersion := scala213

publishMavenStyle := true
releaseEarlyWith in Global := SonatypePublisher


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
  .aggregate(dora,docs)
  .dependsOn(dora,docs)

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
ThisBuild / githubWorkflowPublishTargetBranches +=
  RefPredicate.StartsWith(Ref.Tag("v"))

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



//docs build
import Dependencies.commonSettings

lazy val docs = (project in file("docs"))
  .enablePlugins(ParadoxPlugin)
  .settings(commonSettings: _*)
  .settings(
    name := "document for dora",
    paradoxTheme := Some(builtinParadoxTheme("generic")),
    paradoxIllegalLinkPath := raw".*\\.md".r,
    paradoxProperties in Compile ++=Map("project.description" -> "Description for dora library.",
      "github.base_url" -> s"https://github.com/wherby/dora/tree/v${version.value}")
  )


// Define task to  copy html files
val copyDocs = taskKey[Unit]("Copy html files from src/main/html to cross-version target directory")

// Implement task
copyDocs := {
  import Path._

  val src = baseDirectory.value  /"docs" /"target" / "paradox"/"site"/ "main"

  val dest = baseDirectory.value /"public" /"docs"
  IO.delete(dest)
  dest.mkdir()
  // Copy files to source files to target
  IO.copyDirectory(src,dest)
}

inThisBuild(List(
  organization := "org.dora",
  homepage := Some(url("https://github.com/wherby/dora")),
  licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
  developers := List(
    Developer(
      "wherby",
      "Tao Zhou",
      "187225577@qq.com",
      url("https://github.com/wherby/dora")
    )
  )
))