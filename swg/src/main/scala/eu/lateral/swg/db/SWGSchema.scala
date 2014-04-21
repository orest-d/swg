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
import org.squeryl.Schema
import org.squeryl.PrimitiveTypeMode._

object SWGSchema extends Schema {
  val projects = table[Project]("projects")
  val languages = table[Language]("languages")
  val projectLanguages = table[ProjectLanguage]("project_languages")
  val projectLanguagesView = table[ProjectLanguageView]("project_languages_view")
  val siteInfo = table[SiteInfo]("siteinfo")
  val siteInfoView = table[SiteInfoView]("siteinfo_view")
  val translations = table[Translation]("translations")
  val translationsView = table[TranslationView]("translations_view")
  val articles = table[Article]("articles")
  val articleTexts = table[ArticleTexts]("article_texts")
  val articlesView = table[ArticleView]("articles_view")
  val images = table[ImageRecord]("images")

  val projectToLanguages =
    oneToManyRelation(projects, projectLanguagesView).
      via((p, l) => p.id === l.projectId)
  val projectToSiteInfo =
    oneToManyRelation(projects, siteInfoView).
      via((p, l) => p.id === l.projectId)
  val projectToTranslations =
    oneToManyRelation(projects, translationsView).
      via((p, l) => p.id === l.projectId)
  val projectToArticles =
    oneToManyRelation(projects, articles).
      via((p, l) => p.id === l.projectId)
  val projectToImages =
    oneToManyRelation(projects, images).
      via((p, l) => p.id === l.projectId)
}
