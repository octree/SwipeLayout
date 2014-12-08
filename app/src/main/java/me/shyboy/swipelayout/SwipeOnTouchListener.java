package me.shyboy.swipelayout;

import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

/**
 * Created by foul on 14/12/8.
 */
public class SwipeOnTouchListener implements View.OnTouchListener {

    private static HorizontalScrollView _currentActiveHSV = null;
    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        //获得ViewHolder
        SwipeViewHolder viewHolder = (SwipeViewHolder) v.getTag();
        if(_currentActiveHSV != null && _currentActiveHSV != viewHolder.hSView)
        {
            _currentActiveHSV.smoothScrollTo(0,0);
            _currentActiveHSV = null;
            return true;
        }
        switch (event.getAction())
        {

            case MotionEvent.ACTION_UP:
                //获得HorizontalScrollView滑动的水平方向值.
                int scrollX = viewHolder.hSView.getScrollX();

                //获得操作区域的长度
                int actionW = viewHolder.viewContainer.getChildAt(1).getWidth();

                //注意使用smoothScrollTo,这样效果看起来比较圆滑,不生硬
                //如果水平方向的移动值<操作区域的长度的一半,就复原
                if (scrollX < actionW / 2)
                {
                    viewHolder.hSView.smoothScrollTo(0, 0);
                }
                else//否则的话显示操作区域
                {
                    viewHolder.hSView.smoothScrollTo(actionW, 0);
                    _currentActiveHSV = viewHolder.hSView;
                }
                return true;
        }
        return false;
    }
}
