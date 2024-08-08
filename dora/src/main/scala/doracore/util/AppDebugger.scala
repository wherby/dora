/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.util

import akka.event.slf4j.Logger

/** Copyright (c) 2020
  * For  in doradilla
  * Created by where on 2020/8/29
  */
object AppDebugger {
  def log(str: String, domain: Option[String]) = {
    val logger = Logger.apply(getClass.getName)
    logger.info(s"$domain >>>>")
    logger.info(str)
    logger.info(s"$domain <<<<")
  }
}
