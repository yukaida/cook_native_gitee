package com.ebanswers.kitchendiary.mvp.contract;

import com.ebanswers.kitchendiary.mvp.model.OrderListInfo;
import com.ebanswers.kitchendiary.mvp.presenter.BillPresenter;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-10-18
 * desc：
 */
public interface BillContract {

    interface Model {
        /**
         *  作用：请求网络数据，给presenter提供数据更新。
         * @param username
         * @param session
         * @param ktime
         * @param jtime
         * @param presenter
         */

        void loadOrderList(String username, String session, String ktime, String jtime, BillPresenter presenter);
        void loadOrder(String userName, String session, String ktime, String jtime, BillPresenter presenter);
    }

    interface View extends BaseView<OrderListInfo> {
        /**
         * 作用 ： 获取视图控件，根据数据更新界面显示
         * @param data
         */
        void setData(List<OrderListInfo> data);
        void netWorkError(String msg);
    }

    interface Presenter {
        /**
         * 作用： 构造传递view，实例化model，进行逻辑梳理。
         * @param username
         * @param session
         * @param ktime
         * @param jtime
         */
        void loadOrderList(String username,String session, String ktime, String jtime);
        void loadOrder(String userName, String session, String ktime, String jtime);
        void setData(List<OrderListInfo> data);
        void netWorkError(String msg);
    }


}
