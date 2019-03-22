package com.njcool.lzccommon.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Created by chuan on 2017/8/10.
 */

public abstract class CoolRecycleViewLoadMoreListener extends RecyclerView.OnScrollListener {

    public boolean isLoading = false;//记录正在加载的状态，防止多次请求
    public int lastItemPosition;
    private int topOffset = 0;//列表顶部容差值
    private int bottomOffset = 0;//列表底部容差值

    protected GridLayoutManager gridLayoutManager;
    private LinearLayoutManager layoutManager;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    public CoolRecycleViewLoadMoreListener(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public CoolRecycleViewLoadMoreListener(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public CoolRecycleViewLoadMoreListener(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.staggeredGridLayoutManager = staggeredGridLayoutManager;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        onScrollMore();
        if (isFullAScreen(recyclerView)) {
            //查找最后一个可见的item的position
            if (gridLayoutManager != null) {
                lastItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 ==
                        gridLayoutManager.getItemCount()) {
                    if (!isLoading) {
                        onLoadMore();
                    }
                }
            }
            if (staggeredGridLayoutManager != null) {
                int[] lastVisiblePositions = staggeredGridLayoutManager.findLastVisibleItemPositions(new int[staggeredGridLayoutManager.getSpanCount()]);
                lastItemPosition = getMaxElem(lastVisiblePositions);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 ==
                        staggeredGridLayoutManager.getItemCount()) {
                    if (!isLoading) {
                        onLoadMore();
                    }
                }
            }
            if (layoutManager != null) {
                lastItemPosition = layoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 ==
                        layoutManager.getItemCount()) {
                    if (!isLoading) {
                        onLoadMore();
                    }
                }
            }

        }
    }

    public int getMaxElem(int[] arr) {
        int size = arr.length;
        int maxVal = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            if (arr[i] > maxVal)
                maxVal = arr[i];
        }
        return maxVal;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
    }

    /**
     * 检查是否满一屏
     *
     * @param recyclerView
     * @return
     */
    public boolean isFullAScreen(RecyclerView recyclerView) {
        //获取item总个数，一般用mAdapter.getItemCount()，用mRecyclerView.getLayoutManager().getItemCount()也可以
        //获取当前可见的item view的个数,这个数字是不固定的，随着recycleview的滑动会改变,
        // 比如有的页面显示出了6个view，那这个数字就是6。此时滑一下，第一个view出去了一半，后边又加进来半个view，此时getChildCount()
        // 就是7。所以这里可见item view的个数，露出一半也算一个。
        int visiableItemCount = recyclerView.getChildCount();
        if (visiableItemCount > 0) {
            View lastChildView = recyclerView.getChildAt(visiableItemCount - 1);
            //获取第一个childView
            View firstChildView = recyclerView.getChildAt(0);
            int top = firstChildView.getTop();
            int bottom = lastChildView.getBottom();
            //recycleView显示itemView的有效区域的bottom坐标Y
            int bottomEdge = recyclerView.getHeight() - recyclerView.getPaddingBottom() + bottomOffset;
            //recycleView显示itemView的有效区域的top坐标Y
            int topEdge = recyclerView.getPaddingTop() + topOffset;
            //第一个view的顶部小于top边界值,说明第一个view已经部分或者完全移出了界面
            //最后一个view的底部小于bottom边界值,说明最后一个view已经完全显示在界面
            //若满足这两个条件,说明所有子view已经填充满了recycleView,recycleView可以"真正地"滑动
            if (bottom <= bottomEdge && top < topEdge) {
                //满屏的recyceView
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    public abstract void onLoadMore();

    public abstract void onScrollMore();

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }
}
