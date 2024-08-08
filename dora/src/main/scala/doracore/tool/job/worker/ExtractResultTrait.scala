/*
 * Copyright (C) 2024 [Tao Zhou](187225577@qq.com). - All Rights Reserved
 *
 * Unauthorized copying or redistribution of this file in source and binary forms via any medium
 * is strictly prohibited.
 */

package doracore.tool.job.worker

import doracore.core.msg.Job.{JobResult, JobStatus}

import scala.util.{Failure, Success, Try}

trait ExtractResultTrait {
  def extractJobResult(executeResultEither: Try[Any]): JobResult = {
    executeResultEither match {
      case Success(executeResult) => JobResult(JobStatus.Finished, executeResult)
      case Failure(value)         => JobResult(JobStatus.Failed, value)
    }
  }
}
