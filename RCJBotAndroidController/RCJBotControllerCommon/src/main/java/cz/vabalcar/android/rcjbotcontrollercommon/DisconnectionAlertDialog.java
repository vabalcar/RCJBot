package cz.vabalcar.android.rcjbotcontrollercommon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

/**
 * Dialog that informs user that previously running connection with a JBot has been interrupted.
 */
public class DisconnectionAlertDialog extends DialogFragment {

    /**
     * Standard DialogFragment's implementation.
     * @param savedInstanceState saved instance state
     * @return created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View dialogView = getActivity().getLayoutInflater().inflate(R.layout.disconnection_alert_dialog, null);
        dialogBuilder
                .setTitle(R.string.disconnection_alert_dialog_title)
                .setView(dialogView)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return dialogBuilder.create();
    }
}
