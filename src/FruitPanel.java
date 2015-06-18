package Editor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

public class FruitPanel extends JPanel implements Runnable {
	
	// PANEL THREAD.
	private Thread t;
	
	// DIMENSION CONSTANTS.
	private static final int EWIDTH = 800;
	private static final int EHEIGHT = 600;
	
	private String[] menuName = {"File", "Edit", "View", "FruitTools", "Run", "Help"};
	
	// MENU COMPONENTS: The Main Menu Bar
	private JMenuBar menuBar;
	
	// MENU COMPS: The Menus
	private JMenu fileMenu;
	private JMenu editMenu;
	private JMenu drawMenu;
	private JMenu viewMenu;
	private JMenu toolMenu;
	private JMenu helpMenu;
	
	// MENU COMPS: The Sub-Menus
	private JMenu modeMenu;
	private JMenu scaleMenu;
	
	// MENU COMPS: The Menu Toolbar
	private JToolBar mainToolBar;
	
	// MENU COMPS: The Menu Groups
	private ButtonGroup modegrp;
	private ButtonGroup scalegrp;
	
	// MENU COMPS: Menu items
	// MENU: FILE
	private JMenuItem newItem;
	private JMenuItem openItem;
	private JMenuItem saveItem;
	private JMenuItem saveAsItem;
	private JMenuItem closeItem;
	// MENU: EDIT
	private JMenuItem undoItem;
	private JMenuItem redoItem;
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	// MENU: VIEW -> MODE
	private JMenuItem oneItem;
	private JMenuItem twoItem;
	private JMenuItem fourItem;
	private JMenuItem eightItem;
	// MENU: VIEW -> SCALE
	private JMenuItem mapModeItem;
	private JMenuItem eventModeItem;
	// MENU: DRAW
	private JMenuItem pencilItem;
	private JMenuItem rectItem;
	private JMenuItem circleItem;
	private JMenuItem fillItem;
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
	private JButton undoBtn;
	private JButton redoBtn;
	//
	private JButton toolButton;
	
	// MENU COMPS: for other windows
	private JInternalFrame frame;
	private JDialog aboutDialog;
	
	// OBJECTS.
	private MapFile mapFile;
	private MapPanel mapPanel;
	
	//CONSTRUCTOR.
	public FruitPanel() {
		setSize(EWIDTH, EHEIGHT);
		
		// Set up the editor menu
		menuSetup();
		
		// Set up the Toolbar.
		toolbarSetup();
		
		// Set up the editor panel.
		panelSetup();
		
		// Execute the thread.
		threadSetup();
		
		setVisible(true);
		setFocusable(true);
		requestFocusInWindow();
	}
	
	//================================
	// threadSetup() - Setup thread.
	//================================
	private void threadSetup() {
		t = new Thread(this);
		t.start();
	}
	
	//================================
	// run() - Run the thread.
	//================================
	public void run() {
		try {
			long startTime, diffTime;
			long waitTime;
			
			while(true) {
				startTime = System.currentTimeInMillis();
				
				update(); // Update MapPanel.
				repaint(); // Repaint program JIC.
				
				diffTime = System.currentTimeInMillis() - startTime;
				
				waitTime = startTime - diffTime / 1000;
				
				if (waitTime < 0) waitTime = 5;
				Thread.sleep(waitTime);
			}
		} catch (Exception e) {
			System.err.println("ERROR: " + e);
		}
	}
	
	//================================
	// menuSetup() - Set up main menu and main panel.
	//================================
	protected void menuSetup() {
		// Assign main menu bar.
		menuBar = new JMenuBar();
		
		// Assign the menus.
		fileMenu = new JMenu(menuName[0]);
		editMenu = new JMenu(menuName[1]);
		viewMenu = new JMenu(menuName[2]);
		toolMenu = new JMenu(menuName[3]);
		
		// Disable other menus if no map is loaded.
		if (mapFile == null) {
			editMenu.setEnabled(false);
			viewMenu.setEnabled(false);
			toolMenu.setEnabled(false);
		}
		
		// Create menu shortcuts.	
	  	fileMenu.setMnemonic(menuName[0].charAt(0));
	  	editMenu.setMnemonic(menuName[1].charAt(0));
	  	viewMenu.setMnemonic(menuName[2].charAt(0));
		toolMenu.setMnemonic(menuName[3].charAt(5)); // Make 'T' menu shortcut
			
		subSetup(); // Get the menu event methods.
		
		// Set mainBar as main menu bar.
		setJMenuBar(menuBar);
	}
	
	//==================================================
	// subSetup() - Get the methods that will be run for each menu.
	//==================================================
	protected void subSetup() {
		fileSetup();	
		editSetup();
		viewSetup();
		toolSetup();
	}
	
	//=========================================
	// Setup the menu items for each menu.
	//=========================================
	protected void fileSetup() {
		// FILE -> NEW
		newItem = new JMenuItem("New");
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		newItem.addActionListener(new ActionListener() { // set handler NEW
			public void actionPerformed(ActionEvent nfe) {
				newAction();
			}
		});
		fileMenu.add(newItem);
		// FILE -> OPEN
		openItem = new JMenuItem("Open");
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openItem.addActionListener(new ActionListener() { // set handler OPEN
			public void actionPerformed(ActionEvent ofe) {
				openAction();
			}
		});
		fileMenu.add(openItem);
		// FILE -> SAVE
		saveItem = new JMenuItem("Save");
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() { // set handler SAVE
			public void actionPerformed(ActionEvent sfe) {
				if (map == null) saveAsAction(); 
				else saveAction();
			}
		});
		
		if (mapFile == null)
			saveItem.setEnabled(false);
		
		fileMenu.add(saveItem);
		// FILE -> SAVE AS
		saveAsItem = new JMenuItem("Save As...");
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		saveAsItem.addActionListener(new ActionListener() { // set handler SAVE AS
			public void actionPerformed(ActionEvent safe) {
				saveAsAction();
			}
		});
		
		if (mapFile == null)
			saveAsItem.setEnabled(false);
	
		fileMenu.add(saveAsItem);
		// FILE SEPARATOR
		fileMenu.addSeparator();
		// FILE -> CLOSE
		closeItem = new JMenuItem("Close");
		closeItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				System.exit(0);
			}
		});
		fileMenu.add(closeItem);
		menuBar.add(fileMenu);
	}
	
	protected void editSetup() {
		// EDIT -> UNDO
		menuItem = new JMenuItem("Undo");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		//menuItem.addActionListener(new ActionListener() {});
		menu.add(menuItem);
		// EDIT -> REDO
		menuItem = new JMenuItem("Redo");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		//menuItem.addActionListener(new ActionListener() {});
		menu.add(menuItem);
		// EDIT SEPARATOR
		editMenu.addSeparator();
		// EDIT -> CUT
		menuItem = new JMenuItem("Cut");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		//menuItem.addActionListener(new ActionListener() {});
		menu.add(menuItem);
		// EDIT -> COPY
		menuItem = new JMenuItem("Copy");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		//menuItem.addActionListener(new ActionListener() {});
		menu.add(menuItem);
		// EDIT -> PASTE
		menuItem = new JMenuItem("Paste");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		//menuItem.addActionListener(new ActionListener() {});
		menu.add(menuItem);
		// EDIT -> DELETE
		menuItem = new JMenuItem("Delete");
		menuItem.setAccelerator(KeyStroke.getKeyStroke("DELETE"));
		//menuItem.addActionListener(new ActionListener() {});
		menu.add(menuItem);
		menuBar.add(menu);
	}
	
	protected void viewSetup() {
		// VIEW -> SCALE
		scaleMenu = new JMenu("Scale");
		scalegrp = new ButtonGroup();
		// VIEW -> SCALE -> 1:1
		oneItem = new JRadioButtonMenuItem("1:1");
		oneItem.setSelected(true);
		scalegrp.add(oneItem);
		scaleMenu.add(oneItem);
		// VIEW -> SCALE -> 1:2
		twoItem = new JRadioButtonMenuItem("1:2");
		scalegrp.add(twoItem);
		scaleMenu.add(twoItem);
		// VIEW -> SCALE -> 1:4
		fourItem = new JRadioButtonMenuItem("1:4");
		scalegrp.add(fourItem);
		scaleMenu.add(fourItem);
		// VIEW -> SCALE -> 1:8
		eightItem = new JRadioButtonMenuItem("1:8");
		scalegrp.add(eightItem);
		scaleMenu.add(eightItem);
		viewMenu.add(scaleMenu);
		// VIEW SEPARATOR
		viewMenu.addSeparator();
		// VIEW -> MODE
		modeMenu = new JMenu("Mode");
		modegrp = new ButtonGroup();
		// VIEW -> MODE -> MAP MODE
		mapModeItem = new JRadioButtonMenuItem("Map Mode");
		menuItem.setSelected(true);
		modegrp.add(mapModeItem);
		modeMenu.add(menuItem);
		// VIEW -> MODE -> EVENT MODE
		eventModeItem = new JRadioButtonMenuItem("Event Mode");
		menugrp.add(eventModeItem);
		modeMenu.add(eventModeItem);
		viewMenu.add(modeMenu);
		
		menuBar.add(viewMenu);
	}
	
	protected void toolSetup() {
		// TOOLKIT -> CHERRY DATABASE
		databaseItem = new JMenuItem("Cherry DataBase");
		databaseItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F9, ActionEvent.CTRL_MASK));
		databaseItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent datae) {
				/*dialogFrame = new CherryDatabase();
				dialogFrame.setLocationRelativeTo(null);
				dialogFrame.setVisible(true);*/
			}
		});
		toolMenu.add(databaseItem);
		// TOOLKIT -> ORANGE SCRIPTMAKER
		scriptItem = new JMenuItem("Orange ScriptMaker");
		scriptItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, ActionEvent.CTRL_MASK));
		scriptItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent scripte) {
				
			}
		});
		toolMenu.add(scriptItem);
		// TOOLKIT -> LIME RESOURCEBASE
		resourceItem = new JMenuItem("Lime ResourceBase");
		resourceItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, ActionEvent.CTRL_MASK));
		resourceItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent rese) {
				
			}
		});
		toolMenu.add(resourceItem);
		// TOOLKIT SEPARATOR
		toolMenu.addSeparator();
		// TOOLKIT -> SETTINGS
		configItem = new JMenuItem("Settings...");
		toolMenu.add(configItem);
		menuBar.add(toolMenu);
	}
	
	/*protected void runSetup() {
		// RUN -> START TEST
		menuItem = new JMenuItem("Start Debug");
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, ActionEvent.CTRL_MASK));
		menu.add(menuItem);
		menuBar.add(menu);
	}*/
	
	protected void helpSetup() {
		// HELP -> ABOUT
		aboutItem = new JMenuItem("About...");
		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent hae) {
				aboutDialog = new FruitAbout();
				aboutDialog.pack();
				aboutDialog.setLocationRelativeTo(null);
				aboutDialog.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
		menuBar.add(helpMenu);
	}
	
	protected void newAction() {
		/*dialogFrame = new EditorNew();
		dialogFrame.setLocationRelativeTo(null);
		dialogFrame.setVisible(true);*/
	}
	
	protected void openAction() {
		String feName = "FruitEditor Files (*.feproj)";
		String feExt = "feproj";
		JFileChooser open = new JFileChooser();
		FileNameExtensionFilter openFilt = new FileNameExtensionFilter(feName, feExt);
		open.setFileFilter(openFilt);
		
		int confirm = open.showOpenDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			mapFile = new MapFile();
			mapFile = (EditorFile)open.getSelectedFile();
			mapFile.fileOpen();
		}
	}
	
	protected void saveAction() {
		String feName = "FruitEditor Files (*.feproj)"
		String feExt = "feproj";
		JFileChooser save = new JFileChooser();
		FileNameExtensionFilter saveFilt = new FileNameExtensionFilter(feName, feExt);
		save.setFileFilter(saveFilt);
	
		int confirm = save.showSaveDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			mapFile  = new MapFile();
			mapFile  = (MapFile)save.getSelectedFile();
			mapFile.fileSave();
		}
	}
	
	//=====================================
	// toolbarSetup() - Set up the main toolbar menu.
	//=====================================
	protected void toolbarSetup() {

		mainToolBar = new JToolBar();
		mainToolBar.setRollover(true); // Make a visual indication if mouse goes over the button
		mainToolBar.setFloatable(false); // Ensure that toolbar is not adjustable
		
		final int TOOLCOLUMN = 7;
		
		for (int t = 0; t < TOOLCOLUMN; t++) {
			
			switch (t) {
			case 0:
				fileTool();
				break;
			case 1:
				fixTool();
				break;
			case 2:
				editTool();
				break;
			case 3:
				viewTool();
				break;
			case 4:
				scaleTool();
				break;
			case 5:
				tbTool();
				break;
			case 6:
				runTool();
				break;
			}
			
			mainToolBar.addSeparator();
		}
		
		add(mainToolBar, BorderLayout.NORTH);
	}
	
	public void fileTool() {
		
		// Set up the integer for the fileTool for loop.
		int ft;
		
		// Arrays for the toolButtons.
		String[] fileShort = {"N", "O", "S"};
		String[] fileTip = {"New File", "Open File", "Save File"};
		
		for (ft = 0; ft <= 2; ft++) {
			toolButton = new JButton(fileShort[ft]);
			toolButton.setToolTipText(fileTip[ft]);
			switch (ft) {
				case 0:
					toolButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent nte) {
							newAction();
						}
					});
					break;
				case 1:
					toolButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent nte) {
							openAction();
						}
					});
					break;
				case 2:
					toolButton.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent nte) {
							saveAction();
						}
					});
					break;
			}
			
			if (ft == 2 && mapFile == null) {
				toolButton.setEnabled(false);
			}
			
			mainToolBar.add(toolButton);
			
		}
		
	}
	
	public void fixTool() {
		int fixt;
		
		String [] fixShort = {"U", "R"};
		String [] fixTip = {"Undo", "Redo"};
		
		for (fixt = 0; fixt <= 1; fixt++) {
			toolButton = new JButton(fixShort[fixt]);
			toolButton.setToolTipText(fixTip[fixt]);
			
			if (mapFile == null)
				toolButton.setEnabled(false);
			
			mainToolBar.add(toolButton);
		}
	}
	
	public void editTool() {
		
		// Set up integer for edit tool bar for loop.
		int et;
		
		// Set up the arrays for edit tool bar and tool tip text.
		//Image [] editIcon = {};
		String [] editShort = {"Cut", "Copy", "P", "D"};
		String [] editTip = {"Cut", "Copy", "Paste", "Delete"};
		
		for (et = 0; et <= 3; et++) {
			toolButton = new JButton(editShort[et]);
			toolButton.setToolTipText(editTip[et]);
			switch (et) {
				case 0:
					/*toolButton.addActionListener(new ActionListener() {
						
					});*/
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
			}
			
			if (mapFile == null)
				toolButton.setEnabled(false);
			
			mainToolBar.add(toolButton);
		}
		
	}
	
	public void viewTool() {
		
		// Set up the integer for the view for loop.
		int vt;
		
		String [] viewShort = {"M", "EV"};
		String [] viewTip = {"Map Mode", "Event Mode"};
		
		for (vt = 0; vt <= 1; vt++) {
			toolButton = new JButton(viewShort[vt]);
			toolButton.setToolTipText(viewTip[vt]);
			
			if (mapFile == null) 
				toolButton.setEnabled(false);
			
			mainToolBar.add(toolButton);
		}
	}
	
	public void scaleTool() {
		
		// Set up the integer for the scale for loop.
		int st;
		
		String [] scaleShort = {"1:1", "1:2", "1:4", "1:8"};
		String [] scaleTip = {"Scale - 1:1", "Scale - 1:2", "Scale - 1:4", "Scale - 1:8"};
		
		for (st = 0; st <= 3; st++) {
			toolButton = new JButton(scaleShort[st]);
			toolButton.setToolTipText(scaleTip[st]);
			switch (st) {
				case 0:
					break;
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
			}
			
			if (mapFile == null)
				toolButton.setEnabled(false);
			
			mainToolBar.add(toolButton);
		}
	}
	
	public void tbTool() {
		
		int tbt;
		
		String [] tBarShort = {"C", "O", "L"};
		String [] tBarTip = {"Cherry DataBase", "Orange ScriptMaker", "Lime ResourceBase"};
		
		for (tbt = 0; tbt <= 2; tbt++) {
			toolButton = new JButton(tBarShort[tbt]);
			toolButton.setToolTipText(tBarTip[tbt]);
			
			if (mapFile == null)
				toolButton.setEnabled(false);
			
			mainToolBar.add(toolButton);
		}
	}
	
	public void runTool() {
		
		toolButton = new JButton(">>");
		toolButton.setToolTipText("Run Game");
		
		if (mapFile == null)
			toolButton.setEnabled(false);
		
		mainToolBar.add(toolButton);
	}
	
	protected void panelSetup() {
		
		// Assign main panel.
		mapPanel = new MapPanel();
		
		// Add panel to frame if there is a file in use.
		if (mapFile != null)
			add(mapPanel);
	}
}
