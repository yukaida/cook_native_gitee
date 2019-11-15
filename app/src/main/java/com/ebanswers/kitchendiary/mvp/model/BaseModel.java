package com.ebanswers.kitchendiary.mvp.model;

import android.app.Activity;

/**
 * 在实现此类的类中加载数据
 * by y on 2016/5/27.
 */
public interface BaseModel<T> {


    void netWork(T model);

   /* interface TabNewsModel extends BaseModel<BaseDataBridge.TabNewsData> {
    }*/
    //登录网络数据请求方法
    interface LoginInfoModel {
        void login(Activity activity, String username, String password, BaseDataBridge.LoginInfoData loginInfoData);
    }

    interface ForgetPwInfoModel{
        void requestNetwork(Activity activity, String mobileNum, BaseDataBridge.ForgetPwInfoData forgetPwInfoData);
        void submit(Activity activity, String mobileNum, String identifyCode, String newPw, BaseDataBridge.ForgetPwInfoData forgetPwInfoData);

    }

    interface SignInfoModel{
        void requestNetwork(Activity activity, String username, BaseDataBridge.SignInfoData signInfoData);
        void sign(Activity activity, String username, String lng, String lat, BaseDataBridge.SignInfoData signInfoData);

    }

    interface HomeIndexModel{
        void requestNetwork(Activity activity, String username, int page, BaseDataBridge.HomeFragmentData homeFragmentData);
    }

    interface MessageListModel{
        void requestNetwork(Activity activity, String pageNo, String pageSize, BaseDataBridge.MessageFragmentData messageFragmentData);
    }

    interface WorkingTimeModel{
        void requestNetwork(Activity activity, String pageNo, String pageSize, BaseDataBridge.WorkingTimeData workingTimeData);
    }

    interface MyMattersModel{
        void requestNetwork(Activity activity, String username, int page, BaseDataBridge.MyMattersData myMattersData);
        void getMyMattersDetail(Activity activity, String leaveId, BaseDataBridge.MyMattersData myMattersData);
    }

    interface LeaveModel{

        void requestLeaveTypeNetwork(Activity activity, BaseDataBridge.LeaveInfoData leaveInfoData);
        void requestApproverNetwork(Activity activity, String leaveTypeId, String username, BaseDataBridge.LeaveInfoData leaveInfoData);
        void submitNetwork(Activity activity, String username, String leaveTime, String halfDay,
                           String leaveDayNum, String leaveType, String leaveCont, String nextPerson, BaseDataBridge.LeaveInfoData leaveInfoData);

    }

    interface WorkingDetailModel{
        void requestNetwork(Activity activity, String userCode, String date, BaseDataBridge.WorkingDetailData workingdetailData);
    }

    interface PersonWorkingDetailModel{
        void requestNetwork(Activity activity, String userCode, String pageNo, String pageSize, BaseDataBridge.PersonWorkingDetailData personWorkingDetailData);
    }

    interface ProjectWorkingDetailModel{
        void requestNetwork(Activity activity, String projectName, String pageNo, String pageSize, BaseDataBridge.ProjectWorkingDetailData projectWorkingDetailData);
    }

}
