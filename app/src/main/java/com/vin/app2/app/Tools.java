package com.vin.app2.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by orphira on 14-4-17.
 */
public class Tools {

    public static String getUrlWithParam(String url,HashMap<String,String> params){
        for (Iterator<String> i = params.keySet().iterator();i.hasNext();){
            String key = i.next();
            String value = params.get(key);
            url=url+key+"="+value+"&";
        }
        return url;
    }

    public static void imageInit(Context c){
        FileCache fc = new FileCache(c);
        File cacheDir =fc.getCacheDir();
        //ImageLoader.getInstance().destroy();
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config =new ImageLoaderConfiguration
                .Builder(c)
                .defaultDisplayImageOptions(defaultOptions)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .build();
        ImageLoader.getInstance().init(config);
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
