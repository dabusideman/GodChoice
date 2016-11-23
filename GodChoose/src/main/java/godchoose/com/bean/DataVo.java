package godchoose.com.bean;

import java.io.Serializable;

/**
 *  ZPX
 * @author LC_22
 *
 */
public class DataVo extends BaseVo implements Serializable{
	public FragmentDataListVo data;

	public FragmentDataListVo getData() {
		return data;
	}

	public void setData(FragmentDataListVo data) {
		this.data = data;
	}
}
