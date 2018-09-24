package cz.vabalcar.rcjbotcontroller;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

import cz.vabalcar.rcjbotcontroller.visualizer.Arc;
import cz.vabalcar.rcjbotcontroller.visualizer.Circle;
import cz.vabalcar.rcjbotcontroller.visualizer.Graphics;
import cz.vabalcar.rcjbotcontroller.visualizer.IntColor;
import cz.vabalcar.rcjbotcontroller.visualizer.Rectangle;

public class SwingGraphics implements Graphics {
	
	private final Graphics2D graphics;
	
	public SwingGraphics(Graphics2D graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		this.graphics = graphics;
	}

	@Override
	public IntColor getColor() {
		return new IntColor(graphics.getColor().getRGB());
	}

	@Override
	public void setColor(IntColor color) {
		graphics.setColor(new java.awt.Color(color.getColor()));
	}

	@Override
	public void fill(Rectangle rectangle) {
		graphics.fillRect((int) rectangle.getX(), (int) rectangle.getY(), (int) rectangle.getWidth(), (int) rectangle.getHeight());
	}

	@Override
	public void fill(Arc arc) {
		Rectangle arcBounds = arc.getBounds();
		graphics.fillArc((int) arcBounds.getX(), (int) arcBounds.getY(), (int) arcBounds.getWidth(), (int) arcBounds.getHeight(), (int) arc.getStart(), (int) arc.getExtent());
	}

	@Override
	public void fill(Circle circle) {
		int x = (int) (circle.getCenter().getX() - circle.getRadius());
		int y = (int) (circle.getCenter().getY() - circle.getRadius());
		int r = (int) circle.getRadius();
		int d = r + r;
		graphics.fillRoundRect(x, y, d, d, d, d);
	}
	
}
