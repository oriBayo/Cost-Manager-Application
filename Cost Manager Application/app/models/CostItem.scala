package models

import play.api.libs.json._

/**
 * Cost Item class
 *
 * @param name
 * @param price
 * @param category
 * @param date
 * @param description
 */
case class CostItem(
                     name: String,
                     price: Double,
                     category: String,
                     date: String,
                     description: String
                   )

object CostItem {
  implicit val format: OFormat[CostItem] = Json.format[CostItem]

}