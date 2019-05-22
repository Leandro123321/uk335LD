package ch.ny.persistence;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.runner.AndroidJUnit4;

import androidx.test.core.app.ApplicationProvider;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ch.ny.persistence.AppDatabase;
import ch.ny.persistence.dao.CityDao;
import ch.ny.persistence.entity.City;

import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class SimpleEntityReadWriteTest {

    private CityDao cityDao;
    private AppDatabase db;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        cityDao = db.getCityDao();
    }

    @After
    public void closeDb() {
        db.close();
    }

    @Test
    public void writeUserAndReadInList() {
        City city = new City();

        city.name = "Lisbon";
        city.status = "Cloudy";
        city.country = "PT";
        city.isFavorite = true;

        cityDao.insertOne(city);
        List<City> favorites = cityDao.getFavorites();
        assertThat(favorites.get(0), Matchers.equalTo(city));
    }


}
