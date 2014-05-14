package name.filejunkie.osbmavenplugin.manager

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import name.filejunkie.osbmavenplugin.osbentities.BuildNumber

@RunWith(classOf[JUnitRunner])
object BuildNumberTest extends Specification {
  "Build Number file" should {
    "print passed build number" in {
      val buildNumber = new BuildNumber("buildNumber", Some("1"))

      buildNumber.content.replaceAll("\\s", "") must be matching ".*buildNumber>1&lt;\\/buildNumber.*"
    }

    "default to LOCAL_BUILD" in {
      val buildNumber = new BuildNumber("buildNumber", None)

      buildNumber.content.replaceAll("\\s", "") must be matching ".*buildNumber>LOCAL_BUILD&lt;\\/buildNumber.*"
    }
  }
}
