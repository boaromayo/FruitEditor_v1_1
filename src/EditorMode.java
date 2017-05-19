package FruitEditor;

public enum EditorMode {
	MAP_MODE(0), EVENT_MODE(1);
	
	private int mode;
	
	private EditorMode(int m) {
		mode = m;
	}
	
	public int mode() {
		return mode;
	}
}
