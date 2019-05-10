package ch.ny.Entity;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class City {

    //Autoincrement
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo
    public String Cityname;

    @ColumnInfo
    public int temperature;

    @ColumnInfo
    public String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityname() {
        return Cityname;
    }

    public void setCityname(String cityname) {
        Cityname = cityname;
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
}
