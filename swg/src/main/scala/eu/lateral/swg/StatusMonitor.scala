/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.lateral.swg

trait StatusMonitor {
  def info(text:String):Unit
  def error(text:String):Unit
  def error(throwable:Throwable):Unit=error(throwable.getMessage.toString)
  def safely(perform: =>Unit){
    try{
      perform
    }
    catch{
      case t:Throwable => error(t)
    }
  }
  def safelyReport(perform: =>String){
    try{
      info(perform)
    }
    catch{
      case t:Throwable => error(t)
    }    
  }
}

trait ConsoleStatusMonitor extends StatusMonitor{
  def info(text:String):Unit = {println(text)}
  def error(text:String):Unit = {println("ERROR: "+text)}
}