package com.alexis.matatu.FirebaseHelper;

/*
 * 1. RECEIVE DB REFERENCE
 * 3. RETRIEVE
 * 4. RETURN ARRAYLIST
 */

import androidx.annotation.NonNull;

import com.alexis.matatu.Models.MatatuModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MatatuHelper {
    DatabaseReference db;
    ArrayList<MatatuModel> mMatatulists = new ArrayList<>();

    public MatatuHelper(DatabaseReference db) {
        this.db = db;
    }

    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST
    private void fetchData(DataSnapshot dataSnapshot)
    {
        mMatatulists.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            MatatuModel matatuModel =ds.getValue(MatatuModel.class);
            mMatatulists.add(matatuModel);
        }
    }
    //READ
    public ArrayList<MatatuModel> retrieve()
    {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mMatatulists;
    }
}
