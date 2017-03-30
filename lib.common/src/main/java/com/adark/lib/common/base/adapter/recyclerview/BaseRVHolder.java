package com.adark.lib.common.base.adapter.recyclerview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.adark.lib.common.base.adapter.helper.ViewHelper;
import com.adark.lib.common.widget.imageloader.glide.GlideCircleTransform;
import com.adark.lib.common.widget.imageloader.glide.GlideRoundTransform;

/**
 * Created by Shelly on 2017-3-29.
 */

public class BaseRVHolder extends RecyclerView.ViewHolder implements ViewHelper.AbsRecyclerView<BaseRVHolder> {

    private Context mContext;
    private View mConvertView;
    private int mLayoutId;

    private SparseArray<View> mViews = new SparseArray<>();

    public BaseRVHolder(View pItemView) {
        super(pItemView);
        mConvertView = pItemView;
        mConvertView.setTag(this);
    }

    public BaseRVHolder(ViewGroup pParent, @LayoutRes int pLayoutId) {
        super(initView(pParent.getContext(), pParent, pLayoutId));
        mConvertView = itemView;
        mLayoutId = pLayoutId;
        mConvertView.setTag(this);
    }

    private static View initView(Context pContext, ViewGroup pParent, int pLayoutId) {
        return LayoutInflater.from(pContext).inflate(pLayoutId, pParent, false);
    }

    public Context getContext() {
        return mContext == null ? itemView.getContext() : mContext;
    }

    private <V extends View> V getView(int pViewId) {
        View view = mViews.get(pViewId);
        if (view == null) {
            view = mConvertView.findViewById(pViewId);
            mViews.put(pViewId, view);
        }

        return (V) view;
    }

    public View getConvertView() {
        return mConvertView;
    }

    public int getLayoutId() {
        return mLayoutId;
    }

    public BaseRVHolder setOnItemClickListener(View.OnClickListener pOnClickListener) {
        mConvertView.setOnClickListener(pOnClickListener);
        return this;
    }

    @Override
    public BaseRVHolder setText(int pViewId, String pValue) {
        TextView view = getView(pViewId);
        view.setText(pValue);
        return this;
    }

    @Override
    public BaseRVHolder setTextColor(int pViewId, int pColor) {
        TextView view = getView(pViewId);
        view.setTextColor(pColor);
        return this;
    }

    @Override
    public BaseRVHolder setTextColorRes(int pViewId, int pColorRes) {
        TextView view = getView(pViewId);
        view.setTextColor(ContextCompat.getColor(mContext, pColorRes));
        return this;
    }

    @Override
    public BaseRVHolder setImageResource(int pViewId, int pImgResId) {
        ImageView view = getView(pViewId);
        view.setImageResource(pImgResId);
        return this;
    }

    @Override
    public BaseRVHolder setBackgroundColor(int pViewId, int pColor) {
        View view = getView(pViewId);
        view.setBackgroundColor(pColor);
        return this;
    }

    @Override
    public BaseRVHolder setBackgroundColorRes(int pViewId, int pColorRes) {
        View view = getView(pViewId);
        view.setBackgroundResource(pColorRes);
        return this;
    }

    @Override
    public BaseRVHolder setImageDrawable(int pViewId, Drawable pDrawable) {
        ImageView view = getView(pViewId);
        view.setImageDrawable(pDrawable);
        return this;
    }

    @Override
    public BaseRVHolder setImageDrawableRes(int pViewId, int pDrawableRes) {
        Drawable drawable = ContextCompat.getDrawable(mContext, pDrawableRes);
        return setImageDrawable(pViewId, drawable);
    }

    @Override
    public BaseRVHolder setImageUrl(int pViewId, String pImgUrl) {
        ImageView view = getView(pViewId);
        Glide.with(mContext).load(pImgUrl).into(view);
        return this;
    }

    public BaseRVHolder setImageUrl(int viewId, String imgUrl, int placeHolderRes) {
        ImageView view = getView(viewId);
        Glide.with(mContext).load(imgUrl).placeholder(placeHolderRes).into(view);
        return this;
    }

    public BaseRVHolder setCircleImageUrl(int viewId, String imgUrl, int placeHolderRes) {
        ImageView view = getView(viewId);
        Glide.with(mContext)
             .load(imgUrl)
             .placeholder(placeHolderRes)
             .transform(new GlideCircleTransform(mContext))
             .into(view);
        return this;
    }

    public BaseRVHolder setRoundImageUrl(int viewId, String imgUrl, int placeHolderRes) {
        ImageView view = getView(viewId);
        Glide.with(mContext)
             .load(imgUrl)
             .placeholder(placeHolderRes)
             .transform(new GlideRoundTransform(mContext))
             .into(view);
        return this;
    }

    @Override
    public BaseRVHolder setImageBitmap(int pViewId, Bitmap pImgBitmap) {
        ImageView view = getView(pViewId);
        view.setImageBitmap(pImgBitmap);
        return this;
    }

    @Override
    public BaseRVHolder setVisible(int pViewId, boolean pVisible) {
        View view = getView(pViewId);
        view.setVisibility(pVisible ? View.VISIBLE : View.GONE);
        return this;
    }

    @Override
    public BaseRVHolder setVisible(int pViewId, int pVisible) {
        View view = getView(pViewId);
        view.setVisibility(pVisible);
        return this;
    }

    @Override
    public BaseRVHolder setTag(int pViewId, Object pTag) {
        View view = getView(pViewId);
        view.setTag(pTag);
        return this;
    }

    @Override
    public BaseRVHolder setTag(int pViewId, int pKey, Object pTag) {
        View view = getView(pViewId);
        view.setTag(pKey, pTag);
        return this;
    }

    @Override
    public BaseRVHolder setChecked(int pViewId, boolean pChecked) {
        Checkable view = getView(pViewId);
        view.setChecked(pChecked);
        return this;
    }

    @Override
    public BaseRVHolder setAlpha(int pViewId, float pValue) {
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
    public BaseRVHolder setTypeface(int pViewId, Typeface pTypeface) {
        TextView view = getView(pViewId);
        view.setTypeface(pTypeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    @Override
    public BaseRVHolder setTypeface(Typeface pTypeface, int... pViewIds) {
        for (int viewId : pViewIds) {
            TextView view = getView(viewId);
            view.setTypeface(pTypeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    @Override
    public BaseRVHolder setOnClickListener(int pViewId, View.OnClickListener pListener) {
        View view = getView(pViewId);
        view.setOnClickListener(pListener);
        return this;
    }
}
