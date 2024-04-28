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
		 private static Application app;
		  private static Application.ActivityLifecycleCallbacks callbacks;

    public void load(Context context, Intent intent, int numero) {
				Log.d("BackgroundrunPlugin", "entro5");
				Log.d("BackgroundrunPlugin", "entro5Numero:"+numero);
        app = (Application) getContext().getApplicationContext();
				setApplication(app);
				callbacks = new Application.ActivityLifecycleCallbacks() {
					// Log.d("BackgroundrunPlugin", "entro5 calllls");
            // Implementa los métodos de los callbacks aquí
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
									Log.d("BackgroundrunPlugin", "entro77");
											Log.d("BackgroundrunPlugin", "entro77:"+numero);

								if (numero == 0) {
									Log.d("BackgroundrunPlugin", "entro7");
									

									Log.d("BackgroundrunPlugin", "entro607.5"+app);
							Log.d("BackgroundrunPlugin", "entro607"+callbacks);
									context.startService(intent);
									
									appIsInForeground = false;
								}
            }

     
            public void onActivityStopped(Activity activity) {
            }

   
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

   
            public void onActivityDestroyed(Activity activity) {
            }
        };
				Log.d("BackgroundrunPlugin", "entro55Register");
				setCallbacks(callbacks);
        app.registerActivityLifecycleCallbacks(callbacks);
        /*app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
	
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
									Log.d("BackgroundrunPlugin", "entro77");
											Log.d("BackgroundrunPlugin", "entro77:"+numero);
								if (numero == 0) {
									Log.d("BackgroundrunPlugin", "entro7");
									
									context.startService(intent);
									
									appIsInForeground = false;
								}
            }

     
            public void onActivityStopped(Activity activity) {
            }

   
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

   
            public void onActivityDestroyed(Activity activity) {
            }
        });
				*/
    }

		 public static void setApplication(Application application) {
        app = application;
    }

    public static void setCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        callbacks = activityLifecycleCallbacks;
    }

	// Use
	@PluginMethod
	public void stopNotificationService(PluginCall call) {
		Context context = getContext();
    if (context != null) {
				if (app != null && callbacks != null) {
					app.unregisterActivityLifecycleCallbacks(callbacks);
					callbacks = null; // Limpia la referencia
        }
				JSObject ret = new JSObject();
				ret.put("message", "Servicio detenido exitosamente");
        call.success(ret);
    } else {
      call.reject("Service not running");
    }
	}
	
	// Use
	@PluginMethod
	public void showNotificationOnAppClose(PluginCall call) {
			BackgroundService service = new BackgroundService();
			Context context = getContext();
    if (context != null) {
			Intent intent = new Intent(context, BackgroundService.class);
			intent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
				call.resolve();
			load(context, intent, 0);
    }else{
			call.reject("Failed to start service");
		} 
  }

	// Not Use
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

	// Not Use
	private int getNotificationIcon(Context context) {
			boolean isWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
			String iconName = isWhiteIcon ? "ic_notification" : "ic_notification_dark";
			return context.getResources().getIdentifier(iconName, "drawable", context.getPackageName());
	}    
}
