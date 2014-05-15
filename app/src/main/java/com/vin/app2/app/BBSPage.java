package com.vin.app2.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.vin.app2.app.view.XListView;

public class BBSPage extends Activity implements XListView.IXListViewListener {
    private int pageCount=2;
    private XListView xListView;
    private Boolean loaded=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbspage);
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setTitle(getIntent().getStringExtra("title"));
        getActionBar().show();
        xListView = (XListView)findViewById(R.id.bbs_detail);
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(false);
        xListView.setXListViewListener(this);
        String  url=getIntent().getStringExtra("url");
        JsonMaker jsonMaker = new JsonMaker("bbs_detail",url,BBSPage.this);
        jsonMaker.setJson();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bbspage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void onLoad() {
        loaded=true;
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.setRefreshTime("刚刚");
        pageCount++;
    }

    @Override
    public void onRefresh() {}

    @Override
    public void onLoadMore() {
        if(loaded){
            loaded=false;
            String  url=getIntent().getStringExtra("url");
            JsonMaker jsonMaker = new JsonMaker("bbs_detail",url,BBSPage.this);
            jsonMaker.setFlag("loadMore");
            jsonMaker.setPage(pageCount);
            jsonMaker.setJson();
            onLoad();
        }
    }
}
