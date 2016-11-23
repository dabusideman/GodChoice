package xuezhangyouhuo.com.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.zxing.CaptureActivity;


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
public class HoloMainActivity extends MyBaseActivity {
    private TextView tvGoErWeima,tvGoFlashLight,tvCreateErW;


    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_holomain, null);
        tvGoErWeima= (TextView) view.findViewById(R.id.tv_go_erweima);
        tvGoFlashLight= (TextView) view.findViewById(R.id.tv_go_flashlight);
        tvCreateErW= (TextView) view.findViewById(R.id.tv_go_create_erweima);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        tvGoErWeima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoloMainActivity.this.startActivity(new Intent(HoloMainActivity.this, CaptureActivity.class));
            }
        });
        tvGoFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoloMainActivity.this.startActivity(new Intent(HoloMainActivity.this, OpenMyFlashLightActivity.class));
            }
        });
        tvCreateErW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HoloMainActivity.this.startActivity(new Intent(HoloMainActivity.this, CreateErWeiMaActivity.class));
            }
        });
    }


}
