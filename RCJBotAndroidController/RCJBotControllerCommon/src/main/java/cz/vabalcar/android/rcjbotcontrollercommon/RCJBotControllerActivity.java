package cz.vabalcar.android.rcjbotcontrollercommon;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cz.vabalcar.jbot.events.DataEvent;
import cz.vabalcar.jbot.moving.Movement;
import cz.vabalcar.util.FloatArray;

/**
 * An abstract activity that provides everything necessary (well, not everything) for easy implementation of custom JBot's controllers method,
 * e.g. drawer menu that loads all installed methods, connecting button in toolbar etc.
 */
public abstract class RCJBotControllerActivity extends AppCompatActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
                    DisconnectionYesNoDialog.DisconnectionYesNoDialogListener {

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
            if (!active) {
                return;
            }
            switch (MessageSubject.values()[msg.what]) {
                case ASSIGN:
                    if (drivingMethod) {
                        setAsLastDrivingMethod();
                    }
                    onJBotUpdate(
                            msg.getData().getBoolean(MessageData.JBOT_CONNECTED, false),
                            getRemoteJBotInfo(msg));
                    break;
                case CONNECT:
                    onJBotConnected(
                            msg.getData().getBoolean(MessageData.JBOT_CONNECTED, false),
                            getRemoteJBotInfo(msg));
                    break;
                case DISCONNECT:
                    onJBotDisconnected(
                            msg.getData().getBoolean(MessageData.JBOT_CONNECTED, false),
                            getRemoteJBotInfo(msg));
                    break;
                case CONNECTION_INTERRUPTED:
                    onJBotConnectionInterrupted();
                    break;
                case GET_DRIVING_METHOD:
                    startDrivingMethod(msg);
                    break;
                case JBOT_DATA_EVENT:
                    onJBotDataEvent(getJBotDataEvent(msg));
                    break;
                default:
                    super.handleMessage(msg);
                    break;
            }
        }

        /**
         * Reads JBot's info from a message if the message contains it.
         * @param msg message to read from
         * @return the JBot's info
         */
        private RemoteJBotInfo getRemoteJBotInfo(Message msg) {
            Object deserializedJBotInfo = msg.getData().getSerializable(MessageData.JBOT_INFO);
            RemoteJBotInfo info = null;
            if (deserializedJBotInfo != null && deserializedJBotInfo instanceof RemoteJBotInfo) {
                info = (RemoteJBotInfo) deserializedJBotInfo;
            }
            return info;
        }

        /**
         * Reads JBot's data event from a message if the message contains it.
         * @param msg message to read from
         * @return the JBot's data event
         */
        @SuppressWarnings("unchecked")
        private DataEvent<? extends FloatArray> getJBotDataEvent(Message msg) {
            Object deserializedJBotDataEvent = msg.getData().getSerializable(MessageData.JBOT_DATA_EVENT);
            DataEvent<? extends FloatArray> dataEvent = null;
            if (deserializedJBotDataEvent != null) {
                dataEvent = (DataEvent<? extends FloatArray>) deserializedJBotDataEvent;
            }
            return dataEvent;
        }
    }

    /**
     * Main layout that contains activity content and the drawer menu
     */
    private DrawerLayout drawer;

    /**
     * True if a JBot is connected
     */
    private boolean connected = false;

    /**
     * True if the activity is active (in foreground)
     */
    private boolean active = false;

    /**
     * A messenger for incoming messages
     */
    private final Messenger inMessenger = new Messenger(new IncomingHandler());

    /**
     * A messenger for outgoing messages
     */
    private Messenger outMessenger;

    /**
     * A connect/disconnect button from the toolbar
     */
    private MenuItem miConnectionChange;

    /**
     * Android's standard ServiceConnection implementation
     */
    private final ServiceConnection connection = new ServiceConnection() {
        /**
         * Android's standard ServiceConnection's onServiceConnected implementation
         * @param name component name
         * @param service service's binder
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            outMessenger = new Messenger(service);
            serviceBound = true;
            assignToService();
        }

        /**
         * Android's standard ServiceConnection's onServiceDisconnected implementation
         * @param name component name
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
            outMessenger = null;
        }
    };

    /**
     * Says hi to bound service.
     */
    private void assignToService() {
        if (!serviceBound) {
            return;
        }
        Message msg = Message.obtain(null, MessageSubject.ASSIGN.ordinal());
        msg.replyTo = inMessenger;
        try {
            outMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * True if a service is bound.
     */
    private boolean serviceBound = false;

    /**
     * Plugin's activity content
     */
    private View childContent;

    /**
     * Background color of the toolbar
     */
    private int toolbarBackgroundColor;
    private boolean toolbarBackgroundColorSet = false;
    private int toolbarTitleColor = Color.WHITE;
    private String toolbarTitle = "Title goes there";
    private boolean menuVisible = true;
    private boolean drivingMethod = true;


    /**
     * Sets child content.
     * @param childContent plugin's activity main content's root view
     */
    protected void setChildContent(View childContent) {
        this.childContent = childContent;
    }

    /**
     * Sets background color of the toolbar.
     * @param toolbarBackgroundColor toolbar's background color
     */
    protected void setToolbarBackgroundColor(@ColorInt int toolbarBackgroundColor) {
        this.toolbarBackgroundColor = toolbarBackgroundColor;
        if (!toolbarBackgroundColorSet) {
            toolbarBackgroundColorSet = true;
        }
    }

    /**
     * Sets toolbar title's color.
     * @param toolbarTitleColor toolbar's title color
     */
    protected void setToolbarTitleColor(@ColorInt int toolbarTitleColor) {
        this.toolbarTitleColor = toolbarTitleColor;
    }

    /**
     * Sets toolbar title.
     * @param toolbarTitle toolbar's title
     */
    protected void setToolbarTitle(String toolbarTitle) {
        this.toolbarTitle = toolbarTitle;
    }

    /**
     * Sets if options menu is visible.
     * @param menuVisible true if menu has to be visible, false otherwise
     */
    protected void setMenuVisible(boolean menuVisible) {
        this.menuVisible = menuVisible;
    }

    /**
     * Sets if the activity is implementation of a driving method.
     * @param drivingMethod true if the activity is implementation of a driving method, false otherwise
     */
    protected void setDrivingMethod(boolean drivingMethod) {
        this.drivingMethod = drivingMethod;
    }

    /**
     * Standard Activity's onCreate implementation
     * @param savedInstanceState saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeGUI();
        bindService();
    }

    /**
     * An OnNavigationItemSelectedListener's onNavigationItemSelected implementation
     * @param item selected drawer menu item
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    /**
     * Standard Activity's onCreateOptionsMenu implementation. Sets menu to R.menu.action_connection_change menu.
     * @param menu to be set
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menuVisible) {
            getMenuInflater().inflate(R.menu.menu, menu);
            miConnectionChange = menu.findItem(R.id.action_connection_change);
            updateConnectMenuItem(miConnectionChange);
        }
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Standard Activity's onOptionsItemSelected implementation
     * @param item selected menu item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.action_connection_change) {
            if (connected) {
                showDisconnectionYesNoDialog();
            } else {
                startConnectingActivity();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Activity's onDestroy implementation. Unbinds the activity from the service.
     */
    @Override
    protected void onDestroy() {
        unbindService();
        super.onDestroy();
    }

    /**
     * Activity's onStart implementation. Assign the activity to the service.
     */
    @Override
    protected void onStart() {
        super.onStart();
        assignToService();
    }

    /**
     * Activity's onResume implementation. Nothing special happens here, just maintenance of consistency.
     */
    @Override
    protected void onResume() {
        super.onResume();
        active = true;
    }

    /**
     * Activity's onPause implementation. Nothing special happens here, just maintenance of consistency.
     */
    @Override
    protected void onPause() {
        active = false;
        super.onPause();
    }

    /**
     * Activity's onBackPressed implementation. Shows a MinimizeYesNoDialog.
     */
    @Override
    public void onBackPressed() {
        MinimizeYesNoDialog minimizeYesNoDialog = new MinimizeYesNoDialog();
        minimizeYesNoDialog.show(getFragmentManager(), "MinimizeYesNoDialog");
    }

    /**
     * Initializes GUI.
     */
    private void initializeGUI() {
        drawer = new DrawerLayout(this);
        DrawerLayout.LayoutParams drawerLayoutParams = new DrawerLayout.LayoutParams(
                DrawerLayout.LayoutParams.MATCH_PARENT,
                DrawerLayout.LayoutParams.MATCH_PARENT,
                GravityCompat.START);

        NavigationView drawerPane = new NavigationView(this);
        drawerPane.setNavigationItemSelectedListener(this);
        drawerPane.inflateHeaderView(R.layout.drawer_header);
        drawerPane.inflateMenu(R.menu.drawer_menu);
        MenuItem drivingMethodsMenuItem = drawerPane.getMenu().findItem(R.id.drivingMethodsMenu);
        initializeDrawerMenu(drivingMethodsMenuItem.getSubMenu());

        Toolbar toolbar = new Toolbar(this);
        setSupportActionBar(toolbar);
        Toolbar.LayoutParams toolbarLayoutParams = new Toolbar.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT,
                Toolbar.LayoutParams.WRAP_CONTENT);

        if (!toolbarBackgroundColorSet) {
            toolbarBackgroundColor = getResources().getColor(R.color.colorPrimaryDark);
        }

        toolbar.setBackgroundColor(toolbarBackgroundColor);
        toolbar.setTitle(toolbarTitle);
        toolbar.setTitleTextColor(toolbarTitleColor);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams contentLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        content.addView(toolbar, toolbarLayoutParams);
        if (childContent == null) {
            childContent = getLayoutInflater().inflate(R.layout.missing_activity, null);
        }
        content.addView(childContent, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        drawer.addView(content, contentLayoutParams);
        drawer.addView(drawerPane, new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, GravityCompat.START));

        setContentView(drawer, drawerLayoutParams);
    }

    /**
     * Initializes drawer menu.
     * @param menu a menu to be initialized
     */
    private void initializeDrawerMenu(Menu menu) {
        Intent i = new Intent("cz.vabalcar.android.rcjbotandroidcontroller.PLUGIN");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> result = pm.queryIntentActivities(i,0);
        String currentActivityClass = getClass().getName();
        for (ResolveInfo info : result) {
            MenuItem addedItem = menu.add(info.loadLabel(pm));
            addedItem.setIcon(info.loadIcon(pm));
            if (!info.activityInfo.name.equals(currentActivityClass)) {
                Intent startPluginActivity = new Intent();
                startPluginActivity.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                addedItem.setIntent(startPluginActivity);
            }
        }
    }

    /**
     * Binds the RCJBotControllerService.
     */
    private void bindService() {
        if (!serviceBound) {
            Intent i = new Intent();
            i.setComponent(new ComponentName("cz.vabalcar.android.rcjbotandroidcontroller", "cz.vabalcar.android.rcjbotandroidcontroller.RCJBotControllerService"));
            if (!bindService(i, connection, Context.BIND_AUTO_CREATE)) {
                Toast.makeText(this, "service not bound", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Unbinds the RCJBotControllerService.
     */
    private void unbindService() {
        if (serviceBound) {
            unbindService(connection);
            serviceBound = false;
        }
    }

    /**
     * Connects to the specified JBot.
     * @param name name of the JBot to connect to
     * @param port port of the JBot to connect to
     */
    protected void connectToJBot(String name, int port) {
        Message msg = Message.obtain(null, MessageSubject.CONNECT.ordinal());
        Bundle b = new Bundle();
        b.putString(MessageData.JBOT_NAME, name);
        b.putInt(MessageData.JBOT_PORT, port);
        msg.setData(b);
        sendMessage(msg);
    }

    /**
     * Cancels connecting to a JBot.
     */
    protected void cancelConnectingToJBot() {
        disconnectFromJBot();
    }

    /**
     * Disconnects from the connected JBot.
     */
    private void disconnectFromJBot() {
        Message msg = Message.obtain(null, MessageSubject.DISCONNECT.ordinal());
        sendMessage(msg);
    }

    /**
     * Sends movement request to the connected JBot.
     * @param movement requested movement
     */
    protected void sendMovement(Movement movement) {
        Message msg = Message.obtain(null, MessageSubject.MOVEMENT.ordinal());
        Bundle b = new Bundle();
        b.putSerializable(MessageData.JBOT_MOVEMENT, movement);
        msg.setData(b);
        sendMessage(msg);
    }

    /**
     * Sets current activity as last used driving method.
     * That means that the activity will be opened next time after connection with a JBot will be successfully established.
     */
    private void setAsLastDrivingMethod() {
        Message msg = Message.obtain(null, MessageSubject.SET_DRIVING_METHOD.ordinal());
        Bundle b = new Bundle();
        b.putString(MessageData.LAST_DRIVING_METHOD_PACKAGE, getClass().getPackage().getName());
        b.putString(MessageData.LAST_DRIVING_METHOD_CLASS, getClass().getName());
        msg.setData(b);
        sendMessage(msg);
    }

    /**
     * Requests sending last driving method's details from the service.
     */
    private void requestLastDrivingMethod() {
        Message msg = Message.obtain(null, MessageSubject.GET_DRIVING_METHOD.ordinal());
        sendMessage(msg);
    }

    /**
     * Sends message to the service.
     * @param msg message to be send to the service
     */
    private void sendMessage(Message msg) {
        if (!serviceBound || outMessenger == null) {
            return;
        }
        try {
            outMessenger.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called when a JBot connects.
     * @param connected true if the connection was successful, false otherwise
     * @param remoteJBotInfo info about the connected JBot
     */
    protected void onJBotConnected(boolean connected, RemoteJBotInfo remoteJBotInfo) {
        onJBotUpdate(connected, remoteJBotInfo);
        if (connected) {
            Toast.makeText(this, R.string.event_connected, Toast.LENGTH_SHORT).show();
            requestLastDrivingMethod();
        }
    }

    /**
     * This method is called when the connected JBot disconnects.
     * @param connected true if the disconnection was unsuccessful, false otherwise
     * @param remoteJBotInfo info about the connected JBot
     */
    protected void onJBotDisconnected(boolean connected, RemoteJBotInfo remoteJBotInfo) {
        onJBotUpdate(connected, remoteJBotInfo);
    }

    /**
     * This method is called when the connection with the JBot interrupts.
     * It shows alert dialog about it.
     */
    protected void onJBotConnectionInterrupted() {
        showDisconnectionAlertDialog();
    }

    /**
     * This method is called when the connection with a JBot changes. It updates GUI.
     * @param connected true if the connection is alive, false otherwise
     * @param remoteJBotInfo info about the JBot
     */
    protected void onJBotUpdate(boolean connected, RemoteJBotInfo remoteJBotInfo) {
        this.connected = connected;

        TextView tvDrawerHeaderTitle = findViewById(R.id.tv_drawer_header_title);
        TextView tvDrawerHeaderSubtitle = findViewById(R.id.tv_drawer_header_subtitle);

        if (connected) {
            tvDrawerHeaderTitle.setText(remoteJBotInfo.getName());
            tvDrawerHeaderSubtitle.setText(remoteJBotInfo.getUsedNIName());
        } else {
            tvDrawerHeaderTitle.setText(getResources().getString(R.string.not_connected));
            tvDrawerHeaderSubtitle.setText("");
        }

        if (menuVisible && miConnectionChange != null) {
            updateConnectMenuItem(miConnectionChange);
        }
    }

    /**
     * This method is called when data from the connected JBot is received.
     * @param dataEvent JBot's data event
     */
    protected void onJBotDataEvent(DataEvent<? extends FloatArray> dataEvent) {
    }

    /**
     * Shows a DisconnectionAlertDialog.
     */
    private void showDisconnectionAlertDialog() {
        DisconnectionAlertDialog dialog = new DisconnectionAlertDialog();
        dialog.show(getFragmentManager(), "DisconnectionAlertDialog");
    }

    /**
     * Shows a DisconnectionYesNoDialog.
     */
    private void showDisconnectionYesNoDialog() {
        DisconnectionYesNoDialog disconnectionYesNoDialog = new DisconnectionYesNoDialog();
        disconnectionYesNoDialog.show(getFragmentManager(), "DisconnectionYesNoDialog");
    }

    /**
     * DisconnectionYesNoDialogListener's onPositiveButtonClicked implementation. It opens connecting activity.
     * @param dialog source dialog
     * @param which dialog's id
     */
    @Override
    public void onPositiveButtonClicked(DialogInterface dialog, int which) {
        disconnectFromJBot();
        dialog.dismiss();
        startConnectingActivity();
    }

    /**
     * Starts connecting activity.
     */
    private void startConnectingActivity() {
        startActivity("cz.vabalcar.android.rcjbotandroidcontroller", "cz.vabalcar.android.rcjbotandroidcontroller.RCJBotConnectingActivity");
    }

    /**
     * Starts driving method read from the given message.
     * @param msg message
     */
    private void startDrivingMethod(@NonNull Message msg) {
        String drivingMethodPackage = msg.getData().getString(MessageData.LAST_DRIVING_METHOD_PACKAGE, null);
        String drivingMethodClass = msg.getData().getString(MessageData.LAST_DRIVING_METHOD_CLASS, null);
        if (drivingMethodPackage != null && drivingMethodClass != null) {
            startActivity(drivingMethodPackage, drivingMethodClass);
        }
    }

    /**
     * Starts an activity.
     * @param activityPackage activity's package
     * @param activityClass activity's class
     */
    private void startActivity(String activityPackage, String activityClass) {
        Intent i = new Intent();
        i.setComponent(new ComponentName(activityPackage, activityClass));
        startActivity(i);
    }

    /**
     * Updates connect/disconnect button's title to match the current connection status.
     * @param cmi the button
     */
    private void updateConnectMenuItem(@NonNull MenuItem cmi) {
        cmi.setTitle(connected ? R.string.action_disconnect : R.string.action_connect);
    }
}
