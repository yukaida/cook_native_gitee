package com.ebanswers.kitchendiary.network.response;

import com.ebanswers.kitchendiary.bean.FoundTopInfo;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-14
 * desc：
 */
public class FoundTopResponse extends BaseResponse{

    private List<FoundTopInfo> data;

    public List<FoundTopInfo> getData() {
        return data;
    }

    public void setData(List<FoundTopInfo> data) {
        this.data = data;
    }

}
