package com.vin.app2.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by orphira on 14-5-1.
 */
public  class ViewPagerAdapter extends PagerAdapter {
    private List<View> views = null;
    private Activity mActivity = null;
    private ArrayList<String> mData = new ArrayList<String>();
    public ViewPagerAdapter(List<View> views,Activity activity,ArrayList<String> data) {
        mActivity = activity;
        this.views = views;
        mData = data;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView(views.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {

    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public Object instantiateItem(View arg0, final int arg1) {
        //((ViewPager) arg0).addView(views.get(arg1), 0);
        //return views.get(arg1);
        View item = views.get(arg1);
        item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity,NewsPage.class);
                intent.putExtra("url",mData.get(arg1));
                mActivity.startActivity(intent);
            }
        });
       ((ViewPager) arg0).addView(item);
       return views.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {

    }

}


