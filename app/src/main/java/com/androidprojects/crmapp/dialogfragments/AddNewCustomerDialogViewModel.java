package com.androidprojects.crmapp.dialogfragments;

import android.app.AlertDialog;
import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
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
import com.androidprojects.crmapp.utility.Utility;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ViewModel to Add and update customer details
 */
public class AddNewCustomerDialogViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<CustomerInformation>> singleCustomerDetailArrayListLiveData = new MutableLiveData<>();

    private RequestQueue requestQueue;

    public AddNewCustomerDialogViewModel(Application application) {
        super(application);
    }



    /**
     *
     * add customer details to the backend as JSON object in the body
     * POST is used with JSONObject in the body
     *
    {
    "id": 56,
    "name": "Anna Austin",
    "createdDate": "2023-01-23T09:56:00",
    "email": "anna@gmail.com",
    "phoneNumber": "(555) 218-5170",
    "status": "active"
}
     */

    public void addNewCustomerDetail(CustomerInformation newCustomerInformation){
        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());

        /**
         * url for API call
         */

        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String url = baseURL + endpoint;
        Log.v("ccpp", "url: " + url);

        /**
         *  JSONObject to be sent with the API call
         */

        JSONObject jsonObjectCustomerDetails = new JSONObject();
        try {
            jsonObjectCustomerDetails.put("id", String.valueOf(newCustomerInformation.getCustomerId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("name", newCustomerInformation.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("createdDate", newCustomerInformation.getCreatedDate() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObjectCustomerDetails.put("email", newCustomerInformation.getEmail() );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            jsonObjectCustomerDetails.put("phone", newCustomerInformation.getPhoneNumber() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObjectCustomerDetails.put("status", newCustomerInformation.getStatus() );
        } catch (JSONException e) {
            e.printStackTrace();
        }



        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObjectCustomerDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("ccpp", response.toString());
                //if the data is saved successfully a toast message is shown
                if(!response.optString("name").equals("")){
                    //Utility.showSnackBar(getApplication().getBaseContext());
                    String message = "Customer Details saved successfully";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();



                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log.v("ccpp_error", error.getLocalizedMessage().toString());
                String message = null;
                if( error instanceof NetworkError) {
                    message = "Cannot connect to Internet. Please check your connection!";
                    Toast.makeText(getApplication().getBaseContext(), message, Toast.LENGTH_SHORT).show();

                } else if( error instanceof ServerError) {
                    message = "You may be using and existing customerID or server encountered an unexpected condition that prevented it from fulfilling the request. Please try again!";
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
     * method to update details of the existing customer
     * PUT is used with JSONObject in the body
     * @param customerInformation
     */

    public void updateCustomerDetail(CustomerInformation customerInformation){
        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());

        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";
        String id = String.valueOf(customerInformation.getCustomerId());

        String url = baseURL + endpoint + "/" + id ;
        Log.v("ccppzx", "url: " + url);


        JSONObject jsonObjectCustomerDetails = new JSONObject();
        try {
            jsonObjectCustomerDetails.put("id", String.valueOf(customerInformation.getCustomerId()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("name", customerInformation.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            jsonObjectCustomerDetails.put("createdDate", customerInformation.getCreatedDate() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObjectCustomerDetails.put("email", customerInformation.getEmail() );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            jsonObjectCustomerDetails.put("phone", customerInformation.getPhoneNumber() );
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            jsonObjectCustomerDetails.put("status", customerInformation.getStatus() );
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Log.v("ccpp", jsonObjectCustomerDetails.toString());
        //new JSONObject(params)

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url, jsonObjectCustomerDetails, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.v("ccppf", response.toString());

                if(!response.optString("name").equals("")){
                    //Utility.showSnackBar(getApplication().getBaseContext());
                    String message = "Customer Details saved successfully";
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
                    message = "You may be using and existing customerID or server encountered an unexpected condition that prevented it from fulfilling the request. Please try again!";
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