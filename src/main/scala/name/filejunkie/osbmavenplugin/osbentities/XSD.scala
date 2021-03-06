package name.filejunkie.osbmavenplugin.osbentities

import java.io.File
import org.apache.commons.io.FileUtils
import name.filejunkie.osbmavenplugin.utils.XMLUtils

class XSD(osbEntityName: String, thisEntityFolder: Option[String]) extends OSBEntity(osbEntityName, thisEntityFolder){
  override val representationVersion: String = "0"
  override val dataClass: String = "com.bea.wli.sb.resources.config.impl.SchemaEntryDocumentImpl"
  override val typeId: String = "XMLSchema"
  override val entitiesFolder = Some("Resources")

  var fileContent: Option[String] = None
  val inputFileName = OSBEntity.inputFolder + "/" + thisEntityFolder.getOrElse("") + "/" + osbEntityName + ".xsd"
  lazy val readFileContent = fileContent.getOrElse(FileUtils.readFileToString(new File(inputFileName)))
  lazy val dependenciesLinks = XMLUtils.getDependencies(readFileContent)

  override lazy val dependencies = {
    for(dep <- dependenciesLinks) yield {
      val dep2 = dep.replace("../","")

      val xsdParams = if(dep2.contains("/")){
        val split = dep2.split("\\/")
        (Some(split(0)), cutExtension(split(1)))
      }
      else{
        (None, dep2)
      }

      new XSD(xsdParams._2, xsdParams._1)
    }
  }

  protected val xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<con:schemaEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n    <con:schema><![CDATA["
  protected val xml2 = "]]></con:schema>\n"
  protected val xml3 = "</con:targetNamespace>\n</con:schemaEntry>"

  override def content: String = {
    val depsText = if (!dependenciesLinks.isEmpty){
      "    <con:dependencies>\n" +
        dependenciesLinks.map(dep =>
          "        <con:import schemaLocation=\"" +
            dep +
            "\" namespace=\"" +
            XMLUtils.getNamespace(new File(OSBEntity.inputFolder + "/" + entitiesFolder.getOrElse("") + "/" + dep)) +
            "\" ref=\"" + mergePath(pathDirOnlyFixed, cutExtension(dep)) + "\"/>\n"

        ).foldLeft("")((m: String, n: String) => m + n) +
        "    </con:dependencies>\n"
    }
    else ""

    xml1 +
      readFileContent +
      xml2 +
    depsText  +
    "    <con:targetNamespace>" +
    XMLUtils.getNamespace(readFileContent) +
      xml3

  }
}
