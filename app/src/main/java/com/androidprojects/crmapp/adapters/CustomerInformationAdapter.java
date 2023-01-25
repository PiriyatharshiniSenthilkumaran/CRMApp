package com.androidprojects.crmapp.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.fragments.SingleCustomerFragment;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.viewholders.CustomerInformationViewHolder;

import java.util.ArrayList;


/**
 * Adapter which handles CustomerDetails RecyclerView to display the list of Customer Information
 */
public class CustomerInformationAdapter extends RecyclerView.Adapter<CustomerInformationViewHolder> {

    ArrayList<CustomerInformation> customerInformationArrayList = new ArrayList();

    Context context;
    FragmentActivity fragmentActivity;
    int id;

    /**
     * constructor to parse information from where it is called
     * @param customerInformationArrayList
     * @param context
     * @param fragmentActivity
     */

    public CustomerInformationAdapter(ArrayList<CustomerInformation> customerInformationArrayList, Context context, FragmentActivity fragmentActivity) {
        this.customerInformationArrayList = customerInformationArrayList;
        this.context = context;
        this.fragmentActivity = fragmentActivity;
    }

    /**
     *  callback method responsible for inflating the cardview layout
     * @param parent
     * @param viewType
     * @return
     */


    @NonNull
    @Override
    public CustomerInformationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customerInformationCardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_customer_information, parent, false);

        return new CustomerInformationViewHolder(customerInformationCardView);
    }

    /**
     * callbackmethod responsible for binding data in the cardview in ViewHolder
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(@NonNull CustomerInformationViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.updateData(customerInformationArrayList.get(position));

        /**
         * when a cardview or an item in the list is clicked it will load the SingleCustomerFragment
         */

        holder.customerInformationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                id = customerInformationArrayList.get(position).getCustomerId();
                //CustomerDetailFragment customerDetailFragment= new CustomerDetailFragment();
                SingleCustomerFragment singleCustomerFragment = new SingleCustomerFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                singleCustomerFragment.setArguments(bundle);

                fragmentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, singleCustomerFragment)
                        .addToBackStack(null)
                        .commit();

                Log.v("ccpp1", String.valueOf(id));
            }
        });
    }

    /**
     * callback method which return the entire size of the customerInformationArrayList
     * @return
     */

    @Override
    public int getItemCount() {
        return customerInformationArrayList.size();
    }
}
