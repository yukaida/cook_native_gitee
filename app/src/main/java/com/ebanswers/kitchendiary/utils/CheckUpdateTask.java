package com.ebanswers.kitchendiary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanswers.kitchendiary.R;

import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author Created by lishihui on 2016/7/18.
 */
public class CheckUpdateTask {
    private String alias;
    private String versionCode;
    private String apk_url;
    private String updateContent;
    private String isMust;
    private String server_url;
    private Context mContext;
    private MyHandler handler;
    private final int UPDATA_CLIENT = 1;
    private final int GET_UNDATAINFO_ERROR = 2;
    private final int DOWN_ERROR = 3;
    private final int NONEED_UPDATE = 4;
    private final int DEFAULT_INSTALL = 5;
    private final int HAVE_IGNORE = 6;
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    public CheckUpdateTask(Context context, String server_url) {
        this.server_url = server_url + "?v=" + System.currentTimeMillis();
        this.mContext = context;
        this.handler = new MyHandler(context);
    }

    public void run(final boolean isShow, final CallBack callBack) {
        HttpUtils.doGetAsync(server_url, new HttpUtils.CallBack() {
            @Override
            public void onRequestComplete(HttpResponse result) {
                Log.d("CheckUpdateTask", "onRequestComplete: "+result);
                if (callBack != null) {
                    callBack.success();
                }
                try {
                    JSONObject jsonObject = new JSONObject(result.getBodyToString());
                    alias = jsonObject.getString("alias");
                    versionCode = jsonObject.getString("versionCode");
                    apk_url = jsonObject.getString("url");
                    updateContent = jsonObject.getString("updateContent");
                    isMust = jsonObject.getString("must");
                    int code = Integer.parseInt(versionCode);
//                        handler.sendEmptyMessage(version);
                    if (code > getVersionCode(mContext)) {
                        if (isMust.equals("no")) {
                            String vc = (String) (SPUtils.get(mContext, "versionCode", "-1"));
                            if (!(vc.equals(versionCode)))
                                handler.sendEmptyMessage(UPDATA_CLIENT);
                            else
                                handler.sendEmptyMessage(HAVE_IGNORE);
                        } else {
                            handler.sendEmptyMessage(DEFAULT_INSTALL);
                        }
                    } else {
                        if (isShow)
                            handler.sendEmptyMessage(NONEED_UPDATE);
                    }
                } catch (Exception e) {
                    handler.sendEmptyMessage(GET_UNDATAINFO_ERROR);
                }
            }

            @Override
            public void onError(Exception e) {
                if (callBack != null) {
                    callBack.error();
                }
                handler.sendEmptyMessage(GET_UNDATAINFO_ERROR);
            }
        });

    }

    public void destroy() {
        if (mContext != null) {
            mContext = null;
            if (!scheduledExecutorService.isShutdown()) {
                scheduledExecutorService.shutdown();
            }
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }


    private class MyHandler extends Handler {
        private WeakReference<Context> mActivity;

        public MyHandler(Context activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message message) {
            Context activity = mActivity.get();
            if (activity != null) {
                switch (message.what) {
                    case UPDATA_CLIENT:
                        //对话框通知用户升级程序
                        showUpdataDialog();
                        break;
                    case GET_UNDATAINFO_ERROR:
                        //服务器超时
                        ToastCustom.makeText(activity, R.string.set_update_error, Toast.LENGTH_SHORT).show();
                        break;
                    case DOWN_ERROR:
                        //下载apk失败
                        ToastCustom.makeText(activity, R.string.set_download_error, Toast.LENGTH_SHORT).show();
                        break;
                    case NONEED_UPDATE:
                        ToastCustom.makeText(activity, R.string.set_version, Toast.LENGTH_SHORT).show();
                        break;
                    case DEFAULT_INSTALL:
                        downLoadApk(true);
                        break;
                    case HAVE_IGNORE:
                        ToastCustom.makeText(activity, R.string.set_update_ignore, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }

        }
    }


    /*
     *
     * 弹出对话框通知用户更新程序
     * 弹出对话框的步骤：
     *  1.创建alertDialog的builder.
     *  2.要给builder设置属性, 对话框的内容,样式,按钮
     *  3.通过builder 创建一个对话框
     *  4.对话框show()出来
     */
    private void showUpdataDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final Window window = dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View RootView = View.inflate(mContext, R.layout.selectdialog_layout, null);
        window.setContentView(RootView);
        TextView content = (TextView) RootView.findViewById(R.id.id_tv_pb_content);
        TextView update = (TextView) RootView.findViewById(R.id.id_tv_pb_sure);
        TextView ignore = (TextView) RootView.findViewById(R.id.id_tv_pb_ignore);
        update.setVisibility(View.VISIBLE);
        ignore.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(updateContent))
            //todo 版本升级 提示 dialog
            content.setText(LanguageUtil.getInstance().getStringById(R.string.app_name)+" " + LanguageUtil.getInstance().getStringById(R.string.set_update_tip)+" ");
        else
            content.setText(updateContent);
        //当点确定按钮时从服务器上下载 新的apk 然后安装   װ
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                downLoadApk(false);//下载apk
            }
        });
        ignore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }

    /*
     * 从服务器中下载APK
     */
    private void downLoadApk(boolean isdefault) {
        String name = mContext.getPackageName();
        new CheckUpdateAsycTask(mContext, name.substring(name.lastIndexOf(".") + 1) + ".apk", handler).execute(apk_url);
    }


    public void checkUpdateTaskRease() {
        if (!scheduledExecutorService.isShutdown()) {
            scheduledExecutorService.isShutdown();
        }
    }

    private int getVersionCode(Context context) {
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),
                    0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionCode;
    }

    public interface CallBack {
        void success();
        void error();
    }
}
