package godchoose.com.util;


/**
 * Created by IntelliJ IDEA.
 * User: zhouxin@easier.cn
 * 字符串的处理类
 * Date: 12-11-22
 * Time: 下午4:35
 * To change this template use File | Settings | File Templates.
 */
public class StringUtil {
    /**
     * 判断是否为null或空值
     *
     * @param str String
     * @return true or false
     */
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
    /**
     * <p>
     * Checks if a String is not empty (""), not null and not whitespace only.
     * </p>
     * 
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     * 
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null and not
     *         whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
	return !StringUtil.isBlank(str);
    }
    
    public static boolean isBlank(String str) {
	int strLen;
	if (str == null || (strLen = str.length()) == 0) {
	    return true;
	}
	for (int i = 0; i < strLen; i++) {
	    if ((Character.isWhitespace(str.charAt(i)) == false)) {
		return false;
	    }
	}
	return true;
    }
    /**
     * 判断str1和str2是否相同
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equals(String str1, String str2) {
        return str1 == str2 || str1 != null && str1.equals(str2);
    }

    /**
     * 判断str1和str2是否相同(不区分大小写)
     *
     * @param str1 str1
     * @param str2 str2
     * @return true or false
     */
    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 != null && str1.equalsIgnoreCase(str2);
    }

    /**
     * 判断字符串str1是否包含字符串str2
     *
     * @param str1 源字符串
     * @param str2 指定字符串
     * @return true源字符串包含指定字符串，false源字符串不包含指定字符串
     */
    public static boolean contains(String str1, String str2) {
        return str1 != null && str1.contains(str2);
    }

    /**
     * 判断字符串是否为空，为空则返回一个空值，不为空则返回原字符串
     *
     * @param str 待判断字符串
     * @return 判断后的字符串
     */
    public static String getString(String str) {
        return str == null ? "" : str;
    }
}

