package com.androidprojects.crmapp.dialogfragments;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.models.Opportunities;

import java.util.ArrayList;
/**DialogFragment responsible for
 * adding new opportunities and
 * editing existing opportunities
 */
public class AddNewOpportunityDialogFragment extends DialogFragment {

    private AddNewOpportunityDialogViewModel addNewOpportunityDialogViewModel;

    EditText opportunityIdEditText;
    EditText opportunityNameEditText;
    Spinner opportunityStatusSpinner;
    Button addOpportunityButton;
    Button closeOpportunityButton;

    int customerId = 0;

    AlertDialog alertDialog;
    String status = "";

    boolean isEdit = false;

    ArrayList<Opportunities> opportunitiesArrayList = new ArrayList<>();


    public AddNewOpportunityDialogFragment() {

    }
    /**
     *
     * @param opportunitiesArrayList
     */
    public AddNewOpportunityDialogFragment(ArrayList<Opportunities> opportunitiesArrayList) {
        this.opportunitiesArrayList = opportunitiesArrayList;
    }



    /**
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        addNewOpportunityDialogViewModel = new ViewModelProvider(this).get(AddNewOpportunityDialogViewModel.class);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            customerId = bundle.getInt("id", 0);
        }

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_add_new_opportunity_dialog, null);
        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        opportunityIdEditText = dialogView.findViewById(R.id.opportunity_id_editText);
        opportunityNameEditText = dialogView.findViewById(R.id.opportunity_name_editText);
        opportunityStatusSpinner = dialogView.findViewById(R.id.opportunity_status_spinner);
        addOpportunityButton = dialogView.findViewById(R.id.save_opportunities_button);
        closeOpportunityButton = dialogView.findViewById(R.id.close_opportunities_button);

        //“New”, “Closed Won”, “Closed Lost”
        String[] opportunityItems = new String[]{"New", "Closed Won", "Closed Lost"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, opportunityItems);
        opportunityStatusSpinner.setAdapter(adapter);

        opportunityStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                status = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Please select a status", Toast.LENGTH_SHORT).show();
            }
        });


        if(opportunitiesArrayList!=null){
            if (opportunitiesArrayList.size() != 0) {

                isEdit = true;
                opportunityIdEditText.setEnabled(false);
                opportunityIdEditText.setText(String.valueOf(opportunitiesArrayList.get(0).getOpportunityId()));
                opportunityNameEditText.setText(opportunitiesArrayList.get(0).getOpportunityName());
                status = String.valueOf(opportunitiesArrayList.get(0).getOpportunityStatus());
                if (status.equals("New")) {
                    opportunityStatusSpinner.setSelection(0);
                } else if (status.equals("Closed Won")) {
                    opportunityStatusSpinner.setSelection(1);
                } else {
                    opportunityStatusSpinner.setSelection(2);
                }
            }
        }

        /**
         * button to add or update opportunities
         */

        addOpportunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /**
                 * validation - checks whether the user entered data is valid
                 */

                if(opportunityIdEditText.getText().toString().trim().equals("")){
                    opportunityIdEditText.setError("Please enter id");
                    opportunityIdEditText.requestFocus();
                }
                if(opportunityNameEditText.getText().toString().trim().equals("")){
                    opportunityNameEditText.setError("Please enter opportunity name");
                    opportunityNameEditText.requestFocus();
                }

                if(!opportunityIdEditText.getText().toString().trim().equals("")&&
                        !opportunityNameEditText.getText().toString().trim().equals("")&&
                        !status.trim().equals("")) {
                    /**
                     * validations are complete
                     */


                    int id = Integer.parseInt(String.valueOf(opportunityIdEditText.getText()));
                    String name = String.valueOf(opportunityNameEditText.getText());
                    Opportunities opportunities = new Opportunities(id, customerId, status, name);
                    if (isEdit) {
                        addNewOpportunityDialogViewModel.updateOpportunityDetail(opportunities);

                    } else {
                        addNewOpportunityDialogViewModel.addNewOpportunityDetail(opportunities);
                    }



                }
            }
        });

        /**
         * button to dismiss the dialog
         */

        closeOpportunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        return alertDialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addNewOpportunityDialogViewModel = new ViewModelProvider(this).get(AddNewOpportunityDialogViewModel.class);
        // TODO: Use the ViewModel
    }



}