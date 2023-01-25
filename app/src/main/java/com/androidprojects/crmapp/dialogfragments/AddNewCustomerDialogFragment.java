package com.androidprojects.crmapp.dialogfragments;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.androidprojects.crmapp.utility.Utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**DialogFragment responsible for
 * 1. adding new customer details and
 * 2. editing existing customer details
 */
public class AddNewCustomerDialogFragment extends DialogFragment {

    private AddNewCustomerDialogViewModel addNewCustomerDialogViewModel;

    EditText idEditText;
    EditText nameEditText;
    EditText emailEditText;
    EditText phoneEditText;
    Spinner statusSpinner;
    Button addButton;
    Button closeButton;

    String status = "";
    boolean isEdit = false;

    ArrayList<CustomerInformation> customerInformationArrayList = new ArrayList<>();

    public AddNewCustomerDialogFragment() {
    }

    public AddNewCustomerDialogFragment(ArrayList<CustomerInformation> customerInformationArrayList){
        this.customerInformationArrayList = customerInformationArrayList;
    }


    AlertDialog alertDialog;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addNewCustomerDialogViewModel = new ViewModelProvider(this).get(AddNewCustomerDialogViewModel.class);
        // TODO: Use the ViewModel
    }



    /**
     * method to implement functionalities inside the Dialog
     * @param savedInstanceState
     * @return
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        addNewCustomerDialogViewModel = new ViewModelProvider(this).get(AddNewCustomerDialogViewModel.class);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.fragment_add_new_customer_dialog, null);
        dialogBuilder.setView(dialogView);

        alertDialog = dialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        //  alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.);

        idEditText = dialogView.findViewById(R.id.id_editText);
        nameEditText = dialogView.findViewById(R.id.name_editText);
        emailEditText = dialogView.findViewById(R.id.email_editText);
        phoneEditText = dialogView.findViewById(R.id.phone_number_editText);
        statusSpinner = dialogView.findViewById(R.id.status_spinner);

        addButton = dialogView.findViewById(R.id.save_button);
        closeButton = dialogView.findViewById(R.id.close_button);

        phoneEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());



        /**
         * if customerInformationArrayList contains data, then proceed with
         * displaying details so that users will be able to edit when needed and
         * users can edit only the fields which needs to be edited
         */

        if(customerInformationArrayList.size()!=0){
            isEdit = true;
            idEditText.setEnabled(false);

            idEditText.setText(String.valueOf(customerInformationArrayList.get(0).getCustomerId()));
            nameEditText.setText(String.valueOf(customerInformationArrayList.get(0).getName()));
            emailEditText.setText(String.valueOf(customerInformationArrayList.get(0).getEmail()));
            phoneEditText.setText(String.valueOf(customerInformationArrayList.get(0).getPhoneNumber()));

            status = String.valueOf(customerInformationArrayList.get(0).getStatus());
            if(status.equals("active")) {
                statusSpinner.setSelection(0);
            }else if (status.equals("non-active")){
                statusSpinner.setSelection(1);
            }else{
                statusSpinner.setSelection(2);
            }

        }

        String[] items = new String[]{"active", "non-active", "lead"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        statusSpinner.setAdapter(adapter);

        /**
         * when an item is selected in the status spinner
         */

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                status = String.valueOf(adapterView.getItemAtPosition(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(getContext(), "Please select a status", Toast.LENGTH_SHORT).show();
            }
        });

/**
 * button to add or update customer details
 */


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/**
 * validation --- checks whether the user entered data is valid to proceed to be saved
 */

                if(idEditText.getText().toString().trim().equals("")){
                    idEditText.setError("Please enter id");
                    idEditText.requestFocus();
                }
                if(nameEditText.getText().toString().trim().equals("")){
                    nameEditText.setError("Please enter customer name");
                    nameEditText.requestFocus();
                }

                if(emailEditText.getText().toString().trim().equals("")){
                    emailEditText.setError("Please enter the email id");
                    emailEditText.requestFocus();
                }

                if(!Utility.isEmailValid(emailEditText.getText().toString())){
                    emailEditText.setError("Please enter a valid email id");
                    emailEditText.requestFocus();
                }

                if(phoneEditText.getText().toString().length() < 10 ){
                    phoneEditText.setError("Please enter valid phone number (consisting 10 digits)");
                    phoneEditText.requestFocus();
                }
                if(phoneEditText.getText().toString().trim().equals("")){
                    phoneEditText.setError("Please enter phone number");
                    phoneEditText.requestFocus();
                }

                if(!idEditText.getText().toString().trim().equals("")&&
                        !nameEditText.getText().toString().trim().equals("")&&
                        !phoneEditText.getText().toString().trim().equals("")&&
                        !emailEditText.getText().toString().trim().equals("")&&
                        Utility.isEmailValid(emailEditText.getText().toString())) {

                    /**
                     * validations are complete
                      */



                    int id = Integer.parseInt(String.valueOf(idEditText.getText()));
                    String name = String.valueOf(nameEditText.getText());
                    //Taking the current time for created time
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    String createdDate = sdf.format(new Date());
                    String email = String.valueOf(emailEditText.getText());
                    String phone = String.valueOf(phoneEditText.getText());
                    CustomerInformation customerInformation = new CustomerInformation(id, createdDate, name, email, phone, status);


                    /**Rather than having two layouts and two fragments to add new customer and
                    existing customer, same layouts and fragments are reused since they are modularized
                    if 1. isEdit is true then proceed to update existing customer details
                      else  2. add new customer details
                     */
                    if (isEdit) {
                        addNewCustomerDialogViewModel.updateCustomerDetail(customerInformation);
                    } else {
                        addNewCustomerDialogViewModel.addNewCustomerDetail(customerInformation);
                    }

                }
            }
        });


        /**
         * close button enables to close the alertDialog
         */

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.dismiss();

                Bundle closeBundle = new Bundle();
                closeBundle.putBoolean("dialogfragmentclosed", true);
                getParentFragmentManager().setFragmentResult(Utility.CLOSE_REQUEST_KEY, closeBundle);
            }
        });
        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        ((AlertDialog) Objects.requireNonNull(getDialog())).getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.teal_200));

    }



}