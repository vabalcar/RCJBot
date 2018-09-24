package cz.vabalcar.android.rcjbotjoystickcontrol

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import cz.vabalcar.android.androidsensordatavisualizer.AndroidGraphics
import cz.vabalcar.rcjbotcontroller.visualizer.Circle
import cz.vabalcar.rcjbotcontroller.visualizer.IntColor
import cz.vabalcar.rcjbotcontroller.visualizer.Point
import java.lang.IllegalArgumentException
import java.util.*

/**
 * Virtual joystick form Android's GUI
 */
class Joystick(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    /**
     *  Stick of the joystick
     */
    private inner class Stick {

        /**
         * Animation of the stick
         */
        private inner class Animation(private val startVelocity: Float, private val acceleration: Float, private val fps: Long) {

            /**
             * A timer used by the animation.
             */
            private var timer : Timer? = null

            /**
             * Current velocity of the animation
             */
            private var velocity = 0f

            /**
             * x coordination value of the current target point of the animation
             */
            private var targetX = 0f

            /**
             * y coordination value of the current target point of the animation
             */
            private var targetY = 0f

            /**
             * Starts the animation from current position to the target position
             */
            fun start(target: Point) = start(target.x, target.y)

            /**
             * Starts the animation from current position to the target position
             */
            fun start(targetX: Float, targetY: Float) {
                this.targetX = targetX
                this.targetY = targetY
                stop()
                timer?.scheduleAtFixedRate(object: TimerTask() {
                    var lastTime = System.nanoTime()
                    override fun run() {
                        val currentTime = System.nanoTime()
                        if (updatePosition((currentTime - lastTime).toFloat() / 1000000000)) {
                            timer?.cancel()
                        }
                    }
                }, 0, fps)
            }

            /**
             * Updates position of the stick in the stick space. It simulates a step of the animation.
             */
            private fun updatePosition(dt: Float): Boolean {
                velocity += acceleration * dt
                var animationCompleted = false

                if (targetX < model.center.x) {
                    model.center.x += velocity * dt

                    if (model.center.x >= targetX) {
                        animationCompleted = true
                    }

                } else {
                    model.center.x -= velocity * dt

                    if (model.center.x <= targetX) {
                        animationCompleted = true
                    }
                }

                if (targetY < model.center.y) {
                    model.center.y += velocity * dt

                    if (model.center.y >= targetY) {
                        animationCompleted = true
                    }

                } else {
                    model.center.y -= velocity * dt

                    if (model.center.y <= targetY) {
                        animationCompleted = true
                    }
                }

                if (animationCompleted) {
                    model.center.x = targetX
                    model.center.y = targetY
                    velocity = 0f
                }

                this@Joystick.postInvalidate()
                return animationCompleted
            }

            /**
             * Stops the animation.
             */
            fun stop() {
                timer?.cancel()
                timer = Timer()
                velocity = startVelocity
            }
        }

        /**
         * Model of the stick.
         */
        var model = Circle(Point(), 0f)

        /**
         * Stick's animation
         */
        private val animation = Animation(0f, 2f, 25)

        /**
         * Returns the stick to the center of the stick space using animation.
         */
        fun returnToCenter() = animation.start(stickSpace.center)
    }

    /**
     * The AndroidGraphics used to draw the joystick
     */
    private val graphics = AndroidGraphics()

    /**
     * A model of the stick space
     */
    private val stickSpace = Circle(Point(), 0f)

    /**
     * The stick of the joystick
     */
    private val stick = Stick()

    /**
     * Current version of measures done during measure phase.
     */
    private var measureVersion = Int.MIN_VALUE

    /**
     * Last version of measures done during measure phase.
     */
    private var lastMeasureVersion = measureVersion

    /**
     * Color of the stick space
     */
    private val stickSpaceColor = IntColor(0, 115, 230)

    /**
     * Color of the stick
     */
    private val stickColor = IntColor(128, 191, 255)

    /**
     * Joystick changes listener
     */
    var listener: ((Float, Float) -> Unit)? = null

    /**
     * Standard View's onMeasure implementation
     * @param widthMeasureSpec width measure specs
     * @param heightMeasureSpec height measure specs
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(width, height)
        measureVersion++
    }

    /**
     * Updates joystick's model to make the joystick fit a new measurement results.
     */
    private fun updateModel() {
        stickSpace.center.x = (width * 0.5).toFloat()
        stickSpace.center.y = (height * 0.5).toFloat()
        stickSpace.radius = (Math.min(width, height) * 0.335).toFloat()

        stick.model.center.x = stickSpace.center.x
        stick.model.center.y = stickSpace.center.y
        stick.model.radius = (stickSpace.radius * 0.4).toFloat()
    }

    /**
     * Standard View's onDraw implementation
     * @param canvas the canvas to draw into
     */
    override fun onDraw(canvas: Canvas?) {
        if (measureVersion != lastMeasureVersion) {
            updateModel()
            lastMeasureVersion = measureVersion
        }
        graphics.setCanvas(canvas)
        graphics.color = stickSpaceColor
        graphics.fill(stickSpace)
        graphics.color = stickColor
        graphics.fill(stick.model)

        listener?.invoke(normalize(1, stick.model.center.x, stickSpace.center.x), normalize(-1, stick.model.center.y, stickSpace.center.y))
    }

    /**
     * Normalizes the given value.
     */
    private fun normalize(sign: Int, value: Float, reference: Float): Float {
        val dist = value - reference
        return if (dist == 0f) 0f else sign * (dist / stickSpace.radius)
    }

    /**
     * View's onTouchEvent implementation. It moves stick due to user's interaction.
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchedPoint = Point(event!!.x, event.y)
        var newPoint: Point? = null
        when {
            event.action == MotionEvent.ACTION_UP -> stick.returnToCenter()
            distance(touchedPoint, stickSpace.center) <= stickSpace.radius -> newPoint = touchedPoint
            else -> newPoint = pointAtOrientedSegmentLine(stickSpace.center, touchedPoint, stickSpace.radius)
        }
        if (newPoint != null) {
            stick.model.center.x = newPoint.x
            stick.model.center.y = newPoint.y
            invalidate()
        }
        return true
    }

    /**
     * Counts the distance between given two points.
     */
    private fun distance(p1: Point, p2: Point): Float {
        val dx = Math.abs(p1.x - p2.x)
        val dy = Math.abs(p1.y - p2.y)
        return Math.sqrt((dx * dx + dy * dy).toDouble()).toFloat()
    }

    /**
     * Counts the point at segment line described by two points which lies in the given distance from the start of the segment line.
     */
    private fun pointAtOrientedSegmentLine(start: Point, end: Point, dist: Float) : Point {
        val length = distance(start, end)
        if (dist > length) {
            throw IllegalArgumentException("$dist > $length")
        }
        val relativeD = dist /length
        val vector = moveToOrigin(start, end)
        vector.x *= relativeD
        vector.y *= relativeD
        return Point(vector.x + start.x, vector.y + start.y)
    }

    /**
     * Counts the point retrieved from vector created by moving a segment line described by two points to the origin.
     */
    private fun moveToOrigin(start: Point, end: Point) = Point(end.x - start.x, end.y - start.y)

}