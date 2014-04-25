package com.vin.app2.app;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.util.HashMap;

/**
 * Created by orphira on 14-4-25.
 */
public class BBSPageListener implements AdapterView.OnItemClickListener {

    private Activity a;
    BBSPageListener(Activity activity){
        a=activity;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        HashMap<String,String> m=(HashMap<String,String>)adapterView.getAdapter().getItem(i);
        //Toast.makeText(a.getApplicationContext(),m.get("href"),Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(a.getApplicationContext(),BBSPage.class);
        intent.putExtra("url",m.get("href"));
        a.startActivity(intent);
    }
}
