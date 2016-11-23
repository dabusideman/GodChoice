package godchoose.com.activity;

import android.view.View;

import java.util.ArrayList;

import godchoose.com.R;
import godchoose.com.adapter.ProductDetailLeaveMessageAdapter;
import godchoose.com.adapter.SimleProductDetailPicsAdapter;
import godchoose.com.bean.DataVo;
import godchoose.com.bean.FragmentDataDetailVo;
import godchoose.com.service.imp.RemoteImpl;
import godchoose.com.util.LogUtil;
import godchoose.com.util.MyAsyncTask;
import godchoose.com.widget.MyListView;


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
public class ProduceDetailActivity extends MyBaseActivity {
    private MyListView myListViewPic,myListViewMessage;
    private ArrayList<FragmentDataDetailVo> listData;
    private SimleProductDetailPicsAdapter simleProductDetailPicsAdapter;


    private ArrayList<String > listLeaveMessage;
    private ProductDetailLeaveMessageAdapter productDetailLeaveMessageAdapter;

    @Override
    public void initTitle() {
    }

    @Override
    public View initView() {
        fl_home_content.setVisibility(View.GONE);
        View view = View.inflate(this, R.layout.produce_detail, null);
        myListViewPic = (MyListView) view.findViewById(R.id.lv_pic_produce);
        myListViewMessage= (MyListView) view.findViewById(R.id.lv_leave_message);

        listData = new ArrayList<>();
        simleProductDetailPicsAdapter = new SimleProductDetailPicsAdapter(this, listData);
        myListViewPic.setAdapter(simleProductDetailPicsAdapter);


        listLeaveMessage = new ArrayList<>();
        productDetailLeaveMessageAdapter = new ProductDetailLeaveMessageAdapter(this, listLeaveMessage);
        myListViewMessage.setAdapter(productDetailLeaveMessageAdapter);
        return view;
    }

    @Override
    public void initData() {
        getProductDetail();
    }

    @Override
    public void addListener() {

    }


    private void getProductDetail() {
        new MyAsyncTask<Void, Void, DataVo>(this, false) {

            @Override
            public DataVo before(Void... params) throws Exception {
                // TODO Auto-generated method stub
                return RemoteImpl.getInstance().getProductDetail(3 + "");
            }

            @Override
            public void after(DataVo result) {
                // TODO Auto-generated method stub

                listData.clear();
                listData.addAll(result.data.blogs);
                simleProductDetailPicsAdapter.notifyDataSetChanged();
                LogUtil.e("size=" + listData.size());
                fl_home_content.setVisibility(View.VISIBLE);
            }

            @Override
            public void exception() {
                // TODO Auto-generated method stub
            }
        }.execute();
    }

}
