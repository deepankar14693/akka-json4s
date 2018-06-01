name := "BlogContent"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.0.11"

libraryDependencies += "org.json4s" %% "json4s-native" % "3.2.11"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % Test

libraryDependencies += "com.google.inject" % "guice" % "3.0"

libraryDependencies += "com.typesafe.akka" %% "akka-http-testkit" % "10.1.1"

libraryDependencies += "org.mockito" % "mockito-core" % "2.11.0" % Test