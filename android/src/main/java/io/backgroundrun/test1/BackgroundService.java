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
	
	private static final String ACTION_OPEN_ACTIVITY = "io.backgroundrun.test1.ACTION_OPEN_ACTIVITY";
	private static final String CHANNEL_ID = "BackgroundServiceChannel";
	private static final int NOTIFICATION_ID = 1;
	private int counter = 0;
	private int timerCounter = 0;
	private NotificationCompat.Builder notificationBuilder;
	private NotificationManager notificationManager;

	private LocationManager locationManager;
	private LocationListener locationListener;

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
			updateNotification(2);
			handler.postDelayed(this, 5000);
		}
	};

	private Handler handler = new Handler();
	private Runnable runnable = new Runnable() {
		@Override
		public void run() {
			int timer = BackgroundService.getTimerGps();
			handler.removeCallbacks(runnable2);
			updateNotification(1);
			handler.postDelayed(this, timer);
		}
	};

	// This method is not being used.
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
			try {
      	if ("OPEN_MY_APP".equals(intent.getAction())) {
					NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
					Notification notification = new NotificationCompat.Builder(context, CHANNEL_ID)
					.setSmallIcon(android.R.drawable.ic_dialog_info)
					.setContentTitle("My App")
					.setContentText("Tap to open the app")
					.setPriority(NotificationCompat.PRIORITY_HIGH)
					// .setSmallIcon(R.drawable.ic_notification)
					.setContentIntent(getPendingIntent(context))
					.setAutoCancel(true)
					.setSound(null)
					.build();
					notificationManager.notify(NOTIFICATION_ID, notification);
        }
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(e.getMessage());
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
		// Stop the service when the notification is pressed
		if (intent != null && "STOP_SERVICE".equals(intent.getAction())) {
			stopForeground(true);
			stopSelf();
			stopLocationUpdates();
			handler.removeCallbacks(runnable); // Stop service;
		}
		return START_STICKY;
	}

	private PendingIntent getPendingIntent(Context context) {
		String packageName = context.getPackageName();
		Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
		String className = launchIntent.getComponent().getClassName();
		Intent intent = null;
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
		createNotificationChannel();
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
		// It works without the broadcaster
		PendingIntent contentIntent = getPendingIntent(this);

		String titleGet = BackgroundService.getTitle();
		final String titleNotification = titleGet.isEmpty() ? "Background Run" : titleGet;

		notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
		.setContentTitle(titleNotification)
		//.setSmallIcon(android.R.drawable.ic_dialog_info)
		.setSmallIcon(getNotificationIcon(this))
		.setOngoing(true) // This line makes the notification persistent.
		.setSound(null) // This line disables the sound of the notification.
		.setContentIntent(contentIntent); 	// Funciona sin el  broadcaster
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			notificationBuilder.setChannelId(CHANNEL_ID);
		}
		handler.postDelayed(runnable2, 20);
		int timer = BackgroundService.getTimerGps();
		handler.postDelayed(runnable, 1000); // Init container
	}

	private int getNotificationIcon(Context context) {
		boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
		String iconName = isWhiteIcon ? "ic_notification" : "ic_notification_dark";
		return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
	}  

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Make sure to unregister the broadcast receiver
		stopLocationUpdates();
		handler.removeCallbacks(runnable); // Detener el contador al destruir el servicio;
	}

	private void createNotificationChannel() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			// NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background Service", NotificationManager.IMPORTANCE_DEFAULT);
			NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Background Service", NotificationManager.IMPORTANCE_NONE);
			notificationManager = getSystemService(NotificationManager.class);
			notificationManager.createNotificationChannel(channel);
		}
	}

	// Method to stop location updates.
	private void stopLocationUpdates() {
		if (locationManager != null && locationListener != null) {
			locationManager.removeUpdates(locationListener);
		}
	}

  private void updateNotification(int flag) {
		if(flag ==2) {
			Notification notification = notificationBuilder.build();
			startForeground(NOTIFICATION_ID, notification);
			handler.removeCallbacks(runnable2);
		}else{ 
			Calendar calendar = Calendar.getInstance();
			Date date = calendar.getTime();

			// Format the date and time.
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
			String formattedDate = sdf.format(date);

			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-mm-yyyy HH:mm:ss", Locale.getDefault());
			String formattedDate2 = sdf.format(date);

			// Check if permissions are granted to access the location.
    	if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        // Get the current location.
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
          @Override
          public void onLocationChanged(Location location) {
						String urlSend = BackgroundService.getUrl();
            // Update the notification with the current location.
						boolean coordinatesShow = BackgroundService.getCoordinatesShow();
						final String coordinatesNotification = coordinatesShow == true ? location.getLatitude() + ", " + location.getLongitude() : ""; 

						boolean timeShow = BackgroundService.getTimeShow();
						final String timeNotification = timeShow == true ? formattedDate : ""; 

            notificationBuilder.setContentText("" + coordinatesNotification + " "+ timeNotification);
						Notification notification = notificationBuilder.build();
      			startForeground(NOTIFICATION_ID, notification);

            // Stop location updates after obtaining it.
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
				// Request location updates.
				// PRIORITY_LOW_POWER
				// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

				// PRIORITY_BALANCED_POWER_ACCURACY
				// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, locationListener);

				// PRIORITY_HIGH_ACCURACY
				// locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, locationListener);

				// Super PRIORITY_HIGH_ACCURACY
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0, locationListener);
    	}
		}
  }

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}