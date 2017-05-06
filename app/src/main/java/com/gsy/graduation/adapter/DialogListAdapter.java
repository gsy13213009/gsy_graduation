package com.gsy.graduation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gsy.graduation.R;
import com.gsy.graduation.data.HotMovieData;

/**
 *
 */
public class DialogListAdapter extends BaseAdapter {

    private HotMovieData.DataBean mDataBean;
    private Context mContext;
    public DialogListAdapter(Context context, HotMovieData.DataBean dataBean) {
        this.mContext = context;
        mDataBean = dataBean;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_hot_list_item, parent, false);
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

        /**
         * viewHolder的构造方法，初始化view
         */
        public MovieViewHolder(View view) {
            movieName = (TextView) view.findViewById(R.id.menu_list_movie_name);
            movieComment = (TextView) view.findViewById(R.id.menu_list_movie_comment);
            movieAddress = (TextView) view.findViewById(R.id.menu_list_movie_address);
        }

        /**
         * 处理item的相关逻辑
         */
        public void updateViewHolder(HotMovieData.DataBean.MovieBean moviData, HotMovieData.DataBean dataBean) {
            movieName.setText(moviData.name);
            movieComment.setText(moviData.click_value);
            movieAddress.setText(dataBean.address);
        }
    }
}
