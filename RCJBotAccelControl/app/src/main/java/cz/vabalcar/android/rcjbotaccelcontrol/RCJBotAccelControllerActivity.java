package cz.vabalcar.android.rcjbotaccelcontrol;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

import cz.vabalcar.android.androidsensordatavisualizer.AndroidSensorDataVisualizer;
import cz.vabalcar.android.rcjbotcontrollercommon.RemoteJBotInfo;

import cz.vabalcar.android.rcjbotsimplecontrolleractivity.MotorActionSelectDialog;
import cz.vabalcar.android.rcjbotsimplecontrolleractivity.RCJBotSimpleControllerActivity;
import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.util.FloatArray;

/**
 * An instance of the RCJBotSimpleControllerActivity that generates normalized vector of JBot's movement
 * from current tilt of the device.
 */
public class RCJBotAccelControllerActivity extends RCJBotSimpleControllerActivity
        implements SensorEventListener, MotorActionSelectDialog.MotorActionSelectDialogListener {

    /**
     * Minimal threshold normalized to maximal normal value.
     */
    private static final int TILT_THRESHOLD_MAX = 35;

    /**
     * Maximal threshold normalized to minimal normal value.
     */
    private static final int TILT_THRESHOLD_MIN = 5;

    /**
     * Android's sensor manager
     */
    private SensorManager sensorManager;

    /**
     * A gravity sensor
     */
    private Sensor gravitySensor;

    /**
     * Boolean which is true when gravity sensors data are being listened
     */
    private boolean listeningGravitySensor = false;

    /**
     * Last measured tilt along x axis
     */
    private int lastTiltX;
    /**
     * Last measured tilt along y axis
     */
    private int lastTiltY;

    /**
     * Local instance of a view called AndroidSensorDataVisualizer
     */
    private AndroidSensorDataVisualizer dataVisualizer;

    /**
     * Local instance of a Button btnRunMotorAction
     */
    private Button btnRunMotorAction;

    /**
     * Local instance of a TextView that shows a tilt along x axis
     */
    private TextView tvTiltX;

    /**
     * Local instance of a TextView that shows a tilt along y axis
     */
    private TextView tvTiltY;

    /**
     * RCJBotSimpleControllerActivity's getBtnRunMotorAction implementation.
     * @return btnRunMotorAction
     */
    @Override
    @NonNull
    public Button getBtnRunMotorAction() {
        if (btnRunMotorAction == null) {
            btnRunMotorAction = findViewById(R.id.btn_run_motor_action);
        }
        return btnRunMotorAction;
    }

    /**
     * Standard Activity's onCreate implementation
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        LayoutInflater layoutInflater = getLayoutInflater();
        setChildContent(layoutInflater.inflate(R.layout.activity_rcjbot_accel_controller, null));
        setToolbarTitle(getResources().getString(R.string.app_name));
        super.onCreate(savedInstanceState);

        dataVisualizer = findViewById(R.id.dataVisualizer);
        tvTiltX = findViewById(R.id.tvTiltX);
        tvTiltY = findViewById(R.id.tvTiltY);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        }
        startListeningGravitySensor();
    }

    /**
     * Activity's onPause implementation. Pauses reading data from gravity sensor.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (listeningGravitySensor) {
            stopListeningGravitySensor();
        }
    }

    /**
     * Activity's onPause implementation. Resumes reading data from gravity sensor.
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (listeningGravitySensor) {
            startListeningGravitySensor();
        }
    }

    /**
     * RCJBotAndroidControllerActivity's onJBotUpdate extension. Starts or stops reading data from gravity sensor as necessary.
     */
    @Override
    protected void onJBotUpdate(boolean connected, RemoteJBotInfo remoteJBotInfo) {
        super.onJBotUpdate(connected, remoteJBotInfo);
        if (sensorManager != null && gravitySensor != null) {
            if (connected) {
                startListeningGravitySensor();
            } else {
                //stopListeningGravitySensor();
            }
        }
    }

    /**
     * RCJBotAndroidControllerActivity's onJBotDataEvent implementation. Visualizes received data received from a JBot.
     */
    @Override
    protected void onJBotDataEvent(DataEvent<? extends FloatArray> dataEvent) {
        dataVisualizer.visualize(dataEvent);
    }

    /**
     * SensorEventListener's onSensorChanged implementation. Reads and processes data from gravity sensor.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            int tiltX = (int) - Math.toDegrees(Math.atan2(y, z));
            int tiltY = (int) - Math.toDegrees(Math.atan2(x, z));

            if (lastTiltX == tiltX && lastTiltY == tiltY) {
                return;
            }

            lastTiltX = tiltX;
            lastTiltY = tiltY;

            sendMovement(normalize(tiltX), normalize(tiltY));

            tvTiltX.setText(String.format(Locale.getDefault(), "%s%d°", getResources().getString(R.string.tilt_x), tiltX));
            tvTiltY.setText(String.format(Locale.getDefault(), "%s%d°", getResources().getString(R.string.tilt_y), tiltY));
        }
    }

    /**
     * SensorEventListener's onAccuracyChanged implementation. Nothing happens here.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing to do here.
    }

    /**
     * Normalizes a given angle. Value of the angle is transformed to the interval [-1,1]
     * @param tilt an angle in degrees to be normalized
     * @return value from [-1,1] that represents normalized value of the angle
     */
    private float normalize(int tilt) {
        int absTilt = Math.abs(tilt);

        if (absTilt < TILT_THRESHOLD_MIN) {
            return 0;
        }

        if (absTilt > TILT_THRESHOLD_MAX) {
            return Math.signum(tilt);
        }

        return tilt / (float) TILT_THRESHOLD_MAX;
    }

    /**
     * Starts reading data from the gravity sensor.
     */
    private void startListeningGravitySensor() {
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        listeningGravitySensor = true;
    }

    /**
     * Stops reading data from the gravity sensor.
     */
    private void stopListeningGravitySensor() {
        listeningGravitySensor = false;
        sensorManager.unregisterListener(this);
    }
}
