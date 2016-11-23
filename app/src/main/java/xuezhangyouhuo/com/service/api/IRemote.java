package xuezhangyouhuo.com.service.api;


import xuezhangyouhuo.com.bean.BaseBeans;
import xuezhangyouhuo.com.bean.DataVo;

/**
 * 
 * @author zjy
 * @createdate 2014-6-21 上午10:30:36
 * @Description: 接口
 */
public interface IRemote {

	/**
	 * 
	 * @Description:TODO 获取初始data页数据
	 * @auther:LC_22 
	 * @time:2015-7-1 上午11:07:51
	 */
	public DataVo getPicBelongList(String pageindex)
			throws Exception;


	public DataVo getProductDetail(String pageindex)
			throws Exception;


	public BaseBeans getUID(String uid)
			throws Exception;

	public BaseBeans postTestBean(String uid)
			throws Exception;
	
}