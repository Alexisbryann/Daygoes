package com.alexis.matatu.Adapters;

import android.app.AlertDialog;
import android.content.Context;
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

public class RoutesAdapter extends FirebaseRecyclerAdapter<RoutesModel, RoutesAdapter.FirebaseViewHolder> {

    private final Context mContext;
    private ArrayList<String> mStops;

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

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Routes");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
//                            mStops = dataSnapshot.child(route).getValue(String.class);
                        String stop1 = dataSnapshot.child(route).getValue(StopsModel.class).getStop1();
                        String stop2 = dataSnapshot.child(route).getValue(StopsModel.class).getStop2();
                        String stop3 = dataSnapshot.child(route).getValue(StopsModel.class).getStop3();
                        String stop4 = dataSnapshot.child(route).getValue(StopsModel.class).getStop4();
                        String stop5 = dataSnapshot.child(route).getValue(StopsModel.class).getStop5();
                        String stop6 = dataSnapshot.child(route).getValue(StopsModel.class).getStop6();
                        String stop7 = dataSnapshot.child(route).getValue(StopsModel.class).getStop7();
                        String stop8 = dataSnapshot.child(route).getValue(StopsModel.class).getStop8();
                        String stop9 = dataSnapshot.child(route).getValue(StopsModel.class).getStop9();

                        mStops = new ArrayList<>();

                        mStops.add(stop1);
                        mStops.add(stop2);
                        mStops.add(stop3);
                        mStops.add(stop4);
                        mStops.add(stop5);
                        mStops.add(stop6);
                        mStops.add(stop7);
                        mStops.add(stop8);
                        mStops.add(stop9);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setTitle("Stops along this route");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_list_item_1);
            builder.setAdapter((ListAdapter) mStops, (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}
