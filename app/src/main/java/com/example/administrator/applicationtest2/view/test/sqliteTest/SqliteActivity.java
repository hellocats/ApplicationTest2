package com.example.administrator.applicationtest2.view.test.sqliteTest;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.MySqliteHelper;
import com.example.administrator.applicationtest2.common.ParamDefine;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import butterknife.BindView;

/**
 * Created by hepeng on 2016/12/8.
 */
public class SqliteActivity extends BaseClsActivity implements View.OnClickListener {
    private MySqliteHelper helper;
    private SQLiteDatabase db;
    @BindView(R.id.test_sqlite_main_btnCreate)
    Button btnCreate;
    @BindView(R.id.test_sqlite_main_btnInsert)
    Button btnInsert;
    @BindView(R.id.test_sqlite_main_btnDelete)
    Button btnDelete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_sqlite_main);
        clickEvent();
    }

    private void clickEvent() {
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_sqlite_main_btnCreate:
                /*getWritableDatabase(),getReadableDatabase()
                  默认打开都是可读可写的数据库对象，
                  磁盘已满或数据库本身权限等情况getReadableDatabase()打开是只读的*/
                 db =helper.getWritableDatabase();
                break;
            case R.id.test_sqlite_main_btnInsert:
                 db =helper.getWritableDatabase();
                String sql = "";
//                DbManager.execSQL(db,sql);
                ContentValues values = new ContentValues();
                values.put(ParamDefine._ID,3);
                values.put(ParamDefine.NAME,"张三");
                values.put(ParamDefine.AGE,"12");
                long result =db.insert(ParamDefine.TABLE_NAME,null,values);
                if (result>0) {
                    showToast("插入成功！");
                }
                db.close();
                break;
            case R.id.test_sqlite_main_btnDelete:
                break;
        }
    }
}
