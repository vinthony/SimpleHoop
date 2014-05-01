package com.vin.app2.app;

import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by orphira on 14-5-1.
 */
public class GuidePageChangeListener implements ViewPager.OnPageChangeListener {
    private AtomicInteger what ;
    private ImageView[] imageViews;
    private TextView textView;
    private ArrayList<String> tv_data;
    public GuidePageChangeListener(AtomicInteger w , ImageView[] iv,TextView tv,ArrayList<String> data){
        what=w;
        imageViews=iv;
        textView= tv;
        tv_data = data;
    }
    @Override
    public void onPageScrollStateChanged(int arg0) {

            }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

    @Override
    public void onPageSelected(int arg0) {
            what.getAndSet(arg0);
            for (int i = 0; i < imageViews.length; i++) {
            imageViews[arg0].setBackgroundResource(R.drawable.indicator_focused);
            textView.setText(tv_data.get(arg0));
            if (arg0 != i) {
            imageViews[i].setBackgroundResource(R.drawable.indicator);
                }
            }

    }
}
