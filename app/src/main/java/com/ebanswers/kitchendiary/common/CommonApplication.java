package com.ebanswers.kitchendiary.common;

import android.app.Activity;
import android.content.Context;

import com.ebanswers.kitchendiary.R;
import com.hjq.toast.ToastUtils;
import com.scwang.smartrefresh.header.BezierCircleHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;


/**
 * desc   : 项目中的Activity基类
 */
public class CommonApplication extends UIApplication {
    public static final String DEFAULT_USER = "admin";//默认用户名
    private static final String TAG = "CommonApplication";
    private static CommonApplication instance;
    //运用list来保存们每一个activity是关键
    public  List<Activity> activityList = new LinkedList<Activity>();

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new BezierCircleHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context);
            }
        });
    }

    {
        //微信
        PlatformConfig.setWeixin("wxc0c98be669f24f3c", "794c685413551d3a89d33c4cec307bf0");
        //新浪微博
//        PlatformConfig.setSinaWeibo("2974250008", "f0ed38a6119295284c2e4960ad74679c");
        PlatformConfig.setQQZone("1105879955", "HaFPUO3O5E3FaHbR");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        AutoSizeConfig.getInstance().setCustomFragment(true);
        AutoSize.initCompatMultiProcess(this);

        // 初始化吐司工具类
        ToastUtils.init(getApplicationContext());

        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret
         */
        UMConfigure.init(this, "587dca03717c19623d001674", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "1fe6a20054bcef865eeb0991ee84525b");
        // 一些分享配置
        UMShareConfig config = new UMShareConfig();
        config.isOpenShareEditActivity(true);
        config.isNeedAuthOnGetUserInfo(true);
        config.setSinaAuthType(UMShareConfig.AUTH_TYPE_SSO);
        UMShareAPI.get(getApplicationContext()).setShareConfig(config);
    }


    public static CommonApplication getInstance() {
        return instance;
    }

    /**
     * 添加activity
     */
    public void addActivity(Activity act) {
        activityList.add(act);
    }

    /**
     * 移除activity
     */
    public void removeActivity(Activity act) {
        if (act != null) {
            if (activityList.contains(act)) {
                activityList.remove(act);
            }
            act.finish();
            act = null;
        }
    }
    /**
     * 移除全部 activity
     * @param act
     */
    public void removeAllActivity(Activity act) {

        Iterator<Activity> iterator = activityList.iterator();
        while(iterator.hasNext()){
            Activity activity = iterator.next();
            if (activity != null && activity != act) {
                if (activityList.contains(activity)) {
                    activity.finish();
                    iterator.remove();   //注意这个地方
                }
            }
        }
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }
}