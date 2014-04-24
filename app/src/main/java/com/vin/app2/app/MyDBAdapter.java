package com.vin.app2.app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by orphira on 14-4-21.
 */
public class MyDBAdapter {
    private static final String MyDBAdapter_TAG="myDBAdapter";
    private static final String MyDBAdapter_DB_NAME="Hupo.db";
    private static final String MyDBAdapter_DB_TABLE="logInfo";
    public static final String MyDBAdapter_KEY_ID = "_id";// 表中一条数据的内容
    public static final String MyDBAdapter_KEY_NUM = "num";// 表中一条数据的id
    public static final String MyDBAdapter_KEY_DATA = "data";//数据库名称为data
    private static final int MyDBAdapter_DB_VERSION = 1;
    private Context mContext=null;
    private static final String MyDBAdapter_DB_CREATE="CREATE TABLE "+MyDBAdapter_DB_TABLE+" ("+MyDBAdapter_KEY_ID
            +" INTEGER PRIMARY KEY,"+MyDBAdapter_KEY_NUM+" INTEGER,"+MyDBAdapter_KEY_DATA+" TEXT)";
    private SQLiteDatabase mSQLiteDatabase=null;
    private DatabaseHelper mDatabaseHelper=null;
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context,MyDBAdapter_DB_NAME,null,MyDBAdapter_DB_VERSION);
        }
        public void onCreate(SQLiteDatabase db){
            db.execSQL(MyDBAdapter_DB_CREATE);
        }
        public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
            db.execSQL("DROP IF EXIST "+MyDBAdapter_DB_TABLE);
            onCreate(db);
        }
    }
    public MyDBAdapter(Context context){
        mContext=context;
    }
    public void open() throws SQLException{
        mDatabaseHelper = new DatabaseHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }
    public void close(){
        mDatabaseHelper.close();
    }
    public long insertData(int num,String data){
        ContentValues cv = new ContentValues();
        cv.put(MyDBAdapter_KEY_NUM,num);
        cv.put(MyDBAdapter_KEY_DATA,data);
        return mSQLiteDatabase.insert(MyDBAdapter_DB_TABLE,MyDBAdapter_KEY_ID,cv);
    }
    public boolean deleteData(long rowId){
        return mSQLiteDatabase.delete(MyDBAdapter_DB_TABLE,MyDBAdapter_KEY_ID+"="+rowId,null)>0;
    }
    public Cursor fetchAllData(){
        return mSQLiteDatabase.query(MyDBAdapter_DB_TABLE,new String[]{MyDBAdapter_KEY_ID,MyDBAdapter_KEY_NUM,MyDBAdapter_KEY_DATA},null,null,null,null,null);
    }
    public Cursor fetchData(long rowId) throws SQLException{
        Cursor mCursor =mSQLiteDatabase.query(true,MyDBAdapter_DB_TABLE,new String[]{MyDBAdapter_KEY_ID,MyDBAdapter_KEY_NUM,MyDBAdapter_KEY_DATA},MyDBAdapter_KEY_ID+"="+rowId,null,null,null,null,null);
        if(mCursor != null){
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public boolean updateData(long rowId,int num,String data){
        ContentValues contentValues = new ContentValues();
        contentValues.put(MyDBAdapter_KEY_NUM,num);
        contentValues.put(MyDBAdapter_KEY_DATA,data);
        return mSQLiteDatabase.update(MyDBAdapter_DB_TABLE,contentValues,MyDBAdapter_KEY_ID+"="+rowId,null)>0;
    }
    public boolean isLogin(){
        if(fetchAllData().getCount()==1){
            return true;
        }else{
            return false;
        }
    }
}
