package com.example.woi_fe.ui.Diet.ItemMove;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface ItemMoveListener {
    boolean onItemMove(int from_position, int to_position);
}
