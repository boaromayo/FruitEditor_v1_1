package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;

import javax.swing.*;
import javax.swing.filechooser.*;

public class NewDialog implements ActionListener {
	// DIALOG.
	private JDialog newdialog;
	
	// DIALOG SIZE.
	private final int WIDTH = 480;
	private final int HEIGHT = 200;
	
	// COMPONENTS.
	private JLabel mapLabel;
	private JLabel tileLabel;
	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;
	private JLabel gridWidthLabel;
	private JLabel gridHeightLabel;
	
	private JTextField mapText;
	private JTextField tileText;
	private JSpinner mapWidthText;
	private JSpinner mapHeightText;
	private JSpinner gridWidthText;
	private JSpinner gridHeightText;
	
	private JButton browseBtn;
	private JButton okBtn;
	private JButton cancelBtn;
	
	// PROPERTIES.
	private String mapName;
	private String mapFilename;
	private String tileFilename;
	private int mapWidth;
	private int mapHeight;
	private int gridWidth;
	private int gridHeight;
	
	public NewDialog() {
		newdialog = new JDialog();
		
		init();
	}
	
	public void init() {
		// Initialize dimensions.
		mapWidth = 10;
		mapHeight = 10;
		gridWidth = gridHeight = 24;
		
		// Initialize labels.
		mapLabel = makeLabel("Map Name", "mapLabel");
		tileLabel = makeLabel("Tileset", "tileLabel");
		mapWidthLabel = makeLabel("Width:", "mapWidthLabel");
		mapHeightLabel = makeLabel("Height:", "mapHeightLabel");
		gridWidthLabel = makeLabel("Grid W:", "gridWidthLabel");
		gridHeightLabel = makeLabel("Grid H:", "gridHeightLabel");
		
		// initialize text fields.
		mapText = makeTextField("mapText");
		tileText = makeTextField("tileText", 30);
		mapWidthText = makeSpinner(mapWidth, "mapWidthText");
		mapHeightText = makeSpinner(mapHeight, "mapHeightText");
		gridWidthText = makeSpinner(gridWidth, "gridWidthText");
		gridHeightText = makeSpinner(gridHeight, "gridHeightText");
		
		// initialize buttons.
		browseBtn 	= makeButton("B", "../img/openfile.png", "browseBtn"); // Load open dialog to browse tileset files (*.png, *.jpg)
		okBtn  	   	= makeButton("OK", "okBtn");
		cancelBtn  	= makeButton("Cancel", "cancelBtn");
		
		// Add components to dialog
		addComps();
		
		// Set specs for new map dialog.
		newdialog.pack();
		
		newdialog.setTitle("Create New Map");
		newdialog.setSize(WIDTH,HEIGHT);
		newdialog.setLocationRelativeTo(null);
		newdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		newdialog.setVisible(true);
		newdialog.setFocusable(true);
		newdialog.setResizable(false);
	}
	
	/**==================================
	// addComps() - Add components
	//===================================**/
	private void addComps() {
		newdialog.setLayout(new GridLayout(4,1,2,2));
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel size = new JPanel();
		JPanel btn = new JPanel();
		
		one.add(mapLabel); // (0,0)
		one.add(mapText); // (1,0)
		
		newdialog.add(one);
		
		two.add(tileLabel); // (0,1)
		two.add(tileText); // (1,1)
		two.add(browseBtn); // (2,1)
		
		newdialog.add(two);
		
		size.setLayout(new GridLayout(2,4,8,1));
		
		size.add(mapWidthLabel); // (0,2)
		size.add(mapHeightLabel); // (1,2)
		size.add(gridWidthLabel); // (2,2)
		size.add(gridHeightLabel); // (3,2)
		size.add(mapWidthText); // (4,2)
		size.add(mapHeightText); // (5,2)
		size.add(gridWidthText); // (6,2)
		size.add(gridHeightText); // (7,2)
		
		newdialog.add(size);
		
		btn.add(okBtn); // (0,3)
		btn.add(cancelBtn); // (1,3)
		
		newdialog.add(btn);
	}
	
	/**==================================
	// PROPERTY SETTER METHODS.
	//===================================**/
	public void setName(String name) {
		mapName = name;
	}
	
	public void setMapFilename(String name) {
		mapFilename = name;
	}
	
	public void setTilesetFilename(String name) {
		tileFilename = name;
	}
	
	public void setWidth(int w) {
		mapWidth = w;
	}
	
	public void setWidth(double w) {
		mapWidth = (int)w;
	}
	
	public void setHeight(int h) {
		mapHeight = h;
	}
	
	public void setHeight(double h) {
		mapHeight = (int)h;
	}
	
	/**==================================
	// PROPERTY GETTER METHODS.
	//===================================**/
	public String getMapName() {
		return mapName;
	}
	
	public String getMapText() {
		return mapText.getText();
	}
	
	public String getMapFilename() {
		return mapFilename;
	}
	
	public String getTilesetFilename() {
		return tileFilename;
	}
	
	public String getTilesetText() {
		return tileText.getText();
	}
	
	public int getWidth() {
		return mapWidth;
	}
	
	public int getWidthText() {
		return (Integer)mapWidthText.getValue();
	}
	
	public int getHeight() {
		return mapHeight;
	}
	
	public int getHeightText() {
		return (Integer)mapHeightText.getValue();
	}
	
	public int getGridWidth() {
		return gridWidth;
	}
	
	public int getGridWidthText() {
		return (Integer)gridWidthText.getValue();
	}
	
	public int getGridHeight() {
		return gridHeight;
	}
	
	public int getGridHeightText() {
		return (Integer)gridHeightText.getValue();
	}
	
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
		return makeTextField(name, 25);
	}
	
	private JTextField makeTextField(String name, int width) {
		JTextField txtField;
		
		txtField = new JTextField(width);
		txtField.setName(name);
		txtField.addActionListener(this);
		
		return txtField;
	}
	
	private JSpinner makeSpinner(int num, String name) {
		JSpinner spinner;
		
		if (name.startsWith("grid")) {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, 8, Map.MAP_SIZE, 1));
			spinner.setName(name);
			
		} else {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, num, Map.MAP_SIZE, 1));
			spinner.setName(name);
			
		}
		
		return spinner;
	}
	 
	private JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		
		btn.addActionListener(this);
		
		return btn;
	}
	
	private JButton makeButton(String text, String icon, String name) {
		JButton btn;
		
		// Add button, load text if unable to load icon.
		try {
			btn = new JButton(FruitImgLoader.loadIconImage(icon));
		} catch (Exception e) {
			btn = new JButton(text);
		}
		
		btn.setName(name);
		btn.addActionListener(this);
		
		return btn;
	}
	
	/**=======================================
	// actionPerformed(actionEvent)
	//========================================*/
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (btn == okBtn) {
			// if map name or tileset name is blank, put warning prompt
			// otherwise, set map name 
			if (getMapText().equals("\\s+") || getMapText() == null) {
				
			} else {
				setName(getMapText());
			}
		} else if (btn == cancelBtn) {
			newdialog.dispose();
		} else if (btn == browseBtn) {
			JFileChooser open = new JFileChooser();
			
			int confirm = open.showOpenDialog(null);
			
			if (confirm == JFileChooser.APPROVE_OPTION) {
				try {
					// the file to be opened
					File openfile = open.getSelectedFile();
					
					// set tileset filename and text to the pathname
					setTilesetFilename(openfile.getAbsolutePath());
					tileText.setText(openfile.getAbsolutePath());
				} catch (Exception exc) {
					System.err.println("ERROR: Cannot load file. " +
							"Reason: " + exc.getMessage() + "\n");
					exc.printStackTrace();
				}
			}
		}
	}
}
