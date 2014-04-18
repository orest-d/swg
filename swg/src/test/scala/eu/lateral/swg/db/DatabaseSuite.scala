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

import org.scalatest.Assertions
import org.testng.Assert._
import org.testng.annotations.Test
import org.squeryl.PrimitiveTypeMode._

class DatabaseSuite extends Assertions{
  @Test def testTrivial() {
    assertTrue(1 == 1)
  }
  @Test def testDefaultProject() {
    SessionManager.initializeDatabase("swg_test")
    transaction{
      assertEquals(SWGSchema.projects.lookup(1L).get.projectName,"default")
    }
  }
}