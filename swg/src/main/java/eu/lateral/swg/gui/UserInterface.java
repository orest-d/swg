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
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

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
	public Text text_1;
	public Text text_2;
	public Text text_3;
	public Text text_4;
	public Combo selectArticleCombo;
	public Text articleTitleText;
	public Text articleLinkText;
	public StyledText articleText;
	public Text text_7;
	public Text text_8;
	public Text text_9;
	public Text text_10;
	public Text text_11;
	public Text text_12;
	public Text text_13;
	public Text text_14;
	public Text text_15;
	public Text text_16;
	public Table table;

	/**
	 * @wbp.parser.entryPoint
	 */
	public int createUI() {
		Display display = new Display();
		Shell mainShell = new Shell(display, SWT.RESIZE | SWT.TITLE);
		mainShell.setSize(670, 488);
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
		GridData gd_languageCombo = new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1);
		gd_languageCombo.widthHint = 202;
		languageCombo.setLayoutData(gd_languageCombo);
		languageCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				languageChanged();
			}
		});
		
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
		composite_1.setLayout(new GridLayout(3, false));
		
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
		new Label(composite_1, SWT.NONE);
		
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
		new Label(composite_1, SWT.NONE);
		
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
		new Label(composite_1, SWT.NONE);
		
		Label lblSiteLanguages = new Label(composite_1, SWT.NONE);
		lblSiteLanguages.setText("Site languages");
		
		siteLanguagesList = new List(composite_1, SWT.BORDER | SWT.MULTI);
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
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		
		Button btnOk = new Button(composite_1, SWT.NONE);
		btnOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				siteInfoUpdated();
			}
		});
		GridData gd_btnOk = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnOk.widthHint = 94;
		btnOk.setLayoutData(gd_btnOk);
		btnOk.setText("OK");
		
		TabItem tbtmArticle = new TabItem(tabFolder, SWT.NONE);
		tbtmArticle.setText("Article");
		
		Composite composite_3 = new Composite(tabFolder, SWT.NONE);
		tbtmArticle.setControl(composite_3);
		composite_3.setLayout(new GridLayout(4, false));
		
		Button newArticleButton = new Button(composite_3, SWT.NONE);
		newArticleButton.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		newArticleButton.setText("New");
		
		Button deleteArticleButton = new Button(composite_3, SWT.NONE);
		deleteArticleButton.setText("Delete");
		
		selectArticleCombo = new Combo(composite_3, SWT.NONE);
		selectArticleCombo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				articleChanged();
			}
		});
		selectArticleCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblTitle_1 = new Label(composite_3, SWT.NONE);
		lblTitle_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle_1.setText("Title:");
		
		articleTitleText = new Text(composite_3, SWT.BORDER);
		articleTitleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		
		Label lblMenuLink = new Label(composite_3, SWT.NONE);
		lblMenuLink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblMenuLink.setText("Menu link:");
		
		articleLinkText = new Text(composite_3, SWT.BORDER);
		GridData gd_articleLinkText = new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1);
		gd_articleLinkText.widthHint = 164;
		articleLinkText.setLayoutData(gd_articleLinkText);
		
		Button articleShowInMenuButton = new Button(composite_3, SWT.CHECK);
		articleShowInMenuButton.setText("Show in menu");
		
		articleText = new StyledText(composite_3, SWT.BORDER);
		GridData gd_articleText = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		gd_articleText.widthHint = 509;
		articleText.setLayoutData(gd_articleText);
		
		TabItem tbtmImage = new TabItem(tabFolder, SWT.NONE);
		tbtmImage.setText("Image");
		
		Composite composite_4 = new Composite(tabFolder, SWT.NONE);
		tbtmImage.setControl(composite_4);
		composite_4.setLayout(new GridLayout(3, false));
		
		List list_1 = new List(composite_4, SWT.BORDER);
		list_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 7));
		
		Label lblTitle_2 = new Label(composite_4, SWT.NONE);
		lblTitle_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle_2.setText("Title:");
		
		text_9 = new Text(composite_4, SWT.BORDER);
		text_9.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblAuthor = new Label(composite_4, SWT.NONE);
		lblAuthor.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblAuthor.setText("Author:");
		
		text_13 = new Text(composite_4, SWT.BORDER);
		text_13.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblTechnique = new Label(composite_4, SWT.NONE);
		lblTechnique.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTechnique.setText("Technique:");
		
		Combo combo_1 = new Combo(composite_4, SWT.NONE);
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblWidth_2 = new Label(composite_4, SWT.NONE);
		lblWidth_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWidth_2.setText("Width:");
		
		text_10 = new Text(composite_4, SWT.BORDER);
		text_10.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight_2 = new Label(composite_4, SWT.NONE);
		lblHeight_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHeight_2.setText("Height:");
		
		text_11 = new Text(composite_4, SWT.BORDER);
		text_11.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblInception = new Label(composite_4, SWT.NONE);
		lblInception.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblInception.setText("Inception:");
		
		text_12 = new Text(composite_4, SWT.BORDER);
		text_12.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Canvas canvas = new Canvas(composite_4, SWT.NONE);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		
		TabItem tbtmImageSize = new TabItem(tabFolder, SWT.NONE);
		tbtmImageSize.setText("Image Attributes");
		
		Composite composite_2 = new Composite(tabFolder, SWT.NONE);
		tbtmImageSize.setControl(composite_2);
		composite_2.setLayout(new GridLayout(6, false));
		
		Label lblThumbnails = new Label(composite_2, SWT.NONE);
		lblThumbnails.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblThumbnails.setText("Thumbnails:");
		
		Label label_1 = new Label(composite_2, SWT.SEPARATOR | SWT.VERTICAL);
		GridData gd_label_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 7);
		gd_label_1.widthHint = -52;
		label_1.setLayoutData(gd_label_1);
		
		Label lblNewLabel = new Label(composite_2, SWT.NONE);
		lblNewLabel.setText("Techniques:");
		new Label(composite_2, SWT.NONE);
		new Label(composite_2, SWT.NONE);
		
		Label lblWidth = new Label(composite_2, SWT.NONE);
		lblWidth.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWidth.setText("Width:");
		
		text_1 = new Text(composite_2, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblNewLabel_1 = new Label(composite_2, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("Key:");
		
		text_7 = new Text(composite_2, SWT.BORDER);
		text_7.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblHeight = new Label(composite_2, SWT.NONE);
		lblHeight.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHeight.setText("Height:");
		
		text_2 = new Text(composite_2, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblName = new Label(composite_2, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Name:");
		
		text_8 = new Text(composite_2, SWT.BORDER);
		text_8.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label label = new Label(composite_2, SWT.SEPARATOR | SWT.HORIZONTAL);
		label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		new Label(composite_2, SWT.NONE);
		
		Button btnNew_1 = new Button(composite_2, SWT.NONE);
		btnNew_1.setText("New");
		
		Button btnDelete = new Button(composite_2, SWT.NONE);
		btnDelete.setText("Delete");
		
		Label lblImage = new Label(composite_2, SWT.NONE);
		lblImage.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		lblImage.setText("Image:");
		
		List list = new List(composite_2, SWT.BORDER);
		list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 3));
		
		Label lblWidth_1 = new Label(composite_2, SWT.NONE);
		lblWidth_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblWidth_1.setText("Width:");
		
		text_3 = new Text(composite_2, SWT.BORDER);
		text_3.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		Label lblHeight_1 = new Label(composite_2, SWT.NONE);
		lblHeight_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblHeight_1.setText("Height:");
		
		text_4 = new Text(composite_2, SWT.BORDER);
		text_4.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		
		TabItem tbtmMenu = new TabItem(tabFolder, SWT.NONE);
		tbtmMenu.setText("Menu");
		
		Composite composite_5 = new Composite(tabFolder, SWT.NONE);
		tbtmMenu.setControl(composite_5);
		composite_5.setLayout(new GridLayout(5, false));
		
		table = new Table(composite_5, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4);
		gd_table.widthHint = 173;
		gd_table.heightHint = 230;
		table.setLayoutData(gd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnLink = new TableColumn(table, SWT.NONE);
		tblclmnLink.setWidth(100);
		tblclmnLink.setText("Link");
		
		TableColumn tblclmnType = new TableColumn(table, SWT.NONE);
		tblclmnType.setWidth(100);
		tblclmnType.setText("Type");
		
		Browser browser = new Browser(composite_5, SWT.NONE);
		GridData gd_browser = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 4);
		gd_browser.heightHint = 264;
		gd_browser.widthHint = 170;
		browser.setLayoutData(gd_browser);
		
		Label lblTitle_3 = new Label(composite_5, SWT.NONE);
		lblTitle_3.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle_3.setText("Title:");
		
		text_14 = new Text(composite_5, SWT.BORDER);
		text_14.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		
		Label lblLevel = new Label(composite_5, SWT.NONE);
		lblLevel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLevel.setText("Level:");
		
		Spinner spinner = new Spinner(composite_5, SWT.BORDER);
		GridData gd_spinner = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_spinner.widthHint = 118;
		spinner.setLayoutData(gd_spinner);
		
		Button btnUp = new Button(composite_5, SWT.NONE);
		GridData gd_btnUp = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnUp.widthHint = 83;
		btnUp.setLayoutData(gd_btnUp);
		btnUp.setText("Up");
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		Button btnDown = new Button(composite_5, SWT.NONE);
		GridData gd_btnDown = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnDown.widthHint = 83;
		btnDown.setLayoutData(gd_btnDown);
		btnDown.setText("Down");
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		new Label(composite_5, SWT.NONE);
		
		TabItem tbtmGallery = new TabItem(tabFolder, SWT.NONE);
		tbtmGallery.setText("Gallery");
		
		Composite composite_6 = new Composite(tabFolder, SWT.NONE);
		tbtmGallery.setControl(composite_6);
		composite_6.setLayout(new GridLayout(2, false));
		
		Label lblTitle_4 = new Label(composite_6, SWT.NONE);
		lblTitle_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblTitle_4.setText("Title:");
		
		text_15 = new Text(composite_6, SWT.BORDER);
		text_15.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblLink = new Label(composite_6, SWT.NONE);
		lblLink.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblLink.setText("Link:");
		
		text_16 = new Text(composite_6, SWT.BORDER);
		text_16.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		statusLabel = new Label(composite, SWT.NONE);
		statusLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
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
	public void articleChanged(){
		
	}
	public void setStatus(String text){
		statusLabel.setText(text);
	}
	public void init(){		
	}
	public void deploy(){		
	}
}
