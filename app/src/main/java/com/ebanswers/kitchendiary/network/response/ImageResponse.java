package com.ebanswers.kitchendiary.network.response;

import com.ebanswers.kitchendiary.bean.ImageData;

/**
 * Create by dongli
 * Create date 2019-11-12
 * desc：
 */
public class ImageResponse extends BaseResponse{

    private ImageData data;

    public ImageData getData() {
        return data;
    }

    public void setData(ImageData data) {
        this.data = data;
    }
}
