package com.adark.lib.common.base.adapter.helper;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.adark.lib.common.base.adapter.listview.BaseLVHolder;
import com.adark.lib.common.base.adapter.recyclerview.BaseRVHolder;

/**
 * Created by Shelly on 2017-3-29.
 */

public interface ViewHelper {
    interface AbsListView<VH extends BaseLVHolder> {
        /**
         * 设置textView文本内容
         *
         * @param pViewId pViewId
         * @param pValue  文本内容
         * @return viewHolder viewHolder viewHolder
         */
        VH setText(int pViewId, String pValue);

        /**
         * 设置textView文本颜色
         *
         * @param pViewId pViewId
         * @param pColor  颜色数值
         * @return viewHolder
         */
        VH setTextColor(int pViewId, int pColor);

        /**
         * 设置textView文本颜色
         *
         * @param pViewId   pViewId
         * @param pColorRes 颜色Id
         * @return viewHolder
         */
        VH setTextColorRes(int pViewId, int pColorRes);

        /**
         * 设置imgView的图片,通过Id设置
         *
         * @param pViewId   pViewId
         * @param pImgResId 图片Id
         * @return viewHolder viewHolder
         */
        VH setImageResource(int pViewId, int pImgResId);

        /**
         * 设置背景颜色
         *
         * @param pViewId pViewId
         * @param pColor  颜色数值
         * @return viewHolder viewHolder
         */
        VH setBackgroundColor(int pViewId, int pColor);

        /**
         * 设置背景颜色
         *
         * @param pViewId   pViewId
         * @param pColorRes 颜色Id
         * @return viewHolder
         */
        VH setBackgroundColorRes(int pViewId, int pColorRes);

        /**
         * 设置img的Drawable
         *
         * @param pViewId   pViewId
         * @param pDrawable pDrawable
         * @return viewHolder
         */
        VH setImageDrawable(int pViewId, Drawable pDrawable);

        /**
         * 设置img的Drawable
         *
         * @param pViewId      pViewId
         * @param pDrawableRes drawableId
         * @return viewHolder
         */
        VH setImageDrawableRes(int pViewId, int pDrawableRes);

        /**
         * 设置img图片路径
         *
         * @param pViewId pViewId
         * @param pImgUrl 图片路径
         * @return viewHolder
         */
        VH setImageUrl(int pViewId, String pImgUrl);

        /**
         * 设置img图片Bitmap
         *
         * @param pViewId    pViewId
         * @param pImgBitmap pImgBitmap
         * @return viewHolder
         */
        VH setImageBitmap(int pViewId, Bitmap pImgBitmap);

        /**
         * 设置控件是否显示
         *
         * @param pViewId  pViewId
         * @param pVisible true(pVisible)/false(gone)
         * @return viewHolder
         */
        VH setVisible(int pViewId, boolean pVisible);

        /**
         * 设置控件是否显示
         *
         * @param pViewId  pViewId
         * @param pVisible pVisible,invisible,gone
         * @return viewHolder
         */
        VH setVisible(int pViewId, int pVisible);

        /**
         * 设置控件的tag
         *
         * @param pViewId pViewId
         * @param pTag    pTag
         * @return viewHolder
         */
        VH setTag(int pViewId, Object pTag);

        /**
         * 设置控件tag
         *
         * @param pViewId pViewId
         * @param pKey    tag的key
         * @param pTag    pTag
         * @return viewHolder
         */
        VH setTag(int pViewId, int pKey, Object pTag);

        /**
         * 设置Checkable控件的选择情况
         *
         * @param pViewId  viewId
         * @param pChecked 选择
         * @return viewHolder
         */
        VH setChecked(int pViewId, boolean pChecked);

        /**
         * 设置控件透明效果
         *
         * @param pViewId pViewId
         * @param pValue  透明值
         * @return viewHolder
         */
        VH setAlpha(int pViewId, float pValue);

        /**
         * 设置TextView字体
         *
         * @param pViewId   pViewId
         * @param pTypeface pTypeface
         * @return viewHolder
         */
        VH setTypeface(int pViewId, Typeface pTypeface);

        /**
         * 设置多个TextView字体
         *
         * @param pTypeface pTypeface
         * @param pViewIds  viewId组合
         * @return viewHolder
         */
        VH setTypeface(Typeface pTypeface, int... pViewIds);

        /**
         * 设置监听
         *
         * @param pViewId
         * @param pListener
         * @return
         */
        VH setOnClickListener(int pViewId, View.OnClickListener pListener);
    }

    interface AbsRecyclerView<VH extends BaseRVHolder> {
        /**
         * 设置textView文本内容
         *
         * @param pViewId pViewId
         * @param pValue  文本内容
         * @return viewHolder viewHolder viewHolder
         */
        VH setText(int pViewId, String pValue);

        /**
         * 设置textView文本颜色
         *
         * @param pViewId pViewId
         * @param pColor  颜色数值
         * @return viewHolder
         */
        VH setTextColor(int pViewId, int pColor);

        /**
         * 设置textView文本颜色
         *
         * @param pViewId   pViewId
         * @param pColorRes 颜色Id
         * @return viewHolder
         */
        VH setTextColorRes(int pViewId, int pColorRes);

        /**
         * 设置imgView的图片,通过Id设置
         *
         * @param pViewId   pViewId
         * @param pImgResId 图片Id
         * @return viewHolder viewHolder
         */
        VH setImageResource(int pViewId, int pImgResId);

        /**
         * 设置背景颜色
         *
         * @param pViewId pViewId
         * @param pColor  颜色数值
         * @return viewHolder viewHolder
         */
        VH setBackgroundColor(int pViewId, int pColor);

        /**
         * 设置背景颜色
         *
         * @param pViewId   pViewId
         * @param pColorRes 颜色Id
         * @return viewHolder
         */
        VH setBackgroundColorRes(int pViewId, int pColorRes);

        /**
         * 设置img的Drawable
         *
         * @param pViewId   pViewId
         * @param pDrawable pDrawable
         * @return viewHolder
         */
        VH setImageDrawable(int pViewId, Drawable pDrawable);

        /**
         * 设置img的Drawable
         *
         * @param pViewId      pViewId
         * @param pDrawableRes drawableId
         * @return viewHolder
         */
        VH setImageDrawableRes(int pViewId, int pDrawableRes);

        /**
         * 设置img图片路径
         *
         * @param pViewId pViewId
         * @param pImgUrl 图片路径
         * @return viewHolder
         */
        VH setImageUrl(int pViewId, String pImgUrl);

        /**
         * 设置img图片Bitmap
         *
         * @param pViewId    pViewId
         * @param pImgBitmap pImgBitmap
         * @return viewHolder
         */
        VH setImageBitmap(int pViewId, Bitmap pImgBitmap);

        /**
         * 设置控件是否显示
         *
         * @param pViewId  pViewId
         * @param pVisible true(pVisible)/false(gone)
         * @return viewHolder
         */
        VH setVisible(int pViewId, boolean pVisible);

        /**
         * 设置控件是否显示
         *
         * @param pViewId  pViewId
         * @param pVisible pVisible,invisible,gone
         * @return viewHolder
         */
        VH setVisible(int pViewId, int pVisible);

        /**
         * 设置控件的tag
         *
         * @param pViewId pViewId
         * @param pTag    pTag
         * @return viewHolder
         */
        VH setTag(int pViewId, Object pTag);

        /**
         * 设置控件tag
         *
         * @param pViewId pViewId
         * @param pKey    tag的key
         * @param pTag    pTag
         * @return viewHolder
         */
        VH setTag(int pViewId, int pKey, Object pTag);

        /**
         * 设置Checkable控件的选择情况
         *
         * @param pViewId  pViewId
         * @param pChecked 选择
         * @return viewHolder
         */
        VH setChecked(int pViewId, boolean pChecked);

        /**
         * 设置控件透明效果
         *
         * @param pViewId pViewId
         * @param pValue  透明值
         * @return viewHolder
         */
        VH setAlpha(int pViewId, float pValue);

        /**
         * 设置TextView字体
         *
         * @param pViewId   pViewId
         * @param pTypeface pTypeface
         * @return viewHolder
         */
        VH setTypeface(int pViewId, Typeface pTypeface);

        /**
         * 设置多个TextView字体
         *
         * @param pTypeface pTypeface
         * @param pViewIds  viewId组合
         * @return viewHolder
         */
        VH setTypeface(Typeface pTypeface, int... pViewIds);

        /**
         * 设置监听
         *
         * @param pViewId
         * @param pListener
         * @return
         */
        VH setOnClickListener(int pViewId, View.OnClickListener pListener);
    }
}
