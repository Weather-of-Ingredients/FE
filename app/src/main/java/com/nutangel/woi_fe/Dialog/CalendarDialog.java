package com.nutangel.woi_fe.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.nutangel.woi_fe.databinding.DialogCalendarBinding;

import java.util.Calendar;

public class CalendarDialog extends Dialog {

    private DialogCalendarBinding binding;
    private CalendarDialogCallbackListener calendarDialogCallbackListener;

    public CalendarDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DialogCalendarBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setCanceledOnTouchOutside(false);
        setCancelable(false);

        //날짜 범위 제한
        setDateInit();
        //완료 버튼 선택 시
        setCompleteButtonClickListener();
    }

    private void setCompleteButtonClickListener() {
        binding.completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //날짜 얻어오기
                int setYear = binding.setDatepicker.getYear();
                int setMonth = binding.setDatepicker.getMonth() + 1;
                Log.d("HTTP", "setYear: "+ setYear + " setMonth: " + setMonth);

                calendarDialogCallbackListener.dialogCallbackListener(setYear, setMonth);
                dismiss();
            }
        });
    }

    private void setDateInit() {
        //현재 날짜
        Calendar maxMonth = Calendar.getInstance();

        //이번 달에 3을 더하여 설정.
        maxMonth.add(Calendar.MONTH, 3);

        // DatePicker의 최대 날짜를 다음 달의 마지막 날로 설정
        binding.setDatepicker.setMaxDate(maxMonth.getTimeInMillis());

    }

    public interface CalendarDialogCallbackListener{
        void dialogCallbackListener(int Year, int Month);
    }

    public void setCalendarDialogCallbackListener(CalendarDialogCallbackListener callbackListener){
        this.calendarDialogCallbackListener = callbackListener;
    }
}
