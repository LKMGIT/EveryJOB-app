package com.example.contest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kakaomaptest.MapActivity;

import java.util.Map;

import me.relex.circleindicator.CircleIndicator3;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 2;
    private CircleIndicator3 mIndicator;
    private Button btnMap, btnProfile;
    private ConstraintLayout grid1, grid2, grid3, grid4;
    private TextView grid1_name, grid2_name, grid3_name, grid4_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        btnMap = findViewById(R.id.map_button);
        btnProfile = findViewById(R.id.profile_button);
        grid1 = findViewById(R.id.grid1);
        grid2 = findViewById(R.id.grid2);
        grid3 = findViewById(R.id.grid3);
        grid4 = findViewById(R.id.grid4);
        grid1_name = findViewById(R.id.grid1_name);
        grid2_name = findViewById(R.id.grid2_name);
        grid3_name = findViewById(R.id.grid3_name);
        grid4_name = findViewById(R.id.grid4_name);

        /**
         * 가로 슬라이드 뷰 Fragment
         */

        //ViewPager2
        mPager = findViewById(R.id.viewpager);
        //Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        //Indicator
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page,0);
        //ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        /**
         * 이 부분 조정하여 처음 시작하는 이미지 설정.
         * 2000장 생성하였으니 현재위치 1002로 설정하여
         * 좌 우로 슬라이딩 할 수 있게 함. 거의 무한대로
         */

        mPager.setCurrentItem(250); //시작 지점
        mPager.setOffscreenPageLimit(2); //최대 이미지 수

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mIndicator.animatePageSelected(position%num_page);
            }
        });


        //근처 일자리
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MapActivity.class);
                startActivity(intent);
            }
        });

        // 프로필 작성 / 수정
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        //레이아웃 클릭 시 회사 정보
        // grid1 레이아웃 클릭 시 이벤트 처리
        grid1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // grid1_name의 텍스트 값을 가져오기
                String companyName = grid1_name.getText().toString();

                // CompanyActivity로 데이터를 전달할 Intent 생성
                Intent intent = new Intent(HomeActivity.this, CompanyActivity.class);
                intent.putExtra("company_name", companyName); // 데이터를 Extra로 추가
                startActivity(intent); // CompanyActivity 시작
            }
        });


    }
}
