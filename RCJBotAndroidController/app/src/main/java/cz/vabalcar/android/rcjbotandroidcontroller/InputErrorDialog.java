package cz.vabalcar.android.rcjbotandroidcontroller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Dialog that appears when user fail filling connection information in RCJBotConnectingActivity.
 */
public class InputErrorDialog extends DialogFragment {

    /**
     * Inner private field to  store a message to be shown to the user in the dialog.
     */
    private String message;

    /**
     * Sets message to be shown to the user in dialog.
     * @param message message to show in dialog
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Standard DialogFragment's implementation.
     * @param savedInstanceState saved instance state
     * @return created dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        View content = getActivity().getLayoutInflater().inflate(R.layout.input_error_dialog, null);
        TextView errorMessage = content.findViewById(R.id.tvInputErrorMessage);
        errorMessage.setText(message);
        dialogBuilder
                .setTitle(R.string.input_error_dialog_title)
                .setView(content)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return dialogBuilder.create();
    }
}
