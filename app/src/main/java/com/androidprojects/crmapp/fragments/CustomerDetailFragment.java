package com.androidprojects.crmapp.fragments;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.dialogfragments.AddNewCustomerDialogFragment;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.utility.Utility;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CustomerDetailFragment extends Fragment {

    private CustomerDetailViewModel customerDetailsViewModel;
    int id;
    TextView idTextView;
    TextView nameTextView;
    TextView createdDateTextView;
    TextView emailTextView;
    TextView phoneTextView;
    TextView statusTextView;

    Button editButton;

    String status = "";

    ArrayList<CustomerInformation> customerInformationArrayListMain = new ArrayList<>();



    public static CustomerDetailFragment newInstance() {
        return new CustomerDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View customerDetailsView = inflater.inflate(R.layout.fragment_customer_detail, container, false);

        customerDetailsViewModel = new ViewModelProvider(this).get(CustomerDetailViewModel.class);


        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id", 0);
        }
        idTextView = customerDetailsView.findViewById(R.id.id_textview);
        nameTextView = customerDetailsView.findViewById(R.id.name_textview);
        createdDateTextView = customerDetailsView.findViewById(R.id.created_date_textview);
        emailTextView = customerDetailsView.findViewById(R.id.email_textview);
        phoneTextView = customerDetailsView.findViewById(R.id.phone_number_textview);
        statusTextView = customerDetailsView.findViewById(R.id.status_textview);

        editButton = customerDetailsView.findViewById(R.id.edit_customer_details_button);

        Log.v("ccppz", "id: " + id);
        customerDetailsViewModel.getSingleCustomerDetailsFromBackEnd(id);


        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<ArrayList<CustomerInformation>> customerInformationArrayList = customerDetailsViewModel.getSingleCustomerDetailArrayList();
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                AddNewCustomerDialogFragment addNewCustomerDialogFragment = new AddNewCustomerDialogFragment(customerInformationArrayListMain);

                addNewCustomerDialogFragment.show(fragmentTransaction, "addNew");
            }
        });


        getActivity().getSupportFragmentManager().setFragmentResultListener(Utility.CLOSE_REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                boolean close = bundle.getBoolean("dialogfragmentclosed");
                customerDetailsViewModel.getSingleCustomerDetailsFromBackEnd(id);

            }
        });

        return customerDetailsView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        customerDetailsViewModel = new ViewModelProvider(this).get(CustomerDetailViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customerDetailsViewModel.getSingleCustomerDetailArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CustomerInformation>>() {
            @Override
            public void onChanged(ArrayList<CustomerInformation> customerInformationArrayList) {

                Log.v("ccppx", "onViewCreated: " );
                loadSingleCustomerDetailsInUI(customerInformationArrayList);
                //update the main arraylist
                customerInformationArrayListMain.clear();
                customerInformationArrayListMain.addAll(customerInformationArrayList);

            }
        });



    }


    /**
     * load data in UI
     * @param customerInformationArrayList
     */

    public void loadSingleCustomerDetailsInUI(ArrayList<CustomerInformation> customerInformationArrayList){
        idTextView.setText(String.valueOf(customerInformationArrayList.get(0).getCustomerId()));
        nameTextView.setText(String.valueOf(customerInformationArrayList.get(0).getName()));

        String createdDate = Utility.formatDate(String.valueOf(customerInformationArrayList.get(0).getCreatedDate()));

        createdDateTextView.setText(createdDate);
        emailTextView.setText(String.valueOf(customerInformationArrayList.get(0).getEmail()));
        phoneTextView.setText(String.valueOf(customerInformationArrayList.get(0).getPhoneNumber()));
        statusTextView.setText(String.valueOf(customerInformationArrayList.get(0).getStatus()));

    }


}