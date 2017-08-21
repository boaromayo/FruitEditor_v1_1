package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;
import javax.swing.event.*;

import java.io.*;

public class TilePanel extends JPanel implements MouseListener, 
	MouseMotionListener, ActionListener {
	// FILES.
	private FruitEditor fruitEditor;
	
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
	private int tilesetWidth;
	private int tilesetHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	// MOUSE COORDS.
	private int mouseX;
	private int mouseY;
	private int oldmouseX;
	private int oldmouseY;
	
	// TILESET.
	private Tileset tileset;
	
	// SELECTED TILE.
	private Tile selectedTile;
	
	public TilePanel(FruitEditor f) {
		fruitEditor = f;
		
		tileset = f.getTileset();
		
		tilesetWidth = tileset.getWidth();
		tilesetHeight = tileset.getHeight();
		
		gridWidth = tileset.getTileWidth();
		gridHeight = tileset.getTileHeight();
		
		setBounds(0, 0, FruitEditor.SCREEN_WIDTH / 4, FruitEditor.SCREEN_HEIGHT);
		setPreferredSize(new Dimension(
				tilesetWidth, 
				tilesetHeight));
		
		// Setup popup menu.
		popupSetup();
		
		selectedTile = tileset.getTile(0,0);
		
		addMouseListener(this);
		addMouseMotionListener(this);
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
		openTileItem.addActionListener(this);
		gridTileItem.addActionListener(this);
		closeTileItem.addActionListener(this);
		
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
		//openTileItem.setEnabled(false);
		gridTileItem.setEnabled(false);
		closeTileItem.setEnabled(false);
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
			if (tileset != null) {
				tileset.draw(g,
						(int)viewport.getViewPosition().getX(), 
						(int)viewport.getViewPosition().getY(), 
						viewport.getSize());
			}

			drawGrid(g);
			
			drawCursor(g, mouseX, mouseY);
			
			if (selectedTile.getID() < 0) {
				drawSelectedCursor(g, 0, 0); // draw cursor at (0,0) if selected tile's id < 0
			} else {
				// cursor is drawn based on selected tile's id in the tileset
				int ix = selectedTile.getID() % tileset.getCols();
				int iy = selectedTile.getID() / tileset.getCols();
				
				drawSelectedCursor(g, ix*gridWidth, iy*gridHeight);
			}
		}
	}
	
	private void drawGrid(Graphics g) {
		Graphics2D g2 = convertTo2d(g);
		g2.setColor(Color.DARK_GRAY);
		g2.setStroke(new BasicStroke(1,
				BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL,
				0,
				new float[] {2},
				0)); // draw dashed line
		
		int r, c; // Init counter for grid lines.
		int viewWidth = tilesetWidth / gridWidth;
		int viewHeight = tilesetHeight / gridHeight;
		
		for (r=0; r <= viewHeight; r++) {
			g2.drawLine(0, r*gridHeight, tilesetWidth, r*gridHeight);
		}
		
		for (c=0; c <= viewWidth; c++) {
			g2.drawLine(c*gridWidth, 0, c*gridWidth, tilesetHeight);
		}
	}
	
	private void drawCursor(Graphics g, int x, int y) {
		Graphics2D g2 = convertTo2d(g);
		int tmx = snap(x, gridWidth);
		int tmy = snap(y, gridHeight);
		
		g.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		
		if (checkBounds(tmx,tmy,tilesetWidth,tilesetHeight)) {
			g.drawRect(tmx, tmy, gridWidth, gridHeight);
		}
	}
	
	private void drawSelectedCursor(Graphics g, int x, int y) {
		int tmx = snap(x, gridWidth);
		int tmy = snap(y, gridHeight);
		
		Graphics2D g2 = convertTo2d(g);
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLUE);
		
		g2.drawRect(tmx, tmy, gridWidth, gridHeight);
	}
	
	public void update() {
		revalidate();
		repaint();
	}
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public void setTileset(Tileset t) {
		if (t == null)
			return;
		
		tileset = t;
		tilesetWidth = t.getWidth();
		tilesetHeight = t.getHeight();
		
		gridWidth = t.getTileWidth();
		gridHeight = t.getTileHeight();
		
		selectedTile = t.getTile(0,0);
		
		setPreferredSize(new Dimension(tilesetWidth,tilesetHeight));
		
		fruitEditor.setTileset(t);
		
		update();
	}
	
	public void setSelectedTile(Tile t) {
		selectedTile = t;
	}
	
	public int getGridWidth() { return gridWidth; }
	
	public int getGridHeight() { return gridHeight; }
	
	public Tileset getTileset() {
		return tileset;
	}
	
	public Tile getSelectedTile() {
		return selectedTile;
	}
	
	public boolean isPanelActive() {
		return fruitEditor.isPanelActive();
	}
	
	/**=======================================
	 * pixelToTile(x,y) - Convert pixel to tile coordinates.
	//========================================**/
	public Point pixelToTile(int x, int y) {
		return new Point(x / gridWidth, y / gridHeight);
	}
	
	/**=====================================
	 * snap(n,snap) - Ensures tile snaps to map's grid.
	//=====================================**/
	public int snap(int n, int snap) {
		return n - (n % snap);
	}
	
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
		
		if (src == openTileItem) {
			new NewTileDialog(fruitEditor);
		} else if (src == gridTileItem) {
			
		} else if (src == closeTileItem) {
			
		}
	}
	
	/**=======================================
	 * MOUSE MOTION LISTENER METHODS.
	//========================================**/
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		
		// Set status
		fruitEditor.getStatusPanel().setCursorLocation();
		
		fruitEditor.update();
	}
	
	public void mouseHovered(MouseEvent e) {
		mouseX = snap(e.getX(), gridWidth); // snap to grid
		mouseY = snap(e.getY(), gridHeight);
		int r = pixelToTile(mouseX,mouseY).y;
		int c = pixelToTile(mouseX,mouseY).x;
		Tile hoveredTile = tileset.getTile(r,c);
		String tileName = hoveredTile.getName() != null ? hoveredTile.getName() : "No Tile";
		
		// Make a tool tip text of the hovered over tile.
		setToolTipText(tileName);
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}

	/**================================
	 * MOUSE LISTENER METHODS.
	//=================================**/
	public void mousePressed(MouseEvent e) {
		int btn = e.getButton();
		mouseX = snap(e.getX(), gridWidth);
		mouseY = snap(e.getY(), gridHeight);
		
		if (btn == MouseEvent.BUTTON1) {
			int tx = pixelToTile(mouseX,mouseY).x;
			int ty = pixelToTile(mouseX,mouseY).y;
			
			if (isPanelActive() && checkBounds(tx,ty,
					tilesetWidth/gridWidth,tilesetHeight/gridHeight)) {
				setSelectedTile(tileset.getTile(ty,tx));
			}
		} else if (btn == MouseEvent.BUTTON3) {
			if (isPanelActive()) {
				popupMenu.show(this, mouseX, mouseY);
			}
		}
		System.out.println(selectedTile.getID());
		
	}

	public void mouseReleased(MouseEvent e) {
		int btn = e.getButton();
		
		if (btn == MouseEvent.BUTTON1) {
			oldmouseX = snap(e.getX(), gridWidth);
			oldmouseY = snap(e.getY(), gridHeight);
		} else if (btn == MouseEvent.BUTTON3) {
			if (isPanelActive()) {
				popupMenu.show(this, oldmouseX, oldmouseY);
			}
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
}