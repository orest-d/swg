/*
 This file is part of Static Web Gallery (SWG).

 MathMaster is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 MathMaster is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with SWG.  If not, see <http://www.gnu.org/licenses/>.
 */
package eu.lateral.swg.db
import org.squeryl.KeyedEntity
import org.squeryl.PrimitiveTypeMode._
import org.squeryl.Schema
import org.squeryl.annotations.Column
import org.squeryl.dsl.OneToMany

class Menu(
  @Column("id") val id: Long,
  @Column("project_id") val projectId: Long,
  @Column("menu_number") val menuNumber: Int,
  @Column("menu_level") val menuLevel: Int,
  @Column("article_number") val articleNumber: Option[Int],
  @Column("gallery_number") val galleryNumber: Option[Int]) extends KeyedEntity[Long] {
  def this() = this(0, 0, 0, 0, None, None)
  def article = {
    inTransaction {
      if (articleNumber.isDefined) {
        from(SWGSchema.articles)(a => where((a.projectId === projectId).and(a.articleNumber === articleNumber)) select (a)).headOption
      } else None
    }
  }
  def gallery = {
    inTransaction {
      if (galleryNumber.isDefined) {
        from(SWGSchema.galleries)(a => where((a.projectId === projectId).and(a.galleryNumber === galleryNumber)) select (a)).headOption
      } else None
    }
  }
}
