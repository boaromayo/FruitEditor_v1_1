package FruitEditor;

import java.awt.*;

import java.awt.event.*;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.*;

public class NewDialog implements ActionListener, ChangeListener, KeyListener {
	// DIALOG.
	protected JDialog newDialog;
	
	// DIALOG SIZE.
	private final int WIDTH = 480;
	private final int HEIGHT = 200;
	
	// COMPONENTS.
	protected JLabel mapLabel;
	//private JLabel tileLabel;
	protected JLabel mapWidthLabel;
	protected JLabel mapHeightLabel;
	protected JLabel gridWidthLabel;
	protected JLabel gridHeightLabel;
	
	protected JTextField mapText;
	//private JTextField tileText;
	protected JSpinner mapWidthText;
	protected JSpinner mapHeightText;
	protected JSpinner gridWidthText;
	protected JSpinner gridHeightText;
	
	//private JButton browseBtn;
	protected JButton okBtn;
	protected JButton cancelBtn;
	protected JToggleButton lockBtn;
	
	// PROPERTIES.
	//private String tileFilename;
	private int mapWidth;
	private int mapHeight;
	private int gridWidth;
	private int gridHeight;
	
	private boolean lock = true;

	protected FruitEditor fruitEditor;
	protected MapPanel mapPanel;
	
	public NewDialog(FruitEditor f) {
		String title = "Create New Map";
		
		fruitEditor = f;
		mapPanel = f.getMapPanel();
		
		newDialog = new JDialog(f.getFrame());
		
		init();
		
		// Add components to dialog
		addComps();
		
		// Set specs for new map dialog.
		newDialog.pack();
		
		newDialog.setTitle(title);
		newDialog.setSize(WIDTH,HEIGHT);

		newDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		newDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		newDialog.setLocationRelativeTo(null);
		newDialog.setVisible(true);
		newDialog.setFocusable(true);
		newDialog.requestFocus();
		newDialog.setResizable(false);
	}
	
	public void init() {
		// Initialize dimensions.
		mapWidth = 8;
		mapHeight = 8;
		gridWidth = FruitEditor.GRID_SIZE;
		gridHeight = FruitEditor.GRID_SIZE;
		
		// Initialize labels.
		mapLabel = makeLabel("Map Name", "mapLabel");
		//tileLabel = makeLabel("Tileset", "tileLabel");
		mapWidthLabel = makeLabel("Width:", "mapWidthLabel");
		mapHeightLabel = makeLabel("Height:", "mapHeightLabel");
		gridWidthLabel = makeLabel("Grid W:", "gridWidthLabel");
		gridHeightLabel = makeLabel("Grid H:", "gridHeightLabel");
		
		// initialize text fields.
		mapText = makeTextField("mapText");
		//tileText = makeTextField("tileText", 30);
		mapWidthText = makeSpinner(mapWidth, "mapWidthText");
		mapHeightText = makeSpinner(mapHeight, "mapHeightText");
		gridWidthText = makeSpinner(gridWidth, "gridWidthText");
		gridHeightText = makeSpinner(gridHeight, "gridHeightText");
		
		// initialize buttons.
		//browseBtn 	= makeButton("B", "../img/openfile.png", "browseBtn"); // Load open dialog to browse tileset files (*.png, *.jpg)
		okBtn  	   	= makeButton("OK", "okBtn");
		cancelBtn  	= makeButton("Cancel", "cancelBtn");
		lockBtn		= makeToggleBtn("Lock", "lockBtn");
		
		lockBtn.setSelected(lock); // set button state to lock.
	}
	
	/**==================================
	// addComps() - Add components
	//===================================**/
	protected void addComps() {
		newDialog.setLayout(new GridLayout(3,1,2,2));
		JPanel one = new JPanel();
		//JPanel two = new JPanel();
		JPanel size = new JPanel();
		JPanel btn = new JPanel();
		
		one.add(mapLabel); // (0,0)
		one.add(mapText); // (1,0)
		
		newDialog.add(one);
		
		//two.add(tileLabel); // (0,1)
		//two.add(tileText); // (1,1)
		//two.add(browseBtn); // (2,1)
		
		//newdialog.add(two);
		
		size.setLayout(new GridLayout(2,4,8,1));
		
		size.add(mapWidthLabel); // (0,2)
		size.add(mapHeightLabel); // (1,2)
		size.add(gridWidthLabel); // (2,2)
		size.add(gridHeightLabel); // (3,2)
		size.add(mapWidthText); // (4,2)
		size.add(mapHeightText); // (5,2)
		size.add(gridWidthText); // (6,2)
		size.add(gridHeightText); // (7,2)
		
		newDialog.add(size);
		
		btn.setLayout(new GridLayout(1,3,2,2));
		
		btn.add(okBtn); // (0,3)
		btn.add(cancelBtn); // (1,3)
		btn.add(lockBtn); // (2,3)
		
		newDialog.add(btn);
	}
	
	/**==================================
	// dispose() - Dispose dialog.
	//===================================**/
	public void dispose() {
		newDialog.dispose();
	}
	
	/**==================================
	// PROPERTY SETTER METHODS.
	//===================================**/
	public void setMapText(String name) {
		mapText.setText(name);
	}
	
	/*public void setTilesetFilename(String name) {
		tileFilename = name;
	}*/
	
	public void setMapWidth(int w) {
		mapWidth = w;
		mapWidthText.setValue(w);
	}
	
	public void setMapHeight(int h) {
		mapHeight = h;
		mapHeightText.setValue(h);
	}
	
	public void setGridWidth(int gw) {
		gridWidth = gw;
		gridWidthText.setValue(gw);
	}
	
	public void setGridHeight(int gh) {
		gridHeight = gh;
		gridHeightText.setValue(gh);
	}
	
	public void setLock(boolean l) {
		lock = l;
	}
	
	/**==================================
	// PROPERTY GETTER METHODS.
	//===================================**/
	public String getMapText() {
		return mapText.getText();
	}
	
	/*public String getTilesetFilename() {
		return tileFilename;
	}*/
	
	/*public String getTileset() {
		return tileText.getText();
	}*/
	
	public int getMapWidth() {
		return (Integer)mapWidthText.getValue();
	}
	
	public int getMapHeight() {
		return (Integer)mapHeightText.getValue();
	}
	
	public int getGridWidth() {
		return (Integer)gridWidthText.getValue();
	}
	
	public int getGridHeight() {
		return (Integer)gridHeightText.getValue();
	}
	
	public boolean isSizeLocked() { return lock; }
 	
	/**==================================
	// HELPER METHODS.
	//===================================**/
	protected JLabel makeLabel(String text, String name) {
		JLabel lbl;
		
		lbl = new JLabel(text);
		lbl.setName(name);
		
		return lbl;
	}
	
	protected JTextField makeTextField(String name) {
		return makeTextField(name, 30);
	}
	
	private JTextField makeTextField(String name, int width) {
		JTextField txtField;
		
		txtField = new JTextField(width);
		txtField.setName(name);
		txtField.addActionListener(this);
		txtField.addKeyListener(this);
		
		return txtField;
	}
	
	protected JSpinner makeSpinner(int num, String name) {
		JSpinner spinner;
		
		if (name.startsWith("grid")) {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, 8, gridWidth*16, 1));
		} else {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, num, Map.MAP_SIZE, 1));
		}
		
		spinner.setName(name);
		spinner.addChangeListener(this);
		spinner.addKeyListener(this);
		
		return spinner;
	}
	 
	protected JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		
		btn.addActionListener(this);
		btn.addKeyListener(this);
		
		return btn;
	}
	
	protected JToggleButton makeToggleBtn(String text, String name) {
		JToggleButton btn;
		
		btn = new JToggleButton(text);
		btn.setName(name);
		
		btn.addActionListener(this);
		btn.addKeyListener(this);
		
		return btn;
	}
	
	/*private JButton makeButton(String text, String icon, String name) {
		JButton btn;
		
		// Add button, load text if unable to load icon.
		try {
			btn = new JButton(FruitImgBank.get().loadIconImage(icon));
		} catch (Exception e) {
			btn = new JButton(text);
		}
		
		btn.setName(name);
		btn.addActionListener(this);
		btn.addKeyListener(this);
		
		return btn;
	}*/
	
	/**=======================================
	// actionPerformed(actionEvent)
	//========================================*/
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (newDialog.isVisible()) {
			if (btn == okBtn) {
				loadMap();
			} else if (btn == cancelBtn) {
				dispose();
			} else if (btn == lockBtn) { 
				// set width and height text field to default size if locked.
				setLock(lockBtn.isSelected());
				
				if (lock) {
					setGridWidth(FruitEditor.GRID_SIZE);
					setGridHeight(FruitEditor.GRID_SIZE);
				}
			}
		}
	}
	
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if (newDialog.isVisible()) {
			if (src == mapWidthText) {
				setMapWidth((Integer)mapWidthText.getValue());
				System.out.println(mapWidth); // test debug here
			} else if (src == mapHeightText) {
				setMapHeight((Integer)mapHeightText.getValue());
				System.out.println(mapHeight); // test debug here
			} else if (src == gridWidthText) {
				setGridWidth((Integer)gridWidthText.getValue()); // Set grid width to value in text field.
				if (lock) {
					// Also set grid height and its text field to new height if locked.
					setGridHeight((Integer)gridWidthText.getValue());
				}
			} else if (src == gridHeightText) {
				setGridHeight((Integer)gridHeightText.getValue());
				if (lock) {
					setGridWidth((Integer)gridHeightText.getValue());
				}
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		Object src = e.getSource();
		int key = e.getKeyCode();
		
		if (newDialog.isVisible()) {
			if (key == KeyEvent.VK_ENTER) {
				if (src == okBtn) {
					okBtn.doClick();
				} else if (src == cancelBtn) {
					cancelBtn.doClick();
				} else if (src == lockBtn) {
					lockBtn.doClick();
				} else if (src == mapText) {
					loadMap();
				}
			} else if (key == KeyEvent.VK_ESCAPE) {
				dispose();
			}
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	private void loadMap() {
		// if map name or tileset name is blank, put warning prompt
		// otherwise, set map name and set map dimensions up
		if (getMapText().equals("") || getMapText().matches("^\\s+")) {
			JOptionPane.showMessageDialog(newDialog, 
					"Enter a name for this map.", 
					"Map Name Blank", 
					JOptionPane.WARNING_MESSAGE);
		} else if (mapPanel == null) {
			JOptionPane.showMessageDialog(newDialog, 
					"The map panel is not set.",
					"Map Panel Not Set",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// prep the new map
			mapPanel.setMap(new Map(getMapWidth(), getMapHeight(), 
					getGridWidth(), getGridHeight()));
			mapPanel.setMapName(getMapText());
			
			// set fruitpanel active if inactive
			mapPanel.setPanelActive(true);
			
			mapPanel.repaint();
			fruitEditor.validate();
			
			setMapText(null); // Leave map text field blank.
			
			dispose(); // Remove new map dialog.
		}
	}
}
