package com.vin.app2.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;


public class UserLoged extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_loged);
        getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.BackBar));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().show();
        MyDBAdapter db =  new MyDBAdapter(this.getApplicationContext());
        String userName="";
        String sign = "";
        try {
            db.open();
            userName=db.fetchData(1).getString(2);
            getActionBar().setTitle(userName);
            sign = db.fetchData(1).getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(db.isLogin()){
            JsonMaker jsonMaker = new JsonMaker("user_main",sign,UserLoged.this);
            jsonMaker.setJson(null,null,null,null);
        }else{
            //todo 浏览别人的空间
        }
        db.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_loged, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
