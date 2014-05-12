package name.filejunkie.osbmavenplugin.manager

import java.io.{PrintWriter, File}
import name.filejunkie.osbmavenplugin.osbentities.OSBEntity
import java.text.SimpleDateFormat
import java.util.{Locale, Calendar}

class ExportInfo(val exportInfoFile: File) {
  private var osbEntities: List[OSBEntity] = List()

  private def prefix =
    """|<?xml version="1.0" encoding="UTF-8"?>
      |<xml-fragment name="" version="v2">
      |  <imp:properties xmlns:imp="http://www.bea.com/wli/config/importexport">
      |    <imp:property name="username" value="weblogic"/>
      |    <imp:property name="description" value=""/>
      |    <imp:property name="exporttime" value="$Timestamp"/>
      |    <imp:property name="productname" value="Oracle Service Bus"/>
      |    <imp:property name="productversion" value="11.1.1.5"/>
      |    <imp:property name="projectLevelExport" value="false"/>
      |  </imp:properties>
    """.stripMargin.replace("$Timestamp", new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH).format(Calendar.getInstance().getTime))

  private def entityReferences = (for(osbEntity <- osbEntities) yield osbEntity.exportInfoLine) mkString "\n"

  private val postfix = "</xml-fragment>"

  def exportInfoText = prefix + entityReferences + postfix

  def addOSBEntity(osbEntity: OSBEntity) = {
    osbEntities = osbEntity :: osbEntities
  }

  def writeFiles() = {
    for(osbEntity <- osbEntities) osbEntity.writeFile

    val writer = new PrintWriter(exportInfoFile)
    writer.write(exportInfoText)
    writer.close()
  }
}
