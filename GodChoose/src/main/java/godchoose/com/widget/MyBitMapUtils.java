package godchoose.com.widget;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import godchoose.com.globle.GodchooseApplication;
import godchoose.com.util.LogUtil;

/**
 * Created by peter on 15/10/28.
 */
public class MyBitMapUtils {
    // SD卡上图片储存地址
     private static String path = Environment.getExternalStorageDirectory().getPath() + "/maiduo";
    /**
     * 保存图片到SD卡上
     *
     *
     */
    public static File saveFile(Bitmap bm, String url) {
        try {
//            BitmapDrawable bd = (BitmapDrawable) dw;
//            Bitmap bm = bd.getBitmap();

            // 获得文件名字
            final String fileNa = url.substring(url.lastIndexOf("/") + 1,
                    url.length()).toLowerCase();

            File file = new File(path + "/image/" + fileNa);
            // 创建图片缓存文件夹
            boolean sdCardExist = Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED); // 判断sd卡是否存在

            if (sdCardExist) {
                File maiduo = new File(path);
                File ad = new File(path + "/image");
                // 如果文件夹不存在
                if (!maiduo.exists()) {
                    // 按照指定的路径创建文件夹
                    maiduo.mkdir();
                    // 如果文件夹不存在
                } else if (!ad.exists()) {
                    // 按照指定的路径创建文件夹
                    ad.mkdir();
                }
                // 检查图片是否存在
                if (!file.exists()) {
                    file.createNewFile();
                }
            }

            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return file;
        } catch (Exception e) {
        }

        return null;
    }


    /**
     * 使用SD卡上的图片
     *
     */
    public static Bitmap useTheImage(String imageUrl) {

        Bitmap bmpDefaultPic = null;

        // 获得文件路径
        String imageSDCardPath = path+ "/image/"+ imageUrl.substring(imageUrl.lastIndexOf("/") + 1,
                imageUrl.length()).toLowerCase();

        File file = new File(imageSDCardPath);
        // 检查图片是否存在
        if (!file.exists()) {
            return null;
        }

        bmpDefaultPic = BitmapFactory.decodeFile(imageSDCardPath, null);

//        if (bmpDefaultPic != null || bmpDefaultPic.toString().length() > 3) {
//            Drawable drawable = new BitmapDrawable(bmpDefaultPic);
//            return drawable;

        return bmpDefaultPic;
    }
    public static File getRealPathFromURI(Uri contentUri) {
        String res = null;

        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = GodchooseApplication.context.getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        File file = new File(res);
        return file;
    }
    public static Bitmap fitSizeImg(File file) {
//        if(path == null || path.length()<1 ) return null;
//        File file = new File(path);
//        File file = getRealPathFromURI(contentUri);
        LogUtil.e("file.length() =" + file.length());
        Bitmap resizeBmp = null;
        BitmapFactory.Options opts = new   BitmapFactory.Options();
        // 数字越大读出的图片占用的heap越小 不然总是溢出
        if (file.length() < 20480) {       // 0-20k
            opts.inSampleSize = 1;
        } else if (file.length() < 51200) { // 20-50k
            opts.inSampleSize = 1;
        } else if (file.length() < 307200) { // 50-300k
            opts.inSampleSize = 1;
        } else if (file.length() < 819200) { // 300-800k
            opts.inSampleSize = 3;
        } else if (file.length() < 1048576) { // 800-1024k
            opts.inSampleSize = 4;
        } else if (file.length() < 1048576*2){//2m
            opts.inSampleSize = 6;
        }else if (file.length() < 1048576*2*2){
            opts.inSampleSize = 7;
        }else {
            opts.inSampleSize = 10;
        }

        resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
        LogUtil.e("resizeBmp.getHeight() ="+resizeBmp.getHeight());
        LogUtil.e("resizeBmp.getWidth() ="+resizeBmp.getWidth());
        return resizeBmp;
    }




}
