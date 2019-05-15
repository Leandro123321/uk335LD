package ch.ny.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import ch.ny.Entity.City;

@Dao
public interface CityDao {
    @Query("SELECT * FROM city LIMIT 10")
    List<City> getAll();

    @Query("SELECT DISTINCT * FROM city WHERE city.name LIKE :text || '%' LIMIT 10")
    List<City> getAllStartingWith(String text);

    @Query("DELETE FROM city")
    void deleteAll();

    @Insert
    void insertAll(List<City> cities);

    @Insert
    void insertOne(City city);
}
