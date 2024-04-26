package io.backgroundrun.test1;

import android.content.Intent;
import android.content.IntentFilter;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.content.Context;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.backgroundrun.test1.R;


@CapacitorPlugin(name = "backgroundrun")
public class backgroundrunPlugin extends Plugin {
		// private static final String CHANNEL_ID = "io.backgroundrun.test1";
		private static final String CHANNEL_ID = "backgroundrun_notification_channel";


		public void onTaskRemoved(Intent rootIntent) {
    Context context = getContext();
    if (context != null) {
        BackgroundrunPlugin.showNotification(context);
    }
	}

		public void onDestroy() {
    if (broadcastReceiver != null) {
        getContext().unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }
	}

		private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Backgroundrun Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(getNotificationIcon(context))
                .setContentTitle("App Notification")
                .setContentText("This is a notification from your app")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private int getNotificationIcon(Context context) {
        boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        String iconName = isWhiteIcon ? "ic_notification" : "ic_notification_dark";
        return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
    }


		/*// Esto si funciona lanza una notificacion cuando se la app se pone en segundo plano
		// @Override
    public void load() {
        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        getContext().registerReceiver(broadcastReceiver, filter);
    }

		private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                showNotification(context);
            }
        }
    };

		// @Override
    // public void onDestroy() {
    //    getContext().unregisterReceiver(broadcastReceiver);
    //    super.onDestroy();
    //}

		public void onDestroy() {
    if (broadcastReceiver != null) {
        getContext().unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }
	}

		private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Backgroundrun Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(getNotificationIcon(context))
                .setContentTitle("App Notification")
                .setContentText("This is a notification from your app")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private int getNotificationIcon(Context context) {
        boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        String iconName = isWhiteIcon ? "ic_notification" : "ic_notification_dark";
        return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
    }*/

		// Esto si funciona lanza una notificacion cuando se abre la app
		/*@PluginMethod
    public void showNotificationOnAppClose(PluginCall call) {
        Context context = getContext();
        if (context != null) {
            showNotification(context);
            call.resolve();
        } else {
            call.reject("No se pudo obtener el contexto de la aplicación");
        }
    }

		private void showNotification(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Backgroundrun Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Channel description");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(getNotificationIcon(context))
                .setContentTitle("App Notification")
                .setContentText("This is a notification from your app")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification = builder.build();
        notificationManager.notify(1, notification);
    }

    private int getNotificationIcon(Context context) {
        boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        String iconName = isWhiteIcon ? "ic_notification" : "ic_notification_dark";
        return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
    }*/
    
}
