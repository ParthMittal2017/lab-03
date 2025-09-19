package com.example.listycity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    private City targetCity;     // used if we are editing
    private boolean editingMode; // true = edit and false = add

    // make a fragment for editing an existing city
    public static EditCityFragment newInstance(City c) {
        EditCityFragment frag = new EditCityFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("city", c);
        bundle.putBoolean("editFlag", true);
        frag.setArguments(bundle);
        return frag;
    }
    // make a fragment for adding a new city
    public static EditCityFragment newInstance() {
        EditCityFragment frag = new EditCityFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("editFlag", false);
        frag.setArguments(bundle);
        return frag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.add_edit_city, null);

        // connect to the edit fields from xml
        EditText nameField = root.findViewById(R.id.input_cityName);
        EditText provinceField = root.findViewById(R.id.input_province);

        if (getArguments() != null) {
            editingMode = getArguments().getBoolean("editFlag");
            if (editingMode) {
                targetCity = (City) getArguments().getSerializable("city");
                if (targetCity != null) {
                    // pre-fill with old values
                    nameField.setText(targetCity.getName());
                    provinceField.setText(targetCity.getProvince());
                }
            }
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder.setView(root)
                .setTitle(editingMode ? "Edit City" : "Add City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Save", (dialog, which) -> {
                    String typedName = nameField.getText().toString().trim();
                    String typedProvince = provinceField.getText().toString().trim();
                    if (typedName.isEmpty() || typedProvince.isEmpty()) return;
                    if (editingMode && targetCity != null) {
                        // update the selected city
                        targetCity.setName(typedName);
                        targetCity.setProvince(typedProvince);
                    } else {
                        // add brand new city
                        ((MainActivity) getActivity()).addNewCity(new City(typedName, typedProvince));
                    }

                    // refresh the adapter so changes show up
                    ((MainActivity) getActivity()).refreshCities();
                }).create();
    }
}
