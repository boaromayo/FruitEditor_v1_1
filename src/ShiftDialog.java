package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.event.*;

public class ShiftDialog extends JDialog implements ActionListener, ChangeListener, ItemListener {
	// DIALOG SIZE.
	private final int WIDTH = 240;
	private final int HEIGHT = 120;
	
	// LIMIT.
	private final int MAP_SHIFT_LIMIT = 99;
	
	// COMPONENTS.
	private JLabel directionLabel;
	private JLabel shiftLabel;
	
	private JComboBox directionBox;
	
	private JSpinner shiftText;
	
	private JButton okBtn;
	private JButton cancelBtn;
	
	// PROPERTIES.
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int DOWN = 3;
	
	private int direction;
	private int shift;
	
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
		setResizable(false);
		setVisible(true);
		setFocusable(true);
	}
	
	private void init() {
		// Initialize shift settings.
		direction = DOWN;
		shift = 0;
		
		// Initialize components.
		directionLabel = makeLabel("Direction:", "directionLabel");
		shiftLabel = makeLabel("Value:", "shiftLabel");
		
		directionBox = makeComboBox(direction, "directionBox");
		
		shiftText = makeSpinner(shift, "shiftText");
		
		okBtn = makeButton("OK", "okBtn");
		cancelBtn = makeButton("Cancel", "cancelBtn");
	}
	
	private void addComps() {
		setLayout(new GridLayout(1,2));
		JPanel west = new JPanel();
		JPanel one = new JPanel();
		JPanel two = new JPanel();
		JPanel btn = new JPanel();
		
		west.setLayout(new GridLayout(2,1));
		
		one.setLayout(new GridLayout(2,1));
		
		one.add(directionLabel); // (0,0)
		one.add(directionBox); // (0,1)
		
		west.add(one);
		
		two.setLayout(new GridLayout(2,1));
		
		two.add(shiftLabel); // (0,2)
		two.add(shiftText); // (0,3)
		
		west.add(two);
		
		add(west);
		
		btn.add(okBtn); // (1,0)
		btn.add(cancelBtn); // (1,1)
		
		add(btn);
	}
	
	public void setShift(int n) {
		shift = n;
		shiftText.setValue(n);
	}
	
	public void setDirection(int dir) {
		direction = dir;
	}
	
	private JLabel makeLabel(String text, String name) {
		JLabel lbl = new JLabel(text);
		lbl.setName(name);
		return lbl;
	}
	
	private JComboBox makeComboBox(int dir, String name) {
		JComboBox comboBox;
		
		comboBox = new JComboBox();
		
		comboBox.addItem("Up");
		comboBox.addItem("Left");
		comboBox.addItem("Right");
		comboBox.addItem("Down");
		
		comboBox.setSelectedIndex(dir);
		comboBox.setName(name);
		comboBox.addItemListener(this);
		
		return comboBox;
	}
  
	private JSpinner makeSpinner(int n, String name) {
		JSpinner spinner;
		
		spinner = new JSpinner(
				new SpinnerNumberModel(n, 0, MAP_SHIFT_LIMIT, 1));
		
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
	
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == okBtn) {
			mapPanel.shiftMap(direction,shift);
			dispose();
		} else if (src == cancelBtn) {
			dispose();
		}
 	}
	
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if (src == shiftText) {
			setShift((Integer)shiftText.getValue());
			System.out.println(shift); // print for debug purposes
		}
	}

	public void itemStateChanged(ItemEvent e) {
		Object src = e.getSource();
		
		if (src == directionBox) {
			setDirection(directionBox.getSelectedIndex());
			System.out.println(direction);
		}
	}
}
