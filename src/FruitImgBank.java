package FruitEditor;

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import javax.swing.*;

public class FruitImgBank {
	// SINGLETON STATIC VARIABLE.
	private static FruitImgBank singleton = null;
	
	private FruitImgBank() {}
	
	/**====================================
	// Call instance to ensure only one object is made for this class.
	//=====================================**/
	public static FruitImgBank get() {
		// Deal with thread concurrency issues with this block.
		if (singleton == null) {
			synchronized (FruitImgLoader.class) {
				if (singleton == null) {
					singleton = new FruitImgLoader();
				}
			}
		}
		
		return singleton;
	}
	
	/**===================================
	// IMAGE LOADING METHODS.
	//====================================**/
	//====================================
	// loadIconImage(path) - Load an ImageIcon.
	//====================================
	public ImageIcon loadIconImage(String path) {
		ImageIcon img;
		try {
			System.out.println("Loading image...");
			img = new ImageIcon(this.getClass().getResource(path));
			return img;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load image " + path);
			e.printStackTrace();
		}
			
		return null;
	}
		
	//====================================
	// loadBufferedImage(path,width,height) - Load a BufferedImage and place the width and height.
	//====================================
	public BufferedImage loadBufferedImage(String path, int width, int height) {
		BufferedImage img;
		try {
			System.out.println("Loading image...");
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			return img;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load buffered image " + path);
			e.printStackTrace();
		}
		
		return null;
	}
}
