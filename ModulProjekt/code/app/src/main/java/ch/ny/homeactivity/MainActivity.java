package ch.ny.homeactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import ch.ny.detailsactivity.DetailsActivity;
import ch.ny.searchactivity.SearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickOpenDetails(View v){
        TextView cityLabel = findViewById(R.id.lbl_City);
        String city = cityLabel.getText().toString();
        Intent showDetailActivityIntent = new Intent(this, DetailsActivity.class);
        showDetailActivityIntent.putExtra("key_city_name", city);
        startActivity(showDetailActivityIntent);
    }

    public void onClickAdd(View v){
        startActivity(new Intent(this, SearchActivity.class));
    }
}
