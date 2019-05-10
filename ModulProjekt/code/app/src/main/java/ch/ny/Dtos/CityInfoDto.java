package ch.ny.homeactivity;

public class CityInfo {

    public WeatherInfo weatherInfo;
    public WeatherInfoMain weatherInfoMain;

    public CityInfo(WeatherInfo weatherInfo, WeatherInfoMain weatherInfoMain) {
        this.weatherInfo = weatherInfo;
        this.weatherInfoMain = weatherInfoMain;
    }

}
