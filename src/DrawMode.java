package FruitEditor;

public enum DrawMode {
	PENCIL(0), 
	RECTANGLE(1), 
	CIRCLE(2), 
	FILL(3);
	
	private int mode;
	
	private DrawMode(int m) {
		mode = m;
	}
	
	public int mode() {
		return mode;
	}
	
	public boolean equals(DrawMode d) {
		return this.mode == d.mode;
	}
}
