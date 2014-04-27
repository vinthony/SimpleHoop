package com.vin.app2.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class NewsPage extends Activity {

    private String url;
    private JsonMaker jsonMaker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().show();
        Bundle bundle =getIntent().getExtras();
        url = bundle.getString("url");
        jsonMaker=new JsonMaker("apage",url,NewsPage.this);
        jsonMaker.setJson();
        Log.d("HUPO_activity","on create");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("HUPO_activity","on start");
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("HUPO_activity","onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("HUPO_activity","onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.show_news, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(NewsPage.this,LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (id == R.id.comment){

            Intent i = new Intent(NewsPage.this,Comment.class);
            i.putExtra("url",url);
            Log.d("HUPOurl",url);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
