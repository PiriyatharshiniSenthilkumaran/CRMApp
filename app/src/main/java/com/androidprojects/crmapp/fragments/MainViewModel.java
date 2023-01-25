package com.androidprojects.crmapp.fragments;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidprojects.crmapp.models.CustomerInformation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainViewModel extends AndroidViewModel {
    private MutableLiveData<ArrayList<CustomerInformation>> customerInformationArrayListLiveData = new MutableLiveData<>();

    private RequestQueue requestQueue;


    public MainViewModel(Application application) {
        super(application);
    }

    public LiveData<ArrayList<CustomerInformation>> getCustomerInformationArrayList(){
        return customerInformationArrayListLiveData;
    }

    public void setCustomerInformationArrayListLiveData(ArrayList<CustomerInformation> customerInformationArrayList) {
        customerInformationArrayListLiveData.setValue(customerInformationArrayList);
    }


    //Get CustomerInformation from the back end
    public void getCustomerInformationFromBackEnd() {

        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());


        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String url = baseURL + endpoint;
        Log.v("ccpp", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                Log.v("ccpp_al", String.valueOf(responseArray.length()));
                ArrayList<CustomerInformation> customerInformationArrayList1 = new ArrayList<>();



                for(int i = 0; i < responseArray.length(); i++){
                    try {
                        JSONObject customerInformationJSONObject = responseArray.getJSONObject(i);



                        int id = customerInformationJSONObject.optInt("id");
                        String name = customerInformationJSONObject.optString("name");
                        String createdDate = customerInformationJSONObject.optString("createdDate");
                        String email = customerInformationJSONObject.optString("email");
                        String phoneNumber = customerInformationJSONObject.optString("phoneNumber");
                        String status = customerInformationJSONObject.optString("status");

                        CustomerInformation customerInformation = new CustomerInformation(id, createdDate, name, email, phoneNumber, status);

                        customerInformationArrayList1.add(customerInformation);
                        Log.v("ccpp_al", String.valueOf(id));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Log.v("ccpp_al", String.valueOf(customerInformationArrayList1.size()));

                customerInformationArrayListLiveData.setValue(customerInformationArrayList1);

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
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);


    }



    //sorting and ordering the list
    public void sortCustomerInformationFromBackEnd(String sortingField, String sortingOrder) {

        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());


        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String sortingString = "?_sort="+ sortingField +"&_order=" + sortingOrder;

        String url = baseURL + endpoint + sortingString;
        Log.v("ccpp_date", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        Log.v("ccpp", responseArray.toString());
                        ArrayList<CustomerInformation> customerInformationArrayList1 = new ArrayList<>();



                        for(int i = 0; i < responseArray.length(); i++){
                            try {
                                JSONObject customerInformationJSONObject = responseArray.getJSONObject(i);



                                int id = customerInformationJSONObject.optInt("id");
                                String name = customerInformationJSONObject.optString("name");
                                String createdDate = customerInformationJSONObject.optString("createdDate");
                                String email = customerInformationJSONObject.optString("email");
                                String phoneNumber = customerInformationJSONObject.optString("phoneNumber");
                                String status = customerInformationJSONObject.optString("status");

                                CustomerInformation customerInformation = new CustomerInformation(id, createdDate, name, email, phoneNumber, status);

                                customerInformationArrayList1.add(customerInformation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        customerInformationArrayListLiveData.setValue(customerInformationArrayList1);

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
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);


    }

    //Get filtered CustomerInformation from the back end
    public void getFilteredCustomerInformationFromBackEnd(String appendedString) {

        requestQueue = Volley.newRequestQueue(getApplication().getBaseContext());


        String baseURL = "https://xvy4yik9yk.us-west-2.awsapprunner.com/";
        String endpoint = "customers";


        String url = baseURL + endpoint+appendedString;
        Log.v("ccppzzz", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray responseArray) {
                        Log.v("ccppzzz", responseArray.toString());
                        ArrayList<CustomerInformation> customerInformationArrayList1 = new ArrayList<>();



                        for(int i = 0; i < responseArray.length(); i++){
                            try {
                                JSONObject customerInformationJSONObject = responseArray.getJSONObject(i);



                                int id = customerInformationJSONObject.optInt("id");
                                String name = customerInformationJSONObject.optString("name");
                                String createdDate = customerInformationJSONObject.optString("createdDate");
                                String email = customerInformationJSONObject.optString("email");
                                String phoneNumber = customerInformationJSONObject.optString("phoneNumber");
                                String status = customerInformationJSONObject.optString("status");

                                CustomerInformation customerInformation = new CustomerInformation(id, createdDate, name, email, phoneNumber, status);

                                customerInformationArrayList1.add(customerInformation);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        customerInformationArrayListLiveData.setValue(customerInformationArrayList1);

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