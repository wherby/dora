package jobs.fib

import akka.actor.Props
import akka.testkit.TestProbe
import doradilla.ActorTestClass
import jobs.fib.FibnacciTranActor.{FibAdd, FibInit, FibRequest, FibResult}
import play.api.libs.json.Json

/**
  * For jobs.fib in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/30
  */
class FibWorkActorSpec extends  ActorTestClass  {
  "FibWorkActor receive message " must{
    val proxy = TestProbe()

    "Receive a fib task for 10 should return result " in{
      val fibTranActor = system.actorOf(Props(new FibWorkActor(Json.toJson( FibRequest(10)).toString)), "FibnacciWorker")
      fibTranActor.tell(FibInit(FibAdd(1,1,0),proxy.ref),proxy.ref)
      proxy.expectMsgPF(){
        case fibResult: FibResult=>fibResult.fa should be(55)
          fibResult.a should be(10)
      }
    }
  }
}
