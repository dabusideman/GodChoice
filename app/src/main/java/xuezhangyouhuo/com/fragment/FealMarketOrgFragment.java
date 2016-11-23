package xuezhangyouhuo.com.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.adapter.FealMarketAdapter;
import xuezhangyouhuo.com.bean.DataVo;
import xuezhangyouhuo.com.bean.FragmentDataDetailVo;
import xuezhangyouhuo.com.service.imp.RemoteImpl;
import xuezhangyouhuo.com.util.MyAsyncTask;




public class FealMarketOrgFragment extends Fragment {
    private View footView;

    private int pageindex = 1;
    private ArrayList<FragmentDataDetailVo> listData;
    private FealMarketAdapter adapter;
    private PullToRefreshListView pull_refresh_list;


    Handler myHandler = new Handler() {
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            FragmentDataDetailVo foot=new FragmentDataDetailVo();
            listData.add(foot);
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
        View view = inflater.inflate(R.layout.fragment_market_org, null);
        setupView(view);
        getFealOrgData(1);
        addListener();
        return view;
    }

    private void setupView(View view) {

        pull_refresh_list = (PullToRefreshListView) view.findViewById(R.id.pull_refresh_listview);
        listData = new ArrayList<>();
        adapter = new FealMarketAdapter(getActivity(), listData);
        pull_refresh_list.setAdapter(adapter);


    }


    private void addListener() {
        pull_refresh_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                listData.clear();
               getFealOrgData(1);
                pageindex=1;
            }
        });
        pull_refresh_list.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
//                ToastUtil.showToast("LAST");
                getFealOrgData(2);
            }
        });
        pull_refresh_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


            }
        });
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


    private void getFealOrgData(final int type){
        new MyAsyncTask<Void, Void, DataVo>(getActivity(),false) {

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
                    }else {
                        listData.addAll(result.data.blogs);
                        myHandler.sendEmptyMessage(0);
                    }
                    pull_refresh_list.onRefreshComplete();
                }

                if (2 == type) {
                    listData.remove(listData.size()-1);
                    listData.addAll(result.data.blogs);
                    myHandler.sendEmptyMessage(0);
                }
                pageindex++;
            }

            @Override
            public void exception() {
                // TODO Auto-generated method stub
                pull_refresh_list.onRefreshComplete();
            }
        }.execute();
    }
}
