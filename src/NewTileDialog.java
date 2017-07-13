package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

public class NewTileDialog implements ActionListener, ChangeListener {
	// DIALOG.
	private JDialog tileDialog;
	
	// DIALOG SIZE.
	private final int WIDTH = 560;
	private final int HEIGHT = 320;
	
	// COMPONENTS.
	private JLabel tileLabel;
	private JLabel tileFileLabel;
	private JLabel gridWidthLabel;
	private JLabel gridHeightLabel;
	private JLabel offsetXLabel;
	private JLabel offsetYLabel;
	private JLabel paddingVertLabel;
	private JLabel paddingHorizLabel;
	
	private JTextField tilesetText;
	private JTextField tilesetFileText;
	private JSpinner gridWidthText;
	private JSpinner gridHeightText;
	private JSpinner offsetXText;
	private JSpinner offsetYText;
	private JSpinner paddingVertText;
	private JSpinner paddingHorizText;
	
	private JButton browseBtn;
	private JButton okBtn;
	private JButton cancelBtn;
	private JToggleButton lockBtn;
	
	// PROPERTIES.
	private int gridWidth;
	private int gridHeight;
	private int offsetX;
	private int offsetY;
	private int paddingV;
	private int paddingH;
	
	private boolean lock = true;
	
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
		tileDialog.setResizable(false);
		tileDialog.setVisible(true);
		tileDialog.setFocusable(true);
	}
	
	public void init() {
		// Initialize dimensions.
		gridWidth = gridHeight = FruitEditor.GRID_SIZE;
		
		// Initialize labels.
		tileLabel = makeLabel("Tileset Name", "tileLabel");
		tileFileLabel = makeLabel("Filename", "tileFileLabel");
		gridWidthLabel = makeLabel("Grid W:", "gridWidth");
		gridHeightLabel = makeLabel("Grid H:", "gridHeight");
		offsetXLabel = makeLabel("X offset:", "offsetX");
		offsetYLabel = makeLabel("Y offset:", "offsetY");
		paddingVertLabel = makeLabel("Vertical:", "spacingV");
		paddingHorizLabel = makeLabel("Horizontal:", "spacingH");
		
		// Initialize text fields.
		tilesetText = makeTextField("tilesetText");
		tilesetFileText = makeTextField("tilesetFileText");
		gridWidthText = makeSpinner(gridWidth, "gridWidthText");
		gridHeightText = makeSpinner(gridHeight, "gridHeightText");
		offsetXText = makeSpinner(offsetX, "offsetXText");
		offsetYText = makeSpinner(offsetY, "offsetYText");
		paddingVertText = makeSpinner(paddingV, "spacingVText");
		paddingHorizText = makeSpinner(paddingH, "spacingHText");
		
		// Initialize buttons.
		browseBtn = makeButton("Browse...", "browseBtn"); // Load open dialog to browse tileset files.
		okBtn = makeButton("OK", "okBtn");
		cancelBtn = makeButton("Cancel", "cancelBtn");
		lockBtn = makeToggleBtn("Lock", "lockBtn");
		
		lockBtn.setSelected(lock);
	}
	
	private void addComps() {
		tileDialog.setLayout(new BoxLayout(tileDialog.getContentPane(), 
				BoxLayout.PAGE_AXIS));
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel three = new JPanel();
		JPanel sizePanel = new JPanel();
		JPanel offsetPanel = new JPanel();
		JPanel spacingPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		
		one.add(tileLabel); // (0,0)
		one.add(tilesetText); // (1,0)
		
		tileDialog.add(one);
	
		two.add(tileFileLabel); // (0,1)
		two.add(tilesetFileText); // (1,1)
		two.add(browseBtn); // (2,1)
		
		tileDialog.add(two);
		
		three.setLayout(new GridLayout(1,3));
		
		sizePanel.setBorder(new TitledBorder("Tile Size"));
		sizePanel.setLayout(new GridLayout(4,1));
		
		sizePanel.add(gridWidthLabel); // (0,2)
		sizePanel.add(gridWidthText); // (1,2)
		sizePanel.add(gridHeightLabel); // (2,2)
		sizePanel.add(gridHeightText); //(3,2)
		
		three.add(sizePanel);
		
		offsetPanel.setBorder(new TitledBorder("Offset"));
		offsetPanel.setLayout(new GridLayout(4,1));
		
		offsetPanel.add(offsetXLabel);
		offsetPanel.add(offsetXText);
		offsetPanel.add(offsetYLabel);
		offsetPanel.add(offsetYText);
		
		three.add(offsetPanel);
		
		spacingPanel.setBorder(new TitledBorder("Spacing"));
		spacingPanel.setLayout(new GridLayout(4,1));
		
		spacingPanel.add(paddingVertLabel);
		spacingPanel.add(paddingVertText);
		spacingPanel.add(paddingHorizLabel);
		spacingPanel.add(paddingHorizText);
		
		three.add(spacingPanel);
		
		tileDialog.add(three);
		
		btnPanel.setLayout(new FlowLayout());
		
		btnPanel.add(okBtn); // (0,3)
		btnPanel.add(cancelBtn); // (1,3)
		btnPanel.add(lockBtn); // (2,3)
		
		tileDialog.add(btnPanel, BorderLayout.SOUTH);
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
		//gridWidthText.setValue(gridWidth);
	}
	
	public void setGridHeight(int gh) {
		gridHeight = gh;
		//gridHeightText.setValue(gridHeight);
	}
	
	public void setGridWidthFromText() {
		gridWidth = getGridWidth();
	}
	
	public void setGridHeightFromText() {
		gridHeight = getGridHeight();
	}
	
	public void setOffsetX(int ox) {
		offsetX = ox;
		//offsetXText.setValue(offsetX);
	}
	
	public void setOffsetY(int oy) {
		offsetY = oy;
		//offsetYText.setValue(offsetY);
	}
	
	public void setVertPadding(int sy) {
		paddingV = sy;
		//spacingVertText.setValue(spacingV);
	}
	
	public void setHorizPadding(int sx) {
		paddingH = sx;
		//spacingHorizText.setValue(spacingH);
	}
	
	public void setLock(boolean l) {
		lock = l;
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
	/**============================
	// makeFileChooser() - make file chooser and filter files to take in FruitEditor files.
	//=============================**/
	private JFileChooser makeImageFileChooser() {
		JFileChooser jfc = new JFileChooser("../FruitEditor_v1_1/src/img");
		
		// Filter to only basic or low-memory image files (png, bmp, gif, jpg).
		FileNameExtensionFilter bmpfilter =
				new FileNameExtensionFilter("PNG Files (*.png)", "png");
		FileNameExtensionFilter pngfilter =
				new FileNameExtensionFilter("Bitmap Files (*.bmp)", "bmp");
		FileNameExtensionFilter jpgfilter = 
				new FileNameExtensionFilter("JPEG Files (*.jpg)", "jpg", "jpeg");
		FileNameExtensionFilter giffilter = 
				new FileNameExtensionFilter("GIF Files (*.gif)", "gif");
		
		// Add file filters.
		jfc.addChoosableFileFilter(bmpfilter);
		jfc.addChoosableFileFilter(pngfilter);
		jfc.addChoosableFileFilter(jpgfilter);
		jfc.addChoosableFileFilter(giffilter);
		
		// Accept image files only.
		jfc.setAcceptAllFileFilterUsed(false);
		
		// Set to bitmap file
		jfc.setFileFilter(bmpfilter);
		
		return jfc;
	}
	
	
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
		JSpinner spinner;
		
		if (name.startsWith("grid")) {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, 8, FruitEditor.GRID_SIZE*4, 2));
		} else {
			spinner = new JSpinner(
					new SpinnerNumberModel(num, 0, 99, 1));
		}
		
		spinner.setName(name);
		spinner.addChangeListener(this);
		
		return spinner;
	}
	
	private JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		btn.addActionListener(this);
		
		return btn;
	}
	
	private JToggleButton makeToggleBtn(String text, String name) {
		JToggleButton btn;
		
		btn = new JToggleButton(text);
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
		File openfile;
		
		if (tileDialog.isVisible()) {
			if (src == okBtn) {
				loadTileset();
			} else if (src == browseBtn) {
				JFileChooser open = makeImageFileChooser();
				
				int confirm = open.showOpenDialog(null);
				
				if (confirm == JFileChooser.APPROVE_OPTION) {
					try {
						// the file to be opened
						openfile = open.getSelectedFile();
						
						// set tileset filename and text to the pathname
						setTilesetFilename(openfile.getPath());
						setTilesetName(openfile.getName());
						
					} catch (Exception exc) {
						System.err.println("ERROR: Cannot load file. " +
								"Reason: " + exc.getMessage() + "\n");
						exc.printStackTrace();
					}
				}
			} else if (src == cancelBtn) {
				dispose();
			} else if (src == lockBtn) {
				setLock(lockBtn.isSelected());
				
				if (lock) {
					setGridWidth(FruitEditor.GRID_SIZE);
					setGridHeight(FruitEditor.GRID_SIZE);
				}
			}
		}
	}
	
	private void loadTileset() {
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
			/* TODO: If tileset grid size != map grid size, prompt user. 
			 * Exception is only when tileset is blank. 
			 * When a new map is formed, the tileset panel assumes that there is a tileset. */
			if (gridWidth != tilePanel.getTileset().getTileWidth() ||
					gridHeight != tilePanel.getTileset().getTileHeight()) {
				int confirm = JOptionPane.showConfirmDialog(tileDialog,
						"New tileset's grid sizes will change. Change anyway?",
						"Grid Size Change",
						JOptionPane.WARNING_MESSAGE);
				
				if (confirm == JOptionPane.OK_OPTION) {
					setupTileset();
				}
			} else {
				setupTileset();
			}
		}
	}
	
	private void setupTileset() {
		try {
			if (offsetX > 0 || offsetY > 0 || 
					paddingV > 0 || paddingH > 0) {
				tilePanel.setTileset(new Tileset(getTilesetFilename(),
						gridWidth, gridHeight, offsetX, offsetY, paddingH, paddingV));
			} else {
				tilePanel.setTileset(new Tileset(getTilesetFilename(), 
						gridWidth, gridHeight));
			}
		} catch (Exception fe) {
			System.err.println("ERROR: File not found. REASON: " + fe.getMessage());
			fe.printStackTrace();
		}
		
		dispose();
	}
	
	/**=======================================
	// stateChanged(actionEvent)
	//========================================*/
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if (tileDialog.isVisible()) {
			if (src == gridWidthText) {
				setGridWidth((Integer)gridWidthText.getValue());
				if (lock) {
					setGridHeight((Integer)gridWidthText.getValue());
				}
			} else if (src == gridHeightText) {
				setGridHeight((Integer)gridHeightText.getValue());
				if (lock) {
					setGridWidth((Integer)gridHeightText.getValue());
				}
			} else if (src == offsetXText) {
				setOffsetX((Integer)offsetXText.getValue());
			} else if (src == offsetYText) {
				setOffsetY((Integer)offsetYText.getValue());
			} else if (src == paddingHorizText) {
				setHorizPadding((Integer)paddingHorizText.getValue());
			} else if (src == paddingVertText) {
				setVertPadding((Integer)paddingVertText.getValue());
			}
		}
	}
}
