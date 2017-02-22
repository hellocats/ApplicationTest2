package com.example.administrator.applicationtest2.common.color;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.color.items.ItemMate;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import butterknife.BindView;

/**
 * Created by hepeng on 2017-02-21.
 */
public class ColorSelect extends BaseClsActivity {
    @BindView(R.id.common_colorselect_btnSure)
    Button btnSure;
    @BindView(R.id.common_colorselect_btnCancel)
    Button btnCancel;

    @BindView(R.id.common_colorselect)
    View colorSelect;
    @BindView(R.id.color_board)
    ColorBoard colorBoard;

    private int color;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.common_colorselect);
            bindEvent();
            colorBoard.setPosition(ColorBoard.LEFT);
            colorBoard.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bindEvent() {
        colorBoard.setIItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(ItemMate mate) {
                color=mate.getColor();
//                Toast.makeText(getBaseContext(), "" + mate.getText(), Toast.LENGTH_SHORT).show();
                colorSelect.setBackgroundColor(mate.getColor() | 0xff000000);
//                colorBoard.show();
            }
        });
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("color", color);
                Intent intent = getIntent();
                intent.putExtras(bundle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED, getIntent());
            }
        });
    }

}
