package com.example.administrator.applicationtest2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * 普通样式公共适配器
 */
@SuppressWarnings("unchecked")
public abstract class CommonAdapter<T>  extends BaseAdapter {

    protected Context mContext;//定义成protected,用来给子类访问
    protected List<T> mList;
    protected final int mItemLayoutId;
    protected LayoutInflater mInflater;

    public CommonAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = list;
        this.mItemLayoutId = itemLayoutId;
    }

    protected View.OnClickListener onClickListener;//定义成protected，让子类重写

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (position < mList.size()) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent)
    {
//        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
//                position);
		if (convertView == null)
		{
			return new ViewHolder(mContext, parent, mItemLayoutId, position);
		}
		return (ViewHolder) convertView.getTag();
    }



	public class ViewHolder {
		private final SparseArray<View> mViews;
		private int mPosition;
		private View mConvertView;

		private ViewHolder(Context context, ViewGroup parent, int layoutId,
						   int position)
		{
			this.mPosition = position;
			this.mViews = new SparseArray<View>();
			mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,false);
			// setTag
			mConvertView.setTag(this);
		}

		/**
		 * 拿到一个ViewHolder对象
		 *
		 * @param context
		 * @param convertView
		 * @param parent
		 * @param layoutId
		 * @param position
		 * @return
		 */
		public  ViewHolder get(Context context, View convertView,
									 ViewGroup parent, int layoutId, int position)
		{
			if (convertView == null)
			{
				return new ViewHolder(context, parent, layoutId, position);
			}
			return (ViewHolder) convertView.getTag();
		}

		public View getConvertView()
		{
			return mConvertView;
		}

		/**
		 * 通过控件的Id获取对于的控件，如果没有则加入views
		 *
		 * @param viewId
		 * @return
		 */
		public <T extends View> T getView(int viewId)
		{
			View view = mViews.get(viewId);
			if (view == null)
			{
				view = mConvertView.findViewById(viewId);
				mViews.put(viewId, view);
			}
			return (T) view;
		}

		/**
		 * 为TextView设置字符串
		 *
		 * @param viewId
		 * @param text
		 * @return
		 */
		public ViewHolder setText(int viewId, String text)
		{
			TextView view = getView(viewId);
			view.setText(text);
			return this;
		}

		/**
		 * 为ImageView设置图片
		 *
		 * @param viewId
		 * @param drawableId
		 * @return
		 */
		public ViewHolder setImageResource(int viewId, int drawableId)
		{
			ImageView view = getView(viewId);
			view.setImageResource(drawableId);

			return this;
		}

		/**
		 * 为ImageView设置图片
		 *
		 * @param viewId
		 * @param bm
		 * @return
		 */
		public ViewHolder setImageBitmap(int viewId, Bitmap bm)
		{
			ImageView view = getView(viewId);
			view.setImageBitmap(bm);
			return this;
		}
//
//    /**
//     * 为ImageView设置图片
//     *
//     * @param viewId
//     * @param drawableId
//     * @return
//     */
//    public ViewHolder setImageByUrl(int viewId, String url)
//    {
//        ImageLoader.getInstance(3, Type.LIFO).loadImage(url,
//                (ImageView) getView(viewId));
//        return this;
//    }

		public int getPosition()
		{
			return mPosition;
		}

	}
}
