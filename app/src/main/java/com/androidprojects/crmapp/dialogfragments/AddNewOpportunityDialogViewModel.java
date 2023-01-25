package com.androidprojects.crmapp.dialogfragments;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
import java.util.HashMap;
import java.util.Map;

public class AddNewOpportunityDialogViewModel extends AndroidViewModel {

    private RequestQueue requestQueue;

    public AddNewOpportunityDialogViewModel(Application application) {
        super(application);
    }


    /**
     * Add new Opportunities
     * @param opportunities
     */

    public void addNewOpportunityDetail(Opportunities opportunities){
        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());

        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String customerId = String.valueOf(opportunities.getCustomerId());
        String url = baseURL + endpoint +"/"+ customerId + "/opportunities/" ;
        Log.v("ccpp", "url: " + url);


        JSONObject jsonObjectCustomerDetails = new JSONObject();
        try {
            jsonObjectCustomerDetails.put("customerId", String.valueOf(opportunities.getCustomerId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("id", String.valueOf(opportunities.getOpportunityId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("name", opportunities.getOpportunityName() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObjectCustomerDetails.put("status", opportunities.getOpportunityStatus() );
        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObjectCustomerDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("ccpp", response.toString());

                if(!response.optString("name").equals("")){
                    //Utility.showSnackBar(getApplication().getBaseContext());
                    String message = "Opportunity saved successfully";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                }
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
        })
        {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                return params;
            }

        };


        int socketTimeout = 2500;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }


    /**
     * update opportunity
     * @param opportunities
     */

    public void updateOpportunityDetail(Opportunities opportunities){
        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());

        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String customerId = String.valueOf(opportunities.getCustomerId());
        String opportunityId = String.valueOf(opportunities.getOpportunityId());
        String url = baseURL + endpoint +"/"+ customerId + "/opportunities/" + opportunityId ;
        Log.v("ccpp", "url: " + url);


        JSONObject jsonObjectCustomerDetails = new JSONObject();
        try {
            jsonObjectCustomerDetails.put("customerId", String.valueOf(opportunities.getCustomerId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("id", String.valueOf(opportunities.getOpportunityId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("name", opportunities.getOpportunityName() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObjectCustomerDetails.put("status", opportunities.getOpportunityStatus() );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObjectCustomerDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("ccpp", response.toString());

                if(!response.optString("name").equals("")){
                    String message = "Opportunity saved successfully";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                }
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
        })
        {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }



            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");

                return params;
            }

        };


        int socketTimeout = 2500;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }


}