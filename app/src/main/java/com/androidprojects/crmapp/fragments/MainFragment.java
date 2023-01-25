package com.androidprojects.crmapp.fragments;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.adapters.CustomerInformationAdapter;
import com.androidprojects.crmapp.dialogfragments.AddNewCustomerDialogFragment;
import com.androidprojects.crmapp.dialogfragments.FiltersDialogFragment;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.utility.Utility;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private MainViewModel mainViewModel;
    RecyclerView customerInformationRecyclerView;

    ImageView idAscendingButton;
    ImageView idDescendingButton;
    ImageView nameAscendingButton;
    ImageView nameDescendingButton;
    ImageView statusAscendingButton;
    ImageView statusDescendingButton;
    ImageView dateAscendingButton;
    ImageView dateDescendingButton;

    ImageView filterImageView;

    FloatingActionButton addNewCustomerFloatingActionButton;


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View customerInformationView = inflater.inflate(R.layout.fragment_main, container, false);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);


        idAscendingButton = customerInformationView.findViewById(R.id.sort_id_ascending_imageview);
        idDescendingButton = customerInformationView.findViewById(R.id.sort_id_descending_imageview);
        nameAscendingButton = customerInformationView.findViewById(R.id.sort_name_ascending_imageview);
        nameDescendingButton = customerInformationView.findViewById(R.id.sort_name_descending_imageview);
        statusAscendingButton = customerInformationView.findViewById(R.id.sort_status_ascending_imageview);
        statusDescendingButton = customerInformationView.findViewById(R.id.sort_status_descending_imageview);
        dateAscendingButton = customerInformationView.findViewById(R.id.sort_date_ascending_imageview);
        dateDescendingButton = customerInformationView.findViewById(R.id.sort_date_descending_imageview);
        filterImageView = customerInformationView.findViewById(R.id.filter_imageview);

        addNewCustomerFloatingActionButton = customerInformationView.findViewById(R.id.add_new_customer_floatingactionbutton);

        customerInformationRecyclerView = customerInformationView.findViewById(R.id.recyclerview_customer_information);

        mainViewModel.getCustomerInformationFromBackEnd();



        //when filter button is clicked
        filterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                FiltersDialogFragment filtersDialogFragment = new FiltersDialogFragment();

                filtersDialogFragment.show(fragmentTransaction, "filterDialogFragment");

            }
        });



        idAscendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("id", "asc");
            }
        });

        idDescendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("id", "desc");
            }
        });


        nameAscendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("name", "asc");
            }
        });

        nameDescendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("name", "desc");
            }
        });



        statusAscendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("status", "asc");
            }
        });

        statusDescendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("status", "desc");
            }
        });


        dateAscendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("createdDate", "asc");
            }
        });

        dateDescendingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainViewModel.sortCustomerInformationFromBackEnd("createdDate", "desc");
            }
        });


        addNewCustomerFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                AddNewCustomerDialogFragment addNewCustomerDialogFragment = new AddNewCustomerDialogFragment();

                addNewCustomerDialogFragment.show(fragmentTransaction, "addNew");
            }
        });


        //To receive a fragment result in the host activity
        getActivity().getSupportFragmentManager().setFragmentResultListener(Utility.FILTER_REQUEST_KEY, this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                mainViewModel.getFilteredCustomerInformationFromBackEnd(result.getString("appendedString"));
            }
        });

        return customerInformationView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainViewModel.getCustomerInformationArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<CustomerInformation>>() {
            @Override
            public void onChanged(ArrayList<CustomerInformation> customerInformationArrayList) {

                Log.v("ccpp_onviewcreated", "onViewCreated: " + customerInformationArrayList.size());
//                Log.v("ccpp_onviewcreated", "onViewCreated1: " + emergencyContactsArrayListMain.size());
//                emergencyContactsArrayListMain = new ArrayList<>(emergencyContactsArrayList);
//                Log.v("ccpp_onviewcreated", "onViewCreated2: " + emergencyContactsArrayListMain.size());
                loadCustomerInformationRecyclerView(customerInformationArrayList);


            }
        });
    }



    //load data in RecyclerView
    //This is used in listing customer information
    public void loadCustomerInformationRecyclerView(ArrayList<CustomerInformation> customerInformationArrayList){

        customerInformationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        CustomerInformationAdapter customerInformationAdapter = new CustomerInformationAdapter(customerInformationArrayList, getContext(), requireActivity());
        customerInformationRecyclerView.setAdapter(customerInformationAdapter);

        customerInformationRecyclerView.setHasFixedSize(true);
    }

}