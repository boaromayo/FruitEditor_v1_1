package FruitEditor;

import java.awt.event.*;

import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import java.util.*;

import java.beans.*;

public class FruitListener implements ActionListener,
	ChangeListener, PropertyChangeListener, WindowListener {
	
	private FruitEditor fruitEditor;
	
	//private Stack<ChangeEvent> changes;
	private Stack<PropertyChangeEvent> actions;
	
	public FruitListener(FruitEditor f) {
		fruitEditor = f;
		
		actions = new Stack<PropertyChangeEvent>();
	}
	
	/**==========================
	// actionPerformed(event) - Action event listener
	//===========================**/
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
				
		// FILE listeners.
		if (src == getComponent("newBtn") || 
				src == getComponent("newItem")) {
			new NewDialog(fruitEditor); // Load NEW dialog.
		} else if (src == getComponent("openBtn") ||
				src == getComponent("openItem")) {
			openAction();
		} else if (src == getComponent("saveBtn") ||
				src == getComponent("saveItem")) {
			saveAction();
		} else if (src == getComponent("saveAsItem")) {
			saveAsAction();
		} else if (src == getComponent("closeItem")) {
			closeAction();
		}
		
		// EDIT -> FIX
		else if (src == getComponent("undoItem") ||
				src == getComponent("undoBtn")) {
			undo();
		} else if (src == getComponent("redoItem") ||
				src == getComponent("redoBtn")) {
			redo();
		}
		
		// EDIT listeners
		else if (src == getComponent("cutItem") ||
				src == getComponent("cutRt") || 
				src == getComponent("cutBtn")) {
			cutAction();
		} else if (src == getComponent("copyItem") ||
				src == getComponent("copyRt") ||
				src == getComponent("copyBtn")) {
			copyAction();
		} else if (src == getComponent("pasteItem") ||
				src == getComponent("pasteRt") ||
				src == getComponent("pasteBtn")) {
			pasteAction();
		} else if (src == getComponent("deleteItem") ||
				src == getComponent("deleteRt") ||
				src == getComponent("deleteBtn")) {
			deleteAction();
		}
		
		// VIEW -> GRID
		else if (src == getComponent("gridItem")) {
			JCheckBoxMenuItem gridItem = (JCheckBoxMenuItem)getComponent("gridItem");
			JToggleButton gridBtn = (JToggleButton)getComponent("gridBtn");
			
			gridBtn.setSelected(gridItem.isSelected());
			setGrid(gridItem.isSelected());
		} else if (src == getComponent("gridBtn")) {
			JCheckBoxMenuItem gridItem = (JCheckBoxMenuItem)getComponent("gridItem");
			JToggleButton gridBtn = (JToggleButton)getComponent("gridBtn");
			
			gridItem.setSelected(gridBtn.isSelected());
			setGrid(gridBtn.isSelected());
		}
		
		// VIEW -> SHIFT
		else if (src == getComponent("shiftBtn")) {
			new ShiftDialog(fruitEditor); // Load Shift Map dialog.
		}
		
		// VIEW -> SCALE item listeners
		else if (src == getComponent("oneItem") ||
				src == getComponent("oneBtn")) {
			JRadioButtonMenuItem oneItem = (JRadioButtonMenuItem) getComponent("oneItem");
			JToggleButton oneBtn = (JToggleButton) getComponent("oneBtn");
			oneItem.setSelected(true);
			oneBtn.setSelected(true);
			//setScale(Map.SCALE_ONE);
		} else if (src == getComponent("twoItem") ||
				src == getComponent("twoBtn")) {
			JRadioButtonMenuItem twoItem = (JRadioButtonMenuItem) getComponent("twoItem");
			JToggleButton twoBtn = (JToggleButton) getComponent("twoBtn");
			twoItem.setSelected(true);
			twoBtn.setSelected(true);
			//setScale(Map.SCALE_TWO);
		} else if (src == getComponent("fourItem") ||
				src == getComponent("fourBtn")) {
			JRadioButtonMenuItem fourItem = (JRadioButtonMenuItem) getComponent("fourItem");
			JToggleButton fourBtn = (JToggleButton) getComponent("fourBtn");
			fourItem.setSelected(true);
			fourBtn.setSelected(true);
			//setScale(Map.SCALE_FOUR);
		} else if (src == getComponent("eightItem") ||
				src == getComponent("eightBtn")) {
			JRadioButtonMenuItem eightItem = (JRadioButtonMenuItem) getComponent("eightItem");
			JToggleButton eightBtn = (JToggleButton) getComponent("eightBtn");
			eightItem.setSelected(true);
			eightBtn.setSelected(true);
			//setScale(Map.SCALE_EIGHT);
		}
		
		// VIEW -> MODE item and button listeners
		else if (src == getComponent("mapModeItem")) {
			JToggleButton mapModeBtn = (JToggleButton) getComponent("mapModeBtn");
			mapModeBtn.setSelected(true);
			setMode(EditorMode.MAP_MODE);
		} else if (src == getComponent("eventModeItem")) {
			JToggleButton eventModeBtn = (JToggleButton) getComponent("eventModeBtn");
			eventModeBtn.setSelected(true);
			setMode(EditorMode.EVENT_MODE);
		} else if (src == getComponent("mapModeBtn")) {
			JRadioButtonMenuItem mapModeItem = (JRadioButtonMenuItem) getComponent("mapModeItem");
			mapModeItem.setSelected(true);
			setMode(EditorMode.MAP_MODE);
		} else if (src == getComponent("eventModeBtn")) {
			JRadioButtonMenuItem eventModeItem = (JRadioButtonMenuItem) getComponent("eventModeItem");
			eventModeItem.setSelected(true);
			setMode(EditorMode.EVENT_MODE);
		}
		
		// DRAW item listeners
		else if (src == getComponent("pencilItem") ||
				src == getComponent("pencilBtn")) {
			JRadioButtonMenuItem pencilItem = (JRadioButtonMenuItem)getComponent("pencilItem");
			JToggleButton pencilBtn = (JToggleButton)getComponent("pencilBtn");
			pencilItem.setSelected(true);
			pencilBtn.setSelected(true);
			setDrawMode(DrawMode.PENCIL);
		} else if (src == getComponent("rectItem") ||
				src == getComponent("rectBtn")) {
			JRadioButtonMenuItem rectItem = (JRadioButtonMenuItem)getComponent("rectItem");
			JToggleButton rectBtn = (JToggleButton)getComponent("rectBtn");
			rectItem.setSelected(true);
			rectBtn.setSelected(true);
			setDrawMode(DrawMode.RECTANGLE);
		} else if (src == getComponent("circleItem") ||
				src == getComponent("circleBtn")) {
			JRadioButtonMenuItem circleItem = (JRadioButtonMenuItem)getComponent("circleItem");
			JToggleButton circleBtn = (JToggleButton)getComponent("circleBtn");
			circleItem.setSelected(true);
			circleBtn.setSelected(true);
			setDrawMode(DrawMode.CIRCLE);
		} else if (src == getComponent("fillItem") ||
				src == getComponent("fillBtn")) {
			JRadioButtonMenuItem fillItem = (JRadioButtonMenuItem)getComponent("fillItem");
			JToggleButton fillBtn = (JToggleButton)getComponent("fillBtn");
			fillItem.setSelected(true);
			fillBtn.setSelected(true);
			setDrawMode(DrawMode.FILL);
		}
		
		// TOOLKIT item listeners
		/*else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		}*/
		
		// HELP item listener
		else if (src == getComponent("aboutItem")) {
			new AboutDialog(fruitEditor);
		}
		
		fruitEditor.update();
	}
	
	/**==============================
	// ACTION EVENT METHODS. 
	//===============================*/
	private void openAction() {
		// Load OPEN dialog.
		JFileChooser open = makeFileChooser();
		
		int confirm = open.showOpenDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = open.getSelectedFile();
			try {
				readText(file);
				fruitEditor.setActiveFile(file);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to read file " + file.getPath());
				e.printStackTrace();
			}
		}
	}
	
	private void saveAction() {
		// If active file is blank, go to save prompt.
		if (fruitEditor.getActiveFile() == null) {
			saveAsAction();
			return;
		}
		
		File file = fruitEditor.getActiveFile();
		try {
			writeText(file);
			fruitEditor.setActiveFile(file);
		} catch (Exception e) {
			System.err.println("ERROR: Unable to write file " + file.getPath());
			e.printStackTrace();
		}
	}

	private void saveAsAction() {
		JFileChooser saveAs = makeFileChooser();
		
		int confirm = saveAs.showSaveDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = saveAs.getSelectedFile();
			try {
				writeText(file);
				fruitEditor.setActiveFile(file);
			} catch (Exception e) {
				System.err.println("ERROR: Unable to write file " + file.getPath());
				e.printStackTrace();
			}
		}
	}
	
	private void closeAction() {
		// If panel active and file blank, prompt user warning.
		if (fruitEditor.getActiveFile() == null && 
				fruitEditor.isPanelActive()) {
			int confirm = JOptionPane.showInternalConfirmDialog(
				fruitEditor.getFrame(), 
				"Save before closing?",
				"Close FruitEditor",
				JOptionPane.YES_NO_CANCEL_OPTION);
		
			if (confirm == JOptionPane.YES_OPTION) {
				saveAction();
				System.exit(0);
			} else if (confirm == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		} else {
			System.exit(0);
		}
	}
	
	private void undo() {
		fruitEditor.update();
	}
	
	private void redo() {
		fruitEditor.update();
	}
	
	private void cutAction() {
		
	}
	
	private void copyAction() {
		
	}
	
	private void pasteAction() {
		
	}
	
	private void deleteAction() {
		
	}
	
	/**================================
	// SHORTCUT METHODS.
	//=================================*/
	private void setGrid(boolean grid) {
		fruitEditor.getMapPanel().setGrid(grid);
	}
	
	private void setMode(EditorMode e) {
		fruitEditor.getMapPanel().setMode(e);
	}
	
	/*private void setScale(int scale) {
		Map map = fruitEditor.getMap();
		map.setScale(scale);
	}*/
	
	private void setDrawMode(DrawMode d) {
		Map map = fruitEditor.getMap();
		map.setDrawMode(d);
	}
	
	/**================================
	// STATE CHANGE METHODS
	//=================================**/
	public void stateChanged(ChangeEvent e) {
		
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		// For any action taken, place in a stack of actions
		actions.add(e);
	}
	
	/**================================
	// WINDOW METHODS.
	//=================================**/
	public void windowOpened(WindowEvent e) {
		
	}
	
	public void windowClosing(WindowEvent e) {
		closeAction();
	}
	
	public void windowClosed(WindowEvent e) {
		
	}
	
	public void windowActivated(WindowEvent e) {
		
	}
	
	public void windowDeactivated(WindowEvent e) {
		
	}
	
	public void windowIconified(WindowEvent e) {
		
	}
	
	public void windowDeiconified(WindowEvent e) {
		
	}
	
	/**===========================
	// FILE MANIPULATION METHODS. 
	//============================**/
	private void readText(File file) {
		try {
			// Load reader
			BufferedReader reader = new BufferedReader(
					new FileReader(file));
			Map map;
			Tileset tileset;
			
			// Read in the map and convert text files to map
			// Read first line to get map name
			String line = reader.readLine();
			
			String mapName = line;
			
			// Read second line to get tileset pathname
			line = reader.readLine();
			
			// Fetch tileset pathname
			String path = line;
			
			// Read second line to get map's parameters (ie, 8 8 32 32, the map and tile sizes)
			line = reader.readLine();
			String [] lines = line.split("\\s+");
			
			// Set the parameters
			int cols = Integer.parseInt(lines[0]);
			int rows = Integer.parseInt(lines[1]);
			int gw = Integer.parseInt(lines[2]);
			int gh = Integer.parseInt(lines[3]);
			
			map = new Map(cols,rows,gw,gh);
			tileset = new Tileset(path,gw,gh);
			
			// Read the IDs of tiles next and store them in an array
			int [][] ids = new int[rows][cols];
			int r, c;
			r = 0;
			
			while (line != null) {
				line = reader.readLine();
				
				if (line == null) 
					break;
				
				lines = line.split("\\s+");
				for (c = 0; c < lines.length; c++) {
					ids[r][c] = Integer.parseInt(lines[c]);
				}
				
				r++;
			}
			
			// Now make the map based on the list read earlier.
			for (r = 0; r < rows; r++) {
				for (c = 0; c < cols; c++) {
					if (ids[r][c] == -1) // Skip any blank tiles on map 
						continue; 
					
					map.setTile(c,r,tileset.getTile(ids[r][c]));
				}
			}
			
			fruitEditor.getMapPanel().setMap(map);
			fruitEditor.getMapPanel().setMapName(mapName);
			fruitEditor.getTilePanel().setTileset(tileset);
			
			// Close reader to cleanup
			reader.close();
		} catch (Exception e) {
			System.err.println("ERROR: Cannot read file " + file);
			e.printStackTrace();
		}
	}
	
	private void writeText(File file) {
		try {
			// Load writer
			PrintWriter writer = new PrintWriter(
				new BufferedWriter(new FileWriter(file)));
			Map map = fruitEditor.getMap();
			
			// Write in map name and tileset path.
			writer.println(map.getName());
			writer.println(fruitEditor.getTileset().getTilesetPath());
			
			// Write map parameters in file.
			writer.print(map.getWidth() + " ");
			writer.print(map.getHeight() + " ");
			writer.print(map.getGridWidth() + " ");
			writer.println(map.getGridHeight());
			
			// Write integer reps of tiles in for one layer only
			int [][] ids = map.getMapIntArray2();
			
			for (int r = 0; r < ids.length; r++) {
				for (int c = 0; c < ids[0].length; c++) {
					writer.print(ids[r][c] + " ");
				}
				writer.println();
			}
			
			// Flush and close writer as cleanup
			writer.flush();
			writer.close();
		} catch (Exception e) {
			System.err.println("ERROR: Unable to write file " + file);
			e.printStackTrace();
		}
	}
	
	/**============================
	// HELPER METHODS.
	//=============================**/
	/**============================
	// makeFileChooser() - make file chooser and filter files to take in FruitEditor files.
	//=============================**/
	private JFileChooser makeFileChooser() {
		JFileChooser jfc = new JFileChooser("../FruitEditor_v1_1");
		
		// FruitEditor and text files only.
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("FruitEditor Maps (*.fmp)", "fmp");
		FileNameExtensionFilter textfilter =
				new FileNameExtensionFilter("Text Files (*.txt)", "txt");
		
		// Add file filters.
		jfc.addChoosableFileFilter(filter);
		jfc.addChoosableFileFilter(textfilter);
		
		// Accept text and .fmp files only
		jfc.setAcceptAllFileFilterUsed(false);
		
		// Set to txt file filter
		jfc.setFileFilter(textfilter);
		
		// Set map name as default file name.
		if (fruitEditor.getMapPanel().getMapName() != null) {
			if (jfc.getFileFilter() == filter) {
				jfc.setSelectedFile(new File(fruitEditor.getMapPanel().getMapName() + ".fmp"));
			} else if (jfc.getFileFilter() == textfilter) {
				jfc.setSelectedFile(new File(fruitEditor.getMapPanel().getMapName() + ".txt"));
			}
		}
		
		return jfc;
	}
	
	private JComponent getComponent(String text) {
		return fruitEditor.getComponent(text);
	}
}
