package com.vin.app2.app;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 *  the url to connect the server
 *  get what kind of data
 * Created by orphira on 14-4-17.
 */
public class JsonMaker {
    private String item;
    private String type;
    private String bbstype;
    private String uname;
    private String pwd;
    private String url;
    private String sign="";
    private String flag="";
    private String content="";
    private Context mContext;
    private Activity mActivity;
    private int is_translate=0;
    private Boolean isLog=false;
    private int floor =0;
    private int toNote=0;
    private String subItem="";
    private int reply_id=0;
    private int reply_floor=0;
    private String [] bbsArray =new String[]{};
    private String webServerUrl="http://hupoapi.sinaapp.com/sandBox.php?";
    private HashMap<String, String> params = new HashMap<String, String>();
    private ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
    private final String HUPO="HUPO";
    private static int STATUS_OK = 200;
    private String final_Url;
    public JsonMaker(String oitem,Activity a){
        item = oitem;
        mActivity=a;
        MyDBAdapter myDBAdapter = new MyDBAdapter(a.getApplicationContext());
        try {
            myDBAdapter.open();
            if(myDBAdapter.isLogin()){
                sign=myDBAdapter.fetchData(1).getString(1);
            }else{
                //todo
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        myDBAdapter.close();
        params.put("item",item);
        params.put("sign",sign);
        Tools.imageInit(a.getApplicationContext());
    }
    //comment
    public JsonMaker(String oitem, String x, String u, String ctent, int is_tran, int f, int tn, Activity a) {
        item=oitem;
        mActivity=a;
        sign=x;
        url=u;
        content=ctent;
        is_translate=is_tran;
        floor = f;
        toNote = tn;
        params.put("item",item);
        params.put("sign",sign);
        params.put("id",url);
        params.put("content",content);
        params.put("is_translate",""+is_translate);
        params.put("floor",""+floor);
        params.put("toNote",""+toNote);
    }

    public JsonMaker(String oitem, int ty, int i, Activity a) {//bbs item
        item=oitem;
        type=""+ty;
        subItem=i+"";
        mActivity=a;
        params.put("item",item);
        params.put("bbs_type",type);
        params.put("bbs_subItem",subItem);

    }

    public enum ITEMS{
        VOICE,BBS,PAGE,APAGE,LOGIN,LOGED,MYPAGE,UNDEFINED,COMMENT,SUPPORT,ADDCOMMENT,USER_MAIN,
        USER_TOPIC,USER_POST,USER_TOPIC_MAIN,USER_TOPIC_RE,USER_TOPIC_FAV,MY_BBS_ITEMS,BBS_ITEM,
        BBS_DETAIL
    }
    public JsonMaker(String oitem,String si,String ux,Activity a){
       switch (ITEMS.valueOf(oitem.toUpperCase())){
           case SUPPORT://support
               item=oitem;
               mActivity=a;
               url=ux;
               sign=si;
               params.put("item",item);
               params.put("sign",sign);
               params.put("id",ux);
               break;
           case LOGIN://login
               item=oitem;
               uname=si;
               pwd=ux;
               mActivity=a;
               params.put("item",item);
               params.put("uname",uname);
               params.put("pwd",pwd);
               break;
           default:
               break;
       }

    }
    public JsonMaker(String oitem,String ux,Activity a){//getPages
        mActivity=a;
        if(oitem.equals("apage")||oitem.equals("comment")||oitem.equals("bbs_detail")){
            item=oitem;
            url=ux;
            params.put("item",item);
            params.put("url",url);
        }else{
            item=oitem;
            sign=ux;
            params.put("item",item);
            params.put("sign",ux);

        }
        Tools.imageInit(a.getApplicationContext());

    }
    public JsonMaker(String oitem, String s,Context c) {//getNewsVoice
        mContext=c;
        item = oitem;
        type = s;
        params.put("item", item);
        params.put("type", type);
        Tools.imageInit(c);
    }
    public void setFlag(String f){
        flag=f;
    }
    public void setJson(final View rV,final Fragment f, final Context c, final ListView lv) {
        if(!item.equals("loged")) {
            final_Url = Tools.getUrlWithParam(webServerUrl, params);
        }else {
            final_Url = url;
        }
        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String finalUrl=final_Url;
                            Log.d(HUPO,finalUrl);
                            URL u = new URL(finalUrl);
                            HttpURLConnection urlConnection = (HttpURLConnection) u.openConnection();
                            if (urlConnection.getResponseCode() == STATUS_OK) {
                                InputStreamReader in = new InputStreamReader(urlConnection.getInputStream());
                                BufferedReader buffer = new BufferedReader(in);
                                String line = "";
                                String result = "";
                                while ((line = buffer.readLine()) != null) {
                                    result += line;
                                }
                                in.close();
                                Log.d(HUPO,result);
                                urlConnection.disconnect();
                                switch (ITEMS.valueOf(item.toUpperCase())){
                                    case VOICE:
                                        getNewsList(result, rV, lv, f, c);
                                        break;
                                    case APAGE:
                                        getNewsPage(result, mActivity);
                                        break;
                                    case LOGIN:
                                        getLoginInformation(result,mActivity);
                                        break;
                                    case COMMENT:
                                        getNewsComment(result,mActivity);
                                        break;
                                    case SUPPORT:
                                        dealSupport(result,mActivity);
                                        break;
                                    case ADDCOMMENT:
                                        dealComment(result,mActivity);
                                        break;
                                    case USER_MAIN:
                                        getUserInfo(result,mActivity);
                                        break;
                                    case USER_TOPIC_MAIN:
                                        getUserTopicMain(result,mActivity);
                                        break;
                                    case USER_TOPIC_RE:
                                        getUserTopicRe(result,mActivity);
                                        break;
                                    case USER_TOPIC_FAV:
                                        getUserTopicMain(result,mActivity);
                                        break;
                                    case MY_BBS_ITEMS:
                                        getMyBBSArray(result);
                                        break;
                                    case BBS_ITEM:
                                        getSubBBSItem(result,mActivity);
                                        break;
                                    case BBS_DETAIL:
                                        getBBSDetail(result,mActivity);
                                        break;

                                }
                            }else{
                            mActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(mActivity.getApplicationContext(),"请检查网络设置",Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }

        ).start();
    }
    private void getBBSDetail(String result,final Activity a){
        try {
            JSONObject j = new JSONObject(result);
            ArrayList<HashMap<String,String>> m = Model.BBSDetail(j);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getSubBBSItem(String result,final Activity a){
        try {
            JSONArray array = new JSONArray(result);
            ArrayList<HashMap<String,String>> items=new ArrayList<HashMap<String, String>>();
            if (!subItem.equals("0")){
                items= Model.bbsGeneralModel(array);
            }else{
                items = Model.bbsListModel(array);
            }
            final ArrayList<HashMap<String,String>> items_final=items;
            final ListView lv = (ListView)a.findViewById(R.id.bbs_list);
            final RefreshableView refreshableView=(RefreshableView)a.findViewById(R.id.refreshable_view);
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(subItem.equals("0")){
                        lv.setAdapter(new MyAdapter(a.getApplicationContext(),items_final,R.layout.list_bbs_items,new String[]{"title","info","parent"},new int[]{R.id.title,R.id.info,R.id.parent}));
                        lv.setOnItemClickListener(new BBSPageListener(mActivity));
                    }else{
                        lv.setAdapter(new MyAdapter(a.getApplicationContext(),items_final,R.layout.general_bbs_items,new String[]{"title","author","info","lastReTime"},new int[]{R.id.title,R.id.author,R.id.info,R.id.lastReTime}));
                        lv.setOnItemClickListener(new BBSPageListener(mActivity));
                    }
                    refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
                        @Override
                        public void onRefresh() {
                            try {
                                JsonMaker jsonMaker = new JsonMaker("bbs_item",type,subItem,mActivity);
                                jsonMaker.setJson(null,null,null,null);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            refreshableView.finishRefreshing();
                        }
                    },0);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getMyBBSArray(String result){
        try {
            ArrayList<String> a = new ArrayList<String>();
            JSONArray jsonArray=new JSONArray(result);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject o = (JSONObject)jsonArray.opt(i);
                a.add(o.getString("title"));
            }
            String [] str=new String[a.size()];

            for (int i =0;i<a.size();i++){
                 str[i]=a.get(i);
            }
            final String []finalStr = str;
            //final String [] str=(String[])a.toArray();
            //Log.d("hupo",bbsArray.toString());
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mActivity.getActionBar().setListNavigationCallbacks(
                            // Specify a SpinnerAdapter to populate the dropdown list.
                            new ArrayAdapter<String>(
                                    mActivity.getActionBar().getThemedContext(),
                                    android.R.layout.simple_list_item_1,
                                    android.R.id.text1,
                                    finalStr),
                            new ActionBar.OnNavigationListener() {
                                @Override
                                public boolean onNavigationItemSelected(int i, long l) {
                                   // return false;
                                    mActivity.getFragmentManager().beginTransaction()
                                            .replace(R.id.container, BBS.PlaceholderFragment.newInstance(i + 1))
                                            .commit();
                                    return true;
                                }
                            });

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getUserTopicMain(String result,final Activity a){
        try{
            final JSONArray j = new JSONArray(result);
            Fragment f= a.getFragmentManager().findFragmentById(R.layout.fragment_user_topic);
            final ArrayList<HashMap<String,String>> m = Model.userTopicModel(j);
            final ListView lv_main =(ListView)f.getView().findViewById(R.id.user_topic);
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lv_main.setAdapter(new MyAdapter(a.getApplicationContext(), m, R.layout.list_topic_item, new String[]{"title", "bbs", "time", "context"}, new int[]{R.id.title, R.id.bbs, R.id.time, R.id.context}));
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getUserTopicRe(String result,final Activity a){
        try{
            final JSONArray j = new JSONArray(result);
            Fragment f= a.getFragmentManager().findFragmentById(R.layout.fragment_user_topic);
            final ArrayList<HashMap<String,String>> m = Model.userTopicReModel(j);
           final ListView lv_main =(ListView)f.getView().findViewById(R.id.user_topic);
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    lv_main.setAdapter(new MyAdapter(a.getApplicationContext(), m, R.layout.list_re_topic_item, new String[]{"title", "bbs", "time", "context"}, new int[]{R.id.title, R.id.bbs, R.id.time, R.id.context}));
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void dealComment(String result,final Activity a){
        try {
            JSONObject j = new JSONObject(result);
            Log.d(HUPO+"res",result);
            final String status = j.getString("status");
            final String msg = j.getString("msg");
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if(status.equals("200")){
                        Intent i = new Intent(a.getApplicationContext(),Comment.class);
                        i.putExtra("url",url);
                        a.startActivity(i);
                        Toast.makeText(a.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_SHORT).show();
                    }else{
                        //todo deal something
                        Toast.makeText(a.getApplicationContext(),Html.fromHtml(msg),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void dealSupport(String result,final Activity a){
        try {
            JSONObject j = new JSONObject(result);
            final String status = j.getString("status");
            final String num = j.getString("num");
            final String msg = j.getString("msg");
            final TextView tv_admireNum = (TextView)a.findViewById(R.id.supportNum);
                a.runOnUiThread(new Runnable() {
                    @Override
                        public void run() {
                        if(status.equals("200")){
                            tv_admireNum.setText(num);
                            Toast.makeText(a.getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            if(msg=="没登陆"){
                                Intent i = new Intent(a,LoginActivity.class);
                                Bundle b = new Bundle();
                                startActivity(a,i,b);
                            }
                        }
                    }
                });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void getNewsComment(String result,final Activity a){
        try {
            JSONObject j = new JSONObject(result);
            ArrayList<HashMap<String,String>> hl_comments=new ArrayList<HashMap<String, String>>();
            ArrayList<HashMap<String,String>> ge_comments=new ArrayList<HashMap<String, String>>();
            JSONArray hl_comment_arr = j.getJSONArray("commentHL");
            JSONArray ge_comment_arr = j.getJSONArray("comment");
            final String u = j.getString("url");
            final ArrayList m_hl_comments = Model.commentModel(hl_comment_arr,hl_comments);
            final ArrayList m_ge_comments = Model.commentModel(ge_comment_arr,ge_comments);
            final ListView lv_ge = (ListView)a.findViewById(R.id.comment_ge);
            final ImageButton ib_sendMessage = (ImageButton)a.findViewById(R.id.sendMessage);
            final EditText et_message = (EditText)a.findViewById(R.id.commentET);

            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   if(flag.equals("hl")){
                       lv_ge.setAdapter(new MyAdapter(a.getApplicationContext(),m_hl_comments,R.layout.comment_item,new String[]{"userName","replyContent","userImg","time","supportNum"},new int[]{R.id.title,R.id.content,R.id.thumb,R.id.time,R.id.commentNum}));
                   }else{
                       lv_ge.setAdapter(new MyAdapter(a.getApplicationContext(),m_ge_comments,R.layout.comment_item,new String[]{"userName","replyContent","userImg","time","supportNum"},new int[]{R.id.title,R.id.content,R.id.thumb,R.id.time,R.id.commentNum}));
                   }

                    ib_sendMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyDBAdapter db = new MyDBAdapter(a.getApplicationContext());
                            try {
                                db.open();
                                if(db.isLogin()){
                                    if(et_message.getText().length()==0){
                                        Toast.makeText(a.getApplicationContext(),"请输入评论",Toast.LENGTH_SHORT).show();
                                    }else{
                                        String content = et_message.getText().toString().trim();
                                        String x=db.fetchData(1).getString(1);
                                        JsonMaker jm = new JsonMaker("addComment",x,u,content,0,0,0,a);
                                        jm.setJson(null,null,null,null);
                                    }

                                }else{
                                    Intent i  = new Intent(a,LoginActivity.class);
                                    a.startActivityForResult(i, 100);

                                }
                                db.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                }
            });
        }catch (JSONException e){
            Log.e(HUPO, "json error");
        }
    }
    private void getUserInfo(String result,final Activity a){
        try {
            final JSONObject j = new JSONObject(result);
            final TextView tv_userName = (TextView)a.findViewById(R.id.userName);
            final TextView tv_pageCount = (TextView)a.findViewById(R.id.pageCount);
            final TextView tv_userinfo = (TextView)a.findViewById(R.id.userinfo);
            //final ListView lv_recentNews = (ListView)a.findViewById(R.id.recentNews);
            final ImageView iv_userImg =(ImageView)a.findViewById(R.id.userImg);
            final Button btn_topic = (Button)a.findViewById(R.id.topic_button);

            final HashMap<String,String> hm = Model.userInfoModel(j);

            final ArrayList<HashMap<String,String>> recentNews=Model.recentNewsModel(j);
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    btn_topic.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(mActivity,UserTopic.class);
                            mActivity.startActivity(i);
                        }
                    });
                    tv_userName.setText(hm.get("username"));
                    tv_pageCount.setText(hm.get("pageCount"));
                    tv_userinfo.setText(Html.fromHtml(hm.get("userinfo")));
                    ImageLoader.getInstance().displayImage(hm.get("img"),iv_userImg);
                    //lv_recentNews.setAdapter(new MyAdapter(a.getApplicationContext(),recentNews,R.layout.list_recent_news_item,new String[]{"userImg","username","type","content"},new int[]{R.id.userImg,R.id.userName,R.id.type,R.id.content}));

                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getNewsPage(String result,final Activity a){
        try {
            JSONObject j = new JSONObject(result);
            HashMap<String,String> article = new HashMap<String, String>();
            article=Model.articleModel(article,j.getString("title"),j.getString("article"),j.getString("img"),j.getString("supportNum"),j.getString("url"));
            final TextView tv_article= (TextView)a.findViewById(R.id.article);
            final TextView tv_title = (TextView)a.findViewById(R.id.title);
            final TextView tv_supportNum=(TextView)a.findViewById(R.id.supportNum);
            final ImageView iv_img = (ImageView)a.findViewById(R.id.mainImg);
            final ImageButton ib_supportNum = (ImageButton)a.findViewById(R.id.admireIMG);
            final ImageButton ib_sendMessage = (ImageButton)a.findViewById(R.id.sendMessage);
            final EditText et_message = (EditText)a.findViewById(R.id.commentET);
            final HashMap<String, String> finalArticle = article;
            mActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_article.setText(Html.fromHtml(finalArticle.get("article")));
                    tv_title.setText(finalArticle.get("title"));
                    tv_supportNum.setText(finalArticle.get("supportNum"));
                    ImageLoader.getInstance().displayImage(finalArticle.get("img"), iv_img);
                    mActivity.setTitle(finalArticle.get("title"));
                    final String u = finalArticle.get("url");
                    Log.d(HUPO,u);
                    ib_supportNum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            MyDBAdapter db = new MyDBAdapter(a.getApplicationContext());
                            try {
                                db.open();
                                if(db.isLogin()){
                                    String x=db.fetchData(1).getString(1);
                                    JsonMaker jm = new JsonMaker("support",x,u,a);
                                    jm.setJson(null,null,null,null);

                                }
                                db.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                        }
                    });

                   ib_sendMessage.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           MyDBAdapter db = new MyDBAdapter(a.getApplicationContext());
                           try {
                               db.open();
                               if(db.isLogin()){
                                   if(et_message.getText().length()==0){
                                       Toast.makeText(a.getApplicationContext(),"请输入评论",Toast.LENGTH_SHORT).show();
                                   }else{
                                       String content = et_message.getText().toString().trim();
                                       String x=db.fetchData(1).getString(1);
                                       JsonMaker jm = new JsonMaker("addComment",x,u,content,0,0,0,a);
                                       jm.setJson(null,null,null,null);
                                   }

                               }
                               db.close();
                           } catch (SQLException e) {
                               e.printStackTrace();
                           }

                       }
                   });
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            Log.e(HUPO, "json error");
        }

    }
    private void getNewsList(String result,View rV, final ListView lv, final Fragment f, final Context c){
        try{
            if(flag.equals("bbs")){
                JSONArray j = new JSONArray(result);
                al=Model.bbsListModel(j);
            }else{
                JSONObject j = new JSONObject(result);
                JSONArray jsonArr = j.getJSONArray("news");
                al=Model.newsListModel(jsonArr,al);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        rV.post(new Runnable() {
            @Override
            public void run() {
                if(flag.equals("bbs")){
                    MyAdapter sa = new MyAdapter(c, al, R.layout.list_bbs_items, new String[]{"title", "info", "parent"}, new int[]{R.id.title, R.id.info, R.id.parent});
                    lv.setAdapter(sa);
                    lv.setOnItemClickListener(new BBSPageListener(f.getActivity()));
                }else {
                    MyAdapter sa = new MyAdapter(c, al, R.layout.list_items, new String[]{"title", "content", "time", "photo", "commentNum", "admireNum"}, new int[]{R.id.title, R.id.content, R.id.time, R.id.thumb, R.id.commentNum, R.id.admireNum});
                    lv.setAdapter(sa);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(c, "正在努力加载中" + al.get(i).get("link") + "。。。。。 QAQ", Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(c, NewsPage.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("url", al.get(i).get("link"));
                            it.putExtras(bundle);
                            f.getActivity().startActivity(it);


                        }
                    });
                }
                }
        });
    }
    private void getLoginInformation(String result, final Activity a){
        Log.d(HUPO,result);
        if(Integer.parseInt(result)<10){
            isLog=false;
            //TODO deal with error;

            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(a.getApplicationContext(),"登录失败",Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            isLog=true;
            try {
                MyDBAdapter myDBAdapter = new MyDBAdapter(a.getApplicationContext());
                myDBAdapter.open();
                TextView tv=(TextView)a.findViewById(R.id.email);
                String username=tv.getText().toString();
                int sign = Integer.parseInt(result);
               if(myDBAdapter.fetchAllData().getCount()== 0){
                   myDBAdapter.insertData(sign,username);
               }else{
                   myDBAdapter.updateData(1,sign,username);
               }
                myDBAdapter.close();
               // myDBAdapter.updateData(1,sign,username);
            }catch (Exception e){
                e.printStackTrace();
            }

            //Todo user view
            //startActivity(i);
            /*
            a.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(a,MainActivity.class);
                    Bundle b = new Bundle();
                    startActivity(a,i,b);
                }
            });
            */
        }
    }
    public Boolean getStatus() {
        Log.d(HUPO,""+isLog);
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }
        return isLog;
    }
    class MyAdapter extends SimpleAdapter{
        public MyAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }
        public void setViewImage(ImageView v, String value) {
            v.setImageResource(R.drawable.no_image);
            ImageLoader.getInstance().displayImage(value, v);
        }

        @Override
        public void setViewText(TextView v, String text) {
            v.setText(Html.fromHtml(text));
        }

        //public void setViewTest
    }

}
