package com.example.taoch.lbstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class MainActivity extends AppCompatActivity  {

    private TextView jd, wd, hb, city, tq;
    int n=-1;
    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        jd = (TextView)findViewById(R.id.jd);
        wd = (TextView)findViewById(R.id.wd);
        hb = (TextView)findViewById(R.id.hb);
        city = (TextView)findViewById(R.id.city);
        tq = (TextView)findViewById(R.id.tq);

        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        mLocationClient.registerLocationListener(myListener); //注册监听函数
        initLocation();
        mLocationClient.start();//调用LocationClient的start()方法，便可发起定位请求

    }
    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        /**可选，设置定位模式，默认高精度LocationMode.Hight_Accuracy：高精度；
         * LocationMode. Battery_Saving：低功耗；LocationMode. Device_Sensors：仅使用设备；*/

        option.setCoorType("bd09ll");
        /**可选，设置返回经纬度坐标类型，默认gcj02gcj02：国测局坐标；bd09ll：百度经纬度坐标；bd09：百度墨卡托坐标；
         海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标*/

        option.setScanSpan(60000);
        /**可选，设置发起定位请求的间隔，int类型，单位ms如果设置为0，则代表单次定位，即仅定位一次，默认为0如果设置非0，需设置1000ms以上才有效*/

        option.setOpenGps(true);
        /**可选，设置是否使用gps，默认false使用高精度和仅用设备两种定位模式的，参数必须设置为true*/

        option.setLocationNotify(false);
/**可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false*/

        option.setIgnoreKillProcess(false);
        /**定位SDK内部是一个service，并放到了独立进程。设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)*/

        option.SetIgnoreCacheException(true);
        /**可选，设置是否收集Crash信息，默认收集，即参数为false*/
        option.setIsNeedAltitude(true);/**设置海拔高度*/

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        /**可选，7.2版本新增能力如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位*/

        option.setEnableSimulateGps(true);
        /**可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false*/

        option.setIsNeedAddress(true);
        /**可选，设置是否需要地址信息，默认不需要*/

        mLocationClient.setLocOption(option);
        /**mLocationClient为第二步初始化过的LocationClient对象需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用*/
    }

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
             n=n+1;
            jd.setText("经度信息：" + longitude);
            wd.setText("纬度信息：" + latitude);
            hb.setText("海拔信息：" + location.getAltitude() + "m");
            tq.setText("获取次数"+n+"次");
            city.setText("" + location.getCity() + " " + location.getDistrict());

            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            String coorType = location.getCoorType();
            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明

        }

    }


}