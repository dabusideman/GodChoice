package xuezhangyouhuo.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowBigPicListVoDemo extends BaseVo implements Serializable{
	
	private ArrayList<ShowBigPicVoDemo> uploadMaterialList;

	public ArrayList<ShowBigPicVoDemo> getUploadMaterialList() {
		return uploadMaterialList;
	}

	public void setUploadMaterialList(
			ArrayList<ShowBigPicVoDemo> uploadMaterialList) {
		this.uploadMaterialList = uploadMaterialList;
	}
}
