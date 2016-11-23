package godchoose.com.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import godchoose.com.R;

/**
 * Created by peter on 15/9/28.
 *    //左侧点击图片
 *    protected TextView iv_home_leftview;
 *    //右侧点击图片
 *    protected TextView iv_home_rightview;
 *    //中间的文本
 *    protected TextView tv_home_title;
 *    //下面的内容
 *    protected FrameLayout fl_home_content;
 *    //替换的title
 *    protected FrameLayout fl_home_title;
 *    //title的内容
 *    protected RelativeLayout rl_home_title;
 *    //私有吐司点击时可以替换掉上次的吐司
 *    private static Toast toast;
 *
 */
public abstract class MyBaseActivity extends FragmentActivity {
    //左侧点击图片
    protected ImageView iv_home_leftview;
    //右侧点击图片
    protected ImageView iv_home_rightview;
    //中间的文本
    protected TextView tv_home_title;
    //右边的文本
    protected TextView tv_home_rightview;
    //下面的内容
    protected FrameLayout fl_home_content;
    //替换的title
    protected FrameLayout fl_home_title;
    //左右点击区域
    protected FrameLayout fl_home_leftview;
    protected FrameLayout fl_home_rightview;
    //title的内容
    protected RelativeLayout rl_home_title;
    protected RelativeLayout rl_home_root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
         //初始化
        init();
        //初始化title
        initTitle();
        //添加view对象
        fl_home_content.addView(initView());
        //初始化数据
        initData();

        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void init() {
        iv_home_leftview = (ImageView) findViewById(R.id.iv_home_leftview);
        iv_home_rightview = (ImageView) findViewById(R.id.iv_home_rightview);
        tv_home_title = (TextView) findViewById(R.id.tv_home_title);
        tv_home_rightview = (TextView) findViewById(R.id.tv_home_rightview);
        fl_home_content = (FrameLayout) findViewById(R.id.fl_home_content);
        fl_home_title = (FrameLayout) findViewById(R.id.fl_home_title);

        fl_home_leftview = (FrameLayout) findViewById(R.id.fl_home_leftview);
        fl_home_rightview = (FrameLayout) findViewById(R.id.fl_home_rightview);
        rl_home_title = (RelativeLayout) findViewById(R.id.rl_home_title);
        rl_home_root = (RelativeLayout) findViewById(R.id.rl_home_root);

        fl_home_leftview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private static Toast toast;


    public  abstract void initTitle();

    public  abstract View initView();

    public  abstract void initData();

    public  abstract void addListener();

}
