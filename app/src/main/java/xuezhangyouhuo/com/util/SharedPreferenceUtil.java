package xuezhangyouhuo.com.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author ty
 * @createdate 2012-8-17 下午12:22:19
 * @Description: SharedPreference
 */
public class SharedPreferenceUtil {
	public static final String SHARED_PREFERENCE_NAME = "itktnew"; // SharedPreference操作的文件

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:26:01
	 * @Description: 保存int数值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveInt(Context context, String key, int value) {
		Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putInt(key, value);
		editor.commit();
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:28:46
	 * @Description: 获取保存的int数值
	 * @param context
	 * @param key
	 * @return
	 */
	public static int getInt(Context context, String key) {
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		int value = shared.getInt(key, 0);
		return value;
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:26:01
	 * @Description: 保存long数值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveLong(Context context, String key, long value) {
		Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putLong(key, value);
		editor.commit();
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:28:46
	 * @Description: 获取保存的long数值
	 * @param context
	 * @param key
	 * @return
	 */
	public static long getLong(Context context, String key) {
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		long value = shared.getLong(key, 0L);
		return value;
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-10-13 上午11:50:33
	 * @Description: 保存boolean值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveBoolean(Context context, String key, boolean value) {
		Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	/**
	 * @author miaoxin.ye
	 * @createdate 2012-10-13 上午11:51:40
	 * @Description: 获取boolean值
	 * @param context
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(Context context, String key) {
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		boolean value = shared.getBoolean(key, true);
		return value;
	}
	public static boolean getBooleanFalse(Context context, String key) {
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		boolean value = shared.getBoolean(key, false);
		return value;
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:26:01
	 * @Description: 保存String数值
	 * @param context
	 * @param key
	 * @param value
	 */
	public static void saveString(Context context, String key, String value) {
		Editor editor = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(key, value);
		editor.commit();
	}

	/**
	 * @author ty
	 * @createdate 2012-8-17 下午12:28:46
	 * @Description: 获取保存的String数值
	 * @param context
	 * @param key
	 * @return
	 */
	public static String getString(Context context, String key) {
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		String value = shared.getString(key, "");
		return value;
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-29 下午2:21:59
	 * @Description 清空本地的缓存
	 * @param context
	 * @param key
	 */
	public static void removeString(Context context, String key) {
		SharedPreferences shared = context.getSharedPreferences(SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.remove(key);
		editor.commit();
	}

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-10-19 下午2:36:35
	 * @Description 保存信用卡信息
	 * @param isNotVip
	 *            是否是非会员，true为非会员，false为会员
	 */
	// public static void saveCreditCardInfo(Context context, CreditCardModel
	// bean, boolean isNotVip) {
	// // boolean isNotVip =
	// this.getIntent().getBooleanExtra(IntentConstants.NO_VIP, true); //
	// 是否是非会员，true为非会员，false为会员
	// if (isNotVip == false) { // 只有会员才保存信用卡信息
	// SharedPreferenceUtil.saveString(context, "credit_card_userId",
	// ItktApplication.USER_ID); // 用户ID
	// SharedPreferenceUtil.saveString(context, "credit_card_id", bean.getId());
	// SharedPreferenceUtil.saveString(context, "credit_card_username",
	// bean.getUserName());
	// SharedPreferenceUtil.saveString(context, "credit_card_idCard",
	// bean.getIdCard());
	// SharedPreferenceUtil.saveString(context, "credit_card_bank",
	// bean.getBank());
	// SharedPreferenceUtil.saveString(context, "credit_card_bankId",
	// bean.getBankId());
	// SharedPreferenceUtil.saveString(context, "credit_card_bankIdCard",
	// bean.getBankIdCard());
	// SharedPreferenceUtil.saveString(context, "credit_card_validityDate",
	// bean.getValidityDate());
	// }
	// }

	/**
	 * 
	 * @author bo.wang
	 * @createdate 2012-11-16 下午3:37:42
	 * @Description 清空本地信用卡信息
	 */
	public static void clearCreditCardInfo(Context context) {
		SharedPreferenceUtil.removeString(context, "credit_card_userId");
		SharedPreferenceUtil.removeString(context, "credit_card_id");
		SharedPreferenceUtil.removeString(context, "credit_card_username");
		SharedPreferenceUtil.removeString(context, "credit_card_idCard");
		SharedPreferenceUtil.removeString(context, "credit_card_bank");
		SharedPreferenceUtil.removeString(context, "credit_card_bankId");
		SharedPreferenceUtil.removeString(context, "credit_card_bankIdCard");
		SharedPreferenceUtil.removeString(context, "credit_card_validityDate");
	}
}
