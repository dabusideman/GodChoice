package xuezhangyouhuo.com.util.bitmaputil;

import android.graphics.Bitmap;
import android.view.View;
import android.view.View.MeasureSpec;

public class ViewToBitMap {
	/**
	 * 
	 * @Description:TODO  将View转化为Bitmap
	 * @auther:Administrator  zpx
	 * @time:2014-6-5 下午3:28:54
	 */
	public static Bitmap convertViewToBitmap(View view) {
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		 view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());  
		 view.buildDrawingCache();  
		 Bitmap bitmap = view.getDrawingCache();  
		 return bitmap;  
	}
}
