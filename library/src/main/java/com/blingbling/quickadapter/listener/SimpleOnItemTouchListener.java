package com.blingbling.quickadapter.listener;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.view.BaseViewHolder;

import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class SimpleOnItemTouchListener implements RecyclerView.OnItemTouchListener {

    private GestureDetectorCompat mGestureDetector;

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public SimpleOnItemTouchListener(OnItemClickListener clickListener) {
        this(clickListener, null);
    }

    public SimpleOnItemTouchListener(OnItemLongClickListener longClickListener) {
        this(null, longClickListener);
    }

    public SimpleOnItemTouchListener(OnItemClickListener clickListener, OnItemLongClickListener longClickListener) {
        mOnItemClickListener = clickListener;
        mOnItemLongClickListener = longClickListener;
    }


    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mGestureDetector == null) {
            mGestureDetector = new GestureDetectorCompat(rv.getContext(),
                    new ItemTouchHelperGestureListener(rv, mOnItemClickListener, mOnItemLongClickListener));
        }
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    private static class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {

        private RecyclerView mRecyclerView;
        private OnItemClickListener mOnItemClickListener;
        private OnItemLongClickListener mOnItemLongClickListener;

        public ItemTouchHelperGestureListener(RecyclerView recyclerView,
                                              OnItemClickListener clickListener,
                                              OnItemLongClickListener longClickListener) {
            this.mRecyclerView = recyclerView;
            mOnItemClickListener = clickListener;
            mOnItemLongClickListener = longClickListener;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if (isScroll()) {
                return true;
            }
            click(false, e);
            return false;
        }


        @Override
        public void onLongPress(MotionEvent e) {
            if (isScroll()) {
                return;
            }
            click(true, e);
        }

        private void click(boolean isLongClick, MotionEvent e) {
            final View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (view != null) {
                final BaseViewHolder vh = (BaseViewHolder) mRecyclerView.getChildViewHolder(view);
                final BaseQuickAdapter adapter = (BaseQuickAdapter) mRecyclerView.getAdapter();
                if (adapter.getViewType(vh.getItemViewType()) == BaseQuickAdapter.VIEW_TYPE_DATA) {
                    final HashSet<View> set = isLongClick ? vh.getLongClickViews() : vh.getClickViews();

                    for (Iterator<View> it = set.iterator(); it.hasNext(); ) {
                        final View childView = it.next();
                        if (childView.isEnabled() && inRangeOfView(childView, e)) {
                            final int position = vh.getLayoutPosition() - adapter.getHeaderViewCount();
                            if (isLongClick) {
                                mOnItemLongClickListener.onItemLongClick(vh, childView, position);
                            } else {
                                if (childView instanceof TextView) {
                                    final TextView tv = (TextView) childView;
                                    if (tv.getLinksClickable()) {
                                        return;
                                    }
                                }
                                childView.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        mOnItemClickListener.onItemClick(vh, childView, position);
                                    }
                                });
                            }
                        }
                    }
                }
            }
        }

        private boolean isScroll() {
            if (mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                return false;
            } else {
                return true;
            }
        }

        public boolean inRangeOfView(View view, MotionEvent ev) {
            int[] location = new int[2];
            if (view.getVisibility() != View.VISIBLE) {
                return false;
            }
            view.getLocationOnScreen(location);
            int x = location[0];
            int y = location[1];
            if (ev.getRawX() < x
                    || ev.getRawX() > (x + view.getWidth())
                    || ev.getRawY() < y
                    || ev.getRawY() > (y + view.getHeight())) {
                return false;
            }
            return true;
        }

    }
}