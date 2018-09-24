package cz.vabalcar.android.rcjbotandroidcontroller;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.concurrent.Callable;

import cz.vabalcar.android.rcjbotcontrollercommon.MessageData;
import cz.vabalcar.android.rcjbotcontrollercommon.MessageSubject;
import cz.vabalcar.android.rcjbotcontrollercommon.Preference;
import cz.vabalcar.android.rcjbotcontrollercommon.RemoteJBotInfo;
import cz.vabalcar.jbot.RemoteJBot;
import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.events.DataListenerImpl;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.jbot.moving.UnsupportedMovementException;
import cz.vabalcar.util.FloatArray;

/**
 * Background bounded service thath provides connection with JBot.
 */
public class RCJBotControllerService extends Service {

    /**
     * An extension of standard system message queue handler
     */
    private class IncomingHandler extends Handler {
        /**
         * The extension itself
         * @param msg received message
         */
        @Override
        public void handleMessage(Message msg) {
            switch (MessageSubject.values()[msg.what]) {
                case ASSIGN:
                    outMessenger = msg.replyTo;
                    sendJBotUpdate(MessageSubject.ASSIGN);
                    break;
                case CONNECT:
                    connectToJBot(msg.getData().getString(MessageData.JBOT_NAME), msg.getData().getInt(MessageData.JBOT_PORT));
                    break;
                case DISCONNECT:
                    disconnectFromJBot();
                    break;
                case MOVEMENT:
                    Object receivedObject = msg.getData().getSerializable(MessageData.JBOT_MOVEMENT);
                    if (receivedObject != null && receivedObject instanceof Movement) {
                        Movement movement = (Movement) receivedObject;
                        try {
                            if (jBot.isConnected()) {
                                jBot.getMovementProcessor().visit(movement);
                            }
                        } catch (UnsupportedMovementException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case GET_DRIVING_METHOD:
                    sendDrivingMethodMessage();
                    break;
                case SET_DRIVING_METHOD:
                    setDrivingMethod(msg);
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }
    }

    /**
     * Stored RemoteJBot instance
     */
    private final RemoteJBot<FloatArray> jBot = new RemoteJBot<>(FloatArray.class);

    /**
     * A messenger for incoming messages
     */
    private final Messenger inMessenger = new Messenger(new IncomingHandler());

    /**
     * A messenger for outgoing messages
     */
    private Messenger outMessenger;

    /**
     * Just the SharedPreferences
     */
    private SharedPreferences preferences;

    /**
     * Just a constructor
     */
    public RCJBotControllerService() {
        jBot.addInterruptionListener(new Callable<Void>() {
            @Override
            public Void call() {
                sendJBotUpdate(MessageSubject.CONNECTION_INTERRUPTED);
                disconnectFromJBot();
                return null;
            }
        });
        jBot.addListener(new DataListenerImpl<FloatArray>(FloatArray.class) {
            @Override
            public boolean processDataEvent(DataEvent<? extends FloatArray> dataEvent) {
                sendMessage(createJBotDataEventMessage(dataEvent));
                return true;
            }

            @Override
            public void close() {
                //Nothing to do here.
            }
        });
    }

    /**
     * Standard Service's onBind implementation
     * @param intent intent received from a system
     * @return binder which can be used to bind to this service (it's managed by system and client can request access to it, but only from system)
     */
    @Override
    public IBinder onBind(Intent intent) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return inMessenger.getBinder();
    }

    /**
     * Reads driving method to be stored in SharedPreferences
     * @param msg message to read driving method from
     */
    private void setDrivingMethod(Message msg) {
        String lastDrivingMethodPackage = msg.getData().getString(Preference.LAST_DRIVING_METHOD_PACKAGE, null);
        String lastDrivingMethodClass = msg.getData().getString(Preference.LAST_DRIVING_METHOD_CLASS, null);
        if (lastDrivingMethodPackage == null || lastDrivingMethodClass == null) {
            return;
        }
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(Preference.LAST_DRIVING_METHOD_PACKAGE, lastDrivingMethodPackage);
        preferencesEditor.putString(Preference.LAST_DRIVING_METHOD_CLASS, lastDrivingMethodClass);
        preferencesEditor.commit();
    }

    /**
     * Connects to a JBot. Connecting runs on a new thread.
     * @param jBotName target JBot's name
     * @param jBotPort target JBot's port to use for connecting
     */
    private void connectToJBot(final String jBotName, final int jBotPort) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    jBot.connectTo(jBotName, jBotPort);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sendJBotUpdate(MessageSubject.CONNECT);
            }
        }).start();
    }

    /**
     * Disconnects from JBot.
     */
    private void disconnectFromJBot() {
        try {
            jBot.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sendJBotUpdate(MessageSubject.DISCONNECT);
    }

    /**
     * Sends update about JBot's status via outMessenger
     * @param messageSubject message subject which describing what's new about JBot
     */
    private void sendJBotUpdate(MessageSubject messageSubject) {
        boolean jBotConnected = jBot.isConnected();
        Message msg = createJBotUpdateMessage(messageSubject, jBotConnected, jBotConnected ? jBot : null);
        sendMessage(msg);
    }

    /**
     * Creates a message about JBot's update.
     * @param messageSubject message subject which describing what's new about JBot
     * @param connected says if the JBot is connected
     * @param remoteJBot JBot itself
     * @return the created message
     */
    private Message createJBotUpdateMessage(MessageSubject messageSubject, boolean connected, RemoteJBot<?> remoteJBot) {
        Message msg = Message.obtain(null, messageSubject.ordinal());
        Bundle b = new Bundle();
        b.putBoolean(MessageData.JBOT_CONNECTED, connected);
        if (remoteJBot != null) {
            b.putSerializable(MessageData.JBOT_INFO, new RemoteJBotInfo(remoteJBot));
        }
        msg.setData(b);
        return msg;
    }

    /**
     * Creates a driving method message.
     * @return the driving method message
     */
    private Message createDrivingMethodMessage() {
        Message msg = Message.obtain(null, MessageSubject.GET_DRIVING_METHOD.ordinal());
        String lastDrivingMethodPackage = preferences.getString(Preference.LAST_DRIVING_METHOD_PACKAGE, null);
        String lastDrivingMethodClass = preferences.getString(Preference.LAST_DRIVING_METHOD_CLASS, null);
        Bundle b = new Bundle();
        if (lastDrivingMethodPackage != null && lastDrivingMethodClass != null) {
            b.putString(MessageData.LAST_DRIVING_METHOD_PACKAGE, lastDrivingMethodPackage);
            b.putString(MessageData.LAST_DRIVING_METHOD_CLASS, lastDrivingMethodClass);
        }
        msg.setData(b);
        return msg;
    }

    /**
     * Sends a driving method message.
     */
    private void sendDrivingMethodMessage() {
        sendMessage(createDrivingMethodMessage());
    }

    /**
     * Creates JBot's data event message
     * @param dataEvent event itself
     * @return the created message
     */
    private Message createJBotDataEventMessage(DataEvent<? extends FloatArray> dataEvent) {
        Message msg = Message.obtain(null, MessageSubject.JBOT_DATA_EVENT.ordinal());
        Bundle b = new Bundle();
        b.putSerializable(MessageData.JBOT_DATA_EVENT, dataEvent);
        msg.setData(b);
        return msg;
    }

    /**
     * Sends message to the JBot via outMessenger.
     * @param msg the message
     */
    private void sendMessage(Message msg) {
        if (outMessenger == null) {
            return;
        }
        try {
            outMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
