package FruitEditor;

import java.awt.event.*;

public class FruitPanelListener implements MouseListener, 
MouseMotionListener, KeyListener 
	{
	
	// FILES.
	private FruitEditor fruitEditor;
	private FruitPanel fruitPanel;
	
	// PANELS.
	MapPanel mapPanel;

	public FruitPanelListener(FruitEditor f) {
		fruitEditor = f;
		fruitPanel = fruitEditor.getPanel();
		
		mapPanel = getMapPanel();
	}
	
	public void mouseMoved(MouseEvent e) {
		mapPanel.mouseMoved(e);
		//tilePanel.mouseMoved(e);
	}
	
	public void mouseHovered(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		mapPanel.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		mapPanel.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		mapPanel.mouseReleased(e);
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
	public void keyTyped(KeyEvent e) {
		
	}
	
	public MapPanel getMapPanel() {
		return fruitPanel.getMapPanel();
	}
}
