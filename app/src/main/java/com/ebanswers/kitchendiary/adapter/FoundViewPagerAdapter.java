package com.ebanswers.kitchendiary.adapter;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ebanswers.baselibrary.base.BaseFragmentPagerAdapter;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.mvp.view.found.FocusFragmentSub;
import com.ebanswers.kitchendiary.mvp.view.found.FoundFragmentSub;

import java.util.List;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 主页界面 ViewPager + Fragment 适配器
 */
public final class FoundViewPagerAdapter extends BaseFragmentPagerAdapter<CommonLazyFragment> {

    public FoundViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager fm, List<CommonLazyFragment> list) {
        list.add(FoundFragmentSub.newInstance());
        list.add(FocusFragmentSub.newInstance());
    }
}