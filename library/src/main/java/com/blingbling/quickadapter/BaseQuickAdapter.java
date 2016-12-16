package com.blingbling.quickadapter;

import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blingbling.quickadapter.listener.OnItemClickListener;
import com.blingbling.quickadapter.listener.OnItemLongClickListener;
import com.blingbling.quickadapter.manager.AnimationManager;
import com.blingbling.quickadapter.manager.EmptyManager;
import com.blingbling.quickadapter.manager.FooterManager;
import com.blingbling.quickadapter.manager.HeaderManager;
import com.blingbling.quickadapter.manager.LoadMoreManager;
import com.blingbling.quickadapter.manager.SpanSizeManager;
import com.blingbling.quickadapter.view.BaseViewHolder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlingBling on 2016/11/10.
 */

public abstract class BaseQuickAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    public static final int VIEW_TYPE_NOT_FIND = -1;
    public static final int VIEW_TYPE_DATA = 0;
    public static final int VIEW_TYPE_HEADER = 1;
    public static final int VIEW_TYPE_FOOTER = 2;
    public static final int VIEW_TYPE_LOAD_MORE = 3;
    public static final int VIEW_TYPE_EMPTY = 4;

    @IntDef({VIEW_TYPE_NOT_FIND, VIEW_TYPE_DATA, VIEW_TYPE_HEADER, VIEW_TYPE_FOOTER, VIEW_TYPE_LOAD_MORE, VIEW_TYPE_EMPTY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
    }

    private List<T> mDatas;

    private SparseArray<Integer> mLayoutIds = new SparseArray<>(1);

    private HeaderManager mHeaderManager;
    private FooterManager mFooterManager;
    private LoadMoreManager mLoadMoreManager;
    private EmptyManager mEmptyManager;
    private AnimationManager mAnimationManager;
    private SpanSizeManager mSpanSizeManager;


    public BaseQuickAdapter() {
        mSpanSizeManager = new SpanSizeManager(this);
    }

    public T getItem(int position) {
        return mDatas.get(position);
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setNewData(List<T> data) {
        mDatas = data;
        if (mLoadMoreManager != null) {
            mLoadMoreManager.setLoadMoreEnd(false);
        }
        if (mEmptyManager != null) {
            if (getDataViewCount() == 0) {
                mEmptyManager.setEmptyStatus(EmptyManager.STATUS_NO_DATA);
            } else {
                mEmptyManager.setEmptyStatus(EmptyManager.STATUS_EMPTY);
            }
        }
        notifyDataSetChanged();
    }

    private void checkDatas() {
        if (mDatas == null) {
            mDatas = new ArrayList<>();
        }
    }

    public void addData(T item) {
        checkDatas();
        addData(mDatas.size(), item);
    }

    public void addData(int index, T item) {
        checkDatas();
        mDatas.add(index, item);
        notifyItemInserted(getHeaderViewCount() + index);
    }

    public void addData(List<T> data) {
        checkDatas();
        addData(mDatas.size(), data);
    }

    public void addData(int index, List<T> data) {
        checkDatas();
        mDatas.addAll(index, data);
        notifyItemRangeInserted(getHeaderViewCount() + index, data.size());
    }

    public void removeData(int index) {
        checkDatas();
        mDatas.remove(index);
        notifyItemRemoved(getHeaderViewCount() + index);
    }

    public final int getDataViewCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public final int getHeaderViewCount() {
        if (mHeaderManager == null) {
            return 0;
        } else {
            return mHeaderManager.getItemViewCount();
        }
    }

    public final int getFooterViewCount() {
        if (mFooterManager == null) {
            return 0;
        } else {
            return mFooterManager.getItemViewCount();
        }
    }

    public final int getLoadMoreViewCount() {
        if (mLoadMoreManager == null) {
            return 0;
        } else {
            return mLoadMoreManager.getItemViewCount();
        }
    }

    public final int getEmptyViewCount() {
        if (mEmptyManager == null) {
            return 0;
        } else {
            return mEmptyManager.getItemViewCount();
        }
    }

    @Override
    public final int getItemCount() {
        if (getEmptyViewCount() == 1) {
            int count = 1;
            if (mEmptyManager.isHeaderAndEmptyEnable()) {
                count += getHeaderViewCount();
            }
            if (mEmptyManager.isFooterAndEmptyEnable()) {
                count += getFooterViewCount();
            }
            return count;
        } else {
            return getHeaderViewCount() + getDataViewCount() + getFooterViewCount() + getLoadMoreViewCount();
        }
    }

    @Override
    public final int getItemViewType(int position) {
        if (getEmptyViewCount() == 1) {
            final int numHeaders;
            if (mEmptyManager.isHeaderAndEmptyEnable()) {
                numHeaders = getHeaderViewCount();
            } else {
                numHeaders = 0;
            }
            if (position < numHeaders) {
                //header view
                return mHeaderManager.getItemViewType(position);
            } else {
                position = position - numHeaders;
                if (position == 0) {
                    //empty view
                    return mEmptyManager.getItemViewType(position);
                } else {
                    //footer view
                    position = position - 1;
                    return mFooterManager.getItemViewType(position);
                }
            }
        } else {
            //auto load more
            if (mLoadMoreManager != null) {
                mLoadMoreManager.autoLoadMore(position);
            }
            //header,data,footer,load more
            final int numHeaders = getHeaderViewCount();
            if (position < numHeaders) {
                return mHeaderManager.getItemViewType(position);
            } else {
                position = position - numHeaders;
                final int adapterCount = getDataViewCount();
                if (position < adapterCount) {
                    return getDataViewType(position);
                } else {
                    position = position - adapterCount;
                    final int numFooters = getFooterViewCount();
                    if (position < numFooters) {
                        return mFooterManager.getItemViewType(position);
                    } else {
                        return mLoadMoreManager.getItemViewType(position - numFooters);
                    }
                }
            }
        }
    }

    @Override
    public final BaseViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final BaseViewHolder holder;
        switch (getViewType(viewType)) {
            case VIEW_TYPE_DATA:
                holder = createBaseViewHolder(parent, getDataViewLayoutId(viewType));
                onCreate(holder, viewType);
                break;
            case VIEW_TYPE_HEADER:
                holder = mHeaderManager.createViewHolder(parent, viewType);
                break;
            case VIEW_TYPE_FOOTER:
                holder = mFooterManager.createViewHolder(parent, viewType);
                break;
            case VIEW_TYPE_LOAD_MORE:
                holder = mLoadMoreManager.createViewHolder(parent, viewType);
                break;
            case VIEW_TYPE_EMPTY:
                holder = mEmptyManager.createViewHolder(parent, viewType);
                break;
            default:
                throw new RuntimeException("not find viewType:" + viewType);
        }
        return holder;
    }

    private BaseViewHolder createBaseViewHolder(ViewGroup parent, int layoutId) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new BaseViewHolder(this, view);
    }

    @Override
    public final void onBindViewHolder(BaseViewHolder holder, int position) {
        final int viewType = holder.getItemViewType();
        switch (getViewType(viewType)) {
            case VIEW_TYPE_DATA:
                position = position - getHeaderViewCount();
                onBind(holder, getItem(position), position);
                break;
            case VIEW_TYPE_HEADER:
                mHeaderManager.bindViewHolder(holder);
                break;
            case VIEW_TYPE_FOOTER:
                mFooterManager.bindViewHolder(holder);
                break;
            case VIEW_TYPE_LOAD_MORE:
                mLoadMoreManager.bindViewHolder(holder);
                break;
            case VIEW_TYPE_EMPTY:
                mEmptyManager.bindViewHolder(holder);
                break;
        }
    }

    private final int getDataViewType(int position) {
        switch (mLayoutIds.size()) {
            case 0:
                throw new RuntimeException("You should call addItemView before RecyclerView.setAdapter.");
            case 1:
                return mLayoutIds.keyAt(0);
            default:
                return getItemViewType(getItem(position), position);
        }
    }

    private final int getDataViewLayoutId(int viewType) {
        return mLayoutIds.get(viewType);
    }

    @ViewType
    public final int getViewType(int viewType) {
        if (mLayoutIds.get(viewType) != null) {
            return VIEW_TYPE_DATA;
        }
        if (mHeaderManager != null) {
            if (mHeaderManager.getItemView(viewType) != null) {
                return VIEW_TYPE_HEADER;
            }
        }
        if (mFooterManager != null) {
            if (mFooterManager.getItemView(viewType) != null) {
                return VIEW_TYPE_FOOTER;
            }
        }
        if (mLoadMoreManager != null) {
            if (mLoadMoreManager.getItemView(viewType) != null) {
                return VIEW_TYPE_LOAD_MORE;
            }
        }
        if (mEmptyManager != null) {
            if (mEmptyManager.getItemView(viewType) != null) {
                return VIEW_TYPE_EMPTY;
            }
        }
        return VIEW_TYPE_NOT_FIND;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSpanSizeManager.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mSpanSizeManager.onViewAttachedToWindow(holder);
        if (mAnimationManager != null) {
            if (getViewType(holder.getItemViewType()) == VIEW_TYPE_DATA) {
                mAnimationManager.addAnimation(holder);
            }
        }
    }

    //user method

    protected int getItemViewType(T item, int position) {
        throw new RuntimeException("You should rewrite the getItemViewType(T item, int position) method.");
    }

    /**
     * default viewType is layoutId.
     */
    public void addItemView(@LayoutRes int layoutId) {
        addItemView(layoutId, layoutId);
    }

    public void addItemView(int viewType, @LayoutRes int layoutId) {
        mLayoutIds.put(viewType, layoutId);
    }

    public HeaderManager headerManager() {
        if (mHeaderManager == null) {
            mHeaderManager = new HeaderManager(this);
        }
        return mHeaderManager;
    }

    public FooterManager footerManager() {
        if (mFooterManager == null) {
            mFooterManager = new FooterManager(this);
        }
        return mFooterManager;
    }

    public LoadMoreManager loadMoreManager() {
        if (mLoadMoreManager == null) {
            mLoadMoreManager = new LoadMoreManager(this);
        }
        return mLoadMoreManager;
    }

    public EmptyManager emptyManager() {
        if (mEmptyManager == null) {
            mEmptyManager = new EmptyManager(this);
        }
        return mEmptyManager;
    }

    public AnimationManager animationManager() {
        if (mAnimationManager == null) {
            mAnimationManager = new AnimationManager(this);
        }
        return mAnimationManager;
    }

    public SpanSizeManager spanSizeManager() {
        return mSpanSizeManager;
    }

    protected abstract void onCreate(BaseViewHolder holder, int viewType);

    protected abstract void onBind(BaseViewHolder holder, T item, int position);

    // listener

    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return mOnItemLongClickListener;
    }
}