package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class MapPanel extends JPanel {
	
	private Map map;
	
	public MapPanel() {
		map = new Map(20, 15);
	}
	
	public MapPanel(Map m) {
		map = m;
		
		setLayout(new FlowLayout());
		setViewport(
	}
	
	public void drawGrid() {
		for (int r=0; r < map.getRows(); r++) {
			for (int c=0; c < map.getCols(); c++) {
				
			}
		}
	}
}
