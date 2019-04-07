package vars

import doradilla.core.msg.Job.JobMsg
import jobs.fib.FibnacciTranActor.FibRequest
import jobs.process.ProcessTranActor.ProcessRequest
import play.api.libs.json.Json

/**
  * For `var` in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/30
  */
object ConstVar {
  val fibTask = JobMsg("fibreq", Json.toJson(FibRequest(10)).toString)
  def fibTaskN(n:Int) = JobMsg("fibreq", Json.toJson(FibRequest(n)).toString)
  val processJob = JobMsg("SimpleProcess",Json.toJson( ProcessRequest(List("ping 127.0.0.1 -n 1"))).toString)
}
