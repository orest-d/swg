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

import eu.lateral.swg.utils._
import org.scalatest.Assertions
import org.testng.Assert._
import org.testng.annotations.{Test,BeforeSuite,AfterSuite}
import org.squeryl.PrimitiveTypeMode._

class DatabaseSuite extends Assertions{
  implicit var implicitProject:Project = null
  @BeforeSuite def setUp(){
    SessionManager.initializeDatabase("swg_test")    
    implicitProject=Project.default
  }
  @AfterSuite def tearDown(){
    delete("swg_test.h2.db")
  }
  
  @Test def testTrivial() {
    assertTrue(1 == 1)
  }
  @Test def testDefaultProject() {
    transaction{
      assertEquals(SWGSchema.projects.lookup(1L).get.projectName,"default")
    }
    delete("swg_test")
  }
  @Test def testDefaultLanguages() {
    SessionManager.initializeDatabase("swg_test")
    transaction{
      assertEquals(SWGSchema.languages.where(l => l.languageCode==="en").single.languageName,"English")
      assertEquals(from(SWGSchema.languages)(l => where(l.languageCode==="en") select(l.languageName)).head,"English")
    }
  }
  @Test def testLanguageNameForCode() {
    assertEquals(Languages.languageForCode("en"),Some("English"))
    assertEquals(Languages.languageForCode("undefined language"),None)
  }  
  @Test def testProjectLanguages() {
     transaction{
       Project.default.languages.exists(_.languageCode=="en")
       assertEquals(Project.default.languages.toSeq.length,1)
       Project.default.addLanguageByCode("sk")
       assertEquals(Project.default.languages.toSeq.length,2)
       Project.default.languages.exists(_.languageCode=="sk")
       Project.default.removeLanguageByCode("sk")
       assertEquals(Project.default.languages.toSeq.length,1)
       Project.default.languages.exists(_.languageCode=="en")
    }
  }  
}
 