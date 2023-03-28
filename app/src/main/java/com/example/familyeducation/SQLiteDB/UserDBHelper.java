package com.example.familyeducation.SQLiteDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.familyeducation.Bean.UserInfo;

import java.util.ArrayList;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final String TAG = "UserDBHelper";
    private static final String DB_NAME = "user.db"; // 数据库的名称
    private static final int DB_VERSION = 1; // 数据库的版本号
    private static UserDBHelper mHelper = null; // 数据库帮助器的实例
    private SQLiteDatabase mDB = null; // 数据库的实例
    public static final String TABLE_NAME = "parent";
    public static final String TABLE_teacher = "teacher";
    public static final String TABLE_order = "par_order";
    public static final String TABLE_reward = "par_reward";
    public static final String TABLE_money = "par_money";
    public static final String TABLE_reserve = "reserve";

    public UserDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private UserDBHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    // 利用单例模式获取数据库帮助器的唯一实例
    public static UserDBHelper getInstance(Context context, int version) {
        if (version > 0 && mHelper == null) {
            mHelper = new UserDBHelper(context, version);
        } else if (mHelper == null) {
            mHelper = new UserDBHelper(context);
        }
        return mHelper;
    }

    //构造数据库，将外部数据库与帮助器内部的数据库连接
    public void setmDB(SQLiteDatabase mDB) {
        this.mDB = mDB;
    }

    // 打开数据库的读连接
    public SQLiteDatabase openReadLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getReadableDatabase();
        }
        return mDB;
    }

    // 打开数据库的写连接
    public SQLiteDatabase openWriteLink() {
        if (mDB == null || !mDB.isOpen()) {
            mDB = mHelper.getWritableDatabase();
        }
        return mDB;
    }

    // 关闭数据库连接
    public void closeLink() {
        if (mDB != null && mDB.isOpen()) {
            mDB.close();
            mDB = null;
        }
    }

    // 创建数据库，执行建表语句
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
//        String drop_TABLE_NAME = "DROP TABLE IF EXISTS " + TABLE_NAME + ";"; //如果存在TABLE_NAME，删除
//        db.execSQL(drop_TABLE_NAME);
//        String drop_TABLE_teacher = "DROP TABLE IF EXISTS " + TABLE_teacher + ";";
//        db.execSQL(drop_TABLE_teacher);

        String create_TABLE_reserve = "CREATE TABLE IF NOT EXISTS  " + TABLE_reserve + " ("
                + "reserve_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                //演示数据库升级时要先把下面这行注释
                + "reserve_parphone VARCHAR NOT NULL" + ",reserve_teaphone VARCHAR NOT NULL,"
                + "reserve_teaname VARCHAR NOT NULL" + ",reserve_teasub VARCHAR NOT NULL,"
                + "reserve_teagrade VARCHAR NOT NULL" + ");";
        db.execSQL(create_TABLE_reserve);

        String create_TABLE_money = "CREATE TABLE IF NOT EXISTS " + TABLE_money + " ("
                + "money_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                //演示数据库升级时要先把下面这行注释
                + "money_phone VARCHAR NOT NULL,"
                + "money_yue VARCHAR" + ",money_jifen VARCHAR" + ");";
        db.execSQL(create_TABLE_money);

        String create_TABLE_reward = "CREATE TABLE IF NOT EXISTS " + TABLE_reward + " ("
                + "reward_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                //演示数据库升级时要先把下面这行注释
                + "reward_phone VARCHAR NOT NULL" + ",reward_price VARCHAR NOT NULL,"
                + "reward_date VARCHAR" + ");";
        db.execSQL(create_TABLE_reward);

        String create_TABLE_order = "CREATE TABLE IF NOT EXISTS " + TABLE_order + " ("
                + "order_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                //演示数据库升级时要先把下面这行注释
                + "par_order_phone VARCHAR NOT NULL,"+ "par_order_bookname VARCHAR NOT NULL,"
                + "par_order_bookcount VARCHAR" + ",par_order_bookprice VARCHAR NOT NULL,"
                + "par_order_bookstate VARCHAR" + ");";
        db.execSQL(create_TABLE_order);

        String create_sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + "par_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                //演示数据库升级时要先把下面这行注释
                + "par_phone VARCHAR NOT NULL" + ",par_password VARCHAR," + "par_icon int" + ");";
        db.execSQL(create_sql);

        String create_TABLE_teacher = "CREATE TABLE IF NOT EXISTS  " + TABLE_teacher + " ("
                + "tea_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                //演示数据库升级时要先把下面这行注释
                + "tea_phone VARCHAR NOT NULL" + ",tea_sex VARCHAR NOT NULL,"
                + "tea_name VARCHAR NOT NULL" + ",tea_sub VARCHAR NOT NULL,"
                + "tea_grade VARCHAR NOT NULL" + ");";
        db.execSQL(create_TABLE_teacher);
    }

    // 修改数据库，执行表结构变更语句
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade oldVersion=" + oldVersion + ", newVersion=" + newVersion);
//        if (newVersion > 1) {
//            //Android的ALTER命令不支持一次添加多列，只能分多次添加
//            String alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "phone VARCHAR;";
//            Log.d(TAG, "alter_sql:" + alter_sql);
//            db.execSQL(alter_sql);
//            alter_sql = "ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + "password VARCHAR;";
//            Log.d(TAG, "alter_sql:" + alter_sql);
//            db.execSQL(alter_sql);
//        }
    }

    /** 根据指定条件删除表记录
     * 调用如下
     UserDBHelper mHelper = null;
     SQLiteDatabase sql = mHelper.openWriteLink();
     mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

     mHelper.delete("par_id='3'");
     * */
    public void delete(String table,String condition) {
        // 执行删除记录动作
        mDB.delete(table, condition, null);
    }

    // 删除该表的所有记录,返回删除记录的数目
    public int deleteAll(String table) {
        return mDB.delete(table, "1=1", null);
    }

    // 往该表添加一条记录
    public void insert(String table,UserInfo info) {
        ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
        infoArray.add(info);
        insert(table,infoArray);
    }

    // 往该表添加多条记录
    public void insert(String table,ArrayList<UserInfo> infoArray) {
        for (int i = 0; i < infoArray.size(); i++) {
            UserInfo info = infoArray.get(i);
            ArrayList<UserInfo> tempArray = new ArrayList<UserInfo>();
            ContentValues cv = new ContentValues();
            String condition = "";
            // 注意条件语句的等号后面要用单引号括起来
            switch (table){
                case TABLE_money:
                    // 如果存在同样的手机号码，则更新记录
                    if (info.money_phone != null && info.money_phone.length() > 0) {
                        condition = String.format("money_phone='%s'", info.money_phone);
                        tempArray = query(TABLE_money,condition);//更新指定的表记录
                        if (tempArray.size() > 0) {
                            update(TABLE_money, info, condition);
                            continue;
                        }
                    }
                    cv.put("money_phone", info.money_phone);
                    cv.put("money_yue", info.money_yue);
                    cv.put("money_jifen", info.money_jifen);
                    break;
                case TABLE_reward:
                    cv.put("reward_phone", info.reward_phone);
                    cv.put("reward_price", info.reward_price);
                    cv.put("reward_date", info.reward_date);
                    break;
                case TABLE_order:
                    cv.put("par_order_phone", info.par_order_phone);
                    cv.put("par_order_bookname", info.par_order_bookname);
                    cv.put("par_order_bookcount", info.par_order_bookcount);
                    cv.put("par_order_bookprice", info.par_order_bookprice);
                    cv.put("par_order_bookstate", info.par_order_bookstate);
                    break;
                case TABLE_NAME:
                    // 如果存在同样的手机号码，则更新记录
                    if (info.par_phone != null && info.par_phone.length() > 0) {
                        condition = String.format("par_phone='%s'", info.par_phone);
                        tempArray = query(TABLE_NAME,condition);//更新指定的表记录
                        if (tempArray.size() > 0) {
                            update(TABLE_NAME, info, condition);
                            continue;
                        }
                    }
                    cv.put("par_phone", info.par_phone);
                    cv.put("par_password", info.par_password);
                    cv.put("par_icon", info.par_icon);
                    break;
                case TABLE_teacher:
                    if (info.tea_phone != null && info.tea_phone.length() > 0) {
                        condition = String.format("tea_phone='%s'", info.tea_phone);
                        tempArray = query(TABLE_teacher,condition);
                        if (tempArray.size() > 0) {
                            update(TABLE_teacher,info, condition);//更新指定的表记录
                            continue;
                        }
                    }
                    cv.put("tea_phone", info.tea_phone);
                    cv.put("tea_sex", info.tea_sex);
                    cv.put("tea_name", info.tea_name);
                    cv.put("tea_sub", info.tea_sub);
                    cv.put("tea_grade", info.tea_grade);
//                    cv.put("tea_isChecked", info.tea_isChecked);
                    break;
                case TABLE_reserve:
                    // 如果存在同样的手机号码，则更新记录
                    if (info.reserve_parphone != null && info.reserve_parphone.length() > 0) {
                        condition = String.format("reserve_parphone='%s'", info.reserve_parphone);
                        tempArray = query(TABLE_reserve,condition);
                        if (tempArray.size() > 0) {
                            update(TABLE_reserve,info, condition);//更新指定的表记录
                            continue;
                        }
                    }
                    cv.put("reserve_parphone", info.reserve_parphone);
                    cv.put("reserve_teaphone", info.reserve_teaphone);
                    cv.put("reserve_teaname", info.reserve_teaname);
                    cv.put("reserve_teasub", info.reserve_teasub);
                    cv.put("reserve_teagrade", info.reserve_teagrade);
                    break;
                default:
                    break;
            }
            mDB.insert(table, "", cv);
        }
    }
    // 往该表添加多条记录
//    public void insert(ArrayList<UserInfo> infoArray) {
//        for (int i = 0; i < infoArray.size(); i++) {
//            UserInfo info = infoArray.get(i);
//            ArrayList<UserInfo> tempArray = new ArrayList<UserInfo>();
//            // 注意条件语句的等号后面要用单引号括起来
//            // 如果存在同样的手机号码，则更新记录
//            if (info.par_phone != null && info.par_phone.length() > 0) {
//                String condition = String.format("par_phone='%s'", info.par_phone);
//                tempArray = query(condition);//更新指定的表记录
//                if (tempArray.size() > 0) {
//                    update(info, condition);
//                    continue;
//                }
//            }
//            // 不存在唯一性重复的记录，则插入新记录
//            ContentValues cv = new ContentValues();
//            cv.put("par_phone", info.par_phone);
//            cv.put("par_password", info.par_password);
//            mDB.insert(TABLE_NAME, "", cv);
//        }
//    }

    /** 根据条件找到指定的表记录，更新par_phone\par_password内容
     * 调用如下
     UserDBHelper mHelper = null;
     SQLiteDatabase sql = mHelper.openWriteLink();
     mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

     UserInfo updateduser = new UserInfo();
     updateduser.par_phone = "00";
     updateduser.par_password = "00";
     mHelper.update(updateduser,"par_id='7'");
     * */
    public void update(String table, UserInfo info, String condition) {
        ContentValues cv = new ContentValues();
        switch (table){
            case TABLE_money:
                cv.put("money_phone", info.money_phone);
                cv.put("money_yue", info.money_yue);
                cv.put("money_jifen", info.money_jifen);
                break;
            case TABLE_reward:
                cv.put("reward_phone", info.reward_phone);
                cv.put("reward_price", info.reward_price);
                cv.put("reward_date", info.reward_date);
                break;
            case TABLE_order:
                cv.put("par_order_phone", info.par_order_phone);
                cv.put("par_order_bookname", info.par_order_bookname);
                cv.put("par_order_bookcount", info.par_order_bookcount);
                cv.put("par_order_bookprice", info.par_order_bookprice);
                cv.put("par_order_bookstate", info.par_order_bookstate);
                break;
            case TABLE_NAME:
                cv.put("par_phone", info.par_phone);
                cv.put("par_password", info.par_password);
                cv.put("par_icon", info.par_icon);
                break;
            case TABLE_teacher:
                cv.put("tea_phone", info.tea_phone);
                cv.put("tea_sex", info.tea_sex);
                cv.put("tea_name", info.tea_name);
                cv.put("tea_sub", info.tea_sub);
                cv.put("tea_grade", info.tea_grade);
//                cv.put("tea_isChecked", info.tea_isChecked);
                break;
            case TABLE_reserve:
                cv.put("reserve_parphone", info.reserve_parphone);
                cv.put("reserve_teaphone", info.reserve_teaphone);
                cv.put("reserve_teaname", info.reserve_teaname);
                cv.put("reserve_teasub", info.reserve_teasub);
                cv.put("reserve_teagrade", info.reserve_teagrade);
                break;
            default:
                break;
        }
        mDB.update(table, cv, condition, null);
    }

    /** 根据条件找到指定的表记录，更新指定的关键字内容
     * 调用如下
     UserDBHelper mHelper = null;
     SQLiteDatabase sql = mHelper.openWriteLink();
     mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

     UserInfo updateduser = new UserInfo();
     updateduser.par_phone = "00";
     mHelper.updateKey(updateduser,"par_phone","par_id='8'");
     * */
//    public void updateKey(UserInfo info,String key,String condition) {
//        ContentValues cv = new ContentValues();
//        switch(key){
//            case "par_id":
//                cv.put("par_id", info.par_id);
//                break;
//            case "par_phone":
//                cv.put("par_phone", info.par_phone);
//                break;
//            case "par_password":
//                cv.put("par_password", info.par_password);
//                break;
//            default:
//                break;
//        }
//        mDB.update(TABLE_NAME, cv, condition, null);
//    }
    public void updateKey(String table,UserInfo info,String key,String condition) {
        ContentValues cv = new ContentValues();
        switch (table){
            case TABLE_money:
                switch(key){
                    case "money_id":
                        cv.put("reward_id", info.reward_id);
                        break;
                    case "money_phone":
                        cv.put("money_phone", info.money_phone);
                        break;
                    case "money_yue":
                        cv.put("money_yue", info.money_yue);
                        break;
                    case "money_jifen":
                        cv.put("money_jifen", info.money_jifen);
                        break;
                    default:
                        break;
                }
                break;
            case TABLE_reward:
                switch(key){
                    case "reward_id":
                        cv.put("reward_id", info.reward_id);
                        break;
                    case "reward_phone":
                        cv.put("reward_phone", info.reward_phone);
                        break;
                    case "reward_price":
                        cv.put("reward_price", info.reward_price);
                        break;
                    case "reward_date":
                        cv.put("reward_date", info.reward_date);
                        break;
                    default:
                        break;
                }
                break;
            case TABLE_order:
                switch(key){
                    case "order_id":
                        cv.put("order_id", info.order_id);
                        break;
                    case "par_order_phone":
                        cv.put("par_order_phone", info.par_order_phone);
                        break;
                    case "par_order_bookname":
                        cv.put("par_order_bookname", info.par_order_bookname);
                        break;
                    case "par_order_bookprice":
                        cv.put("par_order_bookprice", info.par_order_bookprice);
                        break;
                    case "par_order_bookcount":
                        cv.put("par_order_bookcount", info.par_order_bookcount);
                        break;
                    case "par_order_bookstate":
                        cv.put("par_order_bookstate", info.par_order_bookstate);
                        break;
                    default:
                        break;
                }
                break;
            case TABLE_NAME:
                switch(key){
                    case "par_id":
                        cv.put("par_id", info.par_id);
                        break;
                    case "par_phone":
                        cv.put("par_phone", info.par_phone);
                        break;
                    case "par_password":
                        cv.put("par_password", info.par_password);
                        break;
                    case "par_icon":
                        cv.put("par_icon", info.par_icon);
                        break;
                    default:
                        break;
                }
                break;
            case TABLE_teacher:
                switch(key){
                    case "tea_id":
                        cv.put("tea_id", info.tea_id);
                        break;
                    case "tea_phone":
                        cv.put("tea_phone", info.tea_phone);
                        break;
                    case "tea_sex":
                        cv.put("tea_sex", info.tea_sex);
                        break;
                    case "tea_name":
                        cv.put("tea_name", info.tea_name);
                        break;
                    case "tea_sub":
                        cv.put("tea_sub", info.tea_sub);
                        break;
                    case "tea_grade":
                        cv.put("tea_grade", info.tea_grade);
                        break;
//                    case "tea_isChecked":
//                        cv.put("tea_isChecked", info.tea_isChecked);
//                        break;
                    default:
                        break;
                }
                break;
            case TABLE_reserve:
                switch(key){
                    case "reserve_id":
                        cv.put("reserve_id", info.reserve_id);
                        break;
                    case "reserve_parphone":
                        cv.put("reserve_parphone", info.reserve_parphone);
                        break;
                    case "reserve_teaphone":
                        cv.put("reserve_teaphone", info.reserve_teaphone);
                        break;
                    case "reserve_teaname":
                        cv.put("reserve_teaname", info.reserve_teaname);
                        break;
                    case "reserve_teasub":
                        cv.put("reserve_teasub", info.reserve_teasub);
                        break;
                    case "reserve_teagrade":
                        cv.put("reserve_teagrade", info.reserve_teagrade);
                        break;
                    default:
                        break;
                }
                break;
            default:
                break;
        }
        mDB.update(table, cv, condition, null);
    }

    /** 返回数据表 TABLE_NAME 的所有记录:
     * 调用如下
     UserDBHelper mHelper = null;
     SQLiteDatabase sql = mHelper.openReadLink();
     mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

     ArrayList<UserInfo> infoArrayList = mHelper.queryAll();
     for(int i=0;i<infoArrayList.size();i++){
        Log.i("kk",""+infoArrayList.get(i).toString());
     }
     * */
//    public ArrayList<UserInfo> queryAll() {
//        String sql = String.format("select * from %s",TABLE_NAME);
//        Log.d(TAG, "query sql: " + sql);
//        ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
//        // 执行记录查询动作，该语句返回结果集的游标
//        Cursor cursor = mDB.rawQuery(sql, null);
//        // 循环取出游标指向的每条记录
//        while (cursor.moveToNext()) {
//            UserInfo info = new UserInfo();
//            info.par_id = cursor.getInt(0);
//            info.par_phone = cursor.getString(1);
//            info.par_password = cursor.getString(2); // 取出字符串
//            infoArray.add(info);
//        }
//        cursor.close(); // 查询完毕，关闭游标
//        return infoArray;
//    }
    public ArrayList<UserInfo> queryAll(String table) {
        ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
        // 执行记录查询动作，该语句返回结果集的游标
        String sql = String.format("select * from %s",table);
        Cursor cursor = mDB.rawQuery(sql, null);
        switch (table){
            case TABLE_money:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.money_id = cursor.getInt(0);
                    info.money_phone = cursor.getString(1);
                    info.money_yue = cursor.getString(2);
                    info.money_jifen = cursor.getString(3);
                    infoArray.add(info);
                }
                break;
            case TABLE_reward:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.reward_id = cursor.getInt(0);
                    info.reward_phone = cursor.getString(1);
                    info.reward_price = cursor.getString(2);
                    info.reward_date = cursor.getString(3);
                    infoArray.add(info);
                }
                break;
            case TABLE_order:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.order_id = cursor.getInt(0);
                    info.par_order_phone = cursor.getString(1);
                    info.par_order_bookname = cursor.getString(2);
                    info.par_order_bookcount = cursor.getString(3);
                    info.par_order_bookprice = cursor.getString(4);
                    info.par_order_bookstate = cursor.getString(5);
                    infoArray.add(info);
                }
                break;
            case TABLE_NAME:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.par_id = cursor.getInt(0);
                    info.par_phone = cursor.getString(1);
                    info.par_password = cursor.getString(2); // 取出字符串
                    info.par_icon = cursor.getInt(3);
                    infoArray.add(info);
                }
                break;
            case TABLE_teacher:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.tea_id = cursor.getInt(0);
                    info.tea_phone = cursor.getString(1);
                    info.tea_sex = cursor.getString(2);
                    info.tea_name = cursor.getString(3);
                    info.tea_sub = cursor.getString(4);
                    info.tea_grade = cursor.getString(5);
                    //SQLite没有布尔型，用0表示false，用1表示true
//                    info.tea_isChecked = (cursor.getInt(6) == 0) ? false : true;
                    infoArray.add(info);
                }
                break;
            case TABLE_reserve:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.reserve_id = cursor.getInt(0);
                    info.reserve_parphone = cursor.getString(1);
                    info.reserve_teaphone = cursor.getString(2);
                    info.reserve_teaname = cursor.getString(3);
                    info.reserve_teasub = cursor.getString(4);
                    info.reserve_teagrade = cursor.getString(5);
                    infoArray.add(info);
                }
                break;
            default:
                break;
        }
        cursor.close(); // 查询完毕，关闭游标
        return infoArray;
    }

    /** 根据指定条件查询记录，并返回结果数据队列
     * 调用如下
     UserDBHelper mHelper = null;
     SQLiteDatabase sql = mHelper.openReadLink();
     mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

     ArrayList<UserInfo> infoArrayList = mHelper.query("par_id='1'");
     for(int i=0;i<infoArrayList.size();i++){
        Log.i("kk",""+infoArrayList.get(i).toString());
     }
     * */
    public ArrayList<UserInfo> query(String table,String condition) {
        ArrayList<UserInfo> infoArray = new ArrayList<UserInfo>();
        //假设condition为"par_id = '1'"，返回数据表 TABLE_NAME 中 par_id 字段值为 1 的所有记录
        String sql = String.format("select * from %s where %s;",table,condition);
        Cursor cursor = null;
        if(sql != null){
            // 执行记录查询动作，该语句返回结果集的游标
            cursor = mDB.rawQuery(sql, null);
        }
        switch (table){
            case TABLE_money:
                // 循环取出游标指向的每条记录
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.money_id = cursor.getInt(0);
                    info.money_phone = cursor.getString(1);
                    info.money_yue = cursor.getString(2);
                    info.money_jifen = cursor.getString(3);
                    infoArray.add(info);
                }
                break;
            case TABLE_reward:
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.reward_id = cursor.getInt(0);
                    info.reward_phone = cursor.getString(1);
                    info.reward_price = cursor.getString(2);
                    info.reward_date = cursor.getString(3);
                    infoArray.add(info);
                }
                break;
            case TABLE_order:
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.order_id = cursor.getInt(0);
                    info.par_order_phone = cursor.getString(1);
                    info.par_order_bookname = cursor.getString(2);
                    info.par_order_bookcount = cursor.getString(3);
                    info.par_order_bookprice = cursor.getString(4);
                    info.par_order_bookstate = cursor.getString(5);
                    infoArray.add(info);
                }
                break;
            case TABLE_NAME:
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.par_id = cursor.getInt(0);
                    info.par_phone = cursor.getString(1);
                    info.par_password = cursor.getString(2); // 取出字符串
                    info.par_icon = cursor.getInt(3);
                    infoArray.add(info);
                }
                break;
            case TABLE_teacher:
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.tea_id = cursor.getInt(0);
                    info.tea_phone = cursor.getString(1);
                    info.tea_sex = cursor.getString(2);
                    info.tea_name = cursor.getString(3);
                    info.tea_sub = cursor.getString(4);
                    info.tea_grade = cursor.getString(5);
                    //SQLite没有布尔型，用0表示false，用1表示true
//                    info.tea_isChecked = (cursor.getInt(6) == 0) ? false : true;
                    infoArray.add(info);
                }
                break;
            case TABLE_reserve:
                while (cursor.moveToNext()) {
                    UserInfo info = new UserInfo();
                    info.reserve_id = cursor.getInt(0);
                    info.reserve_parphone = cursor.getString(1);
                    info.reserve_teaphone = cursor.getString(2);
                    info.reserve_teaname = cursor.getString(3);
                    info.reserve_teasub = cursor.getString(4);
                    info.reserve_teagrade = cursor.getString(5);
                    infoArray.add(info);
                }
                break;
            default:
                break;
        }
        cursor.close(); // 查询完毕，关闭游标
        return infoArray;
    }

    /** 根据手机号码查询指定记录
     * 调用如下
        UserDBHelper mHelper = null;
        SQLiteDatabase sql = mHelper.openReadLink();
        mHelper.setmDB(sql);//将数据库实例传到数据库帮助器里

        UserInfo info = mHelper.queryByPhone("11");
        Log.i("kk",""+info.toString());
     * */
    public UserInfo queryByPhone(String table,String phone) {
        UserInfo info = null;
        ArrayList<UserInfo> infoArray = new ArrayList<>();
        switch (table){
            case TABLE_money:
                infoArray = query(TABLE_money,String.format("money_phone='%s'", phone));
                break;
            case TABLE_NAME:
                infoArray = query(TABLE_NAME,String.format("par_phone='%s'", phone));
                break;
            case TABLE_teacher:
                infoArray = query(TABLE_teacher,String.format("tea_phone='%s'", phone));
                break;
            case TABLE_reserve:
                infoArray = query(TABLE_reserve,String.format("reserve_parphone='%s'", phone));
                break;
            default:
                break;
        }
        if (infoArray.size() > 0) {
            info = infoArray.get(0);
        }
        return info;
    }

}
