package controllers

import models.CostItem

import play.api.mvc._
import repositories.CostItemRepository
import play.api.libs.json._
import reactivemongo.bson.BSONObjectID
import javax.inject.Inject
import scala.concurrent.{ExecutionContext}

/**
 * Controller for CostItem
 * This controller creates an `Action` to handle HTTP requests to the
 * Cost Manager Application
 *
 * @param ec
 * @param cc
 * @param costItemRepository
 */
class CostItemController @Inject()(
                                    implicit ec: ExecutionContext,
                                    cc: ControllerComponents,
                                    costItemRepository: CostItemRepository
                                  ) extends AbstractController(cc) {

  /**
   * retrieve all the cost items from the repository and show them
   */
  def allCosts = Action.async {
    costItemRepository.list().map {
      costs => Ok(views.html.allcostitems(costs))
    }
  }

  /**
   * create a new cost item from the inserted form data
   */
  def createCostItem = Action { request =>

    // parse the form data from the request
    val postVals = request.body.asFormUrlEncoded
    postVals.map { args =>
      val name = args("name").head
      val price = args("price").head
      val category = args("category").head
      val date = args("date").head
      val description = args("description").head

      // create CostItem object
      val costItem = CostItem(
        name,
        price.toDouble,
        category,
        date,
        description)

      // save the cost item to the db
      costItemRepository.create(costItem)
      // show good message to user
      Redirect(routes.CostItemController.allCosts)
    }.getOrElse(BadRequest("Error Adding cost"))

  }

  /**
   * find existing cost item by id
   *
   * @param id
   */
  def getCostItem(id: BSONObjectID) = Action.async {

    // retrieve the cost from the db
    costItemRepository.read(id).map { maybeItem =>
      maybeItem.map {
        costItem => Ok(Json.toJson(costItem))
      }.getOrElse(NotFound)
    }
  }

  /**
   * delete existing cost item by id
   *
   * @param id
   */
  def deleteCostItem(id: BSONObjectID) = Action.async {

    // delete the cost from the db
    costItemRepository.delete(id).map {
      case Some(item) => Ok(Json.toJson(item))
      case _ => NotFound
    }

  }

  /**
   * show report by month
   *
   * @return
   */
  def report = Action.async {
    costItemRepository.list().map {
      costs => Ok(Json.toJson(costs))
    }
  }

}
