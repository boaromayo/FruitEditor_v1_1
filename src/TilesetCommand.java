package FruitEditor;

public class TilesetCommand implements FruitCommand {
	// TILE PANEL & TILESET.
	private TilePanel tilePanel;
	private Tileset tileset;
	private Tileset prevTileset;
	
	// CHANGE EXPLANATION.
	private String change = "tileset change";
	
	public TilesetCommand(TilePanel tp) {
		tilePanel = tp;
		tileset = tp.getTileset();
	}
	
	public TilesetCommand(TilePanel tp, Tileset prev) {
		tilePanel = tp;
		tileset = tp.getTileset();
		prevTileset = prev;
	}
	
	@Override
	public void undo() {
		if (tileset != null) {
			tilePanel.setTileset(tileset);
		}
	}
	
	@Override
	public void redo() {
		if (prevTileset != null) {
			tilePanel.setTileset(prevTileset);
		}
	}
	
	@Override
	public String getActionString() {
		return change;
	}
}
