package com.gsy.graduation.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import com.gsy.graduation.R;
import com.gsy.graduation.utils.DeviceUtils;
import com.gsy.graduation.utils.MyConstants;
import com.gsy.graduation.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @创建时间 2015-7-4 上午11:05:45
 * @描述 设置向导界面 ，采用Viewpager界面的切换
 *
 *     @ svn提交者：$Author: gd $ @ 提交时间: $Date: 2015-07-04 11:45:43 +0800 (Sat, 04
 *     Jul 2015) $ @ 当前版本: $Rev: 9 $
 */
public class GuideActivity extends Activity
{
    private ViewPager		vp_guids;
    private LinearLayout	ll_points;
    private View			v_redpoint;
    private Button			bt_startExp;
    private List<ImageView>	guids;
    private MyAdapter		adapter;
    private int	disPoints;//点与点之间的距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
        initView();// 初始化界面

        initData();// 初始化数据

        initEvent();// 初始化组件事件
    }

    private void initEvent() {
        //监听布局完成 ，触发的结果
        v_redpoint.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {



            @Override
            public void onGlobalLayout() {
                //取消注册 界面变化而发生的回调结果
                v_redpoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //计算点与点之间的距离
                disPoints = (ll_points.getChildAt(1).getLeft() - ll_points.getChildAt(0)
                        .getLeft());
            }
        });

        //给按钮添加点击事件
        bt_startExp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                //保存设置的状态
                SpUtils.setBoolean(getApplicationContext(), MyConstants.ISSETUP, true);//保存设置完成的状态
                //进入主界面
                Intent main = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(main);//启动主界面
                //关闭自己
                finish();
            }
        });

        //给ViewPage添加页码改变的事件
        vp_guids.setOnPageChangeListener(new OnPageChangeListener() {


            @Override
            public void onPageSelected(int position) {
                //当前ViewPager显示的页码
                //如果ViewPager滑动到第三个页码(最后一页)，显示button
                if (position == guids.size() - 1) {
                    bt_startExp.setVisibility(View.VISIBLE);//设置设置按钮的显示
                } else {
                    //不是最后一页，隐藏该button按钮
                    bt_startExp.setVisibility(View.GONE);
                }
            }

            /* (non-Javadoc)
             * @see android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)
             * 在页面滑动过程触发的事件
             * @param position 当前ViewPage停留的位置
             * @param positionOffset 偏移的比例值
             * @param positionOffsetPixels 偏移的像素
             */
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {

                //positionOffset 移动的比例值
                //计算红点的左边距
                float leftMargin = disPoints * (position + positionOffset);

                //设置红点的左边距
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v_redpoint.getLayoutParams();
                layoutParams.leftMargin = Math.round(leftMargin);//对float类型四舍五入

                //重新设置布局
                v_redpoint.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initData() {
        // viewpaper adapter list
        // 图片的数据
        int[] pics = new int[] { R.drawable.main_dinwei_yindao, R.drawable.map_mode, R.drawable.main_caidan_yindao,
                R.drawable.fujinyingyuan_yindao, R.drawable.relitu_yindao};

        // 定义Viewpager使用的容器

        guids = new ArrayList<ImageView>();

        // 初始化容器中的数据
        for (int i = 0; i < pics.length; i++) {
            ImageView iv_temp = new ImageView(getApplicationContext());
            iv_temp.setBackgroundResource(pics[i]);

            // 添加界面的数据
            guids.add(iv_temp);

            // 给点的容器Linearlayout初始化添加灰色点
            View v_point = new View(getApplicationContext());
            v_point.setBackgroundResource(R.drawable.gray_point);
            int dip = 10;
            // 设置灰色点的大小
            LayoutParams params = new LayoutParams(DeviceUtils.dip2px(getApplicationContext(), dip), DeviceUtils.dip2px(getApplicationContext(), dip));// 注意单位是px 不是dp

            // 设置点与点直接的空隙
            // 第一个点不需要指定
            if (i != 0)// 过滤第一个点
                params.leftMargin = 10;// px
            v_point.setLayoutParams(params);// 无缝隙的挨一起

            // 添加灰色的点到线性布局中
            ll_points.addView(v_point);
        }

        // 界面没有布局前，点的位置是确定不了的，布局完成，再求出点直接的距离


        // 创建ViewPager的适配器
        adapter = new MyAdapter();

        // 设置适配器
        vp_guids.setAdapter(adapter);

    }

    private class MyAdapter extends PagerAdapter
    {

        @Override
        public int getCount() {

            return guids.size();// 返回数据的个数
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;// 过滤和缓存的作用
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);// 从Viewpager中移除
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // container viewpaper
            // 获取View
            View child = guids.get(position);
            // 添加View
            container.addView(child);//

            return child;
        }

    }

    private void initView() {
        setContentView(R.layout.activity_guide);

        // ViewPage组件
        vp_guids = (ViewPager) findViewById(R.id.vp_guide_pages);

        // 动态加点容器
        ll_points = (LinearLayout) findViewById(R.id.ll_guide_points);

        // 红点
        v_redpoint = findViewById(R.id.v_guide_redpoint);

        // 开始体验的按钮
        bt_startExp = (Button) findViewById(R.id.bt_guide_startexp);
    }
}
