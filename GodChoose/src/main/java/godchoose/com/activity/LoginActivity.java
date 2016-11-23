package godchoose.com.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import godchoose.com.R;
import godchoose.com.activity.GodChoice.GodChoiceMainActivity;


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
public class LoginActivity extends MyBaseActivity {
    private Button btLogin;

    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_land, null);
        btLogin= (Button) view.findViewById(R.id.btn_login);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this, GodChoiceMainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


}
