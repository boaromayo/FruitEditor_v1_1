package FruitEditor;

import java.awt.*;
import java.awt.image.*;

public class Tile {
	// IMAGE.
	private BufferedImage img;
	
	// IMAGE DIMENSIONS.
	private int imgWidth;
	private int imgHeight;
	
	// ID AND NAME.
	private int id;
	private String name;
	
	// TILE PROPERTIES.
	private boolean solid;
	private boolean transparent;
	private boolean danger;
	
	public Tile() {
		this.img = null;
		this.id = -1;
		this.name = "None";
		this.solid = false;
		this.transparent = false;
		this.danger = false;
		
		imgWidth = 0;
		imgHeight = 0;
	}
	
	public Tile(Tile t) {
		this(t.id, t.img, t.name, t.solid, t.transparent);
	}
	
	public Tile(int id, BufferedImage img, String name) {
		try {
			this.img = img;
			this.id = id;
			this.name = name;
			imgWidth = img.getWidth();
			imgHeight = img.getHeight();
		} catch (RuntimeException e) {
			System.err.println("ERROR: Could not find image " + name);
			System.exit(1);
		}
		this.solid = false;
		this.transparent = false;
		this.danger = false;
	}
	
	public Tile(int id, BufferedImage img, String name, boolean solid) {
		this(id, img, name);
		this.solid = solid;
		this.transparent = false;
		this.danger = false;
	}
	
	public Tile(int id, BufferedImage img, String name, boolean solid, boolean transparent) {
		this(id, img, name, solid);
		this.transparent = transparent;
		this.danger = false;
	}
	
	/*public void setTile(Tile t) {
		if (t == null) return;
		
		img = t.getImage();
		id = t.getID();
		name = (t.getName() == null) ? "" : t.getName();
		
		setSolid(t.isSolid());
		setDanger(t.isDangerous());
		setTransparent(t.isTransparent());
	}*/
	
	public static void replace(Tile t1, Tile t2) {
		if (t1 == null || t2 == null) return;
		
		if (!t1.equals(t2)) {
			t1 = t2;
		}
	}
	
	public void setSolid(boolean s) { solid = s; }
	
	public void setTransparent(boolean t) { transparent = t; }
	
	public void setDanger(boolean d) { danger = d; }
	
	public boolean equals(Tile t) {
		if (this == null || t == null) return false;
		
		if (this.id == t.id) {
			return true;
		}
		
		return false;
	}
	
	public static boolean compareTo(Tile t1, Tile t2) {
		if (t1 == null && t2 == null) return true;
		
		if (t1 == null) return false;
		
		return t1.equals(t2);
	}
	
	public Tile getTile() { return this; }
	
	public int getID() { return id; }
	
	public String getName() { return name; }
	
	public int getWidth() { return imgWidth; }
	
	public int getHeight() { return imgHeight; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }
	
	public boolean isDangerous() { return danger; }
	
	public void draw(Graphics g, int x, int y) {
		if (img != null) {
			g.drawImage(img, x, y, null);
		}
	}
}