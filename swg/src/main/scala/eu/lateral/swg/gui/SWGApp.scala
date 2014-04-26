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
import eu.lateral.swg.db.Language
import eu.lateral.swg.db.Project
import eu.lateral.swg.Generator
import eu.lateral.swg.StatusMonitor
import eu.lateral.swg.db.ProjectLanguage
import eu.lateral.swg.db.SWGSchema
import eu.lateral.swg.db.SessionManager
import eu.lateral.swg.utils._
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.widgets.Display
import org.squeryl.PrimitiveTypeMode._
import scala.concurrent._
import ExecutionContext.Implicits.global

class SWGApp extends UserInterface with StatusMonitor {
  var project = Project.default
  var allLanguages = List.empty[Language]
  def runnable(perform: => Unit) = new Runnable {
    override def run() = perform
  }
  def swtAssync(perform: => Unit) = {
    if (display == null) {
      println("null display")
      perform
    } else {
      display.asyncExec(runnable(perform))
    }
  }
  def color(r: Int, g: Int, b: Int) = {
    val device = Display.getCurrent
    new Color(device, r, g, b)
  }

  def info(text: String): Unit = {
    println(text)
    swtAssync {
      statusLabel.setBackground(color(240, 240, 240))
      statusLabel.setText(text)
    }
  }

  def error(text: String): Unit = {
    println("ERROR: " + text)
    swtAssync {
      statusLabel.setBackground(color(255, 10, 10))
      statusLabel.setText(text)
    }
  }

  def selectedSiteInfo = {
    inTransaction {
      val index = languageCombo.getSelectionIndex
      val info = project.siteInfo.toSeq
      if (info isDefinedAt index) Some(info(index)) else None
    }
  }
  override def deploy() = {
    val url = urlFromPath("www")
    SessionManager.initializeDatabase()
    val f = future {
      val g = new Generator
      g.deploy(project, url, this)
    }
    f.onSuccess { case _ => info("OK") }
    f.onFailure { case t => error(t) }
  }

  def updateDefaultLanguages() {
    val languages = allLanguages.map(_.languageName).toArray
    defaultLanguageCombo.setItems(languages)
    if (languages.length > 0) {
      defaultLanguageCombo.select(0)
    }
    for (defaultLanguageCode <- project.defaultLanguageCode) {
      defaultLanguageCombo.select(0 max allLanguages.indexWhere(_.languageCode == defaultLanguageCode))
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

  def selectedArticles = {
    inTransaction {
      val ssi = selectedSiteInfo
      for (
        info <- ssi.toList;
        article <- from(SWGSchema.articlesView)(x => where(
          (x.languageCode === info.languageCode).and(x.projectId === info.projectId))
          select (x))
      ) yield article
    }
  }
  def loadDataIntoArticleSelector() {
    val selected = 0 max selectArticleCombo.getSelectionIndex
    val articles = selectedArticles.map(_.articleTitle).toArray
    selectArticleCombo.setItems(articles)
    if (articles.length>0){
      selectArticleCombo.select(selected)
    }
  }
  def loadDataIntoArticle() {
    val selected = 0 max selectArticleCombo.getSelectionIndex
    val articles = selectedArticles
    if (articles.isDefinedAt(selected)){
      val article = articles(selected)
      articleTitleText.setText(article.articleTitle)
      articleLinkText.setText(article.articleLinkTitle)
      articleText.setText(article.articleText)
    }
  }
  override def articleChanged(){
    loadDataIntoArticle()
  }
  def loadAllLanguages() = {
    inTransaction {
      allLanguages = from(SWGSchema.languages)(x => select(x)).toList
    }
  }
  override def init() {
    loadAllLanguages()
    siteLanguagesList.setItems(allLanguages.map(_.languageName).toArray)
    transaction {
      val languages = project.languages.map(_.languageName).toArray
      languageCombo.setItems(languages)
      if (languages.length > 0) {
        languageCombo.select(0)
      }
    }
    loadDataIntoSiteInfo
    loadDataIntoArticleSelector()
    loadDataIntoArticle()
  }
  override def languageChanged() {
    loadDataIntoSiteInfo()
    loadDataIntoArticleSelector()
    loadDataIntoArticle()
  }
  override def siteInfoUpdated() {
    saveDataIntoSiteInfo()
  }
  //  override def run() = perform
}
