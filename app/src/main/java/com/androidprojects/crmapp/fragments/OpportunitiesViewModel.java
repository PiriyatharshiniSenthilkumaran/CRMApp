package com.androidprojects.crmapp.fragments;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidprojects.crmapp.models.CustomerInformation;
import com.androidprojects.crmapp.models.Opportunities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class OpportunitiesViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Opportunities>> customerOpportunitiesArrayListLiveData = new MutableLiveData<>();

    private RequestQueue requestQueue;


    public OpportunitiesViewModel(Application application) {
        super(application);
    }

    public LiveData<ArrayList<Opportunities>> getCustomerOpportunitiesArrayList(){
        return customerOpportunitiesArrayListLiveData;
    }

    //Get CustomerInformation from the back end
    public void getCustomerOpportunitiesFromBackEnd(int customerId) {

        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());

        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String url = baseURL + endpoint + "/" + customerId + "/opportunities/";
        Log.v("ccppx", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        Log.v("ccpp", responseArray.toString());
                        ArrayList<Opportunities> customerOpportunitiesArrayList1 = new ArrayList<>();



                        for(int i = 0; i < responseArray.length(); i++){
                            try {
                                JSONObject customerInformationJSONObject = responseArray.getJSONObject(i);



                                int customerId = customerInformationJSONObject.optInt("customerId");
                                int opportunityId = customerInformationJSONObject.optInt("id");
                                String name = customerInformationJSONObject.optString("name");
                                String status = customerInformationJSONObject.optString("status");

                                //int opportunityId, int customerId, String opportunityStatus, String opportunityName
                                Opportunities opportunities = new Opportunities(opportunityId, customerId, status, name);

                                customerOpportunitiesArrayList1.add(opportunities);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        customerOpportunitiesArrayListLiveData.setValue(customerOpportunitiesArrayList1);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.v("ccpp", error.getLocalizedMessage());

                if (error == null || error.networkResponse == null) {
                    return;
                }

                String errorBody;
                //get status code here
                final String statusCode = String.valueOf(error.networkResponse.statusCode);
                //get response body and parse with appropriate encoding
                errorBody = new String(error.networkResponse.data, StandardCharsets.UTF_8);

                Log.v("ccpp_error", errorBody + "    status code:" + statusCode);

                try {
                    JSONObject errorBodyJSONObject = new JSONObject(errorBody);
                    int result = errorBodyJSONObject.getInt("result");
                    String message =  errorBodyJSONObject.getString("msg");

                    //signUpUserMutableLiveData.setValue(message);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


        int socketTimeout = 2500;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);


    }
}