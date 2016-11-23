package godchoose.com.activity.uploadpic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import godchoose.com.R;
import godchoose.com.bean.BaseVo;
import godchoose.com.util.Base64Utils;
import godchoose.com.util.HttpFileUtils;
import godchoose.com.util.HttpHelps;
import godchoose.com.util.JsonUtil;
import godchoose.com.util.LogUtil;
import godchoose.com.util.MyAsyncTask;
import godchoose.com.util.StringUtil;
import godchoose.com.util.ToastUtil;
import godchoose.com.util.bitmaputil.Bimp;
import godchoose.com.util.bitmaputil.FileUtils;


/**
 * @author zpx
 * @createdate 2014-3-7 下午2:10:55
 * @Description: 上传照片
 */
public class UpLoadPic extends Activity {

	private GridView noScrollgridview; // 图片展示
	private GridAdapter adapter; // 图片适配
//	private GradViewAdapter gvAdapter;//获取上传材料的Adatper
//	private ArrayList<ShowBigPicVoDemo> listShangchuancailiao;
	
	private ProgressDialog pd; // 上传图片进度条
	protected int upIndex=0; // 上传图片下标
	private String img = ""; // 图片的Based64码
	private PopupWindow pop;
	String model = android.os.Build.MODEL;
	private RelativeLayout lyPop;

	private GridView gvUpdatedPic;

	private Button btRight;
	
	private Button btSubmmit;
	private File fileBitMap;

	Handler mHandler=new Handler(){
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what){
				case 0:
					upLoadFile(new File(Bimp.drr.get(upIndex)));
					break;
				case 1:

					pd.dismiss();
					ToastUtil.showToast("上传完毕");
					break;
			}
			super.handleMessage(msg);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// LogUtil.i("PublishedActivity", "手机型号" + model);
		setContentView(R.layout.dataupload);
		btSubmmit= (Button) findViewById(R.id.bt_submit);
		init();
		// loadData();
		addListener();
//		getUpdatedPics();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	private void addListener() {
		// TODO Auto-generated method stub

		btSubmmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				ToastUtil.showToast("CLICK");
				upIndex = 0;
				pd.setMax(Bimp.drr.size());
				pd.show();
				pd.setProgress(0);
				pd.setCancelable(false);
				mHandler.sendEmptyMessage(0);
			}
		});
	}

	private synchronized BaseVo update(final String findId) throws Exception {
		img = uploadImgForBase64(Bimp.bmp.get(upIndex));
		// 上传头像
		BaseVo vo = null;
/*		BaseVo vo = RemoteImpl.getInstance().uploadImg(
				FlyApplication.orderCode,materialDesc, materialName,  "0", "",
				img, "114", "114", upIndex + "");
*/
		upIndex++;

		return null;
	}

	public void init() {
		noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
		btRight= (Button) findViewById(R.id.bt_submit);
		lyPop = (RelativeLayout) findViewById(R.id.ly_sexpop);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new GridAdapter(this);
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在上传图片...");
		pd.setCancelable(true);
		adapter.update();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// ToastUtil.showToast("点击了");
				if (position == Bimp.bmp.size()) {
					// 隐藏软键盘
					// InputMethodManager imm = (InputMethodManager)
					// getSystemService(Context.INPUT_METHOD_SERVICE);
					// imm.hideSoftInputFromWindow(findContentEd.getWindowToken(),
					// 0);
					try {
						new PopupWindows(UpLoadPic.this,
								noScrollgridview);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						ToastUtil.showToast("亲，抱歉，您的手机可能不支持该操作哦！");
						// finish();
					}
				} else {
					Intent intent = new Intent(UpLoadPic.this,
							PhotoActivity.class);
					intent.putExtra("ID", position);
					startActivity(intent);
				}
			}
		});
		// btShangchuan.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// Bimp.clearCahe();
		// // finish();
		// }
		// });
		btRight.setOnClickListener(new OnClickListener() {
			private ArrayList<String> list;

			public void onClick(View v) {
				if (Bimp.bmp.size() == 0) {
					ToastUtil.showToast("请至少上传一张图片");
					return;
				}
				/**
				 * 发布内容接口，此项目可以不要
				 */
				new MyAsyncTask<Void, Void, BaseVo>(UpLoadPic.this,true) {

					@Override
					public BaseVo before(Void... params) throws Exception {
						// TODO Auto-generated method stub
//						 return	RemoteImpl.getInstance().getCailiao();
						return null;
					}

					@Override
					public void after(BaseVo result) {
//						if (!"0000".equals(result.getResDesc())) {
//							ToastUtil.showToast(result.getResDesc());
//							return;
//						}
						/**
						 * 判断是否添加了图片
						 */
						if (Bimp.drr.size() > 0) {
							if (null == list) {
								list = new ArrayList<String>();
							} else {
								list.clear();
							}
							for (int i = 0; i < Bimp.drr.size(); i++) {
								String Str = Bimp.drr.get(i).substring(
										Bimp.drr.get(i).lastIndexOf("/") + 1,
										Bimp.drr.get(i).lastIndexOf("."));
								list.add(FileUtils.SDPATH + Str + ".JPEG");
							}
							upload(list, "");// 是否是上传用的id？
						} else {
							ToastUtil.showToast("发表成功");

							// finish();
						}
					}

				
					
					
					
					/**
					 * 
					 * @author zpx
					 * @createdate 2014-3-19 下午4:29:42
					 * @Description: 上传
					 * @param list
					 * 
					 */
					private void upload(List<String> list, final String findId) {
						
						upIndex = 0;
						pd.setMax(list.size());
						pd.show();
						pd.setProgress(0);
						pd.setCancelable(false);
						for (int i = 0; i < list.size(); i++) {
							new AsyncTask<Void, Void, BaseVo>() {
								@Override
								protected void onPostExecute(BaseVo result) {
									pd.setProgress(upIndex + 1);
									if (upIndex > Bimp.drr.size() - 1) {
										pd.dismiss();
										/**
										 * 图片上传完毕，，后续处理。。。 例如：可以再下面显示已经上传的图片
										 * 
										 */
										Bimp.clearCahe();
										// finish();
									}
									// upIndex++;
									super.onPostExecute(result);
								}

								@Override
								protected void onPreExecute() {
									super.onPreExecute();
								}

								@Override
								protected BaseVo doInBackground(Void... params) {

									try {
										update(findId);
										// Thread.sleep(1000);
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return null;
								}
							}.execute();
						}
					}

					@Override
					public void exception() {
						// TODO Auto-generated method stub

					}
				}.execute();

				// 高清的压缩图片全部就在 list 路径里面了
				// 高清的压缩过的 bmp 对象 都在 Bimp.bmp里面
				// 完成上传服务器后 .........

				FileUtils.deleteDir();
			}
		});
	}

	@SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 视图容器
		private int selectedPosition = -1;// 选中的位置
		private boolean shape;
		Context context;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			this.context = context;
			inflater = LayoutInflater.from(context);
		}

		public void update() {
			loading();
		}

		public int getCount() {
			if (Bimp.bmp.size() == 100) {
				return Bimp.bmp.size();
			}
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			final int coord = position;
			ViewHolder holder = null;
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida,
						parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView
						.findViewById(R.id.item_grida_image);

				// int
				// width=context.getResources().getDisplayMetrics().widthPixels-PxToDp.dip2px(context,
				// 30);
				// width=width/3-PxToDp.dip2px(context, 8);
				// holder.image.setLayoutParams(new
				// LinearLayout.LayoutParams(width,width));

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (position == Bimp.bmp.size()) {
				holder.image.setImageBitmap(BitmapFactory.decodeResource(
						getResources(),
						R.drawable.ic_launcher));
				 if (position == 3) {
				 holder.image.setVisibility(View.GONE);
				 } else {
				 holder.image.setVisibility(View.VISIBLE);
				 }
			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));


			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading() {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								fileBitMap=new File(path);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(
										path.lastIndexOf("/") + 1,
										path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (Exception e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	public String getString(String s) {
		String path = null;
		if (s == null)
			return "";
		for (int i = s.length() - 1; i > 0; i++) {
			s.charAt(i);
		}
		return path;
	}

	protected void onRestart() {
		adapter.update();
		super.onRestart();
	}

	public class PopupWindows extends PopupWindow {

		public PopupWindows(Context mContext, View parent) {

			View view = View.inflate(mContext, R.layout.photopop, null);
			// view.startAnimation(AnimationUtils.loadAnimation(mContext,
			// R.anim.fade_ins));
			// LinearLayout ll_popup = (LinearLayout)
			// view.findViewById(R.id.ll_popup);
			// ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
			// R.anim.push_bottom_in_2));

			setWidth(LayoutParams.MATCH_PARENT);
			setHeight(LayoutParams.WRAP_CONTENT);
			setBackgroundDrawable(new BitmapDrawable());
			setFocusable(true);
			setOutsideTouchable(true);
			setContentView(view);
			showAtLocation(lyPop, Gravity.CENTER, 0, 0);
			update();

			LinearLayout bt1 = (LinearLayout) view.findViewById(R.id.camera);
			LinearLayout bt2 = (LinearLayout) view.findViewById(R.id.chose);
			LinearLayout bt3 = (LinearLayout) view.findViewById(R.id.cancle);
			bt1.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// photo();
					takePhoto();
					dismiss();
				}
			});
			bt2.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(UpLoadPic.this,
							PicListActivity.class);
					startActivity(intent);
					dismiss();
				}
			});
			bt3.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					dismiss();
				}
			});

		}
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";
	private ImageView send_expression;
	private Uri photoUri;
	private ImageView tvIcon;
	private ImageView positionIcon;
	private TextView findContentNum;

	// public void photo() {
	// Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	// File file = new File(Environment.getExternalStorageDirectory() +
	// "/myimage/", String.valueOf(System.currentTimeMillis()) + ".jpg");
	// path = file.getPath();
	// Uri imageUri = Uri.fromFile(file);
	// openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
	// startActivityForResult(openCameraIntent, TAKE_PICTURE);
	// }

	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
			/***
			 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
			 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
			 */
			ContentValues values = new ContentValues();
			photoUri = this.getContentResolver().insert(
					Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
			/** ----------------- */
			startActivityForResult(intent, TAKE_PICTURE);
		} else {
			Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
		}
		// ----------------------------------------------------

		// Intent intent ;
		// {
		// /**
		// * 这一段是为了小米手机出现bug来修改的(Bug:拍照后返回的照片不是原来的图片，定制机，bull shit...)
		// */
		// intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// SDCardFileUtils.creatDir2SDCard(fileDir);
		// intent.putExtra(MediaStore.EXTRA_OUTPUT,
		// Uri.fromFile(new File(fileDir,
		// DateFormat.format("yyyy-MM-dd-hh-mm-ss", new Date()) + ".jpg")));
		// }
		// File file = new File(fileDir);
		// olderList = file.list();
		// startActivityForResult(intent, 0);
	}

	/**
	 * 获取系统默认存储真实文件路径 两个路径 一个是最后一张所拍图片路径
	 * (由于设置了存储路径所以DCIM中会存储两张一模一样的图片，所以在此获取两张图片路径以便于缩放处理后全部删除)
	 * 
	 * @return
	 */
	protected String getRealFilePath() {
		String filePath = "";
		// MediaStore.Images.Media.EXTERNAL_CONTENT_URI
		// content://media/external/images/media
		Cursor cursor = this.getContentResolver().query(
				Media.EXTERNAL_CONTENT_URI, null, null, null,
				Media.DATE_MODIFIED + " desc");
		if (cursor.moveToNext()) {
			/**
			 * _data：文件的绝对路径 Media.DATA='_data'
			 */
			filePath = cursor.getString(cursor.getColumnIndex(Media.DATA));// /storage/sdcard0/DCIM/Camera/1397790445536.jpg
		}
		return filePath;
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case TAKE_PICTURE:
			String picPath = "";
			if (Bimp.drr.size() < 3 && resultCode == -1) {
				// if(!model.equals("GT-N7100")){
				if (photoUri != null) {

					String[] pojo = { Media.DATA };
					Cursor cursor = managedQuery(photoUri, pojo, null, null,
							null);
					if (cursor != null) {
						int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
						cursor.moveToFirst();
						picPath = cursor.getString(columnIndex);
						if (VERSION.SDK_INT < 14) {
							cursor.close();
						}
					}
				} else {
					picPath = StringUtil.isNotBlank(picPath) ? picPath
							: getRealFilePath();

				}
				LogUtil.i("xj", "imagePath = " + picPath);
				/*
				 * if(picPath != null && ( picPath.endsWith(".png") ||
				 * picPath.endsWith(".PNG") ||picPath.endsWith(".jpg")
				 * ||picPath.endsWith(".JPG") ))
				 */
				if (picPath != null) { // /storage/sdcard0/DCIM/Camera/1397787931720.jpg
					Bimp.drr.add(picPath);
					fileBitMap=new File(picPath);
				} else {
					Toast.makeText(this, "选择文件不正确!", Toast.LENGTH_LONG).show();

				}
			}
			break;
		}
	}

	protected String uploadImgForBase64(Bitmap bm) {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, bao);
		byte[] ba = bao.toByteArray();
		String ba1 = Base64.encodeToString(ba, 0, ba.length, Base64.DEFAULT);

		try {
			FileOutputStream fos = new FileOutputStream(new File(
					Environment.getExternalStorageDirectory(), "bim.txt"));
			fos.write(ba1.getBytes("utf-8"));
			fos.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ba1;
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		// Bimp.clearCahe();
		FileUtils.deleteDir();
		super.onDestroy();

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();

	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (event.KEYCODE_BACK == keyCode) {
			Bimp.clearCahe();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}


	/**
	 * 上传一张图片
	 * @param file
	 */
	private void upLoadFile(File file){
		ToastUtil.showToast("CLICK");
		new HttpFileUtils(){

			@Override
			public void initData(String json,int size) {
				String result= Base64Utils.decryptBASE64(json);
				LogUtil.e(result);
				BaseVo bv= JsonUtil.parseJsonToBean(result, BaseVo.class);
				if("1".equals(bv.getResCode())){
					pd.setProgress(upIndex + 1);
					upIndex++;
					if(upIndex<Bimp.drr.size()){

						mHandler.sendEmptyMessage(0);
					}else{
						mHandler.sendEmptyMessage(1);
					}
				}
			}

			@Override
			public void ErrorData(int size) {

			}

			@Override
			public void onMyProgress(int bytesWritten, int totalSize) {

			}
		}.post(HttpHelps.baseUrl+"mod=image&func=upload", file, "",0);
	}

}
