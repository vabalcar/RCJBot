package cz.vabalcar.android.rcjbotsimplecontrolleractivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Dialog that allows user to select motor and motor's direction which are targeted in request sent to
 * robot when btnRunMotorAction is pressed.
 */
public class MotorActionSelectDialog extends DialogFragment {

    /**
     * Listener that gets notified when user makes his decision and fills data in dialog.
     */
    public interface MotorActionSelectDialogListener {
        /**
         * Just a callback method.
         * @param dialog finished dialog
         * @param which dialog's id
         * @param motor selected motor
         * @param direction selected motor's direction
         */
        void onMotorActionSet(DialogInterface dialog, int which, String motor, String direction);
    }

    /**
     * Set instance of MotorActionSelectDialogListener to get notified when user completes his selection.
     */
    private MotorActionSelectDialogListener listener;

    /**
     * Standard DialogFragment's implementation.
     * @param savedInstanceState saved instance state
     * @return created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity parentActivity = getActivity();
        if (parentActivity instanceof MotorActionSelectDialogListener) {
            listener = (MotorActionSelectDialogListener) parentActivity;
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(parentActivity);

        View content = parentActivity.getLayoutInflater().inflate(R.layout.motor_action_select, null);

        final Spinner spinnerMotors = content.findViewById(R.id.spinner_motors);
        ArrayAdapter<CharSequence> spinnerMotorsAdapter = ArrayAdapter.createFromResource(parentActivity,
                R.array.motors_array, android.R.layout.simple_spinner_item);
        spinnerMotorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMotors.setAdapter(spinnerMotorsAdapter);

        final Spinner spinnerMotorDirections = content.findViewById(R.id.spinner_motor_directions);
        ArrayAdapter<CharSequence> spinnerMotorDirectionsAdapter = ArrayAdapter.createFromResource(parentActivity,
                R.array.motor_directions_array, android.R.layout.simple_spinner_item);
        spinnerMotorDirectionsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMotorDirections.setAdapter(spinnerMotorDirectionsAdapter);

        dialogBuilder.setTitle(R.string.motor_action_select_dialog_title)
                     .setView(content)
                     .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             if (listener != null) {
                                 listener.onMotorActionSet(dialog, which,
                                         spinnerMotors.getSelectedItem().toString(),
                                         spinnerMotorDirections.getSelectedItem().toString());
                             }
                         }
                     });

        return dialogBuilder.create();
    }
}
