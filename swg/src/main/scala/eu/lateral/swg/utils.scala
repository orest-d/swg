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

import java.io.File
import org.apache.commons.io.IOUtils
import org.apache.commons.vfs2.FileObject
import org.apache.commons.vfs2.FileType
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
  def urlFromPath(path: String) = (new File(path)).toURI.toURL.toExternalForm

  def embeddedTemplatesBaseURL = {
    val manager = VFS.getManager
    val file = manager.resolveFile(getClass.getResource(getClass.getSimpleName() + ".class").toString)
    file.getParent.getParent.getParent.getParent.getParent.resolveFile("templates").getURL
  }
  def defaultTemplateURL = {
    embeddedTemplatesBaseURL + "/swg1"
  }
  def traverse(file: FileObject): Stream[FileObject] = {
    if (file.getType == FileType.FILE) {
      Stream(file)
    } else {
      for (
        child <- file.getChildren.toStream;
        leaf <- traverse(child)
      ) yield leaf
    }
  }
  def toFileObject(url:String)=VFS.getManager.resolveFile(url)

  def traverse(url: String): Stream[FileObject] = traverse(toFileObject(url))

  def ancestors(file: FileObject): Stream[FileObject] = {
    if (file == null) Stream.empty else file #:: ancestors(file.getParent)
  }
  def toByteArray(file:FileObject)=IOUtils.toByteArray(file.getContent.getInputStream)

}
