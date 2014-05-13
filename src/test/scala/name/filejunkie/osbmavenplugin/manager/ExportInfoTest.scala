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
      val osbEntity = new OSBEntity {
        override def writeFile: Unit = {}
        override def exportInfoLine: String = "dummyOSBEntity"
      }

      exportInfo.addOSBEntity(osbEntity)

      exportInfo.exportInfoText.replaceAll("\\s","") must be matching ".*<\\/imp:properties>dummyOSBEntity<\\/xml-fragment>.*"
    }
  }
}
