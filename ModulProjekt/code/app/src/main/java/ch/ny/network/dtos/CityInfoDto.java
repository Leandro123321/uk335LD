package ch.ny.network.dtos;

import java.time.LocalDateTime;

public class CityInfoDto {

    public final WeatherInfoDto[] weather;
    public final WeatherInfoMainDto main;
    public final long dt;
    public LocalDateTime time;

    public CityInfoDto(WeatherInfoDto[] weather, WeatherInfoMainDto main, long dt) {
        this.weather = weather;
        this.main = main;
        this.dt = dt;
    }

}
