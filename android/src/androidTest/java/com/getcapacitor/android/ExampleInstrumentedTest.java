package com.getcapacitor.android;

import static org.junit.Assert.*;

import android.content.Context;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.util.Log;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.getcapacitor.android", appContext.getPackageName());
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
