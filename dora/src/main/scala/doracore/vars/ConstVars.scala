/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.vars

import scala.concurrent.duration._
import scala.language.postfixOps

/** For doradilla.vars in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/13
  */
object ConstVars {
  val DoraPort    = 1600
  val tickTime    = 100 milliseconds
  val timeout1S   = 1 second
  val longTimeOut = 600 second

  val blockDispatcherName = "blocking-io-dispatcher"
}
