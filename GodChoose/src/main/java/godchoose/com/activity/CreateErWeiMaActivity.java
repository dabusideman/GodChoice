package godchoose.com.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import godchoose.com.R;
import godchoose.com.util.JsonUtil;
import godchoose.com.util.LogUtil;
import godchoose.com.util.SaveToMap;
import godchoose.com.util.StringUtil;
import godchoose.com.util.ToastUtil;


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
public class CreateErWeiMaActivity extends MyBaseActivity {
    public static boolean savepic=true;
    private EditText etPassword,etMessage,etNamePic;
    private ImageView imgErWeiMa;
    private RelativeLayout ryErWeiMa;
    private TextView tvSaveAsPic,tvShare;
    private int QR_WIDTH=300, QR_HEIGHT=300;
    private boolean isHaveErWeiMa=false;
    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        View view = View.inflate(this, R.layout.activity_create_erweima, null);
        etPassword= (EditText) view.findViewById(R.id.et_password_create);
        etMessage= (EditText) view.findViewById(R.id.et_message_create);
        imgErWeiMa= (ImageView) view.findViewById(R.id.img_erweima);
        ryErWeiMa= (RelativeLayout) view.findViewById(R.id.ry_erweima);
        tvSaveAsPic= (TextView) view.findViewById(R.id.tv_save_as_pic);
        tvShare= (TextView) view.findViewById(R.id.tv_share);
        return view;
    }

    @Override
    public void initData() {

    }

    @Override
    public void addListener() {
        tv_home_rightview.setText("生成");
        tv_home_rightview.setVisibility(View.VISIBLE);
        iv_home_rightview.setVisibility(View.GONE);
        fl_home_rightview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPassword.getText().toString().trim();
                String message = etMessage.getText().toString();

                if (StringUtil.isNullOrEmpty(password)) {
                    //若密码为空,password=-1
                    password = "-1";
                } else {
                    //若密令不为空
                    if (password.length() < 6) {
                        ToastUtil.showToast("若设置密码，则为6位");
                        return;
                    }
                }

                createQRImage(getJson(password, message));
            }
        });
        tvSaveAsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHaveErWeiMa==false){
                    ToastUtil.showToast("请先生成您的密语码");
                    return;
                }
                hideSoftInputView();
                if (savepic) {
                    savepic = false;
                    ryErWeiMa.invalidate();
                    SaveToMap.savepic(CreateErWeiMaActivity.this, ryErWeiMa);
                }
            }
        });
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHaveErWeiMa==false){
                    ToastUtil.showToast("请先生成您的密语码");
                    return;
                }


            }
        });
    }

    /**
     *  转化为json字符串
     * @param password
     * @param message
     * @return
     */
    private String getJson(String password,String message){
        String json="";
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("password", password);
        params.put("message", message);
        json= JsonUtil.parseMapToJson(params);
        LogUtil.e("json=" + json);
        return json;
    }

    /**
     * 生成二维码
     *
     * @param
     */
    public void createQRImage(String url) {
        try {
            //判断URL合法性
            if (url == null || "".equals(url) || url.length() < 1) {
                return;
            }
            Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
            hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
            //图像数据转换，使用了矩阵转换
            BitMatrix bitMatrix = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, QR_WIDTH, QR_HEIGHT, hints);
            int[] pixels = new int[QR_WIDTH * QR_HEIGHT];
            //下面这里按照二维码的算法，逐个生成二维码的图片，
            //两个for循环是图片横列扫描的结果
            for (int y = 0; y < QR_HEIGHT; y++) {
                for (int x = 0; x < QR_WIDTH; x++) {
                    if (bitMatrix.get(x, y)) {
//						if(listURL!=null){
//							pixels[y * QR_WIDTH + x] = 0xffffffff;
//						}else{
//
//							pixels[y * QR_WIDTH + x] = 0xff000000;
//						}
                        pixels[y * QR_WIDTH + x] = 0xff000000;//黑色
                    } else {
//                        pixels[y * QR_WIDTH + x] = 0xffffffff;//透明点0x00ffffff,白点为0xffffffff;
                    }
                }
            }
            //生成二维码图片的格式，使用ARGB_8888
            Bitmap bitmap = Bitmap.createBitmap(QR_WIDTH, QR_HEIGHT, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, QR_WIDTH, 0, 0, QR_WIDTH, QR_HEIGHT);
            //显示到一个ImageView上面
            imgErWeiMa.setImageBitmap(bitmap);
            isHaveErWeiMa=true;
            bitmap = null;
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this
                .getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
