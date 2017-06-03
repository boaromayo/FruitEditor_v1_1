package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;

import java.io.*;

import java.util.*;

public class MapPanel extends JPanel implements MouseListener, 
	MouseMotionListener, KeyListener, ActionListener {
	// FILES.
	private FruitEditor fruitEditor;
	
	// MAP.
	private Map map;
	
	// EVENT LISTENER.
	private FruitListener fruitListener;
	
	// EDITOR MODE.
	private EditorMode editorMode;
		
	// POPUP (RIGHT-CLICK) MENU.
	private JPopupMenu popupMenu;
	
	// POPUP MENU COMPS.
	private JMenuItem renameItem;
	private JMenuItem shiftItem;
	private JMenuItem cutItem;
	private JMenuItem copyItem;
	private JMenuItem pasteItem;
	private JMenuItem deleteItem;
	
	// VIEWPORT.
	private JViewport viewport;
	
	// MAP DIMENSIONS.
	private int mapWidth;
	private int mapHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	// GRID ON/OFF.
	private boolean grid = true;
	
	// MOUSE COORDS.
	private int mouseX;
	private int mouseY;
	private int oldmouseX;
	private int oldmouseY;
	
	// CURSOR COORDS.
	private int cursorX;
	private int cursorY;
	
	public MapPanel(FruitEditor f) {
		fruitEditor = f;
		
		map = f.getMap();
		
		fruitListener = f.getListener();
		
		editorMode = EditorMode.MAP_MODE;
		
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		
		gridWidth = map.getGridWidth();
		gridHeight = map.getGridHeight();
		
		mouseX = mouseY = 0;
		
		// Setup right-click menu.
		popupSetup();
	
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		setLayout(new FlowLayout());
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		addPropertyChangeListener(fruitListener);
		
		setFocusable(true);
		requestFocusInWindow();
	}
	
	private void popupSetup() {
		// Assign popup menu.
		popupMenu = new JPopupMenu();
		
		subSetup(); // Setup menu items.
		
		disableItems();
		
		popupMenu.add(renameItem);
		popupMenu.add(shiftItem);
		
		popupMenu.addSeparator();
		
		popupMenu.add(cutItem);
		popupMenu.add(copyItem);
		popupMenu.add(pasteItem);
		
		popupMenu.addSeparator();
		
		popupMenu.add(deleteItem);
	}
	
	private void subSetup() {
		// RIGHT CLICK MENU ITEMS.
		renameItem = new JMenuItem("Rename...");	// RENAME
		shiftItem = new JMenuItem("Shift...");  // SHIFT MAP
		
		editSetup();
		
		// Add in event listeners.
		renameItem.addActionListener(this);
		shiftItem.addActionListener(this);
		
		// Set names for components.
		renameItem.setName("rename");
		shiftItem.setName("shift");
		
		// Put comps in hashmap.
		fruitEditor.putComponent(renameItem.getName(), renameItem);
		fruitEditor.putComponent(shiftItem.getName(), shiftItem);
	}
	
	private void editSetup() {
		// RIGHT CLICK MENU -> EDIT ITEMS.
		cutItem = new JMenuItem("Cut"); 		// CUT
		copyItem = new JMenuItem("Copy"); 		// COPY
		pasteItem = new JMenuItem("Paste"); 	// PASTE
		deleteItem = new JMenuItem("Delete"); 	// DELETE
		
		cutItem.addActionListener(fruitListener);
		copyItem.addActionListener(fruitListener);
		pasteItem.addActionListener(fruitListener);
		deleteItem.addActionListener(fruitListener);
		
		// Set names for components.
		cutItem.setName("cutRt");
		copyItem.setName("copyRt");
		pasteItem.setName("pasteRt");
		deleteItem.setName("deleteRt");
		
		// Put comps in hashmap.
		fruitEditor.putComponent(cutItem.getName(), cutItem);
		fruitEditor.putComponent(copyItem.getName(), copyItem);
		fruitEditor.putComponent(pasteItem.getName(), pasteItem);
		fruitEditor.putComponent(deleteItem.getName(), deleteItem);
	}
	
	private void disableItems() {
		shiftItem.setEnabled(false);
		cutItem.setEnabled(false);
		copyItem.setEnabled(false);
		pasteItem.setEnabled(false);
		deleteItem.setEnabled(false);
	}
	
	public void update() {
		revalidate();
		repaint();
	}
	
	@Override
	public synchronized void paint(Graphics g) {
		super.paintComponent(g);
		
		Image img = createImage(getWidth(), getHeight());
		Graphics g2 = img.getGraphics();
		draw(g2);
		g.drawImage(img, 0, 0, 
				getWidth(), getHeight(), null);
	}
	
	public synchronized void draw(Graphics g) {	
		if (isPanelActive()) {
			if (map != null) {
				map.draw(g, 
						(int)viewport.getViewPosition().getX(), 
						(int)viewport.getViewPosition().getY(),
						viewport.getSize());
			
			}
			
			drawCursor(g, mouseX, mouseY);
			
			if (grid) {
				drawGrid(g);
			}
		
			if (editorMode.equals(EditorMode.EVENT_MODE)) {
				drawEventCursor(g);
			}
		}
	}
	
	private void drawGrid(Graphics g) {
		Graphics2D g2 = convertTo2d(g);
		g2.setColor(Color.GRAY);
		g2.setStroke(new BasicStroke(1, 
				BasicStroke.CAP_BUTT, 
				BasicStroke.JOIN_BEVEL, 
				0, 
				new float[] {2}, 
				0)); // draws dashed line
		
		int r, c; // Init counters for grid drawing. 
		//int scale = map.getScale(); // Scale factor based on zoom view.
		
		for (r=0; r <= mapHeight; r++) {
			g2.drawLine(0, r*gridHeight, mapWidth*gridWidth, r*gridHeight);
		}
		
		for (c=0; c <= mapWidth; c++) {
			g2.drawLine(c*gridWidth, 0, c*gridWidth, mapHeight*gridHeight);
		}
	}
	
	private void drawCursor(Graphics g, int x, int y) {
		int mx = x - (x % gridWidth); // set the cursor positions
		int my = y - (y % gridHeight);
		
		g.setColor(Color.BLACK);
		
		if (checkBounds(mx,my,
				mapWidth*gridWidth,mapHeight*gridHeight)) {
			g.drawRect(mx, my, gridWidth, gridHeight);
		}
	}
	
	private void drawEventCursor(Graphics g) {
		Graphics2D g2 = convertTo2d(g);
		int tx = cursorX - (cursorX % gridWidth);
		int ty = cursorY - (cursorY % gridHeight);
		
		g2.setStroke(new BasicStroke(3));
		g2.setColor(Color.BLACK);
		
		if (checkBounds(tx,ty,
				mapWidth*gridWidth,mapHeight*gridHeight)) {
			g2.drawRect(tx, ty, gridWidth, gridHeight);
		}
	}
	
	public void mapPressed(int x, int y) {
		switch (map.drawMode()) {
		case PENCIL:
			map.setTile(x, y, fruitEditor.getSelectedTile());
			break;
		case RECTANGLE:
			rectFill(x, y, fruitEditor.getSelectedTile());
			break;
		case CIRCLE:
			break;
		case FILL:
			floodFill(x, y, map.getTile(x,y), fruitEditor.getSelectedTile());
		default:
			break;
		}
		
		update();
	}
	
	private void rectFill(int x, int y, Tile newTile) {
		int xmax = mouseX - (mouseX % gridWidth); // Set the maximum based on how far the second click is at.
		int ymax = mouseY - (mouseY % gridHeight);
		int r, c; // Counters
		
		for (r=y; r<=ymax; r++) {
			for (c=x; c<=xmax; c++) {
				if (r==y || r==ymax) {
					map.setTile(x, y, newTile); // Place a tile if on a rectangle's side.
				} else if (c==x || c==xmax) {
					map.setTile(x, y, newTile);
				}
			}
		}
	}
	
	private void floodFill(int x, int y, Tile targetTile, Tile newTile) {
		if (targetTile == newTile)
			return;
	}
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public synchronized void setMap(Map m) {
		fruitEditor.setMap(m);
		
		map = m;
		
		mapWidth = m.getWidth();
		mapHeight = m.getHeight();
		
		gridWidth = m.getGridWidth();
		gridHeight = m.getGridHeight();
		
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		
		if (!isPanelActive()) {
			setPanelActive(true);
		}
		
		fruitEditor.update();
	}
	
	public void setMapName(String n) {
		map.setName(n);
	}
	
	public synchronized void setMapSize(int w, int h) {
		mapWidth = w;
		mapHeight = h;
		map.setWidth(mapWidth);
		map.setHeight(mapHeight);
		
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		
		update();
	}
	
	/**========================================
	// setGrid() - Set grid on/off.
	//=========================================**/
	public void setGrid(boolean gr) {
		grid = gr;
	}
	
	public void setPanelActive(boolean act) {
		fruitEditor.setPanelActive(act);
	}
	
	/**========================================
	// setMode(mode) - Set mode.
	//=========================================**/
	public void setMode(EditorMode m) {
		editorMode = m;
	}
	
	public boolean gridOn() {
		return grid;
	}
	
	public boolean isPanelActive() {
		return fruitEditor.isPanelActive();
	}
	
	public int getMapX() {
		return (int)mouseX / gridWidth;
	}
	
	public int getMapY() {
		return (int)mouseY / gridHeight;
	}
	
	public String getMapName() {
		return map.getName();
	}
	
	public int getMapWidth() {
		return mapWidth;
	}
	
	public int getMapHeight() {
		return mapHeight;
	}
	
	// Keep this checkBounds() method private to prevent any interaction with other panels.
	private boolean checkBounds(int x, int y, int w, int h) {
		return (x >= 0 && x < w && y >= 0 && y < h);
	}
	
	private Graphics2D convertTo2d(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		return g2;
	}
	
	
	/**=======================================
	 * actionPerformed(ActionEvent) - Perform ActionEvents.
	//========================================**/
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == renameItem) {
			new RenameDialog(fruitEditor);
		} else if (src == shiftItem) {
			// TODO: Add shift dialog box here.
		}
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		fruitListener.propertyChange(e);
	}
	
	/**=======================================
	 * MOUSE MOTION LISTENER METHODS.
	//========================================**/
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX() - (e.getX() % gridWidth);
		mouseY = e.getY() - (e.getY() % gridHeight);
		int tx = mouseX / gridWidth; // make the tile coordinates
		int ty = mouseY / gridHeight;
		
		// Set status panel
		if (isPanelActive() && checkBounds(tx,ty,mapWidth,mapHeight)) {
			fruitEditor.getStatusPanel().setCursorLocation(tx,ty);
		} else {
			fruitEditor.getStatusPanel().setCursorLocation();
		}
		
		fruitEditor.update();
	}
	
	public void mouseHovered(MouseEvent e) {
		/*mouseX = e.getX();
		mouseY = e.getY();*/
	}
	
	public void mouseDragged(MouseEvent e) {
		int btn = e.getButton();
		mouseX = e.getX() - (e.getX() % gridWidth);
		mouseY = e.getY() - (e.getY() % gridHeight);
		int tx = mouseX / gridWidth;
		int ty = mouseY / gridHeight;
		
		// Set status panel
		if (isPanelActive() && checkBounds(tx,ty,mapWidth,mapHeight)) {
			fruitEditor.getStatusPanel().setCursorLocation(tx,ty);
		} else {
			fruitEditor.getStatusPanel().setCursorLocation();
		}
		
		// if left-click btn dragged
		if (btn == MouseEvent.BUTTON1) {
			if (isPanelActive() && 
					checkBounds(tx,ty,mapWidth,mapHeight)) {
				mapPressed(tx,ty);
			}
		}
	}
	
	/**================================
	 * MOUSE LISTENER METHODS.
	//=================================**/
	public void mousePressed(MouseEvent e) {
		int btn = e.getButton();
		
		// if left-click btn is pressed
		if (btn == MouseEvent.BUTTON1) {
			mouseX = e.getX() - (e.getX() % gridWidth);
			mouseY = e.getY() - (e.getY() % gridHeight);
			int tx = mouseX / gridWidth;
			int ty = mouseY / gridHeight;
			
			if (isPanelActive() && 
					checkBounds(tx,ty,mapWidth,mapHeight)) {
				mapPressed(tx,ty);
			}
		} else if (btn == MouseEvent.BUTTON3) {
			// if right-click btn is pressed
			mouseX = e.getX();
			mouseY = e.getY();
			if (isPanelActive() &&
					checkBounds(mouseX,mouseY,mapWidth*gridWidth,mapHeight*gridHeight)) {
				popupMenu.show(this, mouseX, mouseY);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		int btn = e.getButton();
		
		if (btn == MouseEvent.BUTTON1) {
			// if left-click btn is released
			oldmouseX = e.getX();
			oldmouseY = e.getY();
		} else if (btn == MouseEvent.BUTTON3) {
			// if right-click btn is released
			oldmouseX = e.getX();
			oldmouseY = e.getY();
			if (isPanelActive() &&
					checkBounds(oldmouseX,oldmouseY,mapWidth*gridWidth,mapHeight*gridHeight)) {
				popupMenu.show(this, oldmouseX, oldmouseY);
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		/*int btn = e.getButton();
		int tx, ty;
		
		if (btn == MouseEvent.BUTTON1) {
			mouseX = e.getX();
			mouseY = e.getY();
			tx = mouseX / gridWidth;
			ty = mouseY / gridHeight;
		}
		
		update();*/
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	/**================================
	 * KEY LISTENER METHODS.
	//=================================**/
	public void keyPressed(KeyEvent e) {
		if (editorMode.equals(EditorMode.EVENT_MODE)) {
			
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
}