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
	
	// NAME.
	private String name;
	
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
	
	// TILES.
	private Tile[][][] fruitTiles;
	
	// CURSOR.
	//private Cursor cursor;
	
	public Map() {
		this(8, 8, 24, 24);
	}
	
	public Map(int width, int height) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		scaleFactor = 1;

		initTiles();
		setScale(scaleFactor);
	}
	
	public Map(int width, int height, int depth) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = depth;
		scaleFactor = 1;
		
		initTiles();
		setScale(scaleFactor);
	}
	
	public Map(int width, int height, int gw, int gh) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		gridWidth = gw;
		gridHeight = gh;
		scaleFactor = 1;
		
		initTiles();
		setScale(scaleFactor);
	}
	
	public void initTiles() { 
		fruitTiles = new Tile[mapHeight][mapWidth][mapDepth];
	}
	
	public void draw(Graphics g) {
		int r = fruitTiles.length;
		int c = fruitTiles[0].length;
		
		for (int i=0; i < r; i++) {
			for (int j=0; j < c; j++) {
				if (getTile(j,i) != null) {
					g.drawImage(getTile(j,i).getImage(), 
							j*scaleWidth, 
							i*scaleHeight, 
							j*scaleWidth + gridWidth, 
							i*scaleHeight + gridHeight, 
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
	
	public void setTile(int x, int y, Tile t) {
		setTile(x,y,0,t); // set first layer by default
	}
	
	public void setTile(int x, int y, int z, Tile t) {
		fruitTiles[y][x][z] = t;
	}
	
	public String getName() { return name; }
	
	public int getScale() { return scaleFactor; }
	
	public int getScaleWidth() { return scaleWidth; }
	
	public int getScaleHeight() { return scaleHeight; }
	
	public int getRows() { return mapHeight; }
	
	public int getCols() { return mapWidth; }
	
	public int getLayers() { return mapDepth; }
	
	public int getGridWidth() { return gridWidth; }
	
	public int getGridHeight() { return gridHeight; }
	
	public Tile getTile(int x, int y) {
		return getTile(x,y,0); // get first layer by default
	}
	
	public Tile getTile(int x, int y, int z) {
		return fruitTiles[y][x][z];
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
					if (fruitTiles[i][j][k] == null)
						sheet[i][j][k] = -1;
					else
						sheet[i][j][k] = fruitTiles[i][j][k].getID();
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
				if (fruitTiles[i][j][layer] == null)
					sheet[i][j] = -1;
				else
					sheet[i][j] = fruitTiles[i][j][layer].getID();
			}
		}
		
		return sheet;
	}
}