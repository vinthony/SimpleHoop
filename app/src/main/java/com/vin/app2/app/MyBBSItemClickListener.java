package com.vin.app2.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by orphira on 14-5-2.
 */
public class MyBBSItemClickListener implements android.widget.AdapterView.OnItemClickListener {
    private Activity mA;
    private HashMap<String,String> data;
    private ArrayList<HashMap<String,String>> maps=new ArrayList<HashMap<String, String>>();
    public MyBBSItemClickListener(Activity a,ArrayList<HashMap<String,String>> mp,HashMap<String,String> d){
        mA=a;
        maps=mp;
        data=d;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, final View view, int i, final long position) {
        if (position >= 0) {
            new AlertDialog.Builder(mA).setItems(new String[]{"引用", "亮了", "只看此人"}, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    HashMap<String, String> hm = maps.get((int) position);
                    if (i == 0) {//引用
                        Toast.makeText(mA, "引用" + hm.get("userName"), Toast.LENGTH_SHORT).show();
                    }
                    if (i == 1) {//亮了
                        //Toast.makeText(mA,"liangle"+position,Toast.LENGTH_LONG).show();
                        JsonMaker jsonMaker = new JsonMaker("light", data.get("tid"), hm.get("pid"), hm.get("authorid"), data.get("fid"), view, mA);
                        jsonMaker.setJson();
                    }
                    if (i == 2) {
                        Intent intent = new Intent(mA, BBSPage.class);
                        intent.putExtra("title", mA.getTitle());
                        intent.putExtra("url", hm.get("sub_page"));
                        mA.startActivity(intent);
                    }
                }

            }).show();
        }
    }
}
