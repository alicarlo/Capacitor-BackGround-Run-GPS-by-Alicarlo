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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;
import android.Manifest;
import androidx.core.content.ContextCompat;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import org.json.JSONException;
import com.getcapacitor.JSObject;
import android.content.SharedPreferences;

public class BackgroundService extends Service {
	
		// private MyBroadcastReceiver myBroadcastReceiver;
		private static final String ACTION_OPEN_ACTIVITY = "io.backgroundrun.test1.ACTION_OPEN_ACTIVITY";
    private static final String CHANNEL_ID = "BackgroundServiceChannel";
    private static final int NOTIFICATION_ID = 1;
		private int counter = 0;
		private int timerCounter = 0;
		private NotificationCompat.Builder notificationBuilder;
		private NotificationManager notificationManager;

		private LocationManager locationManager;
    private LocationListener locationListener;

		// private String url;
		private static String url;
		private static String id1;
		private static String id2;
		private static String id3;
		private static String id4;
		private static String title;
		private static int timerGps;
		private static Boolean coordinatesShow;
		private static Boolean timeShow;

		 public BackgroundService() {
        super();
    }

	public BackgroundService(JSObject data,Context context) {
		super();

		// this.url = url;
		/*this.url = data.getString("url", "Empty");
		this.id1 = data.getString("id1", "Empty");
		this.id2 = data.getString("id2", "Empty");
		this.id3 = data.getString("id3", "Empty");
		this.id4 = data.getString("id4", "Empty");
		this.title = data.getString("title", "Empty");
		this.timerGps =  data.optInt("timerGps", 0);
		this.coordinatesShow =  data.getBoolean("coordinatesShow", false);
		this.timeShow =  data.getBoolean("timeShow", false);
		*/

		 SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString("url", data.getString("url", "Empty"));
    editor.putString("id1", data.getString("id1", "Empty"));
    editor.putString("id2", data.getString("id2", "Empty"));
    editor.putString("id3", data.getString("id3", "Empty"));
    editor.putString("id4", data.getString("id4", "Empty"));
    editor.putString("title", data.getString("title", "Empty"));
    editor.putInt("timerGps", data.optInt("timerGps", 0));
    editor.putBoolean("coordinatesShow", data.getBoolean("coordinatesShow", false));
    editor.putBoolean("timeShow", data.getBoolean("timeShow", false));
    editor.apply();

		
		
	}



	public static String getUrl() {
    return url;
  }

	public static String getId1() {
    return id1;
  }

	public static String getId2() {
    return id2;
  }

	public static String getId3() {
    return id3;
  }

	public static String getId4() {
    return id4;
  }

	public static String getTitle() {
    return title;
  }

	public static int getTimerGps() {
    return timerGps;
  }

	public static Boolean getCoordinatesShow() {
    return coordinatesShow;
  }

	public static Boolean getTimeShow() {
    return timeShow;
  }

	private Handler handler2 = new Handler();
    private Runnable runnable2 = new Runnable() {
        @Override
        public void run() {
						Log.d("BackgroundrunPlugin", "RUUNABLE 2");
						updateNotification(2);
            handler.postDelayed(this, 5000); // Actualizar cada segundo // 5min 300000
        }
    };

		private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
						int timer = BackgroundService.getTimerGps();
						/*int timer = BackgroundService.getTimerGps();
						if (timerCounter >= timer) {
							Log.d("BackgroundrunPlugin", "VEO EL TIME");
							updateNotification();
							timerCounter = 0;
						}else{
							timerCounter += 1;
						}*/
						handler.removeCallbacks(runnable2);
						updateNotification(1);
						Log.d("BackgroundrunPlugin", "TIMER GPS ADD:"+timer);
						// int timer = BackgroundService.getTimerGps();
						// Log.d("BackgroundrunPlugin", "TIMER GPS ADD"+timer);
            handler.postDelayed(this, timer); // Actualizar cada segundo // 5min 300000
        }
    };

		
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
										.setSound(null)
                    .build();

            notificationManager.notify(NOTIFICATION_ID, notification);
						// updateNotification();
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
	super.onStartCommand(intent, flags, startId);
		// Detener el servicio cuando se presiona la notificación
		if (intent != null && "STOP_SERVICE".equals(intent.getAction())) {
		// Intent broadcastIntent = new Intent("OPEN_MY_APP"); // Funciona con el  broadcaster
		// sendBroadcast(broadcastIntent); // Funciona con el  broadcaster
			// stopLocationUpdates();
			Log.e("BackgroundrunPlugin", "entro5010124");
			stopForeground(true);
			stopSelf();
			stopLocationUpdates();
			handler.removeCallbacks(runnable); // Detener el contador al destruir el servicio;
		}

		Log.d("BackgroundrunPlugin", "entro3 intent qualll");

    // As	ignar la URL al Intent
		// String url = BackgroundService.getUrl();
    // intent.putExtra("url", url);

		/*if (intent != null && intent.getExtras() != null) {
        String url = intent.getExtras().getString("url");
        if (url == null) {
            Log.e("BackgroundService", "URL es null");
        } else {
            Log.d("BackgroundService", "URL recibida: " + url);
        }
    }*/
		
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

			SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
			url = sharedPreferences.getString("url", "Empty");
			id1 = sharedPreferences.getString("id1", "Empty");
			id2 = sharedPreferences.getString("id2", "Empty");
			id3 = sharedPreferences.getString("id3", "Empty");
			id4 = sharedPreferences.getString("id4", "Empty");
			title = sharedPreferences.getString("title", "Empty");
			timerGps = sharedPreferences.getInt("timerGps", 0);
			coordinatesShow = sharedPreferences.getBoolean("coordinatesShow", false);
			timeShow = sharedPreferences.getBoolean("timeShow", false);
			// Usa la URL aquí si es necesario
			/*Log.d("BackgroundService", "URL recibid999999a: " + url);
        if (url != null) {
            Log.d("BackgroundService", "URL recibida: " + url);
        } else {
            Log.d("BackgroundService", "URL no está disponible");
        }
				*/

		 
			Log.d("BackgroundrunPlugin", "entra el onCreate");
			Log.d("BackgroundrunPlugin", "entro3");
			createNotificationChannel();
			// Registra el BroadcastReceiver para recibir el broadcast
			// IntentFilter filter = new IntentFilter("OPEN_MY_APP"); 	// Funciona con el  broadcaster
			// registerReceiver(broadcastReceiver, filter); 	// Funciona con el  broadcaster
			// Crear una notificación con una acción para detener el servicio
			Intent stopServiceIntent = new Intent(this, BackgroundService.class);

			stopServiceIntent.putExtra("url", url);

			stopServiceIntent.putExtra("id1", id1);
			stopServiceIntent.putExtra("id2", id2);
			stopServiceIntent.putExtra("id3", id3);
			stopServiceIntent.putExtra("id4", id4);
			stopServiceIntent.putExtra("timerGps", timerGps);
			stopServiceIntent.putExtra("coordinatesShow", coordinatesShow);
			stopServiceIntent.putExtra("timeShow", timeShow);

			stopServiceIntent.putExtra("title", title);
			stopServiceIntent.setAction("STOP_SERVICE");
			PendingIntent pendingStopServiceIntent = PendingIntent.getService(this, 0, stopServiceIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
			// Funciona sin el  broadcaster
			PendingIntent contentIntent = getPendingIntent(this);

			String titleGet = BackgroundService.getTitle();
			Log.d("BackgroundrunPlugin", "truenaaa:"+titleGet);
			final String titleNotification = titleGet.isEmpty() ? "Background Run" : titleGet;

			notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
			.setContentTitle(titleNotification)
			//.setSmallIcon(android.R.drawable.ic_dialog_info)
			.setSmallIcon(getNotificationIcon(this))
			.setOngoing(true) // Esta línea hace que la notificación sea persistente
			.setSound(null) // Esta línea desactiva el sonido de la notificación
			// .addAction(android.R.drawable.ic_menu_close_clear_cancel, "Stop Service", pendingStopServiceIntent) 	// Funciona con el  broadcaster
			.setContentIntent(contentIntent); 	// Funciona sin el  broadcaster
			// updateNotification();
			// handler.postDelayed(runnable, 100); // Iniciar el contad
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				notificationBuilder.setChannelId(CHANNEL_ID);
			}
			// updateNotification();
			handler.postDelayed(runnable2, 20);
			int timer = BackgroundService.getTimerGps();
			handler.postDelayed(runnable, 1000); // Iniciar el contad

    }

		private int getNotificationIcon(Context context) {
			boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
			String iconName = isWhiteIcon ? "ic_notification" : "ic_notification_dark";
			return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
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
				Log.d("BackgroundrunPlugin", "entra el onDestroy");
				// Asegúrate de anular el registro del receptor de transmisiones
				stopLocationUpdates();
        handler.removeCallbacks(runnable); // Detener el contador al destruir el servicio;
				// Funciona para el broadcaster unregisterReceiver(broadcastReceiver);

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background Service", NotificationManager.IMPORTANCE_DEFAULT);
						NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background Service", NotificationManager.IMPORTANCE_NONE);
            notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

		// Método para detener las actualizaciones de ubicación
			private void stopLocationUpdates() {
				Log.d("BackgroundrunPlugin", "update405 Detiene el gps");
					if (locationManager != null && locationListener != null) {
							locationManager.removeUpdates(locationListener);
					}
			}

    private void updateNotification(int flag) {
				if(flag ==2) {
						Notification notification = notificationBuilder.build();
      			startForeground(NOTIFICATION_ID, notification);
						handler.removeCallbacks(runnable2);
					 // Notification notification = notificationBuilder.build();
          // startForeground(NOTIFICATION_ID, notification);
				}else{ 

							Log.d("BackgroundrunPlugin", "VEO EL TIME");
				   //if (counter >= 1000) {
        //     counter = 0; // Reiniciar el contador cuando llegue a 1000
        // }
        // counter++;
				// Obtener la fecha y hora actual
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        // Formatear la fecha y hora
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String formattedDate = sdf.format(date);

				SimpleDateFormat sdf2 = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss", Locale.getDefault());
        String formattedDate2 = sdf.format(date);

		// Verificar si se tienen permisos para acceder a la ubicación
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			/*	int timer = BackgroundService.getTimerGps();
			if (timerCounter >= timer) {
				timerCounter = 0;
			}else{
				timerCounter += 1;
			}*/
			

			Log.d("BackgroundrunPlugin", "update101");
			
        // Obtener la ubicación actual
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
							Log.d("BackgroundrunPlugin", "update102");
							Log.d("BackgroundrunPlugin", "update1010"+location.getLatitude()+"-"+location.getLongitude()+"-"+formattedDate2);
							String urlSend = BackgroundService.getUrl();
							Log.e("BackgroundrunPlugin", "urk gps:"+urlSend);
								Log.e("BackgroundrunPlugin", "urk gps:"+url);
                // Actualizar la notificación con la ubicación actual
								boolean coordinatesShow = BackgroundService.getCoordinatesShow();
								Log.e("BackgroundrunPlugin", "show coordinates:"+coordinatesShow);
								final String coordinatesNotification = coordinatesShow == true ? location.getLatitude() + ", " + location.getLongitude() : ""; 

								boolean timeShow = BackgroundService.getTimeShow();
								final String timeNotification = timeShow == true ? formattedDate : ""; 

                notificationBuilder.setContentText("" + coordinatesNotification + " "+ timeNotification);
								Notification notification = notificationBuilder.build();
      					startForeground(NOTIFICATION_ID, notification);
                // Notification notification = notificationBuilder.build();
                // startForeground(NOTIFICATION_ID, notification);

                // Detener las actualizaciones de la ubicación después de obtenerla
                if (locationManager != null && locationListener != null) {
                    locationManager.removeUpdates(locationListener);
                }
								String id1 = BackgroundService.getId1();
								String id2 = BackgroundService.getId2();
								String id3 = BackgroundService.getId3();
								String id4 = BackgroundService.getId4();

								new CallEndpointService(location.getLatitude(), location.getLongitude(), formattedDate2, urlSend, id1, id2, id3, id4).execute();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        	};

					// Solicitar actualizaciones de ubicación
					// PRIORITY_LOW_POWER
					// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

					// PRIORITY_BALANCED_POWER_ACCURACY
					// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);

					// PRIORITY_HIGH_ACCURACY
					// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

					// Super PRIORITY_HIGH_ACCURACY
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
    		}
			// Log.d("BackgroundrunPlugin", "update100");  
		
				}
    }



    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}