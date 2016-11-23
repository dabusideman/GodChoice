package xuezhangyouhuo.com.activity;

import android.view.View;

import xuezhangyouhuo.com.R;


/**
 * //左侧点击图片
 * protected TextView iv_home_leftview;
 * //右侧点击图片
 * protected TextView iv_home_rightview;
 * //中间的文本
 * protected TextView tv_home_title;
 * //下面的内容
 * protected FrameLayout fl_home_content;
 * //替换的title
 * protected FrameLayout fl_home_title;
 * //title的内容
 * protected RelativeLayout rl_home_title;
 * //私有吐司点击时可以替换掉上次的吐司
 * private static Toast toast;
 */
public class ShowPhotosActivity extends MyBaseActivity {


    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_base, null);

        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {

    }


}
