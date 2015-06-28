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
	
	// TYPE AND NAME.
	private int type;
	private String name;
	
	// TILE CONDITIONS.
	private boolean solid;
	private boolean transparent;
	private boolean danger;
	
	public Tile() {
		this.img = null;
		this.type = -1;
		this.name = "";
		this.solid = false;
		this.transparent = false;
		this.danger = false;
		
		imageWidth = img.getWidth(null);
		imageHeight = img.getHeight(null);
		scaleWidth = imgWidth;
		scaleHeight = imgHeight;
	}
	
	public Tile(int type, String path, String name) {
		this();
		//try {
		//this.img = new BufferedImage(new FruitImgLoader(path));
		//} catch (RuntimeException e) {
			//System.err.println("ERROR: Could not find image in " + path);
		//}
		this.type = type;
		this.name = name;
	}
	
	public Tile(int type, String path, String name, boolean solid) {
		this(type, path, name);
		this.solid = solid;
	}
	
	public Tile(int type, String path, String name, boolean solid, boolean transparent) {
		this(type, path, name, solid);
		this.transparent = transparent;
	}
	
	public BufferedImage getImage() { return img; }
	
	public int getType() { return type; }
	
	public String getName() { return name; }
	
	public boolean isSolid() { return solid; }
	
	public boolean isTransparent() { return transparent; }
	
	public boolean isDanger() { return danger; }
	
	public void setSolid(boolean s) { solid = s; }
	
	public void setTransparent(boolean t) { transparent = t; }
	
	public void setDanger(boolean d) { danger = d; }
	
	
}
