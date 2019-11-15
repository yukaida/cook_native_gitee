package com.ebanswers.kitchendiary.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ebanswers.kitchendiary.R;
import com.ebanswers.kitchendiary.bean.FoundTopInfo;

/**
 * Create by dongli
 * Create date 2019-10-30
 * desc：发现头部适配器
 */
public class FoundTabAdapter extends BaseQuickAdapter<FoundTopInfo, BaseViewHolder> {


    public FoundTabAdapter() {
        super(R.layout.item_found_tab);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoundTopInfo item) {
        helper.setText(R.id.name_tv,"#" + item.getTopic_content());
    }
}
