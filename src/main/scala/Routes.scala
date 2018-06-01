import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.util.control.NonFatal

//the usual config code required for an Akka Http server, mixing in our JsonHelper trait too
object Routes extends App with JsonHelper with Students {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher
  case class Error(message: String)
//  val studentsClass = new Students

 lazy val route =
    pathPrefix("demo") {
      get {
        path("fetchAllStudents") {
          complete {
            val addedStudents = fetchStudents
            val jsonResponse = write(addedStudents)

            /* json for list of Students
            [{"rollNum":1,"name":"Ayush"},{"rollNum":2,"name":"deepankar"}]
             */
            HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, jsonResponse))
          }
        }
      } ~
        post {
          path("addStudent") {
            entity(as[String]) { studentJson => {
              complete {
                val studentRecord = parse(studentJson).extract[Student]

                /*
                Student(3,randhir)
                 */
                val updatedList = addStudent(studentRecord)
                val jsonResponse = write(updatedList)
                HttpResponse(entity = HttpEntity(ContentTypes.`application/json`, jsonResponse))
              }
            }
            }
          }
        } ~
        put {

          //for updating student name student roll number has to be same as entered previously
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
