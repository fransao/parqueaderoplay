name := """parqueaderoplay"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.6")

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.18.3" % Test
libraryDependencies += javaJdbc
libraryDependencies += jdbc % Test
//libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.41"
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"

libraryDependencies += ws
libraryDependencies += ehcache
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.9"


// libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.0"
// libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.0"
// libraryDependencies += "com.h2database" % "h2" % "1.4.197"
// libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.0-M1"
// libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4"
// libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0-M1"


