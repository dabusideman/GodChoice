package godchoose.com.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.conn.ConnectTimeoutException;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import godchoose.com.R;
import godchoose.com.exception.HttpTransportSENoConnectionException;
import godchoose.com.exception.NoDataException;


public abstract class MyAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> implements
		ITask<Params, Progress, Result> {
	private String message = "正在加载...";
	private boolean cancelable = true; // isLoadingPrompt
	private boolean isShowProgressDialog = true;
	private Context context;
	private Dialog dialog;
	private View waitingView;
	private boolean isLoadingPrompt = true;
	private AnimationDrawable logoAnimation;
	private Handler loadingHandler;
	public static int RESULT_DATA = 1;
	private final int ERROR = 4;
	private final int NODATA = 5; // 没有数据
	private final int TIME_OUT = 6; // 超时

	public MyAsyncTask(Context context) {
		this.context = context;
	}

	/**
	 * @param message
	 *            提示信息
	 * @param
	 *
	 * @param isShowProgressDialog
	 *            是否显示加载条
	 */
	public MyAsyncTask(Context context, String message, boolean cancelable, boolean isShowProgressDialog) {
		this.message = message;
		this.context = context;
		isLoadingPrompt = false;
		this.isShowProgressDialog = isShowProgressDialog;
		this.cancelable = cancelable;
	}

	/**
	 * @param isShowProgressDialog
	 *            是否显示加载条
	 */
	public MyAsyncTask(Context context, boolean isShowProgressDialog) {



		this.context = context;
		this.isShowProgressDialog = isShowProgressDialog;
	}

	@Override
	protected Result doInBackground(Params... params) {
		try {
			return before(params);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			showUiThreadInfo(TIME_OUT, this);
			dissmissWaitingDialog();
			cancel(false);
		} catch (NoDataException e) {
			e.printStackTrace();
			showUiThreadInfo(NODATA, this);
			dissmissWaitingDialog();
			cancel(false);
		} catch (SocketException e) {
			e.printStackTrace();
			showUiThreadInfo(TIME_OUT, this);
			dissmissWaitingDialog();
			cancel(false);
		} catch (SocketTimeoutException e) {
			e.printStackTrace(); 
			showUiThreadInfo(TIME_OUT, this);
			dissmissWaitingDialog();
			cancel(false);
		} catch (HttpTransportSENoConnectionException e) {
			e.printStackTrace();
			showUiThreadInfo(TIME_OUT, this);
			dissmissWaitingDialog();
			cancel(false);
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			showUiThreadInfo(TIME_OUT, this);
			dissmissWaitingDialog();
			cancel(false);
		} catch (Exception e) {
			e.printStackTrace();
			showUiThreadInfo(ERROR, this);
			dissmissWaitingDialog();
			cancel(false);
		}
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if (isShowProgressDialog) {
//			if (logoAnimation.isRunning()) { // 如果正在运行,就停止
//				logoAnimation.stop();
//			}

			dialog.dismiss();
		}
		after(result);
	}

	private void showUiThreadInfo(final int index, final ITask task) {
//		((Activity) context).runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//
//			}
//		});

		CommonUtil.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				switch (index) {
					case ERROR:
						showShortToastMessage(context.getString(R.string.error));
						dissmissWaitingDialog();
						break;
					case NODATA:
						showShortToastMessage(context.getString(R.string.no_data));
						dissmissWaitingDialog();
						break;
					// case TIME_OUT: // 针对不同的业务显示不同的提示语
					// switch (ACTIVITY_TYPE) {
					// case ACTIVITY_TYPE_FOR_FLIGHT:
					// showShortToastMessage(getString(R.string.no_connections_for_flight));
					// break;
					// case ACTIVITY_TYPE_FOR_HOTEL:
					// showShortToastMessage(getString(R.string.no_connections_for_hotel));
					// break;
					// case ACTIVITY_TYPE_FOR_CAR:
					// showShortToastMessage(getString(R.string.no_connections_for_car));
					// break;
					// default:
					// showShortToastMessage(getString(R.string.time_out));
					// break;
					// }
					// ACTIVITY_TYPE=ACTIVITY_TYPE_FOR_OTHER;
					// break;
					default:
						break;
				}

				task.exception();
			}
		});
	}

	public void showShortToastMessage(String message) {
		Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPreExecute() {
		if(!NetWorkUtil.isNetWork(context)){
			ToastUtil.showToast("抱歉，网络异常...");
			return;
		}
		if (isShowProgressDialog) {
			showWaitingDialog(message, cancelable, false, isLoadingPrompt);
		}
		super.onPreExecute();
	}

	public void showWaitingDialog(String message, boolean cancelable, boolean flag, boolean isLoadingPrompt) {
		if (null == dialog) {
			dialog = new Dialog(context, R.style.dialog_windowFullscreen);
			waitingView = ((Activity) context).getLayoutInflater().inflate(R.layout.view_loading, null);
			ImageView imageView = (ImageView) waitingView.findViewById(R.id.iv_id);
			logoAnimation = (AnimationDrawable) imageView.getBackground();
			dialog.setContentView(waitingView);
		}

		if (isLoadingPrompt) { // 使用运营推荐消息
			// message = getMessage(ItktApplication.LOADING_PROMPT);
			if (TextUtil.stringIsNull(message)) {
				message = context.getString(R.string.please_wait);
			}
		}

		if (flag) {
			waitingView.setBackgroundResource(R.drawable.img_loading_super_bg);
		} else {
			waitingView.setBackgroundColor(context.getResources().getColor(R.color.color_hei_8));
		}
		waitingView.setBackgroundColor(context.getResources().getColor(R.color.color_hei_8));
		waitingView.invalidate();
		 logoAnimation.start(); //开始动画
		// 使用Handler因为在某些手机不会转
		if (loadingHandler == null) {
			loadingHandler = new Handler();
		}
		loadingHandler.postDelayed(new Runnable() {
			public void run() {
				logoAnimation.start(); // 开始动画
			}
		}, 50);
		TextView textView = (TextView) waitingView.findViewById(R.id.tv_id);
//		textView.setText(message);
		dialog.setCancelable(cancelable);
		dialog.show();
	}

	public void dissmissWaitingDialog() {
		if (logoAnimation != null && logoAnimation.isRunning()) { // 如果正在运行,就停止
			logoAnimation.stop();
		}
		if(dialog!=null){

			dialog.dismiss();
		}
	}

}
