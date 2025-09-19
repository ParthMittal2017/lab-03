package com.example.listycity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // screen widgets
    private ListView cityList;              // the list that shows cities
    private ArrayList<City> cityData;       // holds the City objects name and province
    private CustomList cityAdapter;         // custom adapter for our list
    // top bar buttons
    private Button btnAdd, btnRemove;

    // remember which item got clicked
    private int currentIndex = -1;  // -1 means nothing picked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // load my layout

        // link xml stuff to code
        cityList   = findViewById(R.id.list_cities);
        btnAdd     = findViewById(R.id.btn_addCity);
        btnRemove  = findViewById(R.id.btn_removeCity);

        // put some default cities so list is not empty
        City[] starting = {
                new City("Vancouver", "BC"),
                new City("Ottawa", "ON")
        };
        cityData = new ArrayList<>(Arrays.asList(starting));
        // hook up adapter to show cityData in list
        cityAdapter = new CustomList(this, cityData);
        cityList.setAdapter(cityAdapter);
        cityList.setChoiceMode(ListView.CHOICE_MODE_SINGLE); // allow only 1 select

        // when you tap a city it open dialog to edit
        cityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                currentIndex = pos;
                cityList.setItemChecked(pos, true);
                City chosenCity = cityData.get(pos);
                EditCityFragment dialog = EditCityFragment.newInstance(chosenCity);
                dialog.show(getSupportFragmentManager(), "EditCityFragment");
            }
        });

        // add button open dialog with empty fields
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCityFragment dialog = EditCityFragment.newInstance();
                dialog.show(getSupportFragmentManager(), "AddCityFragment");
            }
        });

        // remove button deletes selected city
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentIndex < 0 || currentIndex >= cityData.size()) {
                    Toast.makeText(MainActivity.this, "Pick a city first!", Toast.LENGTH_SHORT).show();
                    return;
                }
                cityData.remove(currentIndex);        // delete from list
                cityAdapter.notifyDataSetChanged();   // refresh
                cityList.clearChoices();              // unselect
                currentIndex = -1;                    // reset pointer
            }
        });
    }
    // called when adding a new city from dialog
    public void addNewCity(City city) {
        cityData.add(city);
    }
    // called when editing existing city and need refresh
    public void refreshCities() {
        cityAdapter.notifyDataSetChanged();
    }
}
