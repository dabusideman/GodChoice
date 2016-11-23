package godchoose.com.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import godchoose.com.R;

public class FragmentCenter extends Fragment  {
	private TextView tvGoLogin;
	private LinearLayout lyNotLogin,lyYesLogin;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_center, null);
		setupView(view);
		addListener();
		return view;
	}

	@Override
	public void onDestroyOptionsMenu() {
		// TODO Auto-generated method stub
		super.onDestroyOptionsMenu();
	}
	private void setupView(View view) {
		tvGoLogin= (TextView) view.findViewById(R.id.tv_go_login);
		lyNotLogin= (LinearLayout) view.findViewById(R.id.ly_not_login);
		lyYesLogin= (LinearLayout) view.findViewById(R.id.ly_yes_login);
		lyYesLogin.setVisibility(View.GONE);
	}

	private void getData() {
		// TODO Auto-generated method stub
	}
	
	private void addListener() {
		// TODO Auto-generated method stub
		tvGoLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				lyNotLogin.setVisibility(View.GONE);
				lyYesLogin.setVisibility(View.VISIBLE);
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
}
