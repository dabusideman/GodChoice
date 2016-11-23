package xuezhangyouhuo.com.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import xuezhangyouhuo.com.activity.CreateErWeiMaActivity;

/**
 * @Author: zpx
 * @Date: 15/11/4下午12:04
 * @Title: ${enclosing_methed}
 * @Description:
 * @return ${return_type}
 */
public class SaveToMap {
    public static void savepic(Context context,View view) {
//        View view = findViewById(R.id.ly_saveaspic);
        // 打开图像缓存
        view.setDrawingCacheEnabled(true);

        Bitmap bp = view.getDrawingCache();// 获得可视组件的截图

        String path = null;
        File appDir = new File(Environment.getExternalStorageDirectory(), "密语");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".png";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            path=file.toString();
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CreateErWeiMaActivity.savepic=true;
        // 最后通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        ToastUtil.showToast("保存成功");

    }
}
