package ch.ny.Dtos;

public class CityInfoDto {

    public WeatherInfoDto[] weather;
    public WeatherInfoMainDto main;

    public CityInfoDto(WeatherInfoDto[] weather, WeatherInfoMainDto main) {
        this.weather = weather;
        this.main = main;
    }

}
