import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

class StudentsTest extends FunSuite with Students {

  test("fetch students should list all students") {
    val actualResult = fetchStudents
    val expectedResult = ListBuffer(Student(1, "Ayush"), Student(2, "deepankar"))
    assert(actualResult === expectedResult)
  }

  test("add student should add to list") {
    val student = Student(3,"randhir")
    val actualResult = addStudent(student)
    val expectedResult = ListBuffer(Student(3,"randhir"), Student(1, "Ayush"), Student(2, "deepankar"))
    assert(actualResult === expectedResult)
  }

  test("update student should update to list") {
    val student = Student(2,"deepak")
    val actualResult = updateStudent(student)
    val expectedResult = ListBuffer(Student(2, "deepak"), Student(1, "Ayush"))
    assert(actualResult === expectedResult)
  }

  test("delete student should delete from list") {
    val actualResult = deleteStudent(1)
    val expectedResult = ListBuffer(Student(2, "deepankar"))
    assert(actualResult === expectedResult)
  }

}
