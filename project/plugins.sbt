addSbtPlugin("com.github.sbt" % "sbt-native-packager" % "1.10.4")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "2.1.0")

// defined in project/plugins.sbt
//addSbtPlugin("ch.epfl.scala" % "sbt-release-early" % "2.1.1")

//To display dependencies in project  https://stackoverflow.com/questions/25519926/how-to-see-dependency-tree-in-sbt
//addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

addSbtPlugin("com.lightbend.paradox" % "sbt-paradox" % "0.6.8")
addSbtPlugin("io.github.jonas" % "sbt-paradox-material-theme" % "0.6.0")

// code formatting https://scalameta.org/scalafmt/docs/installation.html
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0") // "2.4.0" is just sbt plugin version

// https://github.com/djspiewak/sbt-github-actions
addSbtPlugin("com.github.sbt" % "sbt-github-actions"  % "0.24.0")
// https://github.com/sbt/sbt-ci-release
//addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.11")
addSbtPlugin("com.github.sbt" % "sbt-ci-release" % "1.5.12")

//addDependencyTreePlugin
