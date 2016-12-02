package com.example.administrator.applicationtest2.common;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;

/**
 * Created by Administrator on 2016-12-02.
 */
public class DialogLoading extends Dialog {
    private TextView loadingLabel;

    public DialogLoading(Context context) {
        super(context, R.style.Dialog);
        setContentView(R.layout.com_dialog_loading);
        setCanceledOnTouchOutside(false);
        loadingLabel = (TextView) findViewById(R.id.com_loading_text);
    }

    public void setDialogLabel(String label) {
        loadingLabel.setText(label);
    }
}
