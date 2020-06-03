package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewRestaurants extends AppCompatActivity
{
    private ListView lv1;

    private  Button btnback;
    List<Restaurant> restaurants;

    //our database reference object
    // DatabaseReference databaseUsers;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference table = Database.getReference("Restaurants");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurants);


        table = FirebaseDatabase.getInstance().getReference("Restaurants");


        lv1 = (ListView) findViewById(R.id.lvViewAllREST);

        btnback = (Button) findViewById(R.id.buttonAllRESTList1);

        //list to store artists
        restaurants = new ArrayList<Restaurant>();

        onStart();
        //adding an onclicklistener to button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Adminpage = new Intent(ViewRestaurants.this, AdminPannel.class);
                startActivity(Adminpage);

            }
        });
        ///list view listener

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

//restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime, String imagepath)
                Restaurant r=restaurants.get(i);

                showUpdateDeleteDialog( r.getRestaurantiD(),r.getTitle(),r.getArea(),r.getCity(),r.getContactNo(),r.getAddress(),r.getOpeningTime(),r.getClosingTime());
                return true;
            }
        });

    }//end on create



    @Override
    protected void onStart()
    {
        super.onStart();
        //attaching value event listener

//query select * from restaurants
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants");
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous user list
                restaurants.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Restaurant a = postSnapshot.getValue(Restaurant.class);
                    //adding artist to the list
                    restaurants.add(a);
                }
                //creating adapter
                RestaurantList resAdapter = new  RestaurantList(ViewRestaurants.this, restaurants);
                //attaching adapter to the listview
                lv1.setAdapter(resAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private boolean deleteRestaurant(String restID)
    {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Restaurants").child(restID);
        dR.orderByChild("restaurantiD").equalTo(restID);
        //removing artist
        dR.removeValue();
        Toast.makeText(getApplicationContext(), "Restaurant is  Deleted ", Toast.LENGTH_LONG).show();
        return true;
    }


    private boolean updateRestaurant(String restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime)
    {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Restaurants").child(restaurantiD);
        dR.orderByChild("restaurantiD").equalTo(restaurantiD);
        ;
        //updating artist
        Restaurant a=new Restaurant(restaurantiD,title,area,city,contactNo,address,openingTime,closingTime);
        dR.setValue(a);
        Toast.makeText(getApplicationContext(), "Restaurant is  Updated", Toast.LENGTH_LONG).show();



        return true;
    }

    private void showUpdateDeleteDialog( String restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_restaurant_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText etrestaurantiD = (EditText) dialogView.findViewById(R.id.EditrestaurantiD);
        final EditText ettitle = (EditText) dialogView.findViewById(R.id.EditRESTTitle);
        final EditText etarea = (EditText) dialogView.findViewById(R.id.EditRESTArea);
        final EditText etcity = (EditText) dialogView.findViewById(R.id.EditRestCity);
        final EditText etcontactNo = (EditText) dialogView.findViewById(R.id.EditContactNo);
        final EditText etaddress = (EditText) dialogView.findViewById(R.id.EditRESTAddress);
        final EditText etopeningTime = (EditText) dialogView.findViewById(R.id.EditRestopeningTime);
        final EditText etclosingTime= (EditText) dialogView.findViewById(R.id.EditRestClosingTime);



        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateRestByID);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteRESTByID);

        etrestaurantiD.setText(restaurantiD);

        ettitle.setText(title);
        etarea.setText(area);
        etcity.setText(city);
        etcontactNo.setText(contactNo);

        etaddress.setText(address);
        etopeningTime.setText(openingTime);
        etclosingTime.setText(closingTime);

        dialogBuilder.setTitle(restaurantiD);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //etrestaurantiD,ettitle,etarea,etcity,etcontactNo, etaddress,etopeningTime,etclosingTime

                final    String rid = etrestaurantiD.getText().toString().trim();

                final  String ti =  ettitle.getText().toString().trim();
                final  String ar =  etarea.getText().toString().trim();
                final   String ci = etcity.getText().toString().trim();
                final   String con=  etcontactNo.getText().toString().trim();

                final   String ad =      etaddress.getText().toString().trim();
                final  String op = etopeningTime.getText().toString().trim();
                final  String ct = etclosingTime.getText().toString().trim();


                if (!TextUtils.isEmpty(rid)) {

                    updateRestaurant(rid,ti,ar,ci,con,ad,op,ct);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final   String rid = etrestaurantiD.getText().toString().trim();
                deleteRestaurant( rid);
                b.dismiss();
            }
        });
    }






}//end






