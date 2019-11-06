package com.martinboy.carouselbannerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CarouseBanner extends RelativeLayout implements ViewPager.OnPageChangeListener, View.OnTouchListener {

    private Context context;
    private ViewPager bannerViewPager;
    private LinearLayout bannerViewPagerIndicate;
    private CarouseBannerPageAdapter bannerPageAdapter;
    private BannerHandler bannerHandler;
    private TypedArray res;
    private ArrayList<View> viewList;
    private int height = 166;
    private int delayMilliseconds = 5000;
    private boolean isBreak = false;
    private boolean initPosition = false;

    public CarouseBanner(Context context) {
        super(context);
        this.context = context;
        initView(context);
    }

    public CarouseBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView(context);
    }

    public CarouseBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView(context);
    }

    public CarouseBanner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_banner_view, this, true);
        bannerViewPager = (ViewPager) view.findViewById(R.id.bannerViewPager);
        bannerViewPagerIndicate = (LinearLayout) view.findViewById(R.id.bannerViewPagerIndicate);
    }

    public void setResourceImageList(int[] imageArray, int height) {

        this.height = height;

        setBannerHeight(height);

        final LayoutInflater mInflater = LayoutInflater.from(context);

        if (res == null) {
            res = context.getResources().obtainTypedArray(R.array.banner_imgs_array);
        }

        if (imageArray == null || imageArray.length <= 0)
            return;

        viewList = new ArrayList<>();

        for (int i = 0; i < imageArray.length; i++) {
            View view = mInflater.inflate(R.layout.viewpager_banner, null);
            ImageView img = view.findViewById(R.id.img_banner);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            float px = height * metrics.density;
            layoutParams.height = (int) px;
            img.setLayoutParams(layoutParams);
            Glide.with(context).load(imageArray[i]).into(img);
            viewList.add(view);
        }

        if (bannerPageAdapter == null) {
            bannerPageAdapter = new CarouseBannerPageAdapter(context, viewList);
        }

        if (bannerViewPager != null) {
            bannerViewPager.setAdapter(bannerPageAdapter);
//            bannerViewPager.setOffscreenPageLimit(3);
            bannerViewPager.addOnPageChangeListener(this);
            bannerViewPager.setOnTouchListener(this);
            bannerViewPager.setCurrentItem((Integer.MAX_VALUE / 2));
        }

    }

    public void setNetImageList(String[] imageArray, int height) {

        this.height = height;

        setBannerHeight(height);

        final LayoutInflater mInflater = LayoutInflater.from(context);

        if (res == null) {
            res = context.getResources().obtainTypedArray(R.array.banner_imgs_array);
        }

        if (imageArray == null || imageArray.length <= 0)
            return;

        viewList = new ArrayList<>();

        for (int i = 0; i < imageArray.length; i++) {
            View view = mInflater.inflate(R.layout.viewpager_banner, null);
            ImageView img = view.findViewById(R.id.img_banner);
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) img.getLayoutParams();
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            float px = height * metrics.density;
            layoutParams.height = (int) px;
            img.setLayoutParams(layoutParams);
            Glide.with(context).load(imageArray[i]).into(img);
            viewList.add(view);
            Log.d("tag1", "viewList.add");
        }

        if (bannerPageAdapter == null) {
            bannerPageAdapter = new CarouseBannerPageAdapter(context, viewList);
        }

        if (bannerViewPager != null) {
            bannerViewPager.setAdapter(bannerPageAdapter);
            bannerViewPager.addOnPageChangeListener(this);
            bannerViewPager.setOnTouchListener(this);
            if(viewList.size() > 1) {
                bannerViewPager.setCurrentItem((Integer.MAX_VALUE / 2));
            } else {
                bannerViewPager.setCurrentItem(0);
            }

        }

    }

    public void buildIndicator(int dotLeftMargin, int dotRightMargin, int dotBottomMargin) {
        bannerViewPagerIndicate.removeAllViews();

        int leftMargin = 20;
        int rightMargin = 20;
        int bottomMargin = 35;

        if(dotBottomMargin > 0)
            leftMargin = dotLeftMargin;

        if(dotRightMargin > 0)
            rightMargin = dotRightMargin;

        if(dotBottomMargin > 0)
            bottomMargin = dotBottomMargin;

        if(viewList == null || viewList.size() <= 0)
            return;

        for (int i = 0; i < viewList.size(); i++) {
            ImageView v = new ImageView(context);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = leftMargin;
            params.rightMargin = rightMargin;
            params.bottomMargin = bottomMargin;
            v.setLayoutParams(params);
            if (i == 0) {
                v.setBackgroundResource(R.drawable.banner_circle_selected);
            } else {
                v.setBackgroundResource(R.drawable.banner_circle_unselected);
            }
            bannerViewPagerIndicate.addView(v);
        }
    }

    private void setBannerHeight(int height) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = height * metrics.density;

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) bannerViewPager.getLayoutParams();
        layoutParams.height = (int) px;
        bannerViewPager.setLayoutParams(layoutParams);

    }

    public void startPlay(int milliseconds) {

        if (viewList == null || viewList.size() <= 1) {
            return;
        }

        if (bannerHandler == null) {
            bannerHandler = new BannerHandler(new WeakReference<>(this));
        }

        if (milliseconds > 0) {
            delayMilliseconds = milliseconds;
        } else if (milliseconds < 0) {
            return;
        }

        bannerHandler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_INTRO, delayMilliseconds);
    }

    public void stopPlay() {
        bannerHandler.sendEmptyMessage(BannerHandler.MSG_KEEP_SILENT);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        Log.d("tag1", "onPageSelected position: " + position);

        int listPosition = position % viewList.size();

        if (listPosition < 0) {
            listPosition = viewList.size() + listPosition;
        }

        Log.d("tag1", "onPageSelected listPosition: " + listPosition);

        for (int i = 0; i < bannerViewPagerIndicate.getChildCount(); i++) {
            if (i == listPosition) {
                ImageView child = (ImageView) bannerViewPagerIndicate.getChildAt(i);
                child.setBackgroundResource(R.drawable.banner_circle_selected);
            } else {
                ImageView child = (ImageView) bannerViewPagerIndicate.getChildAt(i);
                child.setBackgroundResource(R.drawable.banner_circle_unselected);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if(bannerHandler != null) {
            switch (motionEvent.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (bannerHandler.hasMessages(BannerHandler.MSG_UPDATE_INTRO)) {
                        bannerHandler.removeMessages(BannerHandler.MSG_UPDATE_INTRO);
                    }
                    bannerHandler.sendEmptyMessage(BannerHandler.MSG_KEEP_SILENT);
                    break;
                case MotionEvent.ACTION_UP:
                    view.getParent().requestDisallowInterceptTouchEvent(false);
                    bannerHandler.sendEmptyMessageDelayed(BannerHandler.MSG_UPDATE_INTRO, delayMilliseconds);
                    break;
                case MotionEvent.ACTION_MOVE:
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (bannerHandler.hasMessages(BannerHandler.MSG_UPDATE_INTRO)) {
                        bannerHandler.removeMessages(BannerHandler.MSG_UPDATE_INTRO);
                    }
                    bannerHandler.sendEmptyMessage(BannerHandler.MSG_KEEP_SILENT);
                    break;
            }
        }

        return false;
    }

    public static class BannerHandler extends Handler {

        static final int MSG_UPDATE_INTRO = 1;
        static final int MSG_KEEP_SILENT = 2;
        static final int MSG_BREAK_SILENT = 3;
        static final int MSG_PAGE_CHANGED = 4;
        private WeakReference<CarouseBanner> contextWeakReference;
        private int currentItem = 0;

        BannerHandler(WeakReference<CarouseBanner> cwk) {
            contextWeakReference = cwk;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            CarouseBanner banner = contextWeakReference.get();

            if (banner == null) {
                return;
            }

            switch (msg.what) {
                case MSG_UPDATE_INTRO:
                    currentItem = banner.bannerViewPager.getCurrentItem();
                    currentItem++;
                    banner.bannerViewPager.setCurrentItem(currentItem);
                    sendEmptyMessageDelayed(MSG_UPDATE_INTRO, banner.delayMilliseconds);
                    break;
                case MSG_KEEP_SILENT:
                    break;
                case MSG_BREAK_SILENT:
                    sendEmptyMessageDelayed(MSG_UPDATE_INTRO, banner.delayMilliseconds);
                    break;

                default:
                    break;
            }
        }

        public void getBannerViewPagerCurrentItem() {

        }

    }

}
