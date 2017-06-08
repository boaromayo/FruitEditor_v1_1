package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ShiftDialog extends JDialog implements ActionListener, ChangeListener {
	// DIALOG SIZE.
	private final int WIDTH = 240;
	private final int HEIGHT = 120;
	
	// LIMIT.
	private final int MAP_SHIFT = 99;
	
	// COMPONENTS.
	private JLabel shiftVLabel;
	private JLabel shiftHLabel;
	
	private JSpinner shiftVText;
	private JSpinner shiftHText;
	
	private JButton okBtn;
	private JButton cancelBtn;
	
	// PROPERTIES.
	private int shiftV;
	private int shiftH;
	
	private MapPanel mapPanel;
	
	public ShiftDialog(FruitEditor f) {		
		super(f.getFrame());
		
		String title = "Shift Map";
		
		mapPanel = f.getMapPanel();
	
		// Set properties and components.
		init();
		
		addComps();
		
		// Set specs for dialog box.
		pack();
		
		setTitle(title);
		setSize(WIDTH,HEIGHT);
		
		setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setFocusable(true);
		setResizable(false);
	}
	
	private void init() {
		// Initialize shifts.
		shiftV = 0;
		shiftH = 0;
		
		// Initialize components.
		shiftVLabel = makeLabel("Up/Down", "shiftV");
		shiftHLabel = makeLabel("Left/Right", "shiftH");
		
		shiftVText = makeSpinner(shiftV, "shiftVText");
		shiftHText = makeSpinner(shiftH, "shiftHText");
		
		okBtn = makeButton("OK", "okBtn");
		cancelBtn = makeButton("Cancel", "cancelBtn");
	}
	
	private void addComps() {
		setLayout(new GridLayout(3,2));
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel btn = new JPanel();
		
		one.add(shiftVLabel); // (0,0)
		one.add(shiftHLabel); // (1,0)
		
		add(one);
		
		two.add(shiftVText); // (0,1)
		two.add(shiftHText); // (1,1)
		
		add(two);
		
		btn.add(okBtn); // (0,2)
		btn.add(cancelBtn); // (1,2)
		
		add(btn);
	}
	
	public void setVerticalShift(int s) {
		shiftV = s;
		shiftVText.setValue(s);
	}
	
	public void setHorizontalShift(int s) {
		shiftH = s;
		shiftHText.setValue(s);
	}
	
	public int getVerticalShift() {
		return (Integer)shiftVText.getValue();
	}
	
	public int getHorizontalShift() {
		return (Integer)shiftHText.getValue();
	}
	
	private JLabel makeLabel(String text, String name) {
		JLabel lbl = new JLabel(text);
		lbl.setName(name);
		return lbl;
	}
	
	private JSpinner makeSpinner(int n, String name) {
		JSpinner spinner;
		
		spinner = new JSpinner(
				new SpinnerNumberModel(n, -MAP_SHIFT, MAP_SHIFT, 1));
		
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
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == okBtn) {
			// Use mapPanel to shift map.
			// For now dispose dialog box.
		} else if (src == cancelBtn) {
			dispose();
		}
 	}
	
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if (src == shiftVText) {
			setVerticalShift((Integer)shiftVText.getValue());
			System.out.println(shiftV); // print for debug purposes
		} else if (src == shiftHText) {
			setHorizontalShift((Integer)shiftHText.getValue());
			System.out.println(shiftH);
		}
	}
}
