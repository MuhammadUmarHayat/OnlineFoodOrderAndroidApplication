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


public class CustomerAllDealsActivity extends AppCompatActivity {
    TextView txtUserID;
    EditText editTextSearch;
    Button buttonSearch,btnRefresh;
    ListView lvresults;
    List<Deals> deals;

    //our database reference object
    // DatabaseReference databaseUsers;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference table = Database.getReference("Deals");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_all_deals);
        Intent intent = getIntent();

        final   String userid = intent.getStringExtra("userid");
        final   String restaurantiD = intent.getStringExtra("restaurantiD");
        final   String  restaurantName= intent.getStringExtra("title");

        txtUserID=(TextView) findViewById(R.id.textViewCustomerID1);
        editTextSearch=(EditText) findViewById(R.id.EditTextSearch1);

        lvresults = (ListView) findViewById(R.id.lvViewAllCustomerRestList1);

        buttonSearch = (Button) findViewById(R.id.ButtonSearch1);

        btnRefresh = (Button) findViewById(R.id.ButtonSearchRefresh1);

        table = FirebaseDatabase.getInstance().getReference("Deals");

        deals = new ArrayList<Deals>();

        txtUserID.setText(userid);




        ShowDealsByRID( restaurantiD);





        //adding an onclicklistener to button
        buttonSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String dealTitle=editTextSearch.getText().toString();

                ShowDealsByDealTitle( dealTitle);
            }
        });



        btnRefresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                ShowDealsByRID(restaurantiD);

            }
        });


        lvresults.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {

//restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime, String imagepath)
                Deals deal=deals.get(i);

                final String dealTitle= deal.getTitle();
                final String rid=deal.getRestaurantiD();
                final String cat=deal.getCategory();
                final int price=deal.getPrice();
                final String img=deal.getImgpath();

                //  Toast.makeText(CustomerActivity.this, "R Title :"+rTitle+" RID : "+rid, Toast.LENGTH_LONG).show();
                final String stringPrice=Integer.toString(price);


                Intent allDealPage = new Intent(CustomerAllDealsActivity.this, CustomerOrderPlacementActivity.class);
                //attach values to the page
                allDealPage.putExtra("userid", userid);
                allDealPage.putExtra("restaurantiD",rid );
                allDealPage.putExtra("restaurantName", restaurantName);
                allDealPage.putExtra("title",dealTitle );//deal title
                allDealPage.putExtra("category",cat );//deal category
                allDealPage.putExtra("price",stringPrice );//deal price per unit
                allDealPage.putExtra("imgpath",img );//image of deal

                Toast.makeText(CustomerAllDealsActivity.this, " Title :"+dealTitle+" Price : "+stringPrice, Toast.LENGTH_LONG).show();



                startActivity(allDealPage);//go






                return true;
            }
        });







    }//end of on create function




    private void ShowDealsByRID(String restaurantiD)
    {
          //query
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Deals");
        ref.orderByChild("restaurantiD").startAt(restaurantiD).endAt(restaurantiD+"\uf8ff").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous user list
                deals.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Deals a = postSnapshot.getValue(Deals.class);
                    //adding artist to the list
                    deals.add(a);
                }
                //creating adapter
                DealsList dealsAdapter = new  DealsList(CustomerAllDealsActivity.this, deals);
                //attaching adapter to the listview
                lvresults.setAdapter(dealsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private void ShowDealsByDealTitle(String dealTitle)
    {
//query
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Deals");
        ref.orderByChild("title").startAt(dealTitle).endAt(dealTitle+"\uf8ff").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous user list
                deals.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Deals a = postSnapshot.getValue(Deals.class);
                    //adding artist to the list
                    deals.add(a);//putt into arraylist
                }
                //creating adapter
                DealsList dealsAdapter = new  DealsList(CustomerAllDealsActivity.this, deals);
                //attaching adapter to the listview
                lvresults.setAdapter(dealsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }


}
