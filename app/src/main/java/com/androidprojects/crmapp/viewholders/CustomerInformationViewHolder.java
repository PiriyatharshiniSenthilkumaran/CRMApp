package com.androidprojects.crmapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.utility.Utility;

public class CustomerInformationViewHolder extends RecyclerView.ViewHolder {

    public TextView idTextView;
    public TextView nameTextView;
    public TextView statusTextView;
    public TextView dateTextView;
    public CardView customerInformationCardView;

    /**
     * Constructor to initialize views in cardview
     * @param itemView
     */
    public CustomerInformationViewHolder(@NonNull View itemView) {
        super(itemView);

        idTextView = itemView.findViewById(R.id.textview_id);
        nameTextView = itemView.findViewById(R.id.textview_name);
        statusTextView = itemView.findViewById(R.id.textview_status);
        dateTextView = itemView.findViewById(R.id.textview_created_date);
        customerInformationCardView = itemView.findViewById(R.id.customer_information_cardview);
    }

    /**
     * Update cardview ui
     * @param customerInformation
     */
    public void updateData(CustomerInformation customerInformation){

        idTextView.setText(String.valueOf(customerInformation.getCustomerId()));
        nameTextView.setText(customerInformation.getName());
        statusTextView.setText(customerInformation.getStatus());
        dateTextView.setText(Utility.formatDate(customerInformation.getCreatedDate()));


    }
}
