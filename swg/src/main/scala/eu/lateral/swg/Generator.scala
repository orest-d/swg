/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg

import eu.lateral.swg.db.Project
import eu.lateral.swg.utils._
import org.apache.commons.io.IOUtils
import org.apache.commons.vfs2.AllFileSelector
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.VFS
import org.squeryl.PrimitiveTypeMode._

class Generator {
  def template = defaultTemplateURL
  def deploy(project: Project, destinationURL: String) {
    val manager = VFS.getManager
    val destination = manager.resolveFile(destinationURL)
    val staticTemplates = manager.resolveFile(template).getChild("static")
    destination.copyFrom(staticTemplates, new AllFileSelector)

    val controller = manager.resolveFile(template).getChild("dynamic").getChild("js").getChild("controllers.js")

    save(substitute(IOUtils.toString(controller.getContent.getInputStream, "US-ASCII"), project), destination, "js/controllers.js")
    save(siteinfo(project), destination, "data/siteinfo.json")
    save(translations(project), destination, "data/translation.json")
  }

  def save(text: String, destination: FileObject, relativeDestinationPath: String) = {
    val destinationFile = destination.resolveFile(relativeDestinationPath)
    val input = IOUtils.toInputStream(text)
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
    val trtext = for (name <- Set(trans.map(_.translationName):_*)) yield {
      val tr = (for (t <- trans; if (t.translationName == name)) yield s"$q${t.languageCode}$q:$q{t.translation}$q").mkString(",")
      "\""+name+"\"{"+tr+"}"
    }
    "{" + trtext.mkString(",\n") + "}"
  }
}
