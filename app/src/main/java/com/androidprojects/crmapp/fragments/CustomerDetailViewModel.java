package com.androidprojects.crmapp.fragments;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.models.Opportunities;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class CustomerDetailViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<CustomerInformation>> singleCustomerDetailArrayListLiveData = new MutableLiveData<>();

    private RequestQueue requestQueue;


    public CustomerDetailViewModel(Application application) {
        super(application);
    }

    public LiveData<ArrayList<CustomerInformation>> getSingleCustomerDetailArrayList(){
        return singleCustomerDetailArrayListLiveData;
    }

    public void setSingleCustomerDetailArrayListLiveData(ArrayList<CustomerInformation> customerInformationArrayList) {
        singleCustomerDetailArrayListLiveData.setValue(customerInformationArrayList);
    }


    //Get CustomerInformation from the back end
    public void getSingleCustomerDetailsFromBackEnd(int id) {

        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());

        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String url = baseURL + endpoint + "/" + id;
        Log.v("ccppx", url);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.v("ccppx", String.valueOf(response.optInt("id")));
                         ArrayList<CustomerInformation> customerInformationArrayList1 = new ArrayList<>();

                        int id = response.optInt("id");
                        String name = response.optString("name");
                        String createdDate = response.optString("createdDate");
                        String email = response.optString("email");
                        String phoneNumber = response.optString("phoneNumber");
                        String status = response.optString("status");

                        CustomerInformation customerInformation = new CustomerInformation(id, createdDate, name, email, phoneNumber, status);

                        customerInformationArrayList1.add(customerInformation);

                        Log.v("ccppx", String.valueOf(customerInformationArrayList1.size()));
                        singleCustomerDetailArrayListLiveData.setValue(customerInformationArrayList1);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String message = null;
                if( error instanceof NetworkError) {
                    message = "Cannot connect to Internet. Please check your connection!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                } else if( error instanceof ServerError) {
                    message = "The server encountered an unexpected condition that prevented it from fulfilling the request. Please try again!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                } else if( error instanceof AuthFailureError) {
                    message = "Authentication failed! Please try again!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                } else if( error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                } else if( error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet. Please check your connection!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                } else if( error instanceof TimeoutError) {
                    message = " Connection had timed out. Please try again!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                }else{
                    message = error.getLocalizedMessage();
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();
                }


            }
        });


        int socketTimeout = 2500;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonObjectRequest.setRetryPolicy(policy);
        requestQueue.add(jsonObjectRequest);


    }

}