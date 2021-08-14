package com.example.videoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.adapter.MyPageAdapter;
import com.example.entity.TabEntity;
import com.example.fragment.CollectFragment;
import com.example.fragment.HomeFragment;
import com.example.fragment.MyFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity {

    private String[] mTitle = {"首页", "资讯", "我的"};
    private int[] mIconUnselectIds = {R.mipmap.home_unselect,
            R.mipmap.collect_unselect, R.mipmap.my_unselect};
    private int[] mIconSelectIds = {R.mipmap.home_selected,
            R.mipmap.collect_selected, R.mipmap.my_selected};
    private ArrayList<Fragment> mFragment = new ArrayList<>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private ViewPager viewPager;
    private CommonTabLayout commonTabLayout;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        initView();
//        initLayout();
//        initData();
//
//    }

    @Override
    protected int initLayout(){
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.viewpager);
        commonTabLayout = findViewById(R.id.commonTabLayout);
    }

    @Override
    protected void initData(){
        mFragment.add(HomeFragment.newInstance());
        mFragment.add(CollectFragment.newInstance());
        mFragment.add(MyFragment.newInstance());
        for (int i = 0; i < mTitle.length; i++){
            mTabEntities.add(new TabEntity(mTitle[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {
            }
        });
        viewPager.setOffscreenPageLimit(mFragment.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new MyPageAdapter(getSupportFragmentManager(), mTitle, mFragment));
    }

}