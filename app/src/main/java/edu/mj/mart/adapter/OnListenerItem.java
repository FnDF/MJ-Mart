package edu.mj.mart.adapter;

import edu.mj.mart.model.Supplier;

public interface OnListenerItem<T> {

    void onClickItem(T item);

    default void onCallSupplier(Supplier supplier) {
    }
}
