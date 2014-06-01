package name.filejunkie.osbmavenplugin.manager

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import name.filejunkie.osbmavenplugin.osbentities.WSDL

@RunWith(classOf[JUnitRunner])
object WSDLEntityTest extends Specification {
  "WSDL entity" should {
    "have correct path" in {
      val wsdl = new WSDL("Domain", Some("Domain_1"))
      wsdl.path must_== "Big Project/Subproject/Resources/Domain_1/Domain"
    }

    "not be transformed if has no deps" in {
      val wsdl = new WSDL("Domain", Some("Domain_1"))
      wsdl.fileContent = Some(XMLUtilsTest.sampleWsdlNoDeps)
      wsdl.content must_==
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<con:wsdlEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n    <con:wsdl><![CDATA[" +
          XMLUtilsTest.sampleWsdlNoDeps +
          "]]></con:wsdl>\n" +
          "    <con:targetNamespace>" +
          "http://www.herongyang.com/Service/" +
          "</con:targetNamespace>\n</con:wsdlEntry>"
    }
  }
}
