package cz.vabalcar.android.rcjbotsimplecontrolleractivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import cz.vabalcar.android.rcjbotcontrollercommon.RCJBotControllerActivity;
import cz.vabalcar.jbot.moving.Direction;
import cz.vabalcar.jbot.moving.Locomotion;
import cz.vabalcar.jbot.moving.MotorMovement;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.Rotation;
import cz.vabalcar.jbot.moving.Stop;

/**
 * Abstract activity that represents RCJBotControllerActivity that controls motors out of chassis using just one button and motor action selector
 * (allows user to select motor and direction mapped to the button). Chassis is controlled via some kind of normalized vector provider. More info
 * about that can be found in documentation of setBtnRunMotorAction method.
 */
public abstract class RCJBotSimpleControllerActivity extends RCJBotControllerActivity
    implements MotorActionSelectDialog.MotorActionSelectDialogListener {

    /**
     * Local SharedPreferences's accessor (set later).
     */
    private SharedPreferences preferences;

    /**
     * Returns an instance of Button that has to be used to control selected motor. To this button is referenced
     * @return an instance of Button that has to be used to control selected motor.
     */
    @NonNull
    public abstract Button getBtnRunMotorAction();

    /**
     * Standard Activity's onCreate implementation
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getPreferences(Context.MODE_PRIVATE);
        String motorPreference = preferences.getString(Preference.MOTOR, getResources().getString(R.string.none));
        String directionPreference = preferences.getString(Preference.DIRECTION, getResources().getString(R.string.forward));

        setBtnRunMotorAction(getBtnRunMotorAction(), motorPreference, directionPreference);
    }

    /**
     * Standard Activity's onCreateOptionsMenu implementation. Extends current menu by R.menu.menu_extension menu.
     * @param menu to be extended
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean displayMenu = super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_extension, menu);
        return displayMenu;
    }

    /**
     * Standard Activity's onOptionsItemSelected implementation
     * @param item selected menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.action_select_motor_action) {
            MotorActionSelectDialog motorActionSelectDialog = new MotorActionSelectDialog();
            motorActionSelectDialog.show(getFragmentManager(), "MotorActionSelectDialog");
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Send movement counted from given values. These values have to be normalized, what means that it have to be values from interval [-1,1].
     * This value represents a movement along an axis. The absolute value of normalized value represents speed of the movement and its sign represents direction of the movement.
     * @param normalizedXValue normalized value representing requested speed and direction along the x axis
     * @param normalizedYValue normalized value representing requested speed and direction along the y axis
     */
    public void sendMovement(float normalizedXValue, float normalizedYValue) {
        if (normalizedXValue >= -1 && normalizedXValue <= 1
                && normalizedYValue >= -1 && normalizedYValue <= 1) {
            Movement requestedMovement;

            float normalizedXValueAbs = Math.abs(normalizedXValue);
            float normalizedYValueAbs = Math.abs(normalizedYValue);

            if (normalizedYValueAbs == 1f) {
                requestedMovement = new Rotation(normalizedYValue < 0 ? Direction.LEFT : Direction.RIGHT);
            } else if (normalizedYValue != 0 && normalizedXValue == 0) {
                requestedMovement = new Rotation(normalizedYValue < 0 ? Direction.LEFT : Direction.RIGHT, (int) (normalizedYValueAbs * 100));
            } else if (normalizedYValue == 0 && normalizedXValue == 0) {
                requestedMovement = new Stop();
            } else {
                requestedMovement = new Locomotion(normalizedXValue < 0 ? Direction.BACKWARD : Direction.FORWARD, normalizedYValue, (int) (normalizedXValueAbs * 100));
            }

            Log.d("send", requestedMovement.toString());

            sendMovement(requestedMovement);
        }
    }

    /**
     * MotorActionSelectDialogListener's onMotorActionSet implementation. It sets btnRunMotorAction due to user's request and stores that request to the SharedPreferences.
     * @param dialog source of the "motorActionSet" event
     * @param which dialog id
     * @param motor selected motor
     * @param direction selected motor's direction
     */
    @Override
    public void onMotorActionSet(DialogInterface dialog, int which, String motor, String direction) {
        setBtnRunMotorAction(getBtnRunMotorAction(), motor, direction);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(Preference.MOTOR, motor);
        preferencesEditor.putString(Preference.DIRECTION, direction);
        preferencesEditor.commit();

        dialog.dismiss();
    }

    /**
     * Sets btnRunMotorAction (due to user's request).
     * @param btnRunMotorAction button to set as btnRunMotorAction
     * @param motor motor to control with the btnRunMotorAction
     * @param direction controlled motor's direction to request when btnRunMotorAction is pressed
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setBtnRunMotorAction(Button btnRunMotorAction, String motor, String direction) {
        if (motor.equals(getResources().getString(R.string.none))) {
            btnRunMotorAction.setVisibility(View.INVISIBLE);
        } else {
            btnRunMotorAction.setVisibility(View.VISIBLE);
            btnRunMotorAction.setText(String.format("%s %s %s", getResources().getString(R.string.run_motor), motor, direction));

            final int targetMotor = Integer.parseInt(motor);
            Direction motorDirection = direction.equals(getResources().getString(R.string.forward)) ? Direction.FORWARD : Direction.BACKWARD;
            final MotorMovement motorMovement = new MotorMovement(targetMotor, new Locomotion(motorDirection));

            btnRunMotorAction.setOnTouchListener(new View.OnTouchListener() {
                private final Movement stop = new MotorMovement(targetMotor, new Stop());
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        sendMovement(motorMovement);
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        sendMovement(stop);
                    }
                    return false;
                }
            });
        }
    }
}
