package FruitEditor;

import java.util.*;

public class UndoManager {
	
	// UNDO & REDO STACKS.
	private Stack<FruitCommand> undoStack;
	private Stack<FruitCommand> redoStack;
	
	/* Stack sets to capture and keep track of 
	 * 'action screenshots' in the editor. */
	public FruitHistory() {
		undoStack = new Stack<FruitCommand>();
		redoStack = new Stack<FruitCommand>();
	}
	
	public void add(FruitCommand cmd) {
		undoStack.push(cmd);
		
		/* If the 'screenshot' taken is different from the top taken from redo stack,
		 * or if the user makes a different action, clear redo stack. */
		if (!redoStack.pop().equals(cmd)) {
			redoStack.clear();
		}
	}
	
	public void undo() {
		if (undoable()) {
			FruitCommand cmd = undoStack.pop();
			cmd.execute();
			redoStack.push(cmd);
		}
	}
	
	public void redo() {
		if (redoable()) {
			FruitCommand cmd = redoStack.pop();
			cmd.execute();
			undoStack.push(cmd);
		}
	}
	
	public void clear() {
		undoStack.clear();
		redoStack.clear();
	}
	
	public boolean undoable() {
		return !(undoStack.isEmpty());
	}
	
	public boolean redoable() {
		return !(redoStack.isEmpty());
	}
	
	public boolean hasChanged() {
		return !(undoStack.isEmpty()) || !(redoStack.isEmpty());
	}
}
