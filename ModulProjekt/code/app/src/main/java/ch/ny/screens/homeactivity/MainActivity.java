package ch.ny.screens.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import ch.ny.persistence.entity.City;
import ch.ny.persistence.AppDatabase;
import ch.ny.persistence.dao.CityDao;
import ch.ny.screens.searchactivity.SearchActivity;

public class MainActivity extends AppCompatActivity {


    private CityDao cityDao;

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection
    private CityCollectionPageAdapter cityCollectionPagerAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cityDao = AppDatabase.getAppDb(getApplicationContext()).getCityDao();
        List<City> cityList = cityDao.getAll();

       if(cityList.isEmpty()) {
           cityList = loadCities();
           cityDao.insertAll(cityList);
       }
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<City> favorites = cityDao.getFavorites();
        if(!favorites.isEmpty()) {
            viewPager = findViewById(R.id.pager);
            cityCollectionPagerAdapter = new CityCollectionPageAdapter(getSupportFragmentManager(), favorites);
            viewPager.setAdapter(cityCollectionPagerAdapter);

            TabLayout tabLayout = findViewById(R.id.tabDots);
            tabLayout.setupWithViewPager(viewPager, true);
        } else {
            startActivity(new Intent(this, SearchActivity.class));
        }
    }

    /**
     * If you click the add button you will go the search activity
     * @param v
     */
    public void onClickAdd(View v){
        startActivity(new Intent(this, SearchActivity.class));
    }

    /**
     * Loads all cities from the JSON file and saves them in the Local Database
     * @return
     */
    private List<City> loadCities() {
        List<City> list;

        String json = null;
        try {
            InputStream is = getAssets().open("city.list.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<LinkedList<City>>(){}.getType());

        return list;
    }
}
