package com.alexis.daygoes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alexis.daygoes.Models.PostsModel2;
import com.alexis.daygoes.Models.SceneModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class PostImage extends AppCompatActivity {

    private String mName;
    private EditText mEdtMessage;
    private String mUsername1;
    private String mGroupName;

    // Uri indicates, where the image will be picked from
    private Uri filePath;

    // request code
    private final int PICK_IMAGE_REQUEST = 22;

    // instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri mDownloadUrl;
    private ImageView mImageView;

    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_image);

        // initialize views
        Button btnSelect = findViewById(R.id.btnChoose);
        Button btnUpload = findViewById(R.id.btnUpload);
        mImageView = findViewById(R.id.imgView);
        mEdtMessage = findViewById(R.id.edt_img_comment);

        //      receive intent data passed.
        Intent i = getIntent();
        mName = i.getStringExtra("NAME_KEY");
        mGroupName = mName + " posts";

        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        mUsername1 = prefs.getString("username", "");

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        // on pressing btnSelect SelectImage() is called
        btnSelect.setOnClickListener(v -> SelectImage());

        // on pressing btnUpload uploadImage() is called
        btnUpload.setOnClickListener(v -> uploadImage());
    }

    // Select Image method
    private void SelectImage() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);

    }

    // Override onActivityResult method
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            filePath = data.getData();
            try {
                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                mImageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    // UploadImage method
    private void uploadImage() {
        if (filePath != null) {

            mAuth = FirebaseAuth.getInstance();
            mCurrentUser = mAuth.getCurrentUser();
            assert mCurrentUser != null;

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

            // adding listeners on upload or failure of image
            // Progress Listener for loading percentage on the dialog box
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {
                                ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                    mDownloadUrl = uri;

                                    String msg = mEdtMessage.getText().toString().trim();
                                    String messageSender = mUsername1;
                                    String url = mDownloadUrl.toString();

                                        DatabaseReference PostGroups = FirebaseDatabase.getInstance()
                                                .getReference()
                                                .child("ChatGroups");
//                                        DatabaseReference userPosts = FirebaseDatabase.getInstance()
//                                                .getReference()
//                                                .child("User Posts").child(mUserId);
                                        // Read the input field and push a new instance of PostsModel to the Firebase database
                                        FirebaseDatabase.getInstance()
                                                .getReference()
                                                .child("Posts").child(mGroupName)
                                                .push()
                                                .setValue(new PostsModel2(msg, messageSender, url));
                                        PostGroups.child(mGroupName).setValue(new SceneModel(mName));
//                                        userPosts.push().setValue(new PostsModel(msg, messageSender, url, null, ServerValue.TIMESTAMP));

                                        // Clear the input

                                    finish();
                                    mEdtMessage.setText("");


                                });
                                // Image uploaded successfully Dismiss dialog
                                progressDialog.dismiss();
                                Toast.makeText(PostImage.this,
                                        "Image Uploaded!!",
                                        Toast.LENGTH_SHORT)
                                        .show();
                            })

                    .addOnFailureListener(e -> {

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast
                                .makeText(PostImage.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    })
                    .addOnProgressListener(
                            taskSnapshot -> {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int) progress + "%");
                            });
        }
    }
}
