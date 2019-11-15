package com.ebanswers.kitchendiary.network.response;

import com.ebanswers.kitchendiary.bean.CookbookInfo;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-11-05
 * desc：
 */
public class CookbookResponse extends BaseResponse{


    private List<CookbookInfo> data;

    public List<CookbookInfo> getData() {
        return data;
    }

    public void setData(List<CookbookInfo> data) {
        this.data = data;
    }

}
