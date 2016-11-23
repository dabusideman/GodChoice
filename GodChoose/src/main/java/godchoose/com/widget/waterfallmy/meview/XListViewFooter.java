/**
 * @file XFooterView.java
 * @create Mar 31, 2012 9:33:43 PM
 * @author Maxwin
 * @description XListView's footer
 */
package godchoose.com.widget.waterfallmy.meview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import godchoose.com.R;


public class XListViewFooter extends LinearLayout {
	private ImageView pull_to_refresh_imguu;
	private AnimationDrawable logoAnimation;
	/**
	 * 放大缩小动画
	 */
	private ScaleAnimation scaleAnimationToBig;

	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_LOADING = 2;
	public final static int NO_MORE = 3;
	public final static int ERROR = 4;
	public final static int NO_HINT = 5;

	private Context mContext;

	private View mContentView;
	private View mProgressBar;
	private TextView mHintView;

	public XListViewFooter(Context context) {
		super(context);
		initView(context);
	}

	public XListViewFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public void setState(int state) {
		mProgressBar.setVisibility(View.GONE);
		mHintView.setVisibility(View.INVISIBLE);
		pull_to_refresh_imguu.clearAnimation();
//		pull_to_refresh_imguu.setVisibility(View.GONE);
		if (state == STATE_READY) {
//			mHintView.setVisibility(View.VISIBLE);
//			mHintView.setText(R.string.xlistview_footer_hint_ready);
			pull_to_refresh_imguu.setVisibility(View.VISIBLE);
//			pull_to_refresh_imguu.clearAnimation();
//			pull_to_refresh_imguu.startAnimation(scaleAnimationToBig);
		} else if (state == STATE_LOADING) {
//			mProgressBar.setVisibility(View.VISIBLE);
			logoAnimation.start();

		} else if(state==NO_MORE){
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
			pull_to_refresh_imguu.setVisibility(View.GONE);
			dissmissWaitingDialog();
		}
		else if(state==ERROR){
			mHintView.setText("都被你看光了啦!~~");

		}
		else if(state==NO_HINT){
			mHintView.setVisibility(GONE);

		}
		else {
			mHintView.setVisibility(View.VISIBLE);
			mHintView.setText(R.string.xlistview_footer_hint_normal);
			dissmissWaitingDialog();
			pull_to_refresh_imguu.setVisibility(View.GONE);
		}
	}

	public void setBottomMargin(int height) {
		if (height < 0)
			return;
		LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.bottomMargin = height;
		mContentView.setLayoutParams(lp);
	}

	public int getBottomMargin() {
		LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		return lp.bottomMargin;
	}

	/**
	 * normal status
	 */
	public void normal() {
		mHintView.setVisibility(View.VISIBLE);
		mProgressBar.setVisibility(View.GONE);
		pull_to_refresh_imguu.setVisibility(View.GONE);
	}

	/**
	 * loading status
	 */
	public void loading() {
		mHintView.setVisibility(View.GONE);
//		mProgressBar.setVisibility(View.VISIBLE);
		pull_to_refresh_imguu.setVisibility(View.VISIBLE);
	}

	/**
	 * hide footer when disable pull load more
	 */
	public void hide() {
		LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.height = 0;
		mContentView.setLayoutParams(lp);
	}

	/**
	 * show footer
	 */
	public void show() {
		LayoutParams lp = (LayoutParams) mContentView.getLayoutParams();
		lp.height = LayoutParams.WRAP_CONTENT;
		mContentView.setLayoutParams(lp);
	}

	private void initView(Context context) {
		mContext = context;
		LinearLayout moreView = (LinearLayout) LayoutInflater.from(mContext).inflate(R.layout.xlistview_footer, null);
		addView(moreView);
		moreView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

		mContentView = moreView.findViewById(R.id.xlistview_footer_content);
		mProgressBar = moreView.findViewById(R.id.xlistview_footer_progressbar);
		mHintView = (TextView) moreView.findViewById(R.id.xlistview_footer_hint_textview);
		pull_to_refresh_imguu = (ImageView) moreView.findViewById(R.id.pull_to_refresh_imguu);
		pull_to_refresh_imguu.setVisibility(View.GONE);
		logoAnimation= (AnimationDrawable) pull_to_refresh_imguu.getBackground();

		scaleAnimationToBig=new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimationToBig.setInterpolator(new LinearInterpolator());
		scaleAnimationToBig.setDuration(500);
		scaleAnimationToBig.setFillAfter(true);
	}
	public void dissmissWaitingDialog() {
		if (logoAnimation != null && logoAnimation.isRunning()) { // 如果正在运行,就停止
			logoAnimation.stop();
//			pull_to_refresh_imguu.setVisibility(View.GONE);
		}
	}
}
