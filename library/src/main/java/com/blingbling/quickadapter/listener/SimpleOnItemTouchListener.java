package com.blingbling.quickadapter.listener;

import android.graphics.Rect;
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

                    View clickView = null;

                    for (Iterator<View> it = set.iterator(); it.hasNext(); ) {
                        final View childView = it.next();
                        if (childView.getVisibility() == View.VISIBLE && childView.isEnabled()) {
                            if (inRangeOfView(childView, e)) {

                                if (clickView == null) {
                                    clickView = childView;
                                } else {
                                    if (checkView1InView2(childView, clickView)) {
                                        clickView = childView;
                                    }
                                }
                            }
                        }
                    }

                    if (clickView != null) {
                        final int position = vh.getLayoutPosition() - adapter.getHeaderViewCount();
                        if (isLongClick) {
                            mOnItemLongClickListener.onItemLongClick(vh, clickView, position);
                        } else {
                            if (clickView instanceof TextView) {
                                final TextView tv = (TextView) clickView;
                                if (tv.getMovementMethod() != null) {
                                    return;
                                }
                            }
                            final View itemClickView = clickView;
                            clickView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mOnItemClickListener.onItemClick(vh, itemClickView, position);
                                }
                            });
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

        /**
         * view1 in view2
         *
         * @param view1
         * @param view2
         * @return
         */
        public boolean checkView1InView2(View view1, View view2) {
            int[] location1 = new int[2];
            int[] location2 = new int[2];
            view1.getLocationOnScreen(location1);
            view2.getLocationOnScreen(location2);
            Rect rect1 = new Rect(location1[0], location1[1], location1[0] + view1.getWidth(), location1[1] + view1.getHeight());
            Rect rect2 = new Rect(location2[0], location2[1], location2[0] + view2.getWidth(), location2[1] + view2.getHeight());

            if (rect1.left >= rect2.left
                    && rect1.top >= rect2.top
                    && rect1.right <= rect2.right
                    && rect1.bottom <= rect2.bottom) {
                return true;
            } else {
                return false;
            }
        }

    }
}