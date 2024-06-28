package com.example.woi_fe.Dialog;

import android.app.AlertDialog;
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
    private DialogCallbackListener callbackListener;
    


    public CustomDialog(@NonNull Context context, DialogCallbackListener callbackListener) {
        super(context);
        this.callbackListener = callbackListener;
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
            callbackListener.dialogCallbackListener(false);//변경 없이 나가기 -> 계속 수정
        }else if(view.getId() == R.id.dialog_custom_yes_btn){
            callbackListener.dialogCallbackListener(true);//변경 없이 창을 닫기 -> 아예 종료
        }
        dismiss();
    }


    public interface DialogCallbackListener{
        void dialogCallbackListener(boolean isDialogResult);
    }

    public void setCallbackListener(DialogCallbackListener dialogCallbackListener){
        this.callbackListener = dialogCallbackListener;
    }
}
