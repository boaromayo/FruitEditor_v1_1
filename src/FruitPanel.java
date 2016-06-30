package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class FruitPanel extends JPanel {
	
	// FILES.
	private FruitEditor fruitEditor;
	private Map mapFile;
	private FruitPanelListener fruitPanelListener;
	
	// PANELS.
	//private MapPanel mapPanel;
	//private TilePanel tilePanel;
	
	// PANEL PANES.
	//JScrollPane tileScrollPane;
	//JTabbedPane tileTabbedPane;
	//private JScrollPane mapScrollPane;
	
	// MAIN PANELS.
	JPanel leftPanel;
	JPanel rightPanel;
	
	public FruitPanel(FruitEditor f) {
		fruitEditor = f;
		mapFile = f.getMap();

		setLayout(new BorderLayout());
		
		setupPanels();
		
		//leftPanel.setLayout(new FlowLayout());
		//rightPanel.setLayout(new BorderLayout());
		
		//leftPanel.add(tilePanel);
		//leftPanel.add(mapListPanel);
		//rightPanel.add(mapPanel);
		
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		
		addMouseListener(fruitPanelListener);
		addMouseMotionListener(fruitPanelListener);
		
		setPreferredSize(new Dimension(
				FruitEditor.SCREEN_WIDTH, 
				FruitEditor.SCREEN_HEIGHT));
	}
	
	private void setupPanels() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		//mapPanel = new MapPanel(mapFile);
		//tilePanel = new TilePanel();
		//mapListPanel = new MapListPanel();
	
		//tileScrollPane = new JScrollPane(tilePanel);
		//tileTabbedPane = new JTabbedPane();
		//mapScrollPane = new JScrollPane(mapPanel);
		
		//mapPanel.setViewport(mapScrollPane.getViewport());
	}
}