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
	private final int WIDTH = 640;
	private final int HEIGHT = 480;
	
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
		init();
	}
	
	public void init() {
		// Initialize dimensions.
		mapWidth = 20;
		mapHeight = 15;
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
		tileText = makeTextField("tileText");
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
		newdialog.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		Container cont = newdialog.getContentPane();
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridwidth = 2; // make this row 2 cols
		gbc.gridx = 0;
		gbc.gridy = 0;
		
		cont.add(mapLabel, gbc); // (0,0)
		
		gbc.gridx = 1;
		
		cont.add(mapText, gbc); // (1,0)
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridwidth = 3; // make this row 3 cols
		gbc.weightx = 0.5; // add some space
		gbc.gridx = 0;
		gbc.gridy = 1;
		
		cont.add(tileLabel, gbc); // (0,1)
		
		gbc.gridx = 1;
		
		cont.add(tileText, gbc); // (1,1)
		
		gbc.gridx = 2;
		
		cont.add(browseBtn, gbc); // (2,1)
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridwidth = 8; // make this row 8 cols
		gbc.weightx = 1.0;
		gbc.gridx = 0;
		gbc.gridy = 2;
		
		cont.add(mapWidthLabel, gbc); // (0,2)
		
		gbc.gridx = 1;
		
		cont.add(mapWidthText, gbc); // (1,2)
		
		gbc.gridx = 2;
		
		cont.add(mapHeightLabel, gbc); // (2,2)
		
		gbc.gridx = 3;
		
		cont.add(mapHeightText, gbc); // (3,2)
		
		gbc.gridx = 4;
		
		cont.add(gridWidthLabel, gbc); // (4,2)
		
		gbc.gridx = 5;
		
		cont.add(gridWidthText, gbc); // (5,2)
		
		gbc.gridx = 6;
		
		cont.add(gridHeightLabel, gbc); // (6,2)

		gbc.gridx = 7;
		
		cont.add(gridHeightText, gbc); // (7,2)
		
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		gbc.gridwidth = 2; // make this row 2 cols
		gbc.ipadx = 10; // Get 10 pixels of padding
		gbc.gridx = 0;
		gbc.gridy = 3;
		
		cont.add(okBtn, gbc); // (0,3)
		
		gbc.gridx = 1;
		
		cont.add(cancelBtn, gbc); // (1,3)
		
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
		JTextField txtField;
		
		txtField = new JTextField();
		txtField.setName(name);
		
		return txtField;
	}
	
	private JTextField makeTextField(String text, String name) {
		JTextField txtField;
		
		txtField = new JTextField(text);
		txtField.setName(name);
		
		return txtField;
	}
	
	private JSpinner makeSpinner(int num, String name) {
		JSpinner spinner;
		
		spinner = new JSpinner(
				new SpinnerNumberModel(num, 0, Map.MAP_SIZE, 1));
		spinner.setName(name);
		
		return spinner;
	}
	 
	private JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		
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
		
		return btn;
	}
	
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
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
