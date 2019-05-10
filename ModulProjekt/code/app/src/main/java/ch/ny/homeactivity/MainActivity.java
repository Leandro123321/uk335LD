package ch.ny.homeactivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import ch.ny.Entity.City;
import ch.ny.connections.AppDatabase;
import ch.ny.dao.CityDao;
import ch.ny.detailsactivity.DetailsActivity;
import ch.ny.searchactivity.SearchActivity;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // When requested, this adapter returns a DemoObjectFragment,
    // representing an object in the collection
    CityCollectionPageAdapter cityCollectionPagerAdapter;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CityDao cityDao = AppDatabase.getAppDb(getApplicationContext()).getCityDao();
        List<City> cityList = cityDao.getAll();

        // In case you wan't to try the home screen and didn't save anything in the database
        // comment out the following code
        /*
        City london = new City();
        london.setCityname("London");
        london.setTemperature(14);
        london.setStatus("cloudy");

        City lisbon = new City();
        lisbon.setCityname("Lisbon");
        lisbon.setTemperature(18);
        lisbon.setStatus("sunny");

        City zurich = new City();
        zurich.setCityname("Zurich");
        zurich.setTemperature(14);
        zurich.setStatus("sunny");

        cityList.add(london);
        cityList.add(lisbon);
        cityList.add(zurich);
        */

        if(cityList != null && cityList.size() > 0) {
            viewPager = findViewById(R.id.pager);
            cityCollectionPagerAdapter = new CityCollectionPageAdapter(getSupportFragmentManager(), cityList);
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
}
