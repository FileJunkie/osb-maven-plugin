package name.filejunkie.osbmavenplugin.manager

import java.io.File
import org.specs2.mutable._
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
object ExportInfoTest extends Specification {
  "ExportInfo file" should {
    "not have exported entities lines if empty" in {
      val exportInfo = new ExportInfo(new File("dummy"))

      exportInfo.exportInfoText.replaceAll("\\s","") must be matching ".*<\\/imp:properties><\\/xml-fragment>.*"
    }
  }
}
