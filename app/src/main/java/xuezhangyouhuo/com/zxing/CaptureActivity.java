package xuezhangyouhuo.com.zxing;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.activity.ShowErWeiMaActivity;
import xuezhangyouhuo.com.bean.JsonToVo;
import xuezhangyouhuo.com.util.JsonUtil;
import xuezhangyouhuo.com.util.LogUtil;
import xuezhangyouhuo.com.util.StringUtil;
import xuezhangyouhuo.com.util.ToastUtil;
import xuezhangyouhuo.com.zxing.camera.CameraManager;
import xuezhangyouhuo.com.zxing.decoding.CaptureActivityHandler;
import xuezhangyouhuo.com.zxing.decoding.InactivityTimer;
import xuezhangyouhuo.com.zxing.decoding.RGBLuminanceSource;
import xuezhangyouhuo.com.zxing.view.ViewfinderView;

/**
 * 拍照的Activity
 *
 * @author Baozi
 *
 */
public class CaptureActivity extends Activity implements Callback {
	private boolean photo = true;
	private static final int PARSE_BARCODE_SUC = 300;
	private static final int PARSE_BARCODE_FAIL = 303;
	private static final int REQUEST_CODE = 234;
	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private TextView tvScarContent;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
	private String photo_path;
	private Bitmap scanBitmap;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_qr_scan);
		// 初始化 CameraManager
		CameraManager.init(getApplication());
//		CameraManager.get().closeDriver();//结束摄像机
		viewfinderView = (ViewfinderView) findViewById(R.id.mo_scanner_viewfinder_view);

		initView();

		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);

	}

	private void initView() {
		findViewById(R.id.mo_scanner_back).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
		findViewById(R.id.mo_scanner_photo).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {

						if (photo) {
							photo=false;
							photo();
						}

						//避免多次点击出现重复弹框
						Timer timer = new Timer();

						timer.schedule(new TimerTask() {
										   public void run() {
											   photo = true;
										   }

									   },

								500);

					}
				});

		findViewById(R.id.mo_scanner_light).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 闪光灯
						light();
					}
				});
		findViewById(R.id.mo_scanner_close).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// 关闭摄像机和灯
						CameraManager.get().closeDriver();
					}
				});
		ImageView mQrLineView = (ImageView) findViewById(R.id.capture_scan_line);
		ScaleAnimation animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
		animation.setRepeatCount(-1);
		animation.setRepeatMode(Animation.RESTART);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(1200);
		mQrLineView.startAnimation(animation);
	}

	boolean flag = true;

	protected void light() {
		if (flag == true) {
			flag = false;
			// 开闪光灯
			CameraManager.get().openLight();

		} else {
			flag = true;
			// 关闪光灯
			CameraManager.get().offLight();
		}

	}

	private void photo() {

//		Intent innerIntent = new Intent(); // "android.intent.action.GET_CONTENT"
//		if (Build.VERSION.SDK_INT < 19) {
//			innerIntent.setAction(Intent.ACTION_GET_CONTENT);
//		} else {
//			innerIntent.setAction(Intent.ACTION_OPEN_DOCUMENT);
//		}
//		// innerIntent.setAction(Intent.ACTION_GET_CONTENT);
//
//		innerIntent.setType("image/*");
//
//		Intent wrapperIntent = Intent.createChooser(innerIntent, "选择二维码图片");
//
//		CaptureActivity.this
//				.startActivityForResult(wrapperIntent, REQUEST_CODE);

		// 调用系统的相册
		Intent tIntent = new Intent(Intent.ACTION_PICK, null);
		tIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		// 调用剪切功能
		CaptureActivity.this.startActivityForResult(tIntent, REQUEST_CODE);
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);

			switch (msg.what) {
				case PARSE_BARCODE_SUC:
					onResultHandler((String)msg.obj, scanBitmap);
					break;
				case PARSE_BARCODE_FAIL:
					Toast.makeText(CaptureActivity.this, (String)msg.obj, Toast.LENGTH_LONG).show();
					break;

			}
		}

	};
	private void onResultHandler(String resultString, Bitmap bitmap){
		if(TextUtils.isEmpty(resultString)){
			Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent resultIntent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("result", resultString);
		bundle.putParcelable("bitmap", bitmap);
		resultIntent.putExtras(bundle);
		this.setResult(RESULT_OK, resultIntent);
		LogUtil.e("二维码==" + resultString);
		doJoin(resultString);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_OK) {

			switch (requestCode) {

				case REQUEST_CODE:

					try {
						Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
						if (cursor.moveToFirst()) {
                            photo_path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        }
						cursor.close();

						LogUtil.e("photo_path="+photo_path);
						new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Result result = scanningImage2(photo_path);
                                LogUtil.e("photo_path="+photo_path);
                                if (result != null) {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_SUC;
                                    m.obj = result.getText();
                                    mHandler.sendMessage(m);
                                } else {
                                    Message m = mHandler.obtainMessage();
                                    m.what = PARSE_BARCODE_FAIL;
                                    m.obj = "识别失败!";
                                    mHandler.sendMessage(m);
                                }
                            }
                        }).start();
					} catch (Exception e) {
						e.printStackTrace();
						Message m = mHandler.obtainMessage();
						m.what = PARSE_BARCODE_FAIL;
						m.obj = "识别失败!";
						mHandler.sendMessage(m);

					}

					break;

			}

		}

	}
	public Result scanningImage2(String path) {
		if(TextUtils.isEmpty(path)){
			return null;
		}
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF8"); //���ö�ά�����ݵı���

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // �Ȼ�ȡԭ��С
		scanBitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false; // ��ȡ�µĴ�С
		int sampleSize = (int) (options.outHeight / (float) 200);
		if (sampleSize <= 0)
			sampleSize = 1;
		options.inSampleSize = sampleSize;
		scanBitmap = BitmapFactory.decodeFile(path, options);
		RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {
			return reader.decode(bitmap1, hints);

		} catch (NotFoundException e) {
			e.printStackTrace();
		} catch (ChecksumException e) {
			e.printStackTrace();
		} catch (FormatException e) {
			e.printStackTrace();
		}
		return null;
	}


	// TODO: 解析部分图片
	protected Result scanningImage(String path) {
		if (TextUtils.isEmpty(path)) {

			return null;

		}
		// DecodeHintType 和EncodeHintType
		Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
		hints.put(DecodeHintType.CHARACTER_SET, "utf-8"); // 设置二维码内容的编码
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true; // 先获取原大小
		scanBitmap = BitmapFactory.decodeFile(path, options);
		options.inJustDecodeBounds = false; // 获取新的大小

		int sampleSize = (int) (options.outHeight / (float) 200);

		if (sampleSize <= 0)
			sampleSize = 1;
		options.inSampleSize = sampleSize;
		scanBitmap = BitmapFactory.decodeFile(path, options);

		// --------------测试的解析方法---PlanarYUVLuminanceSource-这几行代码对project没作功----------

		LuminanceSource source1 = new PlanarYUVLuminanceSource(
				rgb2YUV(scanBitmap), scanBitmap.getWidth(),
				scanBitmap.getHeight(), 0, 0, scanBitmap.getWidth(),
				scanBitmap.getHeight(), false);
		BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
				source1));
		MultiFormatReader reader1 = new MultiFormatReader();
		Result result1;
		try {
			result1 = reader1.decode(binaryBitmap);
			String content = result1.getText();
			LogUtil.e("123content="+ content);
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// ----------------------------

		RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
		BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
		QRCodeReader reader = new QRCodeReader();
		try {

			return reader.decode(bitmap1, hints);

		} catch (NotFoundException e) {

			e.printStackTrace();

		} catch (ChecksumException e) {

			e.printStackTrace();

		} catch (FormatException e) {

			e.printStackTrace();

		}

		return null;

	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.mo_scanner_preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats,
					characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		// viewfinderView.drawViewfinder();

	}

	public void handleDecode(final Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String recode = recode(result.toString());
		LogUtil.e("二维码=="+recode);
//		ToastUtil.showToast(recode);
		// 数据返回
		Intent data = new Intent();
		data.putExtra("result", recode);
		setResult(300, data);
//		finish();//检测URL
		doJoin(recode);

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(
					R.raw.mo_scanner_beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(),
						file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	/**
	 * 中文乱码
	 *
	 * 暂时解决大部分的中文乱码 但是还有部分的乱码无法解决 .
	 *
	 * 如果您有好的解决方式 请联系 2221673069@qq.com
	 *
	 * 我会很乐意向您请教 谢谢您
	 *
	 * @return
	 */
	private String recode(String str) {
		String formart = "";

		try {
			boolean ISO = Charset.forName("ISO-8859-1").newEncoder()
					.canEncode(str);
			if (ISO) {
				formart = new String(str.getBytes("ISO-8859-1"), "GB2312");
				Log.i("1234      ISO8859-1", formart);
			} else {
				formart = str;
				Log.i("1234      stringExtra", str);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formart;
	}

	/**
	 * //TODO: TAOTAO 将bitmap由RGB转换为YUV //TOOD: 研究中
	 *
	 * @param bitmap
	 *            转换的图形
	 * @return YUV数据
	 */
	public byte[] rgb2YUV(Bitmap bitmap) {
		// 该方法来自QQ空间
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		int[] pixels = new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		int len = width * height;
		byte[] yuv = new byte[len * 3 / 2];
		int y, u, v;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int rgb = pixels[i * width + j] & 0x00FFFFFF;

				int r = rgb & 0xFF;
				int g = (rgb >> 8) & 0xFF;
				int b = (rgb >> 16) & 0xFF;

				y = ((66 * r + 129 * g + 25 * b + 128) >> 8) + 16;
				u = ((-38 * r - 74 * g + 112 * b + 128) >> 8) + 128;
				v = ((112 * r - 94 * g - 18 * b + 128) >> 8) + 128;

				y = y < 16 ? 16 : (y > 255 ? 255 : y);
				u = u < 0 ? 0 : (u > 255 ? 255 : u);
				v = v < 0 ? 0 : (v > 255 ? 255 : v);

				yuv[i * width + j] = (byte) y;
				// yuv[len + (i >> 1) * width + (j & ~1) + 0] = (byte) u;
				// yuv[len + (i >> 1) * width + (j & ~1) + 1] = (byte) v;
			}
		}
		return yuv;
	}




	private void getDialog(){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setIcon(R.drawable.logo);
		builder.setMessage("此二维码不是UU有效二维码");
		builder.setCancelable(false);

		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				Intent intent = new Intent(CaptureActivity.this,
						CaptureActivity.class);
				startActivity(intent);
				dialog.dismiss();

			}
		});
		builder.show();

	}


	/**
	 * 如果不是正确的格式显示dialog
	 * 如果是正确的格式：1，有口令：显示password的dialog，输入后跳转显示message，2，无口令：直接跳转显示message
	 * @param str
	 */
	private void doJoin(String str){
		if (isJson(str)){//正确的json
			//执行解析json为Bean
			JsonToVo jsonToVo= null;
			LogUtil.e("json="+str);
			try {
				jsonToVo = JsonUtil.parseJsonToBean(str, JsonToVo.class);
				LogUtil.e("jsonToVo.password="+jsonToVo.password);
				if(StringUtil.isNullOrEmpty(jsonToVo.password)){
					//没这个字段，不是合法的json
					isNotJsonDialog(str);
					return;
				}
				if("-1".equals(jsonToVo.password)){//-1:有password这个字段且没有设置密令
					LogUtil.e("不需要口令就可以进入");
					//直接跳转，不需要password
					Intent intent = new Intent(CaptureActivity.this,
							ShowErWeiMaActivity.class);
					intent.putExtra("MESSAGE",jsonToVo.message);
					startActivity(intent);
					finish();

				}else {//设置了密令，需输入密令
					passwordDialog(jsonToVo.password,jsonToVo.message);
				}

			} catch (Exception e) {
				e.printStackTrace();
				//不是需要的json格式，所以弹出dialog
				LogUtil.e("解析失败");
				isNotJsonDialog(str);
			}

		}else {//不正确的json格式，显示isNotJsonDialog
			isNotJsonDialog(str);
		}

	}


	/**
	 *  是否是json格式
	 * @param str
	 * @return
	 */
	private boolean isJson(String str){
		try {
			new JsonParser().parse(str);
			return true;
		} catch (JsonParseException e) {
//			System.out.println("bad json: " + str);
			LogUtil.e("JSON 格式不正确");
			return false;
		}
	}
	/**
	 * 二维码不对
	 */
	private void isNotJsonDialog(String str) {
		// 布局文件转换为view对象
		LayoutInflater inflater = LayoutInflater.from(this);
		final Dialog dialog = new AlertDialog.Builder(this).create();
		dialog.setCancelable(false);
		dialog.getWindow().setGravity(Gravity.CENTER);
//		dialog.getWindow().setWindowAnimations(R.style.animationDropdown);
		View photoView = LayoutInflater.from(this).inflate(
				R.layout.pop_erro_erweima, null);
		LinearLayout ly_click = (LinearLayout) photoView.findViewById(R.id.ly_click);
		tvScarContent= (TextView) photoView.findViewById(R.id.tv_scr_content);
		tvScarContent.setText(str);
		dialog.show();

		ly_click.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				Intent intent = new Intent(CaptureActivity.this,
						CaptureActivity.class);
				startActivity(intent);
				dialog.dismiss();
			}
		});

		dialog.getWindow().setContentView(photoView);

	}

	/**
	 * 不是有效的json格式
	 */
	private void isNotRightDialog(){

	}

	/**
	 * 输入passwordDialog
	 */
	private void passwordDialog(final String password, final String message){
		LayoutInflater inflater = LayoutInflater.from(this);
		final Dialog dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.getWindow().setGravity(Gravity.CENTER);
//		dialog.getWindow().setWindowAnimations(R.style.animationDropdown);
		View photoView = LayoutInflater.from(this).inflate(
				R.layout.pop_password, null);
		final EditText etPassWord= (EditText) photoView.findViewById(R.id.et_password);
		etPassWord.setFocusable(true);
		TextView tvDiss= (TextView) photoView.findViewById(R.id.tv_dissmiss);
		TextView tvsSure= (TextView) photoView.findViewById(R.id.tv_sure);

		dialog.show();

		tvsSure.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//如果输入的password正确则跳转查看内容，不正确则消失
					if (password.equals(etPassWord.getText().toString())) {//正确，跳转
						dialog.dismiss();
						ToastUtil.showToast("正确了");
						Intent intent = new Intent(CaptureActivity.this,
								ShowErWeiMaActivity.class);
						intent.putExtra("MESSAGE",message);
						CaptureActivity.this.startActivity(intent);
//
						finish();
					}else {//不正确，开启重新扫描
						ToastUtil.showToast("您的密语口令不正确");
					}
				}


		});

		tvDiss.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(CaptureActivity.this,
						CaptureActivity.class);
				startActivity(intent);
				dialog.dismiss();
				finish();
			}
		});
		dialog.getWindow().setContentView(photoView);

	}
}