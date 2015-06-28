package FruitEditor;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

public class Map {
	// DIMENSIONS.
	private int mapWidth;
	private int mapHeight;
	private int mapDepth;
	
	// TILE DIMENSIONS.
	private int tileWidth;
	private int tileHeight;
	
	// SCALE DIMENSIONS.
	private int scaleFactor;
	private int scaleWidth;
	private int scaleHeight;
	
	// TILES.
	private Tile[][][] fruitTiles;
	
	public Map() {
		mapWidth = 20;
		mapHeight = 15;
		mapDepth = 1;
		scaleFactor = 1;
		
		tileWidth = 24;
		tileHeight = 24;
		
		initTiles();
	}
	
	public Map(int width, int height) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = 1;
		scaleFactor = 1;
		
		tileWidth = 24;
		tileHeight = 24;
		
		initTiles();
	}
	
	public Map(int width, int height, int depth) {
		mapWidth = width;
		mapHeight = height;
		mapDepth = depth;
		scaleFactor = scale;
		
		tileWidth = 24;
		tileHeight = 24;
		
		initTiles();
	}
	
	public void initTiles() { 
		fruitTiles = new Tile[mapWidth][mapHeight][mapDepth];
	}
	
	public void setScale(int s) { 
		scaleFactor = s;
		scaleWidth = mapWidth * s;
		scaleHeight = mapHeight * s;
	}
	
	public int getScale() { return scaleFactor; }
	
	public int getRows() { return mapHeight; }
	
	public int getCols() { return mapWidth; }
	
	public Tile getTile(int x, int y, int z) {
		return fruitTiles[x][y][z];
	}
	
	public void setTile(int x, int y, int z, Tile t) {
		fruitTiles[x][y][z] = t;
	}
	
	public int[][][] toIntArray() {
		int[][][] sheet = new int[mapWidth][mapHeight][mapDepth];
		int h = sheet.length;
		int w = sheet[0].length;
		int d = sheet[0][0].length;
		
		for (int i=0; i < h; i++) {
			for (int j=0; j < w; j++) {
				for (int k=0; k < d; k++) {
					sheet[i][j][k] = fruitTiles[i][j][k].getType();
				}
			}
		}
		
		return sheet;
	}
}
