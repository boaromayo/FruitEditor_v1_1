package FruitEditor;

import java.io.*;

import java.awt.*;
import java.awt.image.*;

public class Tileset {
	// TILESET IMAGE.
	private BufferedImage tilesetImg;
	
	// TILESET PATHS.
	private String tilesetPath;
	private String notePath; // text file to label the types (and properties if necessary) of tiles in tileset
	
	// TILES.
	private Tile[][] fruitTiles;
	
	// TILESET DIMENSIONS.
	private int tilesetWidth;
	private int tilesetHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	public Tileset() {
		gridWidth = FruitEditor.GRID_SIZE;
		gridHeight = FruitEditor.GRID_SIZE;
		
		tilesetWidth = gridWidth;
		tilesetHeight = gridHeight;
		
		int tw = (int)(tilesetWidth / gridWidth);
		int th = (int)(tilesetHeight / gridHeight);
		
		fruitTiles = new Tile[th][tw]; // Set number of tiles based on (tileset size / grid size).
		fruitTiles[0][0] = new Tile(); // Set first tile as blank tile.
		
		tilesetImg = null;
		
		tilesetPath = "";	
	}
	
	public Tileset(String path) {
		gridWidth = FruitEditor.GRID_SIZE;
		gridHeight = FruitEditor.GRID_SIZE;
		
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int tw = (int)(tilesetWidth / gridWidth);
		int th = (int)(tilesetHeight / gridHeight);
		
		fruitTiles = new Tile[th][tw];
		
		loadTileset(path);
	}
	
	public Tileset(String path, String note) {
		gridWidth = FruitEditor.GRID_SIZE;
		gridHeight = FruitEditor.GRID_SIZE;
		
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int tw = (int)(tilesetWidth / gridWidth);
		int th = (int)(tilesetHeight / gridHeight);
		
		fruitTiles = new Tile[th][tw];
		
		loadTileset(path,note);
	}
	
	public Tileset(String path, int gw, int gh) {
		gridWidth = gw;
		gridHeight = gh;
		
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int tw = (int)(tilesetWidth / gridWidth);
		int th = (int)(tilesetHeight / gridHeight);
		
		fruitTiles = new Tile[th][tw];
		
		loadTileset(path);
	}
	
	public Tileset(String path, String note, int gw, int gh) {
		this(path,note);
		gridWidth = gw;
		gridHeight = gh;
	}
	
	public void loadTileset(String path) {
		int r, c; // Loop counters.
		int i = 0; // Tile id counter.
		int th = fruitTiles.length;
		int tw = fruitTiles[0].length;
		
		tilesetPath = path;
		notePath = "default.txt"; // filler String here
		
		BufferedImage[][] tileImg = getTilesetImages();
		
		for (r=0; r < th; r++) {
			for (c=0; c < tw; c++, i++) {
				fruitTiles[r][c] = new Tile(i, tileImg[r][c], "None");
			}
		}
	}
	
	public void loadTileset(String path, String note) {
		int r, c; // Loop counters.
		int i = 0; // Tile id counter.
		int th = fruitTiles.length;
		int tw = fruitTiles[0].length;
		
		tilesetPath = path;
		notePath = note;
		
		BufferedImage[][] tileImg = getTilesetImages(tilesetImg);
		
		try {
			File noteFile = new File(notePath);
			BufferedReader br = new BufferedReader(new FileReader(noteFile));
			String line;
			String [] lines = new String[th*tw];
			
			if (tilesetPath == br.readLine()) {
				line = br.readLine();
				while (line != null) {
					lines[i++] = line; // Increment counter after putting line into array.
					line = br.readLine(); // Go to next line.
				}
				
				i = 0;
				
				for (r=0; r < th; r++) {
					for (c=0; c < tw; c++, i++) {
						line = lines[i];
						
						if (line == null) {
							System.err.println("WARNING: There is no name for the selected tile.");
							line = "None";
						}
						
						fruitTiles[r][c] = new Tile(i, tileImg[r][c], line);
					}
				}
			} else {
				throw new Exception("ERROR: " + notePath + " does not match file " + tilesetPath);
			}
			
			br.close();
		} catch (Exception e) {
			System.err.println("ERROR: Could not read file " + tilesetPath);
			System.exit(1);
		}
	}
	
	public void draw(Graphics g, int x, int y, Dimension size) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, tilesetWidth, tilesetHeight);
		
		if (tilesetImg != null) {
			g.drawImage(tilesetImg, 0, 0, tilesetWidth, tilesetHeight, null);
		}
	}
	
	public void setGridWidth(int gw) { gridWidth = gw; }
	
	public void setGridHeight(int gh) { gridHeight = gh; }
	
	public Tile getTile(int r, int c) { return fruitTiles[r][c]; }
	
	public Tile[][] getTileset() { return fruitTiles; }
	
	public int getWidth() { return tilesetWidth; }
	
	public int getHeight() { return tilesetHeight; }
	
	public int getGridWidth() { return gridWidth; }
	
	public int getGridHeight() { return gridHeight; }

	public int getCols() { return fruitTiles[0].length; }
	
	public String getTilesetPath() { return tilesetPath; }
	
	public BufferedImage[][] getTilesetImages() {
		return getTilesetImages(tilesetPath);
	}
	
	private BufferedImage[][] getTilesetImages(String path) {
		int th = fruitTiles.length;
		int tw = fruitTiles[0].length;
		BufferedImage[][] tileImg = new BufferedImage[th][tw];
		
		int i, j; // Loop counters
		
		for (i=0; i < th; i++) {
			for (j=0; j < tw; j++) {
				tileImg[i][j] = FruitImgBank.get().
						loadBufferedImage(path, j*gridWidth, i*gridHeight, gridWidth, gridHeight);
			}
		}
		
		return tileImg;
	}
	
	private BufferedImage[][] getTilesetImages(BufferedImage img) {
		int th = fruitTiles.length;
		int tw = fruitTiles[0].length;
		BufferedImage[][] tileImg = new BufferedImage[th][tw];
		
		int i, j; // Loop counters
		
		for (i=0; i < th; i++) {
			for (j=0; j < tw; j++) {
				tileImg[i][j] = img.getSubimage(j*gridWidth, i*gridHeight, gridWidth, gridHeight);
			}
		}
		
		return tileImg;
	}
}
