package com.gsy.graduation.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.amap.api.maps.model.Poi;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.google.gson.Gson;
import com.gsy.graduation.R;
import com.gsy.graduation.adapter.BusResultListAdapter;
import com.gsy.graduation.adapter.DialogListAdapter;
import com.gsy.graduation.adapter.MenuListAdapter;
import com.gsy.graduation.data.HotMovieData;
import com.gsy.graduation.overlay.DrivingRouteOverlay;
import com.gsy.graduation.overlay.WalkRouteOverlay;
import com.gsy.graduation.utils.AMapUtil;
import com.gsy.graduation.utils.DeviceUtils;
import com.gsy.graduation.utils.ToastUtils;
import com.gsy.graduation.view.MyPoiOverlay;
import com.gsy.graduation.view.SwitchImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.Collections;
import java.util.List;

import static com.gsy.graduation.application.BaseApplication.mIsShow;

public class MainActivity extends Activity implements View.OnClickListener, PoiSearch.OnPoiSearchListener
        , AMap.OnMarkerClickListener, RouteSearch.OnRouteSearchListener, DistrictSearch.OnDistrictSearchListener
        , AMap.OnPOIClickListener, GeocodeSearch.OnGeocodeSearchListener {

    private static final int DURATION_SHOW_MENU = 300;
    private View mMapMenuLl;
    private View mMapMenuIv;
    private View mMapShadowFl;
    private ListView mMMenuListView;
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
    private Marker mlastMarker;
    private MyPoiOverlay poiOverlay;// poi图层
    private LatLonPoint lp;
    private RelativeLayout mBottomLayout;
    private TextView mRotueTimeDes;
    private TextView mRouteDetailDes;
    private View mSelectRoute;
    private ImageView mDrive;
    private ImageView mBus;
    private ImageView mWalk;
    private final int ROUTE_TYPE_BUS = 1;
    private final int ROUTE_TYPE_DRIVE = 2;
    private final int ROUTE_TYPE_WALK = 3;
    private ProgressDialog mProgressDialog = null;// 搜索时进度条
    private RouteSearch mRouteSearch;
    private DriveRouteResult mDriveRouteResult;
    private BusRouteResult mBusRouteResult;
    private WalkRouteResult mWalkRouteResult;
    private ListView mBusResultList;
    private LinearLayout mBusResultLayout;
    private LatLonPoint mStartPoint;
    private LatLonPoint mEndPoint;
    private String mCurrentCityName = "北京";
    private View mSelectUp;
    private View mCurrentIndex;
    private HotMovieData mHotMovieDatas;
    private int[] mMustClick = new int[3];
    private boolean mHasMoved = false;
    private ListView mMovieList;
    private View mMovieDialog;
    private ImageView mMovieImage;
    private TextView mMovieDescribe;
    private View mMovieShadow;
    private Marker mCityMarker;
    private String mMovieName;
    private DialogListAdapter mAdapter;
    private GeocodeSearch mGeocoderSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        mSelectUp.setOnClickListener(this);
        mCurrentIndex.setOnClickListener(new LocationIndex());

        mAMap.setOnMarkerClickListener(this);
        mAMap.setOnPOIClickListener(this);
        mMovieShadow.setOnClickListener(this);
    }

    private void initData() {
        mMapMenuLl.setTranslationX(getResources().getDimension(R.dimen.activity_menu_ll_width));
        mMapModelLl.setTranslationY(-DeviceUtils.dip2px(100));
        mSelectRoute.setTranslationY(-DeviceUtils.dip2px(100));
        MenuListAdapter menuListAdapter = new MenuListAdapter(this, mHotMovieDatas);
        mMMenuListView.setAdapter(menuListAdapter);
        setSwitch1();
        setSwitch2();
        mRouteSearch = new RouteSearch(this);
        mRouteSearch.setRouteSearchListener(this);

        mGeocoderSearch = new GeocodeSearch(this);
        mGeocoderSearch.setOnGeocodeSearchListener(this);
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
        mCurrentIndex = findViewById(R.id.activity_main_current_index);

        // 路径规划
        mBottomLayout = (RelativeLayout) findViewById(R.id.bottom_layout);
        mRotueTimeDes = (TextView) findViewById(R.id.firstline);
        mRouteDetailDes = (TextView) findViewById(R.id.secondline);

        // 选择路线按钮
        mSelectRoute = findViewById(R.id.activity_main_select_route);
        mSelectUp = findViewById(R.id.activity_main_route_up);
        mDrive = (ImageView) findViewById(R.id.route_drive);
        mBus = (ImageView) findViewById(R.id.route_bus);
        mWalk = (ImageView) findViewById(R.id.route_walk);
        mBusResultLayout = (LinearLayout) findViewById(R.id.bus_result);
        mBusResultList = (ListView) findViewById(R.id.bus_result_list);

        // 每个地区的视频信息
        mMovieList = (ListView) findViewById(R.id.activity_main_movie_list);
        mMovieDialog = findViewById(R.id.activity_main_movie_dialog);
        mMovieImage = (ImageView) findViewById(R.id.activity_main_movie_iv);
        mMovieDescribe = (TextView) findViewById(R.id.activity_main_movie_describe);
        mMovieShadow = findViewById(R.id.activity_main_movie_shadow);
        mMovieDialog.setVisibility(View.GONE);
        mMovieDialog.setOnClickListener(null);
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
     * 设置地图模式的显示和隐藏
     *
     * @param visible true 显示，false 隐藏
     */
    private void setSelectRouteVisibility(boolean visible) {
        mSelectRoute.animate()
                .translationY(visible ? 0 : -DeviceUtils.dip2px(100))
                .setDuration(DURATION_SHOW_MENU)
                .setInterpolator(new DecelerateInterpolator())
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
            for (HotMovieData.DataBean dataBean : mHotMovieDatas.data) {
                DistrictSearch search = new DistrictSearch(getApplicationContext());
                DistrictSearchQuery query = new DistrictSearchQuery();
                query.setKeywords(dataBean.address_city);
                query.setShowBoundary(true);
                query.setKeywordsLevel("country");
                search.setQuery(query);
                search.setOnDistrictSearchListener(this);
                search.searchDistrictAsyn();
            }
        } else {
            mMenuSw2.setImageResource(R.drawable.activity_menu_switch_off);
            mHasMoved = false;
            mAMap.clear();
        }
    }

    /**
     * 设置每个地区视频详情对话框是否显示
     */
    private void setMovieDialogVisibility(boolean visible) {
        if (visible) {
            mMovieDialog.setVisibility(View.VISIBLE);
            mMovieShadow.setVisibility(View.VISIBLE);
        } else {
            if (mMovieDialog.isShown()) mMovieDialog.setVisibility(View.GONE);
            mMovieShadow.setVisibility(View.GONE);
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

            case R.id.activity_main_map_model:
                setMapModelVisibility(mMapShadowFl.getVisibility() != View.VISIBLE);
                break;

            case R.id.activity_map_movie_around:
                doSearchQuery();
                break;

            case R.id.activity_main_route_up:
                setSelectRouteVisibility(false);
                break;

            case R.id.activity_main_movie_shadow:
                setMovieDialogVisibility(false);
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
        mHotMovieDatas = new Gson().fromJson(HotMovieData.movie_data.toString() + HotMovieData.movie_data2.toString(), HotMovieData.class);
        Collections.sort(mHotMovieDatas.data);
        for (int i = 0; i < 3; i++) {
            for (HotMovieData.DataBean.MovieBean movieBean : mHotMovieDatas.data.get(i).movie) {
                mMustClick[i] = Integer.parseInt(movieBean.click_value) > mMustClick[i] ? Integer.parseInt(movieBean.click_value) : mMustClick[i];
            }
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
                        poiOverlay = new MyPoiOverlay(mAMap, poiItems, MainActivity.this);
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
                        ToastUtils.show(MainActivity.this, R.string.no_result);
                    }
                }
            } else {
                ToastUtils.show(MainActivity.this, R.string.no_result);
            }
        } else {
            ToastUtils.showerror(this.getApplicationContext(), rcode);
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
                mEndPoint = new LatLonPoint(marker.getPosition().latitude, marker.getPosition().longitude);
                setSelectRouteVisibility(true);
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
        ToastUtils.show(this, infomation);
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onDistrictSearched(DistrictResult districtResult) {
        if (districtResult == null || districtResult.getDistrict() == null) {
            return;
        }
        //通过ErrorCode判断是否成功
        if (districtResult.getAMapException() != null && districtResult.getAMapException().getErrorCode() == AMapException.CODE_AMAP_SUCCESS) {
            final DistrictItem item = districtResult.getDistrict().get(0);
            if (item == null) {
                return;
            }
            LatLonPoint centerLatLng = item.getCenter();
            int index = -1;
            for (int i = 0; i < mHotMovieDatas.data.size(); i++) {
                if (item.getName().contains(mHotMovieDatas.data.get(i).address_city)) {
                    index = i;
                    break;
                }
            }
            if (centerLatLng != null && !mHasMoved) {
                mHasMoved = true;
                mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(34.757975, 113.665412), 5));
            }
            if (index != -1) {
                int clickMust = 0;
                for (HotMovieData.DataBean.MovieBean movie : mHotMovieDatas.data.get(index).movie) {
                    clickMust = Integer.parseInt(movie.click_value) > clickMust ? Integer.parseInt(movie.click_value) : clickMust;
                }
                int color = Color.parseColor("#50888888");
                if (clickMust > 10000) {
                    color = Color.parseColor("#50FF0000");
                } else if (clickMust > 5000) {
                    color = Color.parseColor("#5000FF00");
                } else if (clickMust > 2000) {
                    color = Color.parseColor("#500000FF");
                }
                LatLng latLng = new LatLng(centerLatLng.getLatitude(), centerLatLng.getLongitude());
                mAMap.addCircle(new CircleOptions().center(latLng)
                        .radius(40000).strokeColor(color)
                        .fillColor(color).strokeWidth(25));
            }
        } else {
            if (districtResult.getAMapException() != null) {
                ToastUtils.showerror(this.getApplicationContext(), districtResult.getAMapException().getErrorCode());
            }
        }

    }

    @Override
    public void onPOIClick(Poi poi) {
        boolean clickedIsWant = false;
        int clickIndex = -1;
        for (int i = 0; i < mHotMovieDatas.data.size(); i++) {
            if (mHotMovieDatas.data.get(i).address_city.contains(poi.getName())) {
                clickedIsWant = true;
                clickIndex = i;
                break;
            }
        }
        if (clickedIsWant) {
            setMovieDialogVisibility(true);
            if (mCityMarker != null) mCityMarker.remove();
            Marker marker = mAMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(poi.getCoordinate())
                    .title(poi.getName())
                    .snippet("详细信息")
                    .draggable(true));
            mCityMarker = marker;
            mAMap.moveCamera(CameraUpdateFactory.newLatLngZoom(poi.getCoordinate(), 5));
            setMovieData(clickIndex);
        }
    }

    private void setMovieData(final int index) {
        mAdapter = new DialogListAdapter(this, mHotMovieDatas.data.get(index));
        mMovieList.setAdapter(mAdapter);
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoader.getInstance().init(new ImageLoaderConfiguration.Builder(MainActivity.this).build());
        }
        ImageLoader.getInstance().displayImage(AMapUtil.getUriFromPath(this, mHotMovieDatas.data.get(index).movie.get(0).pic_url).toString(), mMovieImage);
        mMovieName = mHotMovieDatas.data.get(index).movie.get(0).name;
        mMovieDescribe.setText(mHotMovieDatas.data.get(index).movie.get(0).describe);
        mMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageLoader.getInstance().displayImage(AMapUtil.getUriFromPath(MainActivity.this, mHotMovieDatas.data.get(index).movie.get(position).pic_url).toString(), mMovieImage);
                mMovieDescribe.setText(mHotMovieDatas.data.get(index).movie.get(position).describe);
                mMovieName = mHotMovieDatas.data.get(index).movie.get(position).name;
                mAdapter.setSelectedItem(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        mMovieDescribe.setMovementMethod(new ScrollingMovementMethod());
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
            mStartPoint = new LatLonPoint(mAMap.getCameraPosition().target.latitude, mAMap.getCameraPosition().target.longitude);
            Log.d("gsy", "[" + "] " + "startPoint:" + mStartPoint.getLatitude() + "  " + mStartPoint.getLongitude());
            if (!mIsShow) {
                showRecommend();
                mIsShow = true;
            }
        }
    }

    /**
     * 显示用户推荐
     */
    private void showRecommend() {
        RegeocodeQuery query = new RegeocodeQuery(mStartPoint, 200,GeocodeSearch.AMAP);
        mGeocoderSearch.getFromLocationAsyn(query);
    }

    /**
     * 公交路线搜索
     */
    public void onBusClick(View view) {
        searchRouteResult(ROUTE_TYPE_BUS, RouteSearch.BusDefault);
        mDrive.setImageResource(R.drawable.route_drive_normal);
        mBus.setImageResource(R.drawable.route_bus_select);
        mWalk.setImageResource(R.drawable.route_walk_normal);
        mMapView.setVisibility(View.GONE);
        mCurrentIndex.setVisibility(View.GONE);
        mBusResultLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 驾车路线搜索
     */
    public void onDriveClick(View view) {
        searchRouteResult(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault);
        mDrive.setImageResource(R.drawable.route_drive_select);
        mBus.setImageResource(R.drawable.route_bus_normal);
        mWalk.setImageResource(R.drawable.route_walk_normal);
        mMapView.setVisibility(View.VISIBLE);
        mCurrentIndex.setVisibility(View.VISIBLE);
        mBusResultLayout.setVisibility(View.GONE);
    }

    /**
     * 步行路线搜索
     */
    public void onWalkClick(View view) {
        searchRouteResult(ROUTE_TYPE_WALK, RouteSearch.WalkDefault);
        mDrive.setImageResource(R.drawable.route_drive_normal);
        mBus.setImageResource(R.drawable.route_bus_normal);
        mWalk.setImageResource(R.drawable.route_walk_select);
        mMapView.setVisibility(View.VISIBLE);
        mCurrentIndex.setVisibility(View.VISIBLE);
        mBusResultLayout.setVisibility(View.GONE);
    }

    /**
     * 开始搜索路径规划方案
     */
    public void searchRouteResult(int routeType, int mode) {
        if (mStartPoint == null) {
            ToastUtils.show(this, "起点未设置");
            return;
        }
        if (mEndPoint == null) {
            ToastUtils.show(this, "终点未设置");
        }
        showProgressDialog();
        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
                mStartPoint, mEndPoint);
        if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
                    mCurrentCityName, 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
        } else if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
        }
    }

    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
        }
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(true);
        mProgressDialog.setMessage("正在搜索");
        mProgressDialog.show();
    }

    /**
     * 公交路线搜索结果方法回调
     */
    @Override
    public void onBusRouteSearched(BusRouteResult result, int errorCode) {
        dissmissProgressDialog();
        mBottomLayout.setVisibility(View.GONE);
        mAMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mBusRouteResult = result;
                    BusResultListAdapter mBusResultListAdapter = new BusResultListAdapter(MainActivity.this, mBusRouteResult);
                    mBusResultList.setAdapter(mBusResultListAdapter);
                } else if (result != null && result.getPaths() == null) {
                    ToastUtils.show(MainActivity.this, R.string.no_result);
                }
            } else {
                ToastUtils.show(MainActivity.this, R.string.no_result);
            }
        } else {
            ToastUtils.showerror(this.getApplicationContext(), errorCode);
        }
    }

    /**
     * 驾车路线搜索结果方法回调
     */
    @Override
    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
        dissmissProgressDialog();
        mAMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mDriveRouteResult = result;
                    final DrivePath drivePath = mDriveRouteResult.getPaths()
                            .get(0);
                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                            MainActivity.this, mAMap, drivePath,
                            mDriveRouteResult.getStartPos(),
                            mDriveRouteResult.getTargetPos(), null);
                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
                    drivingRouteOverlay.removeFromMap();
                    drivingRouteOverlay.addToMap();
                    drivingRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) drivePath.getDistance();
                    int dur = (int) drivePath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.VISIBLE);
                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
                    mRouteDetailDes.setText("打车约" + taxiCost + "元");
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this,
                                    DriveRouteDetailActivity.class);
                            intent.putExtra("drive_path", drivePath);
                            intent.putExtra("drive_result",
                                    mDriveRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (result != null && result.getPaths() == null) {
                    ToastUtils.show(MainActivity.this, R.string.no_result);
                }

            } else {
                ToastUtils.show(MainActivity.this, R.string.no_result);
            }
        } else {
            ToastUtils.showerror(this.getApplicationContext(), errorCode);
        }


    }

    /**
     * 步行路线搜索结果方法回调
     */
    @Override
    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
        dissmissProgressDialog();
        mAMap.clear();// 清理地图上的所有覆盖物
        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
            if (result != null && result.getPaths() != null) {
                if (result.getPaths().size() > 0) {
                    mWalkRouteResult = result;
                    final WalkPath walkPath = mWalkRouteResult.getPaths()
                            .get(0);
                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                            this, mAMap, walkPath,
                            mWalkRouteResult.getStartPos(),
                            mWalkRouteResult.getTargetPos());
                    walkRouteOverlay.removeFromMap();
                    walkRouteOverlay.addToMap();
                    walkRouteOverlay.zoomToSpan();
                    mBottomLayout.setVisibility(View.VISIBLE);
                    int dis = (int) walkPath.getDistance();
                    int dur = (int) walkPath.getDuration();
                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
                    mRotueTimeDes.setText(des);
                    mRouteDetailDes.setVisibility(View.GONE);
                    mBottomLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MainActivity.this,
                                    WalkRouteDetailActivity.class);
                            intent.putExtra("walk_path", walkPath);
                            intent.putExtra("walk_result",
                                    mWalkRouteResult);
                            startActivity(intent);
                        }
                    });
                } else if (result != null && result.getPaths() == null) {
                    ToastUtils.show(MainActivity.this, R.string.no_result);
                }

            } else {
                ToastUtils.show(MainActivity.this, R.string.no_result);
            }
        } else {
            ToastUtils.showerror(this.getApplicationContext(), errorCode);
        }
    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

    public void onClipNameClick(View view) {
        copyTextToClipboardManager(this, mMovieName);
        ToastUtils.show(this, "视频名字已复制");
    }

    public void onOpenBaiduClick(View view) {
        copyTextToClipboardManager(this, mMovieName);
        ToastUtils.show(this, "视频名字已复制");
        doStartApplicationWithPackageName("com.nuomi");
    }

    /**
     * 将文本复制到剪切板
     *
     * @param content 需要复制的内容
     */
    public void copyTextToClipboardManager(Context context, String content) {
        try {
            // 得到剪贴板管理器,自API11后使用android.content.ClipboardManager
            ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            cmb.setText(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 打开其他应用
     *
     * @param packagename 应用的包名
     */
    private void doStartApplicationWithPackageName(String packagename) {

        // 通过包名获取此APP详细信息，包括Activities、services、versioncode、name等等
        PackageInfo packageinfo = null;
        try {
            packageinfo = getPackageManager().getPackageInfo(packagename, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageinfo == null) {
            return;
        }

        // 创建一个类别为CATEGORY_LAUNCHER的该包名的Intent
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(packageinfo.packageName);

        // 通过getPackageManager()的queryIntentActivities方法遍历
        List<ResolveInfo> resolveinfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);

        ResolveInfo resolveinfo = resolveinfoList.iterator().next();
        if (resolveinfo != null) {
            // packagename = 参数packname
            String packageName = resolveinfo.activityInfo.packageName;
            // 这个就是我们要找的该APP的LAUNCHER的Activity[组织形式：packagename.mainActivityname]
            String className = resolveinfo.activityInfo.name;
            // LAUNCHER Intent
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);

            // 设置ComponentName参数1:packagename参数2:MainActivity路径
            ComponentName cn = new ComponentName(packageName, className);

            intent.setComponent(cn);
            startActivity(intent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setOnCancelListener(null)
                    .setTitle("提醒")
                    .setMessage("是否退出")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    }).setNegativeButton("取消", null);
            builder.show();//显示对话框
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
        String address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
        int index = -1;
        for (int j = 0; j < mHotMovieDatas.data.size(); j++) {
            if (address.contains(mHotMovieDatas.data.get(j).address_city)) {
                index = j;
                break;
            }
        }
        if (index != -1) {
            setMovieData(index);
            setMovieDialogVisibility(true);
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
