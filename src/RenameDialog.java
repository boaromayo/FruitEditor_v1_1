package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class RenameDialog extends NewDialog {

	public RenameDialog(FruitEditor f) {
		super(f);
		
		String title = "Rename/Resize Map";
		
		newDialog.setTitle(title);
	}
	
	@Override
	public void init() {
		// Initialize dimensions.
		
		// Initialize labels.
		mapLabel = makeLabel("Map Name", "mapLabel");
		mapWidthLabel = makeLabel("Width:", "mapWidthLabel");
		mapHeightLabel = makeLabel("Height:", "mapHeightLabel");
		
		// initialize text fields.
		mapText = makeTextField("mapText");
		mapWidthText = makeSpinner(getMapWidth(), "mapWidthText");
		mapHeightText = makeSpinner(getMapHeight(), "mapHeightText");
		
		// initialize buttons.
		okBtn  	   	= makeButton("OK", "okBtn");
		cancelBtn  	= makeButton("Cancel", "cancelBtn");
	}
	
	/**==================================
	// addComps() - Add components
	//===================================**/
	@Override
	protected void addComps() {
		newDialog.setLayout(new GridLayout(3,1,2,2));
		JPanel one = new JPanel();
		JPanel size = new JPanel();
		JPanel btn = new JPanel();
		
		one.add(mapLabel); // (0,0)
		one.add(mapText); // (1,0)
		
		newDialog.add(one);
		
		size.setLayout(new GridLayout(2,2,8,1));
		
		size.add(mapWidthLabel); // (0,2)
		size.add(mapHeightLabel); // (1,2)
		size.add(mapWidthText); // (2,2)
		size.add(mapHeightText); // (3,2)
		
		newDialog.add(size);
		
		btn.add(okBtn); // (0,3)
		btn.add(cancelBtn); // (1,3)
		
		newDialog.add(btn);
	}
	
	/**=======================================
	// actionPerformed(actionEvent)
	//========================================*/
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		if (newDialog.isVisible()) {
			if (btn == okBtn) {
				// if map name or tileset name is blank, put warning prompt
				// otherwise, set map name and set map dimensions up
				if (getMapText().matches("^\\s+") || getMapText().equals("")) {
					JOptionPane.showMessageDialog(newDialog, 
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
					
					fruitEditor.validate();
					
					setMapText(null); // Leave map text field blank.
					
					dispose(); // Remove new map dialog.
				}
			} else if (btn == cancelBtn) {
				dispose();
			}
		}
	}

}
