package com.example.dream.ui.main;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dream.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Fragment4 extends Fragment {

    public Fragment4(){}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    public double latitude; //현위치위도
    public double longitude; //현위치경도
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment4, container, false);

        mArrayMarkerID = new ArrayList<>();
        mMarkerID = 0;
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.map_view);
        n_address = (TextView) view.findViewById(R.id.n_address);
        Address_info = (TextView)view.findViewById(R.id.address_info);
        tmapview = new TMapView(getActivity());
        tmapview.setSKTMapApiKey("f14d574a-63eb-409b-8a59-8f895318bcdb");
        tmapview.setZoomLevel(15);
        tmapview.setMapType(TMapView.MAPTYPE_STANDARD);
        tmapview.setLanguage(TMapView.LANGUAGE_KOREAN);
        tmapview.setTrackingMode(true);
        tmapview.setSightVisible(true);
        linearLayout.addView(tmapview);

        getCurrentPosition();

        tmapview.setCenterPoint(longitude, latitude); //->현재위치 = centerpoint
        tmapview.setLocationPoint(longitude, latitude);

        makegeocoder();

        if(address != null) {
            findAllPoi(address + location); //-> 화장실 검색
        }
        return view;
    }

    public void getCurrentPosition() {
        Intent intent = getActivity().getIntent();
        longitude = intent.getExtras().getDouble("My_longitude_double");
        latitude = intent.getExtras().getDouble("My_latitude_double");
        location = intent.getExtras().getString("location");
    }

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
        public void onStatusChanged(String provider, int status, Bundle extras) {}
        public void onProviderEnabled(String provider) {}
        public void onProviderDisabled(String provider) {}
    };

    public void findAllPoi(String strData) {
        TMapData tmapdata = new TMapData();
        tmapdata.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
            @Override
            public void onFindAllPOI(ArrayList<TMapPOIItem> poiItem) {
                for (int i = 0; i < poiItem.size(); i++) {
                    TMapPOIItem item = poiItem.get(i);
                    name = item.getPOIName();
                    latitudeX = item.getPOIPoint().getLatitude();
                    longitudeY = item.getPOIPoint().getLongitude();
                    //showmarker(poiItem);
                }
            }
        });
    }

    public void makegeocoder(){
        final Geocoder geocoder = new Geocoder(getActivity());
        List<Address> list = null;
        try
        {
            list = geocoder.getFromLocation(latitude, longitude, 5);
        } catch(IOException e)
        {
            e.printStackTrace();
            Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if(list !=null)
        {
            if (list.size() == 0) {
                n_address.setText("해당되는 주소 정보는 없습니다");
                Address_info.setText("");
            } else {
                n_address.setText(list.get(0).toString());
                String cut[] = list.get(0).toString().split(" ");
                for (int i = 0; i < cut.length; i++) {
                    System.out.println("cut[" + i + "] : " + cut[i]);
                }
                n_address.setText(cut[2] + " ");
                address = n_address.getText().toString();
                Address_info.setText("주변"+location+"위치");
            }
        }
    }
}
