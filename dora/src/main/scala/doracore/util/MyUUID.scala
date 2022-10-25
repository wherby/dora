package doracore.util
object MyUUID {
  def getUUIDString() = {
    import java.util.UUID
    UUID.randomUUID.toString
  }
}
