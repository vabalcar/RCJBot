package cz.vabalcar.android.androidsensordatavisualizer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.rcjbotcontroller.visualizer.IntColor;
import cz.vabalcar.rcjbotcontroller.visualizer.SensorDataVisualizer;
import cz.vabalcar.rcjbotcontroller.visualizer.ViewContainer;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.SensorDataProcessor;
import cz.vabalcar.rcjbotcontroller.visualizer.processing.ev3.EV3SensorDataProcessor;
import cz.vabalcar.util.FloatArray;

/**
 * A SensorDataVisualizer for Android
 */
public class AndroidSensorDataVisualizer extends View implements ViewContainer {

    /**
     * An instance of general, portable version of sensor data visualizer
     */
    private final SensorDataVisualizer visualizer = new SensorDataVisualizer(this);
    /**
     * An instance of SensorDataProcessor which processes data from JBot and visualises them to visualizer
     */
    private final SensorDataProcessor sensorDataProcessor = new EV3SensorDataProcessor(visualizer, !isInEditMode());

    /**
     * An instance of Graphics for Android
     */
    private final AndroidGraphics graphics = new AndroidGraphics();

    /**
     * Current version of measures done during measure phase.
     */
    private int measureVersion = Integer.MIN_VALUE;

    /**
     * Last version of measures done during measure phase.
     */
    private int lastMeasureVersion = measureVersion;

    /**
     * Standard View's constructor
     * @param context
     */
    public AndroidSensorDataVisualizer(Context context) {
        super(context);
    }

    /**
     * Standard View's constructor
     * @param context
     * @param attrs
     */
    public AndroidSensorDataVisualizer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Standard View's constructor
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public AndroidSensorDataVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * An implementation of ViewContainer's getBackgroundColor
     * @return background color of AndroidSensorDataVisualizer
     */
    @Override
    public IntColor getBackgroundColor() {
        ColorDrawable background = (ColorDrawable) getBackground();
        return background == null ? IntColor.WHITE : new IntColor(background.getColor());
    }

    /**
     * An implementation of ViewContainer's repaint
     */
    @Override
    public void repaint() {
        invalidate();
    }

    /**
     * Standard View's onDraw implementation
     * @param canvas the canvas to draw into
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (lastMeasureVersion != measureVersion) {
            visualizer.updateModel();
            lastMeasureVersion = measureVersion;
        }
        graphics.setCanvas(canvas);
        visualizer.paint(graphics);
    }

    /**
     * Standard View's onMeasure implementation
     * @param widthMeasureSpec width measure specs
     * @param heightMeasureSpec height measure specs
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
        measureVersion++;
    }

    /**
     * Via this method is the AndroidSensorDataVisualizer notified about received data from JBot which has to be visualized.
     * @param dataEvent
     */
    public void visualize(DataEvent<? extends FloatArray> dataEvent) {
        sensorDataProcessor.process(dataEvent.getSourceName(), dataEvent.getData().getArray());
    }
}
