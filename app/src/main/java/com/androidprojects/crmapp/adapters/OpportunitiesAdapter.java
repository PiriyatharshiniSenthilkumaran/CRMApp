package com.androidprojects.crmapp.adapters;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.dialogfragments.AddNewOpportunityDialogFragment;
import com.androidprojects.crmapp.models.Opportunities;
import com.androidprojects.crmapp.viewholders.OpportunitiesViewHolder;

import java.util.ArrayList;
/**
 * Adapter which handles Opportunities RecyclerView to display the list of Opportunities
 */
public class OpportunitiesAdapter extends RecyclerView.Adapter<OpportunitiesViewHolder> {

    ArrayList<Opportunities> opportunitiesArrayList = new ArrayList();

    Activity activity;

    /**
     * constructor to parse opportunities data
     * @param opportunitiesArrayList
     * @param activity
     */

    public OpportunitiesAdapter(ArrayList<Opportunities> opportunitiesArrayList, Activity activity) {
        this.opportunitiesArrayList = opportunitiesArrayList;
        this.activity = activity;
    }


    /**
     * callback method responsible for inflating opportunities cardview layout
     * @param parent
     * @param viewType
     * @return
     */

    @NonNull
    @Override
    public OpportunitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View opportunitiesCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_opportunities, parent, false);

        return new OpportunitiesViewHolder(opportunitiesCardView);
    }


    /**
     * call back method to handle each view in the Viewholder
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull OpportunitiesViewHolder holder, int position) {
        holder.updateData(opportunitiesArrayList.get(position));

        holder.editOpportunityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction();
                AddNewOpportunityDialogFragment addNewOpportunityDialogFragment = new AddNewOpportunityDialogFragment(opportunitiesArrayList);


                int id = opportunitiesArrayList.get(position).getCustomerId();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                Log.v("ccppz", String.valueOf(id));
                addNewOpportunityDialogFragment.setArguments(bundle);
                addNewOpportunityDialogFragment.show(fragmentTransaction, "addNewOpportunity");
            }
        });
    }

    /**
     * callback method which returns the size of the opportunitiesArrayList
     * @return
     */

    @Override
    public int getItemCount() {
        return opportunitiesArrayList.size();
    }
}
