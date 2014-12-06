package me.shyboy.swipelayout;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/*
 *使用示例,
 * 只需要自己定义内容区和操作区的布局。
 * 设置一个继承SwipeLayoutAdapter的适配器
 * 实现setContentView和setActionView方法
 */
public class MainActivity extends Activity {

    private SwipeLayoutAdapter mAdapter;
    private ListView mListView;
    private List<String> mData;
    private static int mCurrentIndex;

    //插入数据
    public void insertData(int n)
    {
        for(int i = 0 ; i < n; i++)
        {
            mData.add("hello world ----- " + mCurrentIndex++);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mData = new ArrayList<String>();
        mCurrentIndex = 0;
        insertData(5);
        mListView = (ListView)findViewById(R.id.listView);
        mAdapter = new MyAdapater(this,R.layout.item_content,R.layout.item_action,mData);
        mListView.setAdapter(mAdapter);
    }

    //适配器
    class MyAdapater extends SwipeLayoutAdapter<String>
    {
        private List<String> _data;
        public MyAdapater(Activity context,int contentViewResourceId,int actionViewResourceId,List<String> objects)
        {
            super(context,contentViewResourceId,actionViewResourceId,objects);
            _data = objects;
        }

        //实现setContentView方法
        @Override
        public void setContentView(View contentView, int position, HorizontalScrollView parent) {
            TextView tv = (TextView)contentView.findViewById(R.id.tv);
            tv.setText(_data.get(position));
        }

        //实现setActionView方法
        @Override
        public void setActionView(View actionView,final int position, final HorizontalScrollView parent) {

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenWidth = dm.widthPixels;
            ViewGroup.LayoutParams lp = actionView.getLayoutParams();
            lp.width = screenWidth / 3 * 2;

            actionView.findViewById(R.id.action).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parent.scrollTo(0, 0);
                    _data.remove(position);
                    notifyDataSetChanged();
                }
            });

            actionView.findViewById(R.id.star).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this,"start item - " + position,Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

}
