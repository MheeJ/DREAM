package com.example.dream;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    com.example.dream.ui.main.SectionsPagerAdapter sectionsPagerAdapter = null;

   private int[] tabIcons={
            R.drawable.ic_web_black_24dp,
            R.drawable.ic_chat_bubble_outline_black_24dp,
            R.drawable.emer,
            R.drawable.ic_account_box_black_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sectionsPagerAdapter = new com.example.dream.ui.main.SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        for (int i = 0; i < sectionsPagerAdapter.getCount(); i++)
        {
            tabs.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

}