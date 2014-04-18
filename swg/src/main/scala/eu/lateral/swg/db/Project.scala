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

class Project(
  @Column("id") val id: Long,
  @Column("project_name") val projectName: String) extends KeyedEntity[Long] {
  def this() = this(0, "")
  lazy val languages: OneToMany[ProjectLanguageView] = SWGSchema.projectToLanguages.left(this)
  def addLanguageByCode(code: String) = {
    inTransaction {
      if (languages.forall(_.languageCode != code)) {
        for (language <- SWGSchema.languages.where(l => l.languageCode === code)) {
          SWGSchema.projectLanguages.insert(new ProjectLanguage(0, id, language.id))
        }
      }
    }
  }
  def removeLanguageByCode(code: String) = {
    inTransaction {
      for (projectLanguage <- languages; if (projectLanguage.languageCode == code)){
        SWGSchema.projectLanguages.delete(projectLanguage.id)
      }
    }
  }
}

object Project {
  def default:Project = inTransaction {
    SWGSchema.projects.lookup(1L).get
  }
}