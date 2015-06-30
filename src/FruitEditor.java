package FruitEditor;

import javax.swing.*;

public class FruitEditor implements Runnable {
	// MAIN FRAME.
	private FruitFrame fruitFrame;
  
	public FruitEditor() {
		fruitFrame = new FruitFrame();
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
