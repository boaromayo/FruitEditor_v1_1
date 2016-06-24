package FruitEditor;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class AboutDialog extends JDialog {
	
	private final int WIDTH = 256;
	private final int HEIGHT = 128;
	
	private JLabel label1;
	private JLabel copyrightlabel;
	private JLabel iconlabel;
	
	public AboutDialog() {
		init();
		
		addComps();
	
		pack();
		
		setTitle("About FruitEditor");
		setSize(WIDTH,HEIGHT);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setAlwaysOnTop(true);
		setFocusable(true);
	}
	
	private void init() {
		label1 = makeLabel("FruitEditor v1.1_01", "label1");
		copyrightlabel = makeLabel("(c) 2016 Nico Poblete", "copyrightlabel");
		//iconlabel = makeLabel("Icons made by ", "iconlabel");
		iconlabel = makeLabel("Icons made by http://www.aha-soft.com//", "iconlabel");
	}
	
	private void addComps() {
		setLayout(new BorderLayout());
		
		add(label1, BorderLayout.NORTH);
		add(copyrightlabel, BorderLayout.CENTER);
		add(iconlabel, BorderLayout.SOUTH);
	}
	
	/**==============================
	// HELPER METHODS.
	//===============================**/
	private JLabel makeLabel(String text, String name) {
		JLabel lbl;
		
		lbl = new JLabel(text, JLabel.CENTER);
		lbl.setName(name);
		
		return lbl;
	}
}
