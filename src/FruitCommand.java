package FruitEditor;

/**====================================
 *  Using the Command pattern, 
 *  the editor can record 'screenshots' or actions the user performs
 *  and place them in a stack to make undo/redo possible. 
 *  
 *  Note: Actions done on the map (painting, shifting, setting)
 *  and on the tileset (loading, closing) are counted.
 *=====================================*/
public interface FruitCommand {
	public abstract void undo();
	public abstract void redo();
	public abstract String getActionString();
}
