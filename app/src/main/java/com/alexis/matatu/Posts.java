package com.alexis.matatu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.Adapters.PostsAdapter;
import com.alexis.matatu.Models.PostsModel;
import com.alexis.matatu.Models.PostsModel1;
import com.alexis.matatu.Network.CheckInternetConnection;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.UUID;

public class Posts extends AppCompatActivity {

    private ImageButton mSend;
    private EditText mEdtMessage;
    private PostsAdapter mPostsAdapter;
    private String mUsername1;
    private ImageView mImg_pic1;

    //  Uri indicates, where the image will be picked from
    private Uri filePath;

    //  request code
    private final int PICK_IMAGE_REQUEST = 22;

    //  instance for firebase storage and StorageReference
    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);


//      check Internet Connection
        new CheckInternetConnection(this).checkConnection();

        TextView vehicleName = findViewById(R.id.tv_vehicle_name_posts);
        mSend = findViewById(R.id.fab_send_posts);
        ImageView media = findViewById(R.id.img_upload_media_posts);
        mEdtMessage = findViewById(R.id.edt_message_posts);
        mImg_pic1 = findViewById(R.id.img_pic_post);

//      receive intent data passed.
        Intent i = getIntent();
        String name = i.getStringExtra("NAME_KEY");
        vehicleName.setText(name);

        SharedPreferences prefs = this.getSharedPreferences("MY_PREF", MODE_PRIVATE);
        mUsername1 = prefs.getString("username", "");

        // get the Firebase  storage reference
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

//      Initialize recyclerview
        RecyclerView recyclerView = findViewById(R.id.rv_posts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

//      Initialize DB
        assert name != null;
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Posts").child(name);

//      query db
        FirebaseRecyclerOptions<PostsModel1> options
                = new FirebaseRecyclerOptions.Builder<PostsModel1>()
                .setQuery(db, PostsModel1.class)
                .build();

//      Initialize and set adapter
        mPostsAdapter = new PostsAdapter(options, Posts.this, this);
        recyclerView.setAdapter(mPostsAdapter);

        media.setOnClickListener(v -> selectImage());

        mSend.setOnClickListener(v -> mSend.setOnClickListener(view -> {
            String msg = mEdtMessage.getText().toString().trim();
            String messageSender = mUsername1;

            if (msg.isEmpty()) {
                Toast.makeText(Posts.this, "You can not send a blank message", Toast.LENGTH_LONG).show();
            } else {
                // Read the input field and push a new instance of PostsModel to the Firebase database
                FirebaseDatabase.getInstance()
                        .getReference()
                        .child("Posts").child(name)
                        .push()
                        .setValue(new PostsModel(msg, messageSender));
                uploadImage();
//                        FirebaseDatabase.getInstance()
//                                .getReference()
//                                .child("ChatGroups")
//                                .child(groupName)
//                                .setValue(new ChatModel2(groupName));

                // Clear the input
            }
            mEdtMessage.setText("");
            mImg_pic1.setVisibility(View.GONE);
        }));

    }

    private void uploadImage() {
        if (filePath != null) {

//          Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

//          Defining the child of storageReference
            StorageReference ref
                    = storageReference
                    .child(
                            "images/"
                                    + UUID.randomUUID().toString());

//          adding listeners on upload or failure of image
            //                                 Progress Listener for loading percentage on the dialog box
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            taskSnapshot -> {

                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast
                                        .makeText(Posts.this,
                                                "Image Uploaded!!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            })

                    .addOnFailureListener(e -> {

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast
                                .makeText(Posts.this,
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

    private void selectImage() {
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
                mImg_pic1.setVisibility(View.VISIBLE);
                mImg_pic1.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }


    // Function to tell the app to start getting data from database on starting of the activity
    @Override
    public void onStart() {
        super.onStart();
        mPostsAdapter.startListening();
    }

    // Function to tell the app to stop getting data from database on stopping of the activity
    @Override
    public void onStop() {
        super.onStop();
        mPostsAdapter.stopListening();

    }
}