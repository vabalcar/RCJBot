package cz.vabalcar.android.rcjbotcontrollercommon;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.app.DialogFragment;

/**
 * An yes-no dialog shown when user presses the disconnect button.
 */
public class DisconnectionYesNoDialog extends DialogFragment {

    /**
     * Listener that gets notified when user clicks on yes button.
     */
    public interface DisconnectionYesNoDialogListener {

        /**
         * Just a callback method.
         * @param dialog source dialog
         * @param which dialog's id
         */
        void onPositiveButtonClicked(DialogInterface dialog, int which);
    }

    /**
     * Standard DialogFragment's implementation.
     * @param savedInstanceState saved instance state
     * @return created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parentActivity = getActivity();
        final DisconnectionYesNoDialogListener yesButtonListener =
                (parentActivity instanceof DisconnectionYesNoDialogListener) ?
                        (DisconnectionYesNoDialogListener) parentActivity : null;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle("Disconnect the jBot?");
        dialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (yesButtonListener != null) {
                    yesButtonListener.onPositiveButtonClicked(dialog, which);
                } else {
                    dialog.dismiss();
                }
            }
        });

        return dialogBuilder.create();
    }
}
