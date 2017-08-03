package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;

import java.util.*;

public class FruitEditor {
	// CONSTANTS.
	public static final int SCREEN_WIDTH = 960;
	public static final int SCREEN_HEIGHT = 640;
	public static final int FPS = 60;
	public static final int GRID_SIZE = 32;
	public static final int SCROLL_SPEED = 20; // for tile and map scroll panes
	
	// MAP ACTIVE.
	private boolean panelActive = false;
	
	// MAIN FRAME.
	private JFrame fruitFrame;
	
	// PANELS.
	private FruitPanel fruitPanel;
	private JPanel toolbarPanel;
	private StatusPanel statusPanel;
	
	// MENU NAMES.
	private String[] menuName = {"File", "Edit", "View", "Draw", "FruitTools", "Help"};
	
	// EVENT LISTENER.
	private FruitListener fruitListener;
	
	// HISTORY.
	private UndoManager undoManager;
	
	// ACTIVE FILE.
	private File activeFile;
	
	// MAP.
	private Map map;
	
	// TILESET.
	private Tileset tileset;
	
	// HASH MAP FOR COMPONENTS.
	private HashMap<String, JComponent> hash;
	
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
	private JRadioButtonMenuItem lineItem;
	private JRadioButtonMenuItem rectItem;
	private JRadioButtonMenuItem circleItem;
	private JRadioButtonMenuItem fillItem;
	// MENU: TOOLKIT
	//private JMenuItem databaseItem;
	//private JMenuItem mapConvertItem;
	//private JMenuItem resourceItem;
	//private JMenuItem configItem;
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
	private JToggleButton oneBtn;
	private JToggleButton twoBtn;
	private JToggleButton fourBtn;
	private JToggleButton eightBtn;
	// VIEW -> MODE
	private JToggleButton mapModeBtn;
	private JToggleButton eventModeBtn;
	// VIEW
	private JToggleButton gridBtn;
	// SHIFT
	private JButton shiftBtn;
	// DRAW
	private JToggleButton pencilBtn;
	private JToggleButton lineBtn;
	private JToggleButton rectBtn;
	private JToggleButton circleBtn;
	private JToggleButton fillBtn;
	
	// FRUITTOOLS/TOOLKIT
	//private JButton cherryBtn;
	//private JButton orangeBtn;
	//private JButton limeBtn;
	
	public FruitEditor() {
		fruitFrame = new JFrame();
		
		fruitFrame.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		fruitFrame.setLayout(new BorderLayout());
		
		hash = new HashMap<String, JComponent>();
		
		activeFile = null;
		
		map = new Map();
		tileset = new Tileset();
		
		// Initialize panels.
		panelSetup();
		
		// Initialize event listener.
		fruitListener = new FruitListener(this);
		
		// Initialize undo/redo history.
		undoManager = new UndoManager();
				
		// Setup the editor menu.
		menuSetup();
		
		// Setup the toolbar.
		toolbarSetup();
		
		// Disable the menus and tools.
		toggleTools(panelActive);
		toggleSave(panelActive);
		toggleMenus(panelActive);
		toggleUndo(undoable());
		toggleRedo(redoable());
		
		fruitFrame.pack();
		
		fruitFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		fruitFrame.setLocationRelativeTo(null);
		fruitFrame.addWindowListener(fruitListener);
		//fruitFrame.setIconImage(FruitImgBank.get().loadBufferedImage("../img/icons/title-icon.gif", 0, 0, 20, 20));
		fruitFrame.setResizable(false);
		fruitFrame.setVisible(true);
	}
	
	/* TODO: May get rid of this since it's not needed. 
	 * This is not a game-based app, where the run() loop is called every time. */
	/*public void run() {
		long startTime, diffTime;
		
		long targetTime = 1000 / FruitEditor.FPS;
		
		long waitTime;
		//long elapsedTime = 0;
		
		//int frameCount = 0;
		//int maxFrameCount = FruitEditor.FPS;
		
		try {
			while (true) {
				startTime = System.nanoTime();
				
				update();
				
				diffTime = (System.nanoTime() - startTime) / 1000000;
				waitTime = targetTime - diffTime;
				
				if (waitTime < 0) {
					waitTime = targetTime;
				}
				
				Thread.sleep(waitTime);
				
				//elapsedTime += System.nanoTime() - startTime;
				
				//frameCount++;
				
				//if (frameCount == maxFrameCount) {
					//frameCount = 0;
					//elapsedTime = 0;
				//}
			}
		} catch (Exception e) {
			System.err.println("ERROR: Cannot open the panels properly. Reason: " +
					e.getMessage());
			e.getStackTrace();
			System.exit(1);
		}
	}
	
	public void addNotify() {
		if (t == null) {
			t = new Thread();
		}
	}*/
	
	/**================================
	// panelSetup() - Set up main panels.
	//================================**/
	private void panelSetup() {
	    fruitPanel = new FruitPanel(this);
	    statusPanel = new StatusPanel(this);
	    
	    fruitFrame.add(fruitPanel, BorderLayout.CENTER);
	    fruitFrame.add(statusPanel, BorderLayout.SOUTH);
	}
	
	/**================================
	// menuSetup() - Set up main menus and menu toolbar.
	//================================**/
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
			
		// Create menu shortcuts.
		fileMenu.setMnemonic(menuName[0].charAt(0));
		editMenu.setMnemonic(menuName[1].charAt(0));
		viewMenu.setMnemonic(menuName[2].charAt(0));
		drawMenu.setMnemonic(menuName[3].charAt(0));
		toolMenu.setMnemonic(menuName[4].charAt(5)); // Make 'T' menu shortcut
				
		subSetup(); // Get and add the menus.
			
		// Set the menuBar for the frame.
		fruitFrame.setJMenuBar(menuBar);
	}
	
	/**==================================================
	// toggleMenus() - Disable or enable the menus if active.
	//==================================================**/
	public void toggleMenus(boolean act) {
		editMenu.setEnabled(act);
		viewMenu.setEnabled(act);
		drawMenu.setEnabled(act);
		toolMenu.setEnabled(act);
	}
		
	/**==================================================
	// subSetup() - Get and add the methods that will be run for each menu.
	//==================================================**/
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
		newItem.addActionListener(fruitListener);
		openItem.addActionListener(fruitListener);
		saveItem.addActionListener(fruitListener);
		saveAsItem.addActionListener(fruitListener);
		closeItem.addActionListener(fruitListener);
		
		// Add in accelerator keys.
		makeShortcut(newItem, KeyEvent.VK_N, "CTRL");
		makeShortcut(openItem, KeyEvent.VK_O, "CTRL");
		makeShortcut(saveItem, KeyEvent.VK_S, "CTRL");
		
		// Set names for components.
		newItem.setName("newItem");
		openItem.setName("openItem");
		saveItem.setName("saveItem");
		saveAsItem.setName("saveAsItem");
		closeItem.setName("closeItem");
		
		// Add in components.
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		// FILE SEPARATOR.
		fileMenu.addSeparator();
		// Add in components.
		fileMenu.add(closeItem);
		
		// Add components to hash map.
		hash.put(newItem.getName(), newItem);
		hash.put(openItem.getName(), openItem);
		hash.put(saveItem.getName(), saveItem);
		hash.put(saveAsItem.getName(), saveAsItem);
		hash.put(closeItem.getName(), closeItem);
	}
	
	public void toggleSave(boolean act) {
		saveItem.setEnabled(act);
		saveAsItem.setEnabled(act);
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
		undoItem.addActionListener(fruitListener);
		redoItem.addActionListener(fruitListener);
		/*cutItem.addActionListener(fruitListener);
		copyItem.addActionListener(fruitListener);
		pasteItem.addActionListener(fruitListener);
		deleteItem.addActionListener(fruitListener);*/
		
		// Add in accelerator keys.
		makeShortcut(undoItem, KeyEvent.VK_Z, "CTRL");
		makeShortcut(redoItem, KeyEvent.VK_Y, "CTRL");
		makeShortcut(cutItem, KeyEvent.VK_X, "CTRL");
		makeShortcut(copyItem, KeyEvent.VK_C, "CTRL");
		makeShortcut(pasteItem, KeyEvent.VK_V, "CTRL");
		makeShortcut(deleteItem, KeyEvent.VK_DELETE);
		
		// Set names for components.
		undoItem.setName("undoItem");
		redoItem.setName("redoItem");
		cutItem.setName("cutItem");
		copyItem.setName("copyItem");
		pasteItem.setName("pasteItem");
		deleteItem.setName("deleteItem");
		
		// Add edits into actionmap.
		//((JPanel))
		
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
		
		// Add components to hashmap.
		hash.put(undoItem.getName(), undoItem);
		hash.put(redoItem.getName(), redoItem);
		hash.put(cutItem.getName(), cutItem);
		hash.put(copyItem.getName(), copyItem);
		hash.put(pasteItem.getName(), pasteItem);
		hash.put(deleteItem.getName(), deleteItem);
	}
	
	private void viewSetup() {
		// VIEW SUB-METHODS AND MENU ITEMS
		scaleSetup();
		modeSetup();
		// VIEW
		gridItem = new JCheckBoxMenuItem("Show/Hide Grid");			// VIEW -> GRID
		
		// Add in VIEW ActionListeners.
		gridItem.addActionListener(fruitListener);

		// Set grid item if grid is on.
		//gridItem.setState(getMapPanel().gridOn());
		gridItem.setSelected(true);
		
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
		
		// Add component to hashmap.
		hash.put("gridItem", gridItem);
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
		
		// Set names for components.
		oneItem.setName("oneItem");
		twoItem.setName("twoItem");
		fourItem.setName("fourItem");
		eightItem.setName("eightItem");
		
		// Add VIEW -> SCALE items to group.
		scalegrp.add(oneItem);
		scalegrp.add(twoItem);
		scalegrp.add(fourItem);
		scalegrp.add(eightItem);
		
		// Add in VIEW -> SCALE ActionListeners.
		//oneItem.addActionListener(fruitMenuListener);
		//twoItem.addActionListener(fruitMenuListener);
		//fourItem.addActionListener(fruitMenuListener);
		//eightItem.addActionListener(fruitMenuListener);
		
		// Add in sub-menu components.
		scaleMenu.add(oneItem);
		scaleMenu.add(twoItem);
		scaleMenu.add(fourItem);
		scaleMenu.add(eightItem);
		
		// Set 1:1 as default.
		scalegrp.setSelected(oneItem.getModel(), true);
		
		// Add components to hashmap.
		hash.put(oneItem.getName(), oneItem);
		hash.put(twoItem.getName(), twoItem);
		hash.put(fourItem.getName(), fourItem);
		hash.put(eightItem.getName(), eightItem);
	}
	
	private void modeSetup() {
		// VIEW -> MODE SUB-MENU AND MENU ITEMS
		modeMenu = new JMenu("Mode...");
		// VIEW -> MODE BUTTONGROUP
		modegrp = new ButtonGroup();
		// VIEW -> MODE
		mapModeItem 	= new JRadioButtonMenuItem("Map Mode");			// VIEW -> MODE -> MAP MODE
		eventModeItem 	= new JRadioButtonMenuItem("Event Mode");		// VIEW -> MODE -> EVENT MODE
		
		// Set names for components.
		mapModeItem.setName("mapModeItem");
		eventModeItem.setName("eventModeItem");
		
		// Add in VIEW -> MODE items to group.
		modegrp.add(mapModeItem);
		modegrp.add(eventModeItem);
		
		// Add in VIEW -> MODE ActionListeners.
		mapModeItem.addActionListener(fruitListener);
		eventModeItem.addActionListener(fruitListener);
		
		// Add in sub-menu components.
		modeMenu.add(mapModeItem);
		modeMenu.add(eventModeItem);
		
		// Set map mode as default.
		modegrp.setSelected(mapModeItem.getModel(), true);
		
		// Add components to hashmap.
		hash.put(mapModeItem.getName(), mapModeItem);
		hash.put(eventModeItem.getName(), eventModeItem);
	}
	
	private void drawSetup() {
		// DRAW BUTTONGROUP AND MENU ITEMS
		drawgrp = new ButtonGroup();
		// DRAW MENU ITEMS
		pencilItem 	= new JRadioButtonMenuItem("Pencil");			// DRAW -> PENCIL
		lineItem    = new JRadioButtonMenuItem("Line");				// DRAW -> LINE
		rectItem   	= new JRadioButtonMenuItem("Rectangle");		// DRAW -> RECTANGLE
		circleItem	= new JRadioButtonMenuItem("Circle");			// DRAW -> CIRCLE
		fillItem	= new JRadioButtonMenuItem("Flood Fill");		// DRAW -> FILL
		
		// Add in DRAW ActionListeners.
		pencilItem.addActionListener(fruitListener);
		lineItem.addActionListener(fruitListener);
		rectItem.addActionListener(fruitListener);
		circleItem.addActionListener(fruitListener);
		fillItem.addActionListener(fruitListener);
		
		// Set names for components.
		pencilItem.setName("pencilItem");
		lineItem.setName("lineItem");
		rectItem.setName("rectItem");
		circleItem.setName("circleItem");
		fillItem.setName("fillItem");
		
		// Add DRAW items to group.
		drawgrp.add(pencilItem);
		drawgrp.add(lineItem);
		drawgrp.add(rectItem);
		drawgrp.add(circleItem);
		drawgrp.add(fillItem);
		
		// Add in menu components.
		drawMenu.add(pencilItem);
		drawMenu.add(lineItem);
		drawMenu.add(rectItem);
		drawMenu.add(circleItem);
		drawMenu.add(fillItem);
		
		// Set pencil mode as default.
		drawgrp.setSelected(pencilItem.getModel(), true);
		
		// Add components to hashmap.
		hash.put(pencilItem.getName(), pencilItem);
		hash.put(lineItem.getName(), lineItem);
		hash.put(rectItem.getName(), rectItem);
		hash.put(circleItem.getName(), circleItem);
		hash.put(fillItem.getName(), fillItem);
		
	}
	
	private void toolSetup() {
		// TOOLKIT MENU ITEMS
		//databaseItem	= new JMenuItem("Cherry DataBase");			// TOOLKIT -> CHERRY DATABASE
		//mapConvertItem	= new JMenuItem("Orange MapConverter");		// TOOLKIT -> ORANGE MAPCONVERT
		//resourceItem 	= new JMenuItem("Lime ResourceBase");		// TOOLKIT -> LIME RESOURCEBASE
		//configItem		= new JMenuItem("Settings...");				// TOOLKIT -> SETTINGS
		
		// Add in TOOLKIT ActionListeners.
		//databaseItem.addActionListener(fruitListener);
		//mapConvertItem.addActionListener(fruitListener);
		//resourceItem.addActionListener(fruitListener);
		//configItem.addActionListener(fruitListener);
		
		// Add in menu components.
		//toolMenu.add(databaseItem);
		//toolMenu.add(mapConvertItem);
		//toolMenu.add(resourceItem);
		// MENU SEPARATOR.
		//toolMenu.addSeparator();
		// Add in menu components.
		//toolMenu.add(configItem);
		
		// Add components to hashmap.
		//hash.put(databaseItem.getName(), databaseItem);
		//hash.put(mapConvertItem.getName(), mapConvertItem);
		//hash.put(resourceItem.getName(), resourceItem);
		//hash.put("configItem", configItem);
	}
	
	private void helpSetup() {
		// HELP MENU ITEMS
		aboutItem = new JMenuItem("About...");			// HELP -> ABOUT
		
		// Add in HELP ActionListener.
		aboutItem.addActionListener(fruitListener);
		
		// Add in menu components.
		helpMenu.add(aboutItem);
		
		// Add component to hashmap.
		hash.put("aboutItem", aboutItem);
	}
	
	/**=========================================
	// toolbarSetup() - Setup the toolbar buttons.
	//=========================================**/
	private void toolbarSetup() {
		// Initialize toolbar panel.
		toolbarPanel = new JPanel();
		toolbarPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// Initialize toolbar.
		mainToolBar = new JToolBar();
		mainToolBar.setRollover(true);		// Make an indication if mouse goes over toolbar buttons
		mainToolBar.setFloatable(false);	// Do not let it be movable
		
		// Create and add the buttons in toolbar.
		subToolbarSetup();
		
		// add toolbar to toolbarPanel.
		toolbarPanel.add(mainToolBar, BorderLayout.CENTER);
		
		// add toolbarPanel to frame.
		fruitFrame.add(toolbarPanel, BorderLayout.NORTH);
	}
	
	/**=========================================
	// toggleTools() - Enable tool buttons if panel active.
	//=========================================**/
	public void toggleTools(boolean act) {
		saveBtn.setEnabled(act);
		
		cutBtn.setEnabled(act);
		copyBtn.setEnabled(act);
		pasteBtn.setEnabled(act);
		deleteBtn.setEnabled(act);
	
		gridBtn.setEnabled(act);
		
		shiftBtn.setEnabled(act);
	
		oneBtn.setEnabled(act);
		twoBtn.setEnabled(act);
		fourBtn.setEnabled(act);
		eightBtn.setEnabled(act);
	
		mapModeBtn.setEnabled(act);
		eventModeBtn.setEnabled(act);
	
		pencilBtn.setEnabled(act);
		lineBtn.setEnabled(act);
		rectBtn.setEnabled(act);
		circleBtn.setEnabled(act);
		fillBtn.setEnabled(act);
		
		//cherryBtn.setEnabled(act);
		//orangeBtn.setEnabled(act);
		//limeBtn.setEnabled(act);
	}
	
	/**=========================================
	// toggleUndo() - Enable undo if stack has any saved actions.
	//==========================================**/
	public void toggleUndo(boolean act) {
		undoBtn.setEnabled(act);
	}
	
	/**=========================================
	// toggleRedo() - Enable redo if stack has any saved actions.
	//==========================================**/
	public void toggleRedo(boolean act) {
		redoBtn.setEnabled(act);
	}
	
	/**=========================================
	// subToolbarSetup() - Add in the tool buttons for toolbar.
	//=========================================**/
	private void subToolbarSetup() {
		fileToolSetup();
		editToolSetup();
		fixToolSetup();
		viewToolSetup();
		drawToolSetup();
		//toolkitToolSetup();
	}
	
	//=========================================
	// Setup the buttons for each toolbar section.
	//=========================================
	private void fileToolSetup() {
		// FILE BUTTONS
		newBtn 		= makeButton("N", "../img/newfile.png",
				"New", "newBtn");
		openBtn 	= makeButton("O", "../img/openfile.png",
				"Open", "openBtn");
		saveBtn		= makeButton("S", "../img/savefile.png",
				"Save", "saveBtn");
		
		// Add in FILE buttons.
		mainToolBar.add(newBtn);						// FILE -> NEW
		mainToolBar.add(openBtn);						// FILE -> OPEN
		mainToolBar.add(saveBtn);						// FILE -> SAVE

		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	private void editToolSetup() {
		// EDIT BUTTONS
		cutBtn		= makeButton("Cut", "Cut", "cutBtn");			// EDIT -> CUT
		copyBtn		= makeButton("Copy", "Copy", "copyBtn");		// EDIT -> COPY
		pasteBtn	= makeButton("Paste", "Paste", "pasteBtn");		// EDIT -> PASTE
		deleteBtn	= makeButton("X", "../img/delete.png", 
				"Delete", "deleteBtn");								// EDIT -> DELETE
		
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
		undoBtn		= makeButton("U", "../img/undo.png", 
									"Undo", "undoBtn");		// EDIT -> FIX -> UNDO
		redoBtn		= makeButton("R", "../img/redo.png", 
									"Redo", "redoBtn");		// EDIT -> FIX -> REDO
		
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
		gridBtn = makeButton("G", "", "Show/Hide Grid", 
				"gridBtn", true);
		shiftBtn = makeButton("Shift", "Shift Map", "shiftBtn");
	
		// Set VIEW button if grid is on.
		gridBtn.setSelected(true);
		
		// Add in GRID button.
		mainToolBar.add(gridBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
		
		// Add in SHIFT button.
		mainToolBar.add(shiftBtn);
		mainToolBar.addSeparator();
	}
	
	private void scaleToolSetup() {
		// SCALE BUTTONGROUP
		scaleBtnGrp	= new ButtonGroup();
		// SCALE BUTTONS
		oneBtn		= makeButton("1:1", "", "Scale 1:1", "oneBtn", true);
		twoBtn		= makeButton("1:2", "", "Scale 1:2", "twoBtn", true);
		fourBtn		= makeButton("1:4", "", "Scale 1:4", "fourBtn", true);
		eightBtn	= makeButton("1:8", "", "Scale 1:8", "eightBtn", true);
		
		// Add in SCALE buttons to group.
		scaleBtnGrp.add(oneBtn);
		scaleBtnGrp.add(twoBtn);
		scaleBtnGrp.add(fourBtn);
		scaleBtnGrp.add(eightBtn);
		
		// Set 1:1 as default
		scaleBtnGrp.setSelected(oneBtn.getModel(), true);
		
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
		mapModeBtn	 = makeButton("MAP", "", "Map Mode", "mapModeBtn", true);
		eventModeBtn = makeButton("EV", "", "Event Mode", "eventModeBtn", true);
		
		// Add in MODE buttons to group.
		modeBtnGrp.add(mapModeBtn);
		modeBtnGrp.add(eventModeBtn);
		
		// Set map mode as default.
		modeBtnGrp.setSelected(mapModeBtn.getModel(), true);
		
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
		pencilBtn 	= makeButton("Penc", "", "Pencil", "pencilBtn", true);
		lineBtn		= makeButton("Line", "", "Line", "lineBtn", true);
		rectBtn		= makeButton("Rect", "", "Rectangle", "rectBtn", true);
		circleBtn	= makeButton("Circ", "", "Circle", "circleBtn", true);
		fillBtn		= makeButton("Fill", "", "Flood Fill", "fillBtn", true);
		
		// Add in DRAW buttons to group.
		drawBtnGrp.add(pencilBtn);
		drawBtnGrp.add(lineBtn);
		drawBtnGrp.add(rectBtn);
		drawBtnGrp.add(circleBtn);
		drawBtnGrp.add(fillBtn);
		
		// Set pencil mode as default.
		drawBtnGrp.setSelected(pencilBtn.getModel(), true);
		
		// Add in DRAW buttons.
		mainToolBar.add(pencilBtn);
		mainToolBar.add(lineBtn);
		mainToolBar.add(rectBtn);
		mainToolBar.add(circleBtn);
		mainToolBar.add(fillBtn);
		// TOOLBAR SEPARATOR.
		mainToolBar.addSeparator();
	}
	
	/*private void toolkitToolSetup() {
		// TOOLKIT BUTTONS
		//cherryBtn	= makeButton("Cherry", "Cherry DataBase", "cherryBtn");
		//orangeBtn	= makeButton("Orange", "Orange MapConvert", "orangeBtn");
		//limeBtn		= makeButton("Lime", "Lime ResourceBase", "limeBtn");
		
		// Add in TOOLKIT buttons.
		//mainToolBar.add(cherryBtn);
		//mainToolBar.add(orangeBtn);
		//mainToolBar.add(limeBtn);
		// TOOLBAR SEPARATOR.
		//mainToolBar.addSeparator();
	}*/
	
	/**========================================
	// SET METHODS.
	//=========================================**/
	/**========================================
	// setTitle(title) - Set current map's title.
	//=========================================**/
	private void setTitle(String title) {
		if (isPanelActive())
			fruitFrame.setTitle(title);
		else
			fruitFrame.setTitle("FruitEditor");
	}
	
	/**========================================
	// setMap(m) - Set the map.
	//=========================================**/
	public void setMap(Map m) {
		map = m;
	}
	
	/**========================================
	// setTileset(t) - Set the tileset.
	//=========================================*/
	public void setTileset(Tileset t) {
		tileset = t;
	}
	
	/**========================================
	// setPanelActive(act) - Activate/deactivate fruitPanel. 
	//=========================================**/
	public void setPanelActive(boolean act) {
		panelActive = act;
	}
	
	public void addChanges(FruitCommand cmd) {
		undoManager.add(cmd);
	}
	
	/**========================================
	//  setActiveFile(file) - Write new file onto activeFile.
	//=========================================**/
	public void setActiveFile(File file) {
		activeFile = file;
	}
	
	/**========================================
	// putComponent(key, value) - Place JComponent into hashmap. 
	//=========================================*/
	public void putComponent(String key, JComponent value) {
		hash.put(key, value);
	}
	
	/**========================================
	// GET METHODS.
	//=========================================**/
	/**========================================
	// getComponent(text) - Get button.
	//=========================================**/
	public JComponent getComponent(String text) {
		JComponent comp = hash.get(text);
		
		if (comp != null)
			return comp;
	
		return null;
	}
	
	/**========================================
	// getFrame() - Get JFrame.
	//=========================================**/
	public JFrame getFrame() {
		return fruitFrame;
	}
	
	/**========================================
	// getListener() - Get event listener.
	//=========================================**/
	public FruitListener getListener() {
		if (fruitListener != null)
			return fruitListener;
		
		return null;
	}
	
	/**========================================
	// getPanel() - Get FruitPanel.
	//=========================================**/
	public FruitPanel getPanel() {
		return fruitPanel;
	}
	
	/**========================================
	// getMapPanel() - Get MapPanel.
	//=========================================**/
	public MapPanel getMapPanel() {
		if (fruitPanel.getMapPanel() != null)
			return fruitPanel.getMapPanel();
		
		return null;
	}
	
	/**========================================
	// getTilePanel() - Get TilePanel.
	//=========================================**/
	public TilePanel getTilePanel() {
		return fruitPanel.getTilePanel();
	}
	
	/**========================================
	// getStatusPanel() - Get StatusPanel.
	//=========================================**/
	public StatusPanel getStatusPanel() {
		if (statusPanel != null)
			return statusPanel;
		
		return null;
	}
	
	public UndoManager getUndoManager() {
		if (undoManager != null)
			return undoManager;
		
		return null;
	}
	
	public boolean undoable() {
		return undoManager.undoable();
	}
	
	public boolean redoable() {
		return undoManager.redoable();
	}
	
	/**=======================================
	// getActiveFile() - Fetch the current file loaded. 
	//========================================**/
	public File getActiveFile() {
		return activeFile;
	}
	
	/**========================================
	// getMap() - Get map. 
	//=========================================**/
	public Map getMap() {
		if (map != null)
			return map;
		
		return null;
	}
	
	/**========================================
	// getTileset() - Get tileset. 
	//=========================================**/
	public Tileset getTileset() {
		if (tileset != null)
			return tileset;
		
		return null;
	}
	
	/**========================================
	// getSelectedTile() - Get selected tile.
	//=========================================**/
	public Tile getSelectedTile() {
		return getTilePanel().getSelectedTile();
	}
	
	/**========================================
	// isPanelActive() - Check if panel active.
	//=========================================**/
	public boolean isPanelActive() {
		return panelActive;
	}
	
	/**=======================================
	// UPDATE METHOD. 
	//========================================**/
	public void update() {
		setTitle("FruitEditor - " + map.getName());
		
		toggleMenus(panelActive);
		toggleTools(panelActive);
		toggleSave(panelActive);

		menuBar.repaint();
		toolbarPanel.repaint();
		fruitPanel.update();
		statusPanel.update();
		fruitFrame.repaint();
		validate();
		
		toggleUndo(undoable());
		toggleRedo(redoable());
	}
	
	public void validate() {
		fruitFrame.validate();
		menuBar.revalidate();
		toolbarPanel.revalidate();
	}
	
	/**========================================
	// HELPER METHODS.
	//=========================================**/
	/**=========================================
	// makeButton(text,tooltip,varname) - Make text button for the toolbar, assume not a toggle btn.
	//=========================================**/
	private JButton makeButton(String text, String tooltip, String varName) {
		return makeButton(text, null, tooltip, varName);
	}
	
	/**=========================================
	// makeButton(text,icon,tooltip,varname) - Make button for the toolbar.
	//=========================================**/
	private JButton makeButton(String text, String icon, String tooltip, String varName) {
		JButton btn;
		// Add in button, make text if icon unavailable.
		if (icon != null) {
			btn = new JButton(FruitImgBank.get().loadIconImage(icon));
		} else {
			btn = new JButton(text);
		}
		
		//btn = new JButton(text);
		btn.setName(varName);
		btn.setToolTipText(tooltip);

		// Add button ActionListener.
		btn.addActionListener(fruitListener);
		
		// Add button to hashmap.
		hash.put(btn.getName(), btn);
		
		// Print to see if button is in hashmap.
		if (hash.containsValue(btn))
			System.out.println(btn.getName() + " is in hash");
		
		// Ensure the buttons are added in toolbar.
		System.out.println(btn.getName() + " added in toolbar");
		
		return btn;
	}
	
	/**=========================================
	// makeButton(text,icon,tooltip,varname,toggle) - Make toggle button for the toolbar.
	//=========================================**/
	private JToggleButton makeButton(String text, String icon, String tooltip, String varName, boolean toggle) {
		JToggleButton btn;
		// Add in toggle btn, make text if icon unavailable.
		if (toggle) {
			try {
				//btn = new JToggleButton(FruitImgBank.get().loadIconImage(icon));
			} catch (Exception e) {
				btn = new JToggleButton(text);
			}
			
			btn = new JToggleButton(text);
			btn.setName(varName);
			btn.setToolTipText(tooltip);
	
			// Add button ActionListener.
			btn.addActionListener(fruitListener);
			
			// Add button to hashmap.
			hash.put(btn.getName(), btn);
			
			// Print to see if button is in hashmap.
			if (hash.containsValue(btn))
				System.out.println(btn.getName() + " is in hash");
			
			// Ensure the buttons are added in toolbar.
			System.out.println(btn.getName() + " added in toolbar");
			
			return btn;
		}
		
		return null;
	}
	
	/**=========================================
	// makeShortcut(menu,key) - Make menu accelerator shortcut.
	//=========================================**/
	private void makeShortcut(JMenuItem menu, int key) {
		makeShortcut(menu, key, "");
	}
	
	/**=========================================
	// makeShortcut(menu,key,mask) - Make menu accelerator shortcut + key mask.
	//=========================================**/
	private void makeShortcut(JMenuItem menu, int key, String mask) {
		// Branch conditions based off of key mask
		if (mask.equals("CTRL")) {
			try {
				// Get KeyStroke for key and add in accelerator
				KeyStroke k = KeyStroke.getKeyStroke(key, ActionEvent.CTRL_MASK);
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		} else if (mask.equals("ALT")) {
			try {
				// Get KeyStroke for key and add in accelerator
				KeyStroke k = KeyStroke.getKeyStroke(key, ActionEvent.ALT_MASK);
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		} else {
			try {
				// Get KeyStroke for key and add in accelerator
				KeyStroke k = KeyStroke.getKeyStroke(key, 0);
				menu.setAccelerator(k);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to add key accelerator.");
				e.printStackTrace();
			}
		}
	}
}