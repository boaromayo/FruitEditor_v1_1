package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import java.util.*;

public class FruitMenuListener implements ActionListener,
	ChangeListener {
	
	private FruitEditor fruitEditor;
	private Map map;
	
	private Stack<ChangeEvent> actions;
	
	public FruitMenuListener(FruitEditor f) {
		fruitEditor = f;
		map = f.getMap();
		
		actions = new Stack<ChangeEvent>();
	}
	
	/**==========================
	// actionPerformed(event) - Event listener
	//===========================**/
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		Object btn = e.getSource();
		
		// MENU ITEMS.
		// FILE item listeners.
		if (cmd.equals("New")) {
			newAction();
		} else if (cmd.equals("Open")) {
			openAction();
		} else if (cmd.equals("Save")) {
			saveAction();
		} else if (cmd.equals("Save As...")) {
			saveAsAction();
		} else if (cmd.equals("Close")) {
			closeAction();
		}
		
		// EDIT -> FIX item listeners.
		else if (cmd.equals("undo")) {
			undo();
		} else if (cmd.equals("redo")) {
			redo();
		}
		
		// EDIT item listeners
		else if (cmd.equals("Cut")) {
			cutAction();
		} else if (cmd.equals("Copy")) {
			copyAction();
		} else if (cmd.equals("Paste")) {
			pasteAction();
		} else if (cmd.equals("Delete")) {
			deleteAction();
		}
		
		// VIEW -> GRID
		else if (cmd.equals("Show/Hide Grid")) {
			toggleGrid();
		}
		
		// VIEW -> SCALE item listeners
		else if (cmd.equals("1:1")) {
			
		} else if (cmd.equals("1:2")) {
			
		} else if (cmd.equals("1:4")) {
			
		} else if (cmd.equals("1:8")) {
			
		}
		
		// VIEW -> MODE item listeners
		else if (cmd.equals("Map Mode")) {
			
		} else if (cmd.equals("Event Mode")) {
			
		}
		
		// DRAW item listeners
		else if (cmd.equals("Pencil")) {
			
		} else if (cmd.equals("Rectangle")) {
			
		} else if (cmd.equals("Circle")) {
			
		} else if (cmd.equals("Flood Fill")) {
			
		}
		
		// TOOLKIT item listeners
		else if (cmd.equals("Cherry DataBase")) {
			
		} else if (cmd.equals("Orange ScriptMaker")) {
			
		} else if (cmd.equals("Lime ResourceBase")) {
			
		} else if (cmd.equals("Settings...")) {
			
		}
		
		// HELP item listener
		else if (cmd.equals("About...")) {
			aboutAction();
		}
		
		// TOOL BUTTONS.
		// FILE tool buttons.
		if (btn == fruitEditor.getButton("newBtn")) {
			newAction();
		} else if (btn == fruitEditor.getButton("openBtn")) {
			openAction();
		} else if (btn == fruitEditor.getButton("saveBtn")) {
			saveAction();
		}
		
		// EDIT -> FIX tool buttons.
		else if (btn == fruitEditor.getButton("undoBtn")) {
			undo();
		} else if (btn == fruitEditor.getButton("redoBtn")) {
			redo();
		}
		
		// EDIT tool buttons.
		else if (btn == fruitEditor.getButton("cutBtn")) {
			cutAction();
		} else if (btn == fruitEditor.getButton("copyBtn")) {
			copyAction();
		} else if (btn == fruitEditor.getButton("pasteBtn")) {
			pasteAction();
		} else if (btn == fruitEditor.getButton("deleteBtn")) {
			deleteAction();
		}
	}
	
	/**==============================
	// ACTION EVENT METHODS. 
	//===============================*/
	private void newAction() {
		// Load NEW dialog.
		NewDialog n = new NewDialog();
				
		if (n.getMap() != null) {
			map = n.getMap();
			fruitEditor.setMap(map);
			
			fruitEditor.update();
		}
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
		MapPanel mapPanel = fruitEditor.getMapPanel();
		mapPanel.repaint();
		
		
	}
	
	private void redo() {
		MapPanel mapPanel = fruitEditor.getMapPanel();
		mapPanel.repaint();
	}
	
	private void cutAction() {
		
	}
	
	private void copyAction() {
		
	}
	
	private void pasteAction() {
		
	}
	
	private void deleteAction() {
		
	}
	
	private void toggleGrid() {
		fruitEditor.toggleGrid();
	}
	
	private void aboutAction() {
		AboutDialog a = new AboutDialog();
	}
	
	/**================================
	// stateChanged(event) - Update any states changed.
	//=================================**/
	public void stateChanged(ChangeEvent e) {
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
	
	public void validate() {
		if (fruitEditor == null) {
			return;
		}
		
		fruitEditor.validate();
	}
}
