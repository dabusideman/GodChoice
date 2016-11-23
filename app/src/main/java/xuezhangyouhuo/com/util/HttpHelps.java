package xuezhangyouhuo.com.util;

import android.app.Activity;
import android.util.Base64;
import android.view.View;
import android.widget.FrameLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import xuezhangyouhuo.com.R;
import xuezhangyouhuo.com.globle.XuezhangYouHuoApplication;

/**
 * Created by peter on 15/10/26.
 */
public abstract class HttpHelps {

//    public static String baseUrl = "http://192.168.1.105/?";
//    public static String baseUrl = "http://api.uu2me.com/?";
    public static String baseUrl = "http://api.uutome.com/?";
    private View progressBar;

    public  void post(String url, Map<String, String> paramMap, final int size) {
        String param = JsonUtil.parseMapToJson(paramMap);
        RequestParams params  = new RequestParams();
        params.put("params", Base64Utils.encryptBASE64(param));
        LogUtil.e("--------url------" + url);
        LogUtil.e("--------param------" + Base64Utils.encryptBASE64(param));
        LogUtil.e("--------param------" + param);

        XuezhangYouHuoApplication.asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                LogUtil.e("--成功---"+new String(bytes));
                try {
                    String json = new String(Base64.decode(bytes, Base64.NO_WRAP), "UTF-8");
                    LogUtil.e("--成功---" + json);
//                    JsonUtil.parseJsonToBean(json, Objects.class)
                    initData(json,size);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtil.e("--异常---");
                    initFailure(size);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                LogUtil.e("--失败---");
                initFailure(size);
            }
        });


    }
    public  void post(String url, Map<String, String> paramMap,Activity context, final int size) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams
                (FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        progressBar = View.inflate(context, R.layout.page_loading, null);
        progressBar.setVisibility(View.VISIBLE);
        context.addContentView(progressBar,layoutParams);
        RequestParams params  = new RequestParams();
        String param = JsonUtil.parseMapToJson(paramMap);
        LogUtil.e("--------url------" + url);

        params.put("params", Base64Utils.encryptBASE64(param));
        LogUtil.e("--------param加密------" + Base64Utils.encryptBASE64(param));
        LogUtil.e("--------param原始------" + param);

        XuezhangYouHuoApplication.asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                LogUtil.e("------成功原始-------"+new String(bytes));
                try {
                    String json = new String(Base64.decode(bytes, Base64.NO_WRAP), "UTF-8");
                    LogUtil.e("------成功解码-------" + json);
//                    JsonUtil.parseJsonToBean(json, Objects.class)
                    initData(json,size);
                    progressBar.setVisibility(View.INVISIBLE);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtil.e("--异常---");
                    initFailure(size);
                }

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                LogUtil.e("--失败---");
                initFailure(size);
            }
        });


    }

    public abstract void initData(String json,int size);

    public abstract void initFailure(int size);


}
