package FruitEditor;

import java.awt.*;
import java.awt.event.*;

import java.beans.*;

import javax.swing.*;

import java.io.*;

import java.util.*;

public class MapPanel extends JPanel implements MouseListener, 
	MouseMotionListener, ActionListener {
	// FILES.
	private FruitEditor fruitEditor;
	
	// MAP.
	private Map map;
	
	// MODES.
	private DrawMode drawMode;
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
	
	// CURSOR COORDS.
	private int cursorX;
	private int cursorY;
	
	public MapPanel(FruitEditor f) {
		fruitEditor = f;
		
		map = f.getMap();
		
		drawMode = DrawMode.PENCIL;
		editorMode = EditorMode.MAP_MODE;
		
		mapWidth = map.getWidth();
		mapHeight = map.getHeight();
		
		gridWidth = map.getTileWidth();
		gridHeight = map.getTileHeight();
		
		mouseX = mouseY = 0;
		cursorX = cursorY = 0;
		
		// Setup right-click menu.
		popupSetup();
		
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		setLayout(new FlowLayout());
		
		setFocusable(true);
		requestFocusInWindow();
		
		addKeyListener(new KeyClass());
		addMouseListener(this);
		addMouseMotionListener(this);
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
		FruitListener fl = fruitEditor.getListener();
		
		// RIGHT CLICK MENU -> EDIT ITEMS.
		cutItem = new JMenuItem("Cut"); 		// CUT
		copyItem = new JMenuItem("Copy"); 		// COPY
		pasteItem = new JMenuItem("Paste"); 	// PASTE
		deleteItem = new JMenuItem("Delete"); 	// DELETE
		
		cutItem.addActionListener(fl);
		copyItem.addActionListener(fl);
		pasteItem.addActionListener(fl);
		deleteItem.addActionListener(fl);
		
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
						
			if (grid) {
				drawGrid(g);
			}
		
			drawCursor(g, mouseX, mouseY);
			
			if (editorMode.equals(EditorMode.EVENT_MODE)) {
				drawEventCursor(g, cursorX, cursorY);
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
		Graphics2D g2 = convertTo2d(g);
		int mx = snap(x, gridWidth); // set the cursor positions
		int my = snap(y, gridHeight);
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(1));
		
		if (checkBounds(mx,my,
				mapWidth*gridWidth,mapHeight*gridHeight)) {
			g2.drawRect(mx, my, gridWidth, gridHeight);
		}
	}
	
	private void drawEventCursor(Graphics g, int x, int y) {
		Graphics2D g2 = convertTo2d(g);
		int tx = snap(x, gridWidth);
		int ty = snap(y, gridHeight);
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(Color.BLACK);
		
		if (checkBounds(tx,ty,
				mapWidth*gridWidth,mapHeight*gridHeight)) {
			g2.drawRect(tx, ty, gridWidth, gridHeight);
		}
	}
	
	public void mapPressed(int x, int y) {
		int [][][] mapIds = map.getMapIntArray();
		switch (drawMode) {
		case PENCIL:
			map.setTile(x, y, fruitEditor.getSelectedTile());
			break;
		case FILL:
			//floodFill(x, y, map.getTile(x,y), fruitEditor.getSelectedTile());
			break;
		default:
			break;
		}
		fruitEditor.addChanges(new MapChangeCommand(map,fruitEditor.getTileset(),mapIds));
		update();
	}
	
	public void mapDragged(int x1, int y1, int x2, int y2) {
		switch (drawMode) {
		case PENCIL:
			map.setTile(x1, y1, fruitEditor.getSelectedTile());
			break;
		case LINE:
			//lineFill(x1, y1, x2, y2, map.getTile(x1,y1), fruitEditor.getSelectedTile());
			break;
		case RECTANGLE:
			//rectFill(x1, y1, x2, y2, map.getTile(x1,y1), fruitEditor.getSelectedTile());
			break;
		case CIRCLE:
			break;
		default:
			break;
		}
		update();
	}
	
	/**=======================================
	 * lineFill(x1,y1,x2,y2,newTile) - Draw a "line" of newTiles from (x1,y1) to (x2,y2).
	 * @param x1 - starting x location
	 * @param y1 - starting y location
	 * @param x2 - ending x location
	 * @param y2 - ending y location
	 * @param newTile - The new Tile to be set
	 * 
	 * The integer version of Bresenham's line algorithm will be used to
	 * draw the "line", like so:
	 *  set deltax = (x2 - x1)
	 *  set deltay = (y2 - y1)
	 *  set D = dy*2 - dx
	 *  set y to y1
	 *  loop for each x from x1 to x2
	 *  	draw new tile(x,y)
	 *  	if derror >= 0 then
	 *  		increment y by 1
	 *  		decrease derror by dx*2
	 *  	increment derror by dy*2
	 *  return;
	 * 
	//========================================**/
	// for version 0.2.6
	/*private void lineFill(int x1, int y1, int x2, int y2, Tile oldTile, Tile newTile) {
		int dx = Math.abs(x1 - x2);
		int dy = Math.abs(y1 - y2);
		int d = dy*2 - dx;
		int e = 0;
		int y = y1;
		
		if (dx >= dy) {
			for (int x = x1; x < x2; x++) {
				map.setTile(x,y,newTile);
				e += dy;
				if (d > 0) {
					y++;
					d -= dx*2;
					e += dy - dx;
				}
				d += dy*2;
			}
		} else {
			for (int x = x1; x < x2; x++) {
				map.setTile(x,y,newTile);
				e += dy;
				if (d > 0) {
					y--;
					d += dx*2;
					e -= dy - dx;
				}
			}
		}
	}*/
	
	/**========================================
	 * rectFill(x1,y1,x2,y2,newTile) - Fill a rectangle of newTiles starting
	 * from (x1,y1) and end with a width of x2-x1 and a height of y2-y1 at (x2,y2).
	 * @param x1 - starting x location
	 * @param y1 - starting y location
	 * @param x2 - ending x location
	 * @param y2 - ending y location
	 * 
	 * The algorithm is based on tips4java.wordpress.com's solution
	 * for drawing filled rectangles via mouse.
	//=========================================**/
	// for version 0.2.6
	/*private void rectFill(int x1, int y1, int x2, int y2, Tile oldTile, Tile newTile) {
		// Assume that (x1,y1), and (x2,y2) are tile coords.
		int r, c;
		
		int x, y, w, h; // Parameters of the rect.
		
		x = Math.min(x1,x2);
		y = Math.min(y1,y2);
		w = Math.abs(x1 - x2);
		h = Math.abs(y1 - y2);
		
		System.out.println("(" + x + "," + y + ") (" + w + " x " + h + ")");
		
		for (r = y; r < h; r++) {
			for (c = x; c < w; c++) {
				map.setTile(c,r,newTile);
			}
		}
	}*/
	
	/**========================================
	 * floodFill(x,y,targetTile,newTile) - Fill a selected area with a new Tile until it
	 * reaches a Tile different from the target.
	 * @param x - starting x location of target
	 * @param y - starting y location of target
	 * @param targetTile - The targeted old Tile.
	 * @param newTile - The new Tile to be set.
	 * 
	 * To achieve this effect efficiently, a loop moving east and
	 * west will be used, like so:
	 *  let node tile = tile(x,y) on map.
	 * 	if target tile == new tile return;
	 *	if node tile != target tile return;
	 *	Set q to empty queue. (since we assume the map is our queue, we can omit this step)
	 *	Add node to q.
	 *	Loop for each element n (int assumed) in q
	 *		Init integers w (west of node) and e (east of node) to n.
	 *		Move w west until tile west of w != target tile.
	 *		Move e east until tile east of e != target tile.
	 *		Loop for each node n between east and west
	 *			n tile is set to new tile.
	 *			if tile north of n == target tile, add to q.
	 *			if tile south of n == target tile, add to q.
	 *	Loop until q is finished.
	 *	return;
	 *
	 * Here's the revised algorithm:
	 *  if target tile == new tile return;
	 *  init integer w to x.
	 *  init integer e east of x.
	 *  loop
	 *  	move w west until tile west of w != target tile.
	 *  loop
	 *      move e east until tile east of e != target tile.
	 *  loop for each n from w to e
	 *      if tile north of n == target tile, add to recursion stack.
	 *      if tile south of n == target tile, add to recursion stack.
	 *  loop until recursion stack is finished.
	 *  return;
	//========================================**/
	// for version 0.2.6
	/*private void floodFill(int x, int y, Tile targetTile, Tile newTile) {
	 	// Mark current ids of map before change.
	  	int [][][] mapIds = map.getMapIntArray();
	  	
		// Use static comparator method to prevent null-case errors.
		if (Tile.compareTo(targetTile,newTile))
			return;
		
		// Set to x instead of x-1 since west will draw newTile where (x,y) is.
		int w = x;
		int e = x+1;
		
		while (w >= 0 && Tile.compareTo(map.getTile(w,y),targetTile)) {
			map.setTile(w, y, newTile);
			w--;
		}
		w++; // Back off to prevent from going out-of-bounds.
		
		while (e < mapWidth && Tile.compareTo(map.getTile(e,y),targetTile)) {
			map.setTile(e, y, newTile);
			e++;
		}
		e--;
		
		// Recursively draw in newTile for tiles north and south of tile(i,y).
		for (int i = w; i <= e; i++) {
			if (y > 0 && y < mapHeight - 1 && 
					Tile.compareTo(map.getTile(i,y-1),newTile) && 
					Tile.compareTo(map.getTile(i,y+1),newTile)) {
				continue;
			}
			if (y > 0 && Tile.compareTo(map.getTile(i,y-1),targetTile)) {
				floodFill(i, y-1, targetTile, newTile);
			}
			if (y < mapHeight - 1 && Tile.compareTo(map.getTile(i,y+1),targetTile)) {
				floodFill(i, y+1, targetTile, newTile);
			}
		}
		fruitEditor.addChanges(new MapChangeCommand(map,fruitEditor.getTileset(),mapIds));
	}*/
	
	public void setViewport(JViewport vp) {
		viewport = vp;
	}
	
	public synchronized void setMap(Map m) {
		fruitEditor.setMap(m);
		
		map = m;
		
		mapWidth = m.getWidth();
		mapHeight = m.getHeight();
		
		gridWidth = m.getTileWidth();
		gridHeight = m.getTileHeight();
		
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		
		if (!isPanelActive()) {
			setPanelActive(true);
		}
		
		fruitEditor.setActiveFile(null);
		fruitEditor.update();
	}
	
	public void setMapName(String n) {
		if (isPanelActive() && map.getName() != null)
			fruitEditor.addChanges(new MapRenameCommand(map,n));
		
		map.setName(n);
	}
	
	public synchronized void resizeMap(int w, int h) {
		if (isPanelActive())
			fruitEditor.addChanges(new MapResizeCommand(this,map,w,h));
		
		mapWidth = w;
		mapHeight = h;
		map.resize(mapWidth,mapHeight);
		
		setPreferredSize(new Dimension(mapWidth*gridWidth, mapHeight*gridHeight));
		
		update();
	}
	
	public synchronized void shiftMap(int dir, int t) {
		int [][][] mapIds = map.getMapIntArray();
		switch(dir) {
		case ShiftDialog.UP:
			map.shift(0, -t);
			break;
		case ShiftDialog.LEFT:
			map.shift(-t, 0);
			break;
		case ShiftDialog.RIGHT:
			map.shift(t, 0);
			break;
		case ShiftDialog.DOWN:
			map.shift(0, t);
			break;
		}
		fruitEditor.addChanges(new MapChangeCommand(map,fruitEditor.getTileset(),mapIds));
		update();
	}
	
	/**========================================
	 * setMapSize(w,h) - Set map panel size.
	//=========================================**/
	public void setMapSize(int w, int h) {
		mapWidth = w;
		mapHeight = h;
	}
	
	/**========================================
	 * setGrid() - Set grid on/off.
	//=========================================**/
	public void setGrid(boolean gr) {
		grid = gr;
	}
	
	public void setPanelActive(boolean act) {
		fruitEditor.setPanelActive(act);
	}
	
	/**========================================
	// setDrawMode(drawMode) - Set the draw mode.
	//=========================================**/
	public void setDrawMode(DrawMode d) {
		drawMode = d;
	}
	
	/**========================================
	 * setMode(mode) - Set mode.
	//=========================================**/
	public void setMode(EditorMode m) {
		editorMode = m;
	}
	
	public boolean isPanelActive() {
		return fruitEditor.isPanelActive();
	}
	
	public int getMapX() {
		return pixelToTile(mouseX,mouseY).x;
	}
	
	public int getMapY() {
		return pixelToTile(mouseX,mouseY).y;
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
	 * tileToPixel(x,y) - Convert tile to pixel coordinates.
	//========================================**/
	public Point tileToPixel(int x, int y) {
		return new Point(x * gridWidth, y * gridHeight);
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
	
	/**=======================================
	 * actionPerformed(ActionEvent) - Perform ActionEvents.
	//========================================**/
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if (src == renameItem) {
			new RenameDialog(fruitEditor);
		} else if (src == shiftItem) {
			new ShiftDialog(fruitEditor);
		}
	}
	
	/**=======================================
	 * MOUSE MOTION LISTENER METHODS.
	//========================================**/
	public void mouseMoved(MouseEvent e) {
		mouseX = snap(e.getX(), gridWidth);
		mouseY = snap(e.getY(), gridHeight);
		int tx = pixelToTile(mouseX,mouseY).x; // make the tile coordinates
		int ty = pixelToTile(mouseX,mouseY).y;
		
		// Set status panel
		if (isPanelActive() && checkBounds(tx,ty,mapWidth,mapHeight)) {
			fruitEditor.getStatusPanel().setCursorLocation(tx,ty);
		} else {
			fruitEditor.getStatusPanel().setCursorLocation();
		}
		
		fruitEditor.update();
	}
	
	public void mouseDragged(MouseEvent e) {
		int btn = e.getButton();
		int newMouseX = snap(e.getX(), gridWidth);
		int newMouseY = snap(e.getY(), gridHeight);
		
		int tx = pixelToTile(mouseX,mouseY).x;
		int ty = pixelToTile(mouseX,mouseY).y;
		int ntx = pixelToTile(newMouseX,newMouseY).x;
		int nty = pixelToTile(newMouseX,newMouseY).y;
		
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
				if (drawMode == DrawMode.PENCIL || drawMode == DrawMode.FILL) {
					// Update current mouse coords to ensure 
					// draw cursor is flush with mouse cursor
					mouseX = snap(e.getX(), gridWidth);
					mouseY = snap(e.getY(), gridHeight);
					tx = pixelToTile(mouseX,mouseY).x;
					ty = pixelToTile(mouseX,mouseY).y;	
					
					mapPressed(tx,ty);
				} else {
					mapDragged(tx,ty,ntx,nty);
				}
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
			mouseX = snap(e.getX(), gridWidth);
			mouseY = snap(e.getY(), gridHeight);
			int tx = pixelToTile(mouseX,mouseY).x;
			int ty = pixelToTile(mouseX,mouseY).y;
			
			if (isPanelActive() && 
					checkBounds(tx,ty,mapWidth,mapHeight)) {
				mapPressed(tx,ty);
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		int btn = e.getButton();
		
		// if left-click btn released
		if (btn == MouseEvent.BUTTON1) {
			mouseX = snap(e.getX(), gridWidth);
			mouseY = snap(e.getY(), gridHeight);
		}
	}
	
	public void mouseClicked(MouseEvent e) {
		int btn = e.getButton();
		
		// if right-click btn is clicked
		if (btn == MouseEvent.BUTTON3) {
			mouseX = e.getX();
			mouseY = e.getY();
			if (isPanelActive() &&
				checkBounds(mouseX,mouseY,mapWidth*gridWidth,mapHeight*gridHeight)) {
				popupMenu.show(this, mouseX, mouseY);
			}
		}
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	/**================================
	 * KEY LISTENER METHODS.
	//=================================**/
	private class KeyClass extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			boolean up = (keyCode == KeyEvent.VK_UP);
			boolean left = (keyCode == KeyEvent.VK_LEFT);
			boolean right = (keyCode == KeyEvent.VK_RIGHT);
			boolean down = (keyCode == KeyEvent.VK_DOWN);
			
			if (editorMode.equals(EditorMode.EVENT_MODE)) {
				if (up) {
					System.out.println("UP pressed");
					moveUp(true);
				}
				if (left) {
					System.out.println("LEFT pressed");
					moveLeft(true);
				}
				if (right) {
					System.out.println("RIGHT pressed");
					moveRight(true);
				}
				if (down) {
					System.out.println("DOWN pressed");
					moveDown(true);
				}
				update();
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			int keyCode = e.getKeyCode();
			
			boolean up = (keyCode == KeyEvent.VK_UP);
			boolean left = (keyCode == KeyEvent.VK_LEFT);
			boolean right = (keyCode == KeyEvent.VK_RIGHT);
			boolean down = (keyCode == KeyEvent.VK_DOWN);
			
			if (editorMode.equals(EditorMode.EVENT_MODE)) {
				if (up) {
					System.out.println("UP released");
					moveUp(false);
				}
				if (left) {
					System.out.println("LEFT released");
					moveLeft(false);
				}
				if (right) {
					System.out.println("RIGHT released");
					moveRight(false);
				}
				if (down) {
					System.out.println("DOWN released");
					moveDown(false);
				}
				update();
			}
		}
		
		public void moveUp(boolean b) {
			if (b && cursorY > 0) {
				int cy = pixelToTile(cursorX,cursorY).y;
				cy--;
				cy = tileToPixel(cursorX,cursorY).y;
				cursorY = cy;
				b = false;
			}
		}
	
		
		public void moveLeft(boolean b) {
			if (b && cursorX > 0) {
				int cx = pixelToTile(cursorX,cursorY).x;
				cx--;
				cx = tileToPixel(cursorX,cursorY).x;
				cursorX = cx;
				b = false;
			}
		}
	
		public void moveRight(boolean b) {
			if (cursorX < mapWidth*gridWidth) {
				int cx = pixelToTile(cursorX,cursorY).x;
				cx++;
				cx = tileToPixel(cursorX,cursorY).x;
				cursorX = cx;
				b = false;
			}
		}
		
		public void moveDown(boolean b) {
			if (b && cursorY < mapHeight*gridHeight) {
				int cy = pixelToTile(cursorX,cursorY).y;
				cy++;
				cy = tileToPixel(cursorX,cursorY).y;
				cursorY = cy;
				b = false;
			}
		}
	}
}