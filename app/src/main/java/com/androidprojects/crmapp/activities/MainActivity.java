package com.androidprojects.crmapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.androidprojects.crmapp.R;
import com.androidprojects.crmapp.fragments.MainFragment;

/**
 * MainActivity  - Starting point of the App which contains the fragment which displays the list of customers
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * load MainFragment inside MainActivity
         *
         */
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, MainFragment.class, null)
                    .commit();
        }


    }


}