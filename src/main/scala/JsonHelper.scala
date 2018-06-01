import org.json4s.{DefaultFormats, Formats, JNothing, JValue}
import org.json4s.native.JsonMethods.{parse => jParser}
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{write => jWrite}

trait JsonHelper extends {

  val EMPTY_STRING = ""


  implicit val serialization: Serialization.type = Serialization

  implicit val formats: Formats = DefaultFormats

  /*
takes any type of value and serializes it to string
eg: val student = Student(1,"deepankar")
     write(student) will serialize it to {"rollNum":1,"name":"deepankar"}
 */
  def write[T <: AnyRef](value: T): String = jWrite(value)

  /*
  Any valid json can be parsed into internal AST(Abstract Syntax Tree) format.
  eg : If this is our json string coming from a post request
       val jsonString = {"rollNum":1,"name":"deepankar"}
       parse(jsonString) will parse it to something like
       JObject(List((rollNum,JInt(1)), (name,JString(deepankar))))
   */
  protected def parse(value: String): JValue = jParser(value)

  /*
  extract method is used after parsing the json string, as parse method returns JValue so we
  can map this JValue to our target scala data model
  eg :  val jsonString = {"rollNum":1,"name":"deepankar"}
       val parseString = parse(jsonString)
       extract(parseString) will return Student(?1,"deepankar")
   */

  implicit protected def extractOrEmptyString(json: JValue): String = {
    json match {
      case JNothing => EMPTY_STRING
      case data => data.extract[String]
    }
  }

}
