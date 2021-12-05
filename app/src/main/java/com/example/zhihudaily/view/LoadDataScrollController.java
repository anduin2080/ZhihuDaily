package com.example.zhihudaily.view;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.example.zhihudaily.HomePagerClickListener;
import com.example.zhihudaily.Utils.LogUtils;
import com.example.zhihudaily.HomePagerActivity;


public class LoadDataScrollController extends RecyclerView.OnScrollListener implements SwipeRefreshLayout.OnRefreshListener {

    public static final int LINEAR_LAYOUT = 0;
    public static final int GRID_LAYOUT = 1;
    public static final int STRAGGERED_GRID_LAYOUT = 2;

    private int layoutType = 0;
    private int lastVisiableItemPos;
    private int firstVisibleItemPos;
    private int[] lastPos;
    private boolean isLoadingData = false;
    private HomePagerClickListener listener;

    public LoadDataScrollController(HomePagerActivity listener) {
        this.listener = listener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        //super.onScrollStateChanged(recyclerView, newState);
        LogUtils.e("onScrollStateChanged");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        int visibleCount = layoutManager.getChildCount();
        int totalCount = layoutManager.getItemCount();

        LogUtils.e("visibleCount = " + visibleCount);
        LogUtils.e("totalCount = " + totalCount);
        LogUtils.e("lastVisiableItemPos = " + lastVisiableItemPos);
        LogUtils.e("newState = " + newState);
        LogUtils.e("isLoadingData = " + isLoadingData);

        // 四个条件，分别是
        // 1. 是否有数据，
        // 2. 状态是否是滑动停止状态，
        // 3. 显示的最大条目是否大于整个数据（注意偏移量），
        // 4. 是否正在加载数据
        if (visibleCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                && lastVisiableItemPos >= totalCount - 1 && !isLoadingData) {
            if (listener != null) {
                isLoadingData = true;
                listener.onHomePagerloadMore();
            }
        }
    }

    public void setLoadingDataStatus(boolean isLoadData) {
        this.isLoadingData = isLoadData;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        LogUtils.e("onScrolled");
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            layoutType = LINEAR_LAYOUT;
        } else if (layoutManager instanceof GridLayoutManager) {
            layoutType = GRID_LAYOUT;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            layoutType = STRAGGERED_GRID_LAYOUT;
        }

        switch (layoutType) {
            case LINEAR_LAYOUT:
                lastVisiableItemPos = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPos = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case GRID_LAYOUT:
                lastVisiableItemPos = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                firstVisibleItemPos = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                break;
            case STRAGGERED_GRID_LAYOUT:
                StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                if (lastPos == null) {
                    lastPos = new int[staggeredGridLayoutManager.getSpanCount()];
                }
                staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(lastPos);
                lastVisiableItemPos = findMax(lastPos);
                firstVisibleItemPos = 1;
                break;
        }
        //更新toolbar title
        if (listener != null) {
            listener.onHomePagerUpdateToolBarTitle(firstVisibleItemPos);
        }
    }

    private int findMax(int[] lastPos) {
        int max = lastPos[0];
        for (int value : lastPos) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void onRefresh() {
        if (listener != null) {
            isLoadingData = true;
            listener.onHomePagerRefresh();
        }
    }
}
