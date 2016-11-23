package godchoose.com.exception;
/**
 * @createdate 2012-8-4 下午3:27:23
 * @Description: WebService未能连接上服务器
 */
public class HttpTransportSENoConnectionException extends Exception {
	private static final long serialVersionUID = 738131917943443382L;

	public HttpTransportSENoConnectionException() {
		super();
	}

	public HttpTransportSENoConnectionException(String msg) {
		super(msg);
	}

	public HttpTransportSENoConnectionException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public HttpTransportSENoConnectionException(Throwable cause) {
		super(cause);
	}
}