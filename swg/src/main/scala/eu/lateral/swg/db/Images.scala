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

class ImageRecord(
  @Column("id") val id: Long,
  @Column("project_id") val projectId: Long,
  @Column("image_number") val imageNumber: Int,
  @Column("source_url") val sourceURL: String,
  @Column("relative_path") val relativePath: String,
  @Column("original_image") val originalImage: Array[Byte],
  @Column("big_image") val bigImage: Array[Byte],
  @Column("big_image_format") val bigImageFormat: String,
  @Column("thumbnail_image") val thumbnailImage: Array[Byte],
  @Column("thumbnail_format") val thumbnailFormat: String) extends KeyedEntity[Long] {
  def this() = this(0, 0, 0, "", "", null, null, "", null, "")
}
