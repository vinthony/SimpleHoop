package com.vin.app2.app;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by orphira on 14-4-18.
 */
public class Model {
    public static ArrayList<HashMap<String,String>> commentModel(JSONArray commentArray,ArrayList<HashMap<String,String>> commentList){
        try {
            for (int i = 0; i < commentArray.length(); i++) {
                JSONObject o = (JSONObject) commentArray.opt(i);
                HashMap<String, String> temp = new HashMap<String, String>();
                temp.put("userImg", o.getString("userImg"));
                temp.put("userLink", o.getString("userLink"));
                temp.put("userName", o.getString("userName"));
                temp.put("supportNum", o.getString("supportNum"));
                temp.put("replyContent", o.getString("replyContent"));
                temp.put("time", o.getString("time"));
                commentList.add(temp);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return commentList;
    }
    public static HashMap<String,String> userInfoModel(JSONObject j) throws JSONException {
        HashMap<String,String> a = new HashMap<String, String>();
        a.put("img",j.getString("img"));
        a.put("username",j.getString("username"));
        a.put("pageCount",j.getString("pageCount"));
        a.put("userinfo",j.getString("userinfo"));
        return a;
    }
    public static ArrayList<HashMap<String,String>> recentNewsModel(JSONObject j) throws JSONException{
        ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String, String>>();
        JSONArray jsonArray = j.getJSONArray("attentions");
       for (int i=0;i<jsonArray.length();i++){
           JSONObject o = (JSONObject)jsonArray.opt(i);
           HashMap<String,String> m = new HashMap<String, String>();
           m.put("link",o.getString("link"));
           m.put("userImg",o.getString("userImg"));
           m.put("username",o.getString("username"));
           m.put("type",o.getString("type"));
           m.put("content",o.getString("content"));
           al.add(m);
       }
        return al;
    }
    public static HashMap<String,String> articleModel(HashMap<String,String> a,String title,String article,String img,String supportNum,String url){
        a.put("title",title);
        a.put("article",article);
        a.put("img",img);
        a.put("supportNum",supportNum);
        a.put("url",url);
        return a;
    }
    public static ArrayList<HashMap<String,String>> newsListModel(JSONArray jsonArr,ArrayList<HashMap<String,String>> al,int flag){
        try {
            for (int i = flag; i < jsonArr.length(); i++) {
                JSONObject o = (JSONObject) jsonArr.opt(i);
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("title", o.getString("title"));
                m.put("content", o.getString("content"));
                m.put("photo", o.getString("photo"));
                m.put("time", o.getString("time"));
                m.put("commentNum",  o.getString("commentNum"));
                m.put("admireNum", o.getString("admireNum"));
                m.put("link", o.getString("link"));
                al.add(m);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return al;
    }
    public static ArrayList<HashMap<String,String>> userTopicModel(JSONArray j)throws JSONException {
        ArrayList<HashMap<String, String>> re = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < j.length(); i++) {
            JSONObject o = (JSONObject) j.opt(i);
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("title", o.getString("title"));
            temp.put("bbs", o.getString("bbs"));
            temp.put("bbsLink", o.getString("bbsLink"));
            temp.put("link", o.getString("link"));
            temp.put("time", o.getString("time"));
            temp.put("info", o.getString("info"));
            re.add(temp);
        }
        return re;
    }

    public static ArrayList<HashMap<String,String>> userTopicReModel(JSONArray j)throws JSONException {
        ArrayList<HashMap<String, String>> re = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < j.length(); i++) {
            JSONObject o = (JSONObject) j.opt(i);
            HashMap<String, String> temp = new HashMap<String, String>();
            temp.put("title", o.getString("title"));
            temp.put("bbs", o.getString("bbs"));
            temp.put("bbsLink", o.getString("bbslink"));
            temp.put("link", o.getString("relink"));
            temp.put("time", o.getString("time"));
            temp.put("context", o.getString("context"));
            re.add(temp);
        }
        return re;
    }

    public static ArrayList<HashMap<String, String>> bbsListModel(JSONArray j)throws JSONException {
        ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < j.length(); i++) {
            JSONObject o = (JSONObject) j.opt(i);
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("title", o.getString("title"));
            m.put("href", o.getString("href"));
            m.put("info", o.getString("info"));
            m.put("parent", o.getString("parent"));
            m.put("parentHref", o.getString("parentHref"));
            al.add(m);
        }
        return al;
    }
    public static ArrayList<HashMap<String, String>> bbsGeneralModel(JSONArray j)throws JSONException {
        ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < j.length(); i++) {
            JSONObject o = (JSONObject) j.opt(i);
            HashMap<String, String> m = new HashMap<String, String>();
            m.put("title", o.getString("title"));
            m.put("href", o.getString("href"));
            m.put("info", o.getString("info"));
            m.put("lastReTime", o.getString("lastReTime"));
            m.put("author", o.getString("author"));
            al.add(m);
        }
        return al;
    }

    public static HashMap<String,String> BBSMainDetail(JSONObject jo) throws JSONException{
       JSONObject j =jo.getJSONObject("main");
       HashMap<String,String> hashMap = new HashMap<String, String>();
       hashMap.put("title",j.getString("title"));
       hashMap.put("userName",j.getString("userName"));
      // hashMap.put("time",j.getString("time"));
       hashMap.put("content",j.getString("content"));
       hashMap.put("userImg",j.getString("userImg"));
       return hashMap;
    }
    public static ArrayList<HashMap<String,String>> BBSFloorDetail(JSONObject jo) throws JSONException{
        JSONArray j = jo.getJSONArray("floors");
        ArrayList<HashMap<String,String>> al = new ArrayList<HashMap<String, String>>();
        for (int i=0;i<j.length();i++){
            JSONObject o = (JSONObject)j.opt(i);
            HashMap<String,String> m = new HashMap<String, String>();
            m.put("userName",o.getString("authorName"));
            m.put("content",o.getString("contentText"));
            m.put("userImg",o.getString("authorIMG"));
            m.put("admireNum",o.getString("admireNum"));
            m.put("userInfo",o.getString("time"));
            al.add(m);
        }
        return al;
    }
}
