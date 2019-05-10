package ch.ny.homeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;
import com.google.gson.Gson;

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

    private String temperature;
    private String cityName;

    public CityFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        cityName = getArguments().getString("key_city_name");

        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_city, container, false);

        TextView TemperatureLabel = v.findViewById(R.id.lbl_Temperatur);

        TemperatureLabel.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v){
        TextView cityLabel = getActivity().findViewById(R.id.lbl_City);
        String city = cityLabel.getText().toString();
        Intent showDetailActivityIntent = new Intent(this.getActivity(), DetailsActivity.class);
        showDetailActivityIntent.putExtra("key_city_name", city);
        startActivity(showDetailActivityIntent);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        cityNameLbl = view.findViewById(R.id.lbl_City);
        temperatureLbl = view.findViewById(R.id.lbl_Temperatur);

        client = OkClientFactory.getClient();

        try {
            getHttpResponse();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    // Call API
    public void getHttpResponse() throws IOException{
        String url = "https://api.openweathermap.org/data/2.5/weather?q="+cityName+"&units=metric&appid=77078c41435ef3379462eb28afbdf417";

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

                setNewValues(cityInfo);
            }
        });
    }

    private void setNewValues(CityInfoDto cityInfo) {
        temperature = Integer.toString(Math.round(cityInfo.main.temp));

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                cityNameLbl.setText(cityName);
                temperatureLbl.setText(temperature + "°C");
            }
        });
    }
}