package com.ebanswers.kitchendiary.retrofit;

import android.util.Log;
import android.webkit.CookieManager;
import android.widget.Toast;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.LoginResultInfo;
import com.ebanswers.kitchendiary.bean.WechatUserInfo;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.ebanswers.kitchendiary.config.Constans;
import com.ebanswers.kitchendiary.config.PhoneUserConfig;
import com.ebanswers.kitchendiary.config.WechatUserConfig;
import com.ebanswers.kitchendiary.database.bean.AD;
import com.ebanswers.kitchendiary.database.bean.AdBean;
import com.ebanswers.kitchendiary.database.bean.WechatConfig;
import com.ebanswers.kitchendiary.database.bean.WechatRefreshConfig;
import com.ebanswers.kitchendiary.mvp.view.base.RegisterActivity;
import com.ebanswers.kitchendiary.utils.LanguageUtil;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.ebanswers.kitchendiary.utils.ToastCustom;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author Created by lishihui on 2017/1/9.
 */

public class RetrofitTask {
    private static final String TAG = RetrofitTask.class.getSimpleName();

    private static final String TOKEN = "SvycTZu4hMo21A4Fo3KJ53NNwexy3fu8GNcS8J0kiqaQoi0XvgnvXvyv5UhW8nJj_551657047c2d5d0fd8a30e999b4f7b20f5ea568e";
    private static Subscription checkTimer;


    //检查验证码是否正确
    public static void checkCodeRight(String phoneNumber, String checkCode, final CallBack<String> callBack) {
        Observable<String> checkCodeRight;
        if (PhoneUserConfig.isChinaNationCode()) {
            checkCodeRight = RetrofitHelper.getInstance().getService().checkCodeRight(Constans.PHONE_CHECK_CODE, TOKEN, phoneNumber, checkCode);
        } else {
            checkCodeRight = RetrofitHelper.getInstance().getService().checkInternationalCodeRight(Constans.INTERNATIONAL_PHONE_CHECK_CODE, TOKEN, phoneNumber, checkCode, String.valueOf(PhoneUserConfig.getNationCode()));
        }
        checkCodeRight.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (callBack != null) {
                            callBack.result(s);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Toast.makeText(CommonApplication.getInstance(), "验证失败", Toast.LENGTH_SHORT).show();
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }
                });
    }

    /**
     * 手机号邮箱登录成功之后获取用户信息
     *
     * @param phoneNumber
     * @param callBack
     */
    public static void postPhoneUserInfo(final String phoneNumber, final CallBack<LoginResultInfo> callBack) {
        HashMap<String, String> map = new HashMap<>();
        map.put("access_token", "SvycTZu4hMo21A4Fo3KJ5");
        map.put("phone", phoneNumber);
        map.put("source", Constans.factoryId);
        RetrofitHelper.getInstance().getService().postUserInfo("http://wechat.53iq.com/tmp/user/info", map)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<LoginResultInfo>() {
            @Override
            public void call(LoginResultInfo s) {
                Log.d(TAG, "call: " + s.getMsg());
                if (s != null) {
                    WechatUserConfig.clear(CommonApplication.getInstance());
                    //重置极光推送alias
                    //切换用户后需要清空cookie, 否则我的界面用户名不更新
                    CookieManager manager = CookieManager.getInstance();
                    manager.removeAllCookie();
                    PhoneUserConfig.setPhoneNumber(phoneNumber);
                    SPUtils.put(CommonApplication.getInstance(), Constans.DEVICE_TOKEN, s.getData().getToken());
                    WechatUserConfig.setWechatPublicOpenId(CommonApplication.getInstance(), s.getMsg());
                    Log.d("RetrofitTask", "login result: token:" + s.getData().getToken() + ",openid:" + s.getMsg());
                }
                if (callBack != null) {
                    callBack.result(s);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.d(TAG, "call: postPhoneUserInfo");
                ToastCustom.makeText(CommonApplication.getInstance(), R.string.net_error, Toast.LENGTH_SHORT).show();
                if (callBack != null) {
                    callBack.onError();
                }
            }
        });
    }
    public static void getAllAds(String url, final CallBack<List<AD>> callBack) {
        RetrofitHelper.getInstance().getService().getAds(url, "0")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<AdBean>() {
                    @Override
                    public void call(AdBean result) {
                        if (result != null && result.getCode() == 0) {
                            Log.d("RetrofitTask", "wifiConfig call: " + result);
                            if (callBack != null) {
                                callBack.result(result.getData());
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("RetrofitTask", "wifiConfig error: ");
                        throwable.printStackTrace();
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }
                });
    }


    /**
     * 手机号根据国家码判断国内接口还是国外接口
     * 邮箱根据手机系统语言判断国内接口还是国外接口
     *
     * @param loginType   0手机号验证码  1邮箱验证码
     * @param phoneNumber 手机号或邮箱
     * @param callBack
     */
    public static void getPhoneCheckCode(int loginType, String phoneNumber, final CallBack<String> callBack) {
        Observable<String> phoneCheckCode = null;
        if ((loginType == RegisterActivity.TYPE_PHOE && PhoneUserConfig.isChinaNationCode())
                || (loginType == RegisterActivity.TYPE_EMAIL && LanguageUtil.getInstance().isChinease())) {
            phoneCheckCode = RetrofitHelper.getInstance().getService().getPhoneCheckCode(Constans.PHONE_CHECK_CODE, TOKEN, phoneNumber);
        } else if ((loginType == RegisterActivity.TYPE_PHOE && !PhoneUserConfig.isChinaNationCode())
                || (loginType == RegisterActivity.TYPE_EMAIL && !LanguageUtil.getInstance().isChinease())) {
            phoneCheckCode = RetrofitHelper.getInstance().getService().getInternationalPhoneCheckCode(Constans.INTERNATIONAL_PHONE_CHECK_CODE, TOKEN, phoneNumber, String.valueOf(PhoneUserConfig.getNationCode()));
        }
        if (phoneCheckCode == null) {
            return;
        }
        phoneCheckCode.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (callBack != null) {
                            callBack.result(s);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ToastCustom.makeText(CommonApplication.getInstance(), R.string.check_code_get_failed, Toast.LENGTH_SHORT).show();
                        if (callBack != null) {
                            callBack.onError();
                        }
                        cancelCodeTimer();
                    }
                });
        startCodeTimer();
    }
    public static void cancelCodeTimer() {
        if (checkTimer != null && !checkTimer.isUnsubscribed()) {
            checkTimer.unsubscribe();
            checkTimer = null;
        }
    }

    public static void startCodeTimer() {
        if (checkTimer != null && !checkTimer.isUnsubscribed()) {
            checkTimer.unsubscribe();
            checkTimer = null;
        }
        checkTimer = Observable.timer(60, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.d("RetrofitTask", "call: " + aLong);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });
    }
    //修改密码
    public static void changePassword(String phoneNumber, String password, final CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().accountManager("https://oven.53iq.com/api/account", "modify", phoneNumber, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (callBack != null) {
                    callBack.result(s);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastCustom.makeText(CommonApplication.getInstance(), R.string.change_failed, Toast.LENGTH_SHORT).show();
                if (callBack != null) {
                    callBack.onError();
                }
            }
        });
    }


    public static void registerAccount(String phoneNumber, String password, final
    CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().accountManager(Constans.ACCOUNT_MANAGER, "register", phoneNumber, password).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (callBack != null) {
                    callBack.result(s);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                ToastCustom.makeText(CommonApplication.getInstance(), R.string.register_failed, Toast.LENGTH_SHORT).show();
                if (callBack != null) {
                    callBack.onError();
                }
            }
        });
    }

    public static void accountLogin(String phoneNumber, String password, final CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().accountManager(Constans.ACCOUNT_MANAGER, "login", phoneNumber, password).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (callBack != null) {
                    callBack.result(s);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
//                ToastCustom.makeText(KitchenDiaryApplication.getInstance(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                if (callBack != null) {
                    callBack.onError();
                }
            }
        });
    }

    //判断验证码是否正确
    public static void isCodeRight(String phone, String email,String code, final CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().isCodeRight(Constans.PHONE_EMAIL_BIND, "bind", email, phone,code).subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (callBack != null) {
                    callBack.result(s);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
//                ToastCustom.makeText(KitchenDiaryApplication.getInstance(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                if (callBack != null) {
                    callBack.onError();
                }
            }
        });
    }

    //判断用户是否绑定邮箱
    public static void Bind (String phone, final CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().isOrNotBindEmail(Constans.PHONE_EMAIL_ISORNOTBIND,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        if (callBack != null) {
                            callBack.result(s);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
//                ToastCustom.makeText(KitchenDiaryApplication.getInstance(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                        if (callBack != null) {
                            callBack.onError();
                        }
                    }
                });
    }
    //获取绑定邮箱的验证码
    public static void getBindCode(String phone, String email, final CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().getBindcode(Constans.PHONE_EMAIL_GETCHECKCODE, "get_code", email, phone,"en-US ","kitchen diary register mail").subscribeOn(Schedulers.io()).observeOn
                (AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                if (callBack != null) {
                    callBack.result(s);
                }
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                throwable.printStackTrace();
//                ToastCustom.makeText(KitchenDiaryApplication.getInstance(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                if (callBack != null) {
                    callBack.onError();
                }
            }
        });
    }



    public static void getAccessToken(String url, final CallBack<WechatConfig> callBack) {
        RetrofitHelper.getInstance().getService().getAccessToken(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WechatConfig>() {
                    @Override
                    public void call(WechatConfig wechatConfig) {
                        if (callBack != null)
                            callBack.result(wechatConfig);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("RetrofitTask", "getAccessToken:" + throwable.getMessage());
                        if (callBack != null)
                            callBack.onError();
                    }
                });

    }

    public static void getUserInfo(String url, final CallBack<WechatUserInfo> callBack) {
        RetrofitHelper.getInstance().getService().getUserInfo(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WechatUserInfo>() {
                    @Override
                    public void call(WechatUserInfo wechatUserInfo) {
                        if (callBack != null)
                            callBack.result(wechatUserInfo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("RetrofitTask", "getAccessToken:" + throwable.getMessage());
                        if (callBack != null)
                            callBack.onError();
                    }
                });

    }



    public static void postUserInfo(WechatUserInfo wechatUserInfo, final CallBack<LoginResultInfo> callBack) {
        HashMap<String, String> map = new HashMap<>();
        if (wechatUserInfo != null) {
            map.put("access_token", "SvycTZu4hMo21A4Fo3KJ5");
            map.put("openid", wechatUserInfo.getOpenid());
            map.put("nickname", wechatUserInfo.getNickname());
            map.put("sex", String.valueOf(wechatUserInfo.getSex()));
            map.put("province", wechatUserInfo.getProvince());
            map.put("city", wechatUserInfo.getCity());
            map.put("country", wechatUserInfo.getCountry());
            map.put("headimgurl", wechatUserInfo.getHeadimgurl());
            map.put("unionid", wechatUserInfo.getUnionid());
            map.put("source", Constans.factoryId);
            Log.d("RetrofitTask", "postUserInfo: " + map.toString());
            RetrofitHelper.getInstance().getService().postUserInfo("http://wechat.53iq.com/tmp/user/info", map)
                    .timeout(10, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<LoginResultInfo>() {
                        @Override
                        public void call(LoginResultInfo s) {
                            Log.d("RetrofitTask", "postUserInfo: next");
                            if (callBack != null) {
                                callBack.result(s);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            Log.d("RetrofitTask", "postUserInfo: error");
                            throwable.printStackTrace();
                            if (callBack != null) {
                                callBack.onError();
                            }
                        }
                    });
        }
    }

    public static void getRefreshAccessToken(String url, final CallBack<WechatRefreshConfig> callBack) {
        RetrofitHelper.getInstance().getService().getRefreshAccessToken(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<WechatRefreshConfig>() {
                    @Override
                    public void call(WechatRefreshConfig wechatConfig) {
                        if (callBack != null)
                            callBack.result(wechatConfig);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("RetrofitTask", "getAccessToken:" + throwable.getMessage());
                        if (callBack != null)
                            callBack.onError();
                    }
                });

    }
    public static void isExpireAccessToken(String url, final CallBack<String> callBack) {
        RetrofitHelper.getInstance().getService().isExpireAccessToken(url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String str) {
                        if (callBack != null)
                            callBack.result(str);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d("RetrofitTask", "getAccessToken:" + throwable.getMessage());
                        if (callBack != null)
                            callBack.onError();
                    }
                });

    }











    public static boolean isCanSendCode() {
        return !(checkTimer != null && !checkTimer.isUnsubscribed());
    }


    public interface CallBack<T> {
        void result(T s);

        void onError();
    }


}
