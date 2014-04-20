/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg
import eu.lateral.swg.utils._

object Images {
  def importImages(url:String)={
    val root = toFileObject(url)
    for (child <- traverse(url)){
      println(root.getName.getRelativeName(child.getName))
      
    }
  }

}
