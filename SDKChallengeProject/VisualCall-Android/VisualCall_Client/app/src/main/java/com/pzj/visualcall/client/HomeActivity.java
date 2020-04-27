package com.pzj.visualcall.client;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pzj.visualcall.client.fragment.HomeBusinessFragment;
import com.pzj.visualcall.client.fragment.HomeMyFragment;
import com.pzj.visualcall.client.view.NoScrollViewPager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//主页 业务 我的
public class HomeActivity extends AppCompatActivity {


    @BindView(R.id.home_pager)
    NoScrollViewPager home_pager;

    @BindView(R.id.bnv_home_navigation)
    BottomNavigationView bnv_home_navigation;

    @BindView(R.id.tv_title)
    TextView tv_title;

    private List<Fragment> fragmentList=new ArrayList<>();
    private HomeBusinessFragment homeBusinessFragment;
    private HomeMyFragment homeMyFragment;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //初始化 View绑定框架
        ButterKnife.bind(this);

        //沉浸式状态栏
        QMUIStatusBarHelper.translucent(this);
        //设置状态栏白色字体和图标
        QMUIStatusBarHelper.setStatusBarDarkMode(this);

        //初始化页面
        homeBusinessFragment=new HomeBusinessFragment();
        homeMyFragment=new HomeMyFragment();

        fragmentList.add(homeBusinessFragment);
        fragmentList.add(homeMyFragment);

        //初始化页面填充器
        pagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);  //得到Fragment
            }

            @Override
            public int getCount() {
                return fragmentList.size();  //得到数量
            }
        };


        //设置页面适配器
        home_pager.setAdapter(pagerAdapter);
        home_pager.setOffscreenPageLimit(2);

        //设置页面滑动事件
      /*  home_pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bnv_home_navigation.setSelectedItemId(R.id.menu_yewu);
                        tv_title.setText("业务办理");
                        break;
                    case 1:
                        bnv_home_navigation.setSelectedItemId(R.id.menu_wode);
                        tv_title.setText("我的");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/


        //设置导航栏点击监听
        bnv_home_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.menu_yewu:
                        home_pager.setCurrentItem(0);
                        tv_title.setText("业务办理");
                        return true;
                    case R.id.menu_wode:
                        home_pager.setCurrentItem(1);
                        tv_title.setText("我的");
                        return true;
                }
                return false;
            }
        });

        //设置不改变导航栏图标颜色,为了显示未选中状态图标
        bnv_home_navigation.setItemIconTintList(null);

    }
}
