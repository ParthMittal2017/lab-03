package com.example.listycity;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

/*
 * Adapter made by me to show both city and province in the list
 */
public class CustomList extends ArrayAdapter<City> {
    private final Activity myActivity;
    private final ArrayList<City> myCities;
    public CustomList(Activity activity, ArrayList<City> list) {
        super(activity, 0, list);
        this.myActivity = activity;
        this.myCities = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        // if row is not recycled yet then inflate it
        if (rowView == null) {
            LayoutInflater inflater = myActivity.getLayoutInflater();
            rowView = inflater.inflate(R.layout.content, null);
        }

        // grab the city object at this position
        City thisCity = myCities.get(position);

        // connect to the two labels inside content.xml
        TextView nameText = rowView.findViewById(R.id.label_city);
        TextView provText = rowView.findViewById(R.id.label_province);

        // set their text values
        nameText.setText(thisCity.getName());
        provText.setText(thisCity.getProvince());
        return rowView;
    }
}
