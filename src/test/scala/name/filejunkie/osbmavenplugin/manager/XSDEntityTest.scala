package name.filejunkie.osbmavenplugin.manager

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import name.filejunkie.osbmavenplugin.osbentities.XSD
import name.filejunkie.osbmavenplugin.osbentities.OSBEntity

@RunWith(classOf[JUnitRunner])
object XSDEntityTest extends Specification{
  OSBEntity.osbProjectName = Some("Big Project")
  OSBEntity.thisProjectName = "Subproject"
  OSBEntity.outputFolder = "buildDir/osb"

  "XSD entity" should {

    "have correct path" in {
      val xsd = new XSD("Domain", Some("Domain_1"))
      xsd.path must_== "Big Project/Subproject/Resources/Domain_1/Domain"
    }

    "not be transformed if has no deps" in {
      val xsd = new XSD("Domain", Some("Domain_1"))
      xsd.fileContent = Some(XMLUtilsTest.sampleXsdNoDeps)
      xsd.content must_==
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<con:schemaEntry xmlns:con=\"http://www.bea.com/wli/sb/resources/config\">\n    <con:schema><![CDATA[" +
        XMLUtilsTest.sampleXsdNoDeps +
        "]]></con:schema>\n" +
        "    <con:targetNamespace>" +
        "urn:name:filejunkie:domain" +
         "</con:targetNamespace>\n</con:schemaEntry>"
    }
  }
}
