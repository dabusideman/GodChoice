package xuezhangyouhuo.com.util;

import android.content.Context;

public class CommonUtils {
	public static int dp2px(Context context,int dpValue){
		// 获取密度比
		float density = context.getResources().getDisplayMetrics().density;
		int px = (int) (density * dpValue + 0.5f);
		return px;
	}
}
