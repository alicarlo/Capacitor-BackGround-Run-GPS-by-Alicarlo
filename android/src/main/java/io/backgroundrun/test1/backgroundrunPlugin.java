package io.backgroundrun.test1;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import android.content.Context;
import android.util.Log;

@CapacitorPlugin(name = "backgroundrun")
public class backgroundrunPlugin extends Plugin {

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
        Log.d(TAG, "showNotificationOnAppClose called");
        Context context = getContext();
        if (context != null) {
            showNotification(context);
            call.resolve();
        } else {
            call.reject("Context is null");
        }
    }

    private void showNotification(Context context) {
        // Crear una notificación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channel_id")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("¡Hasta luego!")
                .setContentText("La aplicación se ha cerrado.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Mostrar la notificación
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(1, builder.build());
    }
}
