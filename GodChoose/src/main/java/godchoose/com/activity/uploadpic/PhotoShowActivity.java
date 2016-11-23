package godchoose.com.activity.uploadpic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import godchoose.com.R;
import godchoose.com.adapter.PicShowPagerAdapter;
import godchoose.com.bean.ShowBigPicVoDemo;
import godchoose.com.globle.ImageLoaderOptions;
import godchoose.com.util.LogUtil;


/**
 *  查看大图
 */
public class PhotoShowActivity extends Activity {
	private String uploadMaterialId;//当前图片的id 删除所用
//	private ArrayList<ShowBigPicVoDemo> list;
	private List<ShowBigPicVoDemo> list;
	private ViewPager viewPager;
//	private ShowBigPicListVoDemo picItemVo;
	private List<View> viewList = new ArrayList<View>();
	private String type;
	private String imageUrl;
	private String imageUrlBig;
	private ImageView[] imageViews;

	private LinearLayout mViewPoints;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_gallery);
		mViewPoints = (LinearLayout)findViewById(R.id.viewGroup);
		Intent intent = getIntent();
		final int positionId = intent.getIntExtra("POSITION", 0);
		list=new ArrayList<ShowBigPicVoDemo>();
		list=(ArrayList<ShowBigPicVoDemo>) intent.getSerializableExtra("DETAIL");
		uploadMaterialId=list.get(positionId).getUploadMaterialId();
//		Bundle bundle = intent.getExtras();
//		picItemVo = (ShowBigPicVoDemo) bundle.getSerializable("DETAIL");
//		picItemVo =(ShowBigPicListVoDemo) intent.getSerializableExtra("DETAIL");
//		type = intent.getStringExtra("type");
//		String image_type = picItemVo.getImg_type();
//		list = new ArrayList<ShowBigPicVoDemo>();
//		for(ShowBigPicVoDemo imgPathVo : picItemVo.getUploadMaterialList()){
//			list.add(imgPathVo);
//		}
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		initDate();
		PicShowPagerAdapter adapter = new PicShowPagerAdapter(this,viewList,positionId,list,type);
		
		initPoint(positionId);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(positionId, true);
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				uploadMaterialId=list.get(position).getUploadMaterialId();
				LogUtil.i("position==" + uploadMaterialId);
				for (int i = 0; i < imageViews.length; i++) {
					imageViews[position]
							.setBackgroundResource(R.drawable.point_pressed);
					
					
					// 不是当前选中的page，其小圆点设置为未选中的状态
					if (position != i) {
						imageViews[i]
								.setBackgroundResource(R.drawable.point_normal);
					}
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int position) {
			}
		});
		addListener();
	}
	/**
	 * 
	 * @author zpx
	 * @createdate 2014-4-18 下午3:34:27
	 * @Description: 底部圆点
	 * @param positionId
	 *
	 */
	private void initPoint(int positionId) {
		imageViews = new ImageView[viewList.size()];
		for (int i = 0; i < viewList.size(); i++) {
			ImageView imageView = new ImageView(this);
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
					10, 10);
			layoutParams.setMargins(5, 0, 5, 0);
			imageView.setLayoutParams(layoutParams);
			imageViews[i] = imageView;
			if (i == positionId) {
				imageViews[i]
						.setBackgroundResource(R.drawable.point_pressed);
			} else {
				imageViews[i]
						.setBackgroundResource(R.drawable.point_normal);
			}

			// 将imageviews添加到小圆点视图组
			mViewPoints.addView(imageViews[i]);
		}
	}

	private void initDate() {
		if(list == null){
			return;
		}
		ImageView imageView = null;
		for(int i = 0;i<list.size();i++){
			imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
//			imageView.setScaleType(ScaleType.CENTER_CROP);
			imageView.setAdjustViewBounds(true);
			DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
			imageView.setMaxWidth(displayMetrics.widthPixels);
			imageView.setMaxHeight(displayMetrics.heightPixels);
//			FinalBitmap.create(this).display(imageView, list.get(i).getUploadUrl());
			ImageLoader.getInstance().displayImage(list.get(i).getUploadUrl(),imageView, ImageLoaderOptions.list_options);
			viewList.add(imageView);
		}
	}
	
	private void addListener(){
	}
	
}
