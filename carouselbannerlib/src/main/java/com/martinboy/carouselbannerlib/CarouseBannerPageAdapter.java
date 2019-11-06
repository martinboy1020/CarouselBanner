package com.martinboy.carouselbannerlib;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Toast;

import java.util.List;

public class CarouseBannerPageAdapter extends PagerAdapter {

    Context ctx;
    List<View> viewList;
    int currentPos;
    Fragment fragment;

    public CarouseBannerPageAdapter(Context ctx, List<View> viewList) {
        this.ctx = ctx;
        this.viewList = viewList;
    }


    @Override
    public int getCount() {
        if(viewList != null) {

            if(viewList.size() > 1) {
                return Integer.MAX_VALUE;
            } else {
                return viewList.size();
            }

        } else {
            return 0;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object == view;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        position %= viewList.size();

        if (position < 0) {
            position = viewList.size() + position;
        }

        View view = viewList.get(position);

        currentPos = position;

        ViewParent vp = view.getParent();
        if (vp != null) {
            ViewGroup parent = (ViewGroup) vp;
            parent.removeView(view);
        }
        container.addView(view);

        final int finalPosition = position;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ctx, "position: " + finalPosition, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public int getCurrentPostion() {
        return currentPos;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }

}
