package name.filejunkie.osbmavenplugin.osbentities

import name.filejunkie.osbmavenplugin.utils.XMLUtils

class WSDL(osbEntityName: String, thisEntityFolder: Option[String]) extends XSD(osbEntityName, thisEntityFolder){
  override val dataClass: String = "com.bea.wli.sb.resources.config.impl.WsdlEntryDocumentImpl"
  override val typeId: String = "WSDL"

  override val inputFileName = OSBEntity.inputFolder + "/" + thisEntityFolder.getOrElse("") + "/" + osbEntityName + ".wsdl"

  lazy val port = XMLUtils.getPort(readFileContent)
  lazy val namespace = XMLUtils.getNamespace(readFileContent)

  override protected val xml1 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<con:wsdlEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n    <con:wsdl><![CDATA["
  override protected val xml2 = "]]></con:wsdl>\n"
  override protected val xml3 = "</con:targetNamespace>\n</con:wsdlEntry>"
}
