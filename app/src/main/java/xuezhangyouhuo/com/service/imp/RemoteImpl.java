package xuezhangyouhuo.com.service.imp;

import java.util.HashMap;
import java.util.Map;

import xuezhangyouhuo.com.bean.BaseBeans;
import xuezhangyouhuo.com.bean.DataVo;
import xuezhangyouhuo.com.globle.XuezhangYouHuoApplication;
import xuezhangyouhuo.com.service.api.IRemote;
import xuezhangyouhuo.com.util.JsonUtil;
import xuezhangyouhuo.com.util.http.ConnectionUtil;


public class RemoteImpl implements IRemote {
	private static IRemote remote;
	String arrival, endDay;

	private RemoteImpl() {
	}

	static {
		remote = new RemoteImpl();
	}

	public static IRemote getInstance() {
		return remote;
	}


	@Override
	public DataVo getPicBelongList(String pageindex) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", "");
		params.put("imei", XuezhangYouHuoApplication.imei);
		params.put("platformFlag", "1");
		String json = ConnectionUtil
				.get("http://www.duitang.com/album/1733789/masn/p/"+ pageindex + "/8/");
		return JsonUtil.parseJsonToBean(json, DataVo.class);
	}

	@Override
	public DataVo getProductDetail(String pageindex) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("token", "");
		params.put("imei", XuezhangYouHuoApplication.imei);
		params.put("platformFlag", "1");
		String json = ConnectionUtil
				.get("http://www.duitang.com/album/1733789/masn/p/"+ pageindex + "/3/");
		return JsonUtil.parseJsonToBean(json, DataVo.class);
	}

	@Override
	public BaseBeans getUID(String uid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		String paramJson = JsonUtil.parseMapToJson(params);
		String json = ConnectionUtil
				.post(paramJson,"method=checkGo");
		return JsonUtil.parseJsonToBean(json, BaseBeans.class);
	}

	@Override
	public BaseBeans postTestBean(String uid) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("uid", uid);
		String paramJson = JsonUtil.parseMapToJson(params);
		String json = ConnectionUtil
				.post(paramJson,"servlet/ServletDoPost");
		return JsonUtil.parseJsonToBean(json, BaseBeans.class);
	}


}
