package com.alexis.matatu.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.IndividualRoute;
import com.alexis.matatu.MainActivity;
import com.alexis.matatu.Models.RoutesModel;
import com.alexis.matatu.Models.StopsModel;
import com.alexis.matatu.R;
import com.alexis.matatu.Fragments.RoutesFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RoutesAdapter extends FirebaseRecyclerAdapter<RoutesModel, RoutesAdapter.FirebaseViewHolder> {

    private final Context mContext;
    private List<String> mStops;

    public RoutesAdapter(@NonNull FirebaseRecyclerOptions<RoutesModel> options, RoutesFragment routesFragment, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull FirebaseViewHolder holder, int position, @NonNull RoutesModel model) {
        holder.mTv_route.setText(model.getRoute());
    }

    @NonNull
    @Override
    public FirebaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_routes, parent, false);
        return new FirebaseViewHolder(view);
    }

    public class FirebaseViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTv_route;
        private final ImageView mImg_more;


        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mTv_route = itemView.findViewById(R.id.tv_sacco);
            mImg_more = itemView.findViewById(R.id.img_more);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(mContext, mTv_route.getText(), Toast.LENGTH_LONG);
                    toast.show();
                    Context context = v.getContext();
                    Intent i = new Intent(context, IndividualRoute.class);
                    i.putExtra("NAME_KEY", mTv_route.getText().toString());
                    context.startActivity(i);
                }
            });

            mImg_more.setOnClickListener(v -> {
                showDialog();
            });
        }

        private void showDialog() {

            String route = mTv_route.getText().toString();
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Stops along this route.");
            builder.setNegativeButton("OK", null);


            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Routes");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String stop1 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop1();
                        String stop2 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop2();
                        String stop3 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop3();
                        String stop4 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop4();
                        String stop5 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop5();
                        String stop6 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop6();
                        String stop7 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop7();
                        String stop8 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop8();
                        String stop9 = Objects.requireNonNull(dataSnapshot.child(route).getValue(StopsModel.class)).getStop9();

                        final List<String> Stops = new ArrayList<>();
                        Stops.add(stop1);
                        Stops.add(stop2);
                        Stops.add(stop3);
                        Stops.add(stop4);
                        Stops.add(stop5);
                        Stops.add(stop6);
                        Stops.add(stop7);
                        Stops.add(stop8);
                        Stops.add(stop9);

                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mContext,
                                android.R.layout.simple_dropdown_item_1line, Stops);
                        builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(mContext,"You have selected " + Stops.get(which),Toast.LENGTH_LONG).show();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }


            });
        }
    }
}

