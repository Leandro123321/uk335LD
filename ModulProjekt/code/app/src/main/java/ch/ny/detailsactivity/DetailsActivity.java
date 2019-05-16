package ch.ny.detailsactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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

    private RecyclerView hourlyView;
    private HourlyRecyclerViewAdapter recyclerViewAdapter;

    private ListView weeklyView;

    private List<HourlyListViewObject> hourlyListViewObjectList;
    private List<WeeklyListViewObject> weeklyListViewObjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        hourlyListViewObjectList = new ArrayList<>();
        weeklyListViewObjectList = new ArrayList<>();

        // recycler view adapter setup
        hourlyView = findViewById(R.id.recyclerHourly);
        // Add divider
        hourlyView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));
        // Setup adapter and layout manager
        recyclerViewAdapter = new HourlyRecyclerViewAdapter(hourlyListViewObjectList, getApplicationContext());
        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        hourlyView.setLayoutManager(horizontalLayoutManager);
        hourlyView.setAdapter(recyclerViewAdapter);

        // List view setup
        weeklyView = findViewById(R.id.listWeekly);

        Intent intent = getIntent();

        city = new City();

        String cityName = intent.getStringExtra("key_city_name");
        city.setName(cityName);
        TextView cityLabel = findViewById(R.id.lblTitle);
        cityLabel.setText(city.getName());

        String temp = Integer.toString(intent.getIntExtra("key_city_temp", 0));
        TextView mainTemp = findViewById(R.id.lblMainTemp);
        mainTemp.setText(temp + "°C");

        String status = intent.getStringExtra("key_city_status");
        Log.e("STATUS", status);
        ImageView statusIcon = findViewById(R.id.iconStatus);
        Log.e("ICON", statusIcon.toString());
        statusIcon.setImageResource(getIcon(status));

        int cityId = intent.getIntExtra("key_city_id", 0);
        city.setId(cityId);

        client = OkClientFactory.getClient();

        getHourlyForecast();
        getWeeklyForecast();
    }

    public static int getIcon(String status) {
        switch (status) {
            case "Clear":
                return R.drawable.ic_wb_sunny_black_24dp;
            case "Rain":
            case "Drizzle":
            case "Thunderstorm":
            case "Snow":
                return R.drawable.ic_rainy_black_24dp;
            default:
                return R.drawable.ic_cloud_black_24dp;
        }
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
                updateHourlyListView();
            }
        });
    }

    private void updateHourlyListView() {
        List<CityInfoDto> possibleCities = hourlyInfo;

        for (int i = 0; i < 24; i++) {
            HourlyListViewObject item = new HourlyListViewObject();

            CityInfoDto city = possibleCities.get(i);

            item.setTime(new SimpleDateFormat("HH").format(new Date(city.dt * 1000)));
            item.setStatus(city.weather[0].main);
            item.setTemp(Integer.toString(Math.round(city.main.temp)));
            hourlyListViewObjectList.add(item);
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerViewAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getWeeklyForecast() {
        String url = "https://api.openweathermap.org/data/2.5/forecast?id="+city.getId()+"&units=metric&appid=77078c41435ef3379462eb28afbdf417";

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
                weeklyInfo = gson.fromJson(list, new TypeToken<List<CityInfoDto>>(){}.getType());
                updateWeeklyListView();
            }
        });
    }

    private void updateWeeklyListView() {
        weeklyListViewObjectList = new ArrayList<>();

        List<CityInfoDto> citiesUnsorted = weeklyInfo;
        List<CityInfoDto> citiesSorted = new ArrayList<>();

        // Reset all time values to midnight from the same day
        for (int i = 0; i < citiesUnsorted.size(); i++) {
            CityInfoDto city = citiesUnsorted.get(i);

            LocalDateTime time =  Instant.ofEpochMilli(city.dt * 1000).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
            city.time = time;
        }

        // Sort cities to only have one entry for each day and get the average for min and max
        for(int i = 0; i < citiesUnsorted.size() - 1; i++) {
            CityInfoDto city = citiesUnsorted.get(i);
            CityInfoDto city2 = citiesUnsorted.get(i + 1);

            if(city.time.isEqual(citiesUnsorted.get(i+1).time)) {
                city2.main.temp_min = city.main.temp_min < city2.main.temp_min ? city.main.temp_min : city2.main.temp_min;
                city2.main.temp_max = city.main.temp_max > city2.main.temp_max ? city.main.temp_max : city2.main.temp_max;
            } else {
                citiesSorted.add(city);
            }
        }

        // Add sorted cities to our display array
        for(int i = 0; i < citiesSorted.size(); i++) {
            CityInfoDto city = citiesSorted.get(i);

            WeeklyListViewObject item = new WeeklyListViewObject();

            String dayOfWeek = Instant.ofEpochMilli(city.dt * 1000).atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().toString();
            item.setWeekday(dayOfWeek);
            item.setMinTemp(Integer.toString(Math.round(city.main.temp_min)));
            item.setMaxTemp(Integer.toString(Math.round(city.main.temp_max)));
            weeklyListViewObjectList.add(item);
        }

        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                weeklyView.setAdapter(new WeeklyListViewAdapter(getApplicationContext(), weeklyListViewObjectList));
            }
        });
    }
}
