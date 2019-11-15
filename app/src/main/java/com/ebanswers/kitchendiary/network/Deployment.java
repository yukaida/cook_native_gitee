package com.ebanswers.kitchendiary.network;

import android.content.res.Resources;
import android.util.Log;

import com.ebanswers.kitchendiary.BuildConfig;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonApplication;


/**
 * 环境配置
 *
 */
public class Deployment {

    public static Resources resources = CommonApplication.getInstance().getResources();

    public static  int state_id = BuildConfig.DEPLOY_STATE;
//    public static final String BASE_URL = resources.getStringArray(R.array.base_url)[state_id];
    /**后台连接环境*/
    public static final String BASE_URL_LOGIN = resources.getStringArray(R.array.base_url_login)[state_id];

    public static final String BASE_URL_WORK = resources.getStringArray(R.array.base_url_work)[state_id];

    /**图片下载服务器**/
    public static final String IMAGE_PATH = resources.getStringArray(R.array.image_path)[state_id];
    /**图片下载服务器**/
    public static final String ENVIROMENT = resources.getStringArray(R.array.enviroment)[state_id];


    public enum DeployState{
        DEVELOPING(0),PRODUCTION(1);
        int id;
        DeployState(int id) {
            this.id=id;
        }
        static DeployState getDeployState(int id){
            if(id>=0&&id< DeployState.values().length)
                return  DeployState.values()[id];
            else {
                Log.e("deploy state error", "check build.gradle");
                return null;
            }

        }
    }
}
