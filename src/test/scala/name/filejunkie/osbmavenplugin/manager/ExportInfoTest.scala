package name.filejunkie.osbmavenplugin.manager

import java.io.File
import org.specs2.mutable._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import name.filejunkie.osbmavenplugin.osbentities.OSBEntity

@RunWith(classOf[JUnitRunner])
object ExportInfoTest extends Specification {
  "ExportInfo file" should {
    "not have exported entities lines if empty" in {
      val exportInfo = new ExportInfo(new File("dummy"))

      exportInfo.exportInfoText.replaceAll("\\s","") must be matching ".*<\\/imp:properties><\\/xml-fragment>.*"
    }

    "have entities lines written" in {
      val exportInfo = new ExportInfo(new File ("dummy"))
      val osbEntity = new OSBEntity("") {
        override val representationVersion: String = ""
        override protected def outputFilePath: String = {""}
        override protected def outputFileDir: String = {""}
        override def content: String = ""
        override val entitiesFolder: Option[String] = None
        override val dataClass: String = ""
        override val typeId: String = ""
      }

      exportInfo.addOSBEntity(osbEntity)
      exportInfo.exportInfoText.replaceAll("\\s","") must be matching ".*<\\/imp:properties><imp:exportedItemInfoinstanceId=\"/\"typeId=\"\"xmlns:imp=\"http://www.bea.com/wli/config/importexport\"><imp:properties><imp:propertyname=\"representationversion\"value=\"\"/><imp:propertyname=\"dataclass\"value=\"\"/><imp:propertyname=\"isencrypted\"value=\"false\"/><imp:propertyname=\"jarentryname\"value=\"/.null\"/></imp:properties></imp:exportedItemInfo><\\/xml-fragment>.*"
    }
  }
}
