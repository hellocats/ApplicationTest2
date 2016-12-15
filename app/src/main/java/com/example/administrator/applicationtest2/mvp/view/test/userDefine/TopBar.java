package com.example.administrator.applicationtest2.mvp.view.test.userDefine;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;


/**
 * 自定义topbar
 * Created by Allen on 16/4/10.
 */
public class TopBar extends RelativeLayout {

    /**
     * 左边图片的属性值
     */
    private Drawable leftImg;
    /**
     * 左边图片是否显示   默认显示
     */
    private boolean leftImgShow;

    /**
     * 右边图片的属性值
     */
    private Drawable rightImg;
    /**
     * 右边图片是否显示  默认显示
     */
    private boolean rightImgShow;
    /**
     * 右边第二个图片的属性值
     */
    private Drawable rightImg2;
    /**
     * 右边第二个图片是否显示  默认不显示
     */
    private boolean rightImg2Show;
    /**
     * 中间图片属性值
     */
    private Drawable centerImg;
    /**
     * 中间图片是否显示  默认不现实
     */
    private boolean centerImgShow;
    /**
     * 中间文字属性
     */
    private String centerText;
    /**
     * 中间文字字体大小属性
     */
    private float centerTextSize;
    /**
     * 中间文字字体颜色属性
     */
    private int centerTextColor;
    /**
     * 中间文字是否显示   默认显示
     */
    private boolean centerTextShow;

    /**
     * topbar背景
     */
    private int backgroundColor;

    private ImageView mLeftImg;
    private ImageView mRightImg;
    private ImageView mRightImg2;

    private ImageView mCenterImg;

    public TextView mCenterTV;

    public LayoutParams leftParams, rightParams, rightParams2, titleParams, centerParams;

    private OnTopBarClickListener mTopBarClickListener;
    private Context mContext;

    public TopBar(final Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAttr(context, attrs);
        setLeftView(context);
        setRightView(context);
        setRightView2(context);
        setCenterImgView(context);
        setCenterTextView(context);

        if (backgroundColor != 0) {
            setBackgroundColor(backgroundColor);
        } else {
            setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

    /**
     * 获取自定义属性值
     *
     * @param context
     * @param attrs
     */
    private void getAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Topbar);

        leftImg = typedArray.getDrawable(R.styleable.Topbar_leftIcon);
        leftImgShow = typedArray.getBoolean(R.styleable.Topbar_leftIconShow, true);

        rightImg = typedArray.getDrawable(R.styleable.Topbar_rightIcon);
        rightImgShow = typedArray.getBoolean(R.styleable.Topbar_rightIconShow, true);

        rightImg2 = typedArray.getDrawable(R.styleable.Topbar_rightIcon2);
        rightImg2Show = typedArray.getBoolean(R.styleable.Topbar_rightIcon2Show, false);

        centerImg = typedArray.getDrawable(R.styleable.Topbar_centerBackground);
        centerImgShow = typedArray.getBoolean(R.styleable.Topbar_centerBackgroundShow, false);

        centerText = typedArray.getString(R.styleable.Topbar_centerText);
        centerTextSize = typedArray.getDimension(R.styleable.Topbar_centerTextSize, 16);
        centerTextColor = typedArray.getColor(R.styleable.Topbar_centerTextColor, 0);
        centerTextShow = typedArray.getBoolean(R.styleable.Topbar_centerTextShow, true);

        backgroundColor = typedArray.getColor(R.styleable.Topbar_backgroundColor, 0);

        typedArray.recycle();
    }

    /**
     * 设置中间文字显示的textview
     *
     * @param context
     */
    private void setCenterTextView(Context context) {
        mCenterTV = new TextView(context);

        mCenterTV.setText(centerText);
        mCenterTV.setTextColor(centerTextColor);
        mCenterTV.setTextSize(centerTextSize);
        mCenterTV.setGravity(Gravity.CENTER);
        titleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        titleParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);

        addView(mCenterTV, titleParams);

        setCenterTextShow(centerTextShow);
    }

    /**
     * 设置中间的imageview
     *
     * @param context
     */
    private void setCenterImgView(Context context) {
        mCenterImg = new ImageView(context);

        mCenterImg.setImageDrawable(centerImg);
        centerParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        centerParams.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        addView(mCenterImg, centerParams);

        if (centerImgShow) {
            mCenterImg.setVisibility(VISIBLE);
        } else {
            mCenterImg.setVisibility(GONE);
        }
        setCenterIconShow(centerImgShow);
    }

    /**
     * 设置右边第一个view
     *
     * @param context
     */
    private void setRightView(Context context) {
        mRightImg = new ImageView(context);
        mRightImg.setId(R.id.topbar_leftImg_id);
        mRightImg.setImageDrawable(rightImg);
        mRightImg.setPadding(0, 0, 20, 0);
        mRightImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopBarClickListener != null) {
                    mTopBarClickListener.rightIconClickListener();
                }
            }
        });
        rightParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        addView(mRightImg, rightParams);

        setRightIconShow(rightImgShow);
    }

    /**
     * 设置右边第二个view
     *
     * @param context
     */
    private void setRightView2(Context context) {
        mRightImg2 = new ImageView(context);

        mRightImg2.setImageDrawable(rightImg);
        mRightImg2.setPadding(0, 0, 20, 0);
        mRightImg2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopBarClickListener != null) {
                    mTopBarClickListener.rightIconClickListener2();
                }
            }
        });
        rightParams2 = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        rightParams2.addRule(RelativeLayout.CENTER_IN_PARENT, TRUE);
        rightParams2.addRule(RelativeLayout.LEFT_OF, R.id.topbar_leftImg_id);

        addView(mRightImg2, rightParams2);

        setRightIconShow2(rightImg2Show);
    }

    /**
     * 设置左边view
     *
     * @param context
     */
    private void setLeftView(Context context) {
        mLeftImg = new ImageView(context);

        mLeftImg.setImageDrawable(leftImg);
        mLeftImg.setPadding(20, 0, 0, 0);
        mLeftImg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mTopBarClickListener != null) {
                    mTopBarClickListener.LeftIconClickListener();
                }
            }
        });

        leftParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        leftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        addView(mLeftImg, leftParams);

        setLeftIconShow(leftImgShow);

    }

    /**
     * 对外提供接口，设置点击事件
     */
    public interface OnTopBarClickListener {
        public void LeftIconClickListener();

        public void rightIconClickListener();

        public void rightIconClickListener2();
    }

    public void setTopBarClickListener(OnTopBarClickListener listener) {
        mTopBarClickListener = listener;
    }

    /**
     * 设置居中标题文字
     *
     * @param text
     * @return
     */
    public TopBar setCenterText(String text) {
        mCenterTV.setText(text);
        return this;
    }

    /**
     * 设置中间文字的字体大小
     *
     * @param size
     * @return
     */
    public TopBar setCenterTextSize(float size) {
        mCenterTV.setTextSize(size);
        return this;
    }

    /**
     * 设置中间文字的字体颜色
     *
     * @param color
     * @return
     */
    public TopBar setCenterTextColor(int color) {
        mCenterTV.setTextColor(color);
        return this;
    }

    /**
     * 设置中间文字是否显示
     *
     * @param show
     * @return
     */
    public TopBar setCenterTextShow(boolean show) {
        if (show) {
            mCenterTV.setVisibility(VISIBLE);
        } else {
            mCenterTV.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置左边图标的icon
     *
     * @param leftImg
     * @return
     */
    public TopBar setLeftIcon(Drawable leftImg) {
        mLeftImg.setImageDrawable(leftImg);
        return this;
    }

    /**
     * 设置左边icon是否显示
     *
     * @param show
     * @return
     */
    public TopBar setLeftIconShow(boolean show) {
        if (show) {
            mLeftImg.setVisibility(VISIBLE);
        } else {
            mLeftImg.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置右边图标的icon
     *
     * @param rightImg
     * @return
     */
    public TopBar setRightIcon(Drawable rightImg) {
        mRightImg.setImageDrawable(rightImg);
        return this;
    }

    /**
     * 设置右边icon是否显示
     *
     * @param show
     * @return
     */
    public TopBar setRightIconShow(boolean show) {
        if (show) {
            mRightImg.setVisibility(VISIBLE);
        } else {
            mRightImg.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置右边第二个图标的icon
     *
     * @param rightImg2
     * @return
     */
    public TopBar setRightIcon2(Drawable rightImg2) {
        mRightImg2.setImageDrawable(rightImg2);
        return this;
    }

    /**
     * 设置右边icon是否显示
     *
     * @param show2
     * @return
     */
    public TopBar setRightIconShow2(boolean show2) {
        if (show2) {
            mRightImg2.setVisibility(VISIBLE);
        } else {
            mRightImg2.setVisibility(GONE);
        }
        return this;
    }

    /**
     * 设置中间图片是否显示
     *
     * @param show
     * @return
     */
    public TopBar setCenterIconShow(boolean show) {
        if (show) {
            mCenterImg.setVisibility(VISIBLE);
        } else {
            mCenterImg.setVisibility(GONE);
        }
        return this;
    }


}
