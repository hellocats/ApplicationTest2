package com.example.administrator.applicationtest2.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.color.ColorBoard;
import com.example.administrator.applicationtest2.common.color.IItemClickListener;
import com.example.administrator.applicationtest2.common.color.items.ItemMate;


/**
 * Created by hepeng on 2017-02-21.
 */
public class ColorSelectDialog extends Dialog {

    Context context;
    private String title;//标题
    private int mInitialColor;//初始颜色
    private OnColorChangedListener mListener;
    private ColorBoard mColorBoard;
    private Button btnSure;
    private Button btnCancel;
    private int selectedColor;

    public ColorSelectDialog(Context context, String title, OnColorChangedListener listener) {
        super(context);
        this.context = context;
        mListener = listener;
//        mInitialColor = initialColor;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common_colorselect);
        mColorBoard = (ColorBoard) findViewById(R.id.color_board);
        mColorBoard.setPosition(ColorBoard.RIGHT);
//            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//            mColorBoard.setLayoutParams(lp);
        mColorBoard.show();
        setTitle(title);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = ScreenUtils.getScreenWidth(context);
        params.height = ScreenUtils.getScreenHeight(context);
        btnSure = ((Button) findViewById(R.id.common_colorselect_btnSure));
        btnCancel = ((Button) findViewById(R.id.common_colorselect_btnCancel));
        bindEvent();
    }

    private void bindEvent() {
        btnSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null) {
                    mListener.colorChanged(selectedColor);
                    ColorSelectDialog.this.dismiss();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorSelectDialog.this.dismiss();
            }
        });
        mColorBoard.setIItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClick(ItemMate mate) {
                selectedColor=mate.getColor() | 0xff000000;
                mColorBoard.setBackgroundColor(selectedColor);
            }
        });
    }
    public interface OnColorChangedListener {
        /**
         * 回调函数
         * @param color 选中的颜色
         */
        void colorChanged(int color);
    }
}
