package com.gsy.graduation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsy.graduation.R;
import com.gsy.graduation.data.HotMovieData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 *
 */
public class DialogListAdapter extends BaseAdapter {

    private HotMovieData.DataBean mDataBean;
    private Context mContext;
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
        holder.updateViewHolder(getItem(position), mDataBean);
        return convertView;
    }

    private static class MovieViewHolder {
        private TextView movieName;
        private TextView movieComment;
        private TextView movieAddress;
        private final ImageView mImageView;

        /**
         * viewHolder的构造方法，初始化view
         */
        public MovieViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.menu_list_movie_ic);
            movieName = (TextView) view.findViewById(R.id.menu_list_movie_name);
            movieComment = (TextView) view.findViewById(R.id.menu_list_movie_comment);
            movieAddress = (TextView) view.findViewById(R.id.menu_list_movie_address);
        }

        /**
         * 处理item的相关逻辑
         */
        public void updateViewHolder(HotMovieData.DataBean.MovieBean movieData, HotMovieData.DataBean dataBean) {
            ImageLoader.getInstance().displayImage(movieData.pic_url,mImageView);
            movieName.setText(movieData.name);
            movieComment.setText(movieData.click_value);
            movieAddress.setText(dataBean.address);
        }
    }
}
