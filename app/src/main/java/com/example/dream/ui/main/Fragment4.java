package com.example.dream.ui.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.dream.R;

import java.util.ArrayList;

public class Fragment4 extends Fragment {

    public Fragment4(){}
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

   /* Button AddDrone_Btn, DeleteDrone_Btn;
    ListView Drone_list;
    ArrayList<String> items;
    ArrayAdapter adapter;

*/


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment4, container, false);
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
       /* items = new ArrayList<String>() ;
        adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_multiple_choice, items) ;
        Drone_list = (ListView)view.findViewById(R.id.drone_listview);
        Drone_list.setAdapter(adapter);
        AddDrone_Btn = (Button)view.findViewById(R.id.add);
        AddDrone_Btn.setOnClickListener(this);

        DeleteDrone_Btn = (Button)view.findViewById(R.id.deldrone_btn);
        DeleteDrone_Btn.setOnClickListener(this);*/

        return view;
    }

/*    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.adddrone_btn:
               int add_count;
               add_count = adapter.getCount();
               items.add("LIST"+Integer.toString(add_count+1));
               adapter.notifyDataSetChanged();
                break;

            case R.id.deldrone_btn:
                int del_count, checked;
                del_count = adapter.getCount();
                if(del_count>0){
                    checked = Drone_list.getCheckedItemPosition();
                    if(checked > -1 && checked < del_count){
                        items.remove(checked);
                        Drone_list.clearChoices();
                        adapter.notifyDataSetChanged();
                    }
                }
                break;

            default:
                break;
        }
    }*/
}
