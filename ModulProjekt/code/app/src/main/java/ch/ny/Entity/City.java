package ch.ny.Entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;

@Entity
public class City {

    //Autoincrement
    @PrimaryKey
    public int id;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public String country;

    @ColumnInfo
    public int temperature;

    @ColumnInfo
    public String status;

    @ColumnInfo
    public boolean isFavorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFavorite() { return this.isFavorite; }

    public void setFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }

    public String getCountry() { return this.country; }

    public void setCountry(String country) { this.country = country; }


}
