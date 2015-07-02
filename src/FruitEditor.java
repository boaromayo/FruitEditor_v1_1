package FruitEditor;

import java.awt.*;
//import javax.swing.*;

public class FruitEditor implements Runnable {
	// MAIN FRAME.
	private FruitFrame fruitFrame;
  
	public FruitEditor() {
		fruitFrame = new FruitFrame();
		fruitFrame.setPreferredSize(new Dimension(640, 480));
	}
  
	public void run() {
		try {
			while (true) {
        
			}
    	
			
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
