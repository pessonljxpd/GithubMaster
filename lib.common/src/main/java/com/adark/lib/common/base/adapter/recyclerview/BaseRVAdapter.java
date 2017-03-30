package com.adark.lib.common.base.adapter.recyclerview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adark.lib.common.base.adapter.helper.DataHelper;

import java.util.List;

/**
 * Created by Shelly on 2017-3-29.
 */

public abstract class BaseRVAdapter<T> extends RecyclerView.Adapter<BaseRVHolder> implements DataHelper<T> {

    private Context mContext;
    private List<T> mList;
    private int[] mLayoutIds;
    private LayoutInflater mInflater;

    private SparseArray<View> mConvertViews = new SparseArray<>();

    public BaseRVAdapter(Context pContext, List<T> pList, @LayoutRes int... pLayoutIds) {
        mContext = pContext;
        mList = pList;
        mLayoutIds = pLayoutIds;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public BaseRVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType < 0 || viewType > mLayoutIds.length) {
            throw new ArrayIndexOutOfBoundsException("layoutIndex");
        }
        if (mLayoutIds.length == 0) {
            throw new IllegalArgumentException("not layoutId");
        }
        int layoutId = mLayoutIds[viewType];
        View convertView = mConvertViews.get(layoutId);
        if (convertView == null) {
            convertView = mInflater.inflate(layoutId, parent, false);
        }
        BaseRVHolder holder = (BaseRVHolder) convertView.getTag();
        if (holder == null || holder.getLayoutId() != layoutId) {
            holder = new BaseRVHolder(convertView);
            return holder;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseRVHolder holder, int position) {
        T t = mList.get(position);
        onBindViewData(holder, position, t);
    }

    public abstract void onBindViewData(BaseRVHolder pHolder, int pPosition, T pT);

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getLayoutIndex(position, mList.get(position));
    }

    /**
     * 返回layoutId的索引，默认返回第一个
     *
     * @param pPosition
     * @param pT
     * @return
     */
    private int getLayoutIndex(int pPosition, T pT) {
        return 0;
    }

    @Override
    public boolean addAll(List<T> pList) {
        boolean result = false;
        if (mList != null) {
            result = mList.addAll(pList);
            notifyDataSetChanged();
        }
        return result;
    }

    @Override
    public boolean addAll(int pPosition, List<T> pList) {
        boolean result = false;
        if (mList != null) {
            result = mList.addAll(pPosition, pList);
            notifyDataSetChanged();
        }
        return result;
    }

    @Override
    public void add(T pData) {
        if (mList != null) {
            mList.add(pData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void add(int pPosition, T pData) {
        if (mList != null) {
            mList.add(pPosition, pData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void clear() {
        if (mList != null && mList.size() != 0) {
            mList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean contains(T pData) {
        return mList == null ? false : mList.contains(pData);
    }

    @Override
    public T getData(int pIndex) {
        return mList == null ? null : mList.get(pIndex);
    }

    @Override
    public void modify(T pOldData, T pNewData) {
        if (mList != null) {
            modify(mList.indexOf(pOldData), pNewData);
            notifyDataSetChanged();
        }
    }

    @Override
    public void modify(int pIndex, T pNewData) {
        if (mList != null) {
            mList.set(pIndex, pNewData);
            notifyDataSetChanged();
        }
    }

    @Override
    public boolean remove(T pData) {
        boolean result = false;
        if (mList != null) {
            result = mList.remove(pData);
            notifyDataSetChanged();
        }
        return result;
    }

    @Override
    public void remove(int pIndex) {
        if (mList != null) {
            mList.remove(pIndex);
            notifyDataSetChanged();
        }
    }
}
