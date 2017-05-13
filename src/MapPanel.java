package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;

import java.io.*;

import java.util.*;

public class MapPanel extends JPanel {
	// FILES.
	private FruitEditor fruitEditor;
	
	// MAP.
	private Map map;
	
	// EVENT LISTENER.
	private FruitListener fruitListener;
	
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
	
	// DIMENSIONS.
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
	
	
	public MapPanel(FruitEditor f) {
		fruitEditor = f;
		map = f.getMap();
		
		fruitListener = fruitEditor.getFruitListener();
		
		mapWidth = map.getCols();
		mapHeight = map.getRows();
		
		gridWidth = map.getGridWidth();
		gridHeight = map.getGridHeight();
		
		mouseX = mouseY = 0;
		
		// Setup right-click menu.
		popupSetup();
	
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		setLayout(new FlowLayout());
		
		addMouseListener(fruitListener);
		addMouseMotionListener(fruitListener);
		addPropertyChangeListener(fruitListener);
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
		renameItem.addActionListener(fruitListener);
		shiftItem.addActionListener(fruitListener);
		
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
		renameItem.setEnabled(false);
		shiftItem.setEnabled(false);
		cutItem.setEnabled(false);
		copyItem.setEnabled(false);
		pasteItem.setEnabled(false);
		deleteItem.setEnabled(false);
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
			map.draw(g, 
					(int)viewport.getViewPosition().getX(), 
					(int)viewport.getViewPosition().getY(),
					viewport.getSize());
		
			if (grid) {
				drawGrid(g);
			}
		
			drawCursor(g, mouseX, mouseY);
		}
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.GRAY);
		int r, c; // Init counters for grid drawing. 
		int scale = map.getScale(); // Scale factor based on zoom view.
		
		for (r=0; r < mapHeight; r++) {
			g.drawLine(0, r*gridHeight, mapWidth*gridWidth, r*gridHeight);
		}
		
		for (c=0; c < mapWidth; c++) {
			g.drawLine(c*gridWidth, 0, c*gridWidth, mapHeight*gridHeight);
		}
	}
	
	private void drawCursor(Graphics g, int mx, int my) {
		Graphics2D g2 = convertTo2d(g);
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		g2.drawRect(mx , my, gridWidth, gridHeight);
	}
	
	public void update() {
		revalidate();
		repaint();
	}
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public synchronized void setMap(Map m) {
		fruitEditor.setMap(m);
		
		map = m;
		
		mapWidth = m.getCols();
		mapHeight = m.getRows();
		
		gridWidth = m.getGridWidth();
		gridHeight = m.getGridHeight();
		
		update();
	}
	
	public void setMapName(String n) {
		map.setName(n);
	}
	
	public synchronized void setMapSize(int w, int h) {
		map.setWidth(w);
		map.setHeight(h);
		
		update();
	}
	
	/**========================================
	// setGrid() - Set grid on/off.
	//=========================================**/
	public synchronized void setGrid(boolean gr) {
		grid = gr;
		
		update();
	}
	
	public void setPanelActive(boolean act) {
		fruitEditor.setPanelActive(act);
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		fruitListener.propertyChange(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void mouseHovered(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	public void mousePressed(MouseEvent e) {
		int btn = e.getButton();
		int mx, my;

		if (btn == MouseEvent.BUTTON1) {
			mouseX = e.getX();
			mouseY = e.getY();
			mx = mouseX / gridWidth;
			my = mouseY / gridHeight;
			
			map.setTile(mx, my, fruitEditor.getSelectedTile());
		} else if (btn == MouseEvent.BUTTON2) {
			mouseX = e.getX();
			mouseY = e.getY();
			popupMenu.show(this, mouseX, mouseY);
		}
	}

	public void mouseReleased(MouseEvent e) {
		int btn = e.getButton();
		
		if (btn == MouseEvent.BUTTON1) {
			oldmouseX = e.getX();
			oldmouseY = e.getY();
		} else if (btn == MouseEvent.BUTTON2) {
			oldmouseX = e.getX();
			oldmouseY = e.getY();
			popupMenu.show(this, oldmouseX, oldmouseY);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int btn = e.getButton();
		int mx, my;
		
		if (btn == MouseEvent.BUTTON1) {
			mouseX = e.getX();
			mouseY = e.getY();
			mx = mouseX / gridWidth;
			my = mouseY / gridHeight;
		}
		
		update();
	}
	
	public void mouseDragged(MouseEvent e) {
		int btn = e.getButton();
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
	
	private Graphics2D convertTo2d(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		return g2;
	}
}