package com.aphoh.courser.util;

import android.view.View;

/**
 * Created by Will on 9/5/15.
 */
public interface ItemClickListener<T> {
    void onItemClick(T obj, View view, int position);
}
