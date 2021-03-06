/**
 * @file XListViewHeader.java
 * @create Apr 18, 2012 5:22:27 PM
 * @author Maxwin
 * @description XListView's header
 */
package godchoose.com.widget.waterfallmy.meview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import godchoose.com.R;


public class XListViewHeader extends LinearLayout {
	private LinearLayout mContainer;
	private ImageView pull_to_refresh_imguu;
	private AnimationDrawable logoAnimation;
	/**
	 * 放大缩小动画
	 */
	private ScaleAnimation scaleAnimationToBig;


//	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	private TextView mHintTextView;
	private int mState = STATE_NORMAL;

	private Animation mRotateUpAnim;
	private Animation mRotateDownAnim;
	
	private final int ROTATE_ANIM_DURATION = 180;
	
	public final static int STATE_NORMAL = 0;
	public final static int STATE_READY = 1;
	public final static int STATE_REFRESHING = 2;

	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	private void initView(Context context) {
		// 初始情况，设置下拉刷新view高度为0
		LayoutParams lp = new LayoutParams(
				LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		pull_to_refresh_imguu= (ImageView) findViewById(R.id.pull_to_refresh_imguu);
		pull_to_refresh_imguu.setVisibility(View.GONE);
		logoAnimation= (AnimationDrawable) pull_to_refresh_imguu.getBackground();

		scaleAnimationToBig=new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		scaleAnimationToBig.setInterpolator(new LinearInterpolator());
		scaleAnimationToBig.setDuration(500);
		scaleAnimationToBig.setFillAfter(true);



//		mArrowImageView = (ImageView)findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView)findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ProgressBar)findViewById(R.id.xlistview_header_progressbar);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	public void setState(int state) {
		if (state == mState) return ;
		
		if (state == STATE_REFRESHING) {	// 显示进度
//			mArrowImageView.clearAnimation();
//			mArrowImageView.setVisibility(View.INVISIBLE);
//			mProgressBar.setVisibility(View.VISIBLE);
			pull_to_refresh_imguu.clearAnimation();
			logoAnimation.start();


		} else {	// 显示箭头图片
//			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.INVISIBLE);
			dissmissWaitingDialog();
		}
		
		switch(state){
		case STATE_NORMAL:
			if (mState == STATE_READY) {
//				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
//				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal);

			pull_to_refresh_imguu.setVisibility(View.GONE);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
//				mArrowImageView.clearAnimation();
//				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
				pull_to_refresh_imguu.setVisibility(View.VISIBLE);
				pull_to_refresh_imguu.clearAnimation();
				pull_to_refresh_imguu.startAnimation(scaleAnimationToBig);
				dissmissWaitingDialog();
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
//			logoAnimation.start();
			break;
			default:
		}
		
		mState = state;
	}
	
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LayoutParams lp = (LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

	public int getVisiableHeight() {
		return mContainer.getHeight();
	}


	public void dissmissWaitingDialog() {
		if (logoAnimation != null && logoAnimation.isRunning()) { // 如果正在运行,就停止
			logoAnimation.stop();
//			pull_to_refresh_imguu.setVisibility(View.GONE);
		}
	}

}
