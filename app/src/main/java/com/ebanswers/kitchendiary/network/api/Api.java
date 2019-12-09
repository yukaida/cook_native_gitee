package com.ebanswers.kitchendiary.network.api;

import android.os.Environment;

/**
 * Created by Administrator on 2017/12/14/014.
 */

public class Api {

    /**
     * sdcard CROP provisional path
     */
    public static final String PROVISIONAL_PATH = Environment
            .getExternalStorageDirectory() + "/ctb/album/";

//    登录
    public static final  String LOGIN = "agent_Yud_checklogin.html";

    //修改密码
    public static final String MODIFY_PASS = "agent_Yud_forgetpwd.html";

    //活动信息
    public static final String HOME_INFO = "tmp/kitchen/recommend";

    //更多精彩
    public static final String Square = "tmp/kitchen/food/square";

    //关注
    public static final String follower = "tmp/kitchen/follower";

    //收藏
    public static final String collect = "tmp/kitchen/collect";

     //我的
    public static final String mine = "tmp/kitchen/food/diary";


    //我的信息
    public static final String mineinfo = "tmp/kitchen/myhomepage";

    //消息
    public static final String messageInfo = "tmp/kitchen/check/notify";

    //草稿数
    public static final String draftnum = "tmp/kitchen/draftnum";

    //修改用户信息
    public static final String changemsg = "tmp/user/changemsg";

    //保存草稿和发布
    public static final String cookbook = "tmp/kitchen/api/publish/cookbook";


 //保存草稿和发布
    public static final String upload_img = "tmp/kitchen/api/upload_img";


    //发现顶部
    public static final String topic = "tmp/kitchen/rank/topic";


    //获取草稿箱数据----------------------------
    public static final String draftdata = "tmp/kitchen/food/draft";

    //删除草稿箱子项数据
    public static final String draftdelete = "tmp/kitchen/food/draft";


    //获取草稿箱子项 详细数据
    public static final String draftdetail = "tmp/kitchen/food/draft";

    //获取更多评论----------
public static final String getMoreComment = "tmp/kitchen/food/square";


    //发布日记---------------------

    public static final String postDiary = "tmp/kitchen/api/publish/diary";
    //查看话题接口
    public static final String topics = "tmp/kitchen/hot/topic";

}
