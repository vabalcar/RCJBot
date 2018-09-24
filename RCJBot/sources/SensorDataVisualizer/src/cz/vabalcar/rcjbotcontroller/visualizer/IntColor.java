package cz.vabalcar.rcjbotcontroller.visualizer;

public class IntColor {
	public static final IntColor WHITE = new IntColor(255, 255, 255);
	public static final IntColor LIGHT_GRAY = new IntColor(192, 192, 192);
	public static final IntColor GRAY = new IntColor(128, 128, 128);
	public static final IntColor DARK_GRAY = new IntColor(64, 64, 64);
	public static final IntColor BLACK = new IntColor(0, 0, 0);
	public static final IntColor RED = new IntColor(255, 0, 0);
	public static final IntColor PINK = new IntColor(255, 175, 175);
	public static final IntColor ORANGE = new IntColor(255, 200, 0);
	public static final IntColor YELLOW = new IntColor(255, 255, 0);
	public static final IntColor GREEN = new IntColor(0, 255, 0);
	public static final IntColor MAGENTA = new IntColor(255, 0, 255);
	public static final IntColor CYAN = new IntColor(0, 255, 255);
	public static final IntColor BLUE = new IntColor(0, 0, 255);
	
	private int color;
	
	public IntColor(int color) {
		this.color = color;
	}
	
	public IntColor(int a, int r, int g, int b) {
		this((a & 0xff) << 24 | (r & 0xff) << 16 | (g & 0xff) << 8 | (b & 0xff));
	}
	
	public IntColor(int r, int g, int b) {
		this(255, r, g, b);
	}
	
	public int getColor() {
		return color;
	}
}
