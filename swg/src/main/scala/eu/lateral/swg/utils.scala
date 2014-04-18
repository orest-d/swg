/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg

import java.io.File
import org.apache.commons.vfs2.VFS
import scala.util.Try
import com.googlecode.flyway.core.Flyway

package object utils {
  /**
   * Check if a file exists
   */
  def exists(path: String) = (new File(path)).exists
  def delete(path: String) = (new File(path)).delete
  def absolutePath(path: String) = (new File(path)).getAbsolutePath

  def embeddedTemplatesBaseURL={
    val manager = VFS.getManager
    val file = manager.resolveFile(getClass.getResource(getClass.getSimpleName() + ".class").toString)
    file.getParent.getParent.getParent.getParent.getParent.resolveFile("templates").getURL
  }
  def defaultTemplateURL={
    embeddedTemplatesBaseURL+"/swg1"
  }
}
