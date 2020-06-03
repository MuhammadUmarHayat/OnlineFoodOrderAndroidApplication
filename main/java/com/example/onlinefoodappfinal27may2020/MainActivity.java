package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity
{
    EditText etUserID,etPw;
    Button btnLogin,btnReg;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etUserID = (EditText) findViewById(R.id.editTextUserID);
        etPw = (EditText) findViewById(R.id.editTextPassword);

        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnReg = (Button) findViewById(R.id.buttonRegistration);


        btnLogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


                final String userid = etUserID.getText().toString().trim();
                final String pw = etPw.getText().toString().trim();



                if (userid.equals("admin")&& pw.equals("admin"))
                {


                    Intent Adminpage = new Intent(MainActivity.this, AdminPannel.class);
                    startActivity(Adminpage);



                }
                else
                    {


                    try {


                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");

                        ref.orderByChild("userid").equalTo(userid).addValueEventListener(new ValueEventListener()//select * from users where userid='a'
                        {


                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String id1 = "";
                                for (DataSnapshot child : dataSnapshot.getChildren()) {
                                    System.out.println(child.getKey());
                                    System.out.println(child.child("password").getValue());
                                    id1 = child.getKey();
                                }


                                final Users user2 = dataSnapshot.child(id1).getValue(Users.class);


                                String sqlID = user2.getUserid();
                                String sqlPw = user2.getPassword();


                                if (userid.equals(sqlID) && pw.equals(sqlPw)) {


                                    Toast.makeText(MainActivity.this, "password is corract", Toast.LENGTH_LONG).show();

                                    Intent customerPage = new Intent(MainActivity.this, CustomerActivity.class);
                                    //attach values to the page
                                    customerPage.putExtra("userid", user2.getUserid());
                                    customerPage.putExtra("area", user2.getArea());
                                    customerPage.putExtra("city", user2.getCity());
                                    customerPage.putExtra("address",user2.getAddress());

                                    startActivity(customerPage);//nevigate







                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                    catch (Exception exp)
                    {

                        Toast.makeText(MainActivity.this, "Please enter corract id and password", Toast.LENGTH_LONG).show();

                    }
                }








            }
        });


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Regpage = new Intent(MainActivity.this, AddUsers.class);
                startActivity(Regpage);

            }
        });


    }
}
