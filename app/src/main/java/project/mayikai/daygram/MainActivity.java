package project.mayikai.daygram;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


import project.mayikai.item.Item;

public class MainActivity extends Activity {
    ListView lv;
    Button add;
    TextView dayinweek;
    Button preview;
    Button backToMain;
    EditText previewText;
    ArrayList<Item> mylist;
    //定义一个startActivityForResult（）方法用到的整型值
    private final int RequestCode = 1500;
    String rs;  //用来存放edit_activity回传的值
    int rp; //用来存放当前点击item的position
    String[] tempStr;
    Spinner chooseMonth;
    Spinner chooseYear;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ABAdapter abAdapter;
        String[] month = new String[]{"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY"
                , "JUNE", "JULY", "AUGUST", "SPETEMBER", "OCTOBER", "NOVERBER", "DECEMBER"};
        String[] year = new String[]{"2011", "2012", "2013", "2014", "2015", "2016"};

        chooseMonth = (Spinner) findViewById(R.id.choosemonth);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, month);
        chooseMonth.setAdapter(adapter1);
        chooseMonth.setSelection(8);

        chooseYear = (Spinner) findViewById(R.id.chooseyear);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, year);
        chooseYear.setAdapter(adapter2);
        chooseYear.setSelection(5);

        try {
            tempStr = (String[]) getObject("diary.dat");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (tempStr == null) {
            tempStr = new String[31];
            try {
                for (int i = 1; i <= 30; i++) {
                    tempStr[i] = null;
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }

       /* try {
            mylist = (ArrayList<Item>) getObject("object.dat");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        if (null == mylist) {
            mylist = new ArrayList<Item>();
        }*/

        mylist = new ArrayList<Item>();
        //初始化页面，创立30个item
        for (int i = 1; i <= 30; i++) {
            Item item1 = new Item();
            item1.setDay(Integer.toString(i));
            String temp = "2016-09-" + Integer.toString(i);
            String week = getWeek(temp);
            item1.setWeekday(week);
            item1.setDiary(tempStr[i]);
            mylist.add(item1);
        }
        //实现点击item进行跳转功能，并将当前日期传给edit_activity
        lv = (ListView) findViewById(R.id.lv);
        abAdapter = new ABAdapter(MainActivity.this, mylist);
        lv.setAdapter(abAdapter);
        saveObject("object.dat");
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, edit_activity.class);
                bundle.putSerializable("day", Integer.toString(i + 1));
                String wd = "2016-09-" + Integer.toString(i + 1);
                String WEEKDAY = getWeek(wd);
                bundle.putSerializable("weekday", WEEKDAY);
                bundle.putSerializable("diary", tempStr[i + 1]);
                intent.putExtras(bundle);
                startActivityForResult(intent, RequestCode);
            }
        });

        //点击底部“+”按钮进行跳转，新建立编辑日记
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, edit_activity.class);
                bundle.putSerializable("day", Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
                String wd = "2016-09-" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
                String WEEKDAY = getWeek(wd);
                bundle.putSerializable("weekday", WEEKDAY);
                bundle.putSerializable("diary", tempStr[c.get(Calendar.DAY_OF_MONTH)]);
                intent.putExtras(bundle);
                startActivityForResult(intent, RequestCode);
            }
        });

         /*
         *实现长按删除功能
         */
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                //定义AlertDialog.Builder对象，当长按列表项的时候弹出确认删除对话框
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("是否确定删除");
                builder.setTitle("提示");

                //添加AlertDialog.Builder对象的setPositiveButton()方法
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        tempStr[position + 1] = null;
                        saveStr("diary.dat");
                        onCreate(savedInstanceState);
                    }
                });

                //添加AlertDialog.Builder对象的setNegativeButton()的方法
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();
                return false;
            }
        });



        preview = (Button) findViewById(R.id.preview);
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.preview_page);
                for (int i = 1; i <= 30; i++) {
                    if (tempStr[i] != null) {
                        String wd = "2016-09-" + Integer.toString(i);
                        String weekday = getWeek(wd);
                        if (weekday.equals("Mon"))
                            weekday = "Monday";
                        if (weekday.equals("Tue"))
                            weekday = "Tuesday";
                        if (weekday.equals("Wed"))
                            weekday = "Wednesday";
                        if (weekday.equals("Thu"))
                            weekday = "Thursday";
                        if (weekday.equals("Fri"))
                            weekday = "Friday";
                        if (weekday.equals("Sat"))
                            weekday = "Saturday";
                        if (weekday.equals("Sun")) {
                            weekday = "Sunday";
                        }
                        previewText = (EditText) findViewById(R.id.previewText);
                        previewText.append(Integer.toString(i) + "/" + weekday + "/" + tempStr[i]
                                + "\n\n");
                    }
                }
                backToMain = (Button) findViewById(R.id.backToMain);
                backToMain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCreate(savedInstanceState);
                    }
                });
            }
        });
    }


    /**
     * 接受当前Activity跳转后，目标Activity关闭后的回传值
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case RESULT_OK: {  //接收并显示edit_activity传过来的值
                Bundle bundle = data.getExtras();
                rs = bundle.getString("rs");
                rp = Integer.parseInt(bundle.getString("day"));
                break;
            }
            default:
                break;
        }
        mylist = new ArrayList<>();
        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, edit_activity.class);
                bundle.putSerializable("day", Integer.toString(c.get(Calendar.DAY_OF_MONTH)));
                String wd = "2016-09-" + Integer.toString(c.get(Calendar.DAY_OF_MONTH));
                String WEEKDAY = getWeek(wd);
                bundle.putSerializable("weekday", WEEKDAY);
                bundle.putSerializable("diary", tempStr[c.get(Calendar.DAY_OF_MONTH)]);
                intent.putExtras(bundle);
                startActivityForResult(intent, RequestCode);
            }
        });
        for (int i = 1; i <= 30; i++) {
            Item item2 = new Item();
            item2.setDay(Integer.toString(i));
            String temp = "2016-09-" + Integer.toString(i);
            String week = getWeek(temp);
            item2.setWeekday(week);
            if (i == rp) {
                if (!rs.equals(""))
                    tempStr[i] = rs;
            }
            item2.setDiary(tempStr[i]);
            mylist.add(item2);
        }
        ABAdapter abAdapter;
        lv = (ListView) findViewById(R.id.lv);
        abAdapter = new ABAdapter(MainActivity.this, mylist);
        lv.setAdapter(abAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(MainActivity.this, edit_activity.class);
                bundle.putSerializable("day", Integer.toString(i + 1));
                String wd = "2016-09-" + Integer.toString(i + 1);
                String WEEKDAY = getWeek(wd);
                bundle.putSerializable("weekday", WEEKDAY);
                bundle.putSerializable("diary", tempStr[i + 1]);
                intent.putExtras(bundle);
                startActivityForResult(intent, RequestCode);
            }
        });
        saveObject("object.dat");
        saveStr("diary.dat");
    }


   /* public void updataItem(Bundle savedInstanceState){
        int firstvisible = lv.getFirstVisiblePosition();
        int lastvisibale = lv.getLastVisiblePosition();
        if(position>=firstvisible&&position<=lastvisibale){
            View view = lv.getChildAt(position - firstvisible);
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            viewHolder.diary.setText(rs);
        }
    }
    */

    //根据当前日期获取星期
    private String getWeek(String pTime) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            //TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "Sun";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "Mon";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "Tue";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "Wed";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "Thu";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "Fri";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "Sat";
        }
        return Week;
    }

    //存放list
    private void saveObject(String name) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(mylist);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    //存放tempStr数组即日记内容
    private void saveStr(String name) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = this.openFileOutput(name, MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(tempStr);
        } catch (Exception e) {
            e.printStackTrace();
            //这里是保存文件产生异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    //fos流关闭异常
                    e.printStackTrace();
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    //oos流关闭异常
                    e.printStackTrace();
                }
            }
        }
    }

    private Object getObject(String name) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = this.openFileInput(name);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            //这里是读取文件产生异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    //fis流关闭异常
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    //ois流关闭异常
                    e.printStackTrace();
                }
            }
        }
        //读取产生异常，返回null
        return null;
    }
}