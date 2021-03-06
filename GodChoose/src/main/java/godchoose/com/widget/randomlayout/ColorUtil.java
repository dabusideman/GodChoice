package godchoose.com.widget.randomlayout;

import android.graphics.Color;

import java.util.Random;

public class ColorUtil {
	/**
	 * 随机生成颜色
	 * @return
	 */
	public static int randomColor(){
		Random random = new Random();
		//对3原色的值进行限制，因为太大会偏白，太小会偏暗,所以应该取中间的值
		int red = random.nextInt(150)+50;//50->199
		int green = random.nextInt(150)+50;//50->199
		int blue = random.nextInt(150)+50;//50->199
		return Color.rgb(red, green, blue);//混合出一种新的颜色
	}
}
