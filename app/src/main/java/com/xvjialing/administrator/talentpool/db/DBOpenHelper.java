package com.xvjialing.administrator.talentpool.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name) {
        super(context, name, null, 1);
        // TODO Auto-generated constructor stub
    }

    public DBOpenHelper(Context context, String name, CursorFactory factory,
                        int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override//首次创建数据库的时候调用 一般可以把建库 建表的操作
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL("create table if not exists joblist(_id integer primary key autoincrement," +
                "jobName text not null,address text not null,salary text not null," +
                "workLength text not null,publishingTime text not null,companyName text not null," +
                "size text not null,big_logo text not null,degree text not null,welfare text not null," +
                "descrip text not null)");
    }

    @Override//当数据库的版本发生变化的时候 会自动执行
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}
