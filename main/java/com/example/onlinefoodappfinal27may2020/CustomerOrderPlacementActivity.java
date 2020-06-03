package com.example.onlinefoodappfinal27may2020;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CustomerOrderPlacementActivity extends AppCompatActivity
{

    EditText etMaxTime,etQuantity;
    TextView tvCustomerID,tvDealTitle,tvCategory,tvPrice,tvResName,tvRID,tvBill,tvCountDown;
    ImageView img;
    Button btnPlaceOrder,btnCancelOrder;
    DatabaseReference databaseUsers;
    FirebaseStorage storage=FirebaseStorage.getInstance();
    StorageReference storageReference=storage.getReferenceFromUrl("gs://samrafoodapplication.appspot.com").child("1587385249495.jpg");

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_order_placement);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Orders");
        Intent intent = getIntent();

        etMaxTime   = (EditText) findViewById(R.id.EditTextDealMaxTime);
        etQuantity = (EditText) findViewById(R.id.EditTextDealQuantity);

        tvCustomerID = (TextView) findViewById(R.id.textViewOrderCustomerID);
        tvDealTitle = (TextView)  findViewById(R.id.textViewOrderDealTitle);

        tvCategory= (TextView) findViewById(R.id.textViewOrderDealCategory);
        tvPrice = (TextView)  findViewById(R.id.textViewOrderDealPrice);
        tvResName= (TextView) findViewById(R.id.textViewOrderDealRestarantName);
        tvRID= (TextView)  findViewById(R.id.textViewOrderDealRestarantID);
        tvBill= (TextView)  findViewById(R.id.textViewOrderDealBill);
        tvCountDown= (TextView)  findViewById(R.id.textViewOrderDealCountDown);




        img = (ImageView) findViewById(R.id.imageViewCustomerDeal);

//tvDealTitle,tvCategory, tvPrice,  tvResName, tvRID

        btnPlaceOrder = (Button) findViewById(R.id.buttonCustomerOrder);

        btnCancelOrder = (Button) findViewById(R.id.buttonCustomerCancelOrder);


        final   String userid = intent.getStringExtra("userid");
        final   String restaurantiD = intent.getStringExtra("restaurantiD");
        final   String  restaurantName= intent.getStringExtra("restaurantName");

        final   String DealTitle = intent.getStringExtra("title");
        final   String category= intent.getStringExtra("category");
        final   String  price= intent.getStringExtra("price");
        final   String imgpath = intent.getStringExtra("imgpath");

//gs://samrafoodapplication.appspot.com firbase storage address
        storageReference=storage.getReferenceFromUrl("gs://samrafoodapplication.appspot.com").child(imgpath);


        //  Toast.makeText(CustomerOrderPlacementActivity.this, " Title :"+DealTitle+" Price : "+price, Toast.LENGTH_LONG).show();
        showImage();//show image
        tvCustomerID.setText(userid);
        tvDealTitle.setText(DealTitle);
        tvCategory.setText(category);
        tvPrice.setText(price);
        tvResName.setText(restaurantName);
        tvRID.setText(restaurantiD);



        btnPlaceOrder.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v) {

                saveCustomerOrder();

                //milli second= minutes*60000;
                String min=etMaxTime.getText().toString();

                long milliSeconds=Long.parseLong(min)*60000;
                showCountDown(milliSeconds);
            }
        });

        btnCancelOrder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(CustomerOrderPlacementActivity.this, " Your order has been canceled ! ", Toast.LENGTH_LONG).show();
            }
        });






    }//end on create
   // function definations

    private void showImage()
    {
        try{

            final File file =File.createTempFile("image","jpg");
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap= BitmapFactory.decodeFile(file.getAbsolutePath());
                    img.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(CustomerOrderPlacementActivity.this,"Fail to load image",Toast.LENGTH_SHORT).show();
                }
            });

        }
        catch (Exception exp)
        {
            Toast.makeText(CustomerOrderPlacementActivity.this,"Fail to load image",Toast.LENGTH_SHORT).show();

        }





    }


    private void saveCustomerOrder()
    {

        String CustomerID=tvCustomerID.getText().toString();
        String RestaurantID=tvRID.getText().toString();
        String RestaurantName=tvResName.getText().toString();
        String DeaLTitle=tvDealTitle.getText().toString();
        String quantity=etQuantity.getText().toString();
        String unitPrice=tvPrice.getText().toString();


        int grandTotal=Integer.valueOf(quantity)*Integer.valueOf(unitPrice);
        tvBill.setText("Total Bill : "+ grandTotal);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String date = df.format(c);


        String DeliveryTime=String.valueOf(etMaxTime.getText());


        if (!TextUtils.isEmpty(quantity))
        {

            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Artist
            String id = databaseUsers.push().getKey();

            //creating an Artist Object
            //  Artist artist = new Artist(id, name, genre);

            Orders a = new Orders( CustomerID,  RestaurantID,  RestaurantName,  DeaLTitle, quantity,  unitPrice,  grandTotal,  date,DeliveryTime) ;

            //Saving the Artist
            databaseUsers.child(id).setValue(a);//insert query
        }





    }
    private void showCountDown(long millisUntilFinished)
    {

        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("Time remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                tvCountDown.setText("Finish!");
            }

        }.start();


    }

}//end of class
