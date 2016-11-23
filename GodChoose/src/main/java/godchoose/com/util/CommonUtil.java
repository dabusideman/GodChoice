package godchoose.com.util;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import godchoose.com.globle.GodchooseApplication;


public class CommonUtil {
	/**
	 * 将指定子view从他爹中移除
	 * @param child
	 */
	public static void removeSelfFromParent(View child){
		if(child!=null){
			ViewParent parent = child.getParent();
			if(parent!=null && parent instanceof ViewGroup){
				ViewGroup group = (ViewGroup) parent;
				group.removeView(child);//移除子view
			}
		}
	}
	/**
	 * 在子线程更新ui的方法
	 */
	public static void runOnUiThread(Runnable r){
		GodchooseApplication.getMyHanlder().post(r);
	}

	public static Resources getResources(){
		return GodchooseApplication.getContext().getResources();
	}
	
	/**
	 * 获取字符串资源
	 * @param resId
	 * @return
	 */
	public static String getString(int resId){
		return getResources().getString(resId);
	}
	/**
	 * 获取图片资源
	 * @param resId
	 * @return
	 */
	public static Drawable getDrawable(int resId){
		return getResources().getDrawable(resId);
	}
	/**
	 * 获取颜色资源
	 * @param resId
	 * @return
	 */
	public static int getColor(int resId){
		return getResources().getColor(resId);
	}
	/**
	 * 获取dimens资源
	 * @param resId
	 * @return
	 */
	public static float getDimens(int resId){
		return getResources().getDimension(resId);
	}
	/**
	 * 获取字符串数组
	 * @param resId
	 * @return
	 */
	public static String[] getStringArray(int resId){
		return getResources().getStringArray(resId);
	}
}
