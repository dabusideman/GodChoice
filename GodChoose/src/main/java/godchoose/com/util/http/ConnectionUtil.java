package godchoose.com.util.http;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import godchoose.com.exception.NoDataException;
import godchoose.com.util.FileLocalCache;
import godchoose.com.util.JsonUtil;
import godchoose.com.util.LogUtil;
import godchoose.com.util.TextUtil;



/**
 * @author ty
 * @createdate 2012-9-19 上午10:40:06
 * @Description: Http请求
 */
public class ConnectionUtil {
	
//	private static final String URL = "http://192.168.1.105/?";//
//	private static final String URL = "http://api.uu2me.com/?";//
	private static final String URL = "http://192.168.1.110:8080/WebManagerTest/check?";//
	private static final String URLpost = "http://10.22.116.21:8080/testBaby/";//


	private static final String ACCEPT = "Accept-Charset";
	private static final String UTF8 = "UTF-8";
	private static final String UTF8_ES = "UTF-8,*";
	private static final String CONNECTION_JSON = "json";
	private static final String APP_JSON = "APPLICATION/JSON";
	public static final int CACHE_DIR_SD = 10; // 缓存在SD卡的文件
	private static final String REQUEST_URL = "请求：URL:-->:";
	private static final String RESPONSE_STATUS = "响应状态码：";
	private static final String RESPONSE_PARAMS = "---返回的参数为--->>：";
	private static final String TERMINAL_ID = "terminalId";


	/**
	 * 
	 * @author ty
	 * @createdate 2013-3-21 上午10:22:01
	 * @Description: post请求
	 *            请求的URL
	 * @param params
	 *            请求的参数封装
	 * @return
	 * @throws Exception
	 * 
	 */
	public static String post(Map<String, Object> params) throws Exception {
		LogUtil.e(REQUEST_URL + URL);
		HttpPost httpost = new HttpPost(URL);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		StringBuilder sb=new StringBuilder();
		sb.append(REQUEST_URL + URL+"?");
		if (params != null) {
			for (String key : params.keySet()) {
				LogUtil.e("key:" + key + ", value:" + params.get(key));
				sb.append(key)
				.append("=")
				.append(params.get(key))
				.append("&");
				
				nvps.add(new BasicNameValuePair(key,
						params.get(key) == null ?"" : params.get(key)
								.toString()));

			}
			sb.deleteCharAt(sb.lastIndexOf("&"));
			LogUtil.i("请求",sb.toString());
			params.clear();
			params = null;
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		
		return execute(httpost);
	}

	private static String execute(HttpUriRequest req) throws Exception {
		long startTime = System.currentTimeMillis();

		String result = null;
		InputStream instream = null;
		try {
			HttpResponse response = executeLoad(req);
			if (response != null) {
				int statusCode = response.getStatusLine().getStatusCode();
				LogUtil.d(RESPONSE_STATUS + statusCode);
				switch (statusCode) {
				case HttpStatus.SC_OK:
					// result = parseResponse(response, instream);
					result = EntityUtils.toString(response.getEntity(), UTF8);
					// result = EntityUtils.toString(response.getEntity(),
					// UTF8);GBK
					LogUtil.e("result-->"+ result);
					break;
				case HttpStatus.SC_FORBIDDEN: // 验证未通过
					break;
				case HttpStatus.SC_NOT_FOUND: // 请求错误
					break;
				}
			}
		} finally {
			FileLocalCache.closeInputStream(instream);
		}

		// 打包时应该注释
		long endTime = System.currentTimeMillis();
		String info = "请求：Time:" + (endTime - startTime) + "-->:"
				+ req.getURI();
		LogUtil.d(info);
		if (TextUtil.stringIsNull(result)) {
			throw new NoDataException("request not data :" + req.getURI());
		}
		LogUtil.d(RESPONSE_PARAMS + result);
		FileLocalCache.saveFile(info, result);
		return result;
	}

	/**
	 * @author ty
	 * @createdate 2012-11-3 下午9:46:54
	 * @Description: 加载数据
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse executeLoad(HttpUriRequest req) throws Exception {
//		HttpClient httpclient = CustomerHttpClient.getInstance();
		/*
		 * if (NetWorkUtil.ONLYWAP == true) { HttpHost proxy = new
		 * HttpHost(DEPUTIZW_IP, 80);
		 * httpclient.getParams().setParameter(ConnRoutePNames
		 * .DEFAULT_PROXY,proxy); }
		 */
//		req.addHeader(ACCEPT, UTF8_ES);
//		HttpResponse response = httpclient.execute(req);
		HttpResponse response=new DefaultHttpClient().execute(req);
		return response;
	}

	/**
	 * 取得响应的信息流
	 * 
	 * @param response
	 * @param instream
	 * @return
	 * @throws Exception
	 */
	/*
	 * private static String parseResponse(HttpResponse response,InputStream
	 * instream) throws Exception { String result = null; HttpEntity entity =
	 * response.getEntity(); if (entity != null) { instream =
	 * entity.getContent(); result = TextUtil.InputStreamToString(instream); }
	 * return result; }
	 */
	
	
	/**
	 * 
	 * @author ty
	 * @createdate 2013-3-21 上午10:22:01
	 * @Description: post请求
	 *            请求的URL
	 * @param params
	 *            请求的参数封装
	 * @return
	 * @throws Exception
	 * 
	 */
	public static String post(String params, String name) throws Exception {
		String URLNew = URLpost + name;
		HttpPost httpost = new HttpPost(URLNew);
		LogUtil.e("请求的url为==" + URLNew);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("params", params));
		LogUtil.e("SUBMIT:" + params);

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

//		String resultJson= Base64Utils.decryptBASE64(execute(httpost));
		String resultJson= execute(httpost);
		LogUtil.e("resultJson:" + resultJson);
//		BaseBeans baseBeans = JsonUtil.parseJsonToBean(resultJson, BaseBeans.class);
//		return execute(httpost);
//		LogUtil.e("resultJson:"+resultJson);
		return resultJson;
	}


	public static String post(Map<String, Object> params, String url,String empty) throws Exception {
		String URLNew = url;
		// params.put("imei", FlyApplication.sUniquelyCode);
		// params.put("userId", FlyApplication.USER_ID);
		HttpPost httpost = new HttpPost(URLNew);
//		String json = JSON.toJSONString(params);
		String json = JsonUtil.parseMapToJson(params);
		LogUtil.i("请求的url为==" + URLNew);
		LogUtil.i("SUBMIT" + json);
		// ItktLog.e("请求的Url=" + URLNew);
		// ItktLog.e("SUBMIT" + json);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("json", json == null ? null : json));
		if (params != null) {
			for (String key : params.keySet()) {
				LogUtil.i("key:" + key + ", value:" + params.get(key));
			}
			params.clear();
			params = null;
		}
		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		return execute(httpost);
	}


	public static String get(String name)
			throws Exception {
		JSONObject js = new JSONObject();
		String result = "";
		String URLne =  name ;
		String URLnew = URLne.replace("{", "%7B").replace("}", "%7D")
				.replace("\"", "%22");

		Log.d("test3", URLnew);
		LogUtil.e(REQUEST_URL + URLnew);

		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(URLnew);
		HttpResponse res = client.execute(httpGet);
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity entity = res.getEntity();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					entity.getContent(),"GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				result = result + line;
			}

//			byte[] byteIcon = Base64.decode(result.toString(), Base64.DEFAULT);
//			result= new String(byteIcon);
			Log.e("json1", result.toString());
		}
		client.getConnectionManager().shutdown();
		return result;
		// return execute(httpGet);
	}
	/*根据给定的char集合，生成随机的字符串*/
	public static class StringWidthWeightRandom {
		private Random widthRandom = new Random();
		private int length;
		private char[] chars;
		private Random random = new Random();
		public StringWidthWeightRandom(char[] chars) {
			this.chars = chars;
		}

		//参数为生成的字符串的长度，根据给定的char集合生成字符串
		public String getNextString(int length){

			char[] data = new char[length];

			for(int i = 0;i < length;i++){
				int index = random.nextInt(chars.length);
				data[i] = chars[index];
			}
			String s = new String(data);
			return s;
		}


	}

}
