package ch.ny.searchactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ch.ny.Dtos.CityInfoDto;
import ch.ny.Entity.City;
import ch.ny.connections.AppDatabase;
import ch.ny.connections.OkClientFactory;
import ch.ny.dao.CityDao;
import ch.ny.detailsactivity.DetailsActivity;
import ch.ny.homeactivity.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private CityDao cityDao;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.searchcity);

        ListView listview = findViewById(R.id.listCities);

        cityDao = AppDatabase.getAppDb(getApplicationContext()).getCityDao();

        UpdateListView("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                UpdateListView(newText);
                return false;
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewObject object = (ListViewObject) parent.getItemAtPosition(position);
                cityDao.UpdateFavorite(object.getId());

                try {
                    getHttpResponse(object);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    // Call API
    public void getHttpResponse(ListViewObject oldCityInfo) throws IOException{
        final ListViewObject object = oldCityInfo;

        String url = "https://api.openweathermap.org/data/2.5/weather?id="+object.getId()+"&units=metric&appid=77078c41435ef3379462eb28afbdf417";

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        OkHttpClient client = OkClientFactory.getClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                System.out.println(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();

                Gson gson = new Gson();
                CityInfoDto cityInfo = gson.fromJson(body, CityInfoDto.class);

                Intent showNameActivityIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                showNameActivityIntent.putExtra("key_city_id", object.getId());
                showNameActivityIntent.putExtra("key_city_name", object.getCityName());
                Log.e("TEMP", Float.toString(cityInfo.main.temp));
                showNameActivityIntent.putExtra("key_city_temp", cityInfo.main.temp);
                showNameActivityIntent.putExtra("key_city_status", cityInfo.weather[0].main);
                startActivity(showNameActivityIntent);
            }
        });
    }

    public void UpdateListView(String text){
        List<ListViewObject> itemList = new ArrayList<>();

        List<City> possibleCities = cityDao.getAllStartingWith(text);

        for (int i = 0; i < possibleCities.size(); i++) {
            ListViewObject item = new ListViewObject();

            City city = possibleCities.get(i);

            item.setId(city.id);
            item.setCityName(city.name);
            item.setCountry(city.country);
            itemList.add(item);
        }

        ListView listView = findViewById(R.id.listCities);
        listView.setAdapter(new ListViewAdapter(this, itemList));
    }
}
