package io.backgroundrun.test1;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import android.os.Handler;

import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.content.BroadcastReceiver;
import android.app.Activity;
import android.content.ContextWrapper;
import androidx.core.app.NotificationManagerCompat;

public class BackgroundService extends Service {
		// private MyBroadcastReceiver myBroadcastReceiver;
		private static final String ACTION_OPEN_ACTIVITY = "io.backgroundrun.test1.ACTION_OPEN_ACTIVITY";
    private static final String CHANNEL_ID = "BackgroundServiceChannel";
    private static final int NOTIFICATION_ID = 1;
		private int counter = 0;
		
		private NotificationCompat.Builder notificationBuilder;
		private NotificationManager notificationManager;

		 private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateNotification();
            handler.postDelayed(this, 1000); // Actualizar cada segundo
        }
    };

		/*private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí puedes iniciar tu actividad
        Intent launchIntent = new Intent(context, TuActividad.class);
        launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(launchIntent);
    }
	};*/

		/*private BroadcastReceiver stopServiceReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
			/*try {
					Log.d("BackgroundrunPlugin", "entro13");
        	// Obtener el nombre de la clase MainActivity dinámicamente
            String packageName = context.getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            String className = launchIntent.getComponent().getClassName();

            // Crear un Intent para abrir el MainActivity
            Intent openAppIntent = new Intent(context, Class.forName(className));
            openAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(openAppIntent);
						Log.d("BackgroundrunPlugin", "entro14");
			} catch (ClassNotFoundException e) {
    		e.printStackTrace();
			}*/
			// Aquí puedes abrir la aplicación
			/*Log.d("BackgroundrunPlugin", "entro15");
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        if (launchIntent != null) {
            launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(launchIntent);
        }
    }
};*/

/*private BroadcastReceiver notificationReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Aquí se ejecutará tu lógica cuando se reciba la transmisión
        Log.d("BackgroundrunPlugin", "Se recibió la transmisión");
        try {
            // Obtener el nombre de la clase MainActivity dinámicamente
            String packageName = context.getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            String className = launchIntent.getComponent().getClassName();

            // Crear un Intent para abrir el MainActivity
            Intent openAppIntent = new Intent(context, Class.forName(className));
            openAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(openAppIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
};*/

private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
			try {
				 	Log.d("BackgroundrunPlugin", "entro11AAAA");
      if ("OPEN_MY_APP".equals(intent.getAction())) {
             // Obtener el nombre de la clase MainActivity dinámicamente
            /*
						Android 10
						String packageName = context.getPackageName();
            Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            String className = launchIntent.getComponent().getClassName();
						Log.d("BackgroundrunPlugin", "entro11BBB"+className);
						Log.d("BackgroundrunPlugin", "entro11ACCC"+Class.forName(className));
            // Crear un Intent para abrir el MainActivity
            Intent openAppIntent = new Intent(context, Class.forName(className));
            openAppIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(openAppIntent);
						*/
							Log.d("BackgroundrunPlugin", "entro11BBBB");
						NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
										.setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("My App")
                    .setContentText("Tap to open the app")
										.setPriority(NotificationCompat.PRIORITY_HIGH) // Establecer prioridad alta
                    // .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(getPendingIntent(context))
                    .setAutoCancel(true)
                    .build();

            notificationManager.notify(NOTIFICATION_ID, notification);
        }
				} catch (Exception e) {
             e.printStackTrace();
            Log.e("BackgroundrunPlugin", "Error receiving broadcast: " + e.getMessage());
        }
			
    }
};


 private Activity getActivity(Context context) {
    if (context instanceof ContextWrapper) {
        while (!(context instanceof Activity)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (Activity) context;
    }
    return null;
}

@Override
public int onStartCommand(Intent intent, int flags, int startId) {
		// Detener el servicio cuando se presiona la notificación
		if (intent != null && "STOP_SERVICE".equals(intent.getAction())) {
		// Intent broadcastIntent = new Intent("OPEN_MY_APP"); // Funciona con el  broadcaster
		// sendBroadcast(broadcastIntent); // Funciona con el  broadcaster
			stopForeground(true);
			stopSelf();
		}
		return START_STICKY;
}

		
private PendingIntent getPendingIntent(Context context) {
		Log.e("BackgroundrunPlugin", "entro2000");
		String packageName = context.getPackageName();
    Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
    String className = launchIntent.getComponent().getClassName();
    Intent intent = null;
		Log.e("BackgroundrunPlugin", className);
    try {
        intent = new Intent(context, Class.forName(className));
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
    if (intent != null) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT  | PendingIntent.FLAG_IMMUTABLE);
    } else {
        return null;
    }

}

     @Override
    public void onCreate()  {
			super.onCreate();
			Log.d("BackgroundrunPlugin", "entro3");
			createNotificationChannel();
			// Registra el BroadcastReceiver para recibir el broadcast
			// IntentFilter filter = new IntentFilter("OPEN_MY_APP"); 	// Funciona con el  broadcaster
			// registerReceiver(broadcastReceiver, filter); 	// Funciona con el  broadcaster
			// Crear una notificación con una acción para detener el servicio
			Intent stopServiceIntent = new Intent(this, BackgroundService.class);
			stopServiceIntent.setAction("STOP_SERVICE");
			PendingIntent pendingStopServiceIntent = PendingIntent.getService(this, 0, stopServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

			// Funciona sin el  broadcaster
			PendingIntent contentIntent = getPendingIntent(this);
			
        notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
				.setContentTitle("Background Service")
				.setSmallIcon(android.R.drawable.ic_dialog_info)
				// .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop Service", pendingStopServiceIntent) 	// Funciona con el  broadcaster
				.setContentIntent(contentIntent); 	// Funciona sin el  broadcaster
				// .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop Service", pendingStopServiceIntent);
        // startForeground(NOTIFICATION_ID, notificationBuilder.build());
        handler.postDelayed(runnable, 1000); // Iniciar el contad

    }

	  /*@Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        restartForegroundService();
    }*/

    /*private void restartForegroundService() {
        updateNotification();
        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }*/

		@Override
    public void onDestroy() {
        super.onDestroy();

				// Asegúrate de anular el registro del receptor de transmisiones
        handler.removeCallbacks(runnable); // Detener el contador al destruir el servicio;
				// Funciona para el broadcaster unregisterReceiver(broadcastReceiver);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background Service", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void updateNotification() {
        if (counter >= 1000) {
            counter = 0; // Reiniciar el contador cuando llegue a 1000
        }
        counter++;
				
        notificationBuilder.setContentText("The service is running in the background. Counter: " + counter);
        Notification notification = notificationBuilder.build();
        startForeground(NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}