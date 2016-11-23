package xuezhangyouhuo.com.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import xuezhangyouhuo.com.bean.FragmentDataDetailVo;
import xuezhangyouhuo.com.globle.ImageLoaderOptions;
import xuezhangyouhuo.com.globle.XuezhangYouHuoApplication;
import xuezhangyouhuo.com.photoview.PhotoViewAttacher;
import xuezhangyouhuo.com.widget.BottomPreservationImage;

public class ImageScaleAdapter extends PagerAdapter {
	private final Activity imageScaleActivity;
	private ArrayList<FragmentDataDetailVo> list;
	private ImageView iv_loading;

	public void setHideTopBottomListener(HideTopBottomListener hideTopBottomListener) {
		this.hideTopBottomListener = hideTopBottomListener;
	}

	private HideTopBottomListener hideTopBottomListener;


	public ImageScaleAdapter(ArrayList<FragmentDataDetailVo> list, Activity imageScaleActivity, ImageView iv_loading) {
		super();
		this.list = list;
		this.imageScaleActivity = imageScaleActivity;
		this.iv_loading = iv_loading;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, final int position) {

		ImageView imageView = new ImageView(XuezhangYouHuoApplication.getContext());
		imageView.setScaleType(ScaleType.FIT_XY);
		final PhotoViewAttacher attacher = new  PhotoViewAttacher(imageView);
		attacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float x, float y) {
//				hideTopBottomListener.doHide();
				imageScaleActivity.finish();
			}
		});
		final String s = list.get(position).unm;
		attacher.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				BottomPreservationImage bottomDialog = new BottomPreservationImage(imageScaleActivity, s);
				bottomDialog.setCancelable(true);
				bottomDialog.setCanceledOnTouchOutside(true);
				bottomDialog.show();
				return false;
			}
		});
		ImageLoader.getInstance().displayImage(list.get(position).isrc, imageView, ImageLoaderOptions.list_options, new ImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
			}

			@Override
			public void onLoadingFailed(String imageUri, View view,
										FailReason failReason) {
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				iv_loading.setVisibility(View.GONE);
				attacher.update();//当加载完图片的时候需要更新图片的宽高
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
			}
		});
		
		container.addView(imageView);
		return imageView;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	public interface HideTopBottomListener{
		public void doHide();
	}
}
