package xuezhangyouhuo.com.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.bean.BaseBeans;
import xuezhangyouhuo.com.dialog.ChooseSchoolDialog;
import xuezhangyouhuo.com.fragment.FragmentCenter;
import xuezhangyouhuo.com.fragment.FragmentCounter;
import xuezhangyouhuo.com.fragment.FragmentFealMarket;
import xuezhangyouhuo.com.fragment.FragmentFind;
import xuezhangyouhuo.com.fragment.FragmentMessage;
import xuezhangyouhuo.com.service.imp.RemoteImpl;
import xuezhangyouhuo.com.util.LogUtil;
import xuezhangyouhuo.com.util.MyAsyncTask;
import xuezhangyouhuo.com.util.ToastUtil;
import xuezhangyouhuo.com.util.Util;

public class XueZhangMainActivity extends FragmentActivity implements
		OnClickListener {
	private static ClickItemListener clickItemListener;

	public static ClickItemListener getClickItemListener() {
		return clickItemListener;
	}

	public static void setClickItemListener(ClickItemListener clickItemListener) {
		XueZhangMainActivity.clickItemListener = clickItemListener;
	}
//	private DragLayout dl;
//	private ListView lv;
//	private ImageView imgHeadSmall, imgHead;
	private FrameLayout flFealMarket, flCounter, flFind, flMessage,flCenter;

	// 切换Fragment
	private FragmentManager manager;
	private FragmentTransaction transaction;

	private FragmentFealMarket fragmentFealMarket;
	private FragmentCounter fragmentCounter;
	private FragmentFind fragmentFind;
	private FragmentMessage fragmentMessage;
	private FragmentCenter fragmentCenter;

	private RelativeLayout ryPublish;


	// 是否切换时加载数据
	public static boolean isData = false;
	public static boolean isPostTopics = true;
	public static boolean isNews = true;
	public static boolean isVedio = true;

	// 底部4个按钮
	private Button[] btnArray = new Button[5];
	private int currentPageIndex = 0;

	// 发帖
	private RelativeLayout ryChooseSchool;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xuezhang_main);
		Util.initImageLoader(this);
		manager = getSupportFragmentManager();
		initDragLayout();
		initView();
		addListener();
		addFragments();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	private void addListener() {
		// TODO Auto-generated method stub
		for (int i = 0; i < btnArray.length; i++) {
			btnArray[i].setOnClickListener(this);
		}
		ryChooseSchool.setOnClickListener(this);
//		imgHead.setOnClickListener(this);
	}

	private void addFragments() {
		transaction = manager.beginTransaction();
		// News
		fragmentFealMarket = new FragmentFealMarket();
		transaction.replace(R.id.ly_feal_market, fragmentFealMarket);
		// Attentions
		fragmentCounter = new FragmentCounter();
		transaction.replace(R.id.ly_counter, fragmentCounter);
		// Messages
		fragmentFind = new FragmentFind();
		transaction.replace(R.id.ly_find, fragmentFind);
		// Games
		fragmentMessage = new FragmentMessage();
		transaction.replace(R.id.ly_message, fragmentMessage);

		fragmentCenter = new FragmentCenter();
		transaction.replace(R.id.ly_center, fragmentCenter);

		transaction.commit();
	}

	private void initDragLayout() {
//		dl = (DragLayout) findViewById(R.id.dl);
//		dl.setDragListener(new DragLayout.DragListener() {
//			@Override
//			public void onOpen() {
//				lv.smoothScrollToPosition(new Random().nextInt(30));
//			}
//
//			@Override
//			public void onClose() {
//			}
//
//			@Override
//			public void onDrag(float percent) {
//				animate(percent);
//			}
//		});

		btnArray[0] = (Button) findViewById(R.id.bt_flea_market);
		btnArray[1] = (Button) findViewById(R.id.bt_counter);
		btnArray[2] = (Button) findViewById(R.id.bt_find);
		btnArray[3] = (Button) findViewById(R.id.bt_message);
		btnArray[4] = (Button) findViewById(R.id.bt_center);

	}

//	private void animate(float percent) {
//		ViewGroup vg_left = dl.getVg_left();
//		ViewGroup vg_main = dl.getVg_main();
//
//		float f1 = 1 - percent * 0.2f;// 此处控制主界面的比例大小
//		ViewHelper.setScaleX(vg_main, f1);
//		ViewHelper.setScaleY(vg_main, f1);
//		ViewHelper.setTranslationX(vg_left, -vg_left.getWidth() / 2.2f
//				+ vg_left.getWidth() / 2.2f * percent);
//		ViewHelper.setScaleX(vg_left, 0.5f + 0.5f * percent);
//		ViewHelper.setScaleY(vg_left, 0.5f + 0.5f * percent);
//		ViewHelper.setAlpha(vg_left, percent);
//		ViewHelper.setAlpha(imgHeadSmall, 1 - percent);
//
//		int color = (Integer) Util.evaluate(percent,
//				Color.parseColor("#ff000000"), Color.parseColor("#00000000"));
////		dl.getBackground().setColorFilter(color, Mode.SRC_OVER);
//	}

	private void initView() {
		ryPublish= (RelativeLayout) findViewById(R.id.ry_publish);
		flFealMarket = (FrameLayout) findViewById(R.id.ly_feal_market);
		flCounter = (FrameLayout) findViewById(R.id.ly_counter);
		flFind = (FrameLayout) findViewById(R.id.ly_find);
		flMessage = (FrameLayout) findViewById(R.id.ly_message);
		flCenter = (FrameLayout) findViewById(R.id.ly_center);

//		imgHead = (ImageView) findViewById(R.id.img_head);

		ryChooseSchool = (RelativeLayout) findViewById(R.id.ry_choose_school);

//		lv = (ListView) findViewById(R.id.lv);
//		lv.setAdapter(new ArrayAdapter<String>(XueZhangMainActivity.this,
//				R.layout.item_left_listview, new String[] { "FIRST", "财富",
//						"市场", "好友", "反馈" }));
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int position, long arg3) {
//				switch (position) {
//				case 0:
////					dl.close();
//					break;
//				case 1:
//					Toast.makeText(XueZhangMainActivity.this, "SECOND", Toast.LENGTH_SHORT).show();
//					break;
//				case 2:
//					Toast.makeText(XueZhangMainActivity.this, "THIRD", Toast.LENGTH_SHORT).show();
//					break;
//				}
//			}
//		});

		ryPublish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent=new Intent(XueZhangMainActivity.this,PublishStuffActivity.class);
				startActivity(intent);
			}
		});

	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	/**
	 * 退出返回
	 */
	Timer timer = new Timer();
	private static Boolean isQuit = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

//		if (dl.getStatus().equals(DragLayout.Status.Open)) {// 如果侧边栏在开启状态
//			dl.close();
//			return false;
//		}

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (isQuit == false) {
				isQuit = true;
				Toast.makeText(getBaseContext(), "再按一次返回键退出程序",
						Toast.LENGTH_SHORT).show();
				TimerTask task = null;
				task = new TimerTask() {
					@Override
					public void run() {
						isQuit = false;
					}
				};
				timer.schedule(task, 2000);
			} else {
				// saveAppLog();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_flea_market:
			currentPageIndex = 0;
			flFealMarket.setVisibility(View.VISIBLE);
			flCounter.setVisibility(View.GONE);
			flFind.setVisibility(View.GONE);
			flMessage.setVisibility(View.GONE);
			flCenter.setVisibility(View.GONE);
			setButtonColor();
			break;
		case R.id.bt_counter:
//			FragmentCounter.doFirstData=true;
			currentPageIndex = 1;
			flFealMarket.setVisibility(View.GONE);
			flCounter.setVisibility(View.VISIBLE);
			flFind.setVisibility(View.GONE);
			flMessage.setVisibility(View.GONE);
			flCenter.setVisibility(View.GONE);
			setButtonColor();
			clickItemListener.doClickListener();


//			if (isPostTopics) {
//				Intent intent1 = new Intent("POSTTOPIC");
//				sendBroadcast(intent1);
//			}
			break;

		case R.id.bt_find:
			currentPageIndex = 2;
			flFealMarket.setVisibility(View.GONE);
			flCounter.setVisibility(View.GONE);
			flFind.setVisibility(View.VISIBLE);
			flMessage.setVisibility(View.GONE);
			flCenter.setVisibility(View.GONE);
			setButtonColor();
			if (isNews) {
				Intent intent3 = new Intent("NEWS");
				sendBroadcast(intent3);

			}
			break;
		case R.id.bt_message:
			currentPageIndex = 3;
			flFealMarket.setVisibility(View.GONE);
			flCounter.setVisibility(View.GONE);
			flFind.setVisibility(View.GONE);
			flMessage.setVisibility(View.VISIBLE);
			flCenter.setVisibility(View.GONE);
			setButtonColor();
			if (isVedio) {
				Intent intent4 = new Intent("VIDEO");
				sendBroadcast(intent4);
			}
			break;
			case R.id.bt_center:
				currentPageIndex = 4;
				flFealMarket.setVisibility(View.GONE);
				flCounter.setVisibility(View.GONE);
				flFind.setVisibility(View.GONE);
				flMessage.setVisibility(View.GONE);
				flCenter.setVisibility(View.VISIBLE);
				setButtonColor();
				if (isVedio) {
					Intent intent4 = new Intent("VIDEO");
					sendBroadcast(intent4);
				}
				break;

		case R.id.ry_choose_school: // 跳转
			LogUtil.i("跳转===========");
			ChooseSchoolDialog exchangeSuccesDialog=new ChooseSchoolDialog(this,"");
			exchangeSuccesDialog.showDialog();
			doTestPost();
			break;
		case R.id.img_head:
			Intent intent=new Intent(this,PublishStuffActivity.class);
			startActivity(intent);

			break;
		default:
			break;
		}

	}

	private void doTestPost() {
		ToastUtil.showToast("POST");
		new MyAsyncTask<Void,Void,BaseBeans>(this,false){

			@Override
			public BaseBeans before(Void... params) throws Exception {
				return RemoteImpl.getInstance().postTestBean("sss");
			}

			@Override
			public void after(BaseBeans baseBeans) {

			}

			@Override
			public void exception() {

			}
		}.execute();
	}

	/**
	 * 
	 * @Description:TODO 设置按钮颜色
	 * @auther:LC_22
	 * @time:2015-6-1 下午6:02:42
	 */
	private void setButtonColor() {
		for (int i = 0; i < btnArray.length; i++) {
			Button btn = btnArray[i];
			if (i == currentPageIndex) {
				btn.setTextColor(0xFFFFFFFF);

			} else {
				btn.setTextColor(0xFF000000);

			}
		}
	}

	/**
	 * 设置通知提示方式 - 基础属性
	 */
	// private void setStyleBasic(){
	// BasicPushNotificationBuilder builder = new
	// BasicPushNotificationBuilder(this);
	// builder.statusBarDrawable = R.drawable.ic_launcher;
	// builder.notificationFlags = Notification.FLAG_AUTO_CANCEL; //设置为点击后自动消失
	// builder.notificationDefaults = Notification.DEFAULT_SOUND; //设置为铃声（
	// Notification.DEFAULT_SOUND）或者震动（ Notification.DEFAULT_VIBRATE）
	// JPushInterface.setPushNotificationBuilder(1, builder);
	// //NotificationManager.notify(id, Notification); id相tong就会覆盖只显示一条
	// Toast.makeText(this, "Basic Builder - 1", Toast.LENGTH_SHORT).show();
	//
	//
	// }

	/**
	 * 设置通知栏样式 - 定义通知栏Layout
	 */
	// private void setStyleCustom(){
	// CustomPushNotificationBuilder builder = new
	// CustomPushNotificationBuilder(this,R.layout.customer_notitfication_layout,R.id.icon,
	// R.id.title, R.id.text);
	// builder.layoutIconDrawable = R.drawable.ic_launcher;
	// builder.developerArg0 = "developerArg2";
	// JPushInterface.setPushNotificationBuilder(2, builder);
	// Toast.makeText(this,"Custom Builder - 2", Toast.LENGTH_SHORT).show();
	// }
	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		// mCurrentPosition表示当前切换的fragment的数组下标
//		outState.putInt(POSITIONKEY, mCurrentPosition);
		//super.onSaveInstanceState(outState); 总是执行这句代码来调用父类去保存视图层的状态，会导致fragmen重影，不能正常恢复状态
	}

	public interface ClickItemListener {
		void doClickListener();
	}

}
