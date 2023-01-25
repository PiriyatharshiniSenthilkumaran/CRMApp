package com.androidprojects.crmapp.utility;

import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to have the commonly used variables and methods
 */
public class Utility {


    public static final String FILTER_REQUEST_KEY = "filterRequestKey" ;
    public static final String CLOSE_REQUEST_KEY = "closeRequestKey" ;
    /**
     * format date from    "2022-10-15T15:53:00" to October 15, 2022
     * @param unformattedDate
     */

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String formatDate(String unformattedDate){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(unformattedDate, inputFormatter);
            String formattedDate = outputFormatter.format(date);

            return formattedDate;
        }else{
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, \n yyyy");
            Date date = null;
            try {
                date = inputFormat.parse(unformattedDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String formattedDate = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                formattedDate = outputFormat.format(date);
            }
            return formattedDate;
        }

    }



    /**
     * method is used for checking valid email id format.
     *
     * @param email
     * @return boolean true for valid false for invalid
     */
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }








}
