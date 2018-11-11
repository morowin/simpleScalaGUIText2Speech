name := "simpleScalaGUIText2Speech"
organization := "com.morowin"
version := "0.1"

scalaVersion := "2.11.8"
val sparkVersion = "2.2.0"
val maryTTSVersion = "5.2"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion // spark-core
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion // spark-sql
libraryDependencies += "org.scala-lang" % "scala-swing" % "2.11.0-M7" // swing
libraryDependencies += "joda-time" % "joda-time" % "2.9.9" // joda-time

// maryTTS dependancies
resolvers += "Spring Plugins Repository" at "http://repo.spring.io/plugins-release/"
libraryDependencies += "de.dfki.mary" % "marytts-common" % maryTTSVersion
libraryDependencies += "de.dfki.mary" % "marytts-runtime" % maryTTSVersion
libraryDependencies += "de.dfki.mary" % "voice-upmc-pierre-hsmm" % maryTTSVersion
libraryDependencies += "de.dfki.mary" % "voice-enst-dennys-hsmm" % maryTTSVersion
excludeDependencies += "xerces"

javaOptions += "-Djavax.xml.transform.TransformerFactory=com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl"
fork in run := true