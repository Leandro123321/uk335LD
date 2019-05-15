package ch.ny.Dtos;

import java.time.LocalDateTime;

public class CityInfoDto {

    public WeatherInfoDto[] weather;
    public WeatherInfoMainDto main;
    public LocalDateTime dt;

    public CityInfoDto(WeatherInfoDto[] weather, WeatherInfoMainDto main, LocalDateTime dt) {
        this.weather = weather;
        this.main = main;
        this.dt = dt;
    }

}
