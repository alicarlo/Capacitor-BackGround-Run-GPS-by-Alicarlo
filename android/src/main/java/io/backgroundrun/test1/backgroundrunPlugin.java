package io.backgroundrun.test1;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.content.Context;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "backgroundrunPlugin")
public class backgroundrunPlugin extends Plugin {
		private static final String CHANNEL_ID = "io.backgroundrun.test1";
		private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNotification(context);
        }
    };

		@Override
    protected void handleOnDestroy() {
        super.handleOnDestroy();
        getContext().unregisterReceiver(broadcastReceiver);
    }

		@Override
    protected void handleOnStart() {
        super.handleOnStart();
        getContext().registerReceiver(broadcastReceiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    }



    @PluginMethod
		private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "App Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(getNotificationIcon())
                .setContentTitle("App Notification")
                .setContentText("This is a notification from your app")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private int getNotificationIcon() {
        boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return isWhiteIcon ? R.drawable.ic_notification : R.drawable.ic_notification_dark;
    }
    
}
