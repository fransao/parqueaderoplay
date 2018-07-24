package controllers

  import javax.inject._
  import modelo.ParqueoVehiculo
  import play.api.libs.json._
  import play.api.libs.functional.syntax._
  //import play.api.Logger
  import play.api.mvc._

  @Singleton
  class JsonParqueaderoController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder) extends AbstractController(cc) {

    def vehiculosparqueados = Action {
      val json = Json.toJson(ParqueoVehiculo.list)
      Ok(json)
    }

    def ingresovehiculo() = Action(parse.json) { request =>
      val parqueoResult = request.body.validate[ParqueoVehiculo]
      parqueoResult.fold(
        errors => {
          BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        parqueoVehiculo => {
          ParqueoVehiculo.save(parqueoVehiculo)
          Ok(Json.obj("status" ->"OK", "message" -> ("Placa '"+parqueoVehiculo.placa+"' saved.") ))
        }
      )
    }

    def ingresovehiculo4 = Action(parse.json) { request =>
      val parqueoResult = request.body.validate[ParqueoVehiculo]
      parqueoResult.fold(
        errors => {
          BadRequest(Json.obj("status" ->"KO", "message" -> JsError.toJson(errors)))
        },
        parqueoVehiculo => {
          ParqueoVehiculo.save(parqueoVehiculo)
          Ok(Json.obj("status" ->"OK", "message" -> ("Placa '"+parqueoVehiculo.placa+"' saved.") ))
        }
      )
    }

    def ingresovehiculo2 = Action {
        //Results.Ok
      Ok(views.html.index(s"Test Json..." ))
    }

    def salidavehiculo(placa:String) = Action {
      //Results.Ok
      Ok(views.html.index(s"Test Json..." ))
    }

    def getJsValueFromJsObject2() :JsValue = {
      JsObject(Seq(
        "placa" -> JsString("LLW98E"),
        "tipovehiculo" -> JsNumber(1),
        "fechaingreso" -> JsString("2018-07-20"),
        "fechasalida" -> JsString("2018-09-20"),
        "valor" -> JsNumber(10000)

      ))
    }

    def getJsValueFromJson2() :JsValue = {
      Json.obj(
        "placa" -> "LLW98E",
        "tipovehiculo" -> 1,
        "fechaingreso" -> "2018-07-20",
        "fechasalida" -> "2018-09-20",
        "valor" -> 10000
      )
    }


    // -------------------------------
    def index = Action {

      println("get JSValue from JsObject: " + getJsValueFromJsObject)
      println("get JsValue from Json: " + getJsValueFromJson)
      println(writeConverter)


      println(stringUtilidadAndBusqueda)

      //Results.Ok
      Ok(views.html.index(s"Test Json..." ))

    }

    def getJsValueFromJsObject() :JsValue = {
      JsObject(Seq(
        "name" -> JsString("Watership Down"),
        "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
        "residents" -> JsArray(IndexedSeq(
          JsObject(Seq(
            "name" -> JsString("Fiver"),
            "age" -> JsNumber(4),
            "role" -> JsNull
          )),
          JsObject(Seq(
            "name" -> JsString("Bigwig"),
            "age" -> JsNumber(6),
            "role" -> JsString("Owsla")
          ))
        ))
      ))
    }

    def getJsValueFromJson () :JsValue = {
      Json.obj(
        "name" -> "Watership Down",
        "location" -> Json.obj("lat" -> 51.235685, "long" -> -1.309197),
        "residents" -> Json.arr(
          Json.obj(
            "name" -> "Fiver",
            "age" -> 4,
            "role" -> JsNull
          ),
          Json.obj(
            "name" -> "Bigwig",
            "age" -> 6,
            "role" -> "Owsla"
          )
        )
      )
    }

    def writeConverter () :Unit = {
      // basic types
      val jsonString = Json.toJson("Fiver")
      println("Json to String (infiere el tipo): " + jsonString)
      val jsonNumber = Json.toJson(4)
      println("Json to Number: " + jsonNumber)
      val jsonBoolean = Json.toJson(false)
      println("Json to Boolean: " + jsonBoolean)

      // collections of basic types
      val jsonArrayOfInts = Json.toJson(Seq(1, 2, 3, 4))
      println(" Json to Array of int: " + jsonArrayOfInts)
      val jsonArrayOfStrings = Json.toJson(List("Fiver", "Bigwig"))
      println("Json to array of String: " + jsonArrayOfStrings)

    }

    def stringUtilidadAndBusqueda () : Unit = {

      case class Location(lat: Double, long: Double)
      case class Resident(name: String, age: Int, role: Option[String])
      case class Place(name: String, location: Location, residents: Seq[Resident])

      implicit val locationWrites = new Writes[Location] {
        def writes(location: Location) = Json.obj(
          "lat" -> location.lat,
          "long" -> location.long
        )
      }

      implicit val residentWrites = new Writes[Resident] {
        def writes(resident: Resident) = Json.obj(
          "name" -> resident.name,
          "age" -> resident.age,
          "role" -> resident.role
        )
      }

      implicit val placeWrites = new Writes[Place] {
        def writes(place: Place) = Json.obj(
          "name" -> place.name,
          "location" -> place.location,
          "residents" -> place.residents
        )
      }

      val place = Place(
        "Watership Down",
        Location(51.235685, -1.309197),
        Seq(
          Resident("Fiver", 4, None),
          Resident("Bigwig", 6, Some("Owsla"))
        )
      )
      val json = Json.toJson(place)

      val minifiedString: String = Json.stringify(json)
      /* imprime:
      {"name":"Watership Down",
        "location":{"lat":51.235685,"long":-1.309197},
        "residents":[{"name":"Fiver","age":4,"role":null},{"name":"Bigwig","age":6,"role":"Owsla"}]} */

      println("Convierte un JavaScript value to a JSON string: " + minifiedString)

      val readableString: String = Json.prettyPrint(json)
      /* imprime:
      {
        "name" : "Watership Down",
        "location" : {
          "lat" : 51.235685,
          "long" : -1.309197
        },
        "residents" : [ {
        "name" : "Fiver",
        "age" : 4,
        "role" : null
      }, {
        "name" : "Bigwig",
        "age" : 6,
        "role" : "Owsla"
      } ]
      }*/
      println("Pretty print: " + readableString)

      val name = json("name")
      println("Imprimir un elemento del json: " + name)
      // returns JsString("Watership Down")

      val bigwig2 = json("residents")(1)
      println("Imprime un elemento en una posicion dada")
      // returns {"name":"Bigwig","age":6,"role":"Owsla"}

      validateJson(json)
      convertirJsValueToModelo(json)
    }

    def  validateJson (json:JsValue): Unit = {
      val nameResult: JsResult[String] = (json \ "name").validate[String]

      // Pattern matching
      nameResult match {
        case s: JsSuccess[String] => println("validate Json Success: Name: " + s.get)
        case e: JsError => println("Validate Json Errors: " + JsError.toJson(e).toString())
      }

      // Fallback value
      val nameOrFallback = nameResult.getOrElse("Undefined")

      // map
      val nameUpperResult: JsResult[String] = nameResult.map(_.toUpperCase())

      // fold
      val nameOption: Option[String] = nameResult.fold(
        invalid = {
          fieldErrors =>
            fieldErrors.foreach(x => {
              println("field: " + x._1 + ", errors: " + x._2)
            })
            None
        },
        valid = {
          name => Some(name)
        }
      )
    }

    def convertirJsValueToModelo(json:JsValue) : Unit = {
      case class Location(lat: Double, long: Double)
      case class Resident(name: String, age: Int, role: Option[String])
      case class Place(name: String, location: Location, residents: Seq[Resident])

      implicit val locationReads: Reads[Location] = (
        (JsPath \ "lat").read[Double] and
          (JsPath \ "long").read[Double]
        )(Location.apply _)

      implicit val residentReads: Reads[Resident] = (
        (JsPath \ "name").read[String] and
          (JsPath \ "age").read[Int] and
          (JsPath \ "role").readNullable[String]
        )(Resident.apply _)

      implicit val placeReads: Reads[Place] = (
        (JsPath \ "name").read[String] and
          (JsPath \ "location").read[Location] and
          (JsPath \ "residents").read[Seq[Resident]]
        )(Place.apply _)


      val placeResult: JsResult[Place] = json.validate[Place]
      println("De jsvalue a Modelo: " + placeResult)
      //JsSuccess(Place(Watership Down,Location(51.235685,-1.309197),List(Resident(Fiver,4,None), Resident(Bigwig,6,Some(Owsla)))),)

      val residentResult: JsResult[Resident] = (json \ "residents")(1).validate[Resident]
      println("De jsvalue a Modelo: " + residentResult)
      // JsSuccess(Resident(Bigwig,6,Some(Owsla)),)

    }



  }
