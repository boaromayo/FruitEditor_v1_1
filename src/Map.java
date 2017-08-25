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

	// TILE DIMENSIONS.
	private int tileWidth;
	private int tileHeight;
	
	public Map() {
		this(8, 8, 24, 24);
	}
	
	public Map(int width, int height) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		tileWidth = FruitEditor.GRID_SIZE;
		tileHeight = FruitEditor.GRID_SIZE;
		scaleFactor = 1;

		mapTiles = new Tile[mapHeight][mapWidth][mapDepth];
		setScale(scaleFactor);
	}
	
	public Map(int width, int height, int depth) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = depth;
		tileWidth = FruitEditor.GRID_SIZE;
		tileHeight = FruitEditor.GRID_SIZE;
		scaleFactor = 1;
		
		mapTiles = new Tile[mapHeight][mapWidth][mapDepth];
		setScale(scaleFactor);
	}
	
	public Map(int width, int height, int tw, int th) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		tileWidth = tw;
		tileHeight = th;
		scaleFactor = 1;
		
		mapTiles = new Tile[mapHeight][mapWidth][mapDepth];
		setScale(scaleFactor);
	}
	
	public void draw(Graphics g, int x, int y, Dimension size) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, mapWidth*tileWidth, mapHeight*tileHeight);
		
		int xmin = Math.max(x / tileWidth, 0);
		int ymin = Math.max(y / tileHeight, 0);
		
		int r = mapTiles.length;
		int c = mapTiles[0].length;

		int xmax = Math.min((x + (int)size.getWidth()) / tileWidth, c);
		int ymax = Math.min((y + (int)size.getHeight()) / tileHeight, r);
		
		for (int j=ymin; j < ymax; j++) {
			for (int i=xmin; i < xmax; i++) {
				if (getTile(i,j) != null)
					getTile(i,j).draw(g, i*tileWidth, j*tileHeight);
			}
		}
	}
	
	public void resize(int w, int h) {
		Tile[][][] newMapTiles = new Tile[h][w][mapDepth];
		
		mapWidth = w;
		mapHeight = h;
		
		int r = Math.min(mapTiles.length, h);
		int c = Math.min(mapTiles[0].length, w);
		
		for (int j=0; j < r; j++) {
			for (int i=0; i < c; i++) {
				newMapTiles[j][i][0] = mapTiles[j][i][0];
			}
		}
		mapTiles = newMapTiles;
	}
	
	public void shift(int tx, int ty) {
		Tile[][][] newMapTiles = new Tile[mapHeight][mapWidth][mapDepth];
		
		int r = newMapTiles.length;
		int c = newMapTiles[0].length;
		
		int xstart = Math.max(0, -tx);
		int ystart = Math.max(0, -ty);
		
		int xend = Math.min(c, c - tx);
		int yend = Math.min(r, r - ty);
		
		for (int j=ystart; j < yend; j++) {
			for (int i=xstart; i < xend; i++) {
				newMapTiles[j+ty][i+tx][0] = mapTiles[j][i][0];
			}
		}
		mapTiles = newMapTiles;
	}
	
	public void setName(String n) {
		name = n;
	}
	
	public void setScale(int s) { 
		scaleFactor = s;
		scaleWidth = (int)(mapWidth / (scaleFactor > 0 ? scaleFactor : 1));
		scaleHeight = (int)(mapHeight / (scaleFactor > 0 ? scaleFactor : 1));
		tileWidth /= scaleFactor;
		tileHeight /= scaleFactor;
	}
	
	public void setTile(int x, int y, Tile t) {
		setTile(x,y,0,t); // set first layer by default
	}
	
	public void setTile(int x, int y, int z, Tile t) {
		mapTiles[y][x][z] = t;
	}
	
	public void setTiles(int[][] ids, Tileset t) {
		setTiles(ids,t,0); // set all tiles in first layer as default
	}
	
	public void setTiles(int[][] ids, Tileset t, int layer) {
		if (ids != null) {
			int rows = ids.length;
			int cols = ids[0].length;
			
			int i, j; // Loop counters
			
			for (i=0; i < rows; i++)
				for (j=0; j < cols; j++)
					if (ids[i][j] < 0)
						mapTiles[i][j][layer] = null;
					else
						mapTiles[i][j][layer] = t.getTile(ids[i][j]);
		}
	}
	
	public void setAll(int[][][] ids, Tileset t) {
		if (ids != null) {
			int rows = ids.length;
			int cols = ids[0].length;
			int layers = ids[0][0].length;
			
			int i, j, k; // Loop counters
			
			for (i=0; i < rows; i++) {
				for (j=0; j < cols; j++) {
					for (k=0; k < layers; k++) {
						if (ids[i][j][k] < 0)
							mapTiles[i][j][k] = null;
						else
							mapTiles[i][j][k] = t.getTile(ids[i][j][k]);
					}
				}
			}
		}
	}
	
	public String getName() { return name; }
	
	public int getScale() { return scaleFactor; }
	
	public int getScaleWidth() { return scaleWidth; }
	
	public int getScaleHeight() { return scaleHeight; }
	
	public int getWidth() { return mapWidth; }
	
	public int getHeight() { return mapHeight; }
	
	public int getDepth() { return mapDepth; }
	
	public int getTileWidth() { return tileWidth; }
	
	public int getTileHeight() { return tileHeight; }
	
	public Tile getTile(int x, int y) {
		return getTile(x,y,0); // get first layer by default
	}
	
	public Tile getTile(int x, int y, int z) {
		return mapTiles[y][x][z];
	}
	
	public int[][][] getMapIntArray() {
		int[][][] sheet = new int[mapHeight][mapWidth][mapDepth];
		int rows = sheet.length;
		int cols = sheet[0].length;
		int layers = sheet[0][0].length;
		
		int i, j, k; // loop counters
		
		for (i=0; i < rows; i++) {
			for (j=0; j < cols; j++) {
				for (k=0; k < layers; k++) {
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
		int rows = sheet.length;
		int cols = sheet[0].length;
		
		int i, j; // loop counters
		
		for (i=0; i < rows; i++) {
			for (j=0; j < cols; j++) {
				if (mapTiles[i][j][layer] == null)
					sheet[i][j] = -1;
				else
					sheet[i][j] = mapTiles[i][j][layer].getID();
			}
		}
		
		return sheet;
	}
}