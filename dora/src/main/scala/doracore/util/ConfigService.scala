/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.util

import com.typesafe.config.{Config, ConfigException}

/** For doradilla.util in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/9
  */
object ConfigService {
  def getStringOpt(config: Config, path: String): Option[String] = {
    try {
      Some(config.getString(path))
    } catch {
      case e: ConfigException =>
        None
    }
  }

  def getIntOpt(config: Config, path: String): Option[Int] = {
    getStringOpt(config, path) match {
      case Some(str) =>
        try {
          Some(str.toInt)
        } catch {
          case e: Throwable =>
            None
        }
      case _ => None
    }
  }

  def getConfigOpt(config: Config, path: String): Option[Config] = {
    try {
      Some(config.getConfig(path))
    } catch {
      case e: ConfigException =>
        None
    }
  }
}
