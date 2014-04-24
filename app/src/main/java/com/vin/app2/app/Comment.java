package com.vin.app2.app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Comment extends Activity {
    private int flag = 1;
    private String url;
    private JsonMaker jsonMaker;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().show();
        url=getIntent().getStringExtra("url");
        jsonMaker=new JsonMaker("comment",url,Comment.this);
        jsonMaker.setJson(null,null,null,null);
    }

    public Intent getParentActivityIntent() {
        Intent parentIntent= getIntent();
        String u = parentIntent.getStringExtra("url"); //getting the parent class name
        Intent newIntent=null;
        try {
            //you need to define the class with package name
            newIntent = new Intent(Comment.this,NewsPage.class);
            Bundle b = new Bundle();
            b.putString("url",u);
            newIntent.putExtras(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newIntent;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.comment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_hl) {
            if(flag==1){
                jsonMaker=new JsonMaker("comment",url,Comment.this);
                jsonMaker.setFlag("hl");
                jsonMaker.setJson(null,null,null,null);
                flag=0;
                item.setTitle("全部回帖");
            }else{
                jsonMaker=new JsonMaker("comment",url,Comment.this);
                jsonMaker.setFlag("ge");
                jsonMaker.setJson(null,null,null,null);
                item.setTitle("这些亮了");
                flag=1;
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
