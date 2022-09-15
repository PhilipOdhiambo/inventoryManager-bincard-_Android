package com.example.bincard;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bincard.databinding.ActivityAddEditBincardBinding;
import com.example.bincard.model.Bincard;
import com.google.gson.Gson;

import java.util.List;

public class BincardAdapter extends RecyclerView.Adapter<BincardAdapter.BincardViewHolder> {

    Context context;
    List<Bincard> bincardList;
    private final String imageUrlDefault = "https://assets.webiconspng.com/uploads/2016/12/Placeholder-Icon-File.png";

    public BincardAdapter(Context context, List<Bincard> bincardList) {
        this.context = context;
        this.bincardList = bincardList;
    }

    @NonNull
    @Override
    public BincardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_bincard,parent,false);
        return new BincardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BincardViewHolder holder, int position) {
        Bincard bincard = bincardList.get(position);
        holder.code.setText(bincard.getItemCode());
        holder.description.setText(bincard.getDescription());
        holder.unit.setText(bincard.getUnit());
        holder.quantity.setText(bincard.getQuantity());
        if (bincard.getImageUrl().equals("")) {
            bincard.setImageUrl(imageUrlDefault);
        }
        Glide.with(context).load(bincard.getImageUrl()).centerCrop().into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddEditBincard.class);
                Gson gson = new Gson();
                String bincardJson = gson.toJson(bincardList.get(holder.getAdapterPosition()));
                intent.putExtra("bincard",bincardJson);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return bincardList.size();
    }

    public class BincardViewHolder extends RecyclerView.ViewHolder {
        TextView code, description,unit,quantity;
        ImageView imageView;
        ConstraintLayout parentLayout;
        public BincardViewHolder(@NonNull View bincardView) {
            super(bincardView);
            code = bincardView.findViewById(R.id.layout_bincard_tv_drugCode);
            description = bincardView.findViewById(R.id.layout_bincard_tv_drugName);
            unit = bincardView.findViewById(R.id.layout_bincard_tv_drugUnit);
            quantity = bincardView.findViewById(R.id.layout_bincard_tv_drugQty);
            imageView = bincardView.findViewById(R.id.layout_bincard_iv_drugImage);
            parentLayout = bincardView.findViewById(R.id.layout_oneBincard);
        }
    }
}
