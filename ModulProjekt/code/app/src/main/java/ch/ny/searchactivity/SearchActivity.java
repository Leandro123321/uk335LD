package ch.ny.searchactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ch.ny.Dtos.CityInfoDto;
import ch.ny.Entity.City;
import ch.ny.connections.AppDatabase;
import ch.ny.dao.CityDao;
import ch.ny.detailsactivity.DetailsActivity;
import ch.ny.homeactivity.R;

public class SearchActivity extends AppCompatActivity {

    private CityDao cityDao;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.searchcity);

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
    }

    public void onClickOpenDetails(View sendButton) {
        TextView cityLabel = findViewById(R.id.textview_itemname_listview);
        String name = cityLabel.getText().toString();
        Intent showNameActivityIntent = new Intent(this, DetailsActivity.class);
        showNameActivityIntent.putExtra("key_city_name", name);
        startActivity(showNameActivityIntent);
    }

    public void UpdateListView(String text){
        List<ListViewObject> itemList = new ArrayList<>();

        List<City> possibleCities = cityDao.getAllStartingWith(text);

        for (int i = 0; i < possibleCities.size(); i++) {
            ListViewObject item1 = new ListViewObject();

            City city = possibleCities.get(i);

            item1.setCityName(city.name);
            item1.setCountry(Integer.toString(i+1));
            itemList.add(item1);
        }

        ListView listView = findViewById(R.id.listCities);
        listView.setAdapter(new ListViewAdapter(this, itemList));
    }
}
