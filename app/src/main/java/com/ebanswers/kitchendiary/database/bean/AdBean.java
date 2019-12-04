package com.ebanswers.kitchendiary.database.bean;

import java.util.List;

/**
 * @author caixd
 * @date 2019/9/27
 * PS:
 */
public class AdBean {
    /**
     * code : 0
     * data : [{"id":2,"name":"广告名","app":"全部","image":"http://pic1.win4000.com/wallpaper/c/53cdd1f7c1f21.jpg","url":"http://www.baidu.com","info":"测试广告信息","show_time":3,"start_data":"2019-09-23 12:01:00","end_data":"2019-09-23 12:01:00"},{"id":5,"name":"佳威路","app":"全部","image":"http://storage.56iq.net/group1/M00/12/5E/CgoKTl2JfmGAM29uAEH6ukVHhUc453.png","url":"http://wechat.53iq.com/tmp/static/html/act1101/index.html?openid=oYazTsvUlGpNBK08j9JZkPk5fQoc&acv_id=5d8322a116ec9c4afded3cd1&device_name=jiaweilu_acp&expired=0","info":"佳威路蒸烤菜谱","show_time":5,"start_data":"2019-09-25 00:00:00","end_data":"2019-09-30 12:00:00"}]
     */

    private int code;
    private List<AD> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<AD> getData() {
        return data;
    }

    public void setData(List<AD> data) {
        this.data = data;
    }
}
