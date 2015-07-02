package FruitEditor;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class FruitImgLoader {
	// SINGLETON STATIC VARIABLE.
	static FruitImgLoader singleton = null;
	
	/**===================================
	// IMAGE LOADING METHODS.
	//====================================**/
	//====================================
	// loadIconImage(path) - Load an ImageIcon.
	//====================================
	public static ImageIcon loadIconImage(String path) {
		ImageIcon img;
		try {
			img = new ImageIcon(singleton.getClass().getResource(path));
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
	public static BufferedImage loadBufferedImage(String path, int width, int height) {
		BufferedImage img;
		try {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			return img;
		} catch (Exception e) {
			System.err.println("ERROR: Unable to load buffered image " + path);
			e.printStackTrace();
		}
		
		return null;
	}
}
