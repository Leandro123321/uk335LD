package ch.ny.homeactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class CityCollectionPageAdapter extends FragmentStatePagerAdapter {

    public CityCollectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    // Change parameter type to our later created objects
    public Fragment getItem(int i) {
        return new CityFragment();
    }

    @Override
    // Change this object to later have a collection of CityObjects and return the size of it
    public int getCount() {
        return 5;
    }
}
