package doradilla.util

import java.util.concurrent.Executors

import doradilla.util.CommandService.ExecuteResult

import scala.concurrent.{ExecutionContext, Future}

/**
  * For doradilla.util in Doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/4/22
  */
object ProcessService {
  def callProcess(processCallMsg: ProcessCallMsg) = {
    //Call reflection will takes time
    // get runtime universe
    val ru = scala.reflect.runtime.universe
    // get runtime mirror
    val rm = ru.runtimeMirror(getClass.getClassLoader)
    //val instanceMirror = rm.reflectClass(Class.)
    try {
      val a = Class.forName(processCallMsg.clazzName)
      val instance = a.newInstance()
      val methodOpt = a.getMethods.filter(method => method.getName == processCallMsg.methodName).headOption
      methodOpt match {
        case Some(method) => Right(method.invoke(instance, processCallMsg.paras: _*))
        case _ => Left("No such method")
      }
    } catch {
      case e: Throwable => println(e)
        Left("Remote call failed")
    }
  }

  def callProcessResult(processCallMsg: ProcessCallMsg)(implicit executor: ExecutionContext = scala.concurrent.ExecutionContext.Implicits.global): Future[ExecuteResult] = {
    Future{callProcessResultSync(processCallMsg)}(executor)
  }

  private def callProcessResultSync(processCallMsg: ProcessCallMsg): ExecuteResult = {
    callProcess(processCallMsg) match {
      case Right(x) => try {
        x.asInstanceOf[ExecuteResult]
      } catch {
        case _: Throwable => ExecuteResult(-1, "", s"Return value is not in right format: $x")
      }
      case Left(y) => ExecuteResult(-1, "", y.toString)
    }
  }
  case class ProcessCallMsg(clazzName: String, methodName: String, paras: Array[AnyRef])
}