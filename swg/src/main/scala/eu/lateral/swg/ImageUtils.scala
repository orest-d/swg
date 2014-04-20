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
import java.awt.AlphaComposite
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO
import org.apache.commons.io.IOUtils
import org.squeryl.PrimitiveTypeMode._

object ImageUtils {
  def resize(image: Array[Byte], width: Int, height: Int, format: String, thumbnail: Boolean = true): Array[Byte] = {
    val imagetype = if (format.toLowerCase == "jpg") BufferedImage.TYPE_3BYTE_BGR else BufferedImage.TYPE_INT_ARGB
    val originalImage = ImageIO.read(new ByteArrayInputStream(image))
    val (w, h) = (width.toDouble, height.toDouble)
    val (ow, oh) = (originalImage.getWidth.toDouble, originalImage.getHeight.toDouble)

    val (rw, rh, x, y) = if ((w / h) < (ow / oh)) {
      val adapted = oh * w / ow
      (w, adapted, 0.0, (h - adapted) / 2)
    } else {
      val adapted = ow * h / oh
      (adapted, h, (w - adapted) / 2, 0.0)
    }

    val resizedImage = if (thumbnail)
      new BufferedImage(width, height, imagetype)
    else
      new BufferedImage(rw.toInt, rh.toInt, imagetype)
    
    val g = resizedImage.createGraphics()
    g.setComposite(AlphaComposite.Src)
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR)
    g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY)
    g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    if (thumbnail)
      g.drawImage(originalImage, x.toInt, y.toInt, rw.toInt, rh.toInt, null)
    else
      g.drawImage(originalImage, 0, 0, rw.toInt, rh.toInt, null)
    
    g.dispose()
    val out = new ByteArrayOutputStream
    ImageIO.write(resizedImage, format, out)
    out.toByteArray
  }

  def importImages(project: Project, url: String, monitor: StatusMonitor) = {
    val root = toFileObject(url)

    for (child <- traverse(url)) {
      val relativeName = root.getName.getRelativeName(child.getName)
      monitor.info(s"Importing $relativeName")
      transaction {
        val image = toByteArray(child)
        SWGSchema.images.insert(new ImageRecord(
            id = 0,
            projectId = project.id,
            imageNumber = project.maxImageNumber + 1,
            sourceURL = child.getName.getURI,
            relativePath = relativeName,
            originalImage = image,
            bigImage = resize(image, project.imageWidth, project.imageHeight, "jpg",false),
            bigImageFormat = "jpg",
            thumbnailImage = resize(image, project.thumbnailWidth, project.thumbnailHeight, "png",true),
            thumbnailFormat = "png"))
      }
    }
  }
}