addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.19")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0-M1")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.6.1")

// defined in project/plugins.sbt
addSbtPlugin("ch.epfl.scala" % "sbt-release-early" % "2.1.1")

//To display dependencies in project  https://stackoverflow.com/questions/25519926/how-to-see-dependency-tree-in-sbt
addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.9.2")

addSbtPlugin("com.lightbend.paradox" % "sbt-paradox" % "0.6.8")

// code formatting https://scalameta.org/scalafmt/docs/installation.html
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.0") // "2.4.0" is just sbt plugin version

// https://github.com/djspiewak/sbt-github-actions
addSbtPlugin("com.codecommit" % "sbt-github-actions" % "0.13.0")