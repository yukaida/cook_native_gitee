package com.ebanswers.kitchendiary.network.response;

import com.ebanswers.kitchendiary.bean.DiaryInfo;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-05
 * desc：
 */
public class DiaryResponse extends BaseResponse {


    private List<DiaryInfo> data;

    public List<DiaryInfo> getData() {
        return data;
    }

    public void setData(List<DiaryInfo> data) {
        this.data = data;
    }

}
