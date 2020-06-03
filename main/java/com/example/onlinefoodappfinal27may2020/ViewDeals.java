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

public class ViewDeals extends AppCompatActivity
{


    private Button btnback;
    private ListView lv1;
    List<Deals> deals;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference table = Database.getReference("Deals");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_deals);

        table = FirebaseDatabase.getInstance().getReference("Deals");


        lv1 = (ListView) findViewById(R.id.lvViewAllDeals);

        btnback = (Button) findViewById(R.id.buttonAllDealsList1);

        //list to store artists
        deals = new ArrayList<Deals>();

        onStart();
        //adding an onclicklistener to button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Adminpage = new Intent(ViewDeals.this, AdminPannel.class);
                startActivity(Adminpage);

            }
        });
        ///list view listener

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                Deals a=deals.get(i);//String restaurantiD, String category, String title, int price

                showUpdateDeleteDialog( a.getRestaurantiD(),a.getCategory(),a.getTitle(),a.getPrice());
                return true;
            }
        });

    }//end on create



    @Override
    protected void onStart()
    {
        super.onStart();
        //attaching value event listener


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Deals");
        ref.addValueEventListener(new ValueEventListener()
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
                DealsList dealsAdapter = new DealsList(ViewDeals.this, deals);
                //attaching adapter to the listview
                lv1.setAdapter(dealsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private boolean deleteDeals(String title)
    {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Deals").child(title);
        dR.orderByChild("title").equalTo(title);
        //removing artist
        dR.removeValue();
        return true;
    }


    private boolean updateDeals(String rid,String cat, String title, int price)
    {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Deals").child(title);
        dR.orderByChild("title").equalTo(rid);
//dR.getKey();
//String id=dR.getKey();
        //updating artist
        Deals deals=new Deals(rid,cat,title,price);
        dR.setValue(deals);
        Toast.makeText(getApplicationContext(), "Deal is  Updated", Toast.LENGTH_LONG).show();



        return true;
    }

    private void showUpdateDeleteDialog(String restaurantiD, String category, final String title, int price)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_deals_dialog, null);
        dialogBuilder.setView(dialogView);


        //   name,fname,userid,pw,area,city,add,email,mob);
        final EditText editTextRID = (EditText) dialogView.findViewById(R.id.EditDealsrestaurantiD);
        final EditText editTextCat = (EditText) dialogView.findViewById(R.id.EditDealsCategory);
        final EditText editTextTitle = (EditText) dialogView.findViewById(R.id.EditDealsTitle);
        final EditText editTextPrice = (EditText) dialogView.findViewById(R.id.EditDealsPrice);



        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateDealsByID);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteDealsByID);

        editTextRID.setText(restaurantiD);

        editTextCat.setText(category);
        editTextTitle.setText(title);
        editTextPrice.setText(price);;



        final  String deleterestaurantiD=restaurantiD;





        dialogBuilder.setTitle(restaurantiD);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final    String rid = editTextRID.getText().toString().trim();

                final  String cat =  editTextCat.getText().toString().trim();
                final  String title =   editTextTitle.getText().toString().trim();
                final   String p =    editTextPrice.getText().toString().trim();
                int price = Integer.parseInt(p);//convert string to integer

                if (!TextUtils.isEmpty(title)) {
                    updateDeals(rid,cat,title,price);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final   String rid = editTextRID.getText().toString().trim();
                deleteDeals( title);
                b.dismiss();
            }
        });
    }






}//end
