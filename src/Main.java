/**====================================
 * 
 * FruitEditor
 *
 * @author boaromayo
 * 
 * Copyright (c) 2015-2017 boaromayo/Nico Poblete
 * 
 *=====================================*/
package FruitEditor;

import java.awt.EventQueue;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new FruitEditor();
			}
		});
	}
}