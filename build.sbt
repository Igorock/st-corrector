name := "st-corrector"
version := "1.0"
scalaVersion := "2.11.8"
organization := "com.storetranslate"
mainClass in Compile := Some("com.storetranslate.engine.corrector.WebServer")

libraryDependencies ++= {
  val akkaV = "2.4.11"
  val phantomV = "1.22.0"

  Seq(
    "com.websudos"        %%  "phantom-dsl"                 % phantomV,
    "com.websudos"        %%  "phantom-reactivestreams"     % phantomV,
    "org.scalatest" % "scalatest_2.11" % "3.0.0" % "test",
    "com.typesafe.akka" %% "akka-http-core" % akkaV,
    "com.typesafe.akka" %% "akka-http-experimental" % akkaV,
    "com.typesafe.akka" %% "akka-http-spray-json-experimental" % akkaV
  )
}

resolvers ++= Seq(
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  Resolver.typesafeRepo("releases"),
  Resolver.sonatypeRepo("releases"),
  Resolver.bintrayRepo("websudos", "oss-releases")
)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case "reference.conf" => MergeStrategy.concat
 case x => MergeStrategy.first
}