package cz.vabalcar.android.rcjbotjoystickcontrol

import android.os.Bundle
import android.widget.Button
import cz.vabalcar.android.androidsensordatavisualizer.AndroidSensorDataVisualizer
import cz.vabalcar.android.rcjbotsimplecontrolleractivity.RCJBotSimpleControllerActivity
import cz.vabalcar.jbot.events.DataEvent
import cz.vabalcar.util.FloatArray

/**
 * An instance of the RCJBotSimpleControllerActivity that generates normalized vector of JBot's movement
 * from states of virtual joystick.
 */
class RCJBotJoystickControllerActivity : RCJBotSimpleControllerActivity() {

    /**
     * Local instance of a Button btnRunMotorAction
     */
    private var btnRunMotorAction: Button? = null

    /**
     * Local instance of a view called AndroidSensorDataVisualizer
     */
    private var dataVisualizer: AndroidSensorDataVisualizer? = null

    /**
     * RCJBotSimpleControllerActivity's getBtnRunMotorAction implementation.
     * @return btnRunMotorAction
     */
    override fun getBtnRunMotorAction(): Button {
        if (btnRunMotorAction == null) {
            btnRunMotorAction = findViewById(R.id.btn_run_motor_action)
        }
        return btnRunMotorAction!!
    }

    /**
     * A time of last joystick event
     */
    private var lastJoystickEventT: Long = System.nanoTime()

    /**
     * Standard Activity's onCreate implementation
     * @param savedInstanceState saved instance state
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        val layoutInflater = layoutInflater
        setChildContent(layoutInflater.inflate(R.layout.activity_rcjbot_joystick_controller, null))
        setToolbarTitle(resources.getString(R.string.app_name))
        super.onCreate(savedInstanceState)

        dataVisualizer = findViewById(R.id.dataVisualizer)

        val joystick: Joystick = findViewById(R.id.joystick)
        joystick.listener = {x, y -> run {
            var realX = y
            var realY = x
            val t = System.nanoTime()
            if ((x == 0f && y == 0f) || (t - lastJoystickEventT) / 1_000_000 >= 100) {
                if (Math.abs(realX) < 0.2) {
                    realX = 0f
                }
                if (Math.abs(realY) < 0.2) {
                    realY = 0f
                }
                lastJoystickEventT = t
                sendMovement(realX, realY)
            }
        }}
    }

    /**
     * RCJBotAndroidControllerActivity's onJBotDataEvent implementation. Visualizes received data received from a JBot.
     */
    override fun onJBotDataEvent(dataEvent: DataEvent<out FloatArray>?) {
        dataVisualizer?.visualize(dataEvent!!)
    }
}
