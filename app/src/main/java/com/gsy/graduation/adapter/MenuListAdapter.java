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
public class MenuListAdapter extends BaseAdapter {
    private Context mContext;
    private HotMovieData mHotMovieData;

    public MenuListAdapter(Context context, HotMovieData movie) {
        this.mContext = context;
        this.mHotMovieData = movie;
    }

    @Override
    public int getCount() {
        return mHotMovieData.data.size();
    }

    @Override
    public HotMovieData.DataBean getItem(int position) {
        return mHotMovieData.data.get(position);
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
        private TextView movieAddress;

        /**
         * viewHolder的构造方法，初始化view
         */
        public MenuViewHolder(View view) {
            movieName = (TextView) view.findViewById(R.id.menu_list_movie_name);
            movieComment = (TextView) view.findViewById(R.id.menu_list_movie_comment);
            movieAddress = (TextView) view.findViewById(R.id.menu_list_movie_address);
        }

        /**
         * 处理item的相关逻辑
         */
        public void updateViewHolder(HotMovieData.DataBean moviData) {
            HotMovieData.DataBean.MovieBean selcetMovieBean = null;
            for (HotMovieData.DataBean.MovieBean movieBean : moviData.movie) {
                if (selcetMovieBean == null) {
                    selcetMovieBean = movieBean;
                } else {
                    selcetMovieBean = Integer.parseInt(movieBean.click_value) > Integer.parseInt(selcetMovieBean.click_value) ? movieBean : selcetMovieBean;
                }
            }
            movieName.setText(selcetMovieBean != null ? selcetMovieBean.name : null);
            movieComment.setText((selcetMovieBean != null ? selcetMovieBean.click_value : null));
            movieAddress.setText(moviData.address);
        }
    }

}
