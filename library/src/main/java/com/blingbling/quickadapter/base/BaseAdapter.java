package com.blingbling.quickadapter.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blingbling.quickadapter.entity.ViewType;
import com.blingbling.quickadapter.entity.SpanSize;
import com.blingbling.quickadapter.listener.OnItemClickListener;
import com.blingbling.quickadapter.listener.OnItemLongClickListener;
import com.blingbling.quickadapter.listener.OnItemTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BlingBling 2016/11/10.
 */

public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_HEADER = 10000001;
    public static final int VIEW_TYPE_FOOTER = 10000002;
    public static final int VIEW_TYPE_LOAD_MORE = 10000003;
    public static final int VIEW_TYPE_EMPTY = 10000004;

    protected List<T> mData;

    private SparseArray<ItemView> mItemViews = new SparseArray<>();

    // listener
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private OnItemTouchListener mOnItemTouchListener;

    private HeaderManager mHeaderManager;
    private FooterManager mFooterManager;
    private LoadMoreManager mLoadMoreManager;
    private EmptyManager mEmptyManager;
    private AnimationManager mAnimationManager;
    private SpanSizeManager mSpanSizeManager;

    public BaseAdapter() {
        mSpanSizeManager = new SpanSizeManager(this);
    }

    public void addItemView(ItemView itemView) {
        if (itemView == null) {
            throw new NullPointerException();
        }
        mItemViews.put(itemView.getViewType(), itemView);
    }

    public final T getItem(int position) {
        return mData.get(position);
    }

    public final List<T> getData() {
        return mData;
    }

    public void setNewData(List<T> data) {
        mData = data;
        if (mAnimationManager != null) {
            mAnimationManager.resetAnimation();
        }
        if (mLoadMoreManager != null) {
            mLoadMoreManager.resetLoadMoreEnd();
        }
        notifyDataSetChanged();
    }

    public void addData(T item) {
        checkData();
        final int index = mData.size();
        mData.add(item);
        notifyItemInserted(getHeaderViewCount() + index);
        compatibilityDataSizeChanged(1);
    }

    public void addData(int index, T item) {
        checkData();
        mData.add(index, item);
        notifyItemInserted(getHeaderViewCount() + index);
        compatibilityDataSizeChanged(1);
    }

    public void addData(List<T> data) {
        checkData();
        final int index = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(getHeaderViewCount() + index, data.size());
        compatibilityDataSizeChanged(data.size());
    }

    public void addData(int index, List<T> data) {
        checkData();
        mData.addAll(index, data);
        notifyItemRangeInserted(getHeaderViewCount() + index, data.size());
        compatibilityDataSizeChanged(data.size());
    }

    public void removeData(int index) {
        checkData();
        mData.remove(index);
        notifyItemRemoved(getHeaderViewCount() + index);
        compatibilityDataSizeChanged(0);
    }

    public void moveData(int fromPosition, int toPosition) {
        if (mData == null) {
            return;
        }
        T data = mData.remove(fromPosition);
        mData.add(toPosition, data);
        notifyItemMoved(getHeaderViewCount() + fromPosition, getHeaderViewCount() + toPosition);
    }

    public void swapData(int fromPosition, int toPosition) {
        final T fromData = mData.get(fromPosition);
        final T toData = mData.set(toPosition, fromData);
        mData.set(fromPosition, toData);

        notifyItemChanged(getHeaderViewCount() + fromPosition);
        notifyItemChanged(getHeaderViewCount() + toPosition);
    }

    private void checkData() {
        if (mData == null) {
            mData = new ArrayList<>();
        }
    }

    public void loadMoreSetNewData(List<T> data, int pageSize) {
        loadMoreSetNewData(data, pageSize, false);
    }

    public void loadMoreSetNewData(List<T> data, int pageSize, boolean loadMoreEnd) {
        setNewData(data);
        autoChangeLoadMoreStatus(data, pageSize, loadMoreEnd);
    }

    public void loadMoreAddData(List<T> data, int pageSize) {
        loadMoreAddData(data, pageSize, false);
    }

    public void loadMoreAddData(List<T> data, int pageSize, boolean loadMoreEnd) {
        if (data != null) {
            addData(data);
        }
        autoChangeLoadMoreStatus(data, pageSize, loadMoreEnd);
    }

    public void loadMoreFail() {
        if (mLoadMoreManager != null) {
            mLoadMoreManager.loadMoreFail();
        }
    }

    private void autoChangeLoadMoreStatus(List<T> data, int pageSize, boolean loadMoreEnd) {
        if (mLoadMoreManager != null) {
            final int dataSize = data == null ? 0 : data.size();
            if (dataSize < pageSize) {
                mLoadMoreManager.loadMoreEnd(loadMoreEnd);
            } else {
                mLoadMoreManager.loadMoreComplete();
            }
        }
    }

    /**
     * compatible getLoadMoreViewCount and getEmptyViewCount may change
     *
     * @param size Need compatible data size
     */
    private void compatibilityDataSizeChanged(int size) {
        if (getDataViewCount() == size) {
            notifyDataSetChanged();
        }
    }

    public final int getDataViewCount() {
        return mData == null ? 0 : mData.size();
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
                return VIEW_TYPE_HEADER;
            } else {
                position = position - numHeaders;
                if (position == 0) {
                    //empty view
                    return VIEW_TYPE_EMPTY;
                } else {
                    //footer view
                    return VIEW_TYPE_FOOTER;
                }
            }
        } else {
            //header,data,footer,load more
            final int numHeaders = getHeaderViewCount();
            if (position < numHeaders) {
                return VIEW_TYPE_HEADER;
            } else {
                position = position - numHeaders;
                final int adapterCount = getDataViewCount();
                if (position < adapterCount) {
                    return getDataViewType(position);
                } else {
                    position = position - adapterCount;
                    final int numFooters = getFooterViewCount();
                    if (position < numFooters) {
                        return VIEW_TYPE_FOOTER;
                    } else {
                        return VIEW_TYPE_LOAD_MORE;
                    }
                }
            }
        }
    }

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final RecyclerView.ViewHolder holder;
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                holder = new BaseAdapter.ViewHolder(mHeaderManager.createLayout(parent));
                break;
            case VIEW_TYPE_FOOTER:
                holder = new BaseAdapter.ViewHolder(mFooterManager.createLayout(parent));
                break;
            case VIEW_TYPE_LOAD_MORE:
                holder = new BaseAdapter.ViewHolder(mLoadMoreManager.createLayout(parent));
                break;
            case VIEW_TYPE_EMPTY:
                holder = new BaseAdapter.ViewHolder(mEmptyManager.createLayout(parent));
                break;
            default:
                holder = createDataViewHolder(parent, viewType);
                break;
        }
        return holder;
    }

    private VH createDataViewHolder(ViewGroup parent, int viewType) {
        final ItemView itemView = mItemViews.get(viewType);
        final View view = LayoutInflater.from(parent.getContext()).inflate(itemView.getLayoutId(), parent, false);
        VH holder = createViewHolder(viewType, view);
        //bind listener
        itemView.bindClickListener(this, holder, mOnItemClickListener);
        itemView.bindLongClickListener(this, holder, mOnItemLongClickListener);
        itemView.bindTouchListener(this, holder, mOnItemTouchListener);
        onCreate(holder, viewType);
        return holder;
    }

    @Override
    public final void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = holder.getItemViewType();
        switch (viewType) {
            case VIEW_TYPE_HEADER:
                mHeaderManager.bindLayout();
                break;
            case VIEW_TYPE_FOOTER:
                mFooterManager.bindLayout();
                break;
            case VIEW_TYPE_LOAD_MORE:
                mLoadMoreManager.bindLayout();
                break;
            case VIEW_TYPE_EMPTY:
                mEmptyManager.bindLayout();
                break;
            default:
                position = position - getHeaderViewCount();
                onBind((VH) holder, getItem(position), position);
                break;
        }
        //auto load more
        if (getLoadMoreViewCount() != 0) {
            mLoadMoreManager.autoLoadMore(position);
        }
    }

    public final int getDataViewType(int position) {
        switch (mItemViews.size()) {
            case 0:
                throw new RuntimeException("You should call addItemView before RecyclerView.setAdapter.");
            case 1:
                return mItemViews.keyAt(0);
            default:
                return getItemViewType(getItem(position), position);
        }
    }

    protected int getItemViewType(T item, int position) {
        if (item instanceof ViewType) {
            return ((ViewType) item).getViewType();
        } else {
            throw new RuntimeException("Parameter item should implement the ViewType interface or rewrite the getItemViewType(T item,int position) method.");
        }
    }

    protected int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        final T item = getItem(position);
        if (item instanceof SpanSize) {
            return ((SpanSize) item).getSpanSize(gridLayoutManager.getSpanCount());
        } else {
            return 1;
        }
    }

    protected abstract VH createViewHolder(int viewType, View view);

    protected void onCreate(VH holder, int viewType) {
    }

    protected abstract void onBind(VH holder, T item, int position);

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mSpanSizeManager.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        mSpanSizeManager.onViewAttachedToWindow(holder);
        if (mAnimationManager != null) {
            if (isDataView(holder.getItemViewType())) {
                mAnimationManager.addAnimation(holder);
            }
        }
    }

    public boolean isDataView(int viewType) {
        switch (viewType) {
            case VIEW_TYPE_HEADER:
            case VIEW_TYPE_FOOTER:
            case VIEW_TYPE_LOAD_MORE:
            case VIEW_TYPE_EMPTY:
                return false;
            default:
                return true;
        }
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.mOnItemLongClickListener = listener;
    }

    public void setOnItemTouchListener(OnItemTouchListener listener) {
        this.mOnItemTouchListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}