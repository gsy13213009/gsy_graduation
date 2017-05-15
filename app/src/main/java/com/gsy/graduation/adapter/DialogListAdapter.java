package com.gsy.graduation.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsy.graduation.R;
import com.gsy.graduation.data.HotMovieData;
import com.gsy.graduation.utils.AMapUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 *
 */
public class DialogListAdapter extends BaseAdapter {

    private HotMovieData.DataBean mDataBean;
    private Context mContext;
    private int mSelectedItem;

    public DialogListAdapter(Context context, HotMovieData.DataBean dataBean) {
        this.mContext = context;
        mDataBean = dataBean;
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(context).build());
        }
    }

    @Override
    public int getCount() {
        return mDataBean.movie.size();
    }

    @Override
    public HotMovieData.DataBean.MovieBean getItem(int position) {
        return mDataBean.movie.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MovieViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_movie_hot_list, parent, false);
            holder = new MovieViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MovieViewHolder) convertView.getTag();
        }
        holder.updateViewHolder(getItem(position), mDataBean, convertView,mSelectedItem == position);
        return convertView;
    }

    public void setSelectedItem(int selectedItem) {
        mSelectedItem = selectedItem;
    }

    private static class MovieViewHolder {
        private TextView movieName;
        private TextView movieComment;
        private TextView movieAddress;
        private ImageView mImageView;
        private View mRoot;

        /**
         * viewHolder的构造方法，初始化view
         */
        public MovieViewHolder(View view) {
            mRoot = view.findViewById(R.id.dialog_layout);
            mImageView = (ImageView) view.findViewById(R.id.menu_list_movie_ic);
            movieName = (TextView) view.findViewById(R.id.menu_list_movie_name);
            movieComment = (TextView) view.findViewById(R.id.menu_list_movie_comment);
            movieAddress = (TextView) view.findViewById(R.id.menu_list_movie_address);
        }

        /**
         * 处理item的相关逻辑
         */
        public void updateViewHolder(HotMovieData.DataBean.MovieBean movieData, HotMovieData.DataBean dataBean, View view,boolean isSelected) {
            ImageLoader.getInstance().displayImage(AMapUtil.getUriFromPath(view.getContext(), movieData.pic_url).toString(), mImageView);
            movieName.setText(movieData.name);
            movieComment.setText(movieData.click_value);
            movieAddress.setText(dataBean.address);
            if (isSelected) {
                mRoot.setBackgroundColor(view.getResources().getColor(R.color.color666));
            } else {
                mRoot.setBackgroundColor(Color.WHITE);
            }
        }
    }
}
