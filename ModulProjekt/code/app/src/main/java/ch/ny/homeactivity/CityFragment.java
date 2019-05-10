package ch.ny.homeactivity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Objects;

import ch.ny.detailsactivity.DetailsActivity;


/**
 * A simple {@link Fragment} subclass.
 * Instances of this class are fragments representing a single
 * object in our collection
 */
public class CityFragment extends Fragment implements View.OnClickListener {

    public CityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
}