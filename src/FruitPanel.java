package FruitEditor;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;

public class FruitPanel extends JPanel {
	
	// PANELS.
	//MapPanel mapPanel;
	//TilePanel tilePanel;
	//MapListPanel mapListPanel;
	
	// PANEL PANES.
	//JScrollPane tileScrollPane;
	//JTabbedPane tileTabbedPane;
	//JScrollPane mapScrollPane;
	
	// MAIN PANELS.
	JPanel leftPanel;
	JPanel rightPanel;
	
	public FruitPanel() {
		setLayout(new BorderLayout());
		
		setupPanels();
		
		//leftPanel.setLayout(new FlowLayout());
		//rightPanel.setLayout(new BorderLayout());
		
		//leftPanel.add(tilePanel);
		//leftPanel.add(mapListPanel);
		//rightPanel.add(mapPanel);
		
		add(leftPanel, BorderLayout.WEST);
		add(rightPanel, BorderLayout.CENTER);
		
		setPreferredSize(new Dimension(840, 600));
	}
	
	private void setupPanels() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		//mapPanel = new MapPanel();
		//tilePanel = new TilePanel();
		//mapListPanel = new MapListPanel();
	
		//tileScrollPane = new JScrollPane(tilePanel);
		//tileTabbedPane = new JTabbedPane();
		//mapScrollPane = new JScrollPane(mapPanel);
		
		//mapPanel.setViewport(mapScrollPane.getViewport());
	}
}
