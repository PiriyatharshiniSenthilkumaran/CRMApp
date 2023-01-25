package com.androidprojects.crmapp.dialogfragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.utility.Utility;

import java.util.Objects;

/**
 * Fragment to filter customer details
 * Status is used to demonstrate how filtering can be handled
 */
public class FiltersDialogFragment extends DialogFragment {



    CheckBox statusActiveCheckbox;
    CheckBox statusNonActiveCheckbox;
    CheckBox statusLeadCheckbox;

    Button okButton;

    /**
     *
     * @return
     */
    public static FiltersDialogFragment newInstance() {
        return new FiltersDialogFragment();
    }

    /**
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filters_dialog, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    /**
     *
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_filters_dialog, null);
        dialogBuilder.setView(dialogView);

        AlertDialog alertDialog = dialogBuilder.create();
        //  alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.);


        statusActiveCheckbox = dialogView.findViewById(R.id.status_active_checkbox);
        statusNonActiveCheckbox = dialogView.findViewById(R.id.status_non_active_checkbox);
        statusLeadCheckbox = dialogView.findViewById(R.id.status_lead_checkbox);

        okButton = dialogView.findViewById(R.id.ok_button);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuilder result=new StringBuilder();
                result.append("?");

                /**
                 * Different possibilities of user selection for 3 different status and multiple combinations
                 */
                if(!statusActiveCheckbox.isChecked() && !statusNonActiveCheckbox.isChecked() && !statusLeadCheckbox.isChecked()){
                    showAlertDialog();
                }
                else if(statusActiveCheckbox.isChecked() && !statusNonActiveCheckbox.isChecked() && !statusLeadCheckbox.isChecked()){
                    result.append("status=active");

                }

                else if(!statusActiveCheckbox.isChecked()&& statusNonActiveCheckbox.isChecked() && !statusLeadCheckbox.isChecked()){
                    result.append("status=non-active");

                }
                else if(!statusActiveCheckbox.isChecked()&& !statusNonActiveCheckbox.isChecked() && statusLeadCheckbox.isChecked()){
                    result.append("status=lead");

                }
                else if(statusActiveCheckbox.isChecked()&& statusNonActiveCheckbox.isChecked() && !statusLeadCheckbox.isChecked()){
                    result.append("status=active&&status==non-active");

                }

                else if(statusActiveCheckbox.isChecked()&& !statusNonActiveCheckbox.isChecked() && statusLeadCheckbox.isChecked()){
                    result.append("status=active&&status==lead");

                }

                else if(!statusActiveCheckbox.isChecked()&& statusNonActiveCheckbox.isChecked() && statusLeadCheckbox.isChecked()){
                    result.append("status=non-active&&status=lead");

                }

                else if (statusActiveCheckbox.isChecked()&& statusNonActiveCheckbox.isChecked() && statusLeadCheckbox.isChecked()){
                    result.append("status=active&&status=non-active&&status=lead");

                }


                String appendedString = String.valueOf(result);

                /**
                 * if none of the checkbox is selected
                 */
                if(appendedString.equals("?")){
                    showAlertDialog();
                }else{

                    /**
                     * save changes
                     * To notify the hostactivity that this fragment is created
                     */

                    Bundle filterBundle = new Bundle();
                    filterBundle.putString("appendedString", appendedString);
                    getParentFragmentManager().setFragmentResult(Utility.FILTER_REQUEST_KEY, filterBundle);


                    alertDialog.dismiss();
                }

            }
        });
        return alertDialog;
    }

    /**
     * show AlertDialog when the user had not selected any of the status
     */
    public void showAlertDialog(){
        AlertDialog alertDialog = new AlertDialog.Builder(this.getContext()).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("You have not selected any filters.");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) Objects.requireNonNull(getDialog())).getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.teal_200));

    }


}