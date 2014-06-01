package name.filejunkie.osbmavenplugin.osbentities

import org.apache.commons.io.FileUtils
import java.io.File
import name.filejunkie.osbmavenplugin.utils.XMLUtils


class WSDL(osbEntityName: String, thisEntityFolder: Option[String]) extends OSBEntity(osbEntityName, thisEntityFolder){
  override val representationVersion: String = "0"
  override val entitiesFolder: Option[String] = Some("Resources")
  override val dataClass: String = "com.bea.wli.sb.resources.config.impl.WsdlEntryDocumentImpl"
  override val typeId: String = "WSDL"

  var wsdlContent: Option[String] = None
  val inputFileName = OSBEntity.inputFolder + "/" + thisEntityFolder.getOrElse("") + "/" + osbEntityName + ".wsdl"
  lazy val readWsdlContent = wsdlContent.getOrElse(FileUtils.readFileToString(new File(inputFileName)))
  lazy val dependenciesLinks = XMLUtils.getDependencies(readWsdlContent)

  lazy val port = XMLUtils.getPort(readWsdlContent)
  lazy val namespace = XMLUtils.getNamespace(readWsdlContent)

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
  
  override def content: String = {
    val depsText = if (!dependenciesLinks.isEmpty){
      "    <con:dependencies>\n" +
        dependenciesLinks.map(dep =>
          "        <con:schemaRef isInclude=\"false\" schemaLocation=\"" +
            dep +
            "\" namespace=\"" +
            XMLUtils.getNamespace(new File(OSBEntity.inputFolder + "/" + entitiesFolder.getOrElse("") + "/" + dep)) +
            "\" ref=\"" + mergePath(pathDirOnlyFixed, cutExtension(dep)) + "\"/>\n"

        ).foldLeft("")((m: String, n: String) => m + n) +
        "    </con:schemaRef>\n"
    }
    else ""

    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<con:wsdlEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n    <con:wsdl><![CDATA[" +
    readWsdlContent +
    "]]></con:wsdl>\n" +
    depsText  +
    "    <con:targetNamespace>" +
    XMLUtils.getNamespace(readWsdlContent) +
    "</con:targetNamespace>\n</con:wsdlEntry>"
  }
}
