package xuezhangyouhuo.com.globle;

import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.util.PxToDp;

/**
 * Created by peter on 15/9/28.
 */
public class ImageLoaderOptions {

    // 在listview中使用的设置
    public static DisplayImageOptions list_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.default_image)
            .showImageForEmptyUri(R.drawable.default_image)
            .showImageOnFail(R.drawable.default_image).cacheInMemory(true)
            .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
            .displayer(new FadeInBitmapDisplayer(0)).build();// 渐渐显示的动画

        //viewpager
        public static DisplayImageOptions list_options_300 = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_image)
                .showImageForEmptyUri(R.drawable.default_image)
                .showImageOnFail(R.drawable.default_image).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(600)).build();// 渐渐显示的动画

    // .displayer(new RoundedBitmapDisplayer(28)).build();//圆角图片   // 在listview中使用的设置

        public static DisplayImageOptions list_options_default_white = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_pic_white)
                .showImageForEmptyUri(R.drawable.default_pic_white)
                .showImageOnFail(R.drawable.default_pic_white).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(0)).build();// 渐渐显示的动画

        // 在listview中使用的设置
        public static DisplayImageOptions list_optionsList = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_icon)
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(0)).build();// 渐渐显示的动画
        // .displayer(new RoundedBitmapDisplayer(28)).build();//圆角图片   // 在listview中使用的设置

    public static DisplayImageOptions round_options = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.drawable.default_icon)
            .showImageForEmptyUri(R.drawable.default_icon)
            .showImageOnFail(R.drawable.default_icon).cacheInMemory(true)
            .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
           // .displayer(new FadeInBitmapDisplayer(500)).build();// 渐渐显示的动画
            .displayer(new RoundedBitmapDisplayer(100)).build();//圆角图片

    // 在ViewPager中使用的设置
    public static DisplayImageOptions pager_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.ic_launcher)
            .showImageOnFail(R.drawable.ic_launcher)
            .resetViewBeforeLoading(true)//在加载图片之前情况ImageView中的图片
            .cacheOnDisk(true)
            .imageScaleType(ImageScaleType.EXACTLY)//设置缩放类型，会按照ImageView真实的宽高进行缩放
            .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的rgb显示模式，会让图片显示比较高清，而且占用内存较小
            .considerExifParams(true)
            .displayer(new FadeInBitmapDisplayer(500)).build();

        public static DisplayImageOptions list_options_0durtion = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.default_icon)
                .showImageForEmptyUri(R.drawable.default_icon)
                .showImageOnFail(R.drawable.default_icon).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
                .displayer(new FadeInBitmapDisplayer(0)).build();// 渐渐显示的动画

        public static DisplayImageOptions round_options_viewpager = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher).cacheInMemory(true)
                .cacheOnDisk(true).considerExifParams(true)// 会识别图片的方向信息
                        // .displayer(new FadeInBitmapDisplayer(500)).build();// 渐渐显示的动画
                .displayer(new RoundedBitmapDisplayer(PxToDp.dip2px(XuezhangYouHuoApplication.context, 20))).build();//圆角图片
}
