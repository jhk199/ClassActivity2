package com.example.classparticipation2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {

    private TextView textView_cityname;
    private TextView textView_city;
    private String receivedMessage;

    private static final String api_base = "https://api.openweathermap.org/data/2.5/";
    private static final String key = "66a1d1b4d788ac6486816cb5baa8b558";
    private static AsyncHttpClient client = new AsyncHttpClient();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        receivedMessage = intent.getStringExtra("city name");
        Log.d("City from main activity", receivedMessage);

        textView_city = findViewById(R.id.textView_city);
        textView_cityname = findViewById(R.id.textView_cityname);

        printAPI();

    }

    public void printAPI() {
        String api_url = api_base + "weather?q=" + receivedMessage + "&appid=" + key + "&units=imperial";
        Log.d("thisurl", api_url);
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    // name and country
                    String name = json.getString("name") + ", " +
                            json.getJSONObject("sys").getString("country");
                    textView_cityname.setText(name);

                    // since weather is stored in an array, you have to do this to access it
                    JSONArray arr = json.getJSONArray("weather");
                    // very annoying
                    String weatherDesc = arr.getJSONObject(0).getString("description");
                    // set high, low, and feels like
                    String high = "\nhigh: " + json.getJSONObject("main").getString("temp_max");
                    String low = "\nlow: " + json.getJSONObject("main").getString("temp_min");
                    String feels_like = "\nfeels like: " + json.getJSONObject("main").getString("feels_like");
                    // final result
                    String result = weatherDesc + high + low + feels_like;
                    // put it to text view
                    textView_city.setText(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                textView_cityname.setText("No city found");
            }
        });
    }
}
