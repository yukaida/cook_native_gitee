package com.ebanswers.kitchendiary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by air
 *
 * @link https://github.com/LiShiHui24740
 * On 2018/4/4 下午2:00.
 * @description:
 */

public class ScrollSwipeRefreshLayout extends SwipeRefreshLayout {
    private ViewGroup viewGroup;
    private float moveX,moveY;
    public ScrollSwipeRefreshLayout(Context context) {
        super(context);
    }

    public ScrollSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (viewGroup != null) {
            if (viewGroup instanceof WebView) {
                WebView webView = (WebView) viewGroup;
                int scrollY = webView.getWebScrollY();
                Log.d("SwipeRefreshLayout", "onInterceptTouchEvent: "+scrollY);
                if (scrollY > 0) {
                    return false;
                } else if (scrollY == 0) {
                    switch (ev.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            moveX = ev.getRawX();
                            moveY = ev.getRawY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            if (Math.abs(ev.getRawX()-moveX)>Math.abs(ev.getRawY()-moveY)/2){
                                moveX = ev.getRawX();
                                moveY = ev.getRawY();
                                Log.d("SwipeRefreshLayout", "onInterceptTouchEvent: false");
                                return false;
                            }
                            break;
                    }
                }
            } else {
                int scrollY = viewGroup.getScrollY();
                Log.d("SwipeRefreshLayout222", "onInterceptTouchEvent: "+scrollY);
                if (scrollY > 0) {
                    return false;
                }
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

}
