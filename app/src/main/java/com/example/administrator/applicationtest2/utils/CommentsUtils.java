package com.example.administrator.applicationtest2.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;


/**
 * Date:2018-02-06
 * Author:he
 * Description:
 */

public class CommentsUtils {
    private static String sVoiceURL;
    private static String sPhoneURL1;
    private static String sPhoneURL2;
    private static String sPhoneURL3;
    private static String sPhoneURL4;
    /**
     * 弹出评论对话框
     */
    public static Dialog showInputComment(Context context, final CommentDialogListener listener) {
        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.comments_input_content);
       /* dialog.findViewById(R.id.input_comment_dialog_container).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        final Spinner sp_node = (Spinner) dialog.findViewById(R.id.comments_sp_node);

        final LinearLayout lay_content = (LinearLayout) dialog.findViewById(R.id.comments_lay_content);
        final EditText edt_content = (EditText) dialog.findViewById(R.id.comments_edt_content);
        lay_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edt_content.requestFocus();
            }
        });
        final ImageView ivSpeak = (ImageView) dialog.findViewById(R.id.comments_iv_voice_ico);
        final ImageView ivAdd1 = (ImageView) dialog.findViewById(R.id.comments_iv_photo1);
        final ImageView ivAdd2 = (ImageView) dialog.findViewById(R.id.comments_iv_photo2);
        final ImageView ivAdd3 = (ImageView) dialog.findViewById(R.id.comments_iv_photo3);
        final ImageView ivAdd4 = (ImageView) dialog.findViewById(R.id.comments_iv_photo4);
        final TextView tv_ok = (TextView) dialog.findViewById(R.id.comments_tv_ok);
        final TextView tv_cancel = (TextView) dialog.findViewById(R.id.comments_tv_cancel);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSure(edt_content.getText().toString(),sVoiceURL,sPhoneURL1,sPhoneURL2,sPhoneURL3,sPhoneURL4);
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ivSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        dialog.show();
        return dialog;
    }

    public interface CommentDialogListener {
        void onSure(String sContent, String sVoiceURL, String sPhoneURL1, String sPhoneURL2, String sPhoneURL3, String sPhoneURL4);
    }

}
