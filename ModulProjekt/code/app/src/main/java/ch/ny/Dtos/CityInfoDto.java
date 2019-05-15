package ch.ny.Dtos;

import java.sql.Timestamp;

public class CityInfoDto {

    public WeatherInfoDto[] weather;
    public WeatherInfoMainDto main;
    public long dt;
    public Timestamp time;

    public CityInfoDto(WeatherInfoDto[] weather, WeatherInfoMainDto main, long dt) {
        this.weather = weather;
        this.main = main;
        this.dt = dt;
    }

}
