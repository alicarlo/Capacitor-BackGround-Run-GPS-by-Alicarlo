package io.backgroundrun.test1;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.App;
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
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.backgroundrun.test1.R;

import android.content.Context;
import android.app.Activity;
import com.getcapacitor.Bridge;
import android.app.Application;

// import io.backgroundrun.test1.;
// import com.getcapacitor.ActivityState;
// import com.getcapacitor.PluginCallActivityListener;
// import io.backgroundrun.test1.BackgroundrunPlugin;
@CapacitorPlugin(name = "backgroundrun")
public class BackgroundrunPlugin extends Plugin {
	// BackgroundService service = new BackgroundService();
		// private static final String CHANNEL_ID = "io.backgroundrun.test1";
		private static final String CHANNEL_ID = "backgroundrun_notification_channel";
		private boolean appIsInForeground = false;


    public void load(Context context, Intent intent) {
				Log.d("BackgroundrunPlugin", "entro5");

        Application app = (Application) getContext().getApplicationContext();
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
	
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

  
            public void onActivityStarted(Activity activity) {
            }

      
            public void onActivityResumed(Activity activity) {
								Log.d("BackgroundrunPlugin", "entro6");
									context.stopService(intent);
									
								 
                appIsInForeground = true;
            }

     
            public void onActivityPaused(Activity activity) {
								Log.d("BackgroundrunPlugin", "entro7");
								
								context.startService(intent);
								 
                appIsInForeground = false;
            }

     
            public void onActivityStopped(Activity activity) {
            }

   
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

   
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

		@PluginMethod
		public void stopNotificationService(PluginCall call) {
		Context context = getContext();
    if (context != null) {
			Intent intent = new Intent(context, BackgroundService.class);
      intent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.stopService(intent);
        call.success();
    } else {
        call.reject("Service not running");
    }
	}


		@PluginMethod
    public void showNotificationOnAppClose(PluginCall call) {
			BackgroundService service = new BackgroundService();
        /*getApp().getActivity().runOnUiThread(() -> {
        getApp().addStateChangeListeners((state, source) -> {
            if (state == AppState.BACKGROUND) {
                // La aplicación se está cerrando completamente, muestra la notificación aquí
                showNotification(getContext());
            }
        });
    });
    call.resolve();*/
		/* #1 este se activa cuando se bloquea la pantalla
		BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // La aplicación se está cerrando completamente, muestra la notificación aquí
            showNotification(context);
        }
    };

    getContext().registerReceiver(receiver, new IntentFilter(Intent.ACTION_SCREEN_OFF));
    call.resolve();
		*/
		/* #2
		BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action != null && action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                // La aplicación se está cerrando, muestra la notificación aquí
                showNotification(context);
            }
        }
    };

    getContext().registerReceiver(receiver, new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
    call.resolve();
		*/
	/* #3 no funciono
	Context context = getContext();
    if (context instanceof Activity) {
        Activity activity = (Activity) context;
        activity.getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // La actividad principal se ha destruido, muestra la notificación aquí
                showNotification(activity);
            }
        });
    }
    call.resolve();
		*/	

			 Context context = getContext();
    if (context != null) {
				Log.d("BackgroundrunPlugin", "entro1");
        Intent intent = new Intent(context, BackgroundService.class);
        intent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        // context.startService(intent);
				 /*if (appIsInForeground) {
            // La aplicación está en primer plano
            // Haz algo aquí
							context.stopService(intent);
        } else {
            // La aplicación está en segundo plano
            // Haz algo diferente aquí
						context.startService(intent);
        }*/
				 call.resolve();
				 	load(context, intent);
    }else{
			Log.d("BackgroundrunPlugin", "entro1");
			call.reject("Failed to start service");
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

		/*@Override
		public void onTaskRemoved(Intent rootIntent) {
			super.onTaskRemoved(rootIntent);
			Log.d("BackgroundrunPlugin", "onTaskRemoved() called");
    Context context = getContext();
		Log.d("BackgroundrunPlugin", "onTaskRemoved() called2");
    if (context != null) {
        showNotification(context);
    }
		}*/

		/*public void onDestroy() {
    if (broadcastReceiver != null) {
        getContext().unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
    }
		}*/

		

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
