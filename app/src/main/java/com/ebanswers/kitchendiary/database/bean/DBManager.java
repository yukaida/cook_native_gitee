package com.ebanswers.kitchendiary.database.bean;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ebanswers.kitchendiary.common.CommonApplication;

import java.util.List;

/**
 * @author caixd
 * @date 2019/9/3
 * PS:
 */
public class DBManager {
    public static volatile DBManager instance;
    private KeyDao keyDao;
    private ADDao adDao;

    public static DBManager getInstance() {
        if (instance == null) {
            synchronized (DBManager.class) {
                if (instance == null) {
                    instance = new DBManager();
                }
            }
        }
        return instance;
    }

    private DBManager() {
    }

    public void initDb() {
        DaoMaster.DevOpenHelper keyHelper = new DaoMaster.DevOpenHelper(CommonApplication.getInstance(), "db_key", null);
        SQLiteDatabase keySqLiteDatabase = keyHelper.getWritableDatabase();
        DaoMaster keyDaoMaster = new DaoMaster(keySqLiteDatabase);
        keyDao = keyDaoMaster.newSession().getKeyDao();
        DaoMaster.DevOpenHelper adHelper = new DaoMaster.DevOpenHelper(CommonApplication.getInstance(), "db_ad", null);
        SQLiteDatabase adSqLiteDatabase = adHelper.getWritableDatabase();
        DaoMaster adDaoMaster = new DaoMaster(adSqLiteDatabase);
        adDao = adDaoMaster.newSession().getADDao();
    }

    public void addKey(String keyWords) {
        //先删除重复的历史搜索
        List<Key> list = keyDao.queryBuilder().where(KeyDao.Properties.Key.eq(keyWords)).build().list();
        keyDao.deleteInTx(list);
        keyDao.insert(new Key(null, keyWords));
    }


    public List<Key> getAll() {
        return keyDao.queryBuilder().list();
    }

    public void deleteAllKey() {
        keyDao.deleteAll();
    }

    public List<AD> getAllAds() {
        return adDao.queryBuilder().list();
    }

    public void addAds(AD ad) {
        List<AD> list = adDao.queryBuilder().where(ADDao.Properties.Id.eq(ad.getId())).build().list();
        Log.d("DBManager", "addAds: " + list.size());
        if (list.size() == 0) {
            adDao.insert(ad);
        }
    }

    public void deleteAllAd() {
        adDao.deleteAll();
    }

    public void cleacCache() {
        deleteAllKey();
        deleteAllAd();
    }

}
