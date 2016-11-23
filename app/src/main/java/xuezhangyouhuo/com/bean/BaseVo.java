package xuezhangyouhuo.com.bean;

import java.io.Serializable;

public class BaseVo implements Serializable {

	/**
	 * @author zy
	 * @adddate 2013-12-4 下午3:21:34
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int resCode;// 响应码

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	private String resDesc;// 响应码说明




	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
