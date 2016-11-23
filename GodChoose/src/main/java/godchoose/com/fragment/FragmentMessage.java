package godchoose.com.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import godchoose.com.R;
import godchoose.com.activity.XueZhangMainActivity;

public class FragmentMessage extends Fragment  {
	public static VideoReceiver receiver;
	IntentFilter filter = new IntentFilter("VIDEO");
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		try {
			receiver = new VideoReceiver();
			getActivity().registerReceiver(receiver, filter);
		} catch (Exception e) {

		}
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_message, null);
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
	private void setupView(View view) {
	}

	private void getData() {
		// TODO Auto-generated method stub
//		Toast.makeText(getActivity(), "VIDEO", 0).show();
		XueZhangMainActivity.isVedio=false;
	}
	
	private void addListener() {
		// TODO Auto-generated method stub
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
	/**
	 * 
	 * @author LC_22
	 * 	接收主界面的加载数据广播
	 */
	class VideoReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context arg0, Intent arg1) {
		 getData();
		}

	}
}
