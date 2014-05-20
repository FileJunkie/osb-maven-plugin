package name.filejunkie.osbmavenplugin.utils

import java.io.{StringReader, File}
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory}
import org.w3c.dom.Document
import org.xml.sax.InputSource

object XMLUtils {
  private val documentBuilder = {
    val documentBuilderFactory = DocumentBuilderFactory.newInstance()
    documentBuilderFactory.setNamespaceAware(true)
    documentBuilderFactory.newDocumentBuilder()
  }

  private def getNamespace(document: Document): String = {
    val documentElement = document.getDocumentElement
    documentElement.normalize()
    documentElement.getAttribute("targetNamespace")
  }

  private def getDependencies(document: Document): Seq[String] = {
    val imports = document.getElementsByTagNameNS("*", "import")
    (0 until imports.getLength).map(imports.item(_).getAttributes.getNamedItem("schemaLocation").getNodeValue)
  }

  private def getPort(document: Document): String = {
    document.getElementsByTagNameNS("*", "port").item(0).getAttributes.getNamedItem("name").getNodeValue
  }

  def getNamespace(file: File): String = this.synchronized{getNamespace(documentBuilder.parse(file))}
  def getNamespace(xml: String): String = this.synchronized{getNamespace(documentBuilder.parse(new InputSource(new StringReader(xml))))}
  def getDependencies(file: File): Seq[String] = this.synchronized{getDependencies(documentBuilder.parse(file))}
  def getDependencies(xml: String): Seq[String] = this.synchronized{getDependencies(documentBuilder.parse(new InputSource(new StringReader(xml))))}
  def getPort(file: File): String = this.synchronized{getPort(documentBuilder.parse(file))}
  def getPort(xml: String): String = this.synchronized{getPort(documentBuilder.parse(new InputSource(new StringReader(xml))))}
}
