package FruitEditor;

import java.awt.*;
import java.awt.image.*;

import java.io.*;

import javax.swing.*;
import javax.imageio.*;

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
			synchronized (FruitImgBank.class) {
				if (singleton == null) {
					singleton = new FruitImgBank();
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
	// loadBufferedImage(path) - Load a BufferedImage.
	//====================================
	public BufferedImage loadBufferedImage(String path) {
		File file;
		FileInputStream fis;
		BufferedImage img;
		try {
			file = new File(path);
			fis = new FileInputStream(file);
			System.out.println("Loading image...");
			img = ImageIO.read(fis);
			BufferedImage newimg = convertToARGB(img, 0, 0);
			fis.close();
			return newimg;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load buffered image " + path);
			e.printStackTrace();
		}
		
		return null;
	}
	
	//====================================
	// loadBufferedImage(path,x,y,width,height) - Load a BufferedImage, find (x,y), and note the width and height.
	//====================================
	public BufferedImage loadBufferedImage(String path, int x, int y) {
		File file;
		FileInputStream fis;
		BufferedImage img;
		try {
			System.out.println("Loading image...");
			file = new File(path);
			fis = new FileInputStream(file);
			img = ImageIO.read(fis);
			BufferedImage newimg = convertToARGB(img, x, y);
			fis.close();
			return newimg;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load buffered image " + path);
			e.printStackTrace();
		}
		
		return null;
	}
	
	//====================================
	// convertToARGB(img,x,y) - Convert BufferedImage to ARGB type for faster drawing, and draw image from (x,y).
	// Special thanks to Marco13 from this tutorial:
	// https://stackoverflow.com/questions/22230866/java-game-images-loading-very-slowly/22231370#
	//====================================
	public BufferedImage convertToARGB(BufferedImage img, int x, int y) {
		BufferedImage newimg = new BufferedImage(img.getWidth(), 
				img.getHeight(), 
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = newimg.createGraphics();
		g.drawImage(img, x, y, null);
		g.dispose();
		return newimg;
	}
}
