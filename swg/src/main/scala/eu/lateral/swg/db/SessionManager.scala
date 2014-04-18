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
package eu.lateral.swg.db

import com.googlecode.flyway.core.Flyway
import java.sql.Connection
import java.sql.DriverManager
import org.squeryl.Session
import org.squeryl.SessionFactory
import org.squeryl.adapters.H2Adapter
import scala.util.Try

object SessionManager {
  Class.forName("org.h2.Driver").newInstance
  val productionDatabase = "swg"
  val url = urlFromDatabaseName(productionDatabase)
  val user = "sa"
  val password = ""
  def urlFromDatabaseName(database:String)=s"jdbc:h2:$database"

  def getConnectionByUrl(url: String, user: String, password: String): Connection = {
    DriverManager.getConnection(url, user, password)
  }

  def connection(): Connection = getConnectionByUrl(url, user, password)
  def connection(database: String): Connection = getConnectionByUrl(urlFromDatabaseName(database), user, password)

  def setupDatabase(url: String, user: String, password: String): String = {
    val result = Try {
      val flyway = new Flyway
      flyway.setDataSource(url, user, password);
      flyway.migrate();
      "Database migration performed"
    }
    result.getOrElse(result.toString)
  }
  def setupDatabase(database: String = productionDatabase): String = setupDatabase(urlFromDatabaseName(database), user, password)

  def createSession(database: String = productionDatabase) = Session.create(getConnectionByUrl(s"jdbc:h2:$database", user, password), new H2Adapter)

  def initializeDatabase(database:String = productionDatabase)={
    setupDatabase(database)
    SessionFactory.concreteFactory = Some(() => createSession(database))
  }
}
