/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg

import java.io.File
import scala.util.Try
import com.googlecode.flyway.core.Flyway

package object utils {
  /**
   * Check if a file exists
   */
  def exists(path: String) = (new File(path)).exists
  def absolutePath(path: String) = (new File(path)).getAbsolutePath

  def setupDatabase() = {
    val result=Try {
      if (!exists("swg.h2.db")) {
        val flyway = new Flyway
        flyway.setDataSource("jdbc:h2:swg", "sa", "");
        flyway.migrate();
        "Database initialized"
      } else {
        "Database exists"
      }
    }
    result.getOrElse(result.toString)
  }

}
