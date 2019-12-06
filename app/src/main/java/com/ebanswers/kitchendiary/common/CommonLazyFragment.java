package com.ebanswers.kitchendiary.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中Fragment懒加载基类
 */
public abstract class CommonLazyFragment extends UILazyFragment {

    private Unbinder mButterKnife;// View注解

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
//        RefWatcher refWatcher = CommonApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        // 友盟统计
//        MobclickAgent.onResume(getContext());
    }

    @Override
    public void onPause() {
        super.onPause();
        // 友盟统计
//        MobclickAgent.onPause(getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
    }
}