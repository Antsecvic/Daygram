package project.mayikai.daygram;

import android.app.Activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getDate();
    }

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
        }else if (weekday == 4) {
            today = "WEDNESDAY";
        }
        else if (weekday == 5) {
            today = "tHURSDAY";
        }
        else if (weekday == 6) {
            today = "FRIDAY";
        }else if (weekday == 7) {
            today = "SATURDAY";
        }else{
            today = "SUNDAY";
        }
        if(month + 1 == 1){
            thisMonth = "JANUARY";
        }else if(month + 1 == 2){
            thisMonth = "FEBUARY";
        }else if(month + 1 == 3){
            thisMonth = "MARCH";
        }else if(month + 1 == 4){
            thisMonth = "APRIL";
        }else if(month + 1 == 5){
            thisMonth = "MAY";
        }else if(month + 1 == 6){
            thisMonth = "JUNE";
        }else if(month + 1 == 7){
            thisMonth = "JULY";
        }else if(month + 1 == 8){
            thisMonth = "AUGUST";
        }else if(month + 1 == 9){
            thisMonth = "SEPTEMBER";
        }else if(month + 1 == 10){
            thisMonth = "OCTOBER";
        }else if(month + 1 == 11){
            thisMonth = "NOVEMBER";
        }else{
            thisMonth = "DECEMBER";
        }
        editDate.setText(today + "/" + thisMonth + " " + day + "/" + year);
    }

}
