package ch.ny.homeactivity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

        List<String> cityNames = new LinkedList();
        cityNames.add("London");
        cityNames.add("Zurich");
        cityNames.add("Lisbon");

        viewPager = findViewById(R.id.pager);
        cityCollectionPagerAdapter = new CityCollectionPageAdapter(getSupportFragmentManager(), cityNames);
        viewPager.setAdapter(cityCollectionPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(viewPager, true);
    }
}
