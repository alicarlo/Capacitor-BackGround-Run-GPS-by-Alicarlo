package io.backgroundrun.test1;

import android.os.AsyncTask;
import androidx.core.content.ContextCompat;
import java.net.URL;
import java.net.HttpURLConnection;
import org.json.JSONObject;
import java.io.OutputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.IOException;
import org.json.JSONException;

import android.util.Log;




public class CallEndpointService extends AsyncTask<Void, Void, Void> {
    private double lat;
    private double lng;
    private String date;
		private String urlSend;
		private String id1;
		private String id2;
		private String id3;
		private String id4;

		public CallEndpointService(double lat, double lng, String date, String url, String id1, String id2, String id3, String id4) {
        this.lat = lat;
        this.lng = lng;
        this.date = date;
				this.urlSend = url;
				this.id1 = id1;
				this.id2 = id2;
				this.id3 = id3;
				this.id4 = id4;
    }

		@Override
    protected Void doInBackground(Void... params) {
        insertLocation(lat, lng, date, urlSend, id1, id2, id3, id4);
        return null;
    }

		private void insertLocation(double lat, double lng, String date, String urlSend, String id1, String id2, String id3, String id4) {
        try {
						Log.e("BackgroundrunPlugin", "en el callEndpoint:"+urlSend);
            URL url = new URL(urlSend);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonBody = new JSONObject();
            jsonBody.put("lat", lat);
            jsonBody.put("lng", lng);
            jsonBody.put("date", date);
						jsonBody.put("id1", id1);
						jsonBody.put("id2", id2);
						jsonBody.put("id3", id3);
						jsonBody.put("id4", id4);

            OutputStream outputStream = urlConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(jsonBody.toString());
            writer.flush();
            writer.close();
            outputStream.close();

            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // La solicitud fue exitosa
                // Puedes leer la respuesta del servidor si es necesario
            }

            urlConnection.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

