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
	PropertyChangeListener, MouseListener, MouseMotionListener, KeyListener {
	
	private FruitEditor fruitEditor;
	
	private MapPanel mapPanel;
	private TilePanel tilePanel;
	
	private Map map;
	
	private Stack<PropertyChangeEvent> actions;
	
	public FruitListener(FruitEditor f) {
		fruitEditor = f;
		//map = fruitEditor.getMap();
		
		//mapPanel = fruitEditor.getMapPanel();
		//tilePanel = f.getTilePanel();
		
		actions = new Stack<PropertyChangeEvent>();
	}
	
	/**==========================
	// actionPerformed(event) - Event listener
	//===========================**/
	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();
		
		// FILE listeners.
		if (btn == getComponent("newBtn") || 
				btn == getComponent("newItem")) {
			newAction();
		} else if (btn == getComponent("openBtn") ||
				btn == getComponent("openItem")) {
			openAction();
		} else if (btn == getComponent("saveBtn") ||
				btn == getComponent("saveItem")) {
			saveAction();
		} else if (btn == getComponent("saveAsItem")) {
			saveAsAction();
		} else if (btn == getComponent("closeItem")) {
			closeAction();
		}
		
		// EDIT -> FIX
		else if (btn == getComponent("undoItem") ||
				btn == getComponent("undoBtn")) {
			undo();
		} else if (btn == getComponent("redoItem") ||
				btn == getComponent("redoBtn")) {
			redo();
		}
		
		// EDIT listeners
		else if (btn == getComponent("cutItem") ||
				btn == getComponent("cutBtn")) {
			cutAction();
		} else if (btn == getComponent("copyItem") ||
				btn == getComponent("copyBtn")) {
			copyAction();
		} else if (btn == getComponent("pasteItem") ||
				btn == getComponent("pasteBtn")) {
			pasteAction();
		} else if (btn == getComponent("deleteItem") ||
				btn == getComponent("deleteBtn")) {
			deleteAction();
		}
		
		// VIEW -> GRID
		else if (btn == getComponent("gridItem") || 
				btn == getComponent("gridBtn")) {
			gridAction();
		}
		
		// VIEW -> SCALE item listeners
		else if (btn == getComponent("oneItem") ||
				btn == getComponent("oneBtn")) {
			
		} else if (btn == getComponent("twoItem") ||
				btn == getComponent("twoBtn")) {
			
		} else if (btn == getComponent("fourItem") ||
				btn == getComponent("fourBtn")) {
			
		} else if (btn == getComponent("eightItem") ||
				btn == getComponent("eightBtn")) {
			
		}
		
		// VIEW -> MODE item listeners
		else if (btn == getComponent("mapModeItem") ||
				btn == getComponent("mapModeBtn")) {
			
		} else if (btn == getComponent("eventModeItem") ||
				btn == getComponent("eventModeBtn")) {
			
		}
		
		// DRAW item listeners
		else if (btn == getComponent("pencilItem") ||
				btn == getComponent("pencilBtn")) {
			
		} else if (btn == getComponent("rectItem") ||
				btn == getComponent("rectBtn")) {
			
		} else if (btn == getComponent("circleItem") ||
				btn == getComponent("circleBtn")) {
			
		} else if (btn == getComponent("fillItem") ||
				btn == getComponent("fillBtn")) {
			
		}
		
		// TOOLKIT item listeners
		/*else if (cmd.equals("Cherry DataBase")) {
			
		} else if (cmd.equals("Orange ScriptMaker")) {
			
		} else if (cmd.equals("Lime ResourceBase")) {
			
		} else if (cmd.equals("Settings...")) {
			
		}*/
		
		// HELP item listener
		else if (btn == getComponent("aboutItem")) {
			aboutAction();
		}
	}
	
	/**==============================
	// ACTION EVENT METHODS. 
	//===============================*/
	private void newAction() {
		// Load NEW dialog.
		NewDialog n = new NewDialog(fruitEditor.getFrame());
	}
	
	private void openAction() {
		// Load OPEN dialog.
		JFileChooser open = makeFileChooser();
		
		int confirm = open.showOpenDialog(null);
		
		if (confirm == JFileChooser.APPROVE_OPTION) {
			File file = open.getSelectedFile();
			
			try {
				readText(file); // read the map file
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
			
			try {
				writeText(file); // write onto the file
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
		mapPanel.update();
	}
	
	private void redo() {
		mapPanel.update();
	}
	
	private void cutAction() {
		
	}
	
	private void copyAction() {
		
	}
	
	private void pasteAction() {
		
	}
	
	private void deleteAction() {
		
	}
	
	private void gridAction() {
		JCheckBoxMenuItem gridItem = 
				(JCheckBoxMenuItem)getComponent("gridItem");
		JButton gridBtn = (JButton)getComponent("gridBtn");
		
		mapPanel.setGrid(gridItem.isSelected() || gridBtn.isSelected());
		mapPanel.update();
	}
	
	private void aboutAction() {
		AboutDialog a = new AboutDialog(fruitEditor.getFrame());
	}
	
	/**================================
	// MOUSE MOTION LISTENER METHODS.
	//=================================**/
	/**================================
	// mouseMoved(event) - Update if mouse moved.
	//=================================**/
	public void mouseMoved(MouseEvent e) {
		
	}
	/**================================
	// mouseHovered(event) - Update if mouse hovered.
	//=================================**/
	public void mouseHovered(MouseEvent e) {
		
	}
	
	/**================================
	// mouseDragged(event) - Update if mouse dragged.
	//=================================**/
	public void mouseDragged(MouseEvent e) {
		
	}
	
	/**================================
	// MOUSE LISTENER METHODS.
	//=================================**/
	/**================================
	// mouseClicked(event) - Update if mouse clicked.
	//=================================**/
	public void mouseClicked(MouseEvent e) {
		
	}
	
	/**================================
	// mousePressed(event) - Update if mouse pressed.
	//=================================**/
	public void mousePressed(MouseEvent e) {
		
	}
	
	/**================================
	// mouseReleased(event) - Update if mouse released.
	//=================================**/
	public void mouseReleased(MouseEvent e) {
		
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
	// stateChanged(event) - Update any states changed.
	//=================================**/
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
			PrintWriter write = new PrintWriter(
				new BufferedWriter(new FileWriter(file)));
		
			// Write width and height
			write.print(map.getCols() + " ");
			write.println(map.getRows());
			
			// Write integer reps of tiles in for one layer only
			int [][] ids = map.getMapIntArray2();
			
			for (int r = 0; r < ids.length; r++) {
				for (int c = 0; c < ids[0].length; c++) {
					write.print(ids[r][c] + " ");
				}
			}
			
			// Flush and close writer as cleanup
			write.flush();
			write.close();
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
		FileNameExtensionFilter filter = 
				new FileNameExtensionFilter("FruitEditor Maps (*.fmp)", "fmp", "txt");
		
		jfc.setFileFilter(filter);
		
		return jfc;
	}
	
	private JFileChooser makeTextFileChooser() {
		JFileChooser jfc = new JFileChooser();
		FileNameExtensionFilter textfilter = 
				new FileNameExtensionFilter("Text Files (*.txt)", "txt");
		
		jfc.setFileFilter(textfilter);
		
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
