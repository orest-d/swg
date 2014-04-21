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
package eu.lateral.swg

import eu.lateral.swg.db.Project
import eu.lateral.swg.utils._
import java.io.ByteArrayInputStream
import org.apache.commons.io.IOUtils
import org.apache.commons.vfs2.AllFileSelector
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.VFS
import org.squeryl.PrimitiveTypeMode._

class Generator {
  def template = defaultTemplateURL
  def deploy(project: Project, destinationURL: String, monitor: StatusMonitor) {
    monitor.info(s"Deployment to $destinationURL started")
    val manager = VFS.getManager
    val destination = manager.resolveFile(destinationURL)
    val staticTemplates = manager.resolveFile(template).getChild("static")
    destination.copyFrom(staticTemplates, new AllFileSelector)

    monitor.info(s"Copying static templates")
    val controller = manager.resolveFile(template).getChild("dynamic").getChild("js").getChild("controllers.js")

    save(substitute(IOUtils.toString(controller.getContent.getInputStream, "US-ASCII"), project), destination, "js/controllers.js", monitor)
    save(siteinfo(project), destination, "data/siteinfo.json", monitor)
    save(translations(project), destination, "data/translation.json", monitor)

    transaction {
      for (image <- project.images) {
        val number = image.imageNumber
        val bigext = image.bigImageFormat
        val thumbext = image.thumbnailFormat
        save(image.bigImage, destination, s"images/$number.$bigext", monitor)
        save(image.thumbnailImage, destination, s"images/${number}t.$thumbext", monitor)
      }
    }
    transaction {
      for (article <- project.articles) {
        val content = "<div><div ng-switch='language'>\n"+{
              for (a <- article.texts) yield{
                s"""|<div ng-switch-when='${a.languageName}'>
                    |  <h1>${a.articleTitle}</h1>
                    |  ${a.articleText}
                    |</div>""".stripMargin
              }
            }.mkString("\n")+"</div></div>"
        save(content.toString, destination, s"articles/${article.articleNumber}.html", monitor)
      }
    }
  }

  def save(text: String, destination: FileObject, relativeDestinationPath: String, monitor: StatusMonitor) = {
    monitor.info(s"Deploying $relativeDestinationPath")
    val destinationFile = destination.resolveFile(relativeDestinationPath)
    val input = IOUtils.toInputStream(text)
    val output = destinationFile.getContent.getOutputStream
    IOUtils.copy(input, output)
    input.close()
    output.close()
  }
  def save(content: Array[Byte], destination: FileObject, relativeDestinationPath: String, monitor: StatusMonitor) = {
    monitor.info(s"Deploying $relativeDestinationPath")
    val destinationFile = destination.resolveFile(relativeDestinationPath)
    val input = new ByteArrayInputStream(content)
    val output = destinationFile.getContent.getOutputStream
    IOUtils.copy(input, output)
    input.close()
    output.close()
  }

  def substitute(text: String, project: Project) = {
    text.
    replaceAllLiterally("$$LANGUAGETOCODE$$", languagecodes(project)).
    replaceAllLiterally("$$LANGUAGES$$", languagesJSONList(project)).
    replaceAllLiterally("$$DEFAULTLANGUAGE$$", defaultlanguage(project))

  }
  def languagecodes(project: Project) = transaction {
    val q = "\""
    "{" + project.languages.map(x => s"$q${x.languageName}$q:$q${x.languageCode}$q").mkString(",") + "}"
  }

  def defaultlanguage(project: Project) = transaction {
    project.languages.head.languageName
  }

  def languagesJSONList(project: Project) = transaction {
    "[" + project.languages.map(x => "\"" + x.languageName + "\"").mkString(",") + "]"
  }
  def siteinfo(project: Project) = transaction {
    val q = "\""
    val title = "\"title\":{" + project.siteInfo.map(x => s"$q${x.languageCode}$q:$q${x.title}$q").mkString(",") + "}"
    val menutitle = "\"menutitle\":{" + project.siteInfo.map(x => s"$q${x.languageCode}$q:$q${x.menuTitle}$q").mkString(",") + "}"
    val languages = "\"languages\":{" + project.languages.map(x => s"$q${x.languageCode}$q:$q${x.languageName}$q").mkString(",") + "}"
    "{" + title + "," + menutitle + "," + languages + "}"
  }
  def translations(project: Project) = transaction {
    val q = "\""
    val trans = project.translations.toList
    val trtext = for (name <- Set(trans.map(_.translationName): _*)) yield {
      val tr = (for (t <- trans; if (t.translationName == name)) yield s"$q${t.languageCode}$q:$q{t.translation}$q").mkString(",")
      "\"" + name + "\"{" + tr + "}"
    }
    "{" + trtext.mkString(",\n") + "}"
  }
}
