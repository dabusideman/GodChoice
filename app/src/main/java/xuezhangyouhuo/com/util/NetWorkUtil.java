package xuezhangyouhuo.com.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author ty
 * @createdate 2012-6-4 下午2:41:24
 * @Description: 网络工具类
 */
public class NetWorkUtil {
	// true 得采用代理上网，false不需要
	// public static boolean ONLYWAP;
	// true 没有网络，false有网络
	public static boolean NO_NETWORK;

	/**
	 * 获取网络连接NetworkInfo对象
	 * 
	 * @param context
	 * @return
	 */
	public static NetworkInfo getNetworkInfo(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netinfo = cm.getActiveNetworkInfo();
		return netinfo;
	}

	/**
	 * 网络类型
	 * 
	 * @param context
	 */
	public static void getNetWorkInfoType(Context context) {
		NetworkInfo networkinfo = getNetworkInfo(context);
		if (networkinfo != null) {
			NO_NETWORK = false;
			/*
			 * if (networkinfo.getTypeName() == "WIFI") { ONLYWAP = false; } if
			 * (networkinfo.getTypeName().equalsIgnoreCase("MOBILE")) { if
			 * ("cmwap".equalsIgnoreCase(networkinfo.getExtraInfo()) ||
			 * "3gwap".equalsIgnoreCase(networkinfo.getExtraInfo()) ||
			 * "ctwap".equalsIgnoreCase(networkinfo.getExtraInfo())) { ONLYWAP =
			 * true; } else { ONLYWAP = false; } }
			 */
		} else {
			NO_NETWORK = true;
		}
	}

	/**
	 * 判断网络
	 * 
	 * @param context
	 */
	public static boolean isNetWork(Context context) {
		NetworkInfo networkinfo = getNetworkInfo(context);
		if (networkinfo == null) {
			NO_NETWORK = true;
			return false;
			/*
			 * if (networkinfo.getTypeName() == "WIFI") { ONLYWAP = false; } if
			 * (networkinfo.getTypeName().equalsIgnoreCase("MOBILE")) { if
			 * ("cmwap".equalsIgnoreCase(networkinfo.getExtraInfo()) ||
			 * "3gwap".equalsIgnoreCase(networkinfo.getExtraInfo()) ||
			 * "ctwap".equalsIgnoreCase(networkinfo.getExtraInfo())) { ONLYWAP =
			 * true; } else { ONLYWAP = false; } }
			 */
		} else {
			NO_NETWORK = false;
			return true;
		}
	}
	/**
	 * 判断当前网络是否是wifi网络
	 * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) { //判断3G网
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		if (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			return true;
		}
		return false;
	}

	/**
	 * 网络是否可用
	 * 
	 * @param
	 * @return
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static boolean noNetConnected(Context context){
		getNetWorkInfoType(context);
		return NO_NETWORK;
	}
}
