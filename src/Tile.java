package FruitEditor;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class Tile {
	// IMAGE.
	private BufferedImage img;
	
	// IMAGE DIMENSIONS.
	private int imgWidth;
	private int imgHeight;
	
	// SCALE DIMENSIONS.
	private int scaleWidth;
	private int scaleHeight;
	
	// ID AND NAME.
	private int id;
	private String name;
	
	// TILE CONDITIONS.
	private boolean solid;
	private boolean transparent;
	private boolean danger;
	
	public Tile() {
		this.img = null;
		this.id = -1;
		this.name = "";
		this.solid = false;
		this.transparent = false;
		this.danger = false;
		
		imgWidth = img.getWidth();
		imgHeight = img.getHeight();
		scaleWidth = imgWidth;
		scaleHeight = imgHeight;
	}
	
	public Tile(int id, String path, String name) {
		this();
		try {
			this.img = FruitImgLoader.get().loadBufferedImage(
					path, imgWidth, imgHeight);
		} catch (RuntimeException e) {
			System.err.println(
					"ERROR: Could not find image in " + path);
		}
		this.id = id;
		this.name = name;
	}
	
	public Tile(int id, String path, String name, boolean solid) {
		this(id, path, name);
		this.solid = solid;
	}
	
	public Tile(int id, String path, String name, boolean solid, boolean transparent) {
		this(id, path, name, solid);
		this.transparent = transparent;
	}
	
	public BufferedImage getImage() { return img; }
	
	public int getID() { return id; }
	
	public String getName() { return name; }
	
	public int getWidth() { return imgWidth; }
	
	public int getHeight() { return imgHeight; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }
	
	public boolean isDanger() { return danger; }
	
	public void setSolid(boolean s) { solid = s; }
	
	public void setTransparent(boolean t) { transparent = t; }
	
	public void setDanger(boolean d) { danger = d; }
	
	
}
