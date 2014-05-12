package name.filejunkie.osbmavenplugin.osbentities

abstract class OSBEntity {
  def writeFile: Unit
  def exportInfoLine: String
}
