package ch.ny.Dtos;

public class CityInfoDto {

    public int id;
    public String name;
    public String country;
    public WeatherInfoDto[] weather;
    public WeatherInfoMainDto main;

    public CityInfoDto(int id, String name, String country, WeatherInfoDto[] weather, WeatherInfoMainDto main) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.weather = weather;
        this.main = main;
    }

}
