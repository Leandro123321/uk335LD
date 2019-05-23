package ch.ny.app.detailsactivity.ui;

import com.google.gson.Gson;

import java.util.List;

import ch.ny.app.detailsactivity.ui.models.DetailsModel;
import ch.ny.app.detailsactivity.ui.models.WeeklyListViewObject;
import ch.ny.app.homeactivity.R;
import ch.ny.network.OkClientFactory;
import ch.ny.network.dtos.CityInfoDto;

public class DetailsActivityPresenter implements DetailsVP.Presenter {

    private DetailsVP.View view;
    private DetailsModel model;

    public void setView(DetailsVP.View view) {

        this.view = view;
        this.model = new DetailsModel();
    }

    public void getWeeklyInfo(String apiUrl) {

        if(!OkClientFactory.isClientAlive()) {
            OkClientFactory.initClient();
        }

        try {
            String result = OkClientFactory.performGet(apiUrl);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        List<CityInfoDto> weeklyInfoAsJson = gson.fromJson(result, new Type)

        List<WeeklyListViewObject> model.sortDataFromJson(result);
    }

    /**
     * Get the Icon Id for the respective weather status
     * @param status
     * @return
     */
    public int getIconId(String status) {

        switch (status) {
            case "Clear":
                return R.drawable.ic_wb_sunny_black_24dp;
            case "Rain":
            case "Drizzle":
            case "Thunderstorm":
            case "Snow":
                return R.drawable.ic_rainy_black_24dp;
            default:
                return R.drawable.ic_cloud_black_24dp;
        }
    }

}
