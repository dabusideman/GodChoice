package godchoose.com.util.bitmaputil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import godchoose.com.R;
import godchoose.com.globle.GodchooseApplication;
import godchoose.com.util.CommonUtil;
import godchoose.com.util.LogUtil;
import godchoose.com.util.NetWorkUtil;
import godchoose.com.util.ToastUtil;


/**
 * @author ty
 * @createdate 2013-4-19 上午11:54:31
 * @Description: 把drowable里的图片转换成bitmap并进行缩放处理
 */
public class BitmapUtiles {
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014-1-2 下午4:41:25
	 * @Description: (用一句话描述该方法做什么) 网络可用状态下，下载图片并保存在本地文件 
	 * @param strUrl
	 * @param file 
	 * @param context
	 * @return  要下载的图片
	 *
	 */
	public static Bitmap getNetBitmap(String strUrl,File file,Context context) {
		//		LogUtil.e(TAG, "getBitmap from net");
		Bitmap bitmap = null;
		NetWorkUtil.getNetWorkInfoType(context);
		if(!NetWorkUtil.NO_NETWORK){//有网络
			try {
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.connect();
				InputStream in = con.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
				FileOutputStream out = new FileOutputStream(file.getPath());
				bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
				out.flush();
				out.close();
				in.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally{

			}          
		}
		return bitmap;
	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2013-12-12 下午2:01:01
	 * @Description: (用一句话描述该方法做什么) 没有缓存的下载图片
	 * @param strUrl 
	 * @param context
	 * @return
	 *
	 */
	public static Bitmap getNetBitmap(String strUrl,Context context) {
		//		LogUtil.e(TAG, "getBitmap from net");
		Bitmap bitmap = null;
		NetWorkUtil.getNetWorkInfoType(context);
		if(!NetWorkUtil.NO_NETWORK){//有网络
			try {
				URL url = new URL(strUrl);
				HttpURLConnection con = (HttpURLConnection) url.openConnection();
				con.setDoInput(true);
				con.connect();
				InputStream in = con.getInputStream();
				bitmap = BitmapFactory.decodeStream(in);
				//				FileOutputStream out = new FileOutputStream(file.getPath());
				//				bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
				//				out.flush();
				//				out.close();
				in.close();
			} catch (MalformedURLException e) {
				//				bitmap=null;
				e.printStackTrace();
			} catch (IOException e) {
				//				bitmap=null;
				e.printStackTrace();
			} finally{

			}          
		}
		return bitmap;
	}

	public static Bitmap scaleBitmap(Bitmap bitmapOrg, int setwh, int setht) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);

		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		// 定义预转换成的图片的宽度和高度
		int newWidth = setwh;
		int newHeight = setht;

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		bitmapOrg = null;
		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

		return resizedBitmap;
	}

	private static int getPowerOfTwoForSampleRatio(double ratio){
		int k = Integer.highestOneBit((int)Math.floor(ratio));
		if(k==0) return 1;
		else return k;
	}

	public static Bitmap getThumbnail(Uri uri,int size) throws FileNotFoundException, IOException{
		InputStream input = GodchooseApplication.context.getContentResolver().openInputStream(uri);
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither=true;//optional
		onlyBoundsOptions.inPreferredConfig= Config.ARGB_8888;//optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		if ((onlyBoundsOptions.outWidth == -1) || (onlyBoundsOptions.outHeight == -1))
			return null;
		int originalSize = (onlyBoundsOptions.outHeight > onlyBoundsOptions.outWidth) ? onlyBoundsOptions.outHeight : onlyBoundsOptions.outWidth;
		LogUtil.e("originalSize =" + originalSize);
		LogUtil.e("outHeight ="+onlyBoundsOptions.outHeight);
		LogUtil.e("outWidth ="+onlyBoundsOptions.outWidth);
		LogUtil.e("size ="+size);

		double ratio = (originalSize > size) ? (originalSize / size) : 1.0;
		LogUtil.e("ratio ="+ratio);
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = getPowerOfTwoForSampleRatio(ratio);
		bitmapOptions.inDither=true;//optional
		bitmapOptions.inPreferredConfig= Config.ARGB_8888;//optional
		input = GodchooseApplication.context.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		bitmapOptions = null;
		LogUtil.e("getWidth ="+bitmap.getWidth());
		LogUtil.e("getHeight ="+bitmap.getHeight());
		input.close();
		return bitmap;
	}

	public static Bitmap maxBitmap2000(Bitmap bitmapOrg) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);

		// 获取这个图片的宽和高
		int width = 0;
		int height = 0;
		try {
			width = bitmapOrg.getWidth();
			height = bitmapOrg.getHeight();
		} catch (Exception e) {
			bitmapOrg=BitmapFactory.decodeResource(GodchooseApplication.context.getResources(), R.drawable.default_image );
		}
		LogUtil.e("width ="+width);
		LogUtil.e("height =" + height);
		if (width <800 &&height<800){
			return bitmapOrg;
		}
		float i = (float)width / (float)height;
		int newWidth = 0;
		int newHeight = 0;

		if (i > 1.0){
			//说明是宽图
			float i1 = (float)width /(float)800.0;
			newHeight = (int) ((float)height / i1);
			newWidth = 800;
		}else {
			//是长图
			float i2 = (float)height /(float)800.0;
			newHeight = 800;
			newWidth = (int) ((float)width / i2);
		}

//		// 定义预转换成的图片的宽度和高度
//		int newWidth = setwh;
//		int newHeight = setht;

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		bitmapOrg = null;
		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

		return resizedBitmap;
	}
	public static Bitmap maxBitmap2000px(Bitmap bitmapOrg) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);

		// 获取这个图片的宽和高
		int width = 0;
		int height = 0;
		try {
			width = bitmapOrg.getWidth();
			height = bitmapOrg.getHeight();
		} catch (Exception e) {
			bitmapOrg=BitmapFactory.decodeResource(GodchooseApplication.context.getResources(),R.drawable.default_image );
		}
		LogUtil.e("width ="+width);
		LogUtil.e("height =" + height);
		if (width <2000 &&height<2000){
			return bitmapOrg;
		}
		float i = (float)width / (float)height;
		int newWidth = 0;
		int newHeight = 0;

		if (i > 1.0){
			//说明是宽图
			float i1 = (float)width /(float)2000.0;
			newHeight = (int) ((float)height / i1);
			newWidth = 1999;
		}else {
			//是长图
			float i2 = (float)height /(float)2000.0;
			newHeight = 1999;
			newWidth = (int) ((float)width / i2);
		}

//		// 定义预转换成的图片的宽度和高度
//		int newWidth = setwh;
//		int newHeight = setht;

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		bitmapOrg = null;
		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

		return resizedBitmap;
	}
	public static Bitmap maxBitmap500(Bitmap bitmapOrg) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);

		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		if (width <300 &&height<300){
			return bitmapOrg;
		}
		float i = (float)width / (float)height;
		int newWidth = 0;
		int newHeight = 0;

		if (i > 1.0){
			//说明是宽图
			float i1 = (float)width /(float)300;
			newHeight = (int) ((float)height / i1);
			newWidth = 300;
		}else {
			//是长图
			float i2 = (float)height /(float)300;
			newHeight = 300;
			newWidth = (int) ((float)width / i2);
		}

//		// 定义预转换成的图片的宽度和高度
//		int newWidth = setwh;
//		int newHeight = setht;

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);
		bitmapOrg = null;
		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

		return resizedBitmap;
	}

	public static Bitmap maxBitmap2000Rotate(Bitmap bitmapOrg) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);

		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();

		if (width <2000 &&height<2000){
			return bitmapOrg;
		}
		float i = (float)width / (float)height;
		int newWidth = 0;
		int newHeight = 0;

		if (i > 1.0){
			//说明是宽图
			float i1 = (float)width /(float)1999.0;
			newHeight = (int) ((float)height / i1);
			newWidth = 1999;
		}else {
			//是长图
			float i2 = (float)height /(float)1999.0;
			newHeight = 1999;
			newWidth = (int) ((float)width / i2);
		}

//		// 定义预转换成的图片的宽度和高度
//		int newWidth = setwh;
//		int newHeight = setht;

		// 计算缩放率，新尺寸除原始尺寸
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 旋转图片 动作
		 matrix.postRotate(90);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);

		// 将上面创建的Bitmap转换成Drawable对象，使得其可以使用在ImageView, ImageButton中
//		BitmapDrawable bmd = new BitmapDrawable(resizedBitmap);

		return resizedBitmap;
	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014-3-28 下午1:16:48
	 * @Description: (用一句话描述该方法做什么)根据屏幕宽度缩放图片
	 * @param bitmapOrg
	 * @param context
	 * @return
	 *
	 */
	public static Bitmap scaleBitmapByScreenWidth(Bitmap bitmapOrg,Context context) {

		// / 加载需要操作的图片，这里是eoeAndroid的logo图片
		// Bitmap bitmapOrg = BitmapFactory.decodeResource(getResources(),
		// R.drawable.eoe_android);
        if(bitmapOrg==null){
        	return bitmapOrg;
        }
		// 获取这个图片的宽和高
		int width = bitmapOrg.getWidth();
		int height = bitmapOrg.getHeight();
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int screenWidth = displayMetrics.widthPixels;
		// 计算缩放率，新尺寸除原始尺寸
		float scale = screenWidth*1.0f/width;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();

		// 缩放图片动作
		matrix.postScale(scale, scale);

		// 旋转图片 动作
		// matrix.postRotate(45);

		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0, width, height, matrix, true);

		return resizedBitmap;
	}

	/********************* Drawable转Bitmap ************************/
	public static Bitmap drawabletoBitmap(Drawable drawable) {
		int width = drawable.getIntrinsicWidth();
		int height = drawable.getIntrinsicWidth();

		Bitmap bitmap = Bitmap.createBitmap(width, height, drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
				: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, width, height);

		drawable.draw(canvas);

		return bitmap;
	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2013-12-12 下午1:14:18
	 * @Description: (用一句话描述该方法做什么) 裁剪bitmap 为圆形图
	 * @param bitmap
	 * @return
	 *
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if(bitmap==null){
			return bitmap;
		}
//		LogUtil.i("处理前的原bitmap的大小=="+bitmap.getByteCount());
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		float roundPx;
		float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
		if (width <= height) {
			roundPx = width / 2;
			top = 0;
			bottom = width;
			left = 0;
			right = width;
			height = width;
			dst_left = 0;
			dst_top = 0;
			dst_right = width;
			dst_bottom = width;
		} else {
			roundPx = height / 2;
			float clip = (width - height) / 2;
			left = clip;
			right = width - clip;
			top = 0;
			bottom = height;
			width = height;
			dst_left = 0;
			dst_top = 0;
			dst_right = height;
			dst_bottom = height;
		}
//		Bitmap output = Bitmap.createBitmap(width,
//				height, Config.ARGB_8888);
//		Canvas canvas = new Canvas(output);
		//采用弱引用防止oom 即使这样还是不好使，还会内存溢出。
		WeakReference<Bitmap> weakReference;
		try {
			weakReference = new WeakReference<Bitmap>(Bitmap.createBitmap(width,height, Config.ARGB_8888));
		} catch (OutOfMemoryError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.gc();
			return null;
		}
		Canvas canvas = new Canvas(weakReference.get());
		

		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
		final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
		final RectF rectF = new RectF(dst);

		paint.setAntiAlias(true);

		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, src, dst, paint);
        return weakReference.get();
//		return output;
	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2013-12-23 上午11:30:11
	 * @Description: (用一句话描述该方法做什么) 通过反射给imgView 加载知名图片,异常时加载本地的一张图片
	 * @param iv 要显示的控件
	 * @param picName 加载的图片名
	 * @param drawableRes 本地drawable图片资源
	 *
	 */
	public static void setDrwableByName(ImageView iv,String picName,int drawableRes) {
		try {
			if(picName.contains(".png")){
				picName=picName.substring(0,picName.lastIndexOf(".png"));
			}
			//			LogUtil.i("处理后背景图 backGround=="+picName);
			Class<R.drawable> drawable = R.drawable.class;
			Field field = null;
			int res_id = -1;

			field = drawable.getField(picName);
			res_id = field.getInt(field.getName());
			iv.setImageResource(res_id);
		} catch (Exception e) {
			e.printStackTrace();
			iv.setImageResource(drawableRes);
		}

	}
	/**
	 * 
	 * @author zhourihu
	 * @createdate 2014-1-2 下午4:37:36
	 * @Description: (用一句话描述该方法做什么) 通过反射给imgeView 设置 src 属性
	 * @param iv 要显示的控件imageView
	 * @param picName 加载的图片名
	 * @throws Exception 可能找不到该图片会抛异常，交给调用者处理异常
	 *
	 */
	public static void setDrwableByName(ImageView iv,String picName) throws Exception {
		if(picName.contains(".png")){
			picName=picName.substring(0,picName.lastIndexOf(".png"));
		}
//		LogUtil.i("处理后背景图 backGround=="+picName);
		Class<R.drawable> drawable = R.drawable.class;
		Field field = null;
		int res_id = -1;
		field = drawable.getField(picName);
		res_id = field.getInt(field.getName());
		iv.setImageResource(res_id);
	}

	/**保存图片到手机
	 *
	 * @param context
	 */
	public static void saveImageToGallery(Context context, Bitmap bmp) {

		// 首先保存图片
		String path = null;
		File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		path=file.toString();
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
		CommonUtil.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				ToastUtil.showToast("保存成功");
			}
		});

	}
	// 获取指定路径的图片
	public static Bitmap getImage(String urlpath)
			throws Exception {
		URL url = new URL(urlpath);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		Bitmap bitmap = null;
		if (conn.getResponseCode() == 200) {
			InputStream inputStream = conn.getInputStream();
			bitmap = BitmapFactory.decodeStream(inputStream);
		}
		return bitmap;
	}
	public static Bitmap getBitmapFromURL(String src) {

		try {
			Log.e("src", src);
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			Log.e("Bitmap","returned");
			return myBitmap;
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("Exception", e.getMessage());
			return null;
		}
	}


	public static Bitmap fitSizeImg(String path) {
		LogUtil.e("file.length()before==");
		if(path == null || path.length()<1 ) return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new   BitmapFactory.Options();
		LogUtil.e("file.length()before=="+file.length());
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) {       // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 1;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize =1;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 2;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 3;
		} else if(file.length() < 1048576*2){
			opts.inSampleSize = 6;
		}	else if(file.length() < 1048576*3){
			opts.inSampleSize = 10;
		}else if(file.length() < 1048576*4){
			opts.inSampleSize = 11;
		}else if(file.length() < 1048576*5){
			opts.inSampleSize = 14;
		}
		else {
			opts.inSampleSize = 15;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
//		LogUtil.e("file.length()after=="+file.length());
//		File resizeBmp1 = MyBitMapUtils.saveFile(resizeBmp, "resizeBmp");
//		LogUtil.e("resizeBmp1 ="+resizeBmp1.length());
		return resizeBmp;
	}

	public static Bitmap fitSizeImgPop(String path) {
		LogUtil.e("file.length()beforePop==");
		if(path == null || path.length()<1 ) return null;
		File file = new File(path);
		Bitmap resizeBmp = null;
		BitmapFactory.Options opts = new   BitmapFactory.Options();
		LogUtil.e("file.length()beforePop=="+file.length());
		// 数字越大读出的图片占用的heap越小 不然总是溢出
		if (file.length() < 20480) {       // 0-20k
			opts.inSampleSize = 1;
		} else if (file.length() < 51200) { // 20-50k
			opts.inSampleSize = 2;
		} else if (file.length() < 307200) { // 50-300k
			opts.inSampleSize =4;
		} else if (file.length() < 819200) { // 300-800k
			opts.inSampleSize = 6;
		} else if (file.length() < 1048576) { // 800-1024k
			opts.inSampleSize = 8;
		} else {
			opts.inSampleSize = 10;
		}
		resizeBmp = BitmapFactory.decodeFile(file.getPath(), opts);
		return resizeBmp;
	}


	public static Bitmap compressImage(String path) {
		File file = new File(path);
		LogUtil.e("FILE="+file.length());
		Bitmap image = null;
//		image = BitmapFactory.decodeFile(file.getPath());
		image=BitmapUtiles.maxBitmap2000px(BitmapFactory.decodeFile(file.getPath()));
		LogUtil.e("widthbefore="+image.getWidth()+"height="+image.getHeight());
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while ( baos.toByteArray().length / 1024>500) {    //循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();//重置baos即清空baos
			options -= 10;//每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

		LogUtil.e("widthafter="+bitmap.getWidth()+"height="+bitmap.getHeight());
		return bitmap;
	}
}