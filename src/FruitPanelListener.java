package FruitEditor;

import java.awt.event.*;

public class FruitPanelListener implements MouseListener, MouseMotionListener, KeyListener 
	{
	
	// FILES.
	private FruitEditor fruitEditor;
	private FruitPanel fruitPanel;
	
	// MOUSE COORDS.
	private int mx, my;

	public FruitPanelListener(FruitEditor f) {
		fruitEditor = f;
		fruitPanel = f.getPanel();
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void mouseHovered(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		MapPanel mp = getMapPanel();
		
		mp.mouseClicked(e);
	}

	public void mousePressed(MouseEvent e) {
		MapPanel mp = getMapPanel();
		
		mp.mousePressed(e);
	}

	public void mouseReleased(MouseEvent e) {
		MapPanel mp = getMapPanel();
		
		mp.mouseReleased(e);
		
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

	public int mouseX() { return mx; }
	
	public int mouseY() { return my; }
	
	public MapPanel getMapPanel() {
		return fruitPanel.getMapPanel();
	}
}
