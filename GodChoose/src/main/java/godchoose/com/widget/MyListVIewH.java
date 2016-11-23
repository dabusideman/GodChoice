package godchoose.com.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import godchoose.com.globle.GodchooseApplication;
import godchoose.com.util.PxToDp;

/**
 * Created by peter on 16/1/14.
 */
public class MyListVIewH extends ListView{

    private float xDOWN;
    private RightlickListener rightlickListener;

    public MyListVIewH(Context context) {
        super(context);
    }

    public MyListVIewH(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListVIewH(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    public void setRightlickListener(RightlickListener rightlickListener){
        this.rightlickListener =rightlickListener;
    }
    public interface  RightlickListener{
        void OnRightlickListener();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                xDOWN = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float xMOVE = ev.getX();
                if (xDOWN-xMOVE> PxToDp.dip2px(GodchooseApplication.context, 50)){
                    xDOWN=0;
                    if (rightlickListener!=null){
                        rightlickListener.OnRightlickListener();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                xDOWN=0;
                break;
        }
        return super.onTouchEvent(ev);
    }
}
