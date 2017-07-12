package FruitEditor;

public interface FruitCommand {

	public abstract void execute();
	public abstract void undo();
	public abstract void redo();
	
}
