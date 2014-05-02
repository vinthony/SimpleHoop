package com.vin.app2.app;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by orphira on 14-5-1.
 */
public class MyScrollListener implements ListView.OnScrollListener {
    private int lastItem=0;
    private int count=32767;
    private Activity mActivity;
    private View footer;
    private JsonMaker.MyAdapter mAdapter;
    public MyScrollListener(Activity activity,View f,JsonMaker.MyAdapter adapter){
        mActivity = activity;
        footer = f;
        mAdapter = adapter;
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
        Log.d("HUPO_POSITION",i+"");
        Log.d("hupu_ar",SCROLL_STATE_IDLE+"");
        if(lastItem == count && i == SCROLL_STATE_IDLE){
            footer.setVisibility(absListView.VISIBLE);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mActivity,"正在加载",Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        lastItem = i + i2;
        Log.d("HUPO_lastitem",lastItem+"");

        count = i3;
        Log.d("HUPO_count",count+"");
    }
}
