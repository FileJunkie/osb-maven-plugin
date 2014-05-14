package name.filejunkie.osbmavenplugin

import org.apache.maven.plugins.annotations.{Mojo, Parameter}
import org.apache.maven.plugin.AbstractMojo
import java.io.File
import java.util.Properties
import name.filejunkie.osbmavenplugin.osbentities.OSBEntity


@Mojo( name = "generate")
class MainMojo extends AbstractMojo {

  @Parameter( defaultValue = "${project.build.directory}/wsdl" )
  var inputFolder: File = new File("")

  @Parameter( defaultValue = "${project.build.directory}/osb" )
  var outputFolder: File = new File("")

  @Parameter( defaultValue = "${basedir}/target/${project.artifactId}-${project.version}.jar" )
  var outputFile: File = new File("")

  @Parameter( required =  true )
  var wsdlToURL: Properties = new Properties()

  @Parameter( )
  var osbProjectName: String = ""

  @Parameter( required =  true )
  var thisProjectName: String = ""

  @Parameter( defaultValue = "buildNumber")
  var buildNumberFileName: String = "buildNumber"

  @Parameter( defaultValue = "")
  var replaceSpacesTo: String = ""

  override def execute(): Unit = {
    getLog().info("Hello, world.")

    OSBEntity.inputFolder = inputFolder.getPath
    OSBEntity.outputFolder = outputFolder.getPath
    if(osbProjectName != ""){
      OSBEntity.osbProjectName = Option(osbProjectName)
    }
    OSBEntity.thisProjectName = thisProjectName
  }
}
