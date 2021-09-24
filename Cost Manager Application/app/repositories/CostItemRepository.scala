package repositories

import models.CostItem

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.commands.WriteResult
import reactivemongo.api.{Cursor, ReadPreference}
import reactivemongo.bson.{BSONDocument, BSONObjectID}
import reactivemongo.play.json._
import reactivemongo.play.json.collection.JSONCollection


/**
 * DAO class for accessing the MongoDB database
 *
 * @param ec
 * @param reactiveMongoApi
 */
class CostItemRepository @Inject()(
                                    implicit ec: ExecutionContext,
                                    reactiveMongoApi: ReactiveMongoApi
                                  ) {
  // create the collection costs
  private def collection: Future[JSONCollection] =
    reactiveMongoApi.database.map(_.collection("costs"))

  /**
   * get list of all the cost items from the database
   *
   * @param limit
   * @return
   */
  def list(limit: Int = 100): Future[Seq[CostItem]] =
    collection.flatMap(_
      .find(BSONDocument())
      .cursor[CostItem](ReadPreference.primary)
      .collect[Seq](limit, Cursor.FailOnError[Seq[CostItem]]())
    )

  /**
   * add new cost item to the database
   *
   * @param costItem
   * @return
   */
  def create(costItem: CostItem): Future[WriteResult] =
    collection.flatMap(_.insert(costItem))

  /**
   * get specific cost item from the db by id
   *
   * @param id
   * @return
   */
  def read(id: BSONObjectID): Future[Option[CostItem]] =
    collection.flatMap(_.find(BSONDocument("_id" -> id)).one
      [CostItem])


  /**
   * delete an existing cost item from the database
   *
   * @param id
   * @return
   */
  def delete(id: BSONObjectID): Future[Option[CostItem]] =
    collection.flatMap(_.findAndRemove(BSONDocument("_id" -> id)).map(_.result[CostItem])
    )

}
