package com.example.woi_fe.ui.CropPrediction;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.R;
import com.example.woi_fe.Retrofit.dto.recommendation.BadCropMenuDTO;
import com.example.woi_fe.databinding.BottomSheetCropPredBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class BadCropMenuItemAdapter extends RecyclerView.Adapter<BadCropMenuItemAdapter.ItemViewHolder>{
    private List<BadCropMenuDTO> badCropMenuDTOList;
    private Context context;
    private LayoutInflater layoutInflater;
    public BadCropMenuItemAdapter(List<BadCropMenuDTO> badCropMenuDTOList, Context context) {
        this.badCropMenuDTOList = badCropMenuDTOList;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_menu_list, parent, false);
        return new ItemViewHolder(view, context, layoutInflater);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position){
        holder.onBind(badCropMenuDTOList.get(position));
    }

    @Override
    public int getItemCount() {
        return badCropMenuDTOList.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        Button menuName;
        TextView altCropName;

        private Context context;
        private LayoutInflater layoutInflater;


        public ItemViewHolder(@NonNull View itemView, Context context, LayoutInflater layoutInflater) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            menuName = itemView.findViewById(R.id.original_menu);
            altCropName = itemView.findViewById(R.id.pred_menu);

            this.context = context;
            this.layoutInflater = layoutInflater;

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    BadCropMenuDTO clickedItem = badCropMenuDTOList.get(position);
                    ShowBottomSheet(clickedItem);
                }
            });
        }


        public void onBind(BadCropMenuDTO badCropMenuDTO) {
            try {
                Log.e("HTTP", "setBadCropMenu onBind" + badCropMenuDTO.getDay() + " " + badCropMenuDTO.getFood_name() + " " + badCropMenuDTO.getAlt_crop_name());
                day.setText(String.valueOf(badCropMenuDTO.getDay()));
                menuName.setText(badCropMenuDTO.getFood_name());
                altCropName.setText(badCropMenuDTO.getAlt_crop_name());
            } catch (Exception e) {
                Log.e("BadCropMenuItemAdapter", "Error in onBind: " + e.getMessage(), e);
            }

        }

        private void ShowBottomSheet(BadCropMenuDTO badCropMenuDTO) {
            BottomSheetCropPredBinding bottomSheetBinding = BottomSheetCropPredBinding.inflate(layoutInflater);
            BottomSheetDialog dialog = new BottomSheetDialog(context);
            dialog.setContentView(bottomSheetBinding.getRoot());

            bottomSheetBinding.bottomSheetDay.setText(String.valueOf(badCropMenuDTO.getDay()));
            bottomSheetBinding.bottomSheetFoodName.setText(badCropMenuDTO.getFood_name());
            bottomSheetBinding.bottomSheetOriginalMenu.setText(badCropMenuDTO.getBad_crop_name());
            bottomSheetBinding.bottomSheetPredMenu.setText(badCropMenuDTO.getAlt_crop_name());

            dialog.setCanceledOnTouchOutside(true);
            dialog.create();
            dialog.show();
        }
    }

}
