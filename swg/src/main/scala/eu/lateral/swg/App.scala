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
import eu.lateral.swg.db._
import org.apache.commons.cli.GnuParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Options
import org.apache.commons.vfs2.AllFileSelector
import org.apache.commons.vfs2.VFS

object App extends ConsoleStatusMonitor {
  def main(arg: Array[String]) {
    val options = new Options()
    options.addOption("h", "help", false, "print this help")
    options.addOption("g", "generate", false, "Generate the site")
    options.addOption("o", "output", true, "Path to a local directory to deploy")
    options.addOption("d", "database", true, "Database name")
    options.addOption("i", "images", false, "Import images")
    options.addOption("p", "imagespath", true, "path to the images")

    val parser = new GnuParser
    val cli = parser.parse(options, arg)

    if (cli.hasOption("help")) {
      (new HelpFormatter).printHelp("swg", options)
      System.exit(0)
    }

    SessionManager.initializeDatabase(cli.getOptionValue("database", "swg"))

    if (cli.hasOption("images")) {
      val url = urlFromPath(cli.getOptionValue("imagespath", "images"))
      Images.importImages(url)
      System.exit(0)
    }

    if (cli.hasOption("generate")) {
      val url = urlFromPath(cli.getOptionValue("output", "www"))
      val g = new Generator
      g.deploy(Project.default, url, this)
      System.exit(0)
    }
    val app = new eu.lateral.swg.gui.App()
    app.createUI()
    app.mainloop()
  }
}
