name := """play-java-preview"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "org.mongodb" % "mongo-java-driver" % "2.12.2",
  "org.mongodb.morphia" % "morphia" % "0.107",
  "org.webjars" % "bootstrap" % "3.1.1-1",
  "org.webjars" % "jquery" % "2.1.0-2",
  "org.webjars" % "typeaheadjs" % "0.10.2",
  "org.webjars" %% "webjars-play" % "2.3.0-RC1-1"
)
