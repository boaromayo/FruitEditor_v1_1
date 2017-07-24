package FruitEditor;

public class TilesetCommand implements FruitCommand {
	// TILE PANEL & TILESET.
	private TilePanel tilePanel;
	private Tileset tileset;
	
	// CHANGE EXPLANATION.
	private String change;
	
	public TilesetCommand(TilePanel tp) {
		tilePanel = tp;
		tileset = tp.getTileset();
		change = "tileset change";
	}
	
	public TilesetCommand(TilePanel tp, String chg) {
		tilePanel = tp;
		tileset = tp.getTileset();
		change = chg;
	}
	
	public void execute() {
		if (tileset != null) {
			tilePanel.setTileset(tileset);
		}
	}
	
	public String getActionString() {
		return change;
	}
}
