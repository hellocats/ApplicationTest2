package com.example.administrator.applicationtest2;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Person person = new Person();
        person.setName("张三");
        person.setPhone(123);
        person.setId(1);
        person.setAmount(1);
        ListView listView = (ListView) this.findViewById(R.id.listview);

        //获取到集合数据
        List<Person> persons = new ArrayList<Person>();
        persons.add(person);
        List<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
        for (Person person1 : persons) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("id", person1.getId());
            item.put("name", person1.getName());
            item.put("phone", person1.getPhone());
            item.put("amount", person1.getAmount());
            data.add(item);
        }
        //创建SimpleAdapter适配器将数据绑定到item显示控件上
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"name", "phone", "amount"}, new int[]{R.id.name, R.id.Phone, R.id.amount});
        //实现列表的显示
        listView.setAdapter(adapter);


        // 添加长按点击弹出选择菜单
        listView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            public void onCreateContextMenu(ContextMenu menu, View v,
                                            ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("选择操作");
                menu.add(0, 0, 0, "更新该条");
                menu.add(0, 1, 0, "删除该条");
            }
        });

    }
    //给菜单项添加事件
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        //info.id得到listview中选择的条目绑定的id
        String id = String.valueOf(info.id);
        switch (item.getItemId()) {
            case 0:
//                updateDialog(id);  //更新事件的方法
                new AlertDialog.Builder(this)
                        .setTitle("标题")
                        .setMessage("简单消息框")
                        .setPositiveButton("确定", null)
                        .show();
                return true;
            case 1:
                //System.out.println("删除"+info.id);
//                deleteData(db,id);  //删除事件的方法
//                showlist();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
