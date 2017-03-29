package com.jess.arms.base.adapter.listview;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.jess.arms.base.adapter.helper.DataHelper;

import java.util.List;

/**
 * Created by Shelly on 2017-3-29.
 */

public abstract class BaseLVAdapter<T> extends BaseAdapter implements DataHelper<T> {

    private Context mContext;
    private List<T> mList;
    private int[] mLayoutIds;
    private LayoutInflater mInflater;

    private BaseLVHolder mHolder = new BaseLVHolder();

    public BaseLVAdapter(Context pContext, List<T> pList) {
        this.mContext = pContext;
        this.mList = pList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public BaseLVAdapter(Context pContext, List<T> pList, @LayoutRes int... pLayoutIds) {
        this.mContext = pContext;
        this.mList = pList;
        this.mLayoutIds = pLayoutIds;
        this.mInflater = LayoutInflater.from(pContext);
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int pPosition) {
        return mList == null ? null : mList.get(pPosition);
    }

    @Override
    public long getItemId(int pPosition) {
        return pPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int layoutId = getViewCheckLayoutId(position);
        mHolder = mHolder.getViewHolder(mContext, position, convertView, parent, layoutId);
        convert(mHolder, position, mList.get(position));
        return mHolder.getConvertView(layoutId);
    }

    public abstract void convert(BaseLVHolder pHolder, int pPosition, T pT);

    private int getViewCheckLayoutId(int pPosition) {
        int layoutId;
        if (mLayoutIds == null || mLayoutIds.length == 0) {
            layoutId = getLayoutId(pPosition, mList.get(pPosition));
        } else {
            layoutId = mLayoutIds[getLayoutIndex(pPosition, mList.get(pPosition))];
        }
        return layoutId;
    }

    /**
     * 若构造函数没有指定layoutIds, 则必须重写该方法
     *
     * @param pPosition
     * @param pItem
     * @return layoutId
     */
    public int getLayoutId(int pPosition, T pItem) {
        return 0;
    }

    /**
     * 指定item布局样式在layoutIds的索引。默认为第一个
     *
     * @param pPosition
     * @param pItem
     * @return
     */
    public int getLayoutIndex(int pPosition, T pItem) {
        return 0;
    }

    @Override
    public boolean addAll(List pList) {
        boolean result = false;
        if (mList != null) {
            result = mList.addAll(pList);
            notifyDataSetChanged();
        }
        return result;
    }

    @Override
    public boolean addAll(int pPosition, List pList) {
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
