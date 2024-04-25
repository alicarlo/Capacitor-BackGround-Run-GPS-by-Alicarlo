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

@CapacitorPlugin(name = "backgroundrun")
public class backgroundrunPlugin extends Plugin {
		private static final String TAG = "MyNotificationPlugin";
    private backgroundrun implementation = new backgroundrun();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }

		@PluginMethod
    public void showNotificationOnAppClose(PluginCall call) {
        Context context = getContext();
        if (context != null) {
            int iconId = context.getResources().getIdentifier("ic_notification", "drawable", context.getPackageName());

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                    .setSmallIcon(iconId)
                    .setContentTitle("¡Hasta luego!")
                    .setContentText("La aplicación se ha cerrado.")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());

            call.resolve();
        } else {
            call.reject("Context is null");
        }
    }
}
