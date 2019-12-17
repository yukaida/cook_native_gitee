package com.ebanswers.kitchendiary.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.download.library.DownloadImpl;
import com.download.library.DownloadListenerAdapter;
import com.download.library.DownloadingListener;
import com.download.library.Extra;
import com.download.library.Runtime;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonApplication;
import com.hjq.toast.ToastUtils;

import org.json.JSONObject;

import java.io.File;
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

    private void downLoadApk() {
        final File file = new File(Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera");
        if (!file.exists()){
            boolean mkdir = file.mkdir();
        }
        DownloadImpl.getInstance()
                .with(CommonApplication.getInstance())
                .target(new File(Runtime.getInstance().getDir(mContext, true).getAbsolutePath() + "/" + "chufang.apk"), "com.ebanswers.kitchendiary.fileprovider")//自定义路径需指定目录和authority(FileContentProvide),需要相对应匹配才能启动通知，和自动打开文件)
                .setUniquePath(false)//是否唯一路径
                .setForceDownload(true)//不管网络类型
                .setRetry(4)//下载异常，自动重试,最多重试4次
                .setBlockMaxTime(60000L) //以8KB位单位，默认60s ，如果60s内无法从网络流中读满8KB数据，则抛出异常 。
                .setConnectTimeOut(10000L)//连接10超时
                .addHeader("xx","cookie")//添加请求头
                .setDownloadTimeOut(Long.MAX_VALUE)//下载最大时长
                .setOpenBreakPointDownload(true)//打开断点续传
                .setParallelDownload(true)//打开多线程下载
                .autoOpenWithMD5("93d1695d87df5a0c0002058afc0361f1")//校验md5通过后自动打开该文件,校验失败会回调异常
                .autoOpenIgnoreMD5()
                .closeAutoOpen()
                .quickProgress()//快速连续回调进度，默认1.2s回调一次
                .url("http://storage.56iq.net/group1/M00/16/98/CgoKTl3Cq5OAIo-WAH-b0ub501I676.apk")
                .enqueue(new DownloadListenerAdapter() {
                    @Override
                    public void onStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength, Extra extra) {
                        super.onStart(url, userAgent, contentDisposition, mimetype, contentLength, extra);
                        ToastUtils.show("已开启后台下载");
                    }

                    @DownloadingListener.MainThread //加上该注解，自动回调到主线程
                    @Override
                    public void onProgress(String url, long downloaded, long length, long usedTime) {
                        super.onProgress(url, downloaded, length, usedTime);
                        Log.i("qqqqq", " downloaded:" + downloaded + " length:" + length);
                    }

                    @Override
                    public boolean onResult(Throwable throwable, Uri path, String url, Extra extra) {
                        Log.i("qqqqq", " 下载完成" );
                        return super.onResult(throwable, path, url, extra);
                    }
                });

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
//                downLoadApk();
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
