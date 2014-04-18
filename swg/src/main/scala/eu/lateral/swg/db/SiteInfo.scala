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

class SiteInfo(
  @Column("id") val id: Long,
  @Column("project_id") val projectId: Long,
  @Column("project_language_id") val projectLanguageId: Long,
  @Column("title") val title: String,
  @Column("menutitle") val menuTitle: String) extends KeyedEntity[Long] {
  def this() = this(0, 0, 0, "", "")
}

class SiteInfoView(
  @Column("id") val id: Long,
  @Column("project_id") val projectId: Long,
  @Column("project_language_id") val projectLanguageId: Long,
  @Column("title") val title: String,
  @Column("menutitle") val menuTitle: String,
  @Column("project_name") val projectName: String,
  @Column("language_code") val languageCode: String,
  @Column("language_name") val languageName: String) extends KeyedEntity[Long] {
  def this() = this(0, 0, 0, "", "", "", "", "")
}
