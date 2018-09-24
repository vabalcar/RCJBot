package cz.vabalcar.android.rcjbotandroidcontroller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import cz.vabalcar.android.androidsensordatavisualizer.AndroidSensorDataVisualizer;
import cz.vabalcar.android.rcjbotcontrollercommon.RCJBotControllerActivity;
import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.moving.Direction;
import cz.vabalcar.jbot.moving.Locomotion;
import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.Rotation;
import cz.vabalcar.jbot.moving.Stop;
import cz.vabalcar.util.FloatArray;

/**
 * An instance of the RCJBotControllerActivity that provides the most boring driving method of the robot - driving via buttons
 */
public class RCJBotButtonControllerActivity extends RCJBotControllerActivity {

    /**
     * Local instance of a view called AndroidSensorDataVisualizer
    */
    private AndroidSensorDataVisualizer dataVisualizer;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflater layoutInflater = getLayoutInflater();
        setChildContent(layoutInflater.inflate(R.layout.activity_rcjbot_button_controller, null));
        setToolbarTitle(getResources().getString(R.string.app_name));
        super.onCreate(savedInstanceState);

        final Locomotion locomotionForward = new Locomotion(Direction.FORWARD);
        final Locomotion locomotionBackward = new Locomotion(Direction.BACKWARD);
        final Stop stop = new Stop();

        ImageButton btnFwd = findViewById(R.id.btnFwd);
        btnFwd.setOnTouchListener(createButtonListener(locomotionForward, stop));

        ImageButton btnBck = findViewById(R.id.btnBck);
        btnBck.setOnTouchListener(createButtonListener(locomotionBackward, stop));

        ImageButton btnRight = findViewById(R.id.btnRight);
        btnRight.setOnTouchListener(createButtonListener(new Rotation(Direction.RIGHT), stop));

        ImageButton btnLeft = findViewById(R.id.btnLeft);
        btnLeft.setOnTouchListener(createButtonListener(new Rotation(Direction.LEFT), stop));

        ImageButton btnM1Fwd = findViewById(R.id.btnM1Fwd);
        btnM1Fwd.setOnTouchListener(createButtonListener(new MotorMovement(1, locomotionForward), new MotorMovement(1, stop)));

        ImageButton btnM1Bck = findViewById(R.id.btnM1Bck);
        btnM1Bck.setOnTouchListener(createButtonListener(new MotorMovement(1, locomotionBackward), new MotorMovement(1, stop)));

        ImageButton btnM2Fwd = findViewById(R.id.btnM2Fwd);
        btnM2Fwd.setOnTouchListener(createButtonListener(new MotorMovement(2, locomotionForward), new MotorMovement(2, stop)));

        ImageButton btnM2Bck = findViewById(R.id.btnM2Bck);
        btnM2Bck.setOnTouchListener(createButtonListener(new MotorMovement(2, locomotionBackward), new MotorMovement(2, stop)));

        ImageButton btnM3Fwd = findViewById(R.id.btnM3Fwd);
        btnM3Fwd.setOnTouchListener(createButtonListener(new MotorMovement(3, locomotionForward), new MotorMovement(3, stop)));

        ImageButton btnM3Bck = findViewById(R.id.btnM3Bck);
        btnM3Bck.setOnTouchListener(createButtonListener(new MotorMovement(3, locomotionBackward), new MotorMovement(3, stop)));

        ImageButton btnM4Fwd = findViewById(R.id.btnM4Fwd);
        btnM4Fwd.setOnTouchListener(createButtonListener(new MotorMovement(4, locomotionForward), new MotorMovement(4, stop)));

        ImageButton btnM4Bck = findViewById(R.id.btnM4Bck);
        btnM4Bck.setOnTouchListener(createButtonListener(new MotorMovement(4, locomotionBackward), new MotorMovement(4, stop)));

        dataVisualizer = findViewById(R.id.dataVisualizer);
    }

    /**
     * RCJBotAndroidControllerActivity's onJBotDataEvent implementation. Visualizes received data received from a JBot.
     */
    @Override
    protected void onJBotDataEvent(DataEvent<? extends FloatArray> dataEvent) {
        dataVisualizer.visualize(dataEvent);
    }

    /**
     * Creates an OnTouchListener with respect to the given arguments
     * @param movement a movement to be requested when the affected button is pressed
     * @param stop the stop movement
     * @return
     */
    public View.OnTouchListener createButtonListener(final Movement movement, final Movement stop) {
        return new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    sendMovement(movement);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    sendMovement(stop);
                }
                return false;
            }
        };
    }
}
