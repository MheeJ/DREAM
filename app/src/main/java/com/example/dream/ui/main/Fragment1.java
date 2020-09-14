package com.example.dream.ui.main;


import android.app.usage.NetworkStats;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;


import com.example.dream.Background.Status;
import com.example.dream.R;

public class Fragment1 extends Fragment  implements View.OnClickListener {


    TextView Data_Status;
    TextView Wifi_Status;
    Button NewBtn_State;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment1, container, false);

        Data_Status = (TextView)view.findViewById(R.id.data_status);
        Wifi_Status = (TextView)view.findViewById(R.id.wifi_status);
        NewBtn_State = (Button)view.findViewById(R.id.newBtn_state);
        NewBtn_State.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.newBtn_state:
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
                break;

            default:
                break;
        }
    }
    //텍스트뷰
    public void setTextView1(String text){Data_Status.setText(text);}
    //텍스트뷰
    public void setTextView2(String text){Wifi_Status.setText(text);}
}
