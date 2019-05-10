package ch.ny.detailsactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ch.ny.homeactivity.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        Intent intent = getIntent();
        String City = intent.getStringExtra("key_city_name");
        TextView cityLabel = findViewById(R.id.textView);
        cityLabel.setText(City);
    }
}
