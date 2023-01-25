package com.androidprojects.crmapp.fragments;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.dialogfragments.AddNewCustomerDialogFragment;

public class SingleCustomerFragment extends Fragment {

    private SingleCustomerViewModel singleCustomerViewModel;

    int id;

    public static SingleCustomerFragment newInstance() {
        return new SingleCustomerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View singleCustomerView = inflater.inflate(R.layout.fragment_single_customer, container, false);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getInt("id", 0);
        }

        CustomerDetailFragment customerDetailFragment = new CustomerDetailFragment();
        OpportunitiesFragment opportunitiesFragment = new OpportunitiesFragment();


        bundle.putInt("id", id);
        Log.v("ccppz", String.valueOf(id));
        customerDetailFragment.setArguments(bundle);
        opportunitiesFragment.setArguments(bundle);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.fragment_container_customerDetail, customerDetailFragment, "customerDetailFragment").commit();
        fm.beginTransaction().replace(R.id.fragment_container_opportunities, opportunitiesFragment, "opportunitiesFragment").commit();


        singleCustomerViewModel = new ViewModelProvider(this).get(SingleCustomerViewModel.class);
        return singleCustomerView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        singleCustomerViewModel = new ViewModelProvider(this).get(SingleCustomerViewModel.class);
        // TODO: Use the ViewModel
    }




}