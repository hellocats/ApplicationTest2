package com.example.administrator.applicationtest2.view.test.userDefine;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Date:2017-12-29
 * Author:he
 * Description:
 */

public class ListViewAdapter extends BaseAdapter{
//    private List<ListViewEntity> dataItems; // 绑定的数据集
    private List<Map<String,String>> dataList = new ArrayList<>();
    private Map<String,RuleEntity> rule = new HashMap();
    private LayoutInflater mInflater;
    private Context context;
    private List<RuleEntity> rules;
    public ListViewAdapter(Context context, List<ListViewEntity> data,
                           List<Map<String,String>> dataList,Map<String,RuleEntity> rule,
                           List<RuleEntity> rules) {
        // 得到布局填充服务
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
//        this.dataItems = data;
        this.dataList = dataList;
        this.rule = rule;
        this.rules = rules;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        int count  = 0;
//        count = dataItems.size();//数目
        return count;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        View view;
        Holder holder = null;
        if (convertView==null) {
            //因为getView()返回的对象，adapter会自动赋给ListView
            convertView = mInflater.inflate(R.layout.list_view_demo_item,parent, false);
            holder = new Holder();
            holder.lay = (LinearLayout) convertView.findViewById(R.id.list_view_item_lay);//找到Textviewname
            holder.txt = (TextView) convertView.findViewById(R.id.list_view_item_txt);
            convertView.setTag(holder);
        }else{
//            convertView=convertView;
            holder = (Holder) convertView.getTag();
            Log.i("info","有缓存，不需要重新生成"+position);
        }
//        ListViewEntity row = dataItems.get(position);
//        holder.txt.setText(row.getsName());
        Map<String,String> dataMap  = dataList.get(position);

//        Map<String,Integer> line  = new HashMap<>();
//        for (int i = 0; i < rule.size(); i++) {
//            String value = rule.get(i);
//            String name  = value.split(",")[0];
//            String lineCount = value.split(",")[1];
//            String cloCount = value.split(",")[2];
//
//
//            if(line.containsKey(lineCount)){
//                line.put(lineCount,1);
//            }else{
//                int c = line.get(lineCount);
//                line.remove(lineCount);
//                line.put(lineCount,c+1);
//            }
//        }



        for (int i = 0; i <rules.size() ; i++) {
            String name  = rules.get(i).getsName();
            int lineCount = rules.get(i).getnLine();
            int cloCount = rules.get(i).getnColumn();

        }



        TextView tv = new TextView(context);
        tv.setText("示例文本框1");
        holder.lay.addView(tv);

        TextView tv1 = new TextView(context);
        tv1.setText("示例文本框2");
        holder.lay.addView(tv1);

        LinearLayout layout = new LinearLayout(context);
        LinearLayout.LayoutParams params  = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setLayoutParams(params);
        layout.setOrientation(LinearLayout.HORIZONTAL);

        params.weight = 1;
        TextView tv3 = new TextView(context);
        tv3.setLayoutParams(params);
        tv3.setText("示例文本框3fbdsbds");
        tv3.setEllipsize(TextUtils.TruncateAt.END);
        tv3.setSingleLine(true);
        layout.addView(tv3);

        TextView tv4 = new TextView(context);
        tv4.setText("示例文本框4sdaffsda");
        tv4.setLayoutParams(params);
        tv4.setEllipsize(TextUtils.TruncateAt.END);
        tv4.setSingleLine(true);
        layout.addView(tv4);

        TextView tv5 = new TextView(context);
        tv5.setText("示例文本框512313213213213");
        tv5.setLayoutParams(params);
        tv5.setEllipsize(TextUtils.TruncateAt.END);
        tv5.setSingleLine(true);
        layout.addView(tv5);

        holder.lay.addView(layout);

        return convertView;
    }
    @Override
    public long getItemId(int position) {//取在列表中与指定索引对应的行id
        return 0;
    }
    @Override
    public Object getItem(int position) {//获取数据集中与指定索引对应的数据项
        return null;
    }
    private class Holder {
        LinearLayout lay;
        TextView txt;
    }
}
