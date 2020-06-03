package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
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

public class CustomerActivity extends AppCompatActivity {

    TextView txtUserID;
    EditText editTextSearch;
    Button buttonSearch,btnRefresh;
    ListView lvresults;
    List<Restaurant> restaurants;

    //our database reference object
    // DatabaseReference databaseUsers;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference table = Database.getReference("Restaurants");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
//get data from previous page
        Intent intent = getIntent();

        final   String userid = intent.getStringExtra("userid");
        final   String area = intent.getStringExtra("area");

        //////////////////////////////////////

        txtUserID=(TextView) findViewById(R.id.textViewCustomerID);
        editTextSearch=(EditText) findViewById(R.id.EditTextSearch);

        lvresults = (ListView) findViewById(R.id.lvViewAllCustomerRestList);

        buttonSearch = (Button) findViewById(R.id.ButtonSearch);

        btnRefresh = (Button) findViewById(R.id.ButtonSearchRefresh);

        table = FirebaseDatabase.getInstance().getReference("Restaurants");

        restaurants = new ArrayList<Restaurant>();

        txtUserID.setText(userid);

        //  onStart();





        ShowRestaurantsByArea(area);//calling of a function


        //adding an onclicklistener to button
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String RestaurantTitle=editTextSearch.getText().toString();

                ShowRestaurantsByTitle(RestaurantTitle);//calling of a function
            }
        });



        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                ShowRestaurantsByArea(area);//calling of a function

            }
        });


        lvresults.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {

//restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime, String imagepath)
                Restaurant r=restaurants.get(i);

                final String rTitle= r.getTitle();
                final String rid=r.getRestaurantiD();

                //  Toast.makeText(CustomerActivity.this, "R Title :"+rTitle+" RID : "+rid, Toast.LENGTH_LONG).show();



                Intent customerPage = new Intent(CustomerActivity.this, CustomerAllDealsActivity.class);
                //attach values to the page
                customerPage.putExtra("userid", userid);
                customerPage.putExtra("restaurantiD",rid );
                customerPage.putExtra("title",rTitle );


                startActivity(customerPage);






                return true;
            }
        });







    }//end of on create function




    private void ShowRestaurantsByArea(String area)
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants");
        ref.orderByChild("area").startAt(area).endAt(area+"\uf8ff").addValueEventListener(new ValueEventListener()
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
                RestaurantList resAdapter = new  RestaurantList(CustomerActivity.this, restaurants);
                //attaching adapter to the listview
                lvresults.setAdapter(resAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private void ShowRestaurantsByTitle(String title)
    {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants");
        ref.orderByChild("title").startAt(title).endAt(title+"\uf8ff").addValueEventListener(new ValueEventListener()
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
                RestaurantList resAdapter = new  RestaurantList(CustomerActivity.this, restaurants);
                //attaching adapter to the listview
                lvresults.setAdapter(resAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

}
