package com.ebanswers.kitchendiary.mvp.contract;


import com.ebanswers.kitchendiary.bean.FoundHomeInfo;
import com.ebanswers.kitchendiary.bean.FoundLoadMoreInfo;
import com.ebanswers.kitchendiary.bean.HomePageInfo;
import com.ebanswers.kitchendiary.bean.SquareInfo;
import com.ebanswers.kitchendiary.bean.UserInfo;
import com.ebanswers.kitchendiary.network.api.DrufNumResponse;
import com.ebanswers.kitchendiary.network.bean.ApproverInfo;
import com.ebanswers.kitchendiary.network.bean.LeaveRecordInfo;
import com.ebanswers.kitchendiary.network.bean.LeaveTypeInfo;
import com.ebanswers.kitchendiary.network.bean.LoginInfo;
import com.ebanswers.kitchendiary.network.bean.MessageListInfo;
import com.ebanswers.kitchendiary.network.bean.PersonWorkingDetailInfo;
import com.ebanswers.kitchendiary.network.bean.ProjectWorkingDetailInfo;
import com.ebanswers.kitchendiary.network.bean.TimeInfo;
import com.ebanswers.kitchendiary.network.bean.WorkingDetailInfo;
import com.ebanswers.kitchendiary.network.response.BaseResponse;
import com.ebanswers.kitchendiary.network.response.CookbookResponse;
import com.ebanswers.kitchendiary.network.response.DiaryResponse;
import com.ebanswers.kitchendiary.network.response.FocusResponse;
import com.ebanswers.kitchendiary.network.response.FoundTopResponse;
import com.ebanswers.kitchendiary.network.response.ImageResponse;
import com.ebanswers.kitchendiary.network.response.KefuResponse;
import com.ebanswers.kitchendiary.network.response.LoginResponse;

import java.util.List;

/**
 * by y on 2016/5/27.
 */
public interface BaseView<T> {


    void setData(List<T> datas);

    void netWorkError(String msg);

    interface MainView {

        void switchHomeIndex();

        void switchSign();

        void switchMessage();

        void switchUserAddress();

    }

    interface HomeView {

        void setData(HomePageInfo data);
        void setMoreData(SquareInfo data);
        void setFollowerData(BaseResponse data);
        void netWorkError(String result);
    }


    interface HelperView {
        void setFollowerData(BaseResponse data);
        void setMoreData(SquareInfo data);
        void netWorkError(String result);
    }

    interface MineView {

        void setData(UserInfo data);
        void setDiaryData(DiaryResponse data);
        void setCookBookData(CookbookResponse data);
        void setFollowerData(BaseResponse data);
        void changeName(BaseResponse data);
        void changeHeadUrl(BaseResponse data);
        void netWorkError(String result);


    }


    interface FoundView {

        void setData(FoundHomeInfo data);
        void setMoreData(FoundLoadMoreInfo data);
        void setFollowerData(BaseResponse data);
        void netWorkError(String result);

        void saveOrSendData(BaseResponse baseResponse);
        void topData(FoundTopResponse baseResponse);
    }

    interface FocusView {

        void setData(FocusResponse data);
        void setMoreData(FoundLoadMoreInfo data);
        void setFollowerData(BaseResponse data);
        void topData(FoundTopResponse baseResponse);
        void netWorkError(String result);
    }

    interface LoginView {

        void setData(LoginResponse data);

        void netWorkError(String result);
    }

    interface SendRepiceView {

        void setData(DrufNumResponse data);

        void saveOrSendData(BaseResponse data);

        void savePic(ImageResponse data);

        void netWorkError(String result);
    }


    interface ForgetPwView {

        void setData(LoginInfo data);

        void submitData();

        void netWorkError(String msg);
    }

    interface HomeFragmentView extends BaseView<LeaveRecordInfo>{

    }

    interface MessageFragmentView {
        void setData(String url);

        void netWorkError(String msg);
    }

    interface MyMattersView extends BaseView<LeaveRecordInfo>{
        void setLeaveDetailData(LeaveRecordInfo datas);
    }

    interface SignFragmentView {

        void setData(List<TimeInfo> datas);

        void onSign(String status);

        void netWorkError(String msg);
    }

    interface WorkingTimeView extends BaseView<MessageListInfo>{

    }

    interface LeaveView {

        void setTypeData(List<LeaveTypeInfo> datas);

        void setApproverData(List<ApproverInfo> status);

        void leaveResult(String leaveResult);

        void netWorkError(String msg);
    }

    interface WorkingDetailtView extends BaseView<WorkingDetailInfo>{

    }

    interface PersonWorkingDetailView{

        void setData(PersonWorkingDetailInfo data);

        void netWorkError(String msg);
    }

    interface ProjectWorkingDetailtView {
        void setData(ProjectWorkingDetailInfo data);
        void netWorkError(String msg);
    }

    interface KefuView{
        void setData(KefuResponse data);
        void netWorkError(String msg);
    }

    public interface ModifyPasswordView {
        void setData(BaseResponse data);
        void netWorkError(String msg);
    }

     public interface ForgetPassView {
        void setData(BaseResponse data);
        void netWorkError(String msg);
    }



}
