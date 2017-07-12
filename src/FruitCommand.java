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
	public abstract void execute();
	public abstract void undo(); // TODO: Can I take this out? Let History class handle undo/redo actions?
	public abstract void redo(); // TODO: Can I take this out?
}
