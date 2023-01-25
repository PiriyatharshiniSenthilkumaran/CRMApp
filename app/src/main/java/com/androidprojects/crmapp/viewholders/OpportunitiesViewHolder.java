package com.androidprojects.crmapp.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.models.Opportunities;

public class OpportunitiesViewHolder extends RecyclerView.ViewHolder {
    public TextView opportunityIdTextView;
    public TextView opportunityNameTextView;
    public TextView opportunityStatusTextView;
    public Button editOpportunityButton;
    public CardView opportunityCardView;

    /**
     * Constructor to initialize views in cardview
     * @param itemView
     */
    public OpportunitiesViewHolder(@NonNull View itemView) {
        super(itemView);
        opportunityIdTextView = itemView.findViewById(R.id.opportunity_id_textview);
        opportunityNameTextView = itemView.findViewById(R.id.opportunity_name_textview);
        opportunityStatusTextView = itemView.findViewById(R.id.opportunity_status_textview);
        editOpportunityButton = itemView.findViewById(R.id.edit_opportunity_button);
        opportunityCardView = itemView.findViewById(R.id.opportunities_cardview);
    }

    /**
     * Update cardview ui
     * @param opportunities
     */
    public void updateData(Opportunities opportunities){
        opportunityIdTextView.setText(String.valueOf(opportunities.getOpportunityId()));
        opportunityNameTextView.setText(opportunities.getOpportunityName());
        opportunityStatusTextView.setText(opportunities.getOpportunityStatus());
    }


}
