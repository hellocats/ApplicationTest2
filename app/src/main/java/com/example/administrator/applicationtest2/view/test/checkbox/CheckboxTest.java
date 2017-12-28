package com.example.administrator.applicationtest2.view.test.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.example.administrator.applicationtest2.R;

/**
 * Date:2017-03-28
 * Author:he
 * Description:
 */
public class CheckboxTest extends Activity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_checkbox);
        RelativeLayout aLayout = (RelativeLayout) this
                .findViewById(R.id.relativeLayout1);
        RelativeLayout.LayoutParams relativeLayoutParams = null;
        int chk_id = 1000;
        CheckBox checkBox = null;

        int rowCount = 8; // 行总数
        int colCount = 4; // 列总数（这里不含第一列）

        for (int i = 0; i < rowCount; i++) { // 控制行
            checkBox = new CheckBox(this);
            checkBox.setId(chk_id += 10);
            relativeLayoutParams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            if (0 == i) { // 如果是第一行第一列，单独处理
                relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            } else {
                relativeLayoutParams.addRule(RelativeLayout.ALIGN_LEFT,
                        chk_id - 10);
                relativeLayoutParams.addRule(RelativeLayout.BELOW, chk_id - 10);
            }
            checkBox.setText(String.valueOf(chk_id));
            checkBox.setLayoutParams(relativeLayoutParams);
            aLayout.addView(checkBox);
            // ******************
            for (int j = 1; j < colCount; j++) { // 控制列
                checkBox = new CheckBox(this);
                checkBox.setId(chk_id + j);
                checkBox.setText(String.valueOf(chk_id + j));
                relativeLayoutParams = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                relativeLayoutParams.addRule(RelativeLayout.RIGHT_OF, chk_id
                        + j - 1);
                relativeLayoutParams.addRule(RelativeLayout.ALIGN_TOP, chk_id
                        + j - 1);
                checkBox.setLayoutParams(relativeLayoutParams);
                aLayout.addView(checkBox);
            }
        }
    }
}
