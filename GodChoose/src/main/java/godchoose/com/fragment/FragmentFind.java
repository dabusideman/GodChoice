package godchoose.com.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.tsz.afinal.FinalBitmap;

import godchoose.com.R;
import godchoose.com.activity.XueZhangMainActivity;

public class FragmentFind extends Fragment {

	private FinalBitmap fb;
	private Bitmap bmp;
	private int pagenum = 1;// 加载数据的页数
	private boolean flag = true;// 刷新和上拉加载更多 是否能加载数据
	
	public static  NewsReceiver receiver;
	IntentFilter filter = new IntentFilter("NEWS");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			receiver = new NewsReceiver();
			getActivity().registerReceiver(receiver, filter);
		} catch (Exception e) {

		}
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_find, null);
		setupView(view);
		addListener();
		return view;
	}

	@Override
	public void onDestroyOptionsMenu() {
		// TODO Auto-generated method stub
		super.onDestroyOptionsMenu();
		getActivity().unregisterReceiver(receiver);
	}
	private void getData() {
//		Toast.makeText(getActivity(), "NEWS", 0).show();
		XueZhangMainActivity.isNews=false;
		// TODO Auto-generated method stub
//		new MyAsyncTask<Void, Void, PinglunListVo>(this, false) {
//
//			@Override
//			public PinglunListVo before(Void... params) throws Exception {
//				// TODO Auto-generated method stub
//				// return RemoteImpl0.getInstance().getRemarkList(type,
//				// objectId, pagenum+"");
//				return RemoteImpl.getInstance().getRemarkList(remarkType,
//						pagenum + "", 10 + "");
//			}
//
//			@Override
//			public void after(PinglunListVo result) {
//				// TODO Auto-generated method stub
//				mPullToRefreshView.onFooterRefreshComplete();
//				mPullToRefreshView.onHeaderRefreshComplete();
//				flag = true;
//				if (result.getResCode().equals("0000")) {
//					pinglunList.addAll(result.getRemarkList());
//					pagenum++;
//				} else {
//					showShortToastMessage(result.getResDesc());
//				}
//				getMessage(result);
//				// tvWupinglun.setVisibility(View.VISIBLE);
//			}
//
//			@Override
//			public void exception() {
//				// TODO Auto-generated method stub
//				Log.i("aaaaa", "===========");
//				mPullToRefreshView.onFooterRefreshComplete();
//				mPullToRefreshView.onHeaderRefreshComplete();
//			}
//		}.execute();
	}


	private void addListener() {
		// TODO Auto-generated method stub
		
	}


	private void setupView(View view) {}

	@Override
	public void onDestroy() {
		   super.onDestroy();  
	}
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
	}
	

	/**
	 * 
	 * @author LC_22
	 * 	接收主界面的加载数据广播
	 */
	class NewsReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
		 getData();
		}

	}


}
