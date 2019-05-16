package ch.ny.searchactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

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
                Intent showNameActivityIntent = new Intent(getApplicationContext(), DetailsActivity.class);
                showNameActivityIntent.putExtra("key_city_id", object.getId());
                showNameActivityIntent.putExtra("key_city_name", object.getCityName());
                startActivity(showNameActivityIntent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
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
