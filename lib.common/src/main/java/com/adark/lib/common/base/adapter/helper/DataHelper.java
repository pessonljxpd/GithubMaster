package com.adark.lib.common.base.adapter.helper;

import java.util.List;

/**
 * Created by Shelly on 2017-3-29.
 */

public interface DataHelper<T> {

    boolean addAll(List<T> pList);

    boolean addAll(int pPosition, List<T> pList);

    void add(T pData);

    void add(int pPosition, T pData);

    void clear();

    boolean contains(T pData);

    T getData(int pIndex);

    void modify(T pOldData, T pNewData);

    void modify(int pIndex, T pNewData);

    boolean remove(T pData);

    void remove(int pIndex);
}
