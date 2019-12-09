package com.ebanswers.kitchendiary.mvp.view.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.adapter.DraftsAdapter;
import com.ebanswers.kitchendiary.bean.DraftItem;
import com.ebanswers.kitchendiary.bean.Drafts;
import com.ebanswers.kitchendiary.bean.DraftsDeleteBack;
import com.ebanswers.kitchendiary.bean.draftsDetail.DraftsDetail;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.network.api.ApiMethods;
import com.ebanswers.kitchendiary.network.observer.MyObserver;
import com.ebanswers.kitchendiary.network.observer.ObserverOnNextListener;
import com.ebanswers.kitchendiary.widget.dialog.DialogBackTip;
import com.google.gson.Gson;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

/**
 * @author yukd
 * 草稿箱
 * */
public class DraftsActivity extends CommonActivity {
    private static final String TAG = "DraftsActivity";
    private Context context;
    @BindView(R.id.drafts_title)
    TitleBar draftsTitle;
    @BindView(R.id.drafts_rv)
    RecyclerView draftsRv;
    private DialogBackTip.Builder builder;

    private List<DraftItem> list = new ArrayList<>();

    private DraftsAdapter draftsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_drafts;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.drafts_title;
    }

    @Override
    protected void initView() {
        draftsAdapter = new DraftsAdapter(R.layout.item_drafts, list);
        draftsTitle.setOnTitleBarListener(new OnTitleBarListener() {
            @Override
            public void onLeftClick(View v) {
                finish();
            }

            @Override
            public void onTitleClick(View v) {
            }

            @Override
            public void onRightClick(View v) {

            }
        });

        draftsAdapter.openLoadAnimation(SCALEIN);//子项加载动画
        // 滑动最后一个Item的时候回调onLoadMoreRequested方法
        draftsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                draftsRv.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        if (mCurrentCounter >= TOTAL_COUNTER) {
//                            //数据全部加载完毕
//                            mQuickAdapter.loadMoreEnd();
//                        } else {
//                            if (isErr) {
//                                //成功获取更多数据
//                                mQuickAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
//                                mCurrentCounter = mQuickAdapter.getData().size();
//                                mQuickAdapter.loadMoreComplete();
//                            } else {
//                                //获取更多数据失败
//                                isErr = true;
//                                Toast.makeText(PullToRefreshUseActivity.this, R.string.network_err, Toast.LENGTH_LONG).show();
//                                mQuickAdapter.loadMoreFail();
//                            }
//                        }
//                        draftsAdapter.addData(DataServer.getSampleData(PAGE_SIZE));
//                                mCurrentCounter = mQuickAdapter.getData().size();
//                        loadDraftsData("oYazTsqC-maRSIlVMXgnG_mcF2Sk");
                        draftsAdapter.loadMoreEnd();
//                        for (int i = 0; i < list.size(); i++) {
//                            draftsAdapter.addData(list.get(i));
//                        }
//
//                        draftsAdapter.loadMoreComplete();
                    }

                }, 2000);
            }
        }, draftsRv);

        draftsRv.setAdapter(draftsAdapter);
        draftsRv.setLayoutManager(new LinearLayoutManager(this));

        draftsAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                DraftItem draftsItem = (DraftItem) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.drafts_item_textView_delete:
                        popupDialog(draftsItem, position);
                        break;
                    case R.id.drafts_item_imageView:
                        //todo 将子项草稿重新添加到编辑界面
                        Toast.makeText(context, "添加回编辑界面", Toast.LENGTH_SHORT).show();

                        getDraftsDetail(draftsItem.getDraft_id(), "oixkIuM2YmeYND1q67WdUrOqza3I");

                        break;
                }
            }
        });
    }


    public void popupDialog(DraftItem draftsItem, int position) {
        if (builder == null){
            builder = new DialogBackTip.Builder(this);
        }

        builder.setTitle("确认删除这条草稿吗？")
                .setLeftText("取消")
                .setRightText("确认")
                .setRightClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "删除", Toast.LENGTH_SHORT).show();
                        //todo 删除这条草稿
                        Log.d(TAG, "onClick: delete draftsitem"+draftsItem.getDraft_id());
                        deleteDraftsItem("oixkIuM2YmeYND1q67WdUrOqza3I",draftsItem.getDraft_id(),position);

                    }
                }).setLeftClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();


    }//------------删除弹窗

    @Override
    protected void initData() {//初始化数据
        loadDraftsData("oixkIuM2YmeYND1q67WdUrOqza3I");
    }

    public void loadDraftsData( String openid) {//草稿箱加载数据---------------
        ObserverOnNextListener<Drafts, Throwable> listener = new ObserverOnNextListener<Drafts, Throwable>() {
            @Override
            public void onNext(Drafts draftsItem) {//从网络上获取的草稿箱信息
//                getView().setMoreData(squareInfo);
                list = draftsItem.getDraft();
                Log.d(TAG, "onNext: " + list.size());

                for (int i = 0; i < list.size(); i++) {
                    draftsAdapter.addData(list.get(i));
                }
                draftsAdapter.loadMoreComplete();
            }

            @Override
            public void onError(Throwable throwable) {
//                getView().netWorkError("后台网络异常");
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };
            ApiMethods.draftsloadmore(new MyObserver<Drafts>(DraftsActivity.this, listener), "json", openid, "123");
    }


    public void deleteDraftsItem( String openid,String draft_id,int position) {//删除草稿箱子项-----------------------
        ObserverOnNextListener<DraftsDeleteBack, Throwable> listener = new ObserverOnNextListener<DraftsDeleteBack, Throwable>() {
            @Override
            public void onNext(DraftsDeleteBack draftsDeleteBack) {
//                getView().setMoreData(squareInfo);
                if (draftsDeleteBack.getCode() == 0) {
                    Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onNext:delete " + draftsDeleteBack.getCode() + draftsDeleteBack.getData());
                    //刷新草稿箱
                    //todo 加入删除动画
                    draftsAdapter.notifyItemRemoved(position);

                }
            }

            @Override
            public void onError(Throwable throwable) {
//                getView().netWorkError("后台网络异常");
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };

        Log.d(TAG, "onNext:delete " + openid + "\n"+draft_id);
        ApiMethods.draftsdelete(new MyObserver<DraftsDeleteBack>(DraftsActivity.this, listener), "delete_one", draft_id, openid);
    }


    public void getDraftsDetail(String draft_id,String openid) {//获取草稿箱子项详细数据-----------------------
        ObserverOnNextListener<DraftsDetail, Throwable> listener = new ObserverOnNextListener<DraftsDetail, Throwable>() {
            @Override
            public void onNext(DraftsDetail draftsDetail) {//从网络上获取的草稿箱信息
                Log.d(TAG, "onNext: test" + draftsDetail.getData().getDesc());
                if (draftsDetail.getCode() == 0) {
                    // 将草稿箱子项的具体数据传递到菜谱编辑界面结束草稿箱activity
                    Intent intent_toSendRepiceActivity = new Intent(DraftsActivity.this, SendRepiceActivity.class);
                    String json_draftsDetail = new Gson().toJson(draftsDetail);
                    Log.d("testneed ", "onNext: "+json_draftsDetail);
                    intent_toSendRepiceActivity.putExtra("json_draftsDetail", json_draftsDetail);
                    setResult(RESULT_OK, intent_toSendRepiceActivity);
                    finish();

                } else {
                    Toast.makeText(context, "未获取到正确数据，请重试", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Toast.makeText(context, "后台网络异常", Toast.LENGTH_SHORT).show();
            }
        };

        ApiMethods.draftsGetDetail(new MyObserver<DraftsDetail>(DraftsActivity.this, listener), "one", draft_id, openid);
    }

}
