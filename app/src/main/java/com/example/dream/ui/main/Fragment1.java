package com.example.dream.ui.main;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.example.dream.Background.GPSBackgroundService;
import com.example.dream.Background.Status;
import com.example.dream.R;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Fragment1 extends Fragment  implements View.OnClickListener {

    public Fragment1(){}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    TextView Data_Status;
    TextView Wifi_Status, Address;
    Button NewBtn_State;
    ImageView MyAddress;
    MyReceiver myReceiver;
    private String MyAddress_Longitude, MyAddress_Latitude, ServiceData_LongitudeList, ServiceData_LatitudeList;
    List<String> MyLocation_LongitudeList;
    List<String>  MyLocation_LatitudeList;
    private String mLongtitudeX = "&mapX=", mLatitudeY = "&mapY=", mPositionTail = "&radius=20000&listYN=Y&arrange=A";
    private String mPosition =  mLongtitudeX + mLatitudeY + mPositionTail;
    private  String lo = "", la = "";

    private double latitude;
    private double longitude;
    private ListView listView;
    public TextView n_address;
    private TextView Address_info;
    public double latitudeX; //검색위도
    public double longitudeY;//검색경도
    public String name;//검색이름
    public String address;//현위치
    private ArrayList<String> mArrayMarkerID;
    private static int mMarkerID;
    public TMapView tmapview;
    private String location;
    private ProgressBar progressBar;
    private ImageView Restart_Btn;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment1, container, false);

        Data_Status = (TextView)view.findViewById(R.id.data_status);
        Wifi_Status = (TextView)view.findViewById(R.id.wifi_status);
        MyAddress = (ImageView)view.findViewById(R.id.my_address);
        MyAddress.setOnClickListener(this);
        Address = (TextView) view.findViewById(R.id.address);
        progressBar = (ProgressBar)view.findViewById(R.id.progressbar);
        Restart_Btn = (ImageView)view.findViewById(R.id.restart_btn);
        Restart_Btn.setOnClickListener(this);

     /*   prepareBackgroundService();
        getActivity().startService(new Intent(getActivity(), GPSBackgroundService.class));*/

        mArrayMarkerID = new ArrayList<>();
        mMarkerID = 0;
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.map_view);
        n_address = (TextView)view.findViewById(R.id.n_address);
        Address_info = (TextView)view.findViewById(R.id.address_info);
        tmapview = new TMapView(getActivity());
        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);



        return view;


    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.restart_btn:
                progressBar.setVisibility(View.VISIBLE);
                prepareBackgroundService();
                getActivity().startService(new Intent(getActivity(), GPSBackgroundService.class));

                int status = Status.getConnectivityStatus(getContext());
                if(status == Status.TYPE_MOBILE){
                    setTextView1("연결됨");
                    if(status == Status.TYPE_WIFI){
                        setTextView2("연결됨");
                    }
                    else{
                        setTextView1("이용불가");
                    }
                }
                else if(status == Status.TYPE_WIFI){
                    setTextView2("연결됨");

                    if(status == Status.TYPE_MOBILE){
                        setTextView1("연결됨");
                    }
                    else{
                        setTextView1("이용불가");
                    }
                }
                else {
                    setTextView1("이용불가");
                    setTextView2("이용불가");
                }
                getActivity().stopService(new Intent(getActivity(),GPSBackgroundService.class));
                break;

            case R.id.my_address:
                prepareBackgroundService();
                getActivity().startService(new Intent(getActivity(), GPSBackgroundService.class));
               /* prepareBackgroundService();
                Toast.makeText(getContext(),"위치 찾는 중",Toast.LENGTH_LONG).show();
                getActivity().startService(new Intent(getActivity(), GPSBackgroundService.class));*/
              /*  getPosition();
                tmapview.setCenterPoint(longitude, latitude); //->현재위치 = centerpoint
                tmapview.setLocationPoint(longitude, latitude);*/
                //아래 네개 문장은 꼭 필요

                // 일단은 바로 멈추게 함
                //getActivity().stopService(new Intent(getActivity(),GPSBackgroundService.class));
                break;
            default:
                break;
        }
    }

    //서비스 시작하기전 준비
    private void prepareBackgroundService(){
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(GPSBackgroundService.MY_ACTION1);
        getActivity().registerReceiver(myReceiver, intentFilter);
    }

    //서비스에서 데이터 가져오는 부분
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            //사용자가 이동한 전체 위치
            ServiceData_LongitudeList = arg1.getStringExtra("ServiceData_longitudeList");
            ServiceData_LatitudeList = arg1.getStringExtra("ServiceData_latitudeList");
            longitude = arg1.getDoubleExtra("double_longitude",0);
            latitude = arg1.getDoubleExtra("double_latitude",0);
            getMyLocation();
            getPosition();
            tmapview.setCenterPoint(longitude, latitude); //->현재위치 = centerpoint
            tmapview.setLocationPoint(longitude, latitude);
            progressBar.setVisibility(View.GONE);
        }
    }

    //사용자의 현재 위치 MyAddress_Longitude, MyAddress_Latitude 에 저장해놓는 구문임.
    public void getMyLocation(){
        MyLocation_LongitudeList = Arrays.asList(ServiceData_LongitudeList.split("#"));
        if(! MyLocation_LongitudeList.isEmpty()){
            MyAddress_Longitude =  MyLocation_LongitudeList.get( MyLocation_LongitudeList.size()-1);
        }
        MyLocation_LatitudeList = Arrays.asList(ServiceData_LatitudeList.split("#"));
        if(! MyLocation_LatitudeList.isEmpty()){
            MyAddress_Latitude =  MyLocation_LatitudeList.get(MyLocation_LatitudeList.size()-1);
        }
    }


    private void getPosition(){
        /*if(getActivity().getIntent().getExtras() !=null) {
            Intent intent = getActivity().getIntent();
            lo = intent.getStringExtra("My_longitude"); //경도
            la = intent.getStringExtra("My_latitude");  //위도
            mLongtitudeX = mLongtitudeX + lo;
            mLatitudeY = mLatitudeY + la;
            mPosition = mLongtitudeX + mLatitudeY + mPositionTail;
        }*/

        lo = MyAddress_Longitude;
        la = MyAddress_Latitude;
        mLongtitudeX = mLongtitudeX + lo;
        mLatitudeY = mLatitudeY + la;
        mPosition = mLongtitudeX + mLatitudeY + mPositionTail;


        final Geocoder geocoder = new Geocoder(getActivity(),Locale.getDefault());

        List<Address> list = null;
        if(lo != null && la != null){
            try {
                double d1 = Double.parseDouble(lo);
                double d2 = Double.parseDouble(la);
                list = geocoder.getFromLocation(
                        d2, // 위도
                        d1, // 경도
                        2); // 얻어올 값의 개수
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
            }

            if (list != null) {
                if (list.size()==0) {
                    Address.setText("해당되는 주소 정보는 없습니다");
                } else {
                    Address.setText(list.get(0).toString());
                    String cut[] = list.get(0).toString().split(" ");
                    for(int i=0; i<cut.length; i++){
                        System.out.println("cut["+i+"] : " + cut[i]);
                    }
                    Address.setText(cut[1] + " " + cut[2] + " " + cut[3] );
                }
            }
        }

    }


    //텍스트뷰
    public void setTextView1(String text){Data_Status.setText(text);}
    //텍스트뷰
    public void setTextView2(String text){Wifi_Status.setText(text);}
}
