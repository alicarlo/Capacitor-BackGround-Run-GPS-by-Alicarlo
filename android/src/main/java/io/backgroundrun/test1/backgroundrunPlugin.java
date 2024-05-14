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

import android.app.Activity;
import com.getcapacitor.Bridge;
import android.app.Application;
import android.Manifest;
import androidx.core.content.ContextCompat;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import android.provider.Settings;
import android.net.Uri;
import android.app.AlertDialog;
import android.content.DialogInterface;

import android.net.Uri;
import android.os.PowerManager;
import android.app.AppOpsManager;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

@CapacitorPlugin(name = "backgroundrun")
public class BackgroundrunPlugin extends Plugin {

	private PowerManager.WakeLock wakeLock;
	private static final String CHANNEL_ID = "backgroundrun_notification_channel";
	private boolean appIsInForeground = false;
	private static Application app;
	private static Application.ActivityLifecycleCallbacks callbacks;
	private static final int REQUEST_LOCATION_PERMISSION = 1001;
	private static final int REQUEST_BACKGROUND_LOCATION_PERMISSION = 1;
	private static final int REQUEST_NOTIFICATION_PERMISSION = 123;
	public static final String  msg1 = "To enable this feature, you need to grant the necessary permissions in the app settings. Do you want to go to settings now?";
	public static final String  msg2 = "Background location permission is required, you need to grant the allow location permissions all the time. Do you want to go to settings now?";
	
	public void load(Context context, Intent intent, int number) {
		app = (Application) getContext().getApplicationContext();
		setApplication(app);
		callbacks = new Application.ActivityLifecycleCallbacks() {
				// Implementa los métodos de los callbacks aquí
				public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
				}

				public void onActivityStarted(Activity activity) {
				}

				public void onActivityResumed(Activity activity) {
					context.stopService(intent);
					appIsInForeground = true;
				}

				public void onActivityPaused(Activity activity) {
					if (number == 0) {
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
		setCallbacks(callbacks);
		app.registerActivityLifecycleCallbacks(callbacks);
	}

	public static void setApplication(Application application) {
		app = application;
	}

	public static void setCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {
		callbacks = activityLifecycleCallbacks;
	}
	
	// Method used
	@PluginMethod
	public void acquireWakeLock(PluginCall call) {
		Context context = getContext();
		if (context == null) {
			call.reject("Unable to get context");
			return;
		}

		PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		if (powerManager == null) {
			call.reject("Unable to get PowerManager");
			return;
		}

		wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "BackgroundrunPlugin::MyWakelockTag");
		wakeLock.acquire();
		JSObject ret = new JSObject();
		ret.put("message", "permission granted");
		call.resolve(ret);
	}

	// Method used
	@PluginMethod
	public void releaseWakeLock(PluginCall call) {
		if (wakeLock != null && wakeLock.isHeld()) {
			wakeLock.release();
		}
		JSObject ret = new JSObject();
		ret.put("message", "permission removed");
		call.resolve(ret);
	}
	
	// Method used
	@PluginMethod
	public void ignoringBatteryOptimizationsService(PluginCall call) {
		Intent intent = new Intent();
    String packageName = getContext().getPackageName(); // Get the application package name
    PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
    if (!pm.isIgnoringBatteryOptimizations(packageName)) {
        intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
        intent.setData(Uri.parse("package:" + packageName));
        getContext().startActivity(intent); // Use getContext() to start the activity
				JSObject ret = new JSObject();
				ret.put("message", "navigation to battery options");
				call.resolve(ret);
    }else{
			JSObject ret = new JSObject();
			ret.put("message", "battery restriction removed");
			call.resolve(ret);
		}
	}

	// Method used
	@PluginMethod
	public void requestBatteryOptimizations(PluginCall call) {
		Intent intent = new Intent();
    String packageName = getContext().getPackageName();
    PowerManager pm = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
    if (pm.isIgnoringBatteryOptimizations(packageName)) {
        intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
        getContext().startActivity(intent);
				JSObject ret = new JSObject();
				ret.put("message", "navigation to battery options");
				call.resolve(ret);
    }else{
			JSObject ret = new JSObject();
			ret.put("message", "battery restriction activated");
			call.resolve(ret);
		}
	}

	// Method used
	@PluginMethod
	public void pauseNotificationPermission(PluginCall call) {
		if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
		} else {
			// Permission already granted
			JSObject ret = new JSObject();
			ret.put("message", "granted");
			call.resolve(ret);
		}
	}

	// Method used
	@PluginMethod
	public void requestNotificationPermission(PluginCall call) {
		if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_NOTIFICATION_PERMISSION);
		} else {
			// Permission already granted
			JSObject ret = new JSObject();
			ret.put("message", "granted");
			call.resolve(ret);
		}
	}


	@PluginMethod
	public void checkNotificationPermission(PluginCall call) {
			JSObject ret = new JSObject();
			NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
			if (notificationManager != null && notificationManager.isNotificationPolicyAccessGranted()) {
					ret.put("message", "Permission granted");
			} else {
					ret.put("message", "Permission not granted");
			}
			call.resolve(ret);
	}

	@PluginMethod
	public void openNotificationSettings(PluginCall call) {
		Context context = getContext();
		if (context == null) {
				call.reject("Unable to get context");
				return;
		}

		Intent intent = new Intent();
		intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
		intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		try {
			context.startActivity(intent);
			call.success();
		} catch (Exception e) {
			call.error("Failed to open notification settings: " + e.getMessage());
		}
	}

	// Method used
	@PluginMethod
	public void checkPermissionsService(PluginCall call) {
		Activity activity = getActivity();
		boolean value = call.getBoolean("value");
    if (activity != null) {
      requestLocationPermission(call, activity, value);
    }
	}

	// Method used
	@PluginMethod
	public void checkManageAppPermissionsPermission(PluginCall call) {
		// Get the value sent from the client
		boolean value = call.getBoolean("value");
		Context context = getContext();
		// Check if the option is enabled
		boolean hasManageAppPermissions = Settings.System.canWrite(context);
		if (hasManageAppPermissions) {
				// The option is enabled
				JSObject ret = new JSObject();
				ret.put("message", "granted");
				call.resolve(ret);
		} else {
			if (value == true) {
				// Option is not enabled, open settings to request permission
				Activity activity = getActivity();
				dialogNotificationManage(activity);
			}
			JSObject ret = new JSObject();
			ret.put("message", "Permission required. Please enable 'Modify system settings' for this app.");
			call.resolve(ret);
		}
	}

	// Method used
	@PluginMethod
	public void checkUsageStatsNotificationPausePermission(PluginCall call) {
		boolean value = call.getBoolean("value");
		if (hasUsageStatsPermission(getContext())) {
			call.success(); // You already have the permission
		} else {
			if (value == true) {
				// You don't have the permission, redirect the user to settings to enable it
				Activity activity = getActivity();
				dialogNotificationNotificationPause(activity);
			}
			JSObject ret = new JSObject();
			ret.put("message", "Permission required"); // Another option would be to throw an error and handle it in the frontend
			call.resolve(ret);
		}
	}

	// Method used
	@PluginMethod
	public void stopNotificationService(PluginCall call) {
		Context context = getContext();
    if (context != null) {
			if (app != null && callbacks != null) {
				app.unregisterActivityLifecycleCallbacks(callbacks);
				callbacks = null; // Clear the reference
				JSObject ret = new JSObject();
				ret.put("message", "Service stopped successfully");
				call.success(ret);
			}else{
				call.reject("The service continues running");
			}
    } else {
      call.reject("Service not running");
    }
	}
	
	// Method used
	@PluginMethod
	public void showNotificationOnAppClose(PluginCall call) {
		String url = call.getString("url", "Empty");
		String id1 = call.getString("id1", "Empty");
		String id2 = call.getString("id2", "Empty");
		String id3 = call.getString("id3", "Empty");
		String id4 = call.getString("id4", "Empty");
		String title = call.getString("title", "Empty");
		int timerGps =  call.getInt("timerGps", 0);
		boolean coordinatesShow =  call.getBoolean("coordinatesShow", false);
		boolean timeShow =  call.getBoolean("timeShow", false);



		if (url.equals("")) {
			JSObject ret = new JSObject();
			ret.put("message", "Url is required to start the process.");
			call.resolve(ret);
			return;
		}

		try {
			URL urlString = new URL(url);
			if ("https".equals(urlString.getProtocol())) {
				messageFormat("The URL is valid and has the HTTPS protocol.", call);
			} else {
				messageFormat("The URL does not have the HTTPS protocol.", call);
				return;
			}
		} catch (MalformedURLException e) {
			messageFormat("The URL is not valid.", call);
			return;
		}
		
		if (timerGps < 30000) {
			messageFormat("The minimum time required is 30,000 (30 seconds).", call);
			return;
		}

		JSObject data = new JSObject();
		data.put("url", url);
		data.put("id1", id1);
		data.put("id2", id2);
		data.put("id3", id3);
		data.put("id4", id4);
		data.put("title", title);
		data.put("timerGps", timerGps);
		data.put("coordinatesShow", coordinatesShow);
		data.put("timeShow", timeShow);

		
		Context context = getContext();
		BackgroundService service = new BackgroundService(data, context);
    if (context != null) {
			if (callbacks == null) {
				Intent intent = new Intent(context, BackgroundService.class);
				intent.setAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
				load(context, intent, 0);
				JSObject ret = new JSObject();
				ret.put("message", "Process started.");
				call.resolve(ret);
			}else{
				JSObject ret = new JSObject();
				ret.put("message", "A process is already active.");
				call.resolve(ret);
			}
    }
  }

	private void messageFormat(String message,PluginCall call) {
		JSObject ret = new JSObject();
		ret.put("message", message);
		call.resolve(ret);
		return;
	}

	private void dialogNotificationManage(Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("To enable this feature, you need to grant the necessary permissions in the app settings. Do you want to go to settings now?")
			.setTitle("Required permissions")
			.setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
					Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
					intent.setData(uri);
					activity.startActivity(intent);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private boolean hasUsageStatsPermission(Context context) {
		AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
		int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
		return mode == AppOpsManager.MODE_ALLOWED;
	}

	private void dialogNotificationNotificationPause(Activity activity) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage("To enable this feature, you need to grant the necessary permissions in the app settings. Do you want to go to settings now?")
		.setTitle("Permissions required")
		.setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
				Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
				intent.setData(uri);
				activity.startActivity(intent);
			}
		})
		.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void dialogNotification(Activity activity, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setMessage(msg)
			.setTitle("Permissions required")
			.setPositiveButton("Go to settings", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
					Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
					intent.setData(uri);
					activity.startActivity(intent);
				}
			})
			.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
				}
			});
		AlertDialog dialog = builder.create();
		dialog.show();
	}

	private void requestLocationPermission(PluginCall call, Activity activity, boolean value) {
		Context context = getContext().getApplicationContext();
		if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
				if (value == true) {
					dialogNotification(activity, msg1);
				}
				JSObject ret = new JSObject();
				ret.put("message", "Location permission has been denied, permission is required to continue.");
				call.resolve(ret);
			} else {
				if (value == true) {
					dialogNotification(activity, msg1);
				}
				JSObject ret = new JSObject();
				ret.put("message", "Location permission is required to continue.");
				call.resolve(ret);
			}
		} else {
			requestBackgroundLocationPermission(call, activity, value);
		}
	}

	private void requestBackgroundLocationPermission(PluginCall call, Activity activity, boolean value) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
				ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
				if (value == true) {
					dialogNotification(activity, msg2);
				}
				JSObject ret = new JSObject();
				ret.put("message", "Location permission has been denied, permission is required to continue.");
				call.resolve(ret);
			} else {
				if (value == true) {
					dialogNotification(activity, msg2);
				}
				JSObject ret = new JSObject();
				ret.put("message", "Background location permission is required.");
				call.resolve(ret);
			}
		} else {
			JSObject ret = new JSObject();
			ret.put("message", "Background mode permission granted.");
			call.resolve(ret);
		}
	}
}
