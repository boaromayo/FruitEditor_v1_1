package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;
import javax.swing.event.*;

import java.io.*;

public class TilePanel extends JPanel {
	// FILES.
	private FruitEditor fruitEditor;
	
	// EVENT LISTENER.
	private FruitListener fruitListener;
	
	// POPUP (or RIGHT-CLICK) MENU.
	private JPopupMenu popupMenu;
	
	// POPUP (or RIGHT-CLICK) MENU COMPS.
	//private JMenuItem newTileItem;
	private JMenuItem openTileItem;
	private JMenuItem gridTileItem;
	//private JMenuItem saveTileItem;
	private JMenuItem closeTileItem;
	
	// VIEWPORT.
	private JViewport viewport;
	
	// DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	// MOUSE COORDS.
	private int mouseX;
	private int mouseY;
	private int oldmouseX;
	private int oldmouseY;
	
	public TilePanel(FruitEditor f) {
		fruitEditor = f;
		
		fruitListener = fruitEditor.getFruitListener();
		
		// Setup popup menu.
		popupSetup();
		
		addMouseListener(fruitListener);
	}
	
	private void popupSetup() {
		// Assign popup menu.
		popupMenu = new JPopupMenu();
		
		subSetup(); // setup items
		
		disableItems(); // disable menu items
		
		// Add items to popup menu.
		//popupMenu.add(newTileItem);
		popupMenu.add(openTileItem);
		
		popupMenu.addSeparator();
		
		popupMenu.add(gridTileItem);
		
		popupMenu.addSeparator();
		
		popupMenu.add(closeTileItem);
	}
	
	private void subSetup() {
		// RIGHT CLICK MENU ITEMS
		//newTileItem = new JMenuItem("New Tileset");		// NEW
		openTileItem = new JMenuItem("Open Tileset");	// OPEN
		gridTileItem = new JMenuItem("Grid Settings");  // GRID
		closeTileItem = new JMenuItem("Close Tileset"); // CLOSE
		
		// Add in event listeners.
		//newTileItem.addActionListener(fruitListener);
		openTileItem.addActionListener(fruitListener);
		gridTileItem.addActionListener(fruitListener);
		closeTileItem.addActionListener(fruitListener);
		
		// Set names for components.
		//newTileItem.setName("newTileItem");
		openTileItem.setName("openTile");
		gridTileItem.setName("gridTile");
		closeTileItem.setName("closeTile");
		
		// Add component into hashmap.
		fruitEditor.putComponent(openTileItem.getName(), openTileItem);
		fruitEditor.putComponent(gridTileItem.getName(), gridTileItem);
		fruitEditor.putComponent(closeTileItem.getName(), closeTileItem);
	}
	
	private void disableItems() {
		//newTileItem.setEnabled(false);
		openTileItem.setEnabled(false);
		gridTileItem.setEnabled(false);
		closeTileItem.setEnabled(false);
	}
	
	public void openTileset() {
		try {
			
		} catch (Exception e) {
			System.err.println("ERROR: Cannot load tileset. Reason: " + e.getMessage());
			e.printStackTrace();
			System.exit(1);
		}
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
		drawGrid(g);
	
		drawCursor(g);
	}
	
	private void drawGrid(Graphics g) {
		int r, c; // Init counter for grid lines.
		int viewWidth = viewport.getWidth();
		int viewHeight = viewport.getHeight();
		
		g.setColor(Color.GRAY);
		
		
		for (r=0; r < viewHeight; r++) {
			g.drawLine(0, r*gridHeight, viewWidth, r*gridHeight);
		}
		
		for (c=0; c < viewWidth; c++) {
			g.drawLine(c*gridWidth, 0, c*gridWidth, viewHeight);
		}
	}
	
	private void drawCursor(Graphics g) {
		int mx = mouseX / gridWidth;
		int my = mouseY / gridHeight;
		
		Graphics2D g2 = convertTo2D(g);
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		g2.drawRect(mx, my, gridWidth, gridHeight);
	}
	
	public void update() {
		repaint();
	}
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public void propertyChange(PropertyChangeEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseHovered(MouseEvent e) {
		
	}
	
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		if (e.isPopupTrigger()) {
			popupMenu.show(this, mouseX, mouseY);
		}
	}

	public void mouseReleased(MouseEvent e) {
		oldmouseX = e.getX();
		oldmouseY = e.getY();
		
		if (e.isPopupTrigger()) {
			popupMenu.show(this, oldmouseX, oldmouseY);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public Graphics2D convertTo2D(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
				RenderingHints.VALUE_ANTIALIAS_ON);
		return g2;
	}
}