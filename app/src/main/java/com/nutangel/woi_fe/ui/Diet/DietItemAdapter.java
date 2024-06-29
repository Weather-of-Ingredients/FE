package com.nutangel.woi_fe.ui.Diet;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nutangel.woi_fe.Retrofit.controller.DietRetrofitAPI;
import com.nutangel.woi_fe.Retrofit.dto.diet.MenuResponseDTO;
import com.nutangel.woi_fe.ui.Diet.ItemMove.ItemMoveListener;
import com.nutangel.woi_fe.R;

import java.util.ArrayList;
import java.util.List;

public class DietItemAdapter extends RecyclerView.Adapter<DietItemAdapter.ItemViewHolder> implements ItemMoveListener {

    private ArrayList<String> list = new ArrayList<>();
    private List<MenuResponseDTO> menuList;
    private AdapterCallback callback;
    private DietRetrofitAPI retrofitAPI;

    public interface AdapterCallback{
        void onItemAdded(int position);
        void onItemRemoved(int position);
        void onItemChanged(int position);
    }

    public DietItemAdapter(AdapterCallback adapterCallback, List<MenuResponseDTO> menuList) {
        this.callback = adapterCallback;
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("DietItemAdapter", "DietItemAdapter 어댑터연결 성공");
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_diet_update_menu, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MenuResponseDTO data = menuList.get(position);
        holder.onBind(data.getFoodName());
        holder.onBind(list.get(position));

//        Call<List<DietResponseDTO>> call = retrofitAPI.getDietByUserAndDate(date);
//
//        call.enqueue(new Callback<List<DietResponseDTO>>() {
//            @Override
//            public void onResponse(Call<List<DietResponseDTO>> call, Response<List<DietResponseDTO>> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    List<DietResponseDTO> diets = response.body();
//
//                } else {
//                    Log.e("HomeFragment", "Response not successful or body is null");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DietResponseDTO>> call, Throwable t) {
//
//            }
//        });
    }

    public void setMenuList(List<MenuResponseDTO> menuList) {
        this.menuList = menuList;
        notifyDataSetChanged();
        Log.d("MyUDietAdapter", menuList.toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /*기본적으로 세팅될 때 사용하는 코드?*/
    public void addItem(String str) {
        //추후에 추가 시
        list.add(str);
        int position = list.size() - 1;
        notifyItemInserted(position);
    }

    /*어플 내에서 삭제 버튼을 눌렀을 때 사용되는 코드*/
    public void removeItem(int position){
        if (position >= 0 && position < list.size()) {
            list.remove(position);
            notifyItemRemoved(position);
            if (callback != null) {
                callback.onItemRemoved(position);
            }
        }
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        String str = list.get(from_position);
        list.remove(from_position);
        list.add(to_position, str);
        notifyItemMoved(from_position, to_position);
        return true;
    }
    /*
     * 팝업 창 뜨는 case 정리
     *
     * case 1
     *   텍스트의 변경이 있을 시 && 키보드 외부 터치
     * case 2
     *   텍스트의 변경이 있을 시 && 키보드의 엔터키 눌렀을 시
     * case 3
     *   plus 버튼 눌렀을 시 && !text.equals("")
     * case 4
     *   delete 버튼 눌렀을 시
     *
     * Fragment로 Change callback이 전송되고
     * Fragment 측에서 callback과 Save btn 클릭 이벤트 점검을 모두 한 후에 팝업 창 등록
     * */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener, TextView.OnEditorActionListener, TextWatcher {
        TextView text;
        ImageView list_btn;
        ImageView plus_btn;
        ImageView delete_btn;
        public View lastTouchView;
        boolean initState;
        String beforeText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            initState = true;

            text = itemView.findViewById(R.id.diet_item_name);
            list_btn = itemView.findViewById(R.id.diet_item_list_btn);
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
            if (view.getId() == R.id.diet_item_delete_btn) {
                removeItem(getAdapterPosition());
            }

        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                lastTouchView = view;
            }
            return false;
        }

        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_DONE) {
                try {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        String newText = text.getText().toString();
                        // 기존 텍스트와 비교
                        if (!TextUtils.equals(newText, list.get(position))) {
                            list.set(position, newText);
                            notifyItemChanged(position); // 필요 시 사용
                            // 변경 사항 콜백 호출
                            if (callback != null) {
                                callback.onItemChanged(position);
                            }
                        }
                    }

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
            if(!TextUtils.equals(charSequence.toString(), "메뉴명")){
                //흰쌀밥으로 변경되면서 false로 바뀜
                beforeText = charSequence.toString();
                Log.d("MainActivity", beforeText);//흰쌀밥
                initState = false;
            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable){
            if(!initState){
                if (callback != null) {
                    callback.onItemChanged(getAdapterPosition());
                }
            }

        }
    }

}



