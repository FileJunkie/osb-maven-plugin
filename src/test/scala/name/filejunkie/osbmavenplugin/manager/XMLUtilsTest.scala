package name.filejunkie.osbmavenplugin.manager

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import name.filejunkie.osbmavenplugin.utils.XMLUtils

@RunWith(classOf[JUnitRunner])
object XMLUtilsTest extends Specification {
  val sampleXsd = """<?xml version="1.0" encoding="utf-8"?>
                            |<xs:schema elementFormDefault="qualified" xmlns:xs="http://www.w3.org/2001/XMLSchema" targetNamespace="urn:name:filejunkie:domain">
                            |  <xs:import namespace='testNamespace' schemaLocation='../test.xsd' />
                            |  <xs:element name="Address">
                            |    <xs:complexType>
                            |      <xs:sequence>
                            |        <xs:element name="Recipient" type="xs:string" />
                            |        <xs:element name="House" type="xs:string" />
                            |        <xs:element name="Street" type="xs:string" />
                            |        <xs:element name="Town" type="xs:string" />
                            |        <xs:element name="County" type="xs:string" minOccurs="0" />
                            |        <xs:element name="PostCode" type="xs:string" />
                            |        <xs:element name="Country" minOccurs="0">
                            |          <xs:simpleType>
                            |            <xs:restriction base="xs:string">
                            |              <xs:enumeration value="IN" />
                            |              <xs:enumeration value="DE" />
                            |              <xs:enumeration value="ES" />
                            |              <xs:enumeration value="UK" />
                            |              <xs:enumeration value="US" />
                            |            </xs:restriction>
                            |          </xs:simpleType>
                            |        </xs:element>
                            |      </xs:sequence>
                            |    </xs:complexType>
                            |  </xs:element>
                            |</xs:schema>""".stripMargin

  val sampleXsdNoDeps = sampleXsd.replace("<xs:import namespace='testNamespace' schemaLocation='../test.xsd' />", "")

  private val sampleWsdl = """<?xml version="1.0"?>
                             |<wsdl:definitions xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
                             |  xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
                             |  xmlns:hy="http://www.herongyang.com/Service/"
                             |  targetNamespace="http://www.herongyang.com/Service/">
                             |
                             |  <wsdl:documentation>
                             |    Hello_WSDL_11_SOAP.wsdl
                             |    Copyright (c) 2007 by Dr. Herong Yang, herongyang.com
                             |    All rights reserved
                             |  </wsdl:documentation>
                             |
                             |  <wsdl:types>
                             |    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema"
                             |      targetNamespace="http://www.herongyang.com/Service/">
                             |      <xsd:element name="HelloRequest" type="xsd:string"/>
                             |      <xsd:element name="HelloResponse" type="xsd:string"/>
                             |    </xsd:schema>
                             |    <xsd:schema xmlns:xsd="http://www.w3.org/2001/XMLSchema">
                             |      <xsd:import schemaLocation="../imported.xsd" namespace="urn:name.filejunkie.imported" />
                             |    </xsd:schema>
                             |
                             |  </wsdl:types>
                             |
                             |  <wsdl:message name="helloInputMessage">
                             |    <wsdl:part name="helloInputPart" element="hy:HelloRequest"/>
                             |  </wsdl:message>
                             |  <wsdl:message name="helloOutputMessage">
                             |    <wsdl:part name="helloOutputPart" element="hy:HelloResponse"/>
                             |  </wsdl:message>
                             |
                             |  <wsdl:portType name="helloPortType">
                             |    <wsdl:operation name="Hello">
                             |      <wsdl:input name="helloInput"
                             |        message="hy:helloInputMessage"/>
                             |      <wsdl:output name="helloOutput"
                             |        message="hy:helloOutputMessage"/>
                             |    </wsdl:operation>
                             |  </wsdl:portType>
                             |
                             |  <wsdl:binding name="helloBinding" type="hy:helloPortType">
                             |    <soap:binding transport="http://schemas.xmlsoap.org/soap/http"/>
                             |    <wsdl:operation name="Hello">
                             |      <soap:operation
                             |        soapAction="http://www.herongyang.com/Service/Hello"/>
                             |      <wsdl:input name="helloInput">
                             |        <soap:body use="literal"/>
                             |      </wsdl:input>
                             |      <wsdl:output name="helloOutput">
                             |        <soap:body use="literal"/>
                             |      </wsdl:output>
                             |    </wsdl:operation>
                             |  </wsdl:binding>
                             |
                             |  <wsdl:service name="helloService">
                             |    <wsdl:port name="helloPort" binding="hy:helloBinding">
                             |      <soap:address
                             |location="http://www.herongyang.com/Service/Hello_SOAP_11.php"/>
                             |    </wsdl:port>
                             |  </wsdl:service>
                             |
                             |</wsdl:definitions>""".stripMargin

  val sampleWsdlNoDeps = sampleWsdl.replace("<xsd:import schemaLocation=\"../imported.xsd\" namespace=\"urn:name.filejunkie.imported\" />","")

  "XMLUtils object" should {
    "extract namespace from xsd" in {
      XMLUtils.getNamespace(sampleXsd) must_== "urn:name:filejunkie:domain"
    }

    "extract deps from xsd" in {
      val dep = XMLUtils.getDependencies(sampleXsd).head

      dep must_== "../test.xsd"
    }

    "extract namespace from wsdl" in {
      XMLUtils.getNamespace(sampleWsdl) must_== "http://www.herongyang.com/Service/"
    }

    "extract deps from wsdl" in {
      val dep = XMLUtils.getDependencies(sampleWsdl).head

      dep must_== "../imported.xsd"
    }

    "extract port from wsdl" in {
      XMLUtils.getPort(sampleWsdl) must_== "helloPort"
    }
  }
}
