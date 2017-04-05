package com.gsy.graduation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;
import com.gsy.graduation.adapter.MenuListAdapter;
import com.gsy.graduation.application.BaseApplication;
import com.gsy.graduation.data.HotMovieData;
import com.gsy.graduation.utils.DeviceUtils;
import com.gsy.graduation.view.SwitchImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {

    private static final int DURATION_SHOW_MENU = 300;
    private View mMapMenuLl;
    private View mMapMenuIv;
    private View mMapShadowFl;
    private ListView mMMenuListView;
    private List<HotMovieData> mMovieList = new ArrayList<>();
    private SwitchImageView mMenuSw1;
    private SwitchImageView mMenuSw2;
    private MapView mMapView;
    private AMap mAMap;
    private View mMapModelLl;
    private View mMapStandard;
    private View mMapSatellite;
    private View mMapNight;
    private View mMapNavigate;
    private View mMapModelMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseApplication.setApplication(getApplication());
        setContentView(R.layout.activity_main);
        getMoviesData();
        initView();

        mMapView.onCreate(savedInstanceState);
        initData();
        setOnclickListener();
    }

    private void setOnclickListener() {
        mMapStandard.setOnClickListener(this);
        mMapSatellite.setOnClickListener(this);
        mMapNight.setOnClickListener(this);
        mMapNavigate.setOnClickListener(this);
        mMapModelMenu.setOnClickListener(this);
        mMapMenuIv.setOnClickListener(this);
        mMapShadowFl.setOnClickListener(this);
        mMenuSw1.setOnClickListener(this);
        mMenuSw2.setOnClickListener(this);
    }

    private void initData() {
        mMapMenuLl.setTranslationX(getResources().getDimension(R.dimen.activity_menu_ll_width));

        MenuListAdapter menuListAdapter = new MenuListAdapter(this, mMovieList);
        mMMenuListView.setAdapter(menuListAdapter);
        setSwitch1();
        setSwitch2();
    }

    private void initView() {
        mMapView = (MapView) findViewById(R.id.map);
        mAMap = mMapView.getMap();
        mMapModelLl = findViewById(R.id.activity_main_map_model_ll);
        mMapStandard = findViewById(R.id.activity_map_standard);
        mMapSatellite = findViewById(R.id.activity_map_satellite);
        mMapNight = findViewById(R.id.activity_map_night);
        mMapNavigate = findViewById(R.id.activity_map_navigate);
        mMapModelMenu = findViewById(R.id.activity_main_map_model);
        mMapMenuLl = findViewById(R.id.activity_map_menu_ll);
        mMapMenuIv = findViewById(R.id.activity_map_menu_iv);
        mMapShadowFl = findViewById(R.id.activity_map_shadow_fl);
        mMMenuListView = (ListView) findViewById(R.id.activity_menu_list_view);
        mMenuSw1 = (SwitchImageView) findViewById(R.id.activity_menu_sw1);
        mMenuSw2 = (SwitchImageView) findViewById(R.id.activity_menu_sw2);
        findViewById(R.id.activity_main_current_index).setOnClickListener(new LocationIndex());

        mMapModelLl.setTranslationY(-DeviceUtils.dip2px(100));

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
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
     * 设置地图模式的显示和隐藏
     * @param visible true 显示，false 隐藏
     */
    private void setMapModelVisibility(final boolean visible) {
        mMapModelLl.animate()
                .translationY(visible ? 0 : -DeviceUtils.dip2px(100))
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
            mAMap.setTrafficEnabled(true);
            mMenuSw1.setImageResource(R.drawable.activity_menu_switch_on);
        } else {
            mAMap.setTrafficEnabled(false);
            mMenuSw1.setImageResource(R.drawable.activity_menu_switch_off);
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
            case R.id.activity_map_standard:
                mAMap.setMapType(AMap.MAP_TYPE_NORMAL);
                mMapStandard.setBackgroundResource(R.drawable.activity_map_standard_selected);
                mMapSatellite.setBackgroundResource(R.drawable.activity_map_satellite_normal);
                mMapNight.setBackgroundResource(R.drawable.activity_map_night_normal);
                mMapNavigate.setBackgroundResource(R.drawable.activity_map_navigate_normal);
                break;
            case R.id.activity_map_satellite:
                mAMap.setMapType(AMap.MAP_TYPE_SATELLITE);
                mMapStandard.setBackgroundResource(R.drawable.activity_map_standard_normal);
                mMapSatellite.setBackgroundResource(R.drawable.activity_map_satellite_selected);
                mMapNight.setBackgroundResource(R.drawable.activity_map_night_normal);
                mMapNavigate.setBackgroundResource(R.drawable.activity_map_navigate_normal);
                break;

            case R.id.activity_map_night:
                mAMap.setMapType(AMap.MAP_TYPE_NIGHT);
                mMapStandard.setBackgroundResource(R.drawable.activity_map_standard_normal);
                mMapSatellite.setBackgroundResource(R.drawable.activity_map_satellite_normal);
                mMapNight.setBackgroundResource(R.drawable.activity_map_night_selected);
                mMapNavigate.setBackgroundResource(R.drawable.activity_map_navigate_normal);
                break;

            case R.id.activity_map_navigate:
                mAMap.setMapType(AMap.MAP_TYPE_NAVI);
                mMapStandard.setBackgroundResource(R.drawable.activity_map_standard_normal);
                mMapSatellite.setBackgroundResource(R.drawable.activity_map_satellite_normal);
                mMapNight.setBackgroundResource(R.drawable.activity_map_night_normal);
                mMapNavigate.setBackgroundResource(R.drawable.activity_map_navigate_selected);
                break;

            case R.id.activity_map_menu_iv:
                setMenuVisibility(mMapShadowFl.getVisibility() != View.VISIBLE);
                break;

            case R.id.activity_map_shadow_fl:
                setMenuVisibility(false);
                setMapModelVisibility(false);
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

            case R.id.activity_main_map_model:
                setMapModelVisibility(mMapShadowFl.getVisibility() != View.VISIBLE);
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

    private class LocationIndex implements View.OnClickListener {
        final int PLANE_MAP = 0;
        final int SLOPING_MAP = 1;
        int currentMode = PLANE_MAP;
        int zoomLevel = 0;

        @Override
        public void onClick(View v) {
            MyLocationStyle myLocationStyle;
            myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类
            myLocationStyle.strokeColor(Color.parseColor("#36a9ae"));
            myLocationStyle.radiusFillColor(Color.parseColor("#2036a9ae"));
            switch (currentMode) {
                case PLANE_MAP:
                    currentMode = SLOPING_MAP;
                    ((TextView) v).setText("当前\n位置");
                    zoomLevel = 17;
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);// 定位一次，且将视角移动到地图中心点。
                    break;

                case SLOPING_MAP:
                    currentMode = PLANE_MAP;
                    ((TextView) v).setText("连续\n定位");
                    zoomLevel = 19;
                    myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
                    myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);// 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
                    break;

                default:
                    break;
            }
            mAMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
            mAMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
        }
    }
}
