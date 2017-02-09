package com.gsy.graduation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gsy.graduation.R;
import com.gsy.graduation.data.HotMovieData;

import java.util.List;

/**
 *
 */
public class MenuListAdapter extends BaseAdapter {
    private List<HotMovieData> mMovieList;
    private Context mContext;

    public MenuListAdapter(Context context, List<HotMovieData> movieList) {
        this.mContext = context;
        this.mMovieList = movieList;
    }

    @Override
    public int getCount() {
        return mMovieList.size();
    }

    @Override
    public HotMovieData getItem(int position) {
        return mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.movie_hot_list_item, parent, false);
            holder = new MenuViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MenuViewHolder) convertView.getTag();
        }
        holder.updateViewHolder(getItem(position));

        return convertView;
    }

    private static class MenuViewHolder {
        private TextView movieName;
        private TextView movieComment;

        private HotMovieData mHotMovieData;

        /**
         * viewHolder的构造方法，初始化view
         *
         * @param view
         */
        public MenuViewHolder(View view) {
            movieName = (TextView) view.findViewById(R.id.menu_list_movie_name);
            movieComment = (TextView) view.findViewById(R.id.menu_list_movie_comment);
        }

        /**
         * 处理item的相关逻辑
         *
         * @param hotMovieData
         */
        public void updateViewHolder(HotMovieData hotMovieData) {
            movieName.setText(hotMovieData.getName());
            movieComment.setText(hotMovieData.getComment());
        }
    }

}
