package ch.ny.screens.searchactivity;

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

import ch.ny.network.dtos.CityInfoDto;
import ch.ny.persistence.entity.City;
import ch.ny.persistence.AppDatabase;
import ch.ny.network.OkClientFactory;
import ch.ny.persistence.dao.CityDao;
import ch.ny.screens.detailsactivity.DetailsActivity;
import ch.ny.screens.homeactivity.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private CityDao cityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.searchcity);

        ListView listview = findViewById(R.id.listCities);

        cityDao = AppDatabase.getAppDb(getApplicationContext()).getCityDao();

        // update the list with the default order so it has information when you start this activity
        UpdateListView("");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            /**
             * This function is redundant after the onQueryTextChange is called
             * Although this object requires the method definition
             * @param query
             * @return
             */
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            /**
             * Update the listView with the entered search query text
             * @param newText
             * @return
             */
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

    /**
     * Avoids that this activity lands in the backstack
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    /**
     * Calls the api to get the current information of the newly added city (temperature and status)
     * @param oldCityInfo
     * @throws IOException
     */
    public void getHttpResponse(ListViewObject oldCityInfo) throws IOException {
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

            /**
             * Starts the details activity with the new information
             * @param call
             * @param response
             * @throws IOException
             */
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();

                Gson gson = new Gson();
                CityInfoDto cityInfo = gson.fromJson(body, CityInfoDto.class);

                Intent showNameActivityIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                showNameActivityIntent.putExtra("key_city_id", object.getId());
                showNameActivityIntent.putExtra("key_city_name", object.getCityName());
                Log.e("TEMP", Float.toString(cityInfo.main.temp));
                showNameActivityIntent.putExtra("key_city_temp", Math.round(cityInfo.main.temp));
                showNameActivityIntent.putExtra("key_city_status", cityInfo.weather[0].main);
                startActivity(showNameActivityIntent);
            }
        });
    }

    /**
     * Updates the 10 entries on our results list with text that
     * corresponds to the search entered by the user
     * @param text
     */
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
