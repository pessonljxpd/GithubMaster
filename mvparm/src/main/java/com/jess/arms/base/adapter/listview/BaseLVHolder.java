package com.jess.arms.base.adapter.listview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jess.arms.base.adapter.helper.ViewHelper;

/**
 * Created by Shelly on 2017-3-29.
 */

public class BaseLVHolder implements ViewHelper.AbsListView<BaseLVHolder> {

    private Context mContext;
    private View mConvertView;
    private int mPosition;
    private int mLayoutId;

    private SparseArray<View> mViews = new SparseArray<>();
    private SparseArray<View> mConvertViews = new SparseArray<>();

    public BaseLVHolder(Context pContext, int pPosition, ViewGroup pParent, int pLayoutId) {
        mContext = pContext;
        mConvertView = mConvertViews.get(pLayoutId);
        mPosition = pPosition;
        mLayoutId = pLayoutId;

        if (mConvertView == null) {
            mConvertView = LayoutInflater.from(mContext).inflate(pLayoutId, pParent, false);
            mConvertViews.put(mLayoutId, mConvertView);
            mConvertView.setTag(this);
        }
    }

    public <BVH extends BaseLVHolder> BVH getViewHolder(Context pContext, int pPosition, View pConvertView, ViewGroup
            pParent, int pLayoutId) {
        if (pConvertView == null) {
            return (BVH) new BaseLVHolder(pContext, pPosition, pParent, pLayoutId);
        } else {
            BaseLVHolder holder = (BVH) pConvertView.getTag();
            if (holder.getLayoutId() != pLayoutId) {
                holder = new BaseLVHolder(pContext, pPosition, pParent, pLayoutId);
            }
            holder.setPosition(pPosition);
            return (BVH) holder;
        }
    }

    public void setPosition(int pPosition) {
        mPosition = pPosition;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    protected BaseLVHolder() {
    }

    public View getConvertView() {
        return mConvertViews.valueAt(0);
    }

    public View getConvertView(int pLayoutId) {
        return mConvertViews.get(pLayoutId);
    }

    public <V extends View> V getView(int pViewId) {
        View view = mViews.get(pViewId);
        if (view == null) {
            view = mConvertView.findViewById(pViewId);
            mViews.put(pViewId, view);
        }
        return (V) view;
    }

    @Override
    public BaseLVHolder setText(int pViewId, String pValue) {
        TextView view = getView(pViewId);
        view.setText(pValue);
        return this;
    }

    @Override
    public BaseLVHolder setTextColor(int pViewId, int pColor) {
        TextView view = getView(pViewId);
        view.setTextColor(pColor);
        return this;
    }

    @Override
    public BaseLVHolder setTextColorRes(int pViewId, int pColorRes) {
        TextView view = getView(pViewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(mContext.getResources().getColor(pColorRes, null));
        } else {
            view.setTextColor(mContext.getResources().getColor(pColorRes));
        }
        return this;
    }

    @Override
    public BaseLVHolder setImageResource(int pViewId, int pImgResId) {
        ImageView view = getView(pViewId);
        view.setImageResource(pImgResId);
        return this;
    }

    @Override
    public BaseLVHolder setBackgroundColor(int pViewId, int pColor) {
        View view = getView(pViewId);
        view.setBackgroundColor(pColor);
        return this;
    }

    @Override
    public BaseLVHolder setBackgroundColorRes(int pViewId, int pColorRes) {
        View view = getView(pViewId);
        view.setBackgroundResource(pColorRes);
        return this;
    }

    @Override
    public BaseLVHolder setImageDrawable(int pViewId, Drawable pDrawable) {
        ImageView view = getView(pViewId);
        view.setImageDrawable(pDrawable);
        return this;
    }

    @Override
    public BaseLVHolder setImageDrawableRes(int pViewId, int pDrawableRes) {
        Drawable drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = mContext.getResources().getDrawable(pDrawableRes, null);
        } else {
            drawable = mContext.getResources().getDrawable(pDrawableRes);
        }
        return setImageDrawable(pViewId, drawable);
    }

    @Override
    public BaseLVHolder setImageUrl(int pViewId, String pImgUrl) {
        ImageView imageView = getView(pViewId);
        Glide.with(mContext).load(pImgUrl).into(imageView);
        return this;
    }

    public BaseLVHolder setImageUrl(int pViewId, String pImgUrl, int pPlaceHolderRedId) {
        ImageView imageView = getView(pViewId);
        Glide.with(mContext).load(pImgUrl).placeholder(pPlaceHolderRedId).into(imageView);
        return this;
    }

    @Override
    public BaseLVHolder setImageBitmap(int pViewId, Bitmap pImgBitmap) {
        ImageView view = getView(pViewId);
        view.setImageBitmap(pImgBitmap);
        return this;
    }

    @Override
    public BaseLVHolder setVisible(int pViewId, boolean pVisible) {
        View view = getView(pViewId);
        view.setVisibility(pVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public BaseLVHolder setVisible(int pViewId, int pVisible) {
        View view = getView(pViewId);
        view.setVisibility(pVisible);
        return this;
    }

    @Override
    public BaseLVHolder setTag(int pViewId, Object pTag) {
        View view = getView(pViewId);
        view.setTag(pTag);
        return this;
    }

    @Override
    public BaseLVHolder setTag(int pViewId, int pKey, Object pTag) {
        View view = getView(pViewId);
        view.setTag(pKey, pTag);
        return this;
    }

    @Override
    public BaseLVHolder setChecked(int pViewId, boolean pChecked) {
        Checkable view = getView(pViewId);
        view.setChecked(pChecked);
        return this;
    }

    @Override
    public BaseLVHolder setAlpha(int pViewId, float pValue) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            getView(pViewId).setAlpha(pValue);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(pValue, pValue);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(pViewId).startAnimation(alpha);
        }
        return this;
    }

    @Override
    public BaseLVHolder setTypeface(int pViewId, Typeface pTypeface) {
        TextView view = getView(pViewId);
        view.setTypeface(pTypeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    @Override
    public BaseLVHolder setTypeface(Typeface pTypeface, int... pViewIds) {
        for (int viewId : pViewIds) {
            TextView view = getView(viewId);
            view.setTypeface(pTypeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    @Override
    public BaseLVHolder setOnClickListener(int pViewId, View.OnClickListener pListener) {
        View view = getView(pViewId);
        view.setOnClickListener(pListener);
        return this;
    }
}
