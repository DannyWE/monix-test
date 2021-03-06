import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future

// Needed below
import scala.concurrent.Await
import scala.concurrent.duration._
import monix.reactive._


object ObservableTest extends App {
  import monix.eval._

  // A specification for evaluating a sum,
  // nothing gets triggered at this point!
  val task = Task { println("this is task") }.runAsync
  val future = Future { println("this is future") }


  val tick = {
    Observable.interval(1.second)
      // common filtering and mapping
      .filter(_ % 2 == 0)
      .map(_ * 2)
      // any respectable Scala type has flatMap, w00t!
      .flatMap(x => Observable.fromIterable(Seq(x,x)))
      // only take the first 5 elements, then stop
      .take(5)
      // to print the generated events to console
      .dump("Out")
  }

  tick.subscribe()



  Thread.sleep(5000)
}
