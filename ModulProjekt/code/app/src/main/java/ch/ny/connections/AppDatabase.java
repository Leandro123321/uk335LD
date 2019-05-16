package ch.ny.connections;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import ch.ny.Entity.City;
import ch.ny.dao.CityDao;

@Database(entities = City.class, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "db_weather_db";
    private static AppDatabase appDb;

    public static AppDatabase getAppDb(Context context){
        if (appDb == null){
            appDb = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return appDb;
    }

    public abstract CityDao getCityDao();
}
