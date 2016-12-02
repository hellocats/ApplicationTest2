package com.example.administrator.applicationtest2.view.listviewTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.adapter.BaseClsAdapter;
import com.example.administrator.applicationtest2.adapter.ViewHolder;
import com.example.administrator.applicationtest2.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-12-02.
 */
public class ListviewActivity extends BaseClsActivity {
    @BindView(R.id.listview_items)
    ListView listView;
    private BaseAdapter adapter;
    private List<User> lDatas = new ArrayList<User>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getData();
        initController();
    }

    private void init() {
        setContentView(R.layout.test_listview);
        ButterKnife.bind(this);
    }

    private void initController() {
        // 设置适配器
        listView.setAdapter(adapter = new BaseClsAdapter<User>(
                ListviewActivity.this, lDatas, R.layout.test_listview_item)
        {
            @Override
            public void convert(ViewHolder helper, User item)
            {
                helper.setText(R.id.test_listview_name, item.getName());
                helper.setText(R.id.test_listview_phone, String.valueOf(item.getPhone()));
//              helper.getView(R.id.tv_title).setOnClickListener(l)
            }

        });
    }


    public void getData() {
        for (int i = 0; i < 10; i++) {
            User user =new User();
            user.setName("姓名"+i);
            user.setPhone(i);
            lDatas.add(user);
        }
    }
}
