package ch.ny.network.dtos;

public class WeatherInfoMainDto {
    public float temp;
    public float temp_min;
    public float temp_max;

    public WeatherInfoMainDto(float temp, float temp_min, float temp_max) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }
}
