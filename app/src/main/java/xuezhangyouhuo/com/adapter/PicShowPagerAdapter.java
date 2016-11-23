package xuezhangyouhuo.com.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import xuezhangyouhuo.com.bean.ShowBigPicVoDemo;
import xuezhangyouhuo.com.globle.ImageLoaderOptions;


public class PicShowPagerAdapter extends PagerAdapter {
	private List<View> mViewList;
	private Activity context;
	private int positionId;
	private PopupWindow pop;
	private String type;
//	private String image_type;
	private List<ShowBigPicVoDemo> list;
	private String mSaveMessage;
	private final static String ALBUM_PATH = Environment.getExternalStorageDirectory() + "/xuezhang/tupian/";
	Bitmap defaultPic;
	private int scollPosition;
	public PicShowPagerAdapter(Activity context, List<View> viewList, int position, List<ShowBigPicVoDemo> list, String type) {
		mViewList = viewList;
		this.context = context;
//		positionValue = new boolean[viewList.size()];
		this.positionId = position;
		this.list = list;
		this.type = type;
	}

	@Override
	public int getCount() {
		if (mViewList == null) {
			return 0;
		}
		return mViewList.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		ViewPager viewPager = (ViewPager) container;
		viewPager.removeView(mViewList.get(position));
	}
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		ViewPager viewPager = (ViewPager) container;
		viewPager.addView(mViewList.get(position));
		//afinal对图片做了缩放，设置了最大为屏幕宽度。
//		final String imgUrlBig = list.get(position).getImg().replace("small", "big");
//		final String imgUrlBig = list.get(position).getImg().replace("small", "big");
//		fb.display(((ImageView) mViewList.get(position)), list.get(position).getUploadUrl(),defaultPic,defaultPic);
		ImageLoader.getInstance().displayImage(list.get(position).getUploadUrl(),((ImageView) mViewList.get(position)), ImageLoaderOptions.list_options);
		return mViewList.get(position);
	}
	

}
