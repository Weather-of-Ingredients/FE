package com.example.woi_fe.ui.home;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.woi_fe.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarItemHolder> {

    private final String TAG = getClass().getSimpleName();
    private ArrayList<Integer> dataList = new ArrayList<>();
    private Context context;
    private LinearLayout calendarLayout;
    private Date date;
    private FurangCalendar furangCalendar;
    private ItemClick itemClick;

    public CalendarAdapter(Context context, LinearLayout calendarLayout, Date date) {
        this.context = context;
        this.calendarLayout = calendarLayout;
        this.date = date;
        this.furangCalendar = new FurangCalendar(date);
        this.furangCalendar.initBaseCalendar();
        this.dataList = furangCalendar.getDataList();
    }

    public interface ItemClick {
        void onClick(View view, int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public CalendarItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_calendar, parent, false);
        return new CalendarItemHolder(view);
    }

    @Override
    public void onBindViewHolder(CalendarItemHolder holder, int position) {
        // list_item_calendar 높이 지정
        int h = calendarLayout.getHeight() / 6;
        holder.itemView.getLayoutParams().height = h;

        holder.bind(dataList.get(position), position, context);

        if (itemClick != null) {
            holder.itemView.setOnClickListener(v -> itemClick.onClick(v, position));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class CalendarItemHolder extends RecyclerView.ViewHolder {
        TextView itemCalendarDateText;

        public CalendarItemHolder(View itemView) {
            super(itemView);
            itemCalendarDateText = itemView.findViewById(R.id.item_calendar_date_text);
        }

        public void bind(int data, int position, Context context) {
            int firstDateIndex = furangCalendar.getPrevTail();
            int lastDateIndex = dataList.size() - furangCalendar.getNextHead() - 1;

            // 날짜 표시
            itemCalendarDateText.setText(String.valueOf(data));

            // 오늘 날짜 처리
            String dateString = new SimpleDateFormat("dd", Locale.KOREA).format(date);
            int dateInt = Integer.parseInt(dateString);
            if (dataList.get(position) == dateInt) {
                itemCalendarDateText.setTypeface(itemCalendarDateText.getTypeface(), Typeface.BOLD);
            }

            // 현재 월의 1일 이전, 현재 월의 마지막일 이후 값의 텍스트를 회색처리
            if (position < firstDateIndex || position > lastDateIndex) {
                itemCalendarDateText.setTextAppearance(context, R.style.LightColorTextViewStyle);
                // Assuming itemCalendarDotView is a view you want to clear the background of
                // Replace 'itemCalendarDotView' with the actual view if necessary
                // itemCalendarDotView.setBackground(null);
            }
        }
    }
}
