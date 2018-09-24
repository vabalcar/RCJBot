package cz.vabalcar.rcjbotcontroller;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import cz.vabalcar.rcjbotcontroller.visualizer.IntColor;
import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.visualizer.ViewContainer;

public class SwingSensorDataVisualizer extends JPanel implements ViewContainer {

	private static final long serialVersionUID = 1L;
	
	private final SensorDataVisualizer visualizer = new SensorDataVisualizer(this);
	
	public SensorDataVisualizer getVisualizer() {
		return visualizer;
	}

	@Override
	public IntColor getBackgroundColor() {
		return new IntColor(getBackground().getRGB());
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		visualizer.updateModel();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		visualizer.paint(new SwingGraphics((Graphics2D) g));
	}

}
