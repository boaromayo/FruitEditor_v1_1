package FruitEditor;

public class MapResizeCommand implements FruitCommand {
	// MAP AND PANEL.
	private MapPanel mapPanel;
	private Map map;
	
	// WIDTH AND HEIGHT.
	private int oldwidth;
	private int oldheight;
	private int newwidth;
	private int newheight;
	
	// CHANGE EXPLANATION.
	private String change = "map resize change";
	
	public MapResizeCommand(MapPanel mp, Map m, int w, int h) {
		mapPanel = mp;
		map = m;
		oldwidth = map.getWidth();
		oldheight = map.getHeight();
		newwidth = w;
		newheight = h;
	}
	
	@Override
	public void undo() {
		if (mapPanel != null && map != null) {
			mapPanel.setMapSize(oldwidth,oldheight);
			map.resize(oldwidth,oldheight);
		}
	}

	@Override
	public void redo() {
		if (mapPanel != null && map != null) {
			mapPanel.setMapSize(newwidth,newheight);
			map.resize(newwidth,newheight);
		}
	}

	@Override
	public String getActionString() {
		return change;
	}

}
