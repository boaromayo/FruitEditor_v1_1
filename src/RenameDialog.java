package FruitEditor;

import java.awt.event.*;

import javax.swing.*;

public class RenameDialog extends NewDialog {

	public RenameDialog(JFrame frame) {
		super(frame);
		
		String title = "Rename/Resize Map";
		
		newDialog.setTitle(title);
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
				if (getMapText().equals("\\s+") || getMapText() == null) {
					JOptionPane.showMessageDialog(null, 
							"Enter a name for this map.", 
							"Map Name Blank", 
							JOptionPane.WARNING_MESSAGE);
				} else if (getWidth() < 0 || getHeight() < 0) {
					JOptionPane.showMessageDialog(null, 
							"Cannot take in values less than 0.", 
							"Integer error", 
							JOptionPane.ERROR_MESSAGE);
				} else {
					// refactor the map name and size
					mapPanel.setMapName(getMapText());
					mapPanel.setMapSize(getWidth(), getHeight());
					
					setMapText(null); // Leave map text field blank.
					
					dispose(); // Remove new map dialog.
				}
			} else if (btn == cancelBtn) {
				dispose();
			}
		}
	}

}
