package com.alexis.matatu.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alexis.matatu.IndividualMatatu;
import com.alexis.matatu.IndividualRoute;
import com.alexis.matatu.Models.RoutesModel;
import com.alexis.matatu.R;
import com.alexis.matatu.RoutesFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RoutesAdapter extends FirebaseRecyclerAdapter<RoutesModel,RoutesAdapter.FirebaseViewHolder> {

    private final Context mContext;

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

        public FirebaseViewHolder(@NonNull View itemView) {
            super(itemView);

            mTv_route = itemView.findViewById(R.id.tv_route);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast toast = Toast.makeText(mContext,"Clicked",Toast.LENGTH_LONG);
                    toast.show();
                    Context context = v.getContext();
                    Intent i = new Intent(context, IndividualRoute.class);
                    i.putExtra("NAME_KEY", mTv_route.getText().toString());
                    context.startActivity(i);
                }
            });
        }
    }
}
