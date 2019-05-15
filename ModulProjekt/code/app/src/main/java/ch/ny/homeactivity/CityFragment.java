package ch.ny.homeactivity;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import com.google.gson.Gson;

import ch.ny.Entity.City;
import ch.ny.connections.AppDatabase;
import ch.ny.dao.CityDao;
import ch.ny.detailsactivity.DetailsActivity;


import java.io.IOException;

import ch.ny.Dtos.CityInfoDto;
import ch.ny.connections.OkClientFactory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 * Instances of this class are fragments representing a single
 * object in our collection
 */
public class CityFragment extends Fragment implements View.OnClickListener {

    private OkHttpClient client;

    private TextView cityNameLbl;
    private TextView temperatureLbl;
    private ConstraintLayout background;

    private City city;
    private CityDao cityDao;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        city = new City();
        cityDao = AppDatabase.getAppDb(getActivity().getApplicationContext()).getCityDao();

        city.setCityname(getArguments().getString("key_city_name"));
        city.setTemperature(getArguments().getInt("key_city_temperature"));
        city.setStatus(getArguments().getString("key_city_status"));

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_city, container, false);

        TextView TemperatureLabel = v.findViewById(R.id.lbl_Temperatur);

        TemperatureLabel.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v){
        Intent showDetailActivityIntent = new Intent(this.getActivity(), DetailsActivity.class);
        showDetailActivityIntent.putExtra("key_city_name", city.getCityname());
        startActivity(showDetailActivityIntent);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cityNameLbl = view.findViewById(R.id.lbl_City);
        temperatureLbl = view.findViewById(R.id.lbl_Temperatur);
        background = view.findViewById(R.id.background);

        //Set Background
        //setBackground();

        client = OkClientFactory.getClient();
        try {
            getHttpResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setBackground() {
        switch(city.getStatus()) {
            case "sunny":
              background.setBackground(Drawable.createFromPath("/drawable/sunny_day.jpg"));
              break;
            default:
                background.setBackground(Drawable.createFromPath("/drawable/neutral_background.jpg"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        cityDao.insertOne(city);
    }

    // Call API
    public void getHttpResponse() throws IOException{
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+city.getCityname()+"&units=metric&appid=77078c41435ef3379462eb28afbdf417";

        Request request = new Request.Builder()
                .url(url)
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String message = e.getMessage();
                System.out.println(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();

                Gson gson = new Gson();
                CityInfoDto cityInfo = gson.fromJson(body, CityInfoDto.class);

                // Update all changed values
                setNewValues(Math.round(cityInfo.main.temp), cityInfo.weather[0].main);
            }
        });
    }

    private void setNewValues(int temperature, String status) {
        city.setTemperature(temperature);
        city.setStatus(status);

        final String temperatureString = temperature + "°C";

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                cityNameLbl.setText(city.getCityname());
                temperatureLbl.setText(temperatureString);
            }
        });
    }
}