package FruitEditor;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

import java.io.*;
import java.util.*;

public class Map {
	// CONSTANTS.
	public static final int MAP_SIZE = 999;
	public static final int OFFSET = 10;
	
	// SCALE CONSTANTS.
	public static final int SCALE_ONE = 1;
	public static final int SCALE_TWO = 2;
	public static final int SCALE_FOUR = 4;
	public static final int SCALE_EIGHT = 8;

	// DRAW MODE.
	private DrawMode drawMode;
	
	// MAP NAME.
	private String name;
	
	// TILES.
	private Tile[][][] mapTiles;
	
	// DIMENSIONS.
	private int mapWidth;
	private int mapHeight;
	private int mapDepth;
	
	// SCALE DIMENSIONS.
	private int scaleFactor;
	private int scaleWidth;
	private int scaleHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;

	// TILE DIMENSIONS.
	//private int tileWidth;
	//private int tileHeight;
	
	// CURSOR.
	//private Cursor cursor;
	
	public Map() {
		this(8, 8, 24, 24);
	}
	
	public Map(int width, int height) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		gridWidth = FruitEditor.GRID_SIZE;
		gridHeight = FruitEditor.GRID_SIZE;
		//tileWidth = FruitEditor.TILE_SIZE;
		//tileHeight = FruitEditor.TILE_SIZE;
		scaleFactor = 1;

		initTiles();
		setScale(scaleFactor);
		setDrawMode(DrawMode.PENCIL);
	}
	
	public Map(int width, int height, int depth) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = depth;
		gridWidth = FruitEditor.GRID_SIZE;
		gridHeight = FruitEditor.GRID_SIZE;
		//tileWidth = FruitEditor.TILE_SIZE;
		//tileHeight = FruitEditor.TILE_SIZE;
		scaleFactor = 1;
		
		initTiles();
		setScale(scaleFactor);
		setDrawMode(DrawMode.PENCIL);
	}
	
	public Map(int width, int height, int gw, int gh) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		gridWidth = gw;
		gridHeight = gh;
		//tileWidth = FruitEditor.TILE_SIZE;
		//tileHeight = FruitEditor.TILE_SIZE;
		scaleFactor = 1;
		
		initTiles();
		setScale(scaleFactor);
		setDrawMode(DrawMode.PENCIL);
	}
	
	public void initTiles() { 
		mapTiles = new Tile[mapHeight][mapWidth][mapDepth];
	}
	
	public void draw(Graphics g, int x, int y, Dimension size) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, mapWidth*gridWidth, mapHeight*gridHeight);
		
		x = Math.max(x, 0);
		y = Math.max(y, 0);
		
		int r = mapTiles.length;
		int c = mapTiles[0].length;
		
		r = Math.min(y + (int)size.getHeight(), r);
		c = Math.min(x + (int)size.getWidth(), c);
		
		for (int i=y; i < r; i++) {
			for (int j=x; j < c; j++) {
				if (getTile(j,i) != null) {
					g.drawImage(getTile(j,i).getImage(), 
							j*gridWidth, 
							i*gridHeight, 
							gridWidth, 
							gridHeight, 
							null);
				}
			}
		}
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setScale(int s) { 
		scaleFactor = s;
		scaleWidth = (int)(mapWidth / (scaleFactor > 0 ? scaleFactor : 1));
		scaleHeight = (int)(mapHeight / (scaleFactor > 0 ? scaleFactor : 1));
		gridWidth /= scaleFactor;
		gridHeight /= scaleFactor;
	}
	
	public void setWidth(int w) { mapWidth = w; }
	
	public void setHeight(int h) { mapHeight = h; }
	
	public void setDepth(int d) { mapDepth = d; }
	
	public void setGridWidth(int gw) { gridWidth = gw; }
	
	public void setGridHeight(int gh) { gridHeight = gh; }
	
	//public void setTileWidth(int tw) { tileWidth = tw; }
	
	//public void setTileHeight(int th) { tileHeight = th; }
	
	public void setTile(int x, int y, Tile t) {
		setTile(x,y,0,t); // set first layer by default
	}
	
	public void setTile(int x, int y, int z, Tile t) {
		mapTiles[y][x][z] = t;
	}
	
	/**========================================
	// setDrawMode(drawMode) - Set the draw mode.
	//=========================================**/
	public void setDrawMode(DrawMode d) {
		drawMode = d;
	}
	
	public String getName() { return name; }
	
	public int getScale() { return scaleFactor; }
	
	public int getScaleWidth() { return scaleWidth; }
	
	public int getScaleHeight() { return scaleHeight; }
	
	public int getWidth() { return mapWidth; }
	
	public int getHeight() { return mapHeight; }
	
	public int getLayers() { return mapDepth; }
	
	public int getGridWidth() { return gridWidth; }
	
	public int getGridHeight() { return gridHeight; }
	
	//public int getTileWidth() { return tileWidth; }
	
	//public int getTileHeight() { return tileHeight; }
	
	public Tile getTile(int x, int y) {
		return getTile(x,y,0); // get first layer by default
	}
	
	public Tile getTile(int x, int y, int z) {
		return mapTiles[y][x][z];
	}
	
	public DrawMode drawMode() {
		return drawMode;
	}
	
	public int[][][] getMapIntArray() {
		int[][][] sheet = new int[mapHeight][mapWidth][mapDepth];
		int h = sheet.length;
		int w = sheet[0].length;
		int d = sheet[0][0].length;
		
		int i, j, k; // loop counters
		
		for (i=0; i < h; i++) {
			for (j=0; j < w; j++) {
				for (k=0; k < d; k++) {
					if (mapTiles[i][j][k] == null)
						sheet[i][j][k] = -1;
					else
						sheet[i][j][k] = mapTiles[i][j][k].getID();
				}
			}
		}
		
		return sheet;
	}
	
	public int[][] getMapIntArray2() {
		return getMapIntArray2(0); // Get first layer of map
	}
	
	public int[][] getMapIntArray2(int layer) {
		int[][] sheet = new int[mapHeight][mapWidth];
		int r = sheet.length;
		int c = sheet[0].length;
		
		int i, j; // loop counters
		
		for (i=0; i < r; i++) {
			for (j=0; j < c; j++) {
				if (mapTiles[i][j][layer] == null)
					sheet[i][j] = -1;
				else
					sheet[i][j] = mapTiles[i][j][layer].getID();
			}
		}
		
		return sheet;
	}
}