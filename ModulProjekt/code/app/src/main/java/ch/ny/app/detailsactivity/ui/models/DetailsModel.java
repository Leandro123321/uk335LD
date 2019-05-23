package ch.ny.app.detailsactivity.ui.models;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import ch.ny.network.dtos.CityInfoDto;

public class DetailsModel {

    public List<WeeklyListViewObject> sortDataFromJson(List<CityInfoDto> weeklyInfo) {

        List<WeeklyListViewObject> weeklyListViewObjectList = new ArrayList<>();

        List<CityInfoDto> citiesUnsorted = weeklyInfo;
        List<CityInfoDto> citiesSorted = new ArrayList<>();

        // Reset all time values to midnight from the same day
        for (int i = 0; i < citiesUnsorted.size(); i++) {
            CityInfoDto city = citiesUnsorted.get(i);

            city.time = Instant.ofEpochMilli(city.dt * 1000).atZone(ZoneId.systemDefault()).toLocalDate().atStartOfDay();
        }

        // Sort cities to only have one entry for each day and get the average for min and max
        for (int i = 0; i < citiesUnsorted.size() - 1; i++) {
            CityInfoDto city = citiesUnsorted.get(i);
            CityInfoDto city2 = citiesUnsorted.get(i + 1);

            if (city.time.isEqual(citiesUnsorted.get(i + 1).time)) {
                city2.main.temp_min = city.main.temp_min < city2.main.temp_min ? city.main.temp_min : city2.main.temp_min;
                city2.main.temp_max = city.main.temp_max > city2.main.temp_max ? city.main.temp_max : city2.main.temp_max;
            } else {
                citiesSorted.add(city);
            }
        }

        // Add sorted cities to our display array
        for (int i = 0; i < citiesSorted.size(); i++) {
            CityInfoDto city = citiesSorted.get(i);

            WeeklyListViewObject item = new WeeklyListViewObject();

            String dayOfWeek = Instant.ofEpochMilli(city.dt * 1000).atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek().toString();
            item.setWeekday(dayOfWeek);
            item.setMinTemp(Integer.toString(Math.round(city.main.temp_min)));
            item.setMaxTemp(Integer.toString(Math.round(city.main.temp_max)));
            weeklyListViewObjectList.add(item);
        }

        return weeklyListViewObjectList;
    }

}
