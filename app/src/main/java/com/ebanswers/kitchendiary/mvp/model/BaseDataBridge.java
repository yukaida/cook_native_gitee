package com.ebanswers.kitchendiary.mvp.model;


import com.ebanswers.kitchendiary.network.bean.ApproverInfo;
import com.ebanswers.kitchendiary.network.bean.LeaveRecordInfo;
import com.ebanswers.kitchendiary.network.bean.LeaveTypeInfo;
import com.ebanswers.kitchendiary.network.bean.LoginInfo;
import com.ebanswers.kitchendiary.network.bean.MessageListInfo;
import com.ebanswers.kitchendiary.network.bean.PersonWorkingDetailInfo;
import com.ebanswers.kitchendiary.network.bean.ProjectWorkingDetailInfo;
import com.ebanswers.kitchendiary.network.bean.TimeInfo;
import com.ebanswers.kitchendiary.network.bean.WorkingDetailInfo;

import java.util.List;

/**
 * 数据绑定     单独的是单条数据   集成BaseDataBridge是集合数据
 * by y on 2016/5/27.
 */
public interface BaseDataBridge<T> {

    void addData(List<T> datas);

    void error(String msg);

    //多条数据
  /*  interface LoginInfoData extends BaseDataBridge<UserInfo> {
        void addData(UserInfo datas);
        void error();
    }*/

    //单条数据
    interface LoginInfoData {
        void addData(LoginInfo datas);
        void error(String result);
    }

    //单条数据
    interface ForgetPwInfoData extends BaseDataBridge<TimeInfo>{
        /*  void addData(SignInfo datas);
          void error();*/
        void signComplite(String status);
    }

    //单条数据
    interface SignInfoData extends BaseDataBridge<TimeInfo>{
      /*  void addData(SignInfo datas);
        void error();*/
      void signComplite(String status);
    }

    interface HomeFragmentData extends BaseDataBridge<LeaveRecordInfo>{

    }

    interface MessageFragmentData extends BaseDataBridge<MessageListInfo>{

    }

    interface WorkingTimeData extends BaseDataBridge<MessageListInfo>{

    }

    interface WorkingDetailData extends BaseDataBridge<WorkingDetailInfo>{

    }

    interface ProjectWorkingDetailData {
        void setData(ProjectWorkingDetailInfo datas);
        void error(String result);
    }


    interface PersonWorkingDetailData{
        void setData(PersonWorkingDetailInfo datas);
        void error(String result);
    }

    interface MyMattersData extends BaseDataBridge<LeaveRecordInfo>{
        void addTypeData(LeaveRecordInfo datas);
    }

    //单条数据
    interface LeaveInfoData {
        void addTypeData(List<LeaveTypeInfo> datas);
        void addApproverData(List<ApproverInfo> datas);
        void leaveResult(String leaveResult);
        void error(String result);
    }



}
