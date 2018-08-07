package com.xtoolapp.file.filedemo.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xtoolapp.file.filedemo.MyApplication;

/**
 * Created by WangYu on 2018/8/3.
 */
public class AppInfoDataBase extends SQLiteOpenHelper {
    private static final String VALUE_STRING_TABLE_NAME = "table_app";
    private static final int VALUE_INT_VERSION_CODE = 1;
    private static AppInfoDataBase instance = new AppInfoDataBase();

    public static AppInfoDataBase getInstance() {
        return instance;
    }

    public AppInfoDataBase() {
        super(MyApplication.context, "db_app", null, VALUE_INT_VERSION_CODE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("create table %s(id integer primary key autoincrement,name varchar(64),package_name varchar(64))", VALUE_STRING_TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertApp(AppInfo appInfo) {
        if (appInfo==null)
            return false;

        String sql = String.format("insert into %s(name,package_name) values ('%s','%s')", VALUE_STRING_TABLE_NAME, appInfo.appName, appInfo.packageName);
        Log.i("wangyu", sql);
        try {
            getWritableDatabase().execSQL(sql);
        } catch (SQLException e) {
            e.printStackTrace();
           return false;
        }
        return true;
    }

    public boolean getAllAppInfo() {
        String sql = String.format("select * from %s",VALUE_STRING_TABLE_NAME);
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String packageName = cursor.getString(2);
            Log.i("wangyu", "id:" + id + " name:" + name + " package:" + packageName);
        }
        cursor.close();
        return true;
    }

    public void updateInfo() {
        String sql = String.format("update %s set name='%s',package_name='%s' where id=1", VALUE_STRING_TABLE_NAME, "skr", "666");
        getWritableDatabase().execSQL(sql);

    }

    public void deleteInfo() {
        String sql = String.format("delete from %s where name='魔法相机'",VALUE_STRING_TABLE_NAME);
        getWritableDatabase().execSQL(sql);
    }
}
