import scala.collection.mutable.ListBuffer

case class Student(rollNum: Int, name: String)

trait Students {

  val students = ListBuffer(Student(1, "Ayush"), Student(2, "deepankar"))

  def fetchStudents: ListBuffer[Student] = students

  def addStudent(student: Student): ListBuffer[Student] = {
    student +: students
  }

  def updateStudent(student: Student): ListBuffer[Student] = {
    val oldRecord = students.filter(_.rollNum == student.rollNum)
    students --= oldRecord
    student +: students
  }

  def deleteStudent(rollNum: Int): ListBuffer[Student] = {
    val deleteRecord = students.filter(_.rollNum == rollNum)
    students --= deleteRecord
  }

}
