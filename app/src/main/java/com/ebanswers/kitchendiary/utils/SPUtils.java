package com.ebanswers.kitchendiary.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.constant.AppConstant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by HQ on 2016/3/18.
 */
public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "demo_data";

    public static boolean getIsLogin() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin", (Boolean) false);
    }

    public static void  setLogin(boolean islogin) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isLogin", islogin);
        SharedPreferencesCompat.apply(editor);
    }


    public static boolean getIsModifyPw() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("isModifyPw", (Boolean) false);
    }

    public static void  setModifyPw(boolean isModifyPw) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isModifyPw", isModifyPw);
        SharedPreferencesCompat.apply(editor);
    }


    public static boolean getIsBindMobile() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("isBindMobile", (Boolean) false);
    }

    public static void  setBindMobile(boolean isBindMobile) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isBindMobile", isBindMobile);
        SharedPreferencesCompat.apply(editor);
    }

    public static boolean getRemenberPass() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("remenberPass",true);
    }

    public static void  setRemenberPass(boolean remenberPass) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("remenberPass", remenberPass);
        SharedPreferencesCompat.apply(editor);
    }


    public static boolean getAgreement() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("agreement",true);
    }

    public static void  setAgreement(boolean agreement) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("agreement", agreement);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 指纹识别是否开启
     * @return
     */
    public static boolean isFingerPrint() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean("isFingerPrint", (Boolean) false);
    }

    public static void  setFingerPrint(boolean isFingerPrint) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("isFingerPrint", isFingerPrint);
        SharedPreferencesCompat.apply(editor);
    }

    public static void  setPayParam(String pay_price, String pay_type, String pay_purpose, String pay_no) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("pay_price", pay_price);
        editor.putString("pay_type", pay_type); //付款方式|分割 1:美家币支付 2:余额支付 3:微信 4:支付宝 5:银联 6:货到付款 7:积分换购
        editor.putString("pay_purpose", pay_purpose);  // 1下单支付 2钱包充值 3美家币充值
        editor.putString("pay_no", pay_no);
        SharedPreferencesCompat.apply(editor);
    }

    public static String getToken() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString("token", "");
    }

    public static void  setToken(String token) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", token);
        SharedPreferencesCompat.apply(editor);
    }

    public static Double getLatitude() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return Double.parseDouble(sp.getString("latitude", "0"));
    }

    public static void  setLatitude(String latitude) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("latitude", latitude);
        SharedPreferencesCompat.apply(editor);
    }

    public static Double getLongitude() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return  Double.parseDouble(sp.getString("longitude", "0"));
    }

    public static void  setLongitude(String token) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("longitude", token);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 用户Id值
     * @return
     */
    public static String getOperatorCd() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString("operator_cd",CommonApplication.DEFAULT_USER);
    }

    public static String getDefaultUsername() {
        return CommonApplication.DEFAULT_USER;
    }

    public static String getPassword() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getString("password",CommonApplication.DEFAULT_USER);
    }

    public static void  setPassword(String password) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("password", password);
        SharedPreferencesCompat.apply(editor);
    }

    public static void  setOperatorCd(String operatorCd) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("operator_cd", operatorCd);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     * @param key
     * @param object
     */
    public static void put(String key, Object object) {

        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object !=null){

            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(String key, Object defaultObject) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 移除某个key值已经对应的值
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     * @return
     */
    public static Map<String, ?> getAll() {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 存储LIST string
     * @return
     */
    public static void saveStringList(String name, ArrayList<String> list)  {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        // 还原数据
        int size = sp.getInt(name + "_size", 0);
        editor.putInt(name + "_size",0);
        for(int i=0;i< size;i++) {
            editor.remove(name + "_" + i);
        }
        editor.commit();
        // 添加数据
        editor.putInt(name + "_size",list.size());
        for(int i=0;i<list.size();i++) {
            editor.remove(name + "_" + i);
            editor.putString(name + "_" + i, list.get(i));
        }
        editor.commit();
    }
    /**
     * 取LIST string
     * @return
     */
    public static ArrayList<String> getStringList(String name) {
        SharedPreferences sp = CommonApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        int size = sp.getInt(name + "_size", 0);
        ArrayList<String> templist = new ArrayList<>();
        for(int i=0;i< size;i++) {
            templist.add(sp.getString(name + "_" + i, ""));
        }
        return templist;
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     * @author zhy
     *
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();
        /**
         * 反射查找apply的方法
         * @return
         */
        @SuppressWarnings({ "unchecked", "rawtypes" })
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {

            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {

            } catch (IllegalAccessException e) {

            } catch (InvocationTargetException e) {

            }
            editor.commit();
        }
    }


    public static void clearUserInfo() {
        remove("token");
        setLogin(false);
//        setModifyPw(false);
//        setBindMobile(false);
        setOperatorCd(CommonApplication.DEFAULT_USER);
        put(AppConstant.USER_ID, "tmp_user");
        put(AppConstant.USER_NAME, "厨房客人");
        put(AppConstant.USER_ROLE, "");
        put(AppConstant.USER_IMAGE, "");
        put(AppConstant.MOBILE,"");

    }


    public SPUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void put(Context context, String key, Object object) {
        Log.d("SPUtils", "put: "+key+":"+object);
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            if (object!=null)
                editor.putString(key, object.toString());
        }

        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param context
     * @param key
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     *
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param context
     * @param key
     * @return
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @param context
     * @return
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
        return sp.getAll();
    }

}
