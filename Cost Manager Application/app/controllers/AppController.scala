package controllers

import javax.inject._
import play.api.mvc._
import repositories.CostItemRepository

import scala.concurrent.ExecutionContext

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class AppController @Inject()(implicit ec: ExecutionContext,
                              cc: ControllerComponents,
                              postsRepo: CostItemRepository
                             ) extends AbstractController(cc) {

  /**
   * show the home page
   *
   * @return
   */
  def home = Action {
    Ok(views.html.index())
  }

  /**
   * show the add cost page
   *
   * @return
   */
  def addCostItem = Action {
    Ok(views.html.addcostitem())
  }

  /**
   * show the find cost page
   *
   * @return
   */
  def findCostItem = Action {
    Ok(views.html.findcostitem())
  }

  /**
   * show the delete cost page
   *
   * @return
   */
  def deleteCostItem = Action {
    Ok(views.html.deletecostitem())
  }

  /**
   * show the report page
   *
   * @return
   */
  def showReport = Action {
    Ok(views.html.report())
  }
}
