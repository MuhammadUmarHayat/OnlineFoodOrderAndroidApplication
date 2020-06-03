package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddUsers extends AppCompatActivity
{

    EditText etNameR,etFNameR,etIDR,etPwR,etAreaR,etCityR,etAddR,etEmailR,etMobR;

    Button btnBack,btnReg;

    DatabaseReference databaseUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_users);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        etNameR = (EditText) findViewById(R.id.editTextNameR);

        etFNameR = (EditText) findViewById(R.id.editTextFNameR);


        etIDR = (EditText) findViewById(R.id.editTextIDR);

        etPwR = (EditText) findViewById(R.id.editTextPwR);

        etAreaR = (EditText) findViewById(R.id.editTextAreaR);

        etCityR = (EditText) findViewById(R.id.editTextCityR);

        etAddR = (EditText) findViewById(R.id.editTextAddressR);


        etEmailR= (EditText) findViewById(R.id.editTextEmailR);

        etMobR = (EditText) findViewById(R.id.editTextMobR);












        btnBack = (Button) findViewById(R.id.buttonBackR);
        btnReg = (Button) findViewById(R.id. buttonRegister);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent Homepage = new Intent(AddUsers.this, MainActivity.class);
                startActivity(Homepage);//nevigate

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUsers();//calling function

            }
        });



    }

    private void addUsers()//function define
    {
        //  ,,,,,,,,;
        String name = etNameR.getText().toString().trim();
        String fname = etFNameR.getText().toString().trim();

        String userid= etIDR.getText().toString().trim();
        String pw = etPwR.getText().toString().trim();

        String area = etAreaR.getText().toString().trim();
        String city = etCityR.getText().toString().trim();
        String add = etAddR.getText().toString().trim();

        String email = etEmailR.getText().toString().trim();
        String mob = etMobR.getText().toString().trim();











        //checking if the value is provided
        if (!TextUtils.isEmpty(name)) {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id =databaseUsers.push().getKey();

            //creating an Artist Object
            //  Artist artist = new Artist(id, name, genre);

            Users a=new Users(name,fname,userid,pw,area,city,add,email,mob);

            //Saving the Artist
            databaseUsers.child(id).setValue(a);//insert

            //setting edittext to blank again


            etNameR.setText("");
            etFNameR.setText("");
            etIDR.setText("");
            etPwR.setText("");
            etAreaR.setText("");
            etCityR.setText("");
            etAddR.setText("");
            etEmailR .setText("");
            etMobR.setText("");



            //displaying a success toast
            Toast.makeText(this, "User is registered successfully", Toast.LENGTH_LONG).show();
        } else {
            //if the value is not given displaying a toast
            Toast.makeText(this, "Please enter data in the fields", Toast.LENGTH_LONG).show();
        }



    }




}
