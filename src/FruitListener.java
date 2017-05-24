package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import java.util.*;

import java.beans.*;

public class FruitListener implements ActionListener,
	ChangeListener, PropertyChangeListener, MouseListener, MouseMotionListener, KeyListener {
	
	private FruitEditor fruitEditor;
	
	private FruitPanel fruitPanel;
	private StatusPanel statusPanel;
	
	private Map map;
	
	private Stack<PropertyChangeEvent> actions;
	
	public FruitListener(FruitEditor f) {
		fruitEditor = f;
		map = f.getMap();
		
		fruitPanel = f.getPanel();
		statusPanel = f.getStatusPanel();
		
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
			newAction();
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
			
			fruitPanel.setGrid(gridItem.isSelected());
			gridBtn.setSelected(gridItem.isSelected());
			fruitPanel.repaint();
		} else if (src == getComponent("gridBtn")) {
			JCheckBoxMenuItem gridItem = (JCheckBoxMenuItem)getComponent("gridItem");
			JToggleButton gridBtn = (JToggleButton)getComponent("gridBtn");
			
			fruitPanel.setGrid(gridBtn.isSelected());
			gridItem.setSelected(gridBtn.isSelected());
			fruitPanel.repaint();
		}
		
		// VIEW -> SCALE item listeners
		else if (src == getComponent("oneItem") ||
				src == getComponent("oneBtn")) {
			//map.setScale(Map.SCALE_ONE);
		} else if (src == getComponent("twoItem") ||
				src == getComponent("twoBtn")) {
			//map.setScale(Map.SCALE_TWO);
		} else if (src == getComponent("fourItem") ||
				src == getComponent("fourBtn")) {
			//map.setScale(Map.SCALE_FOUR);
		} else if (src == getComponent("eightItem") ||
				src == getComponent("eightBtn")) {
			//map.setScale(Map.SCALE_EIGHT);
		}
		
		// VIEW -> MODE item listeners
		else if (src == getComponent("mapModeItem") ||
				src == getComponent("mapModeBtn")) {
			//fruitEditor.setMode(EditorMode.MAP_MODE);
		} else if (src == getComponent("eventModeItem") ||
				src == getComponent("eventModeBtn")) {
			//fruitEditor.setMode(EditorMode.EVENT_MODE);
		}
		
		// DRAW item listeners
		else if (src == getComponent("pencilItem")) {
			JToggleButton pencilBtn = (JToggleButton)getComponent("pencilBtn");
			
			pencilBtn.setSelected(true);
			map.setDrawMode(DrawMode.PENCIL);
		} else if (src == getComponent("rectItem")) {
			JToggleButton rectBtn = (JToggleButton)getComponent("rectBtn");
			
			rectBtn.setSelected(true);
			map.setDrawMode(DrawMode.RECTANGLE);
		} else if (src == getComponent("circleItem")) {
			JToggleButton circleBtn = (JToggleButton)getComponent("circleBtn");
			
			circleBtn.setSelected(true);
			map.setDrawMode(DrawMode.CIRCLE);
		} else if (src == getComponent("fillItem")) {
			JToggleButton fillBtn = (JToggleButton)getComponent("fillBtn");
			
			fillBtn.setSelected(true);
			map.setDrawMode(DrawMode.FILL);
		}
		
		// DRAW toolbar listeners
		else if (src == getComponent("pencilBtn")) {
			JRadioButtonMenuItem pencilItem = (JRadioButtonMenuItem)getComponent("pencilItem");
			
			pencilItem.setSelected(true);
			map.setDrawMode(DrawMode.PENCIL);
		} else if (src == getComponent("rectBtn")) {
			JRadioButtonMenuItem rectItem = (JRadioButtonMenuItem)getComponent("rectItem");
			
			rectItem.setSelected(true);
			map.setDrawMode(DrawMode.RECTANGLE);
		} else if (src == getComponent("circleBtn")) {
			JRadioButtonMenuItem circleItem = (JRadioButtonMenuItem)getComponent("circleItem");
			
			circleItem.setSelected(true);			
			map.setDrawMode(DrawMode.CIRCLE);
		} else if (src == getComponent("fillBtn")) {
			JRadioButtonMenuItem fillItem = (JRadioButtonMenuItem)getComponent("fillItem");
			
			fillItem.setSelected(true);
			map.setDrawMode(DrawMode.FILL);
		}
		
		// TOOLKIT item listeners
		/*else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		} else if (src == getComponent("")) {
			
		}*/
		
		// HELP item listener
		else if (src == getComponent("aboutItem")) {
			aboutAction();
		}
		
		// RIGHT CLICK MENUS.
		// MapPanel item listeners
		else if (src == getComponent("rename")) {
			
		} else if (src == getComponent("shift")) {
			
		}
		
		// TilePanel item listeners
		else if (src == getComponent("openTile")) {
			openTileAction();
		} else if (src == getComponent("gridTile")) {
			gridTileAction();
		} else if (src == getComponent("closeTile")) {
			closeTileAction();
		}
	}
	
	/**==============================
	// ACTION EVENT METHODS. 
	//===============================*/
	private void newAction() {
		// Load NEW dialog.
		new NewDialog(fruitEditor);
	}
	
	private void openAction() {
		// Load OPEN dialog.
		JFileChooser open = makeFileChooser();
		
		int confirm = open.showOpenDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = open.getSelectedFile();
			String confirmStr = "Map load complete.";
			
			try {
				readText(file); // read the map file
				statusPanel.setStatus(confirmStr);
				validate();
			} catch (Exception e) {
				System.err.println("ERROR: Unable to read file.");
				e.printStackTrace();
			}
		}
	}
	
	private void saveAction() {
		JFileChooser save = makeFileChooser();
		
		int confirm = save.showSaveDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = save.getSelectedFile();
			String confirmStr = "Map saved.";
			
			try {
				writeText(file); // write onto the file
				statusPanel.setStatus(confirmStr);
				validate();
				
				//map = file;
			} catch (Exception e) {
				System.err.println("ERROR: Unable to write file " + file);
				e.printStackTrace();
			}
		}
	}
	
	private void saveAsAction() {
		JFileChooser saveAs = makeFileChooser();
		
		int confirm = saveAs.showSaveDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = saveAs.getSelectedFile();
			
			try {
				writeText(file);
				validate();
				
			} catch (Exception e) {
				System.err.println("ERROR: Unable to write file " + file);
				e.printStackTrace();
			}
		}
	}
	
	private void closeAction() {
		System.exit(0);
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
	
	private void aboutAction() {
		new AboutDialog(fruitEditor);
	}
	
	private void openTileAction() {
		
	}
	
	private void gridTileAction() {
		
	}
	
	private void closeTileAction() {
		
	}
	
	/**================================
	// MOUSE MOTION LISTENER METHODS.
	//=================================**/
	/**================================
	// mouseMoved(event) - Update if mouse moved.
	//=================================**/
	public void mouseMoved(MouseEvent e) {
		fruitPanel.mouseMoved(e);
	}
	/**================================
	// mouseHovered(event) - Update if mouse hovered.
	//=================================**/
	public void mouseHovered(MouseEvent e) {
		fruitPanel.mouseHovered(e);
	}
	
	/**================================
	// mouseDragged(event) - Update if mouse dragged.
	//=================================**/
	public void mouseDragged(MouseEvent e) {
		fruitPanel.mouseDragged(e);
	}
	
	/**================================
	// MOUSE LISTENER METHODS.
	//=================================**/
	/**================================
	// mousePressed(event) - Update if mouse pressed.
	//=================================**/
	public void mousePressed(MouseEvent e) {
		fruitPanel.mousePressed(e);
	}
	
	/**================================
	// mouseReleased(event) - Update if mouse released.
	//=================================**/
	public void mouseReleased(MouseEvent e) {
		fruitPanel.mouseReleased(e);
	}
	
	/**================================
	// mouseClicked(event) - Update if mouse clicked.
	//=================================**/
	public void mouseClicked(MouseEvent e) {
		fruitPanel.mouseClicked(e);
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	/**================================
	// KEY LISTENER METHODS.
	//=================================**/
	/**================================
	// keyPressed(event) - Update if keys are pressed.
	//=================================**/
	public void keyPressed(KeyEvent e) {
		
	}
	
	/**================================
	// keyReleased(event) - Update if keys are released from pressing.
	//=================================**/
	public void keyReleased(KeyEvent e) {
		
	}
	
	/**================================
	// keyTyped(event) - Update any typed keys.
	//=================================**/
	public void keyTyped(KeyEvent e) {
		
	}
	
	/**================================
	// STATE CHANGE METHODS
	//=================================**/
	public void stateChanged(ChangeEvent e) {
		//Object src = e.getSource();
		
		// Toggle menus depending if:
		// a map is loaded and
		// if the panel is active.
		fruitEditor.toggleMenus(fruitEditor.isPanelActive());
		fruitEditor.toggleSave(fruitEditor.isPanelActive());
		fruitEditor.toggleTools(fruitEditor.isPanelActive());
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		// For any action taken, place in a stack of actions
		actions.add(e);
	}
	
	/**===========================
	// FILE MANIPULATION METHODS. 
	//============================**/
	private void readText(File file) {
		try {
			// Load reader
			BufferedReader reader = new BufferedReader(
					new FileReader(file));
			
			// Read in the map and convert text files to map
			// Read first line to get width and height
			// Assume there are two positive integers
			String line = reader.readLine();
			String [] lines = line.split("\\s+");
			
			// Set width and height
			int cols = Integer.parseInt(lines[0]);
			int rows = Integer.parseInt(lines[1]);
			
			map = new Map(cols, rows);
			
			// Read the IDs of tiles next and store them in an int array
			int [][] ids = new int[rows][cols];
			int r, c;
			r = c = 0;
			
			while (!line.isEmpty()) {
				lines = line.split("\\s+");
				
				for (c = 0; c < lines.length; c++) {
					ids[r][c] = Integer.parseInt(lines[c]);
				}
				
				r++;
			}
			
			
			// Convert id to tiles
			//Tile t = new Tile(ids[r][c]);
			
			fruitEditor.setMap(map);
			fruitEditor.update();
			
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
		
			// Write width and height
			writer.print(map.getHeight() + " ");
			writer.println(map.getWidth());
			
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
		JFileChooser jfc = new JFileChooser();
		
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
		
		// Set to .fmp file filter
		jfc.setFileFilter(filter);
		
		return jfc;
	}
	
	private JComponent getComponent(String text) {
		return fruitEditor.getComponent(text);
	}
	
	public void validate() {
		if (fruitEditor == null) {
			return;
		}
		
		fruitEditor.validate();
	}
}
