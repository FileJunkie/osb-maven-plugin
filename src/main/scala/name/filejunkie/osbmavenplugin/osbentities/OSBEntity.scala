package name.filejunkie.osbmavenplugin.osbentities

import java.io.{PrintWriter, File}

abstract class OSBEntity(osbEntityName: String, thisEntityFolder: Option[String]) {
  val representationVersion: String
  val dataClass: String
  val typeId: String
  val entitiesFolder: Option[String]
  val properties: List[String] = Nil
  lazy val dependencies: Seq[OSBEntity] = Nil

  def content: String
  protected lazy val outputFileDir: String = OSBEntity.outputFolder + "/" + pathDirOnlyFixed
  protected lazy val outputFilePath: String = OSBEntity.outputFolder + "/" + pathFixed

  lazy val pathDirOnly = (OSBEntity.osbProjectName match {
    case Some(s) => s + "/"
    case _ => ""
  }) + OSBEntity.thisProjectName + "/" + (entitiesFolder match {
    case Some(s) => s + "/"
    case _ => ""
  }) + (thisEntityFolder match {
    case Some(s) => s + "/"
    case _ => ""
  })
  lazy val path = pathDirOnly + osbEntityName

  lazy val pathFixed = path.replaceAll(" ", "_") + "." + typeId
  lazy val pathDirOnlyFixed = pathDirOnly.replaceAll(" ", "_")
  lazy val extRefPath = typeId + "$" + path.replaceAll(" ", "\\$")

  def exportInfoLine = {
    val allProperties = properties.foldLeft("")((m: String, n: String) => m + "\n" + n) + "\n"

    val dependenciesLinks = dependencies.map("      <imp:property name=\"extrefs\" value=\"" + _.extRefPath + "\"/>\n")
      .foldLeft("")((m: String, n: String) => m + n)

    "  <imp:exportedItemInfo instanceId=\"" + path + "\" typeId=\"" + typeId + "\" xmlns:imp=\"http://www.bea.com/wli/config/importexport\">\n" +
    "    <imp:properties>\n" +
    "      <imp:property name=\"representationversion\" value=\"" + representationVersion + "\"/>\n" +
    "      <imp:property name=\"dataclass\" value=\"" + dataClass + "\"/>\n" +
    "      <imp:property name=\"isencrypted\" value=\"false\"/>\n" +
    "      <imp:property name=\"jarentryname\" value=\"" + pathFixed + "\"/>\n" +
    dependenciesLinks +
    allProperties +
    "    </imp:properties>\n" +
    "  </imp:exportedItemInfo>\n"
  }

  def writeFile(): Unit = {
    val dir = new File(outputFileDir)
    dir.mkdirs()

    val file = new File(outputFilePath)
    val writer = new PrintWriter(file)
    writer.write(content)
    writer.close()
  }

  protected def cutExtension(str: String) = {
    if(str.contains(".")){
      str.substring(0, str.lastIndexOf("."))
    }
    else{
      str
    }
  }

  protected def mergePath(path1: String, path2: String) = {
    if(!path2.startsWith("../")){
      path1 + "/" + path2
    }
    else {
      var path2tmp = path2

      val path1Components = path1.split("\\/")
      var componentsAmount = path1Components.length
      while (path2tmp.startsWith("../") && componentsAmount > 0) {
        componentsAmount = componentsAmount - 1
        path2tmp = path2tmp.replaceFirst("\\.\\.\\/", "")
      }

      (0 until componentsAmount).foreach(i => path1Components(i) + "/") + path2tmp
    }
  }
}

object OSBEntity {
  var inputFolder: String = ""
  var outputFolder: String = ""
  var osbProjectName: Option[String] = None
  var thisProjectName: String = ""
}