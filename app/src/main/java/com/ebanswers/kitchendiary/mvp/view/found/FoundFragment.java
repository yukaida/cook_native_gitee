package com.ebanswers.kitchendiary.mvp.view.found;

import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hjq.bar.TitleBar;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.FoundViewPagerAdapter;
import com.ebanswers.kitchendiary.common.CommonLazyFragment;
import com.ebanswers.kitchendiary.widget.viewpager_animator.ZoomOutPageTransformer;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FoundFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoundFragment extends CommonLazyFragment implements ViewPager.OnPageChangeListener {


    @BindView(R.id.tab_found_tv)
    TextView tabFoundTv;
    @BindView(R.id.tab_focu_tv)
    TextView tabFocuTv;
    @BindView(R.id.title_found)
    TitleBar titleFound;
    @BindView(R.id.focu_found_vp)
    ViewPager focuFoundVp;

    private FoundViewPagerAdapter foundViewPagerAdapter;

    public static FoundFragment newInstance() {
        return new FoundFragment();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_found;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.title_found;
    }

    @Override
    protected void initView() {
        tabFocuTv.setTextSize(16);
        tabFoundTv.setTextSize(20);
        tabFoundTv.setTextColor(getContext().getResources().getColor(R.color.text_black_normal));
        tabFocuTv.setTextColor(getContext().getResources().getColor(R.color.text_gray_normal));
        foundViewPagerAdapter = new FoundViewPagerAdapter(getActivity());
        focuFoundVp.setAdapter(foundViewPagerAdapter);
        focuFoundVp.setOffscreenPageLimit(foundViewPagerAdapter.getCount());
        focuFoundVp.addOnPageChangeListener(this);
        focuFoundVp.setPageTransformer(false, new ZoomOutPageTransformer());



    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean isStatusBarEnabled() {
        // 使用沉浸式状态栏
        return !super.isStatusBarEnabled();
    }

    @Override
    protected boolean statusBarDarkFont() {
        return super.statusBarDarkFont();
    }


    @OnClick({R.id.tab_found_tv, R.id.tab_focu_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_found_tv:
                tabFocuTv.setTextSize(16);
                tabFoundTv.setTextSize(20);
                tabFoundTv.setTextColor(getContext().getResources().getColor(R.color.text_black_normal));
                tabFocuTv.setTextColor(getContext().getResources().getColor(R.color.text_gray_normal));
                focuFoundVp.setCurrentItem(0);
                break;
            case R.id.tab_focu_tv:
                tabFocuTv.setTextSize(20);
                tabFoundTv.setTextSize(16);
                tabFoundTv.setTextColor(getContext().getResources().getColor(R.color.text_gray_normal));
                tabFocuTv.setTextColor(getContext().getResources().getColor(R.color.text_black_normal));
                focuFoundVp.setCurrentItem(1);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            tabFocuTv.setTextSize(16);
            tabFoundTv.setTextSize(20);
            tabFoundTv.setTextColor(getContext().getResources().getColor(R.color.text_black_normal));
            tabFocuTv.setTextColor(getContext().getResources().getColor(R.color.text_gray_normal));
        }else {
            tabFocuTv.setTextSize(20);
            tabFoundTv.setTextSize(16);
            tabFoundTv.setTextColor(getContext().getResources().getColor(R.color.text_gray_normal));
            tabFocuTv.setTextColor(getContext().getResources().getColor(R.color.text_black_normal));
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onStart() {
        super.onStart();
        if (foundViewPagerAdapter != null) {
            FoundFragmentSub item = (FoundFragmentSub) foundViewPagerAdapter.getItem(0);
            if (item != null) {
                item.onStart();
            }
            FocusFragmentSub item1 = (FocusFragmentSub) foundViewPagerAdapter.getItem(1);
            if (item1 != null) {
                item1.onStart();
            }
        }
    }

    public void scroolTopRefresh() {
        FoundFragmentSub item = (FoundFragmentSub) foundViewPagerAdapter.getItem(0);
        FocusFragmentSub item1 = (FocusFragmentSub) foundViewPagerAdapter.getItem(1);
        item.scroolTopRefresh();
        item1.scroolTopRefresh();
    }

    public void addData() {
        focuFoundVp.setCurrentItem(0);
        FoundFragmentSub item = (FoundFragmentSub) foundViewPagerAdapter.getItem(0);
        if (item != null){
            item.addData();
        }

    }

    public void addData3() {//发布日记预展示
        focuFoundVp.setCurrentItem(0);
        FoundFragmentSub item = (FoundFragmentSub) foundViewPagerAdapter.getItem(0);
        if (item != null){
            item.addDiary();
        }

    }

}
