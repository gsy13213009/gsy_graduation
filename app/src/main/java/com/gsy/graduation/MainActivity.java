package com.gsy.graduation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.gsy.graduation.adapter.MenuListAdapter;
import com.gsy.graduation.data.HotMovieData;
import com.gsy.graduation.view.SwitchImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int DURATION_SHOW_MENU = 500;
    MapView mMapView = null;
    private BaiduMap mBaiduMap;
    private View mTvSatellite;
    private View mTv2d;
    private View mMapMenuLl;
    private View mMapMenuIv;
    private View mMapShadowFl;
    private ListView mMMenuListView;
    private List<HotMovieData> mMovieList = new ArrayList<>();
    private SwitchImageView mMenuSw1;
    private SwitchImageView mMenuSw2;
    private View mCurrentIndex;
    private View mHotMap;
    private View mRoot;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    boolean isFirstLoc = true; // 是否首次定位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        getMoviesData();

        initView();
        initData();
        setOnclickListener();
    }

    private void setOnclickListener() {
        mTvSatellite.setOnClickListener(this);
        mTv2d.setOnClickListener(this);
        mMapMenuIv.setOnClickListener(this);
        mMapShadowFl.setOnClickListener(this);
        mMenuSw1.setOnClickListener(this);
        mMenuSw2.setOnClickListener(this);
    }

    private void initData() {
        mMapMenuLl.setTranslationX(getResources().getDimension(R.dimen.activity_menu_ll_width));
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setTrafficEnabled(true);

        MenuListAdapter menuListAdapter = new MenuListAdapter(this, mMovieList);
        mMMenuListView.setAdapter(menuListAdapter);


        LatLng point = new LatLng(39.963175, 116.400244);
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_marka);
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(bitmap);


        mBaiduMap.addOverlay(option);
        setSwitch1();
        setSwitch2();

    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.bmapView);
        mTv2d = findViewById(R.id.activity_map_2d);
        mTvSatellite = findViewById(R.id.activity_map_satellite);
        mMapMenuLl = findViewById(R.id.activity_map_menu_ll);
        mMapMenuIv = findViewById(R.id.activity_map_menu_iv);
        mMapShadowFl = findViewById(R.id.activity_map_shadow_fl);
        mMMenuListView = (ListView) findViewById(R.id.activity_menu_list_view);
        mMenuSw1 = (SwitchImageView) findViewById(R.id.activity_menu_sw1);
        mMenuSw2 = (SwitchImageView) findViewById(R.id.activity_menu_sw2);
        mCurrentIndex = findViewById(R.id.activity_main_current_index);
        mHotMap = findViewById(R.id.activity_main_hot_map);
        mRoot = findViewById(R.id.activity_main_root);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    /**
     * 设置侧边栏的显示和隐藏
     *
     * @param visible true 显示，false 隐藏
     */
    private void setMenuVisibility(final boolean visible) {
        mMapMenuLl.animate()
                .translationX(visible ? 0 : getResources().getDimension(R.dimen.activity_menu_ll_width))
                .setDuration(DURATION_SHOW_MENU)
                .setInterpolator(new DecelerateInterpolator())
                .start();
        if (visible) mMapShadowFl.setVisibility(View.VISIBLE);
        mMapShadowFl.animate()
                .alpha(visible ? 1 : 0)
                .setDuration(DURATION_SHOW_MENU)
                .setInterpolator(new DecelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        if (!visible) {
                            mMapShadowFl.setVisibility(View.GONE);
                        }
                    }
                })
                .start();
    }

    /**
     * 设置第一个开关
     */
    private void setSwitch1() {
        if (mMenuSw1.isChecked()) {
            mMenuSw1.setImageResource(R.drawable.activity_menu_switch_on);
            mBaiduMap.setBaiduHeatMapEnabled(true);
        } else {
            mMenuSw1.setImageResource(R.drawable.activity_menu_switch_off);
            mBaiduMap.setBaiduHeatMapEnabled(false);
        }
    }

    /**
     * 设置第二个开关
     */
    private void setSwitch2() {
        if (mMenuSw2.isChecked()) {
            mMenuSw2.setImageResource(R.drawable.activity_menu_switch_on);
        } else {
            mMenuSw2.setImageResource(R.drawable.activity_menu_switch_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_map_satellite:
                mTvSatellite.setBackgroundResource(R.drawable.activity_map_satellite_press_pc);
                mTv2d.setBackgroundResource(R.drawable.activity_map_2d_normal_pc);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.activity_map_2d:
                mTvSatellite.setBackgroundResource(R.drawable.activity_map_satellite_normal_pc);
                mTv2d.setBackgroundResource(R.drawable.activity_map_2d_press_pc);
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;

            case R.id.activity_map_menu_iv:
                setMenuVisibility(mMapShadowFl.getVisibility() != View.VISIBLE);
                break;

            case R.id.activity_map_shadow_fl:
                setMenuVisibility(mMapShadowFl.getVisibility() != View.VISIBLE);
                break;

            case R.id.activity_menu_sw1:
                mMenuSw1.setChecked(!mMenuSw1.isChecked());
                setSwitch1();
                break;

            case R.id.activity_menu_sw2:
                mMenuSw2.setChecked(!mMenuSw2.isChecked());
                setSwitch2();
                break;
            case R.id.activity_main_current_index:
                break;

            default:
                break;
        }
    }

    public void getMoviesData() {
        for (int i = 0; i < 50; i++) {
            HotMovieData hotMovieData = new HotMovieData();
            hotMovieData.setName("第" + i + "部电影");
            hotMovieData.setComment(i + " 分");
            hotMovieData.setTime("2月14日22:00--24:00");
            mMovieList.add(hotMovieData);
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
}
