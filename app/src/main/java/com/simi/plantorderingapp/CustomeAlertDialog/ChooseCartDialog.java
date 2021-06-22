package com.simi.plantorderingapp.CustomeAlertDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;


import com.simi.plantorderingapp.R;

import java.util.ArrayList;

public class ChooseCartDialog extends AppCompatDialogFragment {
    ChooseCategory listener;
    Spinner spin;

    String choose="";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder profileDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.choose_cart_input_dialog_layout, null);

        profileDialog.setView(view)
                .setTitle("Choose among categories")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Go to cart section", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.applyChooseCategorysFields(choose);
                    }
                });

        spin= view.findViewById(R.id.spinner_id);

        ArrayList<String> spinnerData= new ArrayList<>();

        spinnerData.add(0, "Choose among the categories listed below");

        spinnerData.add(1,"Plants");
        spinnerData.add(2,"Seeds");
        spinnerData.add(3,"Summer Plants");
        spinnerData.add(4,"Winter Plants");
        spinnerData.add(5,"All Season Plants");
        spinnerData.add(6,"Gardening Tools");
        spinnerData.add(7,"Medicinal Plants");
        spinnerData.add(8,"Vegetable");
        spinnerData.add(9,"Soil And Fertilizer");

        ArrayAdapter<String> adp = new ArrayAdapter<String> (getActivity(), android.R.layout.simple_spinner_dropdown_item,spinnerData);
        // APP CURRENTLY CRASHING HERE
        spin.setAdapter(adp);
        //Set listener Called when the item is selected in spinner
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                choose = "You chose: " + parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), choose, Toast.LENGTH_LONG).show();
                if (!choose.contains("Choose among the referenceIds")) {
                    choose= choose.replace("You chose:","").trim();
                }
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                // TODO Auto-generated method stub
            }
        });

        System.out.println("From UpdateJobFromCompanyData spinnerData is: " + spinnerData);

        return profileDialog.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChooseCategory) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Must implement this Doctor_profile_Listener");
        }
    }

    public interface ChooseCategory {
        public void applyChooseCategorysFields(String choose);
    }
}