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
package eu.lateral.swg.gui
import eu.lateral.swg.db.Project
import eu.lateral.swg.Generator
import eu.lateral.swg.db.SessionManager
import eu.lateral.swg.utils._
import org.eclipse.swt.SWT
import org.squeryl.PrimitiveTypeMode._

class SWGApp extends UserInterface {
  override def deploy() {
    val url = urlFromPath("www1")
    SessionManager.initializeDatabase()
    val g = new Generator
    g.deploy(Project.default, url)
  }
  override def init() {
    transaction {
      languageCombo.setItems(Project.default.languages.map(_.languageName).toArray)
    }
  }
}