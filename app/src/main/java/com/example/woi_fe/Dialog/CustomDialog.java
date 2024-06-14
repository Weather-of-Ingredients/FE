package com.example.woi_fe.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.woi_fe.R;
import com.example.woi_fe.databinding.DialogCustomBinding;

public class CustomDialog extends Dialog implements View.OnClickListener {

    private DialogCustomBinding binding;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogCustomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        binding.dialogCustomNoBtn.setOnClickListener(this);
        binding.dialogCustomYesBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.dialog_custom_no_btn){
            dismiss();
        }else if(view.getId() == R.id.dialog_custom_yes_btn){
            //수정되지 않은 채로 dismiss 그리고 DietUpdateFragment도 dismiss
        }else{

        }
    }
}
