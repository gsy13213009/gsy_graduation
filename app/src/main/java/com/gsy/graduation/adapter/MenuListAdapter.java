package com.gsy.graduation.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gsy.graduation.R;
import com.gsy.graduation.data.HotMovieData;
import com.gsy.graduation.utils.AMapUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 *
 */
public class MenuListAdapter extends BaseAdapter {
    private final DisplayImageOptions mHorizontalDisplayOptions;
    private Context mContext;
    private HotMovieData mHotMovieData;

    public MenuListAdapter(Context context, HotMovieData movie) {
        this.mContext = context;
        this.mHotMovieData = movie;
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(context).build());
        }
        final DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.color.meiyin_color_f7f7f7)
                .showImageForEmptyUri(R.color.meiyin_color_f7f7f7)
                .showImageOnFail(R.color.meiyin_color_f7f7f7)
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .considerExifParams(true);
        mHorizontalDisplayOptions = builder
                .build();
    }

    @Override
    public int getCount() {
        return mHotMovieData.menu_data.size();
    }

    @Override
    public HotMovieData.MenuData getItem(int position) {
        return mHotMovieData.menu_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuViewHolder holder;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.menu_movie_hot_list, parent, false);
            holder = new MenuViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MenuViewHolder) convertView.getTag();
        }
        holder.updateViewHolder(getItem(position), convertView,mHorizontalDisplayOptions);

        return convertView;
    }

    private static class MenuViewHolder {
        private TextView movieName;
        private TextView movieComment;
        private ImageView mImageView;

        /**
         * viewHolder的构造方法，初始化view
         */
        MenuViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.menu_list_movie_ic);
            movieName = (TextView) view.findViewById(R.id.menu_list_movie_name);
            movieComment = (TextView) view.findViewById(R.id.menu_list_movie_comment);
        }

        /**
         * 处理item的相关逻辑
         */
        void updateViewHolder(HotMovieData.MenuData menuData, View convertView, DisplayImageOptions horizontalDisplayOptions) {

            ImageLoader.getInstance().displayImage(menuData != null ? AMapUtil.getUriFromPath(convertView.getContext()
                    , menuData.pic_url).toString() : null, mImageView, horizontalDisplayOptions);
            movieName.setText(menuData != null ? menuData.name : null);
            movieComment.setText((menuData != null ? menuData.click_value : null));
        }
    }
    
}
