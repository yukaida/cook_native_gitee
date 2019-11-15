package com.ebanswers.kitchendiary.constant;

import android.os.Environment;

public class AppConstant {
    //生产环境
    //public static final String URL_PATH = "http://app.yitiangroup.cn:6555/acl";
    //public static final String HTTP = "http://app.yitiangroup.cn:8280/services/webServer";
    //测试环境
    public static final String URL_PATH = "http://39.106.212.196/acl";
//	public static final String URL_PATH = "http://192.168.1.61:8385/acl";

    //服务器中新版本的app的地址
    public static final String NEW_VERSION_APP_URL = "http://oh0vbg8a6.bkt.clouddn.com/app-debug.apk";
    //下载后的新版本的apk的名称
    public static final String NEW_VERSION_APK_NAME = "new.apk";

    /**
     * sdcard CROP provisional path
     */
    public static final String PROVISIONAL_PATH = Environment
            .getExternalStorageDirectory() + "/ctb/album/";

    public static final String MP3_SAVE = Environment
            .getExternalStorageDirectory() + "/ctb/mp3/";

    public static final String USER_ID = "userId";
    public static final String MOBILE= "cellphone";
    public static final String PASS= "pass";
    public static final String STORE= "store";
    public static final String BOBAO= "bobao";

    public static final String USER_ITCODE = "userItcode";

    public static final String USER_NAME = "userName";

    public static final String USER_IMAGE = "userImage";

    public static final String USER_OBJECT = "userObject";

    public static final String USER_ROLE = "userRole";

    public static final String ISSAVE =  "isSave";

    public static final String currentTabIndex =  "currentTabIndex";

    public static final String myFragmentFlag =  "myFragmentFlag";
    public static final String myFragmentURL =  "myFragmentURL";

    public static final String deptName =  "deptName";
    public static final String deptId =  "deptId";

    public static final String positionNaming =  "positionNaming";

    public static final String userNameTitle =  "userNameTitle";
    public static final String dynamicKey =  "dynamicKey";

    public static final String repice =  "repice";

    public static final String pic =  "pic";


}
