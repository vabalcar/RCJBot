package cz.vabalcar.rcjbotcontroller.visualizer;

public interface ViewContainer {
	IntColor getBackgroundColor();
	int getWidth();
	int getHeight();
	
	void repaint();
}
