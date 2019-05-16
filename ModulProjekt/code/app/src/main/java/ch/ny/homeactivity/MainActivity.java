package ch.ny.homeactivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import ch.ny.Dtos.CityInfoDto;
import ch.ny.Entity.City;
import ch.ny.connections.AppDatabase;
import ch.ny.dao.CityDao;
import ch.ny.detailsactivity.DetailsActivity;
import ch.ny.searchactivity.SearchActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    CityDao cityDao;

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection
    CityCollectionPageAdapter cityCollectionPagerAdapter;
    ViewPager viewPager;

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

    public void onClickAdd(View v){
        startActivity(new Intent(this, SearchActivity.class));
    }

    public List<City> loadCities() {
        List<City> list;

        String json = null;
        try {
            InputStream is = getAssets().open("city.list.json");

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);
            is.close();

            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        list = gson.fromJson(json, new TypeToken<LinkedList<City>>(){}.getType());

        return list;
    }
}
