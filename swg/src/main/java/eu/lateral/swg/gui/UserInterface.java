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
import org.eclipse.swt.widgets.List;

public class UserInterface {
	public Display display;
	public Shell shell;
	public Label statusLabel;
	public Combo languageCombo;
	public Combo defaultLanguageCombo;
	public List siteLanguagesList;
	public ProgressBar progressBar;
	public Text titleText;
	public Text menuTitleText;

	/**
	 * @wbp.parser.entryPoint
	 */
	public int createUI() {
		Display display = new Display();
		Shell mainShell = new Shell(display, SWT.RESIZE | SWT.TITLE);
		mainShell.setSize(466, 486);
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
		composite.setLayout(new GridLayout(3, false));
		
		languageCombo = new Combo(composite, SWT.NONE);
		languageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				languageChanged();
			}
		});
		languageCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Button deployButton = new Button(composite, SWT.NONE);
		deployButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				deploy();
			}
		});
		deployButton.setText("Deploy");
		new Label(composite, SWT.NONE);
		
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
		
		TabItem tbtmSiteInfo = new TabItem(tabFolder, SWT.NONE);
		tbtmSiteInfo.setText("Site Info");
		
		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbtmSiteInfo.setControl(composite_1);
		composite_1.setLayout(new GridLayout(2, false));
		
		Label lblTitle = new Label(composite_1, SWT.NONE);
		lblTitle.setText("Title");
		
		titleText = new Text(composite_1, SWT.BORDER);
		titleText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				siteInfoUpdated();
			}
		});
		titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblMenuTitle = new Label(composite_1, SWT.NONE);
		lblMenuTitle.setText("Menu title");
		
		menuTitleText = new Text(composite_1, SWT.BORDER);
		menuTitleText.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				siteInfoUpdated();
			}
		});
		menuTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblDefaultLanguage = new Label(composite_1, SWT.NONE);
		lblDefaultLanguage.setText("Default language");
		
		defaultLanguageCombo = new Combo(composite_1, SWT.NONE);
		defaultLanguageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				siteInfoUpdated();
			}
		});
		GridData gd_defaultLanguageCombo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_defaultLanguageCombo.widthHint = 205;
		defaultLanguageCombo.setLayoutData(gd_defaultLanguageCombo);
		
		Label lblSiteLanguages = new Label(composite_1, SWT.NONE);
		lblSiteLanguages.setText("Site languages");
		
		siteLanguagesList = new List(composite_1, SWT.BORDER);
		siteLanguagesList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				siteLanguagesChanged();
			}
		});
		GridData gd_siteLanguagesList = new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 2);
		gd_siteLanguagesList.widthHint = 202;
		siteLanguagesList.setLayoutData(gd_siteLanguagesList);
		new Label(composite_1, SWT.NONE);
		
		statusLabel = new Label(composite, SWT.NONE);
		statusLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		statusLabel.setText("OK");
		
		progressBar = new ProgressBar(composite, SWT.NONE);
		shell.open();
		shell.layout();
		init();
		return 0;
	}
	public void loadDataIntoSiteInfo(){
		
	}
	public void siteLanguagesChanged(){
		
	}
	public void languageChanged(){
		
	}
	public void siteInfoUpdated(){
		
	}
	public void setStatus(String text){
		statusLabel.setText(text);
	}
	public void init(){		
	}
	public void deploy(){		
	}
}
