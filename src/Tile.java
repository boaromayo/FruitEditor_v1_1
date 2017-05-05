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
			this.img = FruitImgBank.get().loadBufferedImage(
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
	
	public void setTile(Tile t) {
		if (t != null) {
			img = t.getImage();
			id = t.getID();
			name = (t.getName() == null) ? "" : t.getName();
			this.setSolid(t.isSolid());
			this.setDanger(t.isDangerous());
			this.setTransparent(t.isTransparent());
		} else {
			return;
		}
	}
	
	public void replace(Tile t1, Tile t2) {
		if (t1 == null || t2 == null) {
			return;
		} else if (t1 != t2) {
			t1.setTile(t2);
		}
	}
	
	public void setSolid(boolean s) { solid = s; }
	
	public void setTransparent(boolean t) { transparent = t; }
	
	public void setDanger(boolean d) { danger = d; }
	
	public Tile getTile() { return this; }
	
	public BufferedImage getImage() { return img; }
	
	public int getID() { return id; }
	
	public String getName() { return name; }
	
	public int getWidth() { return imgWidth; }
	
	public int getHeight() { return imgHeight; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }
	
	public boolean isDangerous() { return danger; }
	
	
}