package com.ebanswers.kitchendiary.adapter;


import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.ebanswers.baselibrary.base.BaseFragmentPagerAdapter;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.mvp.view.found.FoundFragment;
import com.ebanswers.kitchendiary.mvp.view.helper.HelperFragment;
import com.ebanswers.kitchendiary.mvp.view.home.HomeFragment;
import com.ebanswers.kitchendiary.mvp.view.mine.MineFragment;

import java.util.List;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 主页界面 ViewPager + Fragment 适配器
 */
public final class HomeViewPagerAdapter extends BaseFragmentPagerAdapter<CommonLazyFragment> {

    public HomeViewPagerAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager fm, List<CommonLazyFragment> list) {
        list.add(HomeFragment.newInstance());
        list.add(HelperFragment.newInstance());
        list.add(FoundFragment.newInstance());
        list.add(MineFragment.newInstance());
    }
}