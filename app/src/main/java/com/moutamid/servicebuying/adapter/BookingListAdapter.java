package com.moutamid.servicebuying.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.servicebuying.R;
import com.moutamid.servicebuying.SelectedCategoryScreen;
import com.moutamid.servicebuying.model.Request;
import com.moutamid.servicebuying.model.Users;
import com.moutamid.servicebuying.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.HomeCategoryViewHolder> {

    private Context mContext;
    private ArrayList<Request> requestArrayList;

    public BookingListAdapter(Context mContext, ArrayList<Request> requestArrayList) {
        this.mContext = mContext;
        this.requestArrayList = requestArrayList;
    }

    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.booking_custom_layout,parent,false);
        return new HomeCategoryViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Request model = requestArrayList.get(position);
        holder.serviceTxt.setText(model.getServiceName() + "("+model.getSubServiceName()+")");
        holder.statusTxt.setText(model.getStatus());

        if (model.getStatus().equals("Pending")){
            holder.statusTxt.setText("Service Request Placed");
            holder.statusTxt.setTextColor(mContext.getColor(R.color.purple_700));
            holder.statusImg.setImageResource(R.drawable.confirm);
        }else if (model.getStatus().equals("Accepted")){
            holder.statusTxt.setText("Awaiting Payment");
            holder.statusImg.setImageResource(R.drawable.payment_await);
            // holder.statusTxt.setTextColor(mContext.getColor(R.color.red));
            holder.statusTxt.setTextColor(mContext.getColor(R.color.purple_700));
        }
        else if (model.getStatus().equals("Payment Confirmed")){
            holder.statusTxt.setText("Payment Confirmed");
            holder.statusImg.setImageResource(R.drawable.payment_confirmed);
            // holder.statusTxt.setTextColor(mContext.getColor(R.color.red));
            holder.statusTxt.setTextColor(mContext.getColor(R.color.purple_700));
        }
        else if (model.getStatus().equals("Service Confirmed")){
            holder.statusTxt.setText("Service Confirmed");
            holder.statusImg.setImageResource(R.drawable.confirm);
            // holder.statusTxt.setTextColor(mContext.getColor(R.color.red));
            holder.statusTxt.setTextColor(mContext.getColor(R.color.purple_700));
        }

        else if (model.getStatus().equals("Service Completed")){
            holder.statusTxt.setText("Service Completed");
            holder.statusImg.setImageResource(R.drawable.confirm);
            // holder.statusTxt.setTextColor(mContext.getColor(R.color.red));
            holder.statusTxt.setTextColor(mContext.getColor(R.color.purple_700));
        }

        else {
            holder.statusTxt.setText("Cancel Service Request");
            holder.statusTxt.setTextColor(mContext.getColor(R.color.red));
            holder.statusImg.setImageResource(R.drawable.cancel);
        }


        DatabaseReference db = Constants.databaseReference().child("Users").child(model.getUserId());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Users users = snapshot.getValue(Users.class);
                    holder.nameTxt.setText(users.getFname()+" " + users.getLname());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (model.getServiceName().equals("Agriculture Service")) {
            holder.imageView.setImageResource(R.drawable.agriculture);
        }else if (model.getServiceName().equals("Horticultural Service")) {
            holder.imageView.setImageResource(R.drawable.horticulture);
        }else if (model.getServiceName().equals("Veterinary Service Function")) {
            holder.imageView.setImageResource(R.drawable.veterinary);
        }else if (model.getServiceName().equals("Animal Husbandry Service")) {
            holder.imageView.setImageResource(R.drawable.pets);
        }else if (model.getServiceName().equals("Commercial Service")) {
            holder.imageView.setImageResource(R.drawable.market);
        }else if (model.getServiceName().equals("Domestic Service")) {
            holder.imageView.setImageResource(R.drawable.domestic);
        }
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {

        public TextView nameTxt,statusTxt,serviceTxt;
        private ImageView imageView,statusImg;

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTxt = itemView.findViewById(R.id.name);
            statusTxt = itemView.findViewById(R.id.status);
            serviceTxt = itemView.findViewById(R.id.service);
            imageView = itemView.findViewById(R.id.image);
            statusImg = itemView.findViewById(R.id.imageView);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            menu.add(Menu.NONE, getAdapterPosition(), Menu.NONE, "Cancel Service Request");
        }
    }
}
