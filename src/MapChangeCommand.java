package FruitEditor;

public class MapChangeCommand implements FruitCommand {
	// MAP OBJECT.
	private Map map;
	private Tileset tileset;
	private int[][][] oldids;
	private int[][][] newids;
	
	// CHANGE EXPLANATION.
	private String change = "map change";
	
	public MapChangeCommand(Map m, Tileset t, int[][][] ids) {
		map = m;
		tileset = t;
		oldids = ids;
		newids = m.getMapIntArray();
	}
	
	public void undo() {
		// Use IDs to set map back to previous state
		if (map != null) {
			map.setAll(oldids,tileset);
		}
	}
	
	public void redo() {
		if (map != null) {
			map.setAll(newids,tileset);
		}
	}
	
	public String getActionString() {
		return change;
	}
}
