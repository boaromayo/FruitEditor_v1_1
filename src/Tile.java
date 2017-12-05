package FruitEditor;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class Tile implements Serializable {
	// IMAGE.
	private BufferedImage img;
	
	// IMAGE DIMENSIONS.
	//private int imgWidth;
	//private int imgHeight;
	
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
	}
	
	public Tile(Tile t) {
		this(t.id, t.img, t.name, t.solid, t.transparent);
	}
	
	public Tile(int id, BufferedImage img, String name) {
		try {
			this.img = img;
			this.id = id;
			this.name = name;
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
	
	public static boolean compareTo(Tile t1, Tile t2) {
		if (t1 == null && t2 == null) return true;
		
		if (t1 == null) return false;
		
		return t1.equals(t2);
	}
	
	public boolean equals(Tile t) {
		if (t == null) return false;
		
		if (this.id == t.id) {
			return true;
		}
		
		return false;
	}
	
	public Tile getTile() { return this; }
	
	public int getID() { return id; }
	
	public String getName() { return name; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }
	
	public boolean isDangerous() { return danger; }
	
	public void draw(Graphics g, int x, int y) {
		if (img != null) {
			g.drawImage(img, x, y, null);
		}
	}
	
	public void writeTile() {
		try {
			// Start serialization.
			FileOutputStream fos = new FileOutputStream("tile.bin");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(getTile()); // Save in tile.
			
			// Close streams.
			oos.close();
			fos.close();
			
			// Debug only.
			System.out.println("Tile saved in tile.bin");	
		} catch (Exception e) {
			System.err.println("ERROR: Unable to write Tile object into file. REASON: " +
					e.getMessage());
			e.printStackTrace();
		}
	}
	
	public Tile readTile() {
		try {
			// Start deserialization (may be unstable).
			FileInputStream fis = new FileInputStream("tile.bin");
			ObjectInputStream ois = new ObjectInputStream(fis);
			Tile tile = (Tile)ois.readObject();
			
			// Close streams.
			ois.close();
			fis.close();
			
			// Debug console if things go right.
			System.out.println("Tile deserialization successful.");
			
			return tile;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to deserialize Tile object. REASON: " + 
					e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
}