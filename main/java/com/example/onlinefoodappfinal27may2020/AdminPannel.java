package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPannel extends AppCompatActivity
{


    private Button btnAddRestaurants,btnAddMenues,btnViewMenues,btnViewRestaurants,btnViewAllusers, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pannel);


        btnAddRestaurants = (Button) findViewById(R.id.buttonAdminAddRestaurant);
        btnAddMenues= (Button) findViewById(R.id.buttonAdminAddMenues);
        btnViewRestaurants = (Button) findViewById(R.id.buttonAdminViewRestaurant);
        btnViewMenues = (Button) findViewById(R.id.buttonAdminViewMenues);

        btnViewAllusers = (Button) findViewById(R.id.buttonAdminViewAllUsers);
        btnLogout = (Button) findViewById(R.id.buttonAdminLogout);


        btnAddRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Adminpage = new Intent(AdminPannel.this, AddRestaurantActivity.class);
                startActivity(Adminpage);


            }
        });


        btnAddMenues.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent Menunpage = new Intent(AdminPannel.this, AddDealsActivity.class);
                startActivity(Menunpage);


            }
        });



        btnViewAllusers.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent Menunpage = new Intent(AdminPannel.this, ViewUsers.class);
                startActivity(Menunpage);


            }
        });





        btnViewRestaurants.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent Adminpage = new Intent(AdminPannel.this,ViewRestaurants.class);
                startActivity(Adminpage);


            }
        });


        btnViewMenues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Adminpage = new Intent(AdminPannel.this, ViewDeals.class);
                startActivity(Adminpage);



            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Adminpage = new Intent(AdminPannel.this, MainActivity.class);
                startActivity(Adminpage);



            }
        });

    }
}
