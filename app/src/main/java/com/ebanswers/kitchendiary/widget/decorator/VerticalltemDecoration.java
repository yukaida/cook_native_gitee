package com.ebanswers.kitchendiary.widget.decorator;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by huqiang on 2017/2/6.
 */

public class VerticalltemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public VerticalltemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
    }
}