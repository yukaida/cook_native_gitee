package com.ebanswers.kitchendiary.mvp.view.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.common.CommonActivity;
import com.ebanswers.kitchendiary.utils.SPUtils;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author yukaida
 * 我的标签Activity
 */
public class TagActivity extends CommonActivity {

    @BindView(R.id.createTag_title)
    TitleBar createTagTitle;

    boolean hasTagData = false;
    //---------------第一行
    boolean ischecked0 = false;
    boolean ischecked1 = false;
    boolean ischecked2 = false;
    boolean ischecked3 = false;
    //---------------第二行
    boolean ischecked4 = false;
    boolean ischecked5 = false;
    boolean ischecked6 = false;
    boolean ischecked7 = false;
    //----------------第三行
    boolean ischecked8 = false;
    boolean ischecked9 = false;
    boolean ischecked10 = false;
    boolean ischecked11 = false;

    private List<Boolean> stateList = new ArrayList<>();

    @BindView(R.id.tag_imageView)
    ImageView tagImageView;
    @BindView(R.id.tag_imageView1)
    ImageView tagImageView1;
    @BindView(R.id.tag_imageView2)
    ImageView tagImageView2;
    @BindView(R.id.tag_imageView3)
    ImageView tagImageView3;
    @BindView(R.id.tag_imageView4)
    ImageView tagImageView4;
    @BindView(R.id.tag_imageView5)
    ImageView tagImageView5;
    @BindView(R.id.tag_imageView6)
    ImageView tagImageView6;
    @BindView(R.id.tag_imageView7)
    ImageView tagImageView7;
    @BindView(R.id.tag_imageView8)
    ImageView tagImageView8;
    @BindView(R.id.tag_imageView9)
    ImageView tagImageView9;
    @BindView(R.id.tag_imageView10)
    ImageView tagImageView10;
    @BindView(R.id.tag_imageView11)
    ImageView tagImageView11;

    @BindView(R.id.tag_textView_top)
    TextView tagTextViewTop;
    @BindView(R.id.tag_textView_bottom)
    TextView tagTextViewBottom;
    @BindView(R.id.tag_button)
    Button tagButton;

    private String tagState = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tag;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.createTag_title;
    }

    @Override
    protected void initView() {
        //标题栏左中右三个按钮的点击事件
        createTagTitle.setOnTitleBarListener(new OnTitleBarListener() {
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

        //如果sp中存有用户选择的标签信息，则取出将12个状态位重新赋值
        //注意：该项目的基类activity中 启动顺序为initView->initData 不可将数据获取与渲染分别写在initData和initView中，否则会造成渲染界面时为执行获取数据步骤
        if ( (Boolean) SPUtils.get("hasTagData", hasTagData)) {
            ischecked0 = (Boolean) SPUtils.get("ischecked0", ischecked0);
            ischecked1 = (Boolean) SPUtils.get("ischecked1", ischecked0);
            ischecked2 = (Boolean) SPUtils.get("ischecked2", ischecked0);
            ischecked3 = (Boolean) SPUtils.get("ischecked3", ischecked0);
            ischecked4 = (Boolean) SPUtils.get("ischecked4", ischecked0);
            ischecked5 = (Boolean) SPUtils.get("ischecked5", ischecked0);
            ischecked6 = (Boolean) SPUtils.get("ischecked6", ischecked0);
            ischecked7 = (Boolean) SPUtils.get("ischecked7", ischecked0);
            ischecked8 = (Boolean) SPUtils.get("ischecked8", ischecked0);
            ischecked9 = (Boolean) SPUtils.get("ischecked9", ischecked0);
            ischecked10 = (Boolean) SPUtils.get("ischecked10", ischecked0);
            ischecked11 = (Boolean) SPUtils.get("ischecked11", ischecked0);
        }

        if (ischecked0) {
            tagImageView.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked1) {
            tagImageView1.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked2) {
            tagImageView2.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked3) {
            tagImageView3.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked4) {
            tagImageView4.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked5) {
            tagImageView5.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked6) {
            tagImageView6.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked7) {
            tagImageView7.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked8) {
            tagImageView8.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked9) {
            tagImageView9.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked10) {
            tagImageView10.setImageResource(R.drawable.tag_fore);
        }
        if (ischecked11) {
            tagImageView11.setImageResource(R.drawable.tag_fore);
        }


        if (ischecked0 || ischecked1
                || ischecked2
                || ischecked3
                || ischecked4
                || ischecked5
                || ischecked6
                || ischecked7
                || ischecked8
                || ischecked9
                || ischecked10
                || ischecked11
        ) {
            tagButton.setText(getResources().getString(R.string.tag_create_resettag));
        } else {
            tagButton.setText(getResources().getString(R.string.tag_create_tag));
        }

    }

    @Override
    protected void initData() {


    }

    @OnClick({R.id.tag_imageView, R.id.tag_imageView1, R.id.tag_imageView2, R.id.tag_imageView3
            , R.id.tag_imageView4, R.id.tag_imageView5, R.id.tag_imageView6, R.id.tag_imageView7
            , R.id.tag_imageView8, R.id.tag_imageView9, R.id.tag_imageView10, R.id.tag_imageView11,
            R.id.tag_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tag_imageView://0
                if (!ischecked0) {
                    ischecked0 = true;
                    tagImageView.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked0 = false;
                    tagImageView.setImageResource(R.drawable.tag_pic0);
                }
                break;
            case R.id.tag_imageView1://1
                if (!ischecked1) {
                    ischecked1 = true;
                    tagImageView1.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked1 = false;
                    tagImageView1.setImageResource(R.drawable.tag_pic1);
                }
                break;
            case R.id.tag_imageView2://2
                if (!ischecked2) {
                    ischecked2 = true;
                    tagImageView2.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked2 = false;
                    tagImageView2.setImageResource(R.drawable.tag_pic2);
                }
                break;
            case R.id.tag_imageView3://3
                if (!ischecked3) {
                    ischecked3 = true;
                    tagImageView3.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked3 = false;
                    tagImageView3.setImageResource(R.drawable.tag_pic3);
                }
                break;
            case R.id.tag_imageView4://4
                if (!ischecked4) {
                    ischecked4 = true;
                    tagImageView4.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked4 = false;
                    tagImageView4.setImageResource(R.drawable.tag_pic4);
                }
                break;
            case R.id.tag_imageView5://5
                if (!ischecked5) {
                    ischecked5 = true;
                    tagImageView5.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked5 = false;
                    tagImageView5.setImageResource(R.drawable.tag_pic5);
                }
                break;
            case R.id.tag_imageView6://6
                if (!ischecked6) {
                    ischecked6 = true;
                    tagImageView6.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked6 = false;
                    tagImageView6.setImageResource(R.drawable.tag_pic6);
                }
                break;
            case R.id.tag_imageView7://7
                if (!ischecked7) {
                    ischecked7 = true;
                    tagImageView7.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked7 = false;
                    tagImageView7.setImageResource(R.drawable.tag_pic7);
                }
                break;
            case R.id.tag_imageView8://8
                if (!ischecked8) {
                    ischecked8 = true;
                    tagImageView8.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked8 = false;
                    tagImageView8.setImageResource(R.drawable.tag_pic8);
                }
                break;
            case R.id.tag_imageView9://9
                if (!ischecked9) {
                    ischecked9 = true;
                    tagImageView9.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked9 = false;
                    tagImageView9.setImageResource(R.drawable.tag_pic9);
                }
                break;
            case R.id.tag_imageView10://10
                if (!ischecked10) {
                    ischecked10 = true;
                    tagImageView10.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked10 = false;
                    tagImageView10.setImageResource(R.drawable.tag_pic10);
                }
                break;
            case R.id.tag_imageView11://11
                if (!ischecked11) {
                    ischecked11 = true;
                    tagImageView11.setImageResource(R.drawable.tag_fore);
                } else {
                    ischecked11 = false;
                    tagImageView11.setImageResource(R.drawable.tag_pic11);
                }
                break;
            case R.id.tag_button://保存按钮

                Log.d("tag_catch", "onViewClicked: 保存 "
                        + "\n" + ischecked0
                        + "\n" + ischecked1
                        + "\n" + ischecked2
                        + "\n" + ischecked3
                        + "\n" + ischecked4
                        + "\n" + ischecked5
                        + "\n" + ischecked6
                        + "\n" + ischecked7
                        + "\n" + ischecked8
                        + "\n" + ischecked9
                        + "\n" + ischecked10
                        + "\n" + ischecked11
                );

                if (    (Boolean) SPUtils.get("hasTagData", hasTagData)
                ) {//如果有数据且不为空 说明点的是 重置按钮
                    SPUtils.put("ischecked0", false);
                    SPUtils.put("ischecked1", false);
                    SPUtils.put("ischecked2", false);
                    SPUtils.put("ischecked3", false);
                    SPUtils.put("ischecked4", false);
                    SPUtils.put("ischecked5", false);
                    SPUtils.put("ischecked6", false);
                    SPUtils.put("ischecked7", false);
                    SPUtils.put("ischecked8", false);
                    SPUtils.put("ischecked9", false);
                    SPUtils.put("ischecked10", false);
                    SPUtils.put("ischecked11", false);

                    SPUtils.put("hasTagData", false);

                    tagImageView.setImageResource(R.drawable.tag_pic0);
                    tagImageView1.setImageResource(R.drawable.tag_pic1);
                    tagImageView2.setImageResource(R.drawable.tag_pic2);
                    tagImageView3.setImageResource(R.drawable.tag_pic3);
                    tagImageView4.setImageResource(R.drawable.tag_pic4);
                    tagImageView5.setImageResource(R.drawable.tag_pic5);
                    tagImageView6.setImageResource(R.drawable.tag_pic6);
                    tagImageView7.setImageResource(R.drawable.tag_pic7);
                    tagImageView8.setImageResource(R.drawable.tag_pic8);
                    tagImageView9.setImageResource(R.drawable.tag_pic9);
                    tagImageView10.setImageResource(R.drawable.tag_pic10);
                    tagImageView11.setImageResource(R.drawable.tag_pic11);

                    tagButton.setText(getResources().getString(R.string.tag_create_tag));


                } else {//没有数据 则点的是 生成按钮

                    if (ischecked0 || ischecked1
                            || ischecked2
                            || ischecked3
                            || ischecked4
                            || ischecked5
                            || ischecked6
                            || ischecked7
                            || ischecked8
                            || ischecked9
                            || ischecked10
                            || ischecked11
                    ) {
                        SPUtils.put("ischecked0", ischecked0);
                        SPUtils.put("ischecked1", ischecked1);
                        SPUtils.put("ischecked2", ischecked2);
                        SPUtils.put("ischecked3", ischecked3);
                        SPUtils.put("ischecked4", ischecked4);
                        SPUtils.put("ischecked5", ischecked5);
                        SPUtils.put("ischecked6", ischecked6);
                        SPUtils.put("ischecked7", ischecked7);
                        SPUtils.put("ischecked8", ischecked8);
                        SPUtils.put("ischecked9", ischecked9);
                        SPUtils.put("ischecked10", ischecked10);
                        SPUtils.put("ischecked11", ischecked11);
                        SPUtils.put("hasTagData", true);

                        Log.d("tag_catch", "onViewClicked: 保存 "
                                + "\n" + ischecked0
                                + "\n" + ischecked1
                                + "\n" + ischecked2
                                + "\n" + ischecked3
                                + "\n" + ischecked4
                                + "\n" + ischecked5
                                + "\n" + ischecked6
                                + "\n" + ischecked7
                                + "\n" + ischecked8
                                + "\n" + ischecked9
                                + "\n" + ischecked10
                                + "\n" + ischecked11
                        );

                        Toast.makeText(this, "请稍等个性化标签正在生成", Toast.LENGTH_LONG).show();

                        TimerTask task = new TimerTask() {
                            @Override
                            public void run() {
                                /**
                                 *要执行的操作
                                 */
//                        Toast.makeText(TagActivity.this, "已根据你的标签生成个性化推荐", Toast.LENGTH_SHORT).show();
                                tagButton.setText(getResources().getString(R.string.tag_create_resettag));
                                finish();
                            }
                        };
                        Timer timer = new Timer();
                        timer.schedule(task, 3000);//3秒后执行TimeTask的run方法


                    } else {
                        Toast.makeText(this, "请至少选择一个标签", Toast.LENGTH_SHORT).show();


                    }
                }
                break;

        }
    }
}
