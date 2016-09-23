package project.mayikai.daygram;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/9/22.
 */
public class MyTextType extends TextView {
    public MyTextType(Context context){
        super(context);
        init();
    }
    public MyTextType(Context context,AttributeSet attrs){
        super(context,attrs);
        init();
    }

    public MyTextType(Context context,AttributeSet attrs,int defStyle){
        super(context,attrs,defStyle);
        init();
    }

    private void init(){
        Typeface myType = Typeface.createFromAsset(getContext().getAssets(),"myTextType.ttf");
        setTypeface(myType);
    }
}
