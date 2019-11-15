package com.ebanswers.kitchendiary.mvp.presenter;

import com.ebanswers.kitchendiary.mvp.contract.BillContract;
import com.ebanswers.kitchendiary.mvp.model.BillModel;
import com.ebanswers.kitchendiary.mvp.model.OrderListInfo;
import com.ebanswers.kitchendiary.mvp.view.home.BillActivity;

import java.util.List;

/**
 * Create by dongli
 * Create date 2019-10-18
 * desc：
 */

public class BillPresenter implements BillContract.Presenter {

    private BillContract.View view;
    private final BillModel billModel;
    public BillActivity activity;

    public BillPresenter(BillContract.View view, BillActivity activity) {
        this.view = view;
        this.activity = activity;
        billModel = new BillModel();

    }


    @Override
    public void loadOrderList(String username, String session, String ktime, String jtime) {

    }

    @Override
    public void loadOrder(String userName, String session, String ktime, String jtime) {

    }

    @Override
    public void setData(List<OrderListInfo> data) {

    }

    @Override
    public void netWorkError(String msg) {

    }


}
