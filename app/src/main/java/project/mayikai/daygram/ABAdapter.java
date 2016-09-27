package project.mayikai.daygram;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.annotation.RequiresPermission;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import project.mayikai.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2016/9/18.
 */
public class ABAdapter extends BaseAdapter {
    //itemA类的type标志
    private static final int TYPE_A = 0;
    //itemB类的type标志
    private static final int TYPE_B = 1;

    private Context context;

    String week;

    //整合数据
    private List<Item> data = new ArrayList<>();


    public ABAdapter(Context context, ArrayList<Item> list) {
        this.context = context;

        //把数据装载同一个list里面
        //这里把所有数据都转为object类型是为了装载同一个list里面好进行排序
      this.data = list;

    }

    /**
     * 获得itemView的type
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        int result = 0;
        if (data.get(position).getDiary() == null) {
            result = TYPE_A;
        } else {
            result = TYPE_B;
        }
        return result;
    }

    /**
     * 获得有多少中view type
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //创建两种不同种类的viewHolder变量
        ViewHolder1 holder1 = null;
        ViewHolder2 holder2 = null;
        //根据position获得View的type
        int type = getItemViewType(position);
        if (convertView == null) {
            //实例化
            holder1 = new ViewHolder1();
            holder2 = new ViewHolder2();
            //根据不同的type 来inflate不同的item layout
            //然后设置不同的tag
            //这里的tag设置是用的资源ID作为Key
            switch (type) {
                case TYPE_A:
                    convertView = View.inflate(context, R.layout.list_item_a, null);
                    holder1.point = (ImageView) convertView.findViewById(R.id.point);
                    holder1.weekday = (TextView) convertView.findViewById(R.id.weekday);
                    convertView.setTag(R.id.tag_first, holder1);
                    break;
                case TYPE_B:
                    convertView = View.inflate(context, R.layout.list_item_b, null);
                    holder2.day = (TextView) convertView.findViewById(R.id.day);
                    holder2.weekday = (TextView) convertView.findViewById(R.id.weekday);
                    holder2.diary = (TextView) convertView.findViewById(R.id.diary);
                    convertView.setTag(R.id.tag_second, holder2);
                    break;
            }

        } else {
            //根据不同的type来获得tag
            switch (type) {
                case TYPE_A:
                    holder1 = (ViewHolder1) convertView.getTag(R.id.tag_first);
                    break;
                case TYPE_B:
                    holder2 = (ViewHolder2) convertView.getTag(R.id.tag_second);
                    break;
            }
        }

        Item o = data.get(position);
        //根据不同的type设置数据
        switch (type) {
            case TYPE_A:
                if(o.getWeekday().equals("Sun"))
                {
                    holder1.point.setBackground(context.getResources().getDrawable(R.drawable.redpoint));
                }
                else
                {
                    holder1.point.setBackground(context.getResources().getDrawable(R.drawable.point));
                }
                break;

            case TYPE_B:
                holder2.day.setText(o.getDay());
                if(o.getWeekday().equals("Sun"))
                {
                    holder2.weekday.setTextColor(Color.RED);
                }else{
                    holder2.weekday.setTextColor(Color.BLACK);
                }
                holder2.weekday.setText(o.getWeekday());
                holder2.diary.setText(o.getDiary());
                break;
        }
        return convertView;
    }
    
    /**
     * item A 的Viewholder
     */
    private static class ViewHolder1 {
        ImageView point;
        TextView weekday;
    }

    /**
     * item B 的Viewholder
     */
    private static class ViewHolder2 {
        TextView day;
        TextView weekday;
        TextView diary;
    }

}