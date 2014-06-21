package name.filejunkie.osbmavenplugin.manager

import name.filejunkie.osbmavenplugin.osbentities.BusinessService
import name.filejunkie.osbmavenplugin.osbentities.WSDL
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
object BusinessServiceEntityTest extends Specification {
  val wsdl = new WSDL("Domain", Some("Domain_1"))
  wsdl.fileContent = Some(XMLUtilsTest.sampleWsdlNoDeps)

  val businessService = new BusinessService("HelloServiceInteraction", Some("HelloServiceInteraction"), "http://tempuri.org", wsdl)

  "BusinessService" should {
    "have correct path" in {
      businessService.path must_== "Big Project/Subproject/Business Services/HelloServiceInteraction/hello-service-interaction"
    }

    "import WSDL correctly" in {
      businessService.content must_==
        """<?xml version="1.0" encoding="UTF-8"?>
          |<xml-fragment>
          |  <ser:coreEntry isProxy="false" isEnabled="true" isAutoPublish="false" xmlns:ser="http://www.bea.com/wli/sb/services">
          |    <ser:description/>
          |    <ser:binding type="SOAP" isSoap12="false" xsi:type="con:SoapBindingType" xmlns:con="http://www.bea.com/wli/sb/services/bindings/config" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          |      <con:wsdl ref="Big Project/Subproject/Resources/Domain_1/Domain"/>
          |      <con:port>
          |        <con:name>helloPort</con:name>
          |        <con:namespace>http://www.herongyang.com/Service/</con:namespace>
          |      </con:port>
          |    </ser:binding>
          |    <ser:monitoring isEnabled="false">
          |      <ser:aggregationInterval>10</ser:aggregationInterval>
          |    </ser:monitoring>
          |    <ser:sla-alerting isEnabled="true">
          |      <ser:alertLevel>normal</ser:alertLevel>
          |    </ser:sla-alerting>
          |    <ser:ws-policy>
          |      <ser:binding-mode>wsdl-policy-attachments</ser:binding-mode>
          |    </ser:ws-policy>
          |  </ser:coreEntry>
          |  <ser:endpointConfig xmlns:ser="http://www.bea.com/wli/sb/services">
          |    <tran:provider-id xmlns:tran="http://www.bea.com/wli/sb/transports">http</tran:provider-id>
          |    <tran:inbound xmlns:tran="http://www.bea.com/wli/sb/transports">false</tran:inbound>
          |    <tran:URI weight="1" xmlns:tran="http://www.bea.com/wli/sb/transports">
          |      <env:value xmlns:env="http://www.bea.com/wli/config/env">http://tempuri.org</env:value>
          |    </tran:URI>
          |    <tran:outbound-properties xmlns:tran="http://www.bea.com/wli/sb/transports">
          |      <tran:load-balancing-algorithm>round-robin</tran:load-balancing-algorithm>
          |      <tran:retry-count>0</tran:retry-count>
          |      <tran:retry-interval>30</tran:retry-interval>
          |      <tran:retry-application-errors>true</tran:retry-application-errors>
          |    </tran:outbound-properties>
          |    <tran:provider-specific xsi:type="http:HttpEndPointConfiguration" xmlns:http="http://www.bea.com/wli/sb/transports/http" xmlns:tran="http://www.bea.com/wli/sb/transports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
          |      <http:outbound-properties>
          |        <http:request-method>POST</http:request-method>
          |        <http:timeout>0</http:timeout>
          |        <http:connection-timeout>0</http:connection-timeout>
          |        <http:follow-redirects>false</http:follow-redirects>
          |        <http:chunked-streaming-mode>false</http:chunked-streaming-mode>
          |      </http:outbound-properties>
          |    </tran:provider-specific>
          |  </ser:endpointConfig>
          |</xml-fragment>""".stripMargin
    }
  }
}
