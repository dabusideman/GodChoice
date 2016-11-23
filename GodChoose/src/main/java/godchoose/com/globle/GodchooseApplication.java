package godchoose.com.globle;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.view.WindowManager;

import com.loopj.android.http.AsyncHttpClient;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;


/**
 * Created by peter on 15/9/28.
 */
public class GodchooseApplication extends Application {



    public static final int REQUEST_CODE_TAKE_PHOTO = 4;
    public static final int REQUEST_CODE_SELECT_PICTURE = 6;
    public static final int RESULT_CODE_SELECT_PICTURE = 8;
    public static final int REQUEST_CODE_SELECT_ALBUM = 10;
    public static final int RESULT_CODE_SELECT_ALBUM = 11;
    public static final int REQUEST_CODE_BROWSER_PICTURE = 12;
    public static final int RESULT_CODE_BROWSER_PICTURE = 13;
    public static final int REQUEST_CODE_CHAT_DETAIL = 14;
    public static final int RESULT_CODE_CHAT_DETAIL = 15;
    public static final int REQUEST_CODE_FRIEND_INFO = 16;
    public static final int RESULT_CODE_FRIEND_INFO = 17;
    public static final int REQUEST_CODE_CROP_PICTURE = 18;
    public static final int REQUEST_CODE_ME_INFO = 19;
    public static final int RESULT_CODE_ME_INFO = 20;
    public static final int REQUEST_CODE_ALL_MEMBER = 21;
    public static final int RESULT_CODE_ALL_MEMBER = 22;
    public static final int REFRESH_GROUP_NAME = 3000;
    public static final int REFRESH_GROUP_NUM = 3001;
    public static final int ON_GROUP_EVENT = 3004;
    public static final int PAGE_MESSAGE_COUNT = 18;

    private static final String JCHAT_CONFIGS = "JChat_configs";
    public static final String TARGET_APP_KEY = "targetAppKey";
    public static final String TARGET_ID = "targetId";
    public static final String NAME = "name";
    public static final String NICKNAME = "nickname";
    public static final String GROUP_ID = "groupId";
    public static final String IS_GROUP = "isGroup";
    public static final String GROUP_NAME = "groupName";
    public static final String STATUS = "status";
    public static final String POSITION = "position";
    public static final String MsgIDs = "msgIDs";
    public static final String DRAFT = "draft";
    public static final String DELETE_MODE = "deleteMode";
    public static final String MEMBERS_COUNT = "membersCount";
    public static final String PICTURE_DIR = "sdcard/JChatDemo/pictures/";


    public static Context context;

    public static String CACHE_DIR_SD; // 资源缓存的SD卡的文件
    public static boolean IS_EXIST_SDCARD;
    public static String LOG; // 日志保存的SD卡的目录
    public static String AllLOG;
    public static String CACHE_DIR_SYSTEM; // 资源缓存的SYSTEM;卡的文件
    public static AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
    public static int windowWith;
    public static int windownHeight;


    public static String imei;
    @Override
    public void onCreate() {
        super.onCreate();
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
        mHandler = new Handler();
        context = this;
        //获取手机唯一标示
        /* 初始化ImageLoader */
        initImageLoader(context);
        //获取屏幕的尺寸
        getWindow();

    }



    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
//		  ImageLoaderConfiguration.createDefault(this);
        // method.
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();//不会在内存中缓存多个大小的图片
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());//为了保证图片名称唯一
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        //内存缓存大小默认是：app可用内存的1/8
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
//		ImageLoader.getInstance().init( ImageLoaderConfiguration.createDefault(this));
    }

    public static Context getContext(){
        return context;
    }





    private void getWindow(){
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

         windowWith = wm.getDefaultDisplay().getWidth();
         windownHeight = wm.getDefaultDisplay().getHeight();

    }
    private static Handler mHandler;
    /**
     * 获取全局的Handler，也就是主线程中的Handler
     *
     * @return
     */
    public static Handler getMyHanlder() {
        return mHandler;
    }
}
