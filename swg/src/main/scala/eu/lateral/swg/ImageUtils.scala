/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg
import eu.lateral.swg.db.ImageRecord
import eu.lateral.swg.db.Project
import eu.lateral.swg.db.SWGSchema
import eu.lateral.swg.utils._
import org.squeryl.PrimitiveTypeMode._

object ImageUtils {
  def importImages(project: Project, url: String, monitor: StatusMonitor) = {
    val root = toFileObject(url)

    for (child <- traverse(url)) {
      val relativeName = root.getName.getRelativeName(child.getName)
      monitor.info(s"Importing $relativeName")
      transaction{
        SWGSchema.images.insert(new ImageRecord(
            id=0,
            projectId=project.id,
            imageNumber=project.maxImageNumber+1,
            sourceURL=child.getName.getPath,
            relativePath=relativeName,
            originalImage=toByteArray(child),
            bigImage=null,
            thumbnailImage=null))
      }
    }
  }
}
