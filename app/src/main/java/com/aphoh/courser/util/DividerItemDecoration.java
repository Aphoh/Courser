package com.aphoh.courser.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.aphoh.courser.R;


/**
 * Created by Will on 7/22/15.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private EnabledProperties enabledProperties;

    public DividerItemDecoration(Context context) {
        mDivider = ResourcesCompat.getDrawable(context.getResources(), R.drawable.line_divider, context.getTheme());
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() instanceof EnabledProperties) {
            enabledProperties = ((EnabledProperties) parent.getAdapter());
        }

        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        if (enabledProperties != null && !enabledProperties.areAllItemsEnabled()) {
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);
                int adapterPos = parent.getChildAdapterPosition(child);
                if (enabledProperties.isItemEnabled(adapterPos)) {

                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top + mDivider.getIntrinsicHeight();

                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }
        } else {
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
