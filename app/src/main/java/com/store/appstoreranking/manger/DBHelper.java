package com.store.appstoreranking.manger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.store.appstoreranking.model.AppsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wenxiaokang
 * @className DbHelper
 * @description 数据库操作
 * @date 4/16/21 2:16 PM
 */
public class DBHelper extends SQLiteOpenHelper {
    //表名
    private final static String  TABLE_NAME = "apps_table";
    private final static String TAG = "DbHelper";
    //程序标志
    private final static String DATABASE_NAME = "locke_db";
    //版本号
    private final static int DATABASE_VERSION = 1;

    public final static int TABLE_RECOMMEND = 0;
    public final static int TABLE_RANKING = 1;


    //结构
    private final static String TABLE_VALUE =
                    "(id TEXT," +
                    "image TEXT," +
                    "name TEXT," +
                    "trackContentRating FLOAT," +
                    "userRatingCount TEXT," +
                    "category TEXT," +
                    "title TEXT,"+
                    "type INTEGER)";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    /**
     * 创建数据库
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + TABLE_VALUE);
    }

    /**
     * 升级数据库
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *
     /*
     * 添加一条App记录
     * @param appsModel appsModel
     * @param type
     * @return 返回插入的行 id
     */
    public long addApps(AppsModel appsModel ,int type) {
        SQLiteDatabase db = null;
        try {
            ContentValues values = appsModel.getContentValue(type);
            db = this.getWritableDatabase();
            long result = db.insert(TABLE_NAME, null, values);
            return result;
        } catch (SQLiteException e) {
            return -1;
        } finally {
            if (db.isOpen())
                db.close();
        }
    }

    /**
     *
     * @return  返回列表数据
     * @param type 0-推荐 1-排行
     */
    public List<AppsModel> queryApps(int type) {
        Cursor c = null;
        SQLiteDatabase db = null;
        List<AppsModel> result = new ArrayList<>();
        try {
            db = this.getReadableDatabase();
            c = db.query(TABLE_NAME, new String[]{"id", "image", "name", "trackContentRating", "userRatingCount", "category", "title","type"}, "type=?", new String[]{String.valueOf(type)}, null, null, null);
            c.moveToFirst();
            while (!c.isAfterLast()) {
                AppsModel appsModel = new AppsModel();
                appsModel.setId(c.getString(0));
                appsModel.setImage(c.getString(1));
                appsModel.setName(c.getString(2));
                appsModel.setTrackContentRating(c.getFloat(3));
                appsModel.setUserRatingCount(c.getInt(4));
                appsModel.setTitle(c.getString(5));
                result.add(appsModel);
                c.moveToNext();
            }
            return result;
        } catch (SQLiteException e) {
            Log.e(TAG, e.getMessage());
            return null;
        } finally {
            if (c != null) {
                if (!c.isClosed()) {
                    c.close();
                }
                if (db.isOpen()) {
                    db.close();
                }
            }

        }
    }

    /**
     * 清除表数据
     * @param type 0-推荐 1-排行
     */
    public void clearTable(int type) {
        SQLiteDatabase db = null;
        try {
            db = this.getWritableDatabase();
            db.delete(TABLE_NAME,"type=?",new String[]{String.valueOf(type)});
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            db.close();
            this.close();
        }
    }

    /**
     * 添加数据
     *
     * @param appsModels
     * @param type 0-推荐 1-排行
     */
    public boolean addApps(List<AppsModel> appsModels,int type) {
        clearTable(type);
        boolean isSuccess = true;
        for (AppsModel appsModel : appsModels) {
            if (addApps(appsModel,type)==-1) {
                isSuccess = false;
            }
        }
        return isSuccess;
    }
}
