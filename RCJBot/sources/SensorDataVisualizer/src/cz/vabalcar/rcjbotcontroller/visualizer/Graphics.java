package cz.vabalcar.rcjbotcontroller.visualizer;

public interface Graphics {
	IntColor getColor();
	void setColor(IntColor color);
	
	void fill(Rectangle rectangle);
	void fill(Arc arc);
	void fill(Circle circle);
}
