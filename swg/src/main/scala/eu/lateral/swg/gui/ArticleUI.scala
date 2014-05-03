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

trait ArticleUI {
  self:SWGApp =>
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
    if (articles.length > 0) {
      selectArticleCombo.select(selected)
    }
  }
  def loadDataIntoArticle() {
    val selected = 0 max selectArticleCombo.getSelectionIndex
    val articles = selectedArticles
    if (articles.isDefinedAt(selected)) {
      val article = articles(selected)
      articleTitleText.setText(article.articleTitle)
      articleLinkText.setText(article.articleLinkTitle)
      articleText.setText(article.articleText)
    }
  }
  def saveDataIntoArticle() {
    val selected = 0 max selectArticleCombo.getSelectionIndex
    val articles = selectedArticles
    if (articles.isDefinedAt(selected)) {
      val article = articles(selected)
      transaction {
        update(SWGSchema.articleTexts)(x => where(x.id === article.id)
          set (
            x.articleTitle := articleTitleText.getText,
            x.articleLinkTitle := articleLinkText.getText,
            x.articleText := articleText.getText
          ))
      }
    }
  }
  override def articleChanged() {
    loadDataIntoArticle()
  }

}
