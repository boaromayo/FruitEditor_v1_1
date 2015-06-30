package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.filechooser.*;
import java.io.*;

public class FruitFrame extends JFrame {
	// PANELS.	
	private FruitPanel fruitPanel;
	private JPanel toolbarPanel;
	
	private String[] menuName = {"File", "Edit", "View", "Draw", "FruitTools", "Help"};
	
	// MENU COMPONENTS: The Main Menu Bar
	private JMenuBar menuBar;
	
	// MENU COMPS: The Menus
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu viewMenu;
	private JMenu drawMenu;
	private JMenu toolMenu;
	private JMenu helpMenu;
	
	// MENU COMPS: The Sub-Menus
	private JMenu modeMenu;
	private JMenu scaleMenu;
	
	// MENU COMPS: The Menu Toolbar
	private JToolBar mainToolBar;
	
	// MENU COMPS: The Menu Groups
	private ButtonGroup scalegrp;
	private ButtonGroup modegrp;
	private ButtonGroup drawgrp;
	private ButtonGroup scaleBtnGrp;
	private ButtonGroup modeBtnGrp;
	private ButtonGroup drawBtnGrp;
	
	// MENU COMPS: Menu items
	// MENU: FILE
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem closeItem;
	// MENU: EDIT -> FIX
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	// MENU: EDIT
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	// MENU: VIEW -> SCALE
	private JRadioButtonMenuItem oneItem;
	private JRadioButtonMenuItem twoItem;
	private JRadioButtonMenuItem fourItem;
	private JRadioButtonMenuItem eightItem;
	// MENU: VIEW -> MODE
	private JRadioButtonMenuItem mapModeItem;
	private JRadioButtonMenuItem eventModeItem;
	// MENU: VIEW
	private JCheckBoxMenuItem gridItem;
	// MENU: DRAW
	private JRadioButtonMenuItem pencilItem;
	private JRadioButtonMenuItem rectItem;
	private JRadioButtonMenuItem circleItem;
	private JRadioButtonMenuItem fillItem;
	// MENU: TOOLKIT
	private JMenuItem databaseItem;
	private JMenuItem scriptItem;
	private JMenuItem resourceItem;
	private JMenuItem configItem;
	// MENU: HELP
	private JMenuItem aboutItem;
	
	// MENU COMPS: Tool buttons
	// FILE
	private JButton newBtn;
	private JButton openBtn;
	private JButton saveBtn;
	// EDIT
	private JButton cutBtn;
	private JButton copyBtn;
	private JButton pasteBtn;
	private JButton deleteBtn;
	// EDIT -> FIX
	private JButton undoBtn;
	private JButton redoBtn;
	// VIEW -> SCALE
	private JButton oneBtn;
	private JButton twoBtn;
	private JButton fourBtn;
	private JButton eightBtn;
	// VIEW -> MODE
	private JButton mapModeBtn;
	private JButton eventModeBtn;
	// VIEW
	private JToggleButton gridBtn;
	// DRAW
	private JButton pencilBtn;
	private JButton rectBtn;
	private JButton circleBtn;
	private JButton fillBtn;
	// FRUITTOOLS/TOOLKIT
	private JButton cherryBtn;
	private JButton orangeBtn;
	private JButton limeBtn;
	
	public FruitFrame() {
		setTitle("FruitEditor");
		setLayout(new BorderLayout());
		
		// Setup the editor menu
		menuSetup();
		
		// Setup the toolbar.
		toolbarSetup();
		
		// Initialize panels.
        	panelSetup();
		
        	pack();
		
 		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	setLocationRelativeTo(null);
        	setVisible(true);
    	}
  
    	private void panelSetup() {
		fruitPanel = new FruitPanel();
		toolbarPanel = new JPanel();
		
		add(fruitPanel, BorderLayout.CENTER);
		add(toolbarPanel, BorderLayout.NORTH);
    	}
	
	//================================
	// menuSetup() - Set up main menu and main panel.
	//================================
	private void menuSetup() {
		// Assign main menu bar.
		menuBar = new JMenuBar();
		
		// Assign the menus.
		fileMenu = new JMenu(menuName[0]);			// FILE
		editMenu = new JMenu(menuName[1]);			// EDIT
		viewMenu = new JMenu(menuName[2]);			// VIEW
		drawMenu = new JMenu(menuName[3]);			// DRAW
		toolMenu = new JMenu(menuName[4]);			// TOOLKIT
		helpMenu = new JMenu(menuName[5]);			// HELP
		
		// Disable other menus if no map is loaded.
		if (mapFile == null) {
			editMenu.setEnabled(false);
			viewMenu.setEnabled(false);
			drawMenu.setEnabled(false);
			toolMenu.setEnabled(false);
		}
		
		// Create menu shortcuts.	
		fileMenu.setMnemonic(menuName[0].charAt(0));
		editMenu.setMnemonic(menuName[1].charAt(0));
		viewMenu.setMnemonic(menuName[2].charAt(0));
		drawMenu.setMnemonic(menuName[3].charAt(0));
		toolMenu.setMnemonic(menuName[4].charAt(5)); // Make 'T' menu shortcut
			
		subSetup(); // Get and add the menus.
		
		// Set the menuBar for the frame.
		setJMenuBar(menuBar);
	}
	
	//==================================================
	// subSetup() - Get and add the methods that will be run for each menu.
	//==================================================
	private void subSetup() {
		fileSetup();	
		editSetup();
		viewSetup();
		drawSetup();
		toolSetup();
		helpSetup();
		
		// Add menus to menuBar.
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(viewMenu);
		menuBar.add(drawMenu);
		menuBar.add(toolMenu);
		menuBar.add(helpMenu);
	}
	
	//=========================================
	// Setup the menu items for each menu.
	//=========================================
	private void fileSetup() {
		// FILE MENU ITEMS
		newItem = new JMenuItem("New");				// FILE -> NEW
		openItem = new JMenuItem("Open");			// FILE -> OPEN
		saveItem = new JMenuItem("Save");			// FILE -> SAVE
		saveAsItem = new JMenuItem("Save As...");   // FILE -> SAVE AS
		closeItem = new JMenuItem("Close");			// FILE -> CLOSE
		
		// Add in FILE ActionListeners
		//newItem.addActionListener(new FruitListener());
		//openItem.addActionListener(new FruitListener());
		//saveItem.addActionListener(new FruitListener());
		//saveAsItem.addActionListener(new FruitListener());
		//closeItem.addActionListener(new FruitListener());
		
		// Add in accelerator keys.
		/*makeShortcut(newItem, KeyEvent.VK_N, "CTRL");
		makeShortcut(openItem, KeyEvent.VK_O, "CTRL");
		makeShortcut(saveItem, KeyEvent.VK_S, "CTRL");*/
		
		// Case for FILE -> SAVE AS
		if (mapFile == null)
			saveAsItem.setEnabled(false);
		
		// Add in components.
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		// FILE SEPARATOR.
		fileMenu.addSeparator();
		// Add in components.
		fileMenu.add(closeItem);
	}
	
	private void editSetup() {
		// EDIT MENU ITEMS
		// EDIT -> FIX
		undoItem = new JMenuItem("Undo");			// EDIT -> UNDO
		redoItem = new JMenuItem("Redo");			// EDIT -> REDO
		// EDIT
		cutItem = new JMenuItem("Cut");				// EDIT -> CUT
		copyItem = new JMenuItem("Copy");			// EDIT -> COPY
		pasteItem = new JMenuItem("Paste");			// EDIT -> PASTE
		deleteItem = new JMenuItem("Delete");		// EDIT -> DELETE
		
		// Add in EDIT ActionListeners.
		/*undoItem.addActionListener(new FruitListener());
		redoItem.addActionListener(new FruitListener());
		cutItem.addActionListener(new FruitListener());
		copyItem.addActionListener(new FruitListener());
		pasteItem.addActionListener(new FruitListener());
		deleteItem.addActionListener(new FruitListener());*/
		
		// Add in accelerator keys.
		/*makeShortcut(undoItem, KeyEvent.VK_Z, "CTRL");
		makeShortcut(redoItem, KeyEvent.VK_Y, "CTRL");
		makeShortcut(cutItem, KeyEvent.VK_X, "CTRL");
		makeShortcut(copyItem, KeyEvent.VK_C, "CTRL");
		makeShortcut(pasteItem, KeyEvent.VK_V, "CTRL");
		makeShortcut(redoItem, KeyEvent.VK_DELETE);*/
		
		// Add in components.
		editMenu.add(undoItem);
		editMenu.add(redoItem);
		// FILE SEPARATOR.
		editMenu.addSeparator();
		// Add in components.
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.add(deleteItem);
	}
	
	private void viewSetup() {
		// VIEW SUB-METHODS AND MENU ITEMS
		scaleSetup();
		modeSetup();
		// VIEW
		gridItem = new JCheckBoxMenuItem("Show/Hide Grid");			// VIEW -> GRID
		
		// Add in VIEW ActionListeners.
		//gridItem.addActionListener(new FruitListener());
		
		// Set in default selected buttons.
		scalegrp.setSelected(oneItem, true);
		modegrp.setSelected(mapModeItem, true);
		gridItem.setState(true);
		
		// Add in components.
		viewMenu.add(scaleMenu);
		// MENU SEPARATOR.
		viewMenu.addSeparator();
		// Add in components.
		viewMenu.add(modeMenu);
		// MENU SEPARATOR.
		viewMenu.addSeparator();
		// Add in component.
		viewMenu.add(gridItem);
	}
	
	private void scaleSetup() {
		// VIEW -> SCALE SUB-MENU AND MENU ITEMS
		scaleMenu = new JMenu("Scale...");
		// VIEW -> SCALE BUTTONGROUP
		scalegrp = new ButtonGroup();
		// VIEW -> SCALE
		oneItem = new JRadioButtonMenuItem("1:1");					// VIEW -> SCALE -> 1:1
		twoItem = new JRadioButtonMenuItem("1:2");					// VIEW -> SCALE -> 1:2
		fourItem = new JRadioButtonMenuItem("1:4");					// VIEW -> SCALE -> 1:4
		eightItem = new JRadioButtonMenuItem("1:8");				// VIEW -> SCALE -> 1:8
		
		// Add VIEW -> SCALE items to group.
		scalegrp.add(oneItem);
		scalegrp.add(twoItem);
		scalegrp.add(fourItem);
		scalegrp.add(eightItem);
		
		// Add in VIEW -> SCALE ActionListeners.
		//oneItem.addActionListener(new FruitListener());
		//twoItem.addActionListener(new FruitListener());
		//fourItem.addActionListener(new FruitListener());
		//eightItem.addActionListener(new FruitListener());
		
		// Add in sub-menu components.
		scaleMenu.add(oneItem);
		scaleMenu.add(twoItem);
		scaleMenu.add(fourItem);
		scaleMenu.add(eightItem);
	}
	
	private void modeSetup() {
		// VIEW -> MODE SUB-MENU AND MENU ITEMS
		modeMenu = new JMenu("Mode...");
		// VIEW -> MODE BUTTONGROUP
		modegrp = new ButtonGroup();
		// VIEW -> MODE
		mapModeItem = new JRadioButtonMenuItem("Map Mode");		// VIEW -> MODE -> MAP MODE
		eventModeItem = new JRadioButtonMenuItem("Event Mode");		// VIEW -> MODE -> EVENT MODE
		
		// Add in VIEW -> MODE items to group.
		modegrp.add(mapModeItem);
		modegrp.add(eventModeItem);
		
		// Add in VIEW -> MODE ActionListeners.
		//mapModeItem.addActionListener(new FruitListener());
		//eventModeItem.addActionListener(new FruitListener());
		
		// Add in sub-menu components.
		modeMenu.add(mapModeItem);
		modeMenu.add(eventModeItem);
	}
	
	private void drawSetup() {
		// DRAW BUTTONGROUP AND MENU ITEMS
		drawgrp = new ButtonGroup();
		// DRAW MENU ITEMS
		pencilItem 	= new JRadioButtonMenuItem("Pencil");		// DRAW -> PENCIL
		rectItem   	= new JRadioButtonMenuItem("Rectangle");	// DRAW -> RECTANGLE
		circleItem	= new JRadioButtonMenuItem("Circle");		// DRAW -> CIRCLE
		fillItem	= new JRadioButtonMenuItem("Flood Fill");	// DRAW -> FILL
		
		// Add in DRAW ActionListeners.
		/*pencilItem.addActionListener(new FruitListener());
		rectItem.addActionListener(new FruitListener());
		circleItem.addActionListener(new FruitListener());
		fillItem.addActionListener(new FruitListener());*/
		
		// Add DRAW items to group.
		drawgrp.add(pencilItem);
		drawgrp.add(rectItem);
		drawgrp.add(circleItem);
		drawgrp.add(fillItem);
		// Set in default selected buttons.
		drawgrp.setSelected(pencilItem, true);
		
		// Add in menu components.
		drawMenu.add(pencilItem);
		drawMenu.add(rectItem);
		drawMenu.add(circleItem);
		drawMenu.add(fillItem);
	}
	
	private void toolSetup() {
		// TOOLKIT MENU ITEMS
		databaseItem	= new JMenuItem("Cherry DataBase");		// TOOLKIT -> CHERRY DATABASE
		scriptItem	= new JMenuItem("Orange ScriptMaker");		// TOOLKIT -> ORANGE SCRIPTBASE
		resourceItem	= new JMenuItem("Lime ResourceBase");		// TOOLKIT -> LIME RESOURCEBASE
		configItem	= new JMenuItem("Settings...");			// TOOLKIT -> SETTINGS
		
		// Add in TOOLKIT ActionListeners.
		//databaseItem.addActionListener(new FruitListener());
		//scriptItem.addActionListener(new FruitListener());
		//resourceItem.addActionListener(new FruitListener());
		//configItem.addActionListener(new FruitListener());
		
		// Add in menu components.
		toolMenu.add(databaseItem);
		toolMenu.add(scriptItem);
		toolMenu.add(resourceItem);
		// MENU SEPARATOR.
		toolMenu.addSeparator();
		// Add in menu components.
		toolMenu.add(configItem);
	}
	
	private void helpSetup() {
		// HELP MENU ITEMS
		aboutItem = new JMenuItem("About...");			// HELP -> ABOUT
		
		// Add in HELP ActionListener.
		//aboutItem.addActionListener(new FruitListener());
		
		// Add in menu components.
		helpMenu.add(aboutItem);
	}
	
	//=========================================
	// Setup the tool buttons for toolbar.
	//=========================================
	private void toolbarSetup() {
		// Initialize toolbar.
		mainToolBar = new JToolBar();
		mainToolBar.setRollover(true);		// Make an indication if mouse goes over toolbar buttons
		mainToolBar.setFloatable(false);	//
		
		// Create and add the buttons in toolbar.
		subToolbarSetup();
		
		// add toolbar to toolbarPanel.
		toolbarPanel.add(mainToolBar, BorderLayout.CENTER);
	}
	
	//=========================================
	// Add in the tool buttons for toolbar.
	//=========================================
	private void subToolbarSetup() {
		fileToolSetup();
		editToolSetup();
		fixToolSetup();
		viewToolSetup();
		drawToolSetup();
		toolkitToolSetup();
	}
	
	//=========================================
	// Setup the buttons for each toolbar section.
	//=========================================
	private void fileToolSetup() {
		// FILE BUTTONS
		newBtn 		= makeButton("N", "New");
		openBtn 	= makeButton("O", "Open");
		saveBtn		= makeButton("S", "Save");
		
		// Add in FILE buttons.
		mainToolBar.add(newBtn);						// FILE -> NEW
		mainToolBar.add(openBtn);						// FILE -> OPEN
		mainToolBar.add(saveBtn);						// FILE -> SAVE
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void editToolSetup() {
		// EDIT BUTTONS
		cutBtn		= makeButton("Cut", "Cut");				// EDIT -> CUT
		copyBtn		= makeButton("Copy", "Copy");			// EDIT -> COPY
		pasteBtn	= makeButton("Paste", "Paste");			// EDIT -> PASTE
		deleteBtn	= makeButton("X", "Delete");			// EDIT -> DELETE
		
		// Add in EDIT buttons.
		mainToolBar.add(cutBtn);
		mainToolBar.add(copyBtn);
		mainToolBar.add(pasteBtn);
		mainToolBar.add(deleteBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void fixToolSetup() {
		// EDIT -> FIX BUTTONS
		undoBtn		= makeButton("U", "Undo");			// EDIT -> FIX -> UNDO
		redoBtn		= makeButton("R", "Redo");			// EDIT -> FIX -> REDO
		
		// Add in EDIT -> FIX buttons.
		mainToolBar.add(undoBtn);
		mainToolBar.add(redoBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void viewToolSetup() {
		// VIEW SUB-METHODS
		scaleToolSetup();
		modeToolSetup();
		// VIEW BUTTONS
		gridBtn = makeButton("G", "", "Show/Hide Grid", true);
	
		// Add in VIEW button.
		mainToolBar.add(gridBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void scaleToolSetup() {
		// SCALE BUTTONGROUP
		scaleBtnGrp	= new ButtonGroup();
		// SCALE BUTTONS
		oneBtn		= makeButton("1:1", "", "Scale 1:1", true);
		twoBtn		= makeButton("1:2", "", "Scale 1:2", true);
		fourBtn		= makeButton("1:4", "", "Scale 1:4", true);
		eightBtn	= makeButton("1:8", "", "Scale 1:8", true);
		
		// Add in SCALE buttons to group.
		scaleBtnGrp.add(oneBtn);
		scaleBtnGrp.add(twoBtn);
		scaleBtnGrp.add(fourBtn);
		scaleBtnGrp.add(eightBtn);
		// Set 1:1 to default
		scaleBtnGrp.setSelected(oneBtn);
		
		// Add in SCALE buttons.
		mainToolBar.add(oneBtn);
		mainToolBar.add(twoBtn);
		mainToolBar.add(fourBtn);
		mainToolBar.add(eightBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void modeToolSetup() {
		// MODE BUTTONGROUP
		modeBtnGrp = new ButtonGroup();
		// MODE BUTTONS
		mapModeBtn	= makeButton("MAP", "", "Map Mode", true);
		eventModeBtn 	= makeButton("EV", "", "Event Mode", true);
		
		// Add in MODE buttons to group.
		modeBtnGrp.add(mapModeBtn);
		modeBtnGrp.add(eventModeBtn);
		// Set map mode to default.
		modeBtnGrp.setSelected(mapModeBtn);
		
		// Add in MODE buttons.
		mainToolBar.add(mapModeBtn);
		mainToolBar.add(eventModeBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void drawToolSetup() {
		// DRAW BUTTONGROUP
		drawBtnGrp	= new ButtonGroup();
		// DRAW BUTTONS
		pencilBtn 	= makeButton("Penc", "", "Pencil", true);
		rectBtn		= makeButton("Rect", "", "Rectangle", true);
		circleBtn	= makeButton("Circ", "", "Circle", true);
		fillBtn		= makeButton("Fill", "", "Flood Fill", true);
		
		// Add in DRAW buttons to group.
		drawBtnGrp.add(pencilBtn);
		drawBtnGrp.add(rectBtn);
		drawBtnGrp.add(circleBtn);
		drawBtnGrp.add(fillBtn);
		// Set pencil mode to default.
		drawBtnGrp.setSelected(pencilBtn);
		
		// Add in DRAW buttons.
		mainToolBar.add(pencilBtn);
		mainToolBar.add(rectBtn);
		mainToolBar.add(circleBtn);
		mainToolBar.add(fillBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void toolkitTools() {
		// TOOLKIT BUTTONS
		cherryBtn	= makeButton("Cherry", "Cherry DataBase");
		orangeBtn	= makeButton("Orange", "Orange ScriptMaker");
		limeBtn		= makeButton("Lime", "Lime ResourceBase");
		
		// Add in TOOLKIT buttons.
		mainToolBar.add(cherryBtn);
		mainToolBar.add(orangeBtn);
		mainToolBar.add(limeBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	/**========================================
	// HELPER METHODS.
	//=========================================**/
	//=========================================
	// makeButton(text,tooltip) - Make text button for the toolbar.
	//=========================================
	private AbstractButton makeButton(String text, String tooltip) {
		return makeButton(text, "", tooltip, false);
	}
	
	//=========================================
	// makeButton(text,icon,tooltip) - Make button for the toolbar.
	//=========================================
	private AbstractButton makeButton(String text, String icon, String tooltip) {
		return makeButton(text, icon, tooltip, false);
	}
	
	//=========================================
	// makeButton(text,icon,tooltip,toggle) - Make button for the toolbar.
	//=========================================
	private AbstractButton makeButton(String text, String icon, String tooltip, boolean toggle) {
		AbstractButton btn = null;
		// Branch on whether this is a toggle btn or not.
		if (toggle) {
			/*try {
				//btn = new JToggleButton(new FruitImgLoader(icon));
			} catch (Exception e) {
				btn = new JToggleButton(text);
			}*/
			// Add in button.
			btn = new JToggleButton(text);
			btn.setToolTipText(tooltip);
		
			// Add button ActionListener.
			//btn.addActionListener(new FruitListener());
		} else {
			/*try {
				//btn = new JButton(new FruitImgLoader(icon));
			} catch (Exception e) {
				btn = new JButton(text);
			}*/
			// Add in button.
			btn = new JButton(text);
			btn.setToolTipText(tooltip);
		
			// Add button ActionListener.
			//btn.addActionListener(new FruitListener());
		}
		
		return btn;
	}
	
	//=========================================
	// makeShortcut(menu,key) - Make menu accelerator shortcut.
	//=========================================
	private void makeShortcut(JMenuItem menu, KeyEvent key) {
		makeShortcut(menu, key, "");
	}
	
	//=========================================
	// makeShortcut(menu,key,mask) - Make menu accelerator shortcut + key mask.
	//=========================================
	private void makeShortcut(JMenuItem menu, KeyEvent key, String mask) {
		// Get KeyStroke for key and add in accelerator
		KeyStroke k = KeyStroke.getKeyStroke(key);
		if (mask.equals("CTRL")) {
			try {
				menu.setAccelerator(k, ActionEvent.CTRL_MASK);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		} else if (mask.equals("ALT")) {
			try {
				menu.setAccelerator(k, ActionEvent.ALT_MASK);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		} else {
			try {
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		}
	}
}
