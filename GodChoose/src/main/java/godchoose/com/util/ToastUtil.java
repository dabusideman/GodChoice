package godchoose.com.util;

import android.widget.Toast;

import godchoose.com.globle.GodchooseApplication;

/**
 * Created by peter on 15/9/28.
 */
public class ToastUtil {
    private static Toast toast;
    /**
     * 连续弹的吐司
     */
    public static void showToast(String text){
        if(toast==null){
            toast = Toast.makeText(GodchooseApplication.getContext(), text, Toast.LENGTH_SHORT);
        }
        toast.setText(text);
        toast.show();
    }
}
