name := "acceler8"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  javaJpa,
  filters,
  cache,
  javaWs
)


PlayKeys.externalizeResources := false

EclipseKeys.projectFlavor := EclipseProjectFlavor.Java           // Java project. Don't expect Scala IDE
EclipseKeys.createSrc := EclipseCreateSrc.ValueSet(EclipseCreateSrc.ManagedClasses, EclipseCreateSrc.ManagedResources)  // Use .class files instead of generated .scala files for views and routes 
EclipseKeys.preTasks := Seq(compile in Compile)                  // Compile the project before generating Eclipse files, so that .class files for views and routes are present


libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.36"
libraryDependencies += "org.hibernate" % "hibernate-entitymanager" % "5.1.0.Final"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m" 

// https://mvnrepository.com/artifact/org.apache.commons/commons-email
libraryDependencies += "org.apache.commons" % "commons-email" % "1.4"

libraryDependencies += "com.typesafe.play" %% "play-mailer" % "5.0.0"


resolvers += "scribe java" at "https://mvnrepository.com/artifact/com.github.scribejava/scribejava-apis"
libraryDependencies += "com.github.scribejava" % "scribejava-apis" % "3.2.0"

resolvers += "strava java" at "https://mvnrepository.com/artifact/com.github.danshannon/javastrava-api"
libraryDependencies += "com.github.danshannon" % "javastrava-api" % "1.0.3"

resolvers += "log4j" at "https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-api"
libraryDependencies += "org.apache.logging.log4j" % "log4j-api" % "2.2"

resolvers += "slf4j" at "https://mvnrepository.com/artifact/org.slf4j/slf4j-api"
libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.7"

resolvers += "commons-logging" at "https://mvnrepository.com/artifact/commons-logging/commons-logging"
libraryDependencies += "commons-logging" % "commons-logging" % "1.2"

fork in run := false

fork in run := true