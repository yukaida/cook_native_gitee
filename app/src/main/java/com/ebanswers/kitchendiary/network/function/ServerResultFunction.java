package com.ebanswers.kitchendiary.network.function;

import com.google.gson.Gson;
import com.ebanswers.kitchendiary.utils.LogUtils;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * 服务器结果处理函数
 *
 * @author ZhongDaFeng
 */
public class ServerResultFunction implements Function<ResponseBody, Object> {
    @Override
    public Object apply(@NonNull ResponseBody response) throws Exception {
        //打印服务器回传结果
        String json = new String(response.bytes());
        LogUtils.e("返回数据：" + json);
        if (json.startsWith("\"")){
            json = json.substring(1, json.length() - 2);
            if (json.contains("\\")){
                json = json.replace("\\", "");
            }
        }else {
            if (json.contains("\\")){
                json = json.replace("\\", "");
            }
        }
       /* if (!response.isSuccess()) {
            throw new ServerException(response.getCode(), response.getMsg());
        }*/
        return new Gson().toJson(json);
    }
}
