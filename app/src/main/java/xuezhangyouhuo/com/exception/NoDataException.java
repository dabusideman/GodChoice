package xuezhangyouhuo.com.exception;


/**
 * 
 * @createdate 2014-2-11 下午4:32:56
 * @Description:  自定义异常类————没有数据返回
 */
public class NoDataException extends Exception {
	private static final long serialVersionUID = 738131917943443382L;

	public NoDataException() {
		super();
	}

	public NoDataException(String msg) {
		super(msg);
	}

	public NoDataException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public NoDataException(Throwable cause) {
		super(cause);
	}
}