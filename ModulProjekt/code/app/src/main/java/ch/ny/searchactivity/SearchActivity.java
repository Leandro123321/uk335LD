package ch.ny.searchactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import ch.ny.detailsactivity.DetailsActivity;
import ch.ny.homeactivity.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.searchcity);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                UpdateListView(newText);
                return false;
            }
        });
    }

    public void onClickOpenDetails(View sendButton) {
        TextView cityLabel = findViewById(R.id.textview_itemname_listview);
        String name = cityLabel.getText().toString();
        Intent showNameActivityIntent = new Intent(this, DetailsActivity.class);
        showNameActivityIntent.putExtra("key_city_name", name);
        startActivity(showNameActivityIntent);
    }

    public void UpdateListView(String text){
        //Testdaten erstellen
        List<ListViewObject> itemList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ListViewObject item1 = new ListViewObject();
            item1.setItemName(text);
            item1.setItemNumber(i);
            itemList.add(item1);
        }

        ListView listView = findViewById(R.id.listCities);
        listView.setAdapter(new ListViewAdapter(this, itemList));
    }
}
