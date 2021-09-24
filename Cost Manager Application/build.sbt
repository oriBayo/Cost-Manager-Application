name := "costmanagerscala"
 
version := "1.0" 
      
lazy val `costmanagerscala` = (project in file(".")).enablePlugins(PlayScala)

      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
    jdbc,
    ehcache,
    ws,
    specs2 % Test,
    guice,
    "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play27"
)

libraryDependencies += "org.webjars" % "bootstrap" % "3.3.6"
libraryDependencies += "org.webjars" % "requirejs" % "2.2.0"

play.sbt.routes.RoutesKeys.routesImport +=
  "play.modules.reactivemongo.PathBindables._"
