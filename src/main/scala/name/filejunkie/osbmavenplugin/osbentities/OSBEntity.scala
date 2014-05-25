package name.filejunkie.osbmavenplugin.osbentities

import java.io.{PrintWriter, File}

abstract class OSBEntity(osbEntityName: String) {
  val representationVersion: String
  val dataClass: String
  val typeId: String
  val entitiesFolder: Option[String] = None
  val thisEntityFolder: Option[String] = None
  val properties = List[String]()
  val dependencies = List[OSBEntity]()

  def content: String
  protected def outputFileDir: String
  protected def outputFilePath: String

  val pathDirOnly = (OSBEntity.osbProjectName match {
    case Some(s) => s + "/"
    case _ => ""
  }) + OSBEntity.thisProjectName + "/" + (entitiesFolder match {
    case Some(s) => s + "/"
    case _ => ""
  }) + (thisEntityFolder match {
    case Some(s) => s + "/"
    case _ => ""
  })
  val path = pathDirOnly + osbEntityName

  val pathFixed = path.replaceAll(" ", "_") + "." + typeId
  val pathDirOnlyFixed = pathDirOnly.replaceAll(" ", "_")
  val extRefPath = typeId + "$" + path.replaceAll(" ", "\\$")

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
}

object OSBEntity {
  var inputFolder: String = ""
  var outputFolder: String = ""
  var osbProjectName: Option[String] = None
  var thisProjectName: String = ""
}