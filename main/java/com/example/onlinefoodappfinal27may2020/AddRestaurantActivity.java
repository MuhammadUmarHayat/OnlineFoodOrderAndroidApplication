package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddRestaurantActivity extends AppCompatActivity
{

    private static final int PICK_IMAGE_REQUEST = 234;

    //view objects
    private Button buttonChoose;
    private Button buttonUpload;
    private EditText etResID,etResTitle,etResArea,etResCity,etResAddress,etResOpening,ResClosing,etContactNo;
    private TextView textViewShow;
    private ImageView imageView;

    //uri to store file

    private Uri filePath;
    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        mAuth = FirebaseAuth.getInstance();

        buttonChoose = (Button) findViewById(R.id.buttonResChooseFile);
        buttonUpload = (Button) findViewById(R.id.buttonResUploadFile);
        imageView = (ImageView) findViewById(R.id.imageViewRES);
        etResID = (EditText) findViewById(R.id.editTextResID);

        etResTitle = (EditText) findViewById(R.id.editTextResTitle);

        etResArea= (EditText) findViewById(R.id.editTextResArea);

        etResCity = (EditText) findViewById(R.id.editTextResCity);//editTextResContactNo
        etContactNo = (EditText) findViewById(R.id.editTextResContactNo);//

        etResAddress= (EditText) findViewById(R.id.editTextResAddress);

        etResOpening = (EditText) findViewById(R.id.editTextResOpeningTime);

        ResClosing= (EditText) findViewById(R.id.editTextResClosingTime);


        textViewShow = (TextView) findViewById(R.id.textViewRESResult);

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("Restaurants");

        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showFileChooser();

            }
        });
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadFile();
            }
        });





    }

    //methods

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // do your stuff
            signInAnonymously();

        } else {
            signInAnonymously();
        }



    }



    private void signInAnonymously()
    {
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // do your stuff
            }
        })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //  Log.e(TAG, "signInAnonymously:FAILURE", exception);
                    }
                });
    }



    StorageReference sRef;



    private void showFileChooser()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //upload files methods


    public String getFileExtension(Uri uri1)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri1));
    }

    private void uploadFile() {
        //checking if file is available
        if (filePath != null)
        {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final String imgpath= System.currentTimeMillis() + "." + getFileExtension(filePath);
            //getting the storage reference

            sRef = storageReference.child(imgpath);

            //adding the file to reference
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //dismissing the progress dialog
                            progressDialog.dismiss();
                            //displaying success toast
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();

                            //creating the upload object to store uploaded image details
                            // Upload upload = new Upload(editTextName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());
                            //  Upload upload = new Upload(editTextName.getText().toString().trim(), Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));
                            //  Upload upload = new Upload(editTextName.getText().toString().trim(),sRef.getDownloadUrl().toString());


                            //Restaurant(String restaurantiD, String title, String area, String city, String contactNo, String address, String openingTime, String closingTime, String url)
                            // Restaurant restaurant=new        Restaurant(etResID.getText().toString().trim(),etResTitle.getText().toString().trim(),etResArea.getText().toString().trim(),etResCity.getText().toString().trim(),etContactNo.getText().toString().trim() ,etResAddress.getText().toString().trim(),etResOpening.getText().toString().trim(),ResClosing.getText().toString().trim(),taskSnapshot.getStorage().getDownloadUrl().toString());

                            Restaurant restaurant=new        Restaurant(etResID.getText().toString().trim(),etResTitle.getText().toString().trim(),etResArea.getText().toString().trim(),etResCity.getText().toString().trim(),etContactNo.getText().toString().trim() ,etResAddress.getText().toString().trim(),etResOpening.getText().toString().trim(),ResClosing.getText().toString().trim(),imgpath);






                            //adding an upload to firebase database
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(restaurant);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //displaying the upload progress
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        else
        {
            //display an error if no file is selected
            Toast.makeText(getApplicationContext(), "Please Select the file first ", Toast.LENGTH_LONG).show();
        }
    }
















}
