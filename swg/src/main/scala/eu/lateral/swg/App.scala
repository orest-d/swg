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

import eu.lateral.swg.utils._
import org.apache.commons.vfs2.AllFileSelector
import org.apache.commons.vfs2.VFS

object App{
  def main(argv:Array[String]){
    println("SWG")
    val manager = VFS.getManager
    //val file1 = manager.resolveFile("file:///home/orest/zlos/webdev/gallery/GIT/swg/g1-in")
    //val file1 = manager.resolveFile("res://templates/g1")
    //val file1 = manager.resolveFile("jar:///home/orest/zlos/webdev/gallery/GIT/swg/target/swg-1.0-SNAPSHOT-jar-with-dependencies.jar!/templates/g1")
    
    /*
    val templatesLink=if (exists("src/main/resources/templates/g1")){
      absolutePath("src/main/resources/templates")
    }
    */
    val f = manager.resolveFile(getClass.getResource(getClass.getSimpleName() + ".class").toString)
    val ff = f.getParent.getParent.getParent.getParent.resolveFile("templates") 
    println(ff.getURL)
    val file1 = ff
    val file2 = manager.resolveFile("file:///home/orest/zlos/webdev/gallery/GIT/swg/g1-out")
    file2.copyFrom(file1, new AllFileSelector)
  }
}