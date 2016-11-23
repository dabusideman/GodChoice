package xuezhangyouhuo.com.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.adapter.ImageScaleAdapter;
import xuezhangyouhuo.com.bean.FragmentDataDetailVo;
import xuezhangyouhuo.com.photoview.HackyViewPager;

/**
 * Created by peter on 15/11/20.
 */
public class ImageScaleXueZhangActivity extends Activity {

    private ArrayList<FragmentDataDetailVo> listData=new ArrayList<>();

    private HackyViewPager viewPager;
    private ImageScaleAdapter adapter;
    private TextView tvCurrent;
    int currentItem = 0;
    private ImageView iv_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listData = (ArrayList<FragmentDataDetailVo>) getIntent().getSerializableExtra("imageList");
        setContentView(R.layout.activity_image_scale_xuezhang);
        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        iv_loading = (ImageView) findViewById(R.id.iv_loading);
        tvCurrent = (TextView) findViewById(R.id.tv_current);
        tvCurrent.setText("1/"+listData.size());
        AnimationDrawable background = (AnimationDrawable) iv_loading.getBackground();
        background.start();

        adapter = new ImageScaleAdapter(listData, ImageScaleXueZhangActivity.this, iv_loading);
        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(currentItem);//默认选中传递过来的
//        tv_name.setText((currentItem + 1) + "/" + urlList.size());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(listData.size()>0){

                    tvCurrent.setText((position + 1) + "/" + listData.size());
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
