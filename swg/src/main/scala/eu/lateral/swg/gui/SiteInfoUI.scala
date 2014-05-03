/*
 This file is part of Static Web Gallery (SWG).

 MathMaster is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 MathMaster is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with SWG. If not, see <http://www.gnu.org/licenses/>.
 */

package eu.lateral.swg.gui
import eu.lateral.swg.db.SWGSchema
import org.squeryl.PrimitiveTypeMode._
import eu.lateral.swg.db.ProjectLanguage

trait SiteInfoUI {
  self:SWGApp =>
  def selectedSiteInfo = {
    inTransaction {
      val index = languageCombo.getSelectionIndex
      val info = project.siteInfo.toSeq
      if (info isDefinedAt index) Some(info(index)) else None
    }
  }
  override def loadDataIntoSiteInfo() {
    transaction {
      val ssi = selectedSiteInfo
      for (info <- ssi) {
        println(s"Selected ${info.languageName} -> ${info.title}")
        titleText.setText(info.title)
        menuTitleText.setText(info.menuTitle)
        if (!ssi.isDefined) {
          titleText.setText("")
          menuTitleText.setText("")
        }
      }
      updateDefaultLanguages()
      siteLanguagesList.setItems(allLanguages.map(_.languageName).toArray)
      siteLanguagesList.deselectAll
      for ((lang, i) <- allLanguages.zipWithIndex; if (project.languages.map(_.languageId).toSeq.contains(lang.id))) {
        siteLanguagesList.select(i)
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
      val defaultLanguage = allLanguages(0 max defaultLanguageCombo.getSelectionIndex)
      update(SWGSchema.projects)(x => where(x.id === project.id) set (x.defaultLanguageId := defaultLanguage.id))

      for (languageIndex <- siteLanguagesList.getSelectionIndices) {
        val language = allLanguages(languageIndex)
        if (!project.languages.toList.map(_.languageCode).contains(language.languageCode)) {
          SWGSchema.projectLanguages.insert(new ProjectLanguage(0, project.id, language.id))
        }
      }
      updateDefaultLanguages()
    }
  }
}
