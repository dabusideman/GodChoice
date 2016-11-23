package godchoose.com.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HorizontalViewPager extends ViewPager {

	private int downY;
	private int downX;
	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {
		super(context);
	}
	// 处理事件
	/**
	 * 上下滑动，自己不处理事件，让父容器处理事件
	 * 左右滑动
	 * 		1、当显示第0页时，手指从左往右，自己不处理事件，让父容器处理事件
	 * 		2、当显示最后一页时，手指从右往左，自己不处理事件，让父容器处理事件
	 * 		3、其他情况，自己处理事件，父容器不处理事件
	 */
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 保证父容器把后面的事件，比如move。。传递下拉
			getParent().requestDisallowInterceptTouchEvent(true);
			downY = (int) ev.getY();
			downX = (int) ev.getX();
			break;
		case MotionEvent.ACTION_MOVE:
			int moveY = (int) ev.getY();
			int moveX = (int) ev.getX();
			int diffX = moveX - downX;// x轴移动的距离
			int diffY = moveY - downY;// y轴移动的距离 
			if(Math.abs(diffX)>Math.abs(diffY)){// 左右滑动
				// 1、当显示第0页时，手指从左往右，自己不处理事件，让父容器处理事件
				if(getCurrentItem()==0&&diffX>0){
					getParent().requestDisallowInterceptTouchEvent(false);// 让父容器处理事件
				}
				// 2、当显示最后一页时，手指从右往左，自己不处理事件，让父容器处理事件
				else if(getCurrentItem()==getAdapter().getCount()-1&&diffX<0){
					getParent().requestDisallowInterceptTouchEvent(false);// 让父容器处理事件
				}
				// 3、其他情况，自己处理事件，父容器不处理事件
				else{
					getParent().requestDisallowInterceptTouchEvent(true);// 不让父容器处理事件
				}
				
			}else{// 上下滑动，自己不处理事件，让父容器处理事件
				getParent().requestDisallowInterceptTouchEvent(false);// 让父容器处理事件
			}
			
			break;

		default:
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

}
