package FruitEditor;

public enum DrawMode {
	PENCIL(0), 
	LINE(1),
	RECTANGLE(2), 
	CIRCLE(3), 
	FILL(4);
	
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
