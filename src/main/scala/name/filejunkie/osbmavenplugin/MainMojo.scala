package name.filejunkie.osbmavenplugin

import org.apache.maven.plugins.annotations.Mojo
import org.apache.maven.plugin.AbstractMojo


@Mojo( name = "generate")
class MainMojo extends AbstractMojo {

  override def execute(): Unit = {
    getLog().info("Hello, world.")
  }
}
