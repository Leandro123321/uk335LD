package ch.ny.detailsactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ch.ny.Dtos.CityInfoDto;
import ch.ny.Entity.City;
import ch.ny.connections.OkClientFactory;
import ch.ny.homeactivity.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DetailsActivity extends AppCompatActivity {

    private OkHttpClient client;
    private City city;

    private List<CityInfoDto> hourlyInfo;
    private List<CityInfoDto> weeklyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();

        city = new City();

        String cityName = intent.getStringExtra("key_city_name");
        city.setName(cityName);
        int cityId = intent.getIntExtra("key_city_id", 0);
        city.setId(cityId);

        TextView cityLabel = findViewById(R.id.textView);
        cityLabel.setText(city.getName());

        client = OkClientFactory.getClient();

        getHourlyForecast();
        getWeeklyForecast();
    }

    private void getHourlyForecast() {
        String url = "https://api.openweathermap.org/data/2.5/forecast/hourly?id="+city.getId()+"&units=metric&appid=77078c41435ef3379462eb28afbdf417";

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                System.out.println(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                String list = null;

                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONArray array = jsonObject.getJSONArray("list");
                    list = array.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                hourlyInfo = gson.fromJson(list, new TypeToken<List<CityInfoDto>>(){}.getType());
            }
        });
    }

    private void getWeeklyForecast() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?id="+city.getId()+"&units=metric&cnt=6&appid=77078c41435ef3379462eb28afbdf417";

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                System.out.println(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                String list = null;

                try {
                    JSONObject jsonObject = new JSONObject(body);
                    JSONArray array = jsonObject.getJSONArray("list");
                    list = array.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Gson gson = new Gson();
                hourlyInfo = gson.fromJson(list, new TypeToken<List<CityInfoDto>>(){}.getType());
            }
        });
    }
}
