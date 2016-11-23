package godchoose.com.util;

import android.util.Base64;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

import godchoose.com.bean.BaseBeans;
import godchoose.com.globle.GodchooseApplication;

/**
 * Created by peter on 15/10/29.
 */
public abstract class HttpFileUtils {

    public  void post(String url,File file,Map<String, String> paramMap, final int size){
        String param = JsonUtil.parseMapToJson(paramMap);
        try {
            RequestParams params  = new RequestParams();
            params.put("params", Base64Utils.encryptBASE64(param));
            params.put("upload_file",file);
            LogUtil.e("--------url------" + url);
            LogUtil.e("--------param------" + Base64Utils.encryptBASE64(param));
            LogUtil.e("--------param------" + param);
            LogUtil.e("--------upload_file-------" + file);
            GodchooseApplication.asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onProgress(int bytesWritten, int totalSize) {
                    super.onProgress(bytesWritten, totalSize);

                    LogUtil.e("bytesWritten=="+bytesWritten+"//totalSize=="+totalSize);

                    onMyProgress(bytesWritten, totalSize);
                }

                @Override
                public void onRetry(int retryNo) {
                    super.onRetry(retryNo);
                    LogUtil.e("retryNo==" + retryNo);
                }

                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    try {
                        LogUtil.e("--成功---base64" + new String(bytes));
                        String json = new String(Base64.decode(bytes, Base64.NO_WRAP), "UTF-8");
                        LogUtil.e("--成功---json" + json);
//                    JsonUtil.parseJsonToBean(json, Objects.class)
                        initData(json,size);


                    } catch (Exception e) {
                        e.printStackTrace();
                        ErrorData(size);
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    ErrorData(size);
                    LogUtil.e("没返回");
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            ErrorData(size);
        }

    }
    public  void post(String url,File file,String param, final int size){
        try {
            RequestParams params  = new RequestParams();
            params.put("params", Base64Utils.encryptBASE64(param));
            params.put("uploadFile",file);
            LogUtil.e("--------url------" + url);
            LogUtil.e("--------param------" + Base64Utils.encryptBASE64(param));
            LogUtil.e("--------param------" + param);
            LogUtil.e("--------file-------" + file);
            GodchooseApplication.asyncHttpClient.post(url, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    try {
                        LogUtil.e("--成功---" + new String(bytes));
                        String json = new String(Base64.decode(bytes, Base64.NO_WRAP), "UTF-8");
                        LogUtil.e("--成功---" + json);

                        initData(json,size);
                        BaseBeans baseBeans = JsonUtil.parseJsonToBean(json, BaseBeans.class);



                    } catch (Exception e) {
                        e.printStackTrace();
                        ErrorData(size);
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    ErrorData(size);
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public abstract void initData(String json,int size);
    public abstract void ErrorData(int size);
    public abstract void onMyProgress(int bytesWritten, int totalSize);
}
