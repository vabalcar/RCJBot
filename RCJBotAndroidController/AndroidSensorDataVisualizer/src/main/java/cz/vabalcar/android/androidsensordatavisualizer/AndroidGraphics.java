package cz.vabalcar.android.androidsensordatavisualizer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import cz.vabalcar.rcjbotcontroller.visualizer.Arc;
import cz.vabalcar.rcjbotcontroller.visualizer.Circle;
import cz.vabalcar.rcjbotcontroller.visualizer.Graphics;
import cz.vabalcar.rcjbotcontroller.visualizer.IntColor;
import cz.vabalcar.rcjbotcontroller.visualizer.Rectangle;

/**
 * Implementation of Graphics for Android
 */
public class AndroidGraphics implements Graphics {

    /**
     * Stored instance of Android's canvas
     */
    private Canvas canvas;

    /**
     * Stored color for drawing
     */
    private IntColor color = IntColor.BLACK;

    /**
     * Paint used by AndroidGraphics instance to describe instructions for renderer
     */
    private final Paint fillPaint = new Paint();

    /**
     * Just a constructor
     */
    public AndroidGraphics() {
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);
    }

    /**
     * Sent canvas to draw into
     * @param canvas a canvas to use
     */
    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    /**
     * Gets stored color
     * @return stored (last used) color
     */
    @Override
    public IntColor getColor() {
        return color;
    }

    /**
     * Sets color to use for drawing (and more accurately filling).
     * @param color a color to set for drawing
     */
    @Override
    public void setColor(IntColor color) {
        this.color = color;
        fillPaint.setColor(color.getColor());
    }

    /**
     *  Draws a filled rectangle.
     * @param rectangle a rectangle to draw
     */
    @Override
    public void fill(Rectangle rectangle) {
        canvas.drawRect(getBounds(rectangle), fillPaint);
    }

    /**
     * Draw a filled arc.
     * @param arc an arc to draw
     */
    @Override
    public void fill(Arc arc) {
        canvas.drawArc(getBounds(arc.getBounds()), 360 - arc.getStart() - arc.getExtent(), arc.getExtent(), true, fillPaint);
    }

    /**
     * Draw a filled circle.
     * @param circle a circle to draw
     */
    @Override
    public void fill(Circle circle) {
        float left = circle.getCenter().getX() - circle.getRadius();
        float top = circle.getCenter().getY() - circle.getRadius();
        float right = left + circle.getRadius() * 2;
        float bottom = top + circle.getRadius() * 2;
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawRoundRect(bounds, circle.getRadius(), circle.getRadius(), fillPaint);
    }

    /**
     * Converts custom type Rectangle to Android's RectF.
     * @param rectangle a Rectangle to be converted
     * @return created RectF
     */
    private RectF getBounds(Rectangle rectangle) {
        float left = rectangle.getX();
        float top = rectangle.getY();
        float right = left + rectangle.getWidth();
        float bottom = top + rectangle.getHeight();
        return new RectF(left, top, right, bottom);
    }
}
