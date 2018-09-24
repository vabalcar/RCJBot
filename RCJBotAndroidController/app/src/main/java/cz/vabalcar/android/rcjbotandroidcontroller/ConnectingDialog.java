package cz.vabalcar.android.rcjbotandroidcontroller;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * A dialog that informs user that a connecting to JBot is in progress and allows his to cancel that connecting.
 */
public class ConnectingDialog extends DialogFragment {

    /**
     * An Interface that provides way for parent activity to be informed about user's pressing cancel button.
     */
    public interface CancelButtonListener {
        /**
         * Just a callback method
         * @param dialog source dialog
         * @param which dialog's id
         */
        void onCancelButtonPressed(DialogInterface dialog, int which);
    }

    /**
     * Standard DialogFragment's implementation.
     * @param savedInstanceState saved instance state
     * @return created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity parentActivity = getActivity();

        final CancelButtonListener cancelButtonListener =
                (parentActivity instanceof CancelButtonListener) ?
                        (CancelButtonListener) parentActivity : null;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder
                .setTitle("Connecting")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (cancelButtonListener != null) {
                            cancelButtonListener.onCancelButtonPressed(dialog, which);
                        }
                    }
                });
        return dialogBuilder.create();
    }
}
