package doracore

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.wordspec.AnyWordSpecLike
import org.scalatest.{BeforeAndAfterAll}
import org.scalatest.matchers.should.Matchers

/**
  * For doradilla in doradilla
  * Created by whereby[Tao Zhou](187225577@qq.com) on 2019/3/24
  *
  * Design for io.github.wherby.doradilla.test actor system
  */
class ActorTestClass (_system: ActorSystem = ActorSystemTest.actorSystemOpt) extends  TestKit(_system) with ImplicitSender
  with AnyWordSpecLike with Matchers with BeforeAndAfterAll {

  override protected def beforeAll(): Unit = {
    //ActorSystemTest.getActorSystem()
  }
  override def afterAll: Unit = {
    Thread.sleep(2000)
    ActorSystemTest.shutdowmSystem()
  }
}
