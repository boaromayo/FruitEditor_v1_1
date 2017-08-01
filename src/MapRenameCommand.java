package FruitEditor;

public class MapRenameCommand implements FruitCommand {
	// MAP INSTANCE.
	private Map map;
	
	// MAP NAMES.
	private String mapOldName;
	private String mapNewName;
	
	// CHANGE EXPLANATION.
	private String change = "map rename change";
	
	public MapRenameCommand(Map m, String oldName, String newName) {
		map = m;
		mapOldName = oldName;
		mapNewName = newName;
	}
	
	@Override
	public void undo() {
		// Change map name back to old name
		if (mapOldName != null && map != null) {
			map.setName(mapOldName);
		}
	}
	
	@Override
	public void redo() {
		// Change map name back to new name
		if (mapNewName != null && map != null) {
			map.setName(mapNewName);
		}
	}

	@Override
	public String getActionString() {
		return change;
	}

}
