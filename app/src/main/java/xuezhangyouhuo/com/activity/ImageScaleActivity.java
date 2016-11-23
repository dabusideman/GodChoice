package xuezhangyouhuo.com.activity;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.adapter.ImageScaleAdapter;
import xuezhangyouhuo.com.bean.DataVo;
import xuezhangyouhuo.com.bean.FragmentDataDetailVo;
import xuezhangyouhuo.com.photoview.HackyViewPager;
import xuezhangyouhuo.com.service.imp.RemoteImpl;
import xuezhangyouhuo.com.util.MyAsyncTask;
import xuezhangyouhuo.com.util.ToastUtil;

/**
 * Created by peter on 15/11/20.
 */
public class ImageScaleActivity extends Activity {
    private LinearLayout ly_top, ly_bottom;

    private DataVo dataVo=new DataVo();
    private ArrayList<FragmentDataDetailVo> listData=new ArrayList<>();
    private int pageindex = 1;

    private ArrayList<String> urlList;
    private ArrayList<String> descList;
    private HackyViewPager viewPager;
    private ImageScaleAdapter adapter;
    private TextView tv_name;
    private TextView tv_desc;
    private TextView tv_next;
    int currentItem = 0;
    private ImageView iv_loading;

    private boolean show = true;

    private DoHide doHide = new DoHide();

    private int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_scale);
        viewPager = (HackyViewPager) findViewById(R.id.viewPager);
        tv_name = (TextView) findViewById(R.id.tv_name);
        iv_loading = (ImageView) findViewById(R.id.iv_loading);
        ly_top = (LinearLayout) findViewById(R.id.ly_top);
        ly_bottom = (LinearLayout) findViewById(R.id.ly_bottom);
        tv_desc = (TextView) findViewById(R.id.tv_description);
        tv_next = (TextView) findViewById(R.id.tv_next_term);
//        urlList = getIntent().getStringArrayListExtra("urlList");
//        descList = getIntent().getStringArrayListExtra("descList");
//        dataVo = (DataVo) getIntent().getSerializableExtra("listData");
//        listData=dataVo.data.blogs;
        AnimationDrawable background = (AnimationDrawable) iv_loading.getBackground();
        background.start();
//        int currentItem = getIntent().getIntExtra("currentItem", 0);
//        String imageUrlBit = getIntent().getStringExtra("imageUrlBit");
//        for (int i = 0; i < urlList.size(); i++) {
//            String s = urlList.get(i);
//            if (s.equals(imageUrlBit)) {
//                currentItem = i;
//            }
//        }

        getData();
        adapter = new ImageScaleAdapter(listData, ImageScaleActivity.this, iv_loading);
        adapter.setHideTopBottomListener(doHide);
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

                    tv_name.setText((position + 1) + "/" + listData.size());
                    tv_desc.setText(listData.get(position).unm + "\n" + listData.get(position).isrc+"");
                    pos = position + 1;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (show) {
                    ly_bottom.setVisibility(View.GONE);
                    ly_top.setVisibility(View.GONE);
                } else {
                    ly_bottom.setVisibility(View.VISIBLE);
                    ly_top.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }


    private void getData() {
        new MyAsyncTask<Void, Void, DataVo>(this) {

            @Override
            public DataVo before(Void... params) throws Exception {
                // TODO Auto-generated method stub
                return RemoteImpl.getInstance().getPicBelongList(pageindex + "");
            }

            @Override
            public void after(DataVo result) {
                // TODO Auto-generated method stub
                if(result.data.blogs.size()>0){
                    if(1==pageindex){

                    listData.addAll(result.data.blogs);
                        adapter.notifyDataSetChanged();
                    pageindex++;

                        tv_name.setText((1) + "/" + listData.size());
                        tv_desc.setText(listData.get(0).unm + "\n" + listData.get(0).isrc);
                    }else {
                        listData.addAll(result.data.blogs);
                        adapter.notifyDataSetChanged();
                        tv_name.setText(pos + "/" + listData.size());
                        pageindex++;

                    }

                }else {
                    ToastUtil.showToast("人家被你看光啦！！！");
                }

            }

            @Override
            public void exception() {
                // TODO Auto-generated method stub

            }
        }.execute();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public class DoHide implements ImageScaleAdapter.HideTopBottomListener {

        @Override
        public void doHide() {
            if (show) {
                ly_top.setVisibility(View.GONE);
                ly_bottom.setVisibility(View.GONE);
                Animation face_out= AnimationUtils.loadAnimation(ImageScaleActivity.this, R.anim.face_out);
                ly_top.startAnimation(face_out);
                ly_bottom.startAnimation(face_out);

                show = false;
            } else {
                ly_top.setVisibility(View.VISIBLE);
                ly_bottom.setVisibility(View.VISIBLE);
                Animation face_in= AnimationUtils.loadAnimation(ImageScaleActivity.this, R.anim.face_in);
                ly_top.startAnimation(face_in);
                ly_bottom.startAnimation(face_in);

                show = true;
            }
        }
    }
}
