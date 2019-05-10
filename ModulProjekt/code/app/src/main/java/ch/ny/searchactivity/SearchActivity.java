package ch.ny.searchactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import ch.ny.homeactivity.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Testdaten erstellen
        List<ListViewObject> itemList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            ListViewObject item1 = new ListViewObject();
            item1.setItemName("Random Item");
            item1.setItemNumber(i);
            itemList.add(item1);
        }

        ListView listView = findViewById(R.id.listCities);
        listView.setAdapter(new ListViewAdapter(this, itemList));
    }
}
