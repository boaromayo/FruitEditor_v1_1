package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class AboutDialog {
	// DIALOG.
	private JDialog aboutdialog;
	
	// DIMENSIONS.
	private final int WIDTH = 256;
	private final int HEIGHT = 128;
	
	// VARIABLES.
	private JLabel label1;
	private JLabel copyrightlabel;
	private JLabel iconlabel;
	
	private JButton okBtn;
	
	public AboutDialog(JFrame frame) {
		String title = "About FruitEditor";
		
		aboutdialog = new JDialog(frame);
		
		init();
		
		addComps();
	
		aboutdialog.pack();
		
		aboutdialog.setTitle(title);
		aboutdialog.setSize(WIDTH,HEIGHT);
		
		aboutdialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		aboutdialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		aboutdialog.setLocationRelativeTo(null);
		aboutdialog.setVisible(true);
		aboutdialog.setResizable(false);
		aboutdialog.setAlwaysOnTop(true);
		aboutdialog.setFocusable(true);
	}
	
	private void init() {
		label1 = makeLabel("FruitEditor v1.1_01", "label1");
		copyrightlabel = makeLabel("(c) 2016 Nico Poblete", "copyrightlabel");
		//iconlabel = makeLabel("Icons made by ", "iconlabel");
		iconlabel = makeLabel("Icons made by http://www.aha-soft.com//", "iconlabel");
	
		okBtn = makeOKButton("OK", "okBtn");
	}
	
	private void addComps() {
		aboutdialog.setLayout(new FlowLayout());
		
		aboutdialog.add(label1, BorderLayout.NORTH);
		aboutdialog.add(copyrightlabel, BorderLayout.CENTER);
		aboutdialog.add(iconlabel, BorderLayout.SOUTH);
		
		aboutdialog.add(okBtn);
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
	
	private JButton makeOKButton(String text, String name) {
		JButton btn;
		
		btn = new JButton(text);
		btn.setName(name);
		
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutdialog.dispose();
			}
		});
		
		return btn;
	}
}
