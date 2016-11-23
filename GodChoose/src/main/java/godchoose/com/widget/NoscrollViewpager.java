package godchoose.com.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by peter on 15/9/28.
 */
public class NoscrollViewpager extends ViewPager {

    public NoscrollViewpager(Context context) {
        super(context);
    }

    public NoscrollViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 把ViewPager默认的滑动效果去掉
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    // 去掉ViewPager默认的拦截事件机制，不拦截所有事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

}
