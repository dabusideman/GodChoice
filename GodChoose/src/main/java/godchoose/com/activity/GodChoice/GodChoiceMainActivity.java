package godchoose.com.activity.GodChoice;

import android.app.Service;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;

import java.util.ArrayList;
import java.util.Random;

import godchoose.com.R;
import godchoose.com.activity.MyBaseActivity;
import godchoose.com.adapter.ChildAdapter;
import godchoose.com.adapter.FaterAdapter;
import godchoose.com.adapter.StellarMapAdapter;
import godchoose.com.dialog.ShowGodChoicDialog;
import godchoose.com.util.LogUtil;
import godchoose.com.widget.randomlayout.StellarMap;


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
public class GodChoiceMainActivity extends MyBaseActivity implements SensorEventListener {
    private RecyclerView rvChild, rvFather;
    private ChildAdapter childAdapter;//上部横向的适配器
    private FaterAdapter faterAdapter;//左部横向的适配器
    private StellarMapAdapter adapter;
    private StellarMap stellarMap;
    private TextView tvClick;
    int i = 1;
    private ArrayList<String> slist = new ArrayList<>();//中部的内容
    private ArrayList<String> listChild = new ArrayList<>();//上部的选项
    private ArrayList<String> listFather = new ArrayList<>();//上部的选项
    private boolean isShow = true;//是否正在显示


    /**
     * 传感器管理类
     */
    private SensorManager sensorManager;
    /**
     * 震动器
     */
    private Vibrator vibrator;

    /**
     * 摇一摇动画
     */
    private AnimationDrawable animationDrawable;
    private TextView animation_yaoyiyao;

    //手机状态时候在摇动中
    private boolean isShake = false;

    //开始摇动
    private static final int SHAKE_START = 1;
    //结束摇动
    private static final int SHAKE_END = 2;
     ShowGodChoicDialog  showGodChoicDialog =new ShowGodChoicDialog(GodChoiceMainActivity.this);
    private boolean doShake=true; //只有在true的时候才会进行震动选择

    /**
     * 处理每次摇动结束的事件
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SHAKE_START:

                    isShake = true;//摇动中
//                    startAnimation();

                    break;
                case SHAKE_END://摇动结束

                    isShake = false;
                    Random rd=new Random();
                    int random = rd.nextInt(listChild.size());
                    showGodChoicDialog.showDialog();
                    doShake=false;
                    showGodChoicDialog.tvGodChoice.setText(listChild.get(random));
                    showGodChoicDialog.tvReshake.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showGodChoicDialog.dialog.dismiss();
                            vibrator.vibrate(new long[]{200, 300, 200, 300, 200}, -1);
                            mHandler.sendEmptyMessageDelayed(SHAKE_END,1200);

                        }
                    });
                    showGodChoicDialog.tvCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showGodChoicDialog.dialog.dismiss();
                            doShake=true;

                        }
                    });
                    break;
                default:
                    break;
            }

        }
    };


    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {



        View view = View.inflate(this, R.layout.godchoice_main_activity, null);
        tvClick = (TextView) view.findViewById(R.id.tv_click);
        stellarMap = (StellarMap) view.findViewById(R.id.stellarMap);
        rvChild = (RecyclerView) view.findViewById(R.id.rv_child);
        rvFather = (RecyclerView) view.findViewById(R.id.rv_father);
        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvChild.setLayoutManager(linearLayoutManager);
        childAdapter = new ChildAdapter(this, listChild);
        rvChild.setAdapter(childAdapter);

        LinearLayoutManager linearLayoutManagerFather = new LinearLayoutManager(this);
        linearLayoutManagerFather.setOrientation(LinearLayoutManager.VERTICAL);
        rvFather.setLayoutManager(linearLayoutManagerFather);
        faterAdapter = new FaterAdapter(this, listFather);
        rvFather.setAdapter(faterAdapter);
        initStellarMap();

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        animation_yaoyiyao = (TextView) findViewById(R.id.yaoyiyao_test1_img);

        return view;
    }

    @Override
    public void initData() {
        listChild.add("火锅");
        listChild.add("包子");
        listChild.add("麻辣烫");
        listChild.add("馒头");
        listChild.add("盖饭");
        listChild.add("披萨");
        listChild.add("烧烤");

        listFather.add("包剪锤");
        listFather.add("早饭");
        listFather.add("午饭");
        listFather.add("晚饭");
        listFather.add("骰子");
        listFather.add("数字");
    }

    @Override
    public void addListener() {
        tvClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i += 1;

                stellarMap.doFling(i % 2);
                doHideOrShowFater();
            }
        });
    }


    private void doHideOrShowFater() {
        if (isShow) {
            float[] f = new float[2];
            f[0] = 0.0F;
            f[1] = -rvFather.getWidth();
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(rvFather, "translationX", f);
            animator1.setInterpolator(new AccelerateDecelerateInterpolator());
            animator1.setDuration(200);
            animator1.start();
            isShow = false;
        } else {
            float[] f = new float[2];
            f[0] = -rvFather.getWidth();
            f[1] = 0F;
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(rvFather, "translationX", f);
            animator1.setDuration(200);
            animator1.setInterpolator(new AccelerateDecelerateInterpolator());
            animator1.start();
            isShow = true;
        }
    }

    /**
     * 初始化飞入飞出动画与点击效果
     */
    private void initStellarMap() {
        for (int i = 0; i < 32; i++) {
            slist.add("" + i + " 话题");
        }
        adapter = new StellarMapAdapter(this, slist);
        stellarMap.setInnerPadding(15, 15, 15, 15);
        stellarMap.setAdapter(adapter);
        stellarMap.setGroup(0, true);//默认显示第0组的数据
        stellarMap.setRegularity(15, 15);//这个值不要太大，也不要太小
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 注册监听器
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(doShake){
        int sensorType = sensorEvent.sensor.getType();
        // values[0]:X轴，values[1]：Y轴，values[2]：Z轴
        float[] values = sensorEvent.values;
//        Log.e("YaoYiYao", "values[0]:" + values[0] + "      values[1]:" + values[0] + "     values[2]" + values[0]);
        if (sensorType == Sensor.TYPE_ACCELEROMETER) {

            if ((Math.abs(values[0]) + Math.abs(values[1]) + Math
                    .abs(values[2])) > 18) {
                // 摇动手机后，再伴随震动提示~~
                if (!isShake) {
                    LogUtil.e("SHAKE");
                    vibrator.vibrate(new long[]{200, 300, 200, 300, 200}, -1);
                    //发送一个空消息，告诉handler已经开始摇动了
//                    mHandler.sendEmptyMessage(SHAKE_START);
                    mHandler.sendEmptyMessage(SHAKE_START);
                    //间隔1.2s,发送一个空消息，告诉handler已经结束摇动了//避免了多次请求处理
//                    mHandler.sendEmptyMessageDelayed(SHAKE_END, 1200);
                    mHandler.sendEmptyMessageDelayed(SHAKE_END,1200);
                }

            }

        }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    // 当传感器精度改变时回调该方法，Do nothing.
    }
}
