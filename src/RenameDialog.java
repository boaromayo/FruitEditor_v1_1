package FruitEditor;

import java.awt.*;
import java.awt.Dialog.ModalityType;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class RenameDialog implements ActionListener, ChangeListener {
	// DIALOG.
	private JDialog renameDialog;
	
	// COMPONENTS.
	private JLabel mapLabel;
	private JLabel mapWidthLabel;
	private JLabel mapHeightLabel;
	
	private JTextField mapText;
	private JSpinner mapWidthText;
	private JSpinner mapHeightText;
	
	private JButton okBtn;
	private JButton cancelBtn;

	// PROPERTIES.
	private int mapWidth;
	private int mapHeight;
	
	private MapPanel mapPanel;
	
	public RenameDialog(FruitEditor f) {
		String title = "Rename/Resize Map";
		
		renameDialog = new JDialog(f.getFrame());
		
		mapPanel = f.getMapPanel();
		
		init();
		
		addComps();
		
		renameDialog.pack();
		
		renameDialog.setTitle(title);
		
		renameDialog.setModalityType(ModalityType.DOCUMENT_MODAL);
		renameDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		renameDialog.setLocationRelativeTo(null);
		renameDialog.setVisible(true);
		renameDialog.setResizable(false);
		renameDialog.setFocusable(true);
		renameDialog.requestFocus();
	}
	
	//@Override
	public void init() {
		// Initialize properties.
		mapWidth = mapPanel.getMapWidth();
		mapHeight = mapPanel.getMapHeight();
		
		// Initialize labels.
		mapLabel = makeLabel("Map Name", "mapLabel");
		mapWidthLabel = makeLabel("Width:", "mapWidthLabel");
		mapHeightLabel = makeLabel("Height:", "mapHeightLabel");
		
		// initialize text fields.
		mapText = makeTextField("mapText", mapPanel.getMapName());
		mapWidthText = makeSpinner(mapWidth, "mapWidthText");
		mapHeightText = makeSpinner(mapHeight, "mapHeightText");
		
		// initialize buttons.
		okBtn  	   	= makeButton("OK", "okBtn");
		cancelBtn  	= makeButton("Cancel", "cancelBtn");
	}
	
	/**==================================
	// addComps() - Add components
	//===================================**/
	//@Override
	protected void addComps() {
		renameDialog.setLayout(new GridLayout(3,1));
		JPanel one = new JPanel();
		JPanel size = new JPanel();
		JPanel btn = new JPanel();
		
		one.add(mapLabel); // (0,0)
		one.add(mapText); // (1,0)
		
		renameDialog.add(one);
		
		size.setLayout(new GridLayout(2,2,8,1));
		
		size.add(mapWidthLabel); // (0,2)
		size.add(mapHeightLabel); // (1,2)
		size.add(mapWidthText); // (2,2)
		size.add(mapHeightText); // (3,2)
		
		renameDialog.add(size);
		
		btn.add(okBtn); // (0,3)
		btn.add(cancelBtn); // (1,3)
		
		renameDialog.add(btn);
	}
	
	/**==================================
	// dispose() - Dispose dialog.
	//===================================**/
	public void dispose() {
		renameDialog.dispose();
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
	
	/**==================================
	// HELPER METHODS.
	//===================================**/
	protected JLabel makeLabel(String text, String name) {
		JLabel lbl;
		
		lbl = new JLabel(text);
		lbl.setName(name);
		
		return lbl;
	}
	
	protected JTextField makeTextField(String name, String text) {
		return makeTextField(name, text, 30);
	}
	
	private JTextField makeTextField(String name, String text, int width) {
		JTextField txtField;
		
		txtField = new JTextField(text,width);
		txtField.setName(name);
		txtField.addActionListener(this);
		//txtField.addKeyListener(this);
		
		return txtField;
	}
	
	protected JSpinner makeSpinner(int num, String name) {
		JSpinner spinner;
		
		spinner = new JSpinner(
					new SpinnerNumberModel(num, 8, Map.MAP_SIZE, 1));
		
		spinner.setName(name);
		spinner.addChangeListener(this);
		//spinner.addKeyListener(this);
		
		return spinner;
	}
	
	protected JButton makeButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		
		btn.addActionListener(this);
		//btn.addKeyListener(this);
		
		return btn;
	}
	
	/**=======================================
	// actionPerformed(actionEvent)
	//========================================*/
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (renameDialog.isVisible()) {
			if (btn == okBtn) {
				// if map name or tileset name is blank, put warning prompt
				// otherwise, set map name and set map dimensions up
				if (getMapText().matches("^\\s+") || getMapText().equals("")) {
					JOptionPane.showMessageDialog(renameDialog, 
							"Enter a name for this map.", 
							"Map Name Blank", 
							JOptionPane.WARNING_MESSAGE);
				} else if (getMapWidth() < 0 || getMapHeight() < 0) {
					// put an error prompt if width and height are less than 0,
					// though this might not be necessary since the size text fields
					// don't take in sizes less than 8.
					JOptionPane.showMessageDialog(null, 
							"Cannot take in values less than 0.", 
							"Integer error", 
							JOptionPane.ERROR_MESSAGE);
				} else {
					// refactor the map name and size
					mapPanel.setMapName(getMapText());
					mapPanel.setMapSize(getMapWidth(), getMapHeight());
					
					setMapText(null); // Leave map text field blank.
					
					dispose(); // Remove new map dialog.
				}
			} else if (btn == cancelBtn) {
				dispose();
			}
		}
	}
	
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if (renameDialog.isVisible()) {
			if (src == mapWidthText) {
				setMapWidth((Integer)mapWidthText.getValue());
				System.out.println(mapWidth); // test debug here
			} else if (src == mapHeightText) {
				setMapHeight((Integer)mapHeightText.getValue());
				System.out.println(mapHeight); // test debug here
			}
		}
	}

}
