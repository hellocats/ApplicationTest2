package com.example.administrator.applicationtest2.view.test.userDefine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static com.example.administrator.applicationtest2.R.id.list_view;

/**
 * Date:2017-12-29
 * Author:he
 * Description:
 */

public class ListViewDemo  extends BaseClsActivity {
    @BindView(list_view)
    ListView listView;
    private List<ListViewEntity> dataItems = new ArrayList<>();
    private ListViewAdapter viewAdapter;
    private List<Map<String,String>> dataList = new ArrayList<>();
    private Map<String,RuleEntity> rule = new HashMap();

    private List<String> rules = new ArrayList<>();

    private List<List<String>> ruleList = new ArrayList<>();
    private List<String> lineList  = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view_demo);
        rule.put("0",new RuleEntity("sName",1,1));
        rule.put("1",new RuleEntity("dYear",2,1));
        rule.put("2",new RuleEntity("nAge",1,2));
        rule.put("3",new RuleEntity("sHobby",3,1));
        rule.put("4",new RuleEntity("sPlace",4,1));
        rule.put("5",new RuleEntity("sSchool",2,2));
//        rule.put("0","sName,1,1");
//        rule.put("1","dYear,2,1");
//        rule.put("2","nAge,1,2");
//        rule.put("3","sHobby,3,1");
//        rule.put("4","sPlace,4,1");
//        rule.put("5","sSchool,2,2");

        rules.add("sName,1,1");
        rules.add("dYear,2,1");
        rules.add("nAge,1,2");
        rules.add("sHobby,3,1");
        rules.add("sPlace,4,1");
        rules.add("sSchool,2,2");
        for (int i = 0; i < rules.size() ; i++) {
            String lineCount1= rules.get(i).split(",")[1];
            if(!lineList.contains(lineCount1)){
                lineList.add(lineCount1);
            }
        }
        Collections.sort(lineList);
//        Collections.sort(rules,new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                String lineCount1= o1.split(",")[1];
//                String lineCount2 = o2.split(",")[1];
//                return Integer.valueOf(lineCount1) -Integer.valueOf(lineCount2);
//            }
//        });


        //模拟数据库
        for (int i = 0; i < 15; i++) {
            ListViewEntity ue = new ListViewEntity();//给实体类赋值
            ue.setsName("小米"+i);
            String dYear = String.valueOf(199+i);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
            Date date = null;
            try {
                date = sdf.parse(dYear);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            ue.setdYear(date);
            ue.setnAge(i);
            ue.setsHobby("打球"+i);
            ue.setsPlace("地方"+i);
            ue.setsSchool("大学"+i);
            dataItems.add(ue);

            Map<String,String> rule1 = new HashMap();
            rule1.put("sName",ue.getsName());
            rule1.put("dYear", String.valueOf(ue.getdYear()));
            rule1.put("nAge", String.valueOf(ue.getnAge()));
            rule1.put("sHobby",ue.getsHobby());
            rule1.put("sPlace",ue.getsPlace());
            rule1.put("sSchool",ue.getsSchool());
            dataList.add(rule1);
        }
        listView.setAdapter(viewAdapter = new ListViewAdapter(ListViewDemo.this,dataItems,dataList,rule));
    }

}
