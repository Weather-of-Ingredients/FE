package com.example.woi_fe.Diet.ItemMove;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static androidx.core.content.ContentProviderCompat.requireContext;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.R;

import java.util.ArrayList;

public class ItemMoveAdapter extends RecyclerView.Adapter<ItemMoveAdapter.ItemViewHolder> implements ItemMoveListener {

    private ArrayList<String> list = new ArrayList<>();

    public ItemMoveAdapter() {

    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_diet_update_menu, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(String str) {
        //추후에 추가 시
        list.add(str);
    }


    @Override
    public boolean onItemMove(int from_position, int to_position) {
        String str = list.get(from_position);
        list.remove(from_position);
        list.add(to_position, str);
        notifyItemMoved(from_position, to_position);
        return true;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener, TextView.OnEditorActionListener, TextWatcher {
        EditText text;
        ImageView list_btn;
        ImageView plus_btn;
        ImageView delete_btn;
        View lastTouchView;
        private boolean isEditing = false;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            text = itemView.findViewById(R.id.diet_item_name);
            list_btn = itemView.findViewById(R.id.diet_item_list_btn);
            plus_btn = itemView.findViewById(R.id.diet_item_plus_btn);
            delete_btn = itemView.findViewById(R.id.diet_item_delete_btn);

            text.setOnEditorActionListener(this);
            text.addTextChangedListener(this);
            plus_btn.setOnClickListener(this);
            delete_btn.setOnClickListener(this);
            list_btn.setOnTouchListener(this);

        }

        public void onBind(String str) {
            text.setText(str);
        }


        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.diet_item_plus_btn) {
                String newStr = new String("");
                list.add(getAdapterPosition() + 1, newStr);
                notifyItemInserted(getAdapterPosition() + 1);
            } else if (view.getId() == R.id.diet_item_delete_btn) {
                list.remove(getAdapterPosition());
                notifyItemRemoved(getAdapterPosition());
            }

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                lastTouchView = view; // 터치된 뷰를 기록
            }
            return false; // false를 반환하여 다른 이벤트 처리와의 충돌을 피함
        }

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE) {
                try {
                    InputMethodManager inputMethodManager = (InputMethodManager) textView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputMethodManager != null) {
                        inputMethodManager.hideSoftInputFromWindow(textView.getWindowToken(), 0);
                    }
                    textView.setCursorVisible(false);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error hiding keyboard: " + e.getMessage());
                }
                return true;
            }
            return false;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            isEditing = true;
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}



