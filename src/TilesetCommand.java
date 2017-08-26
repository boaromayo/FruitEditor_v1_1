package FruitEditor;

public class TilesetCommand implements FruitCommand {
	// TILESETS & TILE PANEL.
	private TilePanel tilePanel;
	private Tileset oldtileset;
	private Tileset newtileset;
	
	// CHANGE EXPLANATION.
	private String change = "tileset change";
	
	public TilesetCommand(TilePanel tp, Tileset tiles) {
		tilePanel = tp;
		oldtileset = tp.getTileset();
		newtileset = tiles;
	}
	
	@Override
	public void undo() {
		if (oldtileset != null) {
			tilePanel.setTileset(oldtileset,false);
		}
	}
	
	@Override
	public void redo() {
		if (newtileset != null) {
			tilePanel.setTileset(newtileset,false);
		}
	}
	
	@Override
	public String getActionString() {
		return change;
	}
}
