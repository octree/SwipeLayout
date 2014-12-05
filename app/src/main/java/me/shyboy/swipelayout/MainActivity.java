package me.shyboy.swipelayout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 *使用示例
 */
public class MainActivity extends Activity {

    private SwipeLayoutAdapter mAdapter;
    private ListView mListView;
    private List<String> mData;

    public void insertData(int n)
    {
        mData = new ArrayList<String>();
        for(int i = 0 ; i < n; i++)
        {
            mData.add("hello world ----- " + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        insertData(10);
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

        @Override
        public void setContentView(View contentView, int position, HorizontalScrollView scrollParent) {
            TextView tv = (TextView)contentView.findViewById(R.id.tv);
            tv.setText(_data.get(position));
        }

        @Override
        public void setActionView(View actionView,final int position, final HorizontalScrollView scrollParent) {
            ((Button)actionView.findViewById(R.id.action)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollBack(scrollParent);
                    _data.remove(position);
                    notifyDataSetChanged();
                }
            });

        }

        //重写，设置内容区的


    }

}
