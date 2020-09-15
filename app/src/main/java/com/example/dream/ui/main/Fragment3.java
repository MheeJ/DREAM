package com.example.dream.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dream.R;

public class Fragment3 extends Fragment implements View.OnClickListener{

    public Fragment3(){}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    TextView Fire_Number,Police_Number;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment3, container, false);

        Fire_Number = (TextView)view.findViewById(R.id.fire_number);
        Fire_Number.setOnClickListener(this);
        Police_Number = (TextView)view.findViewById(R.id.police_number);
        Police_Number.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.fire_number:
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/119"));
                startActivity(intent1);
                break;

            case R.id.police_number:
                Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/112"));
                startActivity(intent2);
                break;

            default:
                break;
        }
    }

}
