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

public class ViewUsers extends AppCompatActivity
{


    private Button btnback;
    private ListView lv1;
    List<Users> users;

    //our database reference object
    // DatabaseReference databaseUsers;
    private FirebaseDatabase Database = FirebaseDatabase.getInstance();
    private DatabaseReference table = Database.getReference("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);


        table = FirebaseDatabase.getInstance().getReference("Users");


        lv1 = (ListView) findViewById(R.id.lvViewAllUsers);

        btnback = (Button) findViewById(R.id.buttonAllUsersList1);

        //list to store artists
        users = new ArrayList<Users>();

        onStart();
        //adding an onclicklistener to button
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent Adminpage = new Intent(ViewUsers.this, AdminPannel.class);
                startActivity(Adminpage);

            }
        });
        ///list view listener
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //work on single click










                //starting the activity with intent

            }
        });

        lv1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {


                Users a=users.get(i);
                showUpdateDeleteDialog( a.getName(),a.getfName(),a.getUserid(),a.getPassword(),a.getArea(),a.getCity(),a.getAddress(),a.getEmail(),a.getMobile());
                return true;
            }
        });

    }//end on create



    @Override
    protected void onStart()
    {
        super.onStart();
        //attaching value event listener
//get dat from firebase and show into list

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users");
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous user list
                users.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Users a = postSnapshot.getValue(Users.class);
                    //adding artist to the list
                    users.add(a);
                }
                //creating adapter
                UsersList usersAdapter = new  UsersList(ViewUsers.this, users);
                //attaching adapter to the listview
                lv1.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });
    }

    private boolean deleteUser(String userid)
    {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        dR.orderByChild("userid").equalTo(userid);
        //removing artist
        dR.removeValue();
        return true;
    }


    private boolean updateUser( String name,String fname,String userid,String pw,String area,String city,String add,String email,String mob)
    {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Users").child(userid);
        dR.orderByChild("userid").equalTo(userid);

        //updating artist
        Users a=new Users(name,fname,userid,pw,area,city,add,email,mob);
        dR.setValue(a);
        Toast.makeText(getApplicationContext(), "User is  Updated", Toast.LENGTH_LONG).show();



        return true;
    }

    private void showUpdateDeleteDialog( String name, String fname,  String userid, String pw, String area, String city,String add, String email, String mob)
    {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_users_dialog, null);
        dialogBuilder.setView(dialogView);


        //   name,fname,userid,pw,area,city,add,email,mob);
        final EditText editTextName = (EditText) dialogView.findViewById(R.id.textViewUpdateName);
        final EditText editTextFName = (EditText) dialogView.findViewById(R.id.textViewUpdateFNAme);
        final EditText editTextUserid = (EditText) dialogView.findViewById(R.id.textViewUpdateUserID);
        final EditText editTextArea = (EditText) dialogView.findViewById(R.id.textViewUpdateArea);
        final EditText editTextCity = (EditText) dialogView.findViewById(R.id.textViewUpdateCity);
        final EditText editTextAddress = (EditText) dialogView.findViewById(R.id.textViewUpdateAddress);
        final EditText editTextemail1 = (EditText) dialogView.findViewById(R.id.textViewUpdateEmial);
        final EditText editTextMob = (EditText) dialogView.findViewById(R.id.textViewUpdateMobileNo);
        final TextView editTextPw = (TextView) dialogView.findViewById(R.id.textViewUpdatePassword);


        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUserByID);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUserByID);

        editTextName.setText(name);

        editTextFName.setText(fname);
        editTextCity.setText(city);
        editTextArea.setText(area);;
        editTextUserid.setText(userid);

        editTextAddress.setText(add);
        editTextemail1.setText(email);
        editTextMob.setText(mob);
        editTextPw.setText(pw);

        String deleteUserID=userid;





        dialogBuilder.setTitle(userid);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final    String name = editTextName.getText().toString().trim();

                final  String add =  editTextAddress.getText().toString().trim();
                final  String fname =  editTextFName.getText().toString().trim();
                final   String userid = editTextUserid.getText().toString().trim();
                final   String pw =  editTextPw.getText().toString().trim();

                final   String area =      editTextArea.getText().toString().trim();
                final  String city = editTextCity.getText().toString().trim();
                final  String email =  editTextemail1.getText().toString().trim();
                final String mob = editTextMob.getText().toString().trim();

                if (!TextUtils.isEmpty(userid)) {
                    updateUser(name,fname,userid,pw,area,city,add,email,mob);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final   String userid = editTextUserid.getText().toString().trim();
                deleteUser( userid);
                b.dismiss();
            }
        });
    }






}//end
