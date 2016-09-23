package project.mayikai.item;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/19.
 */
public class Item implements Serializable{
    private String day;   //日期
    private String weekday;
    private String diary; //日记

    public String getDay(){
        return this.day;
    }

    public void setDay(String day){
        this.day = day;
    }


    public String getWeekday(){
        return this.weekday;
    }

    public void setWeekday(String weekday)
    {
        this.weekday = weekday;
    }

    public String getDiary(){
        return this.diary;
    }

    public void setDiary(String diary){
        this.diary = diary;
    }
}
