package xuezhangyouhuo.com.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.activity.XueZhangMainActivity;
import xuezhangyouhuo.com.adapter.CounterGrideAdapter;
import xuezhangyouhuo.com.bean.DataVo;
import xuezhangyouhuo.com.bean.FragmentDataDetailVo;
import xuezhangyouhuo.com.service.imp.RemoteImpl;
import xuezhangyouhuo.com.util.LogUtil;
import xuezhangyouhuo.com.util.MyAsyncTask;

public class FragmentCounter extends Fragment {
    private MyItemClicListener myItemClicListener;
    public static boolean doFirstData=false;
    private int firstShowPosition = 0;//就标志位

    WindowManager manager;
    private PullToRefreshGridView pullToRefreshGridView;
    private int pageindex = 1;
    private ArrayList<FragmentDataDetailVo> listData;
    private CounterGrideAdapter adapter;
    private LinearLayout lyGoSearch;
    private TextView tvGoTop;
    private boolean goTop = false;


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        manager = getActivity().getWindowManager();
        View view = inflater.inflate(R.layout.fragment_counter, null);
        getActivity().onWindowFocusChanged(true);
        setupView(view);
        addListener();
        return view;
    }


    @Override
    public void onDestroyOptionsMenu() {
        // TODO Auto-generated method stub
        super.onDestroyOptionsMenu();
    }


    private void addListener() {
        // TODO Auto-generated method stub

        lyGoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
            }
        });

        tvGoTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.e("CLICK");

                float al= tvGoTop.getAlpha();
                if(al!=0){
                    pullToRefreshGridView.getRefreshableView().smoothScrollToPosition(0);
                }


            }
        });
//        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<GridView> refreshView) {
//                listData.clear();
//                getFealOrgData(1);
//                pageindex = 1;
//            }
//        });

        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                listData.clear();
                getFealOrgData(1);
                pageindex = 1;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                getFealOrgData(2);
            }
        });

//        pullToRefreshGridView.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//            @Override
//            public void onLastItemVisible() {
//                getFealOrgData(2);
//            }
//        });
        pullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });

        pullToRefreshGridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstShowPosition != firstVisibleItem) {
                    if ((firstVisibleItem - firstShowPosition) >= 1) {
//                        LogUtil.e("上画");

                        ViewPropertyAnimator.animate(tvGoTop).alpha(0).setDuration(100);
                        firstShowPosition = firstVisibleItem;


                        ViewPropertyAnimator.animate(lyGoSearch).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                tvGoTop.setVisibility(View.GONE);
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });



                    }
                }

                if ((firstVisibleItem - firstShowPosition) <= -1) {
//                        LogUtil.e("下画");
                    tvGoTop.setVisibility(View.VISIBLE);
                    ViewPropertyAnimator.animate(tvGoTop).alpha(1).setDuration(100);
                    firstShowPosition = firstVisibleItem;


                }

                if (firstVisibleItem == 0) {
//					lyGoSearch.setVisibility(View.VISIBLE);


                    ViewPropertyAnimator.animate(lyGoSearch).alpha(1).setDuration(300)
                            .start();


                } else if (firstVisibleItem == 2) {
//					lyGoSearch.setVisibility(View.GONE);
                    ViewPropertyAnimator.animate(lyGoSearch).alpha(0).setDuration(300)
                            .start();

                }

            }
        });
//        pullToRefreshGridView.setOnTouchListener(new dishGridViewOnTouchListener());

//        pullToRefreshGridView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                 float yDown = 0;
//                 float yUp =0 ;
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        yDown = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        yUp=event.getY();
//                        LogUtil.e("To Down");
//                        float discs=yUp=yDown;
//                        if(discs>10){
//                            LogUtil.e("To Down");
//                        }else if (discs<-10){
//                            LogUtil.e("To Up");
//                        }
//
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        LogUtil.e( "mLastDeltaY=" + mLastDeltaY);
//                        if (mLastDeltaY < 0 ) {
//
//                        } else {
//                        }
//                        break;
//                }
//                return false;
//            }
//        });

    }


    private void setupView(View view) {
        pullToRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.gv_counter);
        listData = new ArrayList<>();
        adapter = new CounterGrideAdapter(getActivity(), listData);
        pullToRefreshGridView.setAdapter(adapter);
        lyGoSearch = (LinearLayout) view.findViewById(R.id.ly_go_search);
        tvGoTop = (TextView) view.findViewById(R.id.tv_go_top);
        myItemClicListener=new MyItemClicListener();
        XueZhangMainActivity.setClickItemListener(myItemClicListener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }


    private void getFealOrgData(final int type) {
        new MyAsyncTask<Void, Void, DataVo>(getActivity(), false) {

            @Override
            public DataVo before(Void... params) throws Exception {
                // TODO Auto-generated method stub
                return RemoteImpl.getInstance().getPicBelongList(pageindex + "");
            }

            @Override
            public void after(DataVo result) {
                // TODO Auto-generated method stub
                if (1 == type) {
                    if (listData.size() > 0) {

                        if (!listData.get(0).isrc.equals(result.data.blogs.get(0).isrc)) {
                            listData.clear();
                            listData.addAll(result.data.blogs);
                            myHandler.sendEmptyMessage(0);
                        }
                    } else {
                        listData.addAll(result.data.blogs);
                        myHandler.sendEmptyMessage(0);
                    }
                    pullToRefreshGridView.onRefreshComplete();
                }

                if (2 == type) {
//                    listData.remove(listData.size() - 1);
                    pullToRefreshGridView.onRefreshComplete();
                    listData.addAll(result.data.blogs);
                    myHandler.sendEmptyMessage(0);
                }
                pageindex++;
            }

            @Override
            public void exception() {
                // TODO Auto-generated method stub
                pullToRefreshGridView.onRefreshComplete();
            }
        }.execute();
    }




    /**
     * 点击回调实现
     */
    private class MyItemClicListener implements XueZhangMainActivity.ClickItemListener {

        @Override
        public void doClickListener() {
            getFealOrgData(1);
        }
    }
}
