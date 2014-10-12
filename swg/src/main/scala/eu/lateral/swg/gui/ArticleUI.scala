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
import eu.lateral.swg.db.Article
import eu.lateral.swg.db.ArticleTexts
import eu.lateral.swg.db.SWGSchema
import org.squeryl.PrimitiveTypeMode._

trait ArticleUI {
  self: SWGApp =>
  override def newArticle() = {
    transaction {
      val articlenumber: Int = from(SWGSchema.articles)(
        x => where(x.projectId === project.id).
          compute(max(x.articleNumber))).
        single.measures.getOrElse(0) + 1
      val article = SWGSchema.articles.insert(new Article(0, project.id, articlenumber, true))
      val name = s"Article $articlenumber"
      for (language <- project.languages) {
        SWGSchema.articleTexts.insert(new ArticleTexts(0, project.id, language.id, articlenumber, name, name, ""))
      }
      loadDataIntoArticleSelectorAndSelectByIndex(articleNumberToIndex(articlenumber))
    }
    loadDataIntoArticleSelector
    loadDataIntoArticle
  }
  override def deleteArticle() = {
    if (confirmDeleteArticleButton.getSelection) {
      transaction {
        for (articlenumber <- selectedArticleNumber) {
          SWGSchema.articles.deleteWhere(_.articleNumber === articlenumber)
          SWGSchema.articleTexts.deleteWhere(_.articleNumber === articlenumber)
        }
      }
      loadDataIntoArticleSelector
      loadDataIntoArticle
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
  def articleNumberToIndex(articleNumber: Int) = selectedArticles.indexWhere(_.articleNumber == articleNumber)
  def articleIndexToNumber(articleIndex: Int) = {
    val articles = selectedArticles.toList
    if (articles.isDefinedAt(articleIndex)) Some(articles(articleIndex).articleNumber) else None
  }
  def selectedArticleNumber = articleIndexToNumber(selectArticleCombo.getSelectionIndex)

  def loadDataIntoArticleSelectorAndSelectByIndex(articleIndex: Int) = {
    val articles = selectedArticles.map(_.articleTitle).toArray
    selectArticleCombo.setItems(articles)
    if (articles.length > 0) {
      selectArticleCombo.select(articleIndex)
    }
  }
  def loadDataIntoArticleSelector() = {
    loadDataIntoArticleSelectorAndSelectByIndex(0 max selectArticleCombo.getSelectionIndex)
  }

  def loadDataIntoArticle() = {
    val index = 0 max selectArticleCombo.getSelectionIndex
    val articles = selectedArticles
    if (articles.isDefinedAt(index)) {
      val article = articles(index)
      articleTitleText.setText(article.articleTitle)
      articleLinkText.setText(article.articleLinkTitle)
      articleText.setText(article.articleText)
    }
  }
  def saveDataIntoArticle() {
    val articleIndex = 0 max selectArticleCombo.getSelectionIndex
    val articles = selectedArticles
    if (articles.isDefinedAt(articleIndex)) {
      val article = articles(articleIndex)
      transaction {
        update(SWGSchema.articleTexts)(x => where(x.id === article.id)
          set (
            x.articleTitle := articleTitleText.getText,
            x.articleLinkTitle := articleLinkText.getText,
            x.articleText := articleText.getText))
      }
    }
  }
  override def articleChanged() {
    loadDataIntoArticle()
  }

}
