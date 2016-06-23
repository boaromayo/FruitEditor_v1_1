package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import java.util.*;

public class MapPanel extends JPanel {
	// MAP.
	private Map map;
	
	// GRID DIMENSIONS.
	private int gridWidth;
	private int gridHeight;
	
	public MapPanel() {
		map = new Map(20, 15);
		
		gridWidth = gridHeight = 24;
	}
	
	public MapPanel(Map m) {
		map = m;
		
		setLayout(new FlowLayout());
		//setViewport(map.getViewport());
		
		gridWidth = gridHeight = 24;
	}
	
	
	public void draw(Graphics g) {
		drawGrid(g);
	}
	
	public void drawGrid(Graphics g) {
		for (int r=0; r < map.getRows(); r++) {
			g.drawLine(0, r*map.getRows(), map.getCols()*map.getScale(), r*map.getRows());
			for (int c=0; c < map.getCols(); c++) {
				g.drawLine(c*map.getCols(), 0, c*map.getCols(), map.getRows()*map.getScale());
			}
		}
	}
}