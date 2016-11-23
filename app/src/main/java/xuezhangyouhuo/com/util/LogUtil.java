package xuezhangyouhuo.com.util;

import android.util.Log;

public class LogUtil {

	private static final String KEY = "--Main--";
	public static final boolean FLAG = true; // 测试环境为true,生产环境为false

	

	public static void i(String tag,Object message){
		if(FLAG){
			Log.i(tag, message.toString());
		}
	}
	public static void i(Object message) {
		if (FLAG) {
			Log.i(KEY, message.toString());
		}
	}

	
	
	public static void e(Object message) {
		if (FLAG) {
			Log.e(KEY, message.toString());
		}
	}

	public static void d(Object message) {
		if (FLAG) {
			Log.d(KEY, message.toString());
		}
	}
	public static void d(String tag,Object message){
		if(FLAG){
			Log.d(tag, message.toString());
		}
	}

	public static void w(Object message) {
		if (FLAG) {
			Log.w(KEY, message.toString());
		}
	}

	public static void w(Object message, Throwable tr) {
		if (FLAG) {
			Log.w(KEY, message.toString(), tr);
		}
	}
}
