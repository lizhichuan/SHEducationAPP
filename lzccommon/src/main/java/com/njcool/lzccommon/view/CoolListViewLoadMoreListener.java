package com.njcool.lzccommon.view;

import android.view.View;
import android.widget.AbsListView;

/**
 * Created by chuan on 2017/8/10.
 */

public abstract class CoolListViewLoadMoreListener implements AbsListView.OnScrollListener {

    public boolean isLoading = false;//记录正在加载的状态，防止多次请求
    private int topOffset = 0;//列表顶部容差值
    private int bottomOffset = 0;//列表底部容差值


    public CoolListViewLoadMoreListener() {
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (isFullAScreen(view)) {
            //查找最后一个可见的item的position
            if (scrollState == SCROLL_STATE_IDLE && view.getLastVisiblePosition() == (view.getCount() - 1)) {
                if (!isLoading) {
                    onLoadMore();
                }
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
    }


    /**
     * 检查是否满一屏
     *
     * @param recyclerView
     * @return
     */
    public boolean isFullAScreen(AbsListView recyclerView) {
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

    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }

    public void setBottomOffset(int bottomOffset) {
        this.bottomOffset = bottomOffset;
    }
}
