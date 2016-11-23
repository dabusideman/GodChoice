package xuezhangyouhuo.com.widget;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.util.bitmaputil.BitmapUtiles;


public class BottomPreservationImage extends Dialog implements View.OnClickListener {

    private  String url;
    private RoundImageView iv_card;
    private Activity context;
    private TextView tv_preservation, tv_cancel;

    public BottomPreservationImage(Context context, boolean cancelable,
                                   OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public BottomPreservationImage(Context context, int theme) {
        super(context, theme);
        initUI();
    }

    public BottomPreservationImage(Activity context, String url) {
        this(context, R.style.quick_option_dialog);
//		super(context);
        this.context = context;
        this.url = url;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setGravity(Gravity.BOTTOM);

        WindowManager windowManager = getWindow().getWindowManager();

        Display defaultDisplay = windowManager.getDefaultDisplay();

        LayoutParams attributes = getWindow().getAttributes();

        attributes.width = defaultDisplay.getWidth();

        getWindow().setAttributes(attributes);

    }

    private void initUI() {
        View view = getLayoutInflater().inflate(R.layout.dialog_preservation, null);
        tv_preservation = (TextView) view.findViewById(R.id.tv_preservation);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        tv_preservation.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

//		initAnimation();

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        view.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                BottomPreservationImage.this.dismiss();
                return true;
            }
        });
        super.setContentView(view);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_preservation:
                new Thread(){
                    @Override
                    public void run() {
                        Bitmap image = BitmapUtiles.getBitmapFromURL(url);
                        BitmapUtiles.saveImageToGallery(getContext(),image);
                    }
                }.start();


                break;
        }

        BottomPreservationImage.this.dismiss();
    }
}
