import sbt.util

name := "cloud-simulator"

version := "0.1"

scalaVersion := "3.0.2"
logLevel := util.Level.Info

val logbackVersion = "1.3.0-alpha10"
val sfl4sVersion = "2.0.0-alpha5"
val typesafeConfigVersion = "1.4.1"
val apacheCommonIOVersion = "2.11.0"
val scalacticVersion = "3.2.9"
val scalaticPlusVersion = "3.2.9.0"
val scalaMockVersion = "5.1.0"
val mockitoVersion = "3.12.4"
val cloudSimPlusVersion = "6.4.3"

resolvers += Resolver.jcenterRepo

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-core" % logbackVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "org.slf4j" % "slf4j-api" % sfl4sVersion,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "commons-io" % "commons-io" % apacheCommonIOVersion,
  "org.scalactic" %% "scalactic" % scalacticVersion,
  "org.scalatest" %% "scalatest" % scalacticVersion % Test,
  "org.scalatest" %% "scalatest-featurespec" % scalacticVersion % Test,
  "org.scalatestplus" %% "mockito-3-4" % scalaticPlusVersion % Test,
  "org.scalamock" % "scalamock_2.11" % scalaMockVersion,
  "org.mockito" % "mockito-core" % mockitoVersion,
  "com.typesafe" % "config" % typesafeConfigVersion,
  "org.cloudsimplus" % "cloudsim-plus" % cloudSimPlusVersion
)