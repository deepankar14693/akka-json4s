import Routes.route
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{Matchers, WordSpec}
import org.mockito.Mockito
import org.scalatest.mockito.MockitoSugar

import scala.collection.mutable.ListBuffer

class RoutesTest extends WordSpec with Matchers with ScalatestRouteTest
  with MockitoSugar with JsonHelper{

  val studentRecord: Students = mock[Students]
  val studentsList = ListBuffer(Student(1,"Ayush"),
    Student(2,"deepankar"))
  val addedStudentsList = ListBuffer(Student(3,"randhir"),
    Student(1,"Ayush"),
    Student(2,"deepankar"))
  val deletedStudentsList = ListBuffer(Student(2,"deepankar"))
  val updatedStudentsList = ListBuffer(Student(1,"ankit"),
    Student(2,"deepankar"))


  "Routing API" should {

    "fetch all students" in {

      Mockito.when(studentRecord.fetchStudents).thenReturn(studentsList)
      val result = """[{"rollNum":1,"name":"Ayush"},{"rollNum":2,"name":"deepankar"}]"""
      Get("/demo/fetchAllStudents") ~> route ~> check {

        assert(responseAs[String].equals(result))
      }
    }

    "add student record to list" in {
      val jsonObject =
        """{
          "rollNum":3,
          "name":"randhir"
          }"""
      val student = Student(3,"randhir")
      Mockito.when(studentRecord.addStudent(student)).thenReturn(addedStudentsList)
      val result = """[{"rollNum":3,"name":"randhir"},{"rollNum":1,"name":"Ayush"},{"rollNum":2,"name":"deepankar"}]"""
      Post("/demo/addStudent",jsonObject) ~> route ~> check {
      println(result)
        println(responseAs[String])
        assert(responseAs[String].equals(result))
      }
    }

    "update student record to list" in {
      val jsonObject =
        """{
          "rollNum":1,
          "name":"Ayush"
          }"""
      val student = Student(1,"Ayush")
      Mockito.when(studentRecord.updateStudent(student)).thenReturn(updatedStudentsList)
      val result = """[{"rollNum":1,"name":"ankit"},{"rollNum":2,"name":"deepankar"}]"""
      Post("/demo/updateStudent",jsonObject) ~> route ~> check {

        println(result)
        println(responseAs[String])
        assert(responseAs[String].equals(result))
      }
    }

    "delete student record from list" in {
      val jsonObject =
        """{
          "rollNum":1,
          "name":"Ayush"
          }"""
      val student = Student(3,"ranbir")
      Mockito.when(studentRecord.deleteStudent(student.rollNum)).thenReturn(updatedStudentsList)
      val result = """[{"rollNum":2,"name":"deepankar"}]"""
      Post("/demo/deleteStudent",jsonObject) ~> route ~> check {
        println(result)
        println(responseAs[String])
        assert(responseAs[String].equals(result))
      }
    }

  }
}
