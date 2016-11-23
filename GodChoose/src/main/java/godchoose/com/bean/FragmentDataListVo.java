package godchoose.com.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *  ZPX
 * @author LC_22
 *
 */
public class FragmentDataListVo extends BaseVo implements Serializable{
	public ArrayList<FragmentDataDetailVo> blogs;

	public ArrayList<FragmentDataDetailVo> getBlogs() {
		return blogs;
	}

	public void setBlogs(ArrayList<FragmentDataDetailVo> blogs) {
		this.blogs = blogs;
	}
}
