package name.filejunkie.osbmavenplugin.osbentities

import java.io.File

abstract class OSBEntity(osbEntityName: String) {
  def writeFile: Unit
  def exportInfoLine: String
}

object OSBEntity {
  var inputFolder: File = new File("")
  var outputFolder: File = new File("")
  var osbProjectName: String = ""
  var thisProjectName: String = ""
}