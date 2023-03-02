package com.bottombar;

import android.graphics.Color;
import android.os.Bundle;

import com.bottombar.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());

        //设置导航
        this.activityMainBinding.bN
                //设置导航高度和距离底部的距离
                .setBottomNavHeightAndMarginBottom(60, 2)
                //清除按钮文本
//                .clearBtnText()
                //设置按钮文本
                .setNavText("文本1", "文本2", "文本3", "文本4")
                //设置导航图片 （网路照片)
//                .setBtnImage("https://wenxin.baidu.com/younger/file/ERNIE-ViLG/59957d85ca80324be27bb60a3bb362eei4")
                //设置导航图片（资源的形式)
//                .setBtnImage(R.drawable.nav, R.drawable.nav, R.drawable.nav, R.drawable.nav, R.drawable.nav)
                //设置图片距离上下左右的距离，已经图片大小
                .setImageContainer(10, 2, 55, 90)
                //填充导航，设置曲线起点，曲线高度，绘制高度（一般等于导航高度)，绘制背景颜色，边线阴影和颜色
                .drawBessel(3f, 10, 60, Color.GRAY, 0, 0, 0, Color.GRAY)
                //构建自定义按钮
                .build();

    }

}