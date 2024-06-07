package com.example.woi_fe.Diet.ItemMove;

import android.content.ClipData;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.R;
import com.example.woi_fe.databinding.DietItemBinding;

public class ItemMoveCallback extends ItemTouchHelper.Callback{

    private ItemMoveListener listener;

    public ItemMoveCallback(ItemMoveListener listener) {
        this.listener = listener;

    }

    /*어느방향으로 움직일건지*/
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int drag_flags = ItemTouchHelper.UP|ItemTouchHelper.DOWN;
        return makeMovementFlags(drag_flags,0);
    }

    /*어느위치에서 어느위치로 변경하는지*/
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        ItemMoveAdapter.ItemViewHolder holder = (ItemMoveAdapter.ItemViewHolder) viewHolder;

        if(holder.lastTouchView != null && holder.lastTouchView.getId() == R.id.diet_item_list_btn){
            return listener.onItemMove(viewHolder.getAdapterPosition(),target.getAdapterPosition());
        }else{
            return false;
        }
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

}
