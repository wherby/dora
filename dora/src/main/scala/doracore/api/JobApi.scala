/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.api

import akka.actor.ActorSystem

/** For doradilla.api in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/13
  */
class JobApi(systemOpt: Option[ActorSystem] = None)
    extends SystemApi(systemOpt: Option[ActorSystem])
    with DriverApi
    with CommandTranApi
    with ProcessTranApi {}
