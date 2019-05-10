package ch.ny.homeactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class CityCollectionPageAdapter extends FragmentStatePagerAdapter {

    private List<String> cityNames;

    public CityCollectionPageAdapter(FragmentManager fm, List<String> cityNames) {
        super(fm);
        this.cityNames = cityNames;
    }

    @Override
    // Change parameter type to our later created objects
    public Fragment getItem(int i) {
        CityFragment fragment = new CityFragment();

        Bundle bundle = new Bundle();
        bundle.putString("key_city_name" , cityNames.get(i));

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    // Change this object to later have a collection of CityObjects and return the size of it
    public int getCount() {
        return cityNames.size();
    }
}
