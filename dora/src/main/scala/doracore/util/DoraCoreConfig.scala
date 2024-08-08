/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.util

import com.typesafe.config.{Config, ConfigFactory}

/** For doradilla.util in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/6/11
  */
object DoraCoreConfig {
  def getConfig(): Config = {
    val config = ConfigFactory.load()
    ConfigFactory.load("doradilla").withFallback(config).resolve()
  }
}
