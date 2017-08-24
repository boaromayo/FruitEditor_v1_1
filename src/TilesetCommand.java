package FruitEditor;

public class TilesetCommand implements FruitCommand {
	// TILESET & TILES.
	private Tileset tileset;
	private Tile[][] oldtiles;
	private Tile[][] newtiles;
	
	// CHANGE EXPLANATION.
	private String change = "tileset change";
	
	public TilesetCommand(TilePanel tp, Tile[][] tiles) {
		tileset = tp.getTileset();
		oldtiles = tileset.getTiles();
		newtiles = tiles;
	}
	
	@Override
	public void undo() {
		if (oldtiles != null) {
			// TODO: Set all old tiles for tileset here.
		}
	}
	
	@Override
	public void redo() {
		if (newtiles != null) {
			// TODO: Set all new tiles for tileset here.
		}
	}
	
	@Override
	public String getActionString() {
		return change;
	}
}
