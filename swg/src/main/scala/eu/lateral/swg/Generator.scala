/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg

import eu.lateral.swg.db.Project
import eu.lateral.swg.utils._
import org.apache.commons.vfs2.AllFileSelector
import org.apache.commons.vfs2.VFS

class Generator {
  def template = defaultTemplateURL
  def deploy(project: Project, destinationURL: String) {
    val manager = VFS.getManager
    val destination = manager.resolveFile(destinationURL)
    val staticTemplates = manager.resolveFile(template).getChild("static")
    destination.copyFrom(staticTemplates, new AllFileSelector)
  }
}
