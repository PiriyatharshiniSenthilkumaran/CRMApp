package com.androidprojects.crmapp.fragments;

import androidx.fragment.app.FragmentActivity;
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

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.adapters.CustomerInformationAdapter;
import com.androidprojects.crmapp.adapters.OpportunitiesAdapter;
import com.androidprojects.crmapp.dialogfragments.AddNewCustomerDialogFragment;
import com.androidprojects.crmapp.dialogfragments.AddNewOpportunityDialogFragment;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.models.Opportunities;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class OpportunitiesFragment extends Fragment {

    private OpportunitiesViewModel opportunitiesViewModel;

    RecyclerView customerOpportunitiesRecyclerView;
    Button addOpportunitiesButton;

    int id;

    public static OpportunitiesFragment newInstance() {
        return new OpportunitiesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View customerOpportunitiesView = inflater.inflate(R.layout.fragment_opportunities, container, false);


        opportunitiesViewModel = new ViewModelProvider(this).get(OpportunitiesViewModel.class);

        customerOpportunitiesRecyclerView = customerOpportunitiesView.findViewById(R.id.recyclerview_opportunities);
        addOpportunitiesButton = customerOpportunitiesView.findViewById(R.id.opportunity_add_button);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id", 0);
        }

        opportunitiesViewModel.getCustomerOpportunitiesFromBackEnd(id);


        addOpportunitiesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                AddNewOpportunityDialogFragment addNewOpportunityDialogFragment = new AddNewOpportunityDialogFragment(null);


                bundle.putInt("id", id);
                Log.v("ccppz", String.valueOf(id));
                addNewOpportunityDialogFragment.setArguments(bundle);
                addNewOpportunityDialogFragment.show(fragmentTransaction, "addNewOpportunity");
            }
        });



        return customerOpportunitiesView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        opportunitiesViewModel = new ViewModelProvider(this).get(OpportunitiesViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        opportunitiesViewModel.getCustomerOpportunitiesArrayList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Opportunities>>() {
            @Override
            public void onChanged(ArrayList<Opportunities> customerOpportunitiesArrayList) {

                Log.v("ccpp_onviewcreated", "onViewCreated: " + customerOpportunitiesArrayList.size());
//                Log.v("ccpp_onviewcreated", "onViewCreated1: " + emergencyContactsArrayListMain.size());
//                emergencyContactsArrayListMain = new ArrayList<>(emergencyContactsArrayList);
//                Log.v("ccpp_onviewcreated", "onViewCreated2: " + emergencyContactsArrayListMain.size());
                loadCustomerOpportunitiesRecyclerView(customerOpportunitiesArrayList);
            }
        });
    }



    //load data in RecyclerView
    //This is used in listing customer information
    public void loadCustomerOpportunitiesRecyclerView(ArrayList<Opportunities> customerOpportunitiesArrayList){

        customerOpportunitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        OpportunitiesAdapter customerOpportunitiesAdapter = new OpportunitiesAdapter(customerOpportunitiesArrayList, getActivity());
        customerOpportunitiesRecyclerView.setAdapter(customerOpportunitiesAdapter);

        customerOpportunitiesRecyclerView.setHasFixedSize(true);
    }

}