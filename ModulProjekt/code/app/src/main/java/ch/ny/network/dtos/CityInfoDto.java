package ch.ny.dtos;

import java.time.LocalDateTime;

public class CityInfoDto {

    public WeatherInfoDto[] weather;
    public WeatherInfoMainDto main;
    public long dt;
    public LocalDateTime time;

    public CityInfoDto(WeatherInfoDto[] weather, WeatherInfoMainDto main, long dt) {
        this.weather = weather;
        this.main = main;
        this.dt = dt;
    }

}
