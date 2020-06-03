package com.example.onlinefoodappfinal27may2020;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class AddDealsActivity extends AppCompatActivity
{

    private Button btnMChooseFile,btnMUpload;
    private EditText etMRID,etMCat,etMTitle,etMPrice;
    private ImageView imageView;
    //uri to store file
    private Uri filePath;
    //firebase objects
    private StorageReference storageReference;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private static final int PICK_IMAGE_REQUEST = 234;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_deals);

        mAuth = FirebaseAuth.getInstance();
        btnMChooseFile = (Button) findViewById(R.id.buttonMenuChooseFile);
        btnMUpload = (Button) findViewById(R.id.buttonMenuUploadFile);
        imageView = (ImageView) findViewById(R.id.imageViewMenu);
        etMRID = (EditText) findViewById(R.id.editTextMenuRID);

        etMCat = (EditText) findViewById(R.id.editTextMenuCategory);

        etMTitle= (EditText) findViewById(R.id.editTextMenuTitle);

        etMPrice = (EditText) findViewById(R.id.editTextMenuPrice);






        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference("Deals");

        btnMChooseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                showFileChooser();

            }
        });
        btnMUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                uploadFile();
            }
        });





    }

    //methods

    @Override
    protected void onStart()
    {
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
        mAuth.signInAnonymously().addOnSuccessListener(this, new  OnSuccessListener<AuthResult>()
        {
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
    StorageReference sRef;

    public String getFileExtension(Uri uri1)
    {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri1));
    }

    private void uploadFile() {
        //checking if file is available
        if (filePath != null) {
            //displaying progress dialog while image is uploading
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            final String imgPath=System.currentTimeMillis() + "." + getFileExtension(filePath);
            //getting the storage reference
            sRef = storageReference.child(imgPath);

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
                            // Upload upload = new Upload(editTextName.getText().toString().trim(), taskSnapshot.getDownloadUrl().toString());1
                            //  Upload upload = new Upload(editTextName.getText().toString().trim(), Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + "." + getFileExtension(filePath));2
                            //  Upload upload = new Upload(editTextName.getText().toString().trim(),sRef.getDownloadUrl().toString());3

//etMRID,etMCat,etMTitle,etMPrice

                            int price=Integer.parseInt(etMPrice.getText().toString());
                            Deals deals=new        Deals(etMRID.getText().toString().trim(),etMCat.getText().toString().trim(),etMTitle.getText().toString().trim(),price,imgPath);

                            //adding an upload to firebase database
                            String uploadId = mDatabase.push().getKey();
                            mDatabase.child(uploadId).setValue(deals);


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
        } else {
            //display an error if no file is selected
        }
    }


}
