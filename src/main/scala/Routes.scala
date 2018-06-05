import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.concurrent.ExecutionContextExecutor

object Routes extends App with JsonHelper with Students {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  case class Error(message: String)

 lazy val route =
    pathPrefix("demo") {
      get {
        path("fetchAllStudents") {
          complete {
            val addedStudents = fetchStudents
            val jsonResponse = write(addedStudents)

            HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, jsonResponse))
          }
        }
      } ~
        post {
          path("addStudent") {
            entity(as[String]) { studentJson => {
              complete {
                val studentRecord = parse(studentJson).extract[Student]
                val updatedList = addStudent(studentRecord)
                val jsonResponse = write(updatedList)
                HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, jsonResponse))
              }
            }
            }
          }
        } ~
        put {
          path("updateStudent") {
            entity(as[String]) { studentJson => {
              complete {
                val studentRecord = parse(studentJson).extract[Student]
                val updatedList = updateStudent(studentRecord)
                val jsonResponse = write(updatedList)
                HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, jsonResponse))
              }
            }
            }
          }
        } ~
        delete {
          path("deleteStudent" / IntNumber) { rollNum =>
            complete {
              val deleteRecord = deleteStudent(rollNum)
              val jsonResponse = write(deleteRecord)
              HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, jsonResponse))
            }
          }
        }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  println(s"Server online at http://localhost:8080")
}
