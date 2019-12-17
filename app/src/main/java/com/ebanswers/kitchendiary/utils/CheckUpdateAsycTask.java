package com.ebanswers.kitchendiary.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.ebanswers.kitchendiary.R;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Created by lishihui on 2016/10/14.
 */

public class CheckUpdateAsycTask extends AsyncTask<String, Integer, File> {
    private Context mContext;
    private String apkName;
    private volatile boolean isCancel;
    private AlertDialog pd;    //进度条对话框
    private File mFile;
    private Handler handler;
    private ProgressBar progressBar;
    private TextView tv_percent, tv_scale, tv_cancel, tv_title;
    private NetSpeedTimer mNetSpeedTimer;
    private String speed = " ";
    private Handler speedHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case NetSpeedTimer.NET_SPEED_TIMER_DEFAULT:
                     speed = (String)msg.obj;
                    //打印你所需要的网速值，单位默认为kb/s
                    Log.d("speed", "handleMessage: "+speed);
                    break;
                default:
                    break;
            }

        }
    };


    CheckUpdateAsycTask(Context mContext, String apkName, Handler handler) {
        this.mContext = mContext;
        this.apkName = apkName;
        this.handler = handler;

        //创建NetSpeedTimer实例
        mNetSpeedTimer = new NetSpeedTimer(mContext, new NetSpeed(), speedHandler).setDelayTime(1000).setPeriodTime(2000);
        //在想要开始执行的地方调用该段代码
        mNetSpeedTimer.startSpeedTimer();
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        pd = new AlertDialog.Builder(mContext).create();
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        Window window = pd.getWindow();
        View view = View.inflate(mContext, R.layout.progressdialog_layout, null);
        window.setContentView(view);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        progressBar = view.findViewById(R.id.id_pb_progress_progressdialog);//进度条

        tv_percent = view.findViewById(R.id.id_tv_pb_percent);
        tv_scale = view.findViewById(R.id.id_tv_pb_scale);
        tv_cancel = view.findViewById(R.id.id_tv_pb_cancel);
        tv_title = view.findViewById(R.id.id_pb_progress_title);//todo  版本升级进度条 dialog 提示文字
        pd.setCanceledOnTouchOutside(false);
        progressBar.setMax(100);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCancel = true;
                pd.dismiss();
            }
        });

    }

    @Override
    protected File doInBackground(String... params) {
        synchronized (CheckUpdateAsycTask.class) {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                mFile = new File(Environment.getExternalStorageDirectory(), apkName);
            } else {
                mFile = mContext.getExternalCacheDir() == null ? new File(mContext.getCacheDir().getAbsolutePath() + File.separator + apkName) : null;
            }
            URL url;
            FileOutputStream fos = null;
            BufferedInputStream bis = null;
            try {
                if (mFile != null && mFile.exists()) {
                    mFile.delete();
                    mFile.createNewFile();
                }
                url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(80000);
                //获取到文件的大小
                long maxlength = conn.getContentLength();
                Log.d("updata", "doInBackground: " + maxlength);
                InputStream is = conn.getInputStream();

                if (mFile != null) {
                    fos = new FileOutputStream(mFile);
                    bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    int total = 0;
                    while (!isCancel && (len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        total += len;
                        //获取当前下载量
                        int currentPercent = (int) ((total * 1.0f / maxlength) * 100);
                        Log.d("checkUpdate", "currentPercent:" + currentPercent);
                        Log.d("checkUpdate", "hasDown:" + total);
                        Log.d("checkUpdate", "total:" + maxlength);
                        if (!isCancel) {
                            publishProgress(currentPercent, total, (int) maxlength);
                        }
                    }
                    conn.disconnect();
                } else {
                    if (handler != null) {
                        handler.sendEmptyMessage(3);
                    }
                    Log.d("checkUpdate", "没有文件");
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (handler != null) {
                    handler.sendEmptyMessage(3);
                }
                Log.d("checkUpdate", e.getMessage());
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

        return mFile;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (!isCancel) {
            if (values[0] == 100) {
                pd.dismiss();
            }
            progressBar.setProgress(values[0]);

            int totle = values[1];//获取下载了的apk大小
            int max = values[2];//总大小

            double totle_db1 = new BigDecimal((float) totle / 1048576).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            double max_db1 = new BigDecimal((float) max / 1048576).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();


            tv_percent.setText(speed);//更新到进度条
            tv_scale.setText(totle_db1 + "/" + max_db1 + "MB");//更新到进度条后的提示文字
            Log.d("updata", "onProgressUpdate: " + "totle: " + values[1] + "max: " + values[2]);



        }
    }

    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        synchronized (CheckUpdateAsycTask.class) {
            if (!isCancel) {
                if (mFile != null && mFile.exists()) {
                    installApk(file);
                    if (handler != null) {
                        handler.removeCallbacksAndMessages(null);
                    }
                }
            }
        }

        if(null != mNetSpeedTimer){
            mNetSpeedTimer.stopSpeedTimer();
        }


    }

    //安装apk
    private void installApk(File file) {

        Log.i("qqqqqq", "installApk: " + file.toString());
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri data;
        //7.0引入" StrictMode API 政策" , 禁止向应用外公开 file:// URI


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            data = FileProvider.getUriForFile(mContext, "com.ebanswers.kitchendiary.fileprovider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            data = Uri.fromFile(file);
        }
        intent.setDataAndType(data, "application/vnd.android.package-archive");
        if (mContext != null && mContext instanceof Activity) {
            mContext.startActivity(intent);
        } else if (mContext != null) {
            mContext.getApplicationContext().startActivity(intent);
        }
    }




}
