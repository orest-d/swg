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
import eu.lateral.swg.db.Language
import eu.lateral.swg.db.Project
import eu.lateral.swg.Generator
import eu.lateral.swg.db.SWGSchema
import eu.lateral.swg.db.SessionManager
import eu.lateral.swg.utils._
import org.eclipse.swt.SWT
import org.squeryl.PrimitiveTypeMode._

class SWGApp extends UserInterface {
  var project = Project.default
  var allLanguages = List.empty[Language]

  def selectedSiteInfo = {
    inTransaction {
      val index = languageCombo.getSelectionIndex
      val info = project.siteInfo.toSeq
      if (info isDefinedAt index) Some(info(index)) else None
    }
  }
  override def deploy() {
    val url = urlFromPath("www1")
    SessionManager.initializeDatabase()
    val g = new Generator
    g.deploy(project, url)
  }
  override def loadDataIntoSiteInfo() {
    transaction {
      val ssi = selectedSiteInfo
      for (info <- ssi) {
        titleText.setText(info.title)
        menuTitleText.setText(info.menuTitle)
      }
      if (!ssi.isDefined) {
        titleText.setText("")
        menuTitleText.setText("")
      }
    }
  }
  def saveDataIntoSiteInfo() {
    transaction {
      val info = selectedSiteInfo
      if (info.isDefined) {
        update(SWGSchema.siteInfo)(x =>
          where(x.id === info.get.id)
            set (
              x.title := titleText.getText,
              x.menuTitle := menuTitleText.getText))
      }
    }
  }
  def loadAllLanguages()={
    inTransaction{
      allLanguages=from(SWGSchema.languages)(x => select(x)).toList
    }
  }
  override def init() {
    loadAllLanguages()
    siteLanguagesList.setItems(allLanguages.map(_.languageName).toArray)
    transaction {
      val languages = project.languages.map(_.languageName).toArray
      languageCombo.setItems(languages)
      if (languages.length>0){
        languageCombo.select(0)
      }
            
    }    
    loadDataIntoSiteInfo
  }
  override def languageChanged() {
    loadDataIntoSiteInfo()
  }
  override def siteInfoUpdated(){
    saveDataIntoSiteInfo()
  }
}