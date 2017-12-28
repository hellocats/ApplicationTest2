package com.example.administrator.applicationtest2.view.test.signature;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.ColorPickerDialog;
import com.example.administrator.applicationtest2.common.ColorSelectDialog;
import com.example.administrator.applicationtest2.common.ParamDefine;
import com.example.administrator.applicationtest2.common.SignatureView;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;

/**
 * Created by hepeng on 2017-02-21.
 */
public class Signature extends BaseClsActivity {
    private static final int SELECT_COLOR = 1;
    @BindView(R.id.test_signature_view)
    SignatureView signatureView;
    @BindView(R.id.test_signature_btnSave)
    Button btnSave;
    @BindView(R.id.test_signature_btnClear)
    Button btnClear;
    @BindView(R.id.test_signature_btnFontColor)
    Button btnFontColor;
    @BindView(R.id.test_signature_btnBgColor)
    Button btnBgColor;

    private ColorPickerDialog dialog1;
    private ColorSelectDialog dialog2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_signature_main);
        setEvent();
        clear();
    }

    private void setEvent() {
        btnSave.setOnClickListener(new ClickListener());
        btnClear.setOnClickListener(new ClickListener());
        btnFontColor.setOnClickListener(new ClickListener());
        btnBgColor.setOnClickListener(new ClickListener());
    }

    private void clear() {
        signatureView.setBackColor(Color.WHITE);
        signatureView.setPenColor(Color.BLACK);
    }

    private class ClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_signature_btnSave:
                    if (signatureView.getTouched()) {
                        try {
                            File path = new File(ParamDefine.projectDirectory);
                            if (!path.exists()) {
                                path.mkdirs();
                            }
                            signatureView.save(ParamDefine.projectDirectory + "/signature.png", true, 10);
//                        setResult(100);
//                        finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(Signature.this, "您没有签名~", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.test_signature_btnClear:
                    signatureView.clear();
                    break;
                case R.id.test_signature_btnFontColor:
                    dialog1 =new ColorPickerDialog(Signature.this, "字体颜色", new ColorPickerDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int color) {
                            signatureView.setPenColor(color);
                            signatureView.clear();
                        }
                    });
                    dialog1.show();
                    break;
                case R.id.test_signature_btnBgColor:
                    Drawable background = signatureView.getBackground();
                    int bgcolor = ((ColorDrawable) background).getColor();
                    dialog2 = new ColorSelectDialog(Signature.this,bgcolor, "背景颜色", new ColorSelectDialog.OnColorChangedListener() {
                        @Override
                        public void colorChanged(int color) {
                            signatureView.setBackColor(color);
                            signatureView.clear();
                        }
                    });
                    dialog2.show();
//                    Intent viewBox = new Intent(Signature.this,ColorSelect.class);
//                    startActivityForResult(viewBox,SELECT_COLOR);
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            switch (requestCode) {
                case SELECT_COLOR://阅览箱
                    try {
                        int color = bundle.getInt("color");
                        signatureView.setBackColor(color);
                        signatureView.clear();
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}
