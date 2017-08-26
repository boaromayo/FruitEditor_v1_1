package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class GridTileDialog implements ActionListener, ChangeListener {
	// DIALOG.
	private JDialog dialog;
	
	// DIALOG SIZE.
	private final int WIDTH = 360;
	private final int HEIGHT = 200;
	
	// COMPONENTS.
	private JLabel gridWidthLabel;
	private JLabel gridHeightLabel;
	private JLabel offsetXLabel;
	private JLabel offsetYLabel;
	private JLabel paddingVLabel;
	private JLabel paddingHLabel;
	
	private JSpinner gridWidthText;
	private JSpinner gridHeightText;
	private JSpinner offsetXText;
	private JSpinner offsetYText;
	private JSpinner paddingVText;
	private JSpinner paddingHText;
	
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
	
	public GridTileDialog(FruitEditor f) {
		String title = "Tileset Grid Settings";
		
		dialog = new JDialog(f.getFrame());
		
		tilePanel = f.getTilePanel();

		// Initialize dialog.
		init();
		addComponents();
		
		// Set up dialog.
		dialog.pack();
		
		dialog.setTitle(title);
		dialog.setSize(WIDTH,HEIGHT);
		
		dialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setLocationRelativeTo(null);
		dialog.setResizable(false);
		dialog.setVisible(true);
		dialog.setFocusable(true);
	}
	
	private void init() {
		// Set grid settings.
		gridWidth = tilePanel.getGridWidth();
		gridHeight = tilePanel.getGridHeight();
		
		// Initialize labels.
		gridWidthLabel		= makeLabel("Grid W: ", "gridWidth");
		gridHeightLabel 	= makeLabel("Grid H: ", "gridHeight");
		offsetXLabel		= makeLabel("X offset: ", "offsetX");
		offsetYLabel		= makeLabel("Y offset: ", "offsetY");
		paddingVLabel		= makeLabel("Vertical: ", "paddingV");
		paddingHLabel		= makeLabel("Horizontal: ", "paddingH");
		
		// Initialize text fields.
		gridWidthText		= makeSpinner(gridWidth, "gridWText");
		gridHeightText		= makeSpinner(gridHeight, "gridHText");
		offsetXText			= makeSpinner(offsetX, "offsetXText");
		offsetYText			= makeSpinner(offsetY, "offsetYText");
		paddingVText		= makeSpinner(paddingV, "paddingVTxt");
		paddingHText		= makeSpinner(paddingH, "paddingHTxt");
		
		// Initialize buttons.
		okBtn				= makeButton("OK", "okBtn");
		cancelBtn			= makeButton("Cancel", "cancelBtn");
		lockBtn				= makeToggleBtn("Lock", "lockBtn");
		
		// Toggle lock button based on current lock state.
		lockBtn.setSelected(lock);
	}
	
	private void addComponents() {
		dialog.setLayout(new BoxLayout(dialog.getContentPane(),
				BoxLayout.PAGE_AXIS));
		JPanel all = new JPanel();
		JPanel gridPanel = new JPanel();
		JPanel offsetPanel = new JPanel();
		JPanel paddingPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		
		all.setLayout(new GridLayout(1,3));
		
		gridPanel.setBorder(new TitledBorder("Grid Size"));
		gridPanel.setLayout(new GridLayout(4,1));
		
		gridPanel.add(gridWidthLabel);
		gridPanel.add(gridWidthText);
		gridPanel.add(gridHeightLabel);
		gridPanel.add(gridHeightText);
		
		all.add(gridPanel);
		
		offsetPanel.setBorder(new TitledBorder("Offset"));
		offsetPanel.setLayout(new GridLayout(4,1));
		
		offsetPanel.add(offsetXLabel);
		offsetPanel.add(offsetXText);
		offsetPanel.add(offsetYLabel);
		offsetPanel.add(offsetYText);
		
		all.add(offsetPanel);
		
		paddingPanel.setBorder(new TitledBorder("Padding"));
		paddingPanel.setLayout(new GridLayout(4,1));
		
		paddingPanel.add(paddingVLabel);
		paddingPanel.add(paddingVText);
		paddingPanel.add(paddingHLabel);
		paddingPanel.add(paddingHText);
		
		all.add(paddingPanel);
		
		dialog.add(all);
		
		btnPanel.setLayout(new FlowLayout());
		
		btnPanel.add(okBtn);
		btnPanel.add(cancelBtn);
		btnPanel.add(lockBtn);
		
		dialog.add(btnPanel, BorderLayout.SOUTH);
	}
	
	public void dispose() {
		dialog.dispose();
	}
	
	/**==================================
	// PROPERTY SETTER METHODS.
	//===================================**/
	public void setGridWidth(int gw) {
		gridWidth = gw;
	}
	
	public void setGridHeight(int gh) {
		gridHeight = gh;
	}
	
	public void setOffsetX(int ox) {
		offsetX = ox;
	}
	
	public void setOffsetY(int oy) {
		offsetY = oy;
	}
	
	public void setVertPadding(int sy) {
		paddingV = sy;
	}
	
	public void setHorizPadding(int sx) {
		paddingH = sx;
	}
	
	public void setLock(boolean l) {
		lock = l;
	}
	
	/**==================================
	// PROPERTY GETTER METHODS.
	//===================================**/
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (dialog.isVisible()) {
			if (src == okBtn) {
				setupTileset();
			} else if (src == cancelBtn) {
				dispose();
			} else if (src == lockBtn) {
				setLock(lockBtn.isSelected());
				
				if (lock) {
					setGridHeight(getGridWidth());
				}
			}
		}
	}
	
	private void setupTileset() {
		Tileset tileset = tilePanel.getTileset();
		tilePanel.setTileset(new Tileset(tileset.getTilesetPath(), 
				gridWidth, gridHeight, offsetX, offsetY, paddingV, paddingH));
		
		dispose();
	}
	
	@Override
	public void stateChanged(ChangeEvent e) {
		Object src = e.getSource();
		
		if (dialog.isVisible()) {
			if (src == gridWidthText) {
				setGridWidth((Integer)gridWidthText.getValue());
				if (lock) {
					setGridHeight((Integer)gridHeightText.getValue());
				}
			} else if (src == gridHeightText) {
				setGridHeight((Integer)gridHeightText.getValue());
				if (lock) {
					setGridWidth((Integer)gridWidthText.getValue());
				}
			} else if (src == offsetXText) {
				setOffsetX((Integer)offsetXText.getValue());
			} else if (src == offsetYText) {
				setOffsetY((Integer)offsetYText.getValue());
			} else if (src == paddingVText) {
				setVertPadding((Integer)paddingVText.getValue());
			} else if (src == paddingHText) {
				setHorizPadding((Integer)paddingHText.getValue());
			}
		}
	}
}
