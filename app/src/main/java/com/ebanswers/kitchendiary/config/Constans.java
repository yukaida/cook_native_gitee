package com.ebanswers.kitchendiary.config;

/**
 * @author Created by lishihui on 2017/1/10.
 */

public class Constans {
    //wechat
    public static String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    public static String GetUserInfo = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";
//    public static final String WX_APP_ID = "wxeca9697274e9a75e";
    public static final String WX_APP_ID = "wxc0c98be669f24f3c";
//    public static final String WX_APP_SECRET = "42c9037e54a64bfeaaa1710c56a15204";
    public static final String WX_APP_SECRET = "794c685413551d3a89d33c4cec307bf0";
    public static final String CheckTokenIsUseful = "https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s";
    public static final String WX_REFRESH_Token = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    public static final String Tag_Title = "https://wechat.53iq.com/tmp/kitchen/user/tag?code=123";
    public static final String Relate_Title = "https://wechat.53iq.com/tmp/kitchen/relate/me?code=123";
    public static final String Diary_Main_Page = "https://wechat.53iq.com/tmpd&lang=%s";//首页
    //    public static final String Find_Page_Title = "https://wechat.53iq.com/tmp/kitchen/recommend?code=123";
    public static final String Find_Page_Title = "https://wechat.53iq.com/tmp/static/html/newIndex/newIndex.html?code=123";

    public static final String Diary_Find_Page = "https://wechat.53iq.com/tmp/spp=android&lang=%s";//发现
//    public static final String Diary_Main_Page_Title = "https://wechat.53iq.com/tmp/kitchen/food/square?code=123";
    public static final String Diary_Main_Page_Title = "https://wechat.53iq.com/tmp/static/html/newIndex/foodsquare.html?code=123";
    public static final String Diary_Core_Page = "https://wechat.53iq.com/tmp/kiapp=android&lang=%s";//关注
    public static final String Own_Page = "https://wechat.53iq.com/tmp/kitchen/publish/diary?code=123&state=diary&openid=%s";
    public static final String Recipe_Url = "https://wechat.53iq.com/tmp/kitchen/cookbook/";
    public static final String Recipe_Assistant = "http://wechat.hat&openid=%s&lang=%s";//蒸烤助手
    public static final String Url_Acp = "https://oven.53iq.ctaticoken=%s&lang=%s";//ACP

    public static final String Recipe_Assistant_Title = "http://wechatkitchen/search?code";
    public static final String PUBLISH_RECIPE = "https://wechat.53ite=app&openid=%s&topic=%s";//发布菜谱
    public static final String Recipe_collect = "https://wechat.53n=get_info&openid=%s&lang=%s";
    static final String Access_Token_Key = "access_token";
    static final String Access_Refresh_Token_Key = "access_refresh_token";
    public static final String Own_Page_real = "https://wec/diary?v=wer";
    public static final String Own_Collect = "https://wechat.53iqcollections&code=123";
    public static final String Notify = "https://wechat.53iq.com/tmp/g&code=123&openid=%s";
    public static final String About_53iq = "http://wechat.53iq./about.html";
    public static final String About_Kitchen = "https://mp.weixin.qq.comn=7e202f0fb2f4b89ef5ae3d02bd6d283f#wechat_redirect";
    public static final String Wechat_Authorize = "https://open.weixin.qq.com/connect/oauth2/authorize";
    public static final String Wechat_Authorize_Redirect = "redirect_uri=";
    public static final String Wechat_Redirect_User = "https://wechat.53iq.com/tmp/user/info?openid=%s&redirect_uri=%s";
    public static final String Bind_phone = "https://oven.53iq.com/webapp/jump?page=bind_phone&openid=";
    static final String Open_Id_Key = "open_id";
    static final String Union_Id_Key = "union_id";
    static final String Public_Number_OpenId_Key = "public_open_id";
    static final String Wechat_Head_Url = "head_url";
    static final String Wechat_NickName = "nickname";
    public static final String Publish_Flag_Key = "publish";
    public static final String Public_Diary_Content = "topic";
    public static final int Publish_Recipe_Flag = 0x01;
    public static final int Publish_Diary_Flag = 0x02;
    public static final String BIND_DEVICE = "https:bind"; //设备绑定
    public static final String MEDA_FACTORY_ID = "ead4e16c-579a-11e5-9e88-00a0d1eb6068";
    public static final String OVEN_DEVICE_LIST = "https://oven.%s&factory_id=%s";//设备列表界面
//    public static final String OVEN_DEVICE_LIST = "https://oven.53iq.com/static/list/index.html#/new_index?token=%s&factory_id=%s";//设备列表界面
    public static final String OVEN_DEVICE_CONTROL = "https://odevice/%s";//设备控制界面
    public static final String OVEN_DEVICE_INFO = "https://ovdevice/info";//获取设备基础信息
    public static final String OVEN_DEVICE_IS_MASTER = "https://o_master";//查询用户是否是管理员
    public static final String OVEN_DEVICE_BOUND_COUNT = "https://ond_users?mac=%s";//查询绑定设备的用户列表
    public static final String OVEN_DEVICE_SHARE = "https://ove?token=%s&mac=%s";//分享设备二维码
    public static final String PHONE_CHECK_CODE = "https://api.53iq.com/1/telephone/code";//中国手机号验证码

    public static final String INTERNATIONAL_PHONE_CHECK_CODE = "https://api.53iq.com/1/telephone/intl_code";//国外手机号验证码
    //    public static final String PHONE_CHECK_CODE = "https://oven.53iq.com/webapp/jump";//获取手机验证码

    public static final String ACCOUNT_MANAGER = "https://oven.53iq.com/api/account";//账号注册和密码修改

    public static final String USER_CLOCKDAYS = "http://wechat.53iq.com/tmp/kitchen/publish/diary";//获取用户打开天数
    public static final String ACCOUNT_LOGIN = "https://api.53iq.com/v1/user/login";//新接口
    public static final String ACCOUNT_LOGOUT = "https://api.53iq.com/v1/user/logout";
    public static final String ACCOUNT_REGISTER = "https://oven.53iq.com/v1/user/register";//账号注册
    public static final String REQUEST_BARCODE = "https://api.53iq.com/v1/barcode/goods/details?barcode=%s";//扫码条形码查询商品详情
    public static final String DEVICE_TOKEN = "device_token";
    static final String PHONE_NUMBER = "phone_number";
    static final String EMAIL_ACCOUNT = "email_account";
    static final String NATION_CODE = "NATION_CODE";
    static final String WECHAT_USERINFO_JSON = "WechatUserInfoJson";
    static final String Login_Result_Info_JSON = "LoginResultInfoJson";
    public static final String CONNECT_LOST_RECEIVER_ACTION = "com.ebanswers.kitchendiary.refresh";
    public static final String UPDATE_TOKEN = "https://oven.53iq.com/api/update_token";
    public static final String UPDATE_DEVICE_NAME = "https://oven.53iq.com/api/device/update";
    public static final String COMMON_DEVICE_CONTROL = "https://ovdevice/control";//设备通用控制接口
    public static final String SETTING_DEVICE = "https://oven.53iq.com/static/list/index.html#/setting";
//    public static final String SETTING_UPDATE = "http://ota.53iq.com/static/file/meida.txt";//app检查新版本
    public static final String SETTING_UPDATE = "http://ota.53iq.com/static/file/kitchendiary.txt";//app检查新版本
    public static final String SETTING_UPDATE_DEBUG = "http://ota.53iq.com/static/file/smartkitchentest.txt";
    //zncf: 通用版智能厨房  md:美大版  sjt: 司杰特版  arda:安德版  kinde:金帝版 daogrs:道格斯版  xx_ios:对应ios版
    public static final String URL_HOT_RECIPE = "wechat.53iq.com/tmp/kiook";//热门菜谱
    public static final String URL_CONNECTION_RECIPE = "https://wechat.53iq.ctions&code=123";//收藏菜谱
    public static final String URL_SEARCH_GOODS = "https://oven.53iq.com/s=%s&goods=%s&standard=%s";//搜索商品
    public static final String URL_SEARCH_ACP = "http://oven.53iq.com/staarch_key=%s";//搜索ACP

    public static final String URL_PRIVACY = "file:////android_asset/KitchenDiary_read_privacy2.html";//隐私政策
    public static final String URL_PROTOCOL = "file:////android_asset/KitchenDiary_read_privacy.html";//用户协议
    public static final String URL_RULE = "file:////android_asset/clock_rule.html";//用户协议



    public static final String SEARCH_RECIPE = "http://wechapish/act_cookbook";//用户关联菜谱
    public static final String WIFI_CONFIG = "http://192.1ttp-config.json";//AP配网
    public static final String URL_AD_SERVER = "http://ad.53iq.com/ad";//广告正式环境
    public static final String URL_AD_DEBUG = "http://192.1000/ad";//广告测试环境
    public static final String URL_LATEST_ACP = "http://api?key=test_acp";//使用过的最近一条ACP
    public static final String URL_ACP = "http://oven.53iq.com/soken=%s&cook_id=%s&start_acp=show";//acp界面


    public static final String PHONE_EMAIL_ISORNOTBIND = "https://api.53iq.com/1/user/phone_email";//使用（GET/phone）查询用户是否绑定过邮箱
    //返回值message code  email (code -1  缺少请求参数 ) （code 1 没有绑定邮箱 email null） （code 2 已经绑定邮箱 返回邮箱地址 email ）
    public static final String PHONE_EMAIL_GETCHECKCODE = "https://api.53iq.com/1/user/phone_email";//使用(POST/action=get_code email phone lang=en-US  subject=kitchen diary register mail)
    //获取邮箱验证码
    public static final String PHONE_EMAIL_BIND="https://api.53iq.com/1/user/phone_email";//使用（POST/action=bind email phone code验证码）
    //绑定邮箱  返回值 message code   成功(code 0 message:绑定邮箱成功) （code 2 message 验证码错误） （code 1 没有此用户）

    public static final int DEVICE_TYPE_OVEN = 11;//烤箱
    public static final int DEVICE_TYPE_STEAM = 20;//蒸箱
    public static final int DEVICE_TYPE_AIO = 21;//蒸烤一体机
    public static final int DEVICE_TYPE_MICRO= 30;//微蒸烤
    public static final String factoryId = "zncf";
    public static final String PACKAGE_SINA = "com.sina.weibo";
    public static final int DIARY_REQUEST_IMAGE = 0x01;
    public static final int FILE_SELECT_CODE = 0x00;
    public static final int TYPE_PERMISSION_STORAGE = 177;
    public static final String EVENT_UDP_MAC = "EVENT_UDP_MAC";


    public static final String AGREEN_PRIVACY = "AGREEN_PRIVACY";
}
