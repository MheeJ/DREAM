package com.example.dream.ui.main;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.dream.R;
import com.example.dream.Beacon.BeaconService;
//import static com.facebook.accountkit.internal.AccountKitController.getApplicationContext;

public class Fragment2 extends Fragment implements View.OnClickListener{

    ToggleButton Toggle;
    private Button NewBtn;
    private TextView Beacon_Text;
    private final int PERMISSION_REQUEST_COARSE_LOCATION = 100;
    MyReceiver myReceiver;
    private String Beacon_Condition,DataFromService;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2, container, false);

        Beacon_Text = (TextView)view.findViewById(R.id.beacon_text);
        NewBtn = (Button)view.findViewById(R.id.newBtn);
        NewBtn.setOnClickListener(this);
        context = container.getContext();

        return view;
    }



    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.newBtn:
                Start_BeaconService();
                setToast("주변에 위치한 드론을 확인합니다.");
                break;

            default:
                break;
        }
    }

    //텍스트뷰
    public void setTextView(String text){Beacon_Text.setText(text);}

    //토스트
    public void  setToast(String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }

    //비콘 서비스 시작 준비
    public void Start_BeaconService(){
        //블루투스 이용 허가 부분
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }
        myReceiver = new MyReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BeaconService.MY_ACTION);
        getActivity().registerReceiver(myReceiver, intentFilter);
        Beacon_Condition = "TurnON";
        Intent intent = new Intent(getActivity(), BeaconService.class);
        intent.putExtra("Beacon_Condition",Beacon_Condition);
        getActivity().startService(intent);
    }


    //서비스에서 데이터 가져오는 부분
    private class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent arg1) {
            // TODO Auto-generated method stub
            DataFromService = arg1.getStringExtra("ServiceData");
            //UI에 표시하는 부분 여기에 입력하면 됨 _ 현재 접속된 비콘 이름 출력
            set_UI();
        }
    }

    //서비스에서 가져온 데이터 UI에 표시
    public void set_UI(){
        setToast(DataFromService+" 기기 등록이 완료되었습니다.");
        setTextView(DataFromService);
    }


}