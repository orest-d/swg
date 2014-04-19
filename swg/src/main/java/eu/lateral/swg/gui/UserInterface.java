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
package eu.lateral.swg.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.swt.widgets.Combo;

public class UserInterface {
	public Display display;
	public Shell shell;
	public Label statusLabel;
	public Combo languageCombo;

	/**
	 * @wbp.parser.entryPoint
	 */
	public int createUI() {
		Display display = new Display();
		Shell mainShell = new Shell(display, SWT.RESIZE | SWT.TITLE);
		mainShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
					System.exit(0);
			}
		});
		mainShell.setText("SWG");
		mainShell.setMaximized(true);

		mainShell.layout();
		mainShell.open();

		this.display = display;
		this.shell = mainShell;
		mainShell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite composite = new Composite(mainShell, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		
		languageCombo = new Combo(composite, SWT.NONE);
		languageCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button deployButton = new Button(composite, SWT.NONE);
		deployButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				deploy();
			}
		});
		deployButton.setText("Deploy");
		
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		GridData gd_tabFolder = new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1);
		gd_tabFolder.widthHint = 305;
		tabFolder.setLayoutData(gd_tabFolder);
		
		statusLabel = new Label(composite, SWT.NONE);
		statusLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		statusLabel.setText("OK");
		shell.open();
		shell.layout();
		init();
		return 0;
	}
	
	public void setStatus(String text){
		statusLabel.setText(text);
	}
	public void init(){		
	}
	public void deploy(){		
	}
}
