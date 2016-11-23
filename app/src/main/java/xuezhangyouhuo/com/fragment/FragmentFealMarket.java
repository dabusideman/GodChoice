package xuezhangyouhuo.com.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.adapter.ViewPagerAdapter;
import xuezhangyouhuo.com.widget.ScrollControllViewPager;

public class FragmentFealMarket extends Fragment {
    private TextView tvOrg,tvStudents;
    private FealMarketOrgFragment fealMarketOrgFragment;
    private FealMarketStudentsFragment fealMarketStudentsFragment;
    private ScrollControllViewPager viewPager;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feal_market, null);
        setupView(view);
        addListener();
        setBarColor(0);
        setViewPager();
        return view;
    }

    private void setupView(View view) {
        viewPager = (ScrollControllViewPager) view.findViewById(R.id.viewpager);
        tvOrg= (TextView) view.findViewById(R.id.tv_org);
        tvStudents= (TextView) view.findViewById(R.id.tv_students);
    }


    private void addListener() {
        // TODO Auto-generated method stub

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        setBarColor(0);
                        break;
                    case 1:
                        setBarColor(1);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tvOrg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
            }
        });
        tvStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
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
    private void setViewPager() {
        List<Fragment> fragments = new ArrayList<Fragment>();
        // init Fragment
        fealMarketOrgFragment = new FealMarketOrgFragment();
        fealMarketStudentsFragment = new FealMarketStudentsFragment();
        fragments.add(fealMarketOrgFragment);
        fragments.add(fealMarketStudentsFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManger(),
                fragments);
        viewPager.setAdapter(viewPagerAdapter);
    }
    public FragmentManager getSupportFragmentManger() {
        // TODO Auto-generated method stub
        return getActivity().getSupportFragmentManager();
    }

    private void setBarColor(int i) {
        switch (i) {
            case 0:
                tvOrg.setTextColor(this.getResources().getColor(R.color.black));
                tvStudents.setTextColor(this.getResources().getColor(R.color.yellow));
                break;
            case 1:
                tvOrg.setTextColor(this.getResources().getColor(R.color.yellow));
                tvStudents.setTextColor(this.getResources().getColor(R.color.black));
                break;

        }
    }

}
