name := "BlogContent"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq("com.typesafe.akka" %% "akka-http" % "10.0.11",
  "org.json4s" %% "json4s-native" % "3.2.11",
  "org.scalatest" %% "scalatest" % "3.0.1" % Test,
  "org.mockito" % "mockito-core" % "2.11.0" % Test,
  "com.typesafe.akka" %% "akka-http-testkit" % "10.1.1")