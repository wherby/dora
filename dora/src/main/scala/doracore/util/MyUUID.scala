/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.util
object MyUUID {
  def getUUIDString() = {
    import java.util.UUID
    UUID.randomUUID.toString
  }
}
