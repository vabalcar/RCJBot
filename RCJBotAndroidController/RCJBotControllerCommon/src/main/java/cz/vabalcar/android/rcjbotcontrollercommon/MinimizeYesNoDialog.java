package cz.vabalcar.android.rcjbotcontrollercommon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * An yes-no dialog shown when user presses device's back button.
 */
public class MinimizeYesNoDialog extends DialogFragment {

    /**
     * Standard DialogFragment's implementation.
     * @param savedInstanceState saved instance state
     * @return created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder
                .setTitle(R.string.minimize)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().moveTaskToBack(true);
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return dialogBuilder.create();
    }
}
