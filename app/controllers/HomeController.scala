package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index())
  }

  // GET
  def receive = Action { implicit request =>
    Ok("received")
  }

  //using json
  def json = Action { _ =>
    Ok(Json.obj("yes" -> true))
  }

  //using parameter
  def nameParam(name: String) = Action { _ =>
    Ok(Json.obj("name" -> name))
  }

  // POST
  def posted = Action(parse.json) { implicit request =>
    Ok(Json.toJson(request.body))
  }

  // GET name in JSON
  def sayHello = Action { request =>
    request.body.asJson
      .map { json =>
        (json \ "name")
          .asOpt[String]
          .map { name =>
            Ok("Hello " + name)
          }
          .getOrElse {
            BadRequest("Missing parameter [name]")
          }
      }
      .getOrElse {
        BadRequest("Expecting Json data")
      }
  }

  // Different way of doing sayHello

  // def sayHello = Action(parse.json) { request =>
  //   (request.body \ "name")
  //     .asOpt[String]
  //     .map { name =>
  //       Ok("Hello " + name)
  //     }
  //     .getOrElse {
  //       BadRequest("Missing parameter [name]")
  //     }
  // }

  // def sayHello = Action(parse.json) { request =>
  //   (request.body \ "name")
  //     .asOpt[String]
  //     .map { name =>
  //       Ok(
  //         toJson(
  //           Map("status" -> "OK", "message" -> ("Hello " + name))
  //         )
  //       )
  //     }
  //     .getOrElse {
  //       BadRequest(
  //         toJson(
  //           Map("status" -> "KO", "message" -> "Missing parameter [name]")
  //         )
  //       )
  //     }
  // }
}
