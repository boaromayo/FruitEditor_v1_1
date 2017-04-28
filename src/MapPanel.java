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
	
		setLayout(new FlowLayout());
		
		addMouseListener(fruitListener);
		addMouseMotionListener(fruitListener);
		addPropertyChangeListener(fruitListener);
	}
	
	private void popupSetup() {
		// Assign popup menu.
		popupMenu = new JPopupMenu();
		
		disableItems();
		
		subSetup(); // Setup menu items.
		
		popupMenu.add(renameItem);
		
		popupMenu.addSeparator();
		
		popupMenu.add(shiftItem);
	}
	
	private void subSetup() {
		// RIGHT CLICK MENU ITEMS.
		renameItem = new JMenuItem("Rename...");	// RENAME
		shiftItem = new JMenuItem("Shift Map...");  // SHIFT MAP
		
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
	
	private void disableItems() {
		renameItem.setEnabled(false);
		shiftItem.setEnabled(false);
	}
	
	@Override
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		Image img = createImage(viewport.getWidth(), viewport.getHeight());
		Graphics g2 = img.getGraphics();
		draw(g2);
		g.drawImage(img, 0, 0, 
				viewport.getWidth(), viewport.getHeight(), null);	
	}
	
	public void draw(Graphics g) {	
		if (fruitEditor.isPanelActive()) {
			g.setColor(Color.WHITE);
			setBackground(g.getColor());
		
			map.draw(g);
		
			if (grid) {
				drawGrid(g);
			}
		
			drawCursor(g);	
		} else {
			g.setColor(Color.GRAY);
			setBackground(g.getColor());
		}
	}
	
	private void drawGrid(Graphics g) {
		g.setColor(Color.GRAY);
		int r, c; // Init counters for grid drawing. 
		int scale = map.getScale(); // Scale factor based on zoom view.
		
		for (r=0; r < mapHeight; r++) {
			g.drawLine(0, r*mapHeight*map.getScale(), mapWidth*map.getScale(), r*mapHeight*map.getScale());
		}
		
		for (c=0; c < mapWidth; c++) {
			g.drawLine(c*mapWidth*map.getScale(), 0, c*mapWidth*map.getScale(), mapHeight*map.getScale());
		}
	}
	
	private void drawCursor(Graphics g) {
		Graphics2D g2 = convertTo2d(g);
		int mx = mouseX / gridWidth;
		int my = mouseY / gridHeight;
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		g2.drawRect(mx - Map.OFFSET,
				my - Map.OFFSET,
				gridWidth, gridHeight);
	}
	
	public void update() {
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
		
	}
	
	public void setMapName(String n) {
		map.setName(n);
	}
	
	/**========================================
	// setGrid() - Set grid on/off.
	//=========================================**/
	public synchronized void setGrid(boolean gr) {
		grid = gr;
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		fruitListener.propertyChange(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
	}
	
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		
	}

	public void mouseReleased(MouseEvent e) {
		oldmouseX = e.getX();
		oldmouseY = e.getY();
	}
	
	public void mouseClicked(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		int mx = mouseX / gridWidth;
		int my = mouseY / gridHeight;
		
		//map.setTile(mx, my, );
		update();
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public boolean gridOn() {
		return grid;
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