package com.gsy.graduation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.gsy.graduation.adapter.MenuListAdapter;
import com.gsy.graduation.application.BaseApplication;
import com.gsy.graduation.data.HotMovieData;
import com.gsy.graduation.function.DriveRoute;
import com.gsy.graduation.utils.DeviceUtils;
import com.gsy.graduation.utils.ToastUtil;
import com.gsy.graduation.view.SwitchImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, PoiSearch.OnPoiSearchListener
        , AMap.OnMarkerClickListener {

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
    private View mMovieAround;
    // 查询
    private PoiSearch.Query query;// Poi查询条件类
    private int currentPage = 0;// 当前页面，从0开始计数
    private PoiResult poiResult; // poi返回的结果
    private List<PoiItem> poiItems;// poi数据
    //    private RelativeLayout mPoiDetail;
    private Marker mlastMarker;
    private myPoiOverlay poiOverlay;// poi图层
    private LatLonPoint lp;
    private RelativeLayout mBottomLayout;
    private TextView mRotueTimeDes;
    private TextView mRouteDetailDes;


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
        mMovieAround.setOnClickListener(this);

        mAMap.setOnMarkerClickListener(this);
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
        mMovieAround = findViewById(R.id.activity_map_movie_around);
        findViewById(R.id.activity_main_current_index).setOnClickListener(new LocationIndex());

        mMapModelLl.setTranslationY(-DeviceUtils.dip2px(100));

        // 路径规划
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mRotueTimeDes = (TextView) findViewById(R.id.firstline);
        mRouteDetailDes = (TextView) findViewById(R.id.secondline);

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
     *
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

            case R.id.activity_map_movie_around:
                doSearchQuery();
                break;

            default:
                break;
        }
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        currentPage = 0;
        query = new PoiSearch.Query("电影院", "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        lp = new LatLonPoint(mAMap.getCameraPosition().target.latitude, mAMap.getCameraPosition().target.longitude);
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
        // 设置搜索区域为以lp点为圆心，其周围5000米范围
        poiSearch.searchPOIAsyn();// 异步搜索
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

    @Override
    public void onPoiSearched(PoiResult result, int rcode) {
        if (rcode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getQuery() != null) {// 搜索poi的结果
                if (result.getQuery().equals(query)) {// 是否是同一条
                    poiResult = result;
                    poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems != null && poiItems.size() > 0) {
                        //并还原点击marker样式
                        if (mlastMarker != null) {
                            resetlastmarker();
                        }
                        //清理之前搜索结果的marker
                        if (poiOverlay != null) {
                            poiOverlay.removeFromMap();
                        }
                        mAMap.clear();
                        poiOverlay = new myPoiOverlay(mAMap, poiItems, MainActivity.this);
                        poiOverlay.addToMap();
                        poiOverlay.zoomToSpan();

                        mAMap.addMarker(new MarkerOptions()
                                .anchor(0.5f, 0.5f)
                                .icon(BitmapDescriptorFactory
                                        .fromBitmap(BitmapFactory.decodeResource(
                                                getResources(), R.drawable.point4)))
                                .position(new LatLng(lp.getLatitude(), lp.getLongitude())));

                        mAMap.addCircle(new CircleOptions()
                                .center(new LatLng(lp.getLatitude(),
                                        lp.getLongitude())).radius(5000)
                                .strokeColor(Color.BLUE)
                                .fillColor(Color.argb(50, 1, 1, 1))
                                .strokeWidth(2));

                    } else if (suggestionCities != null
                            && suggestionCities.size() > 0) {
                        showSuggestCity(suggestionCities);
                    } else {
                        ToastUtil.show(MainActivity.this, R.string.no_result);
                    }
                }
            } else {
                ToastUtil.show(MainActivity.this, R.string.no_result);
            }
        } else {
            ToastUtil.showerror(this.getApplicationContext(), rcode);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getObject() != null) {
            try {
                if (mlastMarker == null) {
                    mlastMarker = marker;
                } else {
                    // 将之前被点击的marker置为原来的状态
                    resetlastmarker();
                    mlastMarker = marker;
                }
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(getResources(), R.drawable.poi_marker_pressed)));
                LatLonPoint start = new LatLonPoint(mAMap.getCameraPosition().target.latitude, mAMap.getCameraPosition().target.longitude);
                LatLonPoint end = new LatLonPoint(marker.getPosition().latitude,marker.getPosition().longitude);
                DriveRoute driveRoute = new DriveRoute(MainActivity.this, mAMap, start, end, mMapView);
                driveRoute.setBottomLayout(mBottomLayout);
                driveRoute.setRotueTimeDes(mRotueTimeDes);
                driveRoute.setRouteDetailDes(mRouteDetailDes);

            } catch (Exception e) {
                // TODO: handle exception
            }
        } else {
            resetlastmarker();
        }
        return false;
    }


    // 将之前被点击的marker置为原来的状态
    private void resetlastmarker() {
        int index = poiOverlay.getPoiIndex(mlastMarker);
        if (index < 10) {
            mlastMarker.setIcon(BitmapDescriptorFactory
                    .fromBitmap(BitmapFactory.decodeResource(
                            getResources(),
                            poiOverlay.markers[index])));
        } else {
            mlastMarker.setIcon(BitmapDescriptorFactory.fromBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.marker_other_highlight)));
        }
        mlastMarker = null;

    }

    /**
     * poi没有搜索到数据，返回一些推荐城市的信息
     */
    private void showSuggestCity(List<SuggestionCity> cities) {
        String infomation = "推荐城市\n";
        for (int i = 0; i < cities.size(); i++) {
            infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
                    + cities.get(i).getCityCode() + "城市编码:"
                    + cities.get(i).getAdCode() + "\n";
        }
        ToastUtil.show(this, infomation);

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

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
