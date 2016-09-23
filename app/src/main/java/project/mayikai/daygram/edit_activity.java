package project.mayikai.daygram;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import project.mayikai.item.Item;

/**
 * Created by Administrator on 2016/9/20.
 */
public class edit_activity extends Activity {
    TextView editDate;
    TextView editWeek;
    Button addTime;
    EditText editText;
    Button done;

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.edit_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        editText = (EditText) findViewById(R.id.editText);
        editDate = (TextView) findViewById(R.id.editDate);
        editWeek = (TextView) findViewById(R.id.editWeek);
        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String weekday = bundle.getString("weekday");
        final String day = bundle.getString("day");
        String diary = bundle.getString("diary");

        try {
            if (weekday.equals("Mon"))
                weekday = "MONDAY";
            if (weekday.equals("Tue"))
                weekday = "TUESDAY";
            if (weekday.equals("Wed"))
                weekday = "WEDNESDAY";
            if (weekday.equals("Thu"))
                weekday = "THURSDAY";
            if (weekday.equals("Fri"))
                weekday = "FRIDAY";
            if (weekday.equals("Sat"))
                weekday = "SATURDAY";
            if (weekday.equals("Sun")) {
                weekday = "SUNDAY";
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        editWeek.setText(weekday);
        if (weekday.equals("SUNDAY")) {
            editWeek.setTextColor(Color.RED);
        } else {
            editWeek.setTextColor(Color.BLACK);
        }
        String date = "/" + "SEPTEMBER" + " " + day + "/" + "2016";
        editDate.setText(date);
        editText.setText(diary);
        /*Item s = (Item) getItem("Item.dat");
        try{
            editText.setText(s.getDiary());
        }catch(NullPointerException e){
            e.printStackTrace();
        }*/

        addTime = (Button) findViewById(R.id.addTime);
        addTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Time t = new Time();
                String time = "";
                t.setToNow();
                int hour = t.hour;
                int minute = t.minute;
                if (hour >= 0 && hour <= 12) {
                    time += Integer.toString(hour) + ":" + Integer.toString(minute) + "am";
                    editText.append(time);
                } else if (hour > 12 && hour <= 23) {
                    time += Integer.toString(hour - 12) + ":" + Integer.toString(minute) + "pm";
                    editText.append(time);
                }
            }
        });
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {  //采用Intent绑定Bundle的形式回传值
                //新建一个Bundle，Bundle主要放值类型
                Bundle bundle1 = new Bundle();
                String diaryContent = editText.getText().toString();
                bundle1.putString("rs", diaryContent);
                bundle1.putString("position", day);
                //将Bundle赋给Intent
                intent.putExtras(bundle1);
                //跳转回MainActivity
                //注意下面的RESULT_OK常量要与回传接收的Activity中onActivityResult()方法一致
                edit_activity.this.setResult(RESULT_OK, intent);
                //关闭当前Activity
                edit_activity.this.finish();
            }
        });
    }


    /*
   *获取系统时间，并将日期进行格式转换
    */
    public void getDate() {
        TextView editDate;
        int year;
        int month;
        int day;
        int weekday;
        editDate = (TextView) findViewById(R.id.editDate);
        Calendar calendar = Calendar.getInstance();
        String today = null;
        String thisMonth = null;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekday == 2) {
            today = "MONDAY";
        } else if (weekday == 3) {
            today = "TUESDAY";
        } else if (weekday == 4) {
            today = "WEDNESDAY";
        } else if (weekday == 5) {
            today = "tHURSDAY";
        } else if (weekday == 6) {
            today = "FRIDAY";
        } else if (weekday == 7) {
            today = "SATURDAY";
        } else {
            today = "SUNDAY";
        }
        if (month + 1 == 1) {
            thisMonth = "JANUARY";
        } else if (month + 1 == 2) {
            thisMonth = "FEBUARY";
        } else if (month + 1 == 3) {
            thisMonth = "MARCH";
        } else if (month + 1 == 4) {
            thisMonth = "APRIL";
        } else if (month + 1 == 5) {
            thisMonth = "MAY";
        } else if (month + 1 == 6) {
            thisMonth = "JUNE";
        } else if (month + 1 == 7) {
            thisMonth = "JULY";
        } else if (month + 1 == 8) {
            thisMonth = "AUGUST";
        } else if (month + 1 == 9) {
            thisMonth = "SEPTEMBER";
        } else if (month + 1 == 10) {
            thisMonth = "OCTOBER";
        } else if (month + 1 == 11) {
            thisMonth = "NOVEMBER";
        } else {
            thisMonth = "DECEMBER";
        }
        String date = today + "/" + thisMonth + " " + day + "/" + year;
        editDate.setText(date);
    }
}
