package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class FruitPanel extends JPanel implements Runnable {
	
	// FILES.
	private FruitEditor fruitEditor;
	
	// PANELS.
	private MapPanel mapPanel;
	private TilePanel tilePanel;
	
	// PANEL PANES.
	JScrollPane tileScrollPane;
	//JTabbedPane tileTabbedPane;
	private JScrollPane mapScrollPane;
	
	// MAIN PANELS.
	JPanel leftPanel;
	JPanel rightPanel;
	
	public FruitPanel(FruitEditor f) {
		fruitEditor = f;
		
		setLayout(new BorderLayout());
		
		setupPanels();
	}
	
	private void setupPanels() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		mapPanel = new MapPanel(fruitEditor);
		tilePanel = new TilePanel(fruitEditor);
		//mapListPanel = new MapListPanel();

		tileScrollPane = new JScrollPane(tilePanel);
		//tileTabbedPane = new JTabbedPane();
		mapScrollPane = new JScrollPane(mapPanel);
	
		mapPanel.setViewport(mapScrollPane.getViewport());
		tilePanel.setViewport(tileScrollPane.getViewport());
		
		//leftPanel.setLayout(new FlowLayout());
		
		//rightPanel.setLayout(new BorderLayout());
		
		//leftPanel.add(tilePanel);
		//leftPanel.add(mapListPanel);
		rightPanel.add(mapPanel);
		
		if (!fruitEditor.isPanelActive() || fruitEditor.getMap() == null) {
			disablePanels();
		}
		
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		
		repaint();
	}
	
	private void disablePanels() {
		leftPanel.setEnabled(false);
		rightPanel.setEnabled(false);
	}
	
	public void run() {
		setPreferredSize(new Dimension(
				FruitEditor.SCREEN_WIDTH, 
				FruitEditor.SCREEN_HEIGHT));
		
		long startTime, diffTime;
		
		long targetTime = 1000 / FruitEditor.FPS;
		
		long waitTime;
		//long elapsedTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = FruitEditor.FPS;
		
		try {
			while (true) {
				startTime = System.nanoTime();
				
				update();
				repaint();
				
				diffTime = (System.nanoTime() - startTime) / 1000000;
				waitTime = targetTime - diffTime;
				
				if (waitTime < 0) {
					waitTime = targetTime;
				}
				
				Thread.sleep(waitTime);
				
				//elapsedTime += System.nanoTime() - startTime;
				
				frameCount++;
				
				if (frameCount == maxFrameCount) {
					frameCount = 0;
					//elapsedTime = 0;
				}
			}
		} catch (Exception e) {
			System.err.println("ERROR: Cannot open the panels properly. Reason: " +
					e.getMessage());
			e.getStackTrace();
			System.exit(1);
		}
	}
	
	public MapPanel getMapPanel() {
		if (mapPanel != null)
			return mapPanel;
		
		return null;
	}
	
	public TilePanel getTilePanel() {
		if (tilePanel != null)
			return tilePanel;
		
		return null;
	}
	
	public void update() {
		mapPanel.update();
		tilePanel.update();
	}
}