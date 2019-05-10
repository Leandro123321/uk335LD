package ch.ny.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.ny.Entity.City;

@Dao
public interface CityDao {
    @Query("SELECT * FROM city")
    List<City> getAll();

    @Query("DELETE FROM city")
    void deleteAll();

    @Insert
    void insertAll(List<City> cities);
}
