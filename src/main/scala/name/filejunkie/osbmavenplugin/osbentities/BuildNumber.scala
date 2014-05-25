package name.filejunkie.osbmavenplugin.osbentities

class BuildNumber(osbEntityName: String, buildNumber: Option[String]) extends OSBEntity(osbEntityName, None) {
  private val defaultBuild = "LOCAL_BUILD"

  override val representationVersion: String = "0"
  override val entitiesFolder: Option[String] = Some("Resources")
  override val dataClass: String = "com.bea.wli.sb.resources.config.impl.XmlEntryDocumentImpl"
  override val typeId: String = "XML"

  override def content: String =
    "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<con:xmlEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n" +
      "    <con:xml-content>&lt;buildNumber>" + buildNumber.getOrElse(defaultBuild) +
      "&lt;/buildNumber></con:xml-content>\n</con:xmlEntry>"
}
