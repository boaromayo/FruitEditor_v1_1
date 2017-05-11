package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;

import javax.swing.*;

public class NewTileDialog implements ActionListener {
	// DIALOG.
	private JDialog tileDialog;
	
	// DIALOG SIZE.
	private final int WIDTH = 560;
	private final int HEIGHT = 200;
	
	// COMPONENTS.
	private JLabel tileLabel;
	private JLabel tileFileLabel;
	private JLabel gridWidthLabel;
	private JLabel gridHeightLabel;
	
	private JTextField tilesetText;
	private JTextField tilesetFileText;
	private JSpinner gridWidthText;
	private JSpinner gridHeightText;
	
	private JButton browseBtn;
	private JButton okBtn;
	private JButton cancelBtn;
	
	// PROPERTIES.
	private int gridWidth;
	private int gridHeight;
	
	private TilePanel tilePanel;
	
	public NewTileDialog(FruitEditor f) {
		String title = "Load New Tileset";
		
		tileDialog = new JDialog(f.getFrame());

		tilePanel = f.getTilePanel();
		
		init();
		
		// Add components.
		addComps();
		
		// Set specs for tile dialog.
		tileDialog.pack();
		
		tileDialog.setTitle(title);
		tileDialog.setSize(WIDTH,HEIGHT);
		
		tileDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		tileDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		tileDialog.setLocationRelativeTo(null);
		tileDialog.setVisible(true);
		tileDialog.setFocusable(true);
		tileDialog.setResizable(false);
	}
	
	public void init() {
		// Initialize dimensions.
		gridWidth = gridHeight = 8;
		
		// Initialize labels.
		tileLabel = makeLabel("Tileset Name", "tileLabel");
		tileFileLabel = makeLabel("Filename", "tileFileLabel");
		gridWidthLabel = makeLabel("Grid W:", "gridWidth");
		gridHeightLabel = makeLabel("Grid H:", "gridHeight");
		
		// Initialize text fields.
		tilesetText = makeTextField("tilesetText");
		tilesetFileText = makeTextField("tilesetFileText");
		gridWidthText = makeSpinner(gridWidth, "gridWidthText");
		gridHeightText = makeSpinner(gridHeight, "gridHeightText");
		
		// Initialize buttons.
		browseBtn = makeButton("Browse...", "browseBtn"); // Load open dialog to browse tileset files.
		okBtn = makeButton("OK", "okBtn");
		cancelBtn = makeButton("Cancel", "cancelBtn");
	}
	
	private void addComps() {
		tileDialog.setLayout(new GridLayout(4,1,2,2));
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel sizePanel = new JPanel();
		JPanel btnPanel = new JPanel();
		
		one.add(tileLabel); // (0,0)
		one.add(tilesetText); // (1,0)
		
		tileDialog.add(one);
	
		two.add(tileFileLabel); // (0,1)
		two.add(tilesetFileText); // (1,1)
		two.add(browseBtn); // (2,1)
		
		tileDialog.add(two);
		
		sizePanel.setLayout(new GridLayout(2,2,8,1));
		
		sizePanel.add(gridWidthLabel); // (0,2)
		sizePanel.add(gridHeightLabel); // (1,2)
		sizePanel.add(gridWidthText); // (2,2)
		sizePanel.add(gridHeightText); //(3,2)
		
		tileDialog.add(sizePanel);
		
		btnPanel.add(okBtn); // (0,3)
		btnPanel.add(cancelBtn); // (1,3)
		
		tileDialog.add(btnPanel);
	}
	
	/**==================================
	// dispose() - Dispose dialog.
	//===================================**/
	public void dispose() {
		tileDialog.dispose();
	}
	
	/**==================================
	// PROPERTY SETTER METHODS.
	//===================================**/
	public void setTilesetName(String name) {
		tilesetText.setText(name);
	}
	
	public void setTilesetFilename(String name) {
		tilesetFileText.setText(name);
	}
	
	public void setGridWidth(int gw) {
		gridWidth = gw;
		gridWidthText.setValue(gridWidth);
	}
	
	public void setGridHeight(int gh) {
		gridHeight = gh;
		gridHeightText.setValue(gridHeight);
	}
	
	public void setGridWidthFromText() {
		gridWidth = getGridWidth();
	}
	
	public void setGridHeightFromText() {
		gridHeight = getGridHeight();
	}
	
	/**==================================
	// PROPERTY GETTER METHODS.
	//===================================**/
	public String getTilesetFilename() {
		String tileFilename = tilesetFileText.getText();
		
		if (tileFilename == null)
			return "NONE";
		
		return tileFilename;
	}
	
	public String getTileset() {
		String tt = tilesetText.getText();
		
		if (tt == null)
			return "NONE";
		
		return tt;
	}
	
	public int getGridWidth() {
		return (Integer)gridWidthText.getValue();
	}
	
	public int getGridHeight() {
		return (Integer)gridHeightText.getValue();
	}
	
	/**==================================
	// HELPER METHODS.
	//===================================**/
	private JLabel makeLabel(String text, String name) {
		JLabel lbl = new JLabel(text);
		
		lbl.setName(name);
		
		return lbl;
	}
	
	private JTextField makeTextField(String name) {
		return makeTextField(name, 30);
	}
	
	private JTextField makeTextField(String name, int width) {
		JTextField textField;
		
		textField = new JTextField(width);
		textField.setName(name);
		textField.addActionListener(this);
		
		return textField;
	}
	
	private JSpinner makeSpinner(int num, String name) {
		JSpinner spinner = new JSpinner(
			new SpinnerNumberModel(num, num, Map.MAP_SIZE, 1));
		
		spinner.setName(name);
		return spinner;
	}
	
	private JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		btn.addActionListener(this);
		
		return btn;
	}
	
	/**=======================================
	// actionPerformed(actionEvent)
	//========================================*/
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (tileDialog.isVisible()) {
			if (src == okBtn) {
				// if tileset filename and/or tileset name is blank,
				// put warning, else
				// set up tile panel for tilesets
				if (getTilesetFilename().equals("") || 
						getTilesetFilename().matches("\\s+")) {
					JOptionPane.showMessageDialog(tileDialog, 
							"No file for tileset loaded.", 
							"Tileset File", 
							JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						File readfile = new File(getTilesetFilename());
						BufferedImage bimg = FruitImgBank.get().
								loadBufferedImage(readfile.getAbsolutePath());
						//tilePanel.setTileset(bimg); 
						
						setTilesetName(null);
					} catch (Exception fe) {
						System.err.println("ERROR: File not found. REASON: " + fe.getMessage());
						fe.printStackTrace();
					}
					
					dispose();
				}
			} else if (src == browseBtn) {
				JFileChooser open = new JFileChooser();
				
				int confirm = open.showOpenDialog(null);
				
				if (confirm == JFileChooser.APPROVE_OPTION) {
					try {
						// the file to be opened
						File openfile = open.getSelectedFile();
						
						// set tileset filename and text to the pathname
						setTilesetFilename(openfile.getAbsolutePath());
						setTilesetName(openfile.getName());
						
					} catch (Exception exc) {
						System.err.println("ERROR: Cannot load file. " +
								"Reason: " + exc.getMessage() + "\n");
						exc.printStackTrace();
					}
				}
			} else if (src == cancelBtn) {
				dispose();
			}
		}
	}
}
