package com.adark.gm.mvp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.adark.gm.R;
import com.adark.gm.mvp.ui.holder.UserRecyclerItemHolder;
import com.adark.lib.common.base.BaseHolder;
import com.adark.lib.common.base.DefaultAdapter;
import java.util.List;

/**
 * Created by Shelly on 2017-3-13.
 */

public class UserAdapter extends DefaultAdapter {
    public UserAdapter(List infos) {
        super(infos);
    }

    @Override public BaseHolder getHolder(View v) {
        return new UserRecyclerItemHolder(v);
    }

    @Override public int getLayoutId() {
        return R.layout.user_recycler_list_item;
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
