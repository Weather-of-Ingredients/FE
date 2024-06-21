package com.example.woi_fe.ui.dietcal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.dto.recommendation.CropItem;
import com.example.woi_fe.ui.Diet.DietItemAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class CropListAdapter extends RecyclerView.Adapter<CropListAdapter.ItemViewHolder> {

    private List<CropItem> cropItemList;
    private Context context;

    public CropListAdapter(List<CropItem> cropItemList, Context context) {
        this.cropItemList = cropItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_crop, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(cropItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return cropItemList.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_crop_image);
            textView = itemView.findViewById(R.id.item_crop_id);
        }

        public void onBind(CropItem cropItem){
            textView.setText(cropItem.getIngredient_name());
            Glide.with(context)
                    .load(cropItem.getIngredient_image())
                    .apply(RequestOptions.circleCropTransform())
                    .override(300, 300)
                    .into(imageView);
        }
    }
}
