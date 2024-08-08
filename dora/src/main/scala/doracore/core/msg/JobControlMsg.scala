/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.core.msg

/** For doradilla.core.msg in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/21
  */
object JobControlMsg {

  sealed trait ControlMsg

  final case class ResetFsm() extends ControlMsg

}
