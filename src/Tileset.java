package FruitEditor;

import java.awt.image.*;

public class Tileset {
	// TILESET IMAGE.
	private BufferedImage tilesetImg;
	
	// TILESET PATH.
	private String tilesetPath;
	
	// TILES.
	private Tile[][] fruitTiles;
	
	// TILESET DIMENSIONS.
	private int tilesetWidth;
	private int tilesetHeight;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	public Tileset(String path) {
		tilesetImg = FruitImgBank.get().
				loadBufferedImage(path);
		
		tilesetPath = path;
		
		gridWidth = 24;
		gridHeight = 24;
		
		tilesetWidth = tilesetImg.getWidth();
		tilesetHeight = tilesetImg.getHeight();
		
		int tw = (int)(tilesetWidth / gridWidth);
		int th = (int)(tilesetHeight / gridHeight);
		
		fruitTiles = new Tile[th][tw]; // Set number of tiles based on (tileset size / grid size).
	}
	
	public Tile getTile(int r, int c) { return fruitTiles[r][c]; }
	
	public Tile[][] getTileset() { return fruitTiles; }
	
	public int getWidth() { return tilesetWidth; }
	
	public int getHeight() { return tilesetHeight; }

	public BufferedImage[][] getTilesetImages() {
		int th = fruitTiles.length;
		int tw = fruitTiles[0].length;
		BufferedImage[][] tileImg = new BufferedImage[th][tw];
		
		int i, j; // Loop counters
		
		for (i=0; i < th; i++) {
			for (j=0; j < tw; j++) {
				tileImg[i][j] = FruitImgBank.get().
						loadBufferedImage(tilesetPath, i, j, gridWidth, gridHeight);
			}
		}
		
		return tileImg;
	}
}
