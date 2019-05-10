package com.example.administrator.applicationtest2.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.applicationtest2.R;

import java.io.File;


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
	public static void openFile(Context context, String filePath, Activity activity) {
		Intent intent;
		File file = new File(filePath);
		if (!file.exists()) return;
		/* 取得扩展名 */
		String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
        /* 依扩展名的类型决定MimeType */
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
				end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			intent = getAudioFileIntent(filePath);
		} else if (end.equals("3gp") || end.equals("mp4")) {
			intent = getVideoFileIntent(context, filePath);
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
				end.equals("jpeg") || end.equals("bmp")) {
			intent = getImageFileIntent(context, filePath);
		} else if (end.equals("apk")) {
			intent = getApkFileIntent(context, filePath);
		} else if (end.equals("ppt")) {
			intent = getPptFileIntent(context, filePath);
		} else if (end.equals("xls")) {
			intent = getExcelFileIntent(context, filePath);
		} else if (end.equals("doc")) {
			intent = getWordFileIntent(context, filePath);
		} else if (end.equals("pdf")) {
			intent = getPdfFileIntent(context, filePath);
		} else if (end.equals("chm")) {
			intent = getChmFileIntent(context, filePath);
		} else if (end.equals("txt")) {
			intent = getTextFileIntent(filePath, false);
		} else if (end.equals("docx")) {
			intent = getWordXFileIntent(context, filePath);
		} else if (end.equals("xlsx")) {
			intent = getExcelXFileIntent(context, filePath);
		} else if (end.equals("pptx")) {
			intent = getPptXFileIntent(context, filePath);
		} else {
			intent = getAllIntent(context, filePath);
		}
		try {
			activity.startActivity(intent);
		} catch (Exception e) {
			Toast.makeText(activity, "没有找到能打开文件的程序！", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}
	//Android获取一个用于打开APK文件的intent
	public static Intent getAllIntent(Context context, String param) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "*/*");
		return intent;
	}
	//Android获取一个用于打开APK文件的intent
	public static Intent getApkFileIntent(Context context, String param) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/vnd.android.package-archive");
		return intent;
	}
	//Android获取一个用于打开VIDEO文件的intent
	public static Intent getVideoFileIntent(Context context, String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "video/*");
		return intent;
	}
	//Android获取一个用于打开AUDIO文件的intent
	public static Intent getAudioFileIntent(String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("oneshot", 0);
		intent.putExtra("configchange", 0);
		Uri uri = Uri.fromFile(new File(param));
		intent.setDataAndType(uri, "audio/*");
		return intent;
	}

	//Android获取一个用于打开Html文件的intent
	public static Intent getHtmlFileIntent(String param) {
		Uri uri = Uri.parse(param).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param).build();
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.setDataAndType(uri, "text/html");
		return intent;
	}

	//Android获取一个用于打开图片文件的intent
	public static Intent getImageFileIntent(Context context, String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
			intent.setAction(android.content.Intent.ACTION_VIEW);
		} else {
			uri = Uri.fromFile(new File(param));//获取文件的Uri;
		}

		intent.setDataAndType(uri, "image/*");
		return intent;
	}

	//Android获取一个用于打开PPT文件的intent
	public static Intent getPptFileIntent(Context context, String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
		return intent;
	}

	//Android获取一个用于打开Excel文件的intent
	public static Intent getExcelFileIntent(Context context, String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/vnd.ms-excel");
		return intent;
	}

	//Android获取一个用于打开Word文件的intent
	public static Intent getWordFileIntent(Context context, String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/msword");
		return intent;
	}

	//Android获取一个用于打开CHM文件的intent
	public static Intent getChmFileIntent(Context context, String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/x-chm");
		return intent;
	}

	//Android获取一个用于打开文本文件的intent
	public static Intent getTextFileIntent(String param, boolean paramBoolean) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		if (paramBoolean) {
			Uri uri1 = Uri.parse(param);
			intent.setDataAndType(uri1, "text/plain");
		} else {
			Uri uri2 = Uri.fromFile(new File(param));
			intent.setDataAndType(uri2, "text/plain");
		}
		return intent;
	}

	//Android获取一个用于打开PDF文件的intent
	public static Intent getPdfFileIntent(Context context, String param) {

		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/pdf");
		return intent;
	}

	//Android获取一个用于打开Word2007文件的intent
	public static Intent getWordXFileIntent(Context context, String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
		return intent;
	}

	//Android获取一个用于打开excel2007文件的intent
	public static Intent getExcelXFileIntent(Context context, String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		return intent;
	}

	//Android获取一个用于打开ppt2007文件的intent
	public static Intent getPptXFileIntent(Context context, String param) {
		Intent intent = new Intent("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Uri uri;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			uri = FileProvider.getUriForFile(context, "com.example.administrator.applicationtest2.fileprovider", new File(param));
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
		} else {
			uri = Uri.fromFile(new File(param));
		}
		intent.setDataAndType(uri, "application/vnd.openxmlformats-officedocument.presentationml.presentation");
		return intent;
	}

}
