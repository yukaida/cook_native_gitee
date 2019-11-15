package com.ebanswers.baselibrary.listener;


import androidx.recyclerview.widget.RecyclerView;

/**
 *    author : HJQ
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : RecyclerView滚动监听类
 */
public interface OnScrollingListener {

    /**
     * 列表滚动到最顶部
     */
    void onScrollTop(RecyclerView recyclerView);

    /**
     * 列表滚动到最底部
     */
    void onScrollDown(RecyclerView recyclerView);

    /**
     * 列表滚动中
     */
    void onScrolling(RecyclerView recyclerView);
}

