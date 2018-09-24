package cz.vabalcar.android.rcjbotandroidcontroller;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import cz.vabalcar.android.rcjbotcontrollercommon.Preference;
import cz.vabalcar.android.rcjbotcontrollercommon.RCJBotControllerActivity;
import cz.vabalcar.android.rcjbotcontrollercommon.RemoteJBotInfo;

/**
 * An activity that provides a way to connect to JBot with a given name via a given port.
 */
public class RCJBotConnectingActivity extends RCJBotControllerActivity
        implements ConnectingDialog.CancelButtonListener {

    /**
     * A connect button
     */
    private Button connectButton;

    /**
     * Shown ConnectingDialog
     */
    private ConnectingDialog connectingDialog;

    /**
     * Local SharedPreferences's accessor (set later).
     */
    private SharedPreferences preferences;

    /**
     * Stored last used name when connecting to a JBot has been requested.
     */
    private String jBotName;

    /**
     * Stored last used port when connecting to a JBot has been requested.
     */
    private String jBotPort;

    /**
     * Standard Activity's onCreate implementation
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setDrivingMethod(false);
        LayoutInflater layoutInflater = getLayoutInflater();
        setChildContent(layoutInflater.inflate(R.layout.activity_rcjbot_connecting, null));
        setToolbarTitle(getResources().getString(R.string.app_name));
        super.onCreate(savedInstanceState);

        final EditText etName = findViewById(R.id.etName);
        final EditText etPort = findViewById(R.id.etPort);

        preferences = getPreferences(MODE_PRIVATE);

        etName.setText(preferences.getString(Preference.LAST_JBOT_NAME, ""));
        etPort.setText(preferences.getString(Preference.LAST_JBOT_PORT, ""));

        connectButton = findViewById(R.id.btnConnect);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jBotName = etName.getText().toString();
                jBotPort = etPort.getText().toString();

                if (jBotName.isEmpty()) {
                    showInputErrorDialog("JBot name cannot be empty!");
                    return;
                }

                if (jBotPort.isEmpty()) {
                    showInputErrorDialog("JBot port cannot be empty!");
                    return;
                }

                int jBotPortValue;
                try {
                    jBotPortValue = Integer.parseInt(jBotPort);
                } catch (NumberFormatException e) {
                    showInputErrorDialog("Invalid port number!");
                    return;
                }

                if (jBotPortValue > 65535) {
                    showInputErrorDialog("Invalid port number!");
                    return;
                }

                connectButton.setEnabled(false);
                showConnectingDialog();
                connectToJBot(jBotName, jBotPortValue);
            }
        });
    }

    /**
     * Standard Activity's onCreateOptionsMenu implementation. Extends current menu by R.menu.menu_extension menu.
     * @param menu to be extended
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        setMenuVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * An extension of RCJBotControllerActivity's onJBotConnected that stores connection information into the SharedPreferences.
     * @param connected true if a JBot is connected
     * @param remoteJBotInfo info about a connected JBot
     */
    @Override
    protected void onJBotConnected(boolean connected, RemoteJBotInfo remoteJBotInfo) {
        if (connected && preferences != null && jBotName != null && jBotPort != null) {
            SharedPreferences.Editor preferencesEditor = preferences.edit();
            preferencesEditor.putString(Preference.LAST_JBOT_NAME, jBotName);
            preferencesEditor.putString(Preference.LAST_JBOT_PORT, jBotPort);
            preferencesEditor.commit();
        }
        if (connectButton != null) {
            connectButton.setEnabled(true);
        }
        if (connectingDialog != null) {
            connectingDialog.dismiss();
            connectingDialog = null;
        }
        super.onJBotConnected(connected, remoteJBotInfo);
    }

    /**
     * Shows the InputErrorDialog.
     * @param message a message to show in the dialog
     */
    private void showInputErrorDialog(String message) {
        InputErrorDialog inputErrorDialog = new InputErrorDialog();
        inputErrorDialog.setMessage(message);
        inputErrorDialog.show(getFragmentManager(), "InputErrorDialog");
    }

    /**
     * Shows the ConnectingDialog.
     */
    private void showConnectingDialog() {
        connectingDialog = new ConnectingDialog();
        connectingDialog.show(getFragmentManager(), "ConnectingDialog");
    }

    /**
     * An implementation of CancelButtonListener's onCancelButtonPressed that cancels current connecting to a JBot.
     * @param dialog source dialog
     * @param which dialog's id
     */
    @Override
    public void onCancelButtonPressed(DialogInterface dialog, int which) {
        cancelConnectingToJBot();
        connectButton.setEnabled(true);
        dialog.dismiss();
    }
}
