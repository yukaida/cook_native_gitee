package com.ebanswers.kitchendiary.network.api;

/**
 * Created by Administrator on 2018/5/4.
 */

public interface ImageApi {

    //4)	头像上传dynamicKey
//    file
//           modelCode固定值:CA
    //    uploadType  固定值:1

    //上传图片
  /*  @Multipart
    @POST(Api.uploadingPhoto)
    Observable<ResponseBody> addFile(@Part("dynamicKey") String dynamicKey,
                                     @Part("file\"; filename=\"cropimage.jpg\"") RequestBody params,
                                     @Part("modelCode") String modelCode,
                                     @Part("uploadType") String uploadType,
                                     @Part("type") String type);*/
/*    @Multipart
    @POST(Api.uploadingPhoto)
    Observable<ImageBackResponse> addFile(@Part("dynamicKey") RequestBody dynamicKey,
                                          @Part MultipartBody.Part part,
                                          @Part("modelCode") RequestBody modelCode,
                                          @Part("uploadType") RequestBody uploadType,
                                          @Part("type") RequestBody type);

    @Multipart
    @POST(Api.addMultipartFiles)
    Observable<FileBackResponse> addMultipartFiles(@Part("dynamicKey") RequestBody dynamicKey,
                                                   @Part List<MultipartBody.Part> parts,
                                                   @Part("modelCode") RequestBody modelCode,
                                                   @Part("uploadType") RequestBody uploadType,
                                                   @Part("type") RequestBody type);


    @FormUrlEncoded
    @POST(Api.downloads)
    Observable<BaseResponse> downloads(@Part("dynamicKey") String dynamicKey,
                                       @Part("pkIds") String pkIds);

    @FormUrlEncoded
    @POST(Api.download)
    Observable<BaseResponse> download(@Part("dynamicKey") String dynamicKey,
                                      @Part("pkId") String pkId,
                                      @Part("isDownload") String isDownload);*/




}
