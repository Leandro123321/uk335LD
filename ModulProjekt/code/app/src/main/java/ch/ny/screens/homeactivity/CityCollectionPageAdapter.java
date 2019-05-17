package ch.ny.screens.homeactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import ch.ny.persistence.entity.City;

/**
 * This Class serves as the "interface" between each collection member that will be
 * displayed and it also holds the city collection
 */
public class CityCollectionPageAdapter extends FragmentStatePagerAdapter {

    private List<City> cityList;

    public CityCollectionPageAdapter(FragmentManager fm, List<City> cityList) {
        super(fm);
        this.cityList = cityList;
    }

    @Override
    public Fragment getItem(int i) {
        if(cityList != null && cityList.size() > 0) {

            CityFragment fragment = new CityFragment();

            Bundle bundle = new Bundle();
            bundle.putInt("key_city_id", cityList.get(i).getId());
            bundle.putString("key_city_name", cityList.get(i).getName());
            bundle.putInt("key_city_temperature", cityList.get(i).getTemperature());
            bundle.putString("key_city_status", cityList.get(i).getStatus());

            fragment.setArguments(bundle);

            return fragment;
        } else {
            return null;
        }
    }

    @Override
    // Change this object to later have a collection of CityObjects and return the size of it
    public int getCount() {
        return cityList.size();
    }
}
