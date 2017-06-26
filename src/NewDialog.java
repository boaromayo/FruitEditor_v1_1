package FruitEditor;

import java.awt.*;
import java.awt.Dialog.ModalityType;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

public class NewDialog implements ActionListener, ChangeListener, KeyListener {
	// DIALOG.
	private JDialog newDialog;
	
	// DIALOG SIZE.
	private final int WIDTH = 480;
	private final int HEIGHT = 180;
	
	// COMPONENTS.
	private JLabel mapLabel;
	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;
	private JLabel tileWidthLabel;
	private JLabel tileHeightLabel;
	
	private JTextField mapText;
	private JSpinner mapWidthText;
	private JSpinner mapHeightText;
	private JSpinner tileWidthText;
	private JSpinner tileHeightText;
	
	//private JButton browseBtn;
	private JButton okBtn;
	private JButton cancelBtn;
	private JToggleButton lockBtn;
	
	// PROPERTIES.
	private int mapWidth;
	private int mapHeight;
	private int tileWidth;
	private int tileHeight;
	
	private boolean lock = true;

	private MapPanel mapPanel;
	
	public NewDialog(FruitEditor f) {
		String title = "Create New Map";
		
		mapPanel = f.getMapPanel();
		
		newDialog = new JDialog(f.getFrame());
		
		init();
		
		// Add components to dialog
		addComps();
		
		// Set specs for new map dialog.
		newDialog.setTitle(title);
		newDialog.setSize(WIDTH,HEIGHT);

		newDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		newDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		newDialog.setLocationRelativeTo(null);
		newDialog.setResizable(false);
		newDialog.setVisible(true);
		newDialog.setFocusable(true);
		newDialog.requestFocus();
	}
	
	public void init() {
		// Initialize dimensions.
		mapWidth = 8;
		mapHeight = 8;
		tileWidth = FruitEditor.GRID_SIZE;
		tileHeight = FruitEditor.GRID_SIZE;
		
		// Initialize labels.
		mapLabel = makeLabel("Map Name", "mapLabel");
		mapWidthLabel = makeLabel("Width:", "mapWidthLabel");
		mapHeightLabel = makeLabel("Height:", "mapHeightLabel");
		tileWidthLabel = makeLabel("TileW:", "tileWidthLabel");
		tileHeightLabel = makeLabel("TileH:", "tileHeightLabel");
		
		// initialize text fields.
		mapText = makeTextField("mapText");
		mapWidthText = makeSpinner(mapWidth, "mapWidthText");
		mapHeightText = makeSpinner(mapHeight, "mapHeightText");
		tileWidthText = makeSpinner(tileWidth, "tileWidthText");
		tileHeightText = makeSpinner(tileHeight, "tileHeightText");
		
		// initialize buttons.
		// Load open dialog to browse tileset files (*.png, *.jpg)
		okBtn  	   	= makeButton("OK", "okBtn");
		cancelBtn  	= makeButton("Cancel", "cancelBtn");
		lockBtn		= makeToggleBtn("Lock", "lockBtn");
		
		lockBtn.setSelected(lock); // set button state to lock.
	}
	
	/**==================================
	// addComps() - Add components
	//===================================**/
	private void addComps() {
		newDialog.setLayout(new BoxLayout(newDialog.getContentPane(), 
				BoxLayout.PAGE_AXIS));
		JPanel one = new JPanel(); // panel for map name
		JPanel size = new JPanel(); //panel for sizes
		JPanel size1 = new JPanel(); // panel for map size
		JPanel size2 = new JPanel(); // panel for tile size
		JPanel btn = new JPanel();
		
		one.setLayout(new FlowLayout());
		one.add(mapLabel); // (0,0)
		one.add(mapText); // (1,0)
		
		newDialog.add(one);
		
		size.setLayout(new GridLayout(1,2));
		
		size1.setBorder(new TitledBorder("Map Size"));
		size1.setLayout(new GridLayout(2,2));
		
		size1.add(mapWidthLabel); // (0,1)
		size1.add(mapHeightLabel); // (1,1)
		size1.add(mapWidthText); // (0,2)
		size1.add(mapHeightText); // (1,2)
		
		size.add(size1);
		
		size2.setBorder(new TitledBorder("Tile Size"));
		size2.setLayout(new GridLayout(2,2));
		
		size2.add(tileWidthLabel); // (2,1)
		size2.add(tileHeightLabel); // (3,1)
		size2.add(tileWidthText); // (2,2)
		size2.add(tileHeightText); // (3,2)
		
		size.add(size2);
		
		newDialog.add(size);
		
		btn.setLayout(new FlowLayout());
		
		btn.add(okBtn); // (0,3)
		btn.add(cancelBtn); // (1,3)
		btn.add(lockBtn); // (2,3)
		
		newDialog.add(btn, BorderLayout.SOUTH);
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
	
	public void setMapWidth(int w) {
		mapWidth = w;
		mapWidthText.setValue(w);
	}
	
	public void setMapHeight(int h) {
		mapHeight = h;
		mapHeightText.setValue(h);
	}
	
	public void setTileWidth(int tw) {
		tileWidth = tw;
		tileWidthText.setValue(tw);
	}
	
	public void setTileHeight(int th) {
		tileHeight = th;
		tileHeightText.setValue(th);
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
	
	public int getMapWidth() {
		return (Integer)mapWidthText.getValue();
	}
	
	public int getMapHeight() {
		return (Integer)mapHeightText.getValue();
	}
	
	public int getTileWidth() {
		return (Integer)tileWidthText.getValue();
	}
	
	public int getTileHeight() {
		return (Integer)tileHeightText.getValue();
	}
	
	public boolean isSizeLocked() { return lock; }
 	
	/**==================================
	// HELPER METHODS.
	//===================================**/
	private JLabel makeLabel(String text, String name) {
		JLabel lbl;
		
		lbl = new JLabel(text);
		lbl.setName(name);
		
		return lbl;
	}
	
	private JTextField makeTextField(String name) {
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
	
	private JSpinner makeSpinner(int num, String name) {
		JSpinner spinner;
		
		if (name.startsWith("tile")) {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, 8, FruitEditor.GRID_SIZE*16, 1));
		} else {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, num, Map.MAP_SIZE, 1));
		}
		
		spinner.setName(name);
		spinner.addChangeListener(this);
		spinner.addKeyListener(this);
		
		return spinner;
	}
	 
	private JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		
		btn.addActionListener(this);
		btn.addKeyListener(this);
		
		return btn;
	}
	
	private JToggleButton makeToggleBtn(String text, String name) {
		JToggleButton btn;
		
		btn = new JToggleButton(text);
		btn.setName(name);
		
		btn.addActionListener(this);
		btn.addKeyListener(this);
		
		return btn;
	}
	
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
					setTileWidth(FruitEditor.GRID_SIZE);
					setTileHeight(FruitEditor.GRID_SIZE);
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
			} else if (src == tileWidthText) {
				setTileWidth((Integer)tileWidthText.getValue()); // Set grid width to value in text field.
				if (lock) {
					// Set grid height and its text field to new height if locked.
					setTileHeight((Integer)tileWidthText.getValue());
				}
			} else if (src == tileHeightText) {
				setTileHeight((Integer)tileHeightText.getValue());
				if (lock) {
					setTileWidth((Integer)tileHeightText.getValue());
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
			// updates are taken care of in the map panel's map setting method
			mapPanel.setMap(new Map(getMapWidth(), getMapHeight(), 
					getTileWidth(), getTileHeight()));
			mapPanel.setMapName(getMapText());
			
			setMapText(null); // Leave map text field blank.
			
			dispose(); // Remove new map dialog.
		}
	}
}
