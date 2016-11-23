package xuezhangyouhuo.com.activity;

import android.hardware.Camera;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.List;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.bean.BaseBeans;
import xuezhangyouhuo.com.service.imp.RemoteImpl;
import xuezhangyouhuo.com.util.MyAsyncTask;


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
public class OpenMyFlashLightActivity extends MyBaseActivity {
    Camera camera = Camera.open();//闪光灯


    private Button btControlFlashLight;
    private boolean isOpen = false;

    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        camera.startPreview();
        View view = View.inflate(this, R.layout.activity_open_my_flashlight, null);
        btControlFlashLight = (Button) view.findViewById(R.id.bt_control_flashlight);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        btControlFlashLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUID();
                if (isOpen) {//此时是开着的，执行关闭
                    turnLightOff(camera);
                    btControlFlashLight.setText("暗着的");
                    btControlFlashLight.setBackgroundResource(R.drawable.btn_enter_shape);
                    isOpen = false;
                } else {
                    turnLightOn(camera);
                    btControlFlashLight.setText("亮着的");
                    btControlFlashLight.setBackgroundResource(R.drawable.btn_enter_selector);
                    isOpen = true;
                }


            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        camera.release();
    }

    public static void turnLightOn(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        // Check if camera flash exists
        if (flashModes == null) {
            // Use the screen as a flashlight (next best thing)
            return;
        }
        String flashMode = parameters.getFlashMode();
        if (!Camera.Parameters.FLASH_MODE_TORCH.equals(flashMode)) {
            // Turn on the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_TORCH)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
            } else {
            }
        }
    }

    public static void turnLightOff(Camera mCamera) {
        if (mCamera == null) {
            return;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        if (parameters == null) {
            return;
        }
        List<String> flashModes = parameters.getSupportedFlashModes();
        String flashMode = parameters.getFlashMode();
        // Check if camera flash exists
        if (flashModes == null) {
            return;
        }
        if (!Camera.Parameters.FLASH_MODE_OFF.equals(flashMode)) {
            // Turn off the flash
            if (flashModes.contains(Camera.Parameters.FLASH_MODE_OFF)) {
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
            } else {
                Log.e("ssss", "FLASH_MODE_OFF not supported");
            }
        }
    }

    private void getUID(){
        new MyAsyncTask<Void, Void, BaseBeans>(this,false) {
            @Override
            public BaseBeans before(Void... params) throws Exception {
                return RemoteImpl.getInstance().getUID("Lannith");
            }

            @Override
            public void after(BaseBeans baseBeans) {

            }

            @Override
            public void exception() {

            }
        }.execute();
    }
}
