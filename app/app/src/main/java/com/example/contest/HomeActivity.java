package com.example.contest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kakaomaptest.MapActivity;
import com.example.model.LocationDetailResponseDTO;
import com.example.model.RealtimeJobResponseDTO;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;

import java.util.List;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 2;
    private CircleIndicator3 mIndicator;
    private Button btnMap, btnProfile;
    private ConstraintLayout grid1, grid2, grid3, grid4;
    private Long grid1_id, grid2_id, grid3_id, grid4_id;
    private TextView grid1_name, grid2_name, grid3_name, grid4_name;
    private TextView grid1_pay_type, grid2_pay_type, grid3_pay_type, grid4_pay_type;
    private TextView grid1_pay, grid2_pay, grid3_pay, grid4_pay;
    private TextView grid1_address, grid2_address, grid3_address, grid4_address;
    ApiService apiService;

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
        grid1_pay_type = findViewById(R.id.grid1_pay_type);
        grid2_pay_type = findViewById(R.id.grid2_pay_type);
        grid3_pay_type = findViewById(R.id.grid3_pay_type);
        grid4_pay_type = findViewById(R.id.grid4_pay_type);
        grid1_pay = findViewById(R.id.grid1_pay);
        grid2_pay = findViewById(R.id.grid2_pay);
        grid3_pay = findViewById(R.id.grid3_pay);
        grid4_pay = findViewById(R.id.grid4_pay);
        grid1_address = findViewById(R.id.grid1_address);
        grid2_address = findViewById(R.id.grid2_address);
        grid3_address = findViewById(R.id.grid3_address);
        grid4_address = findViewById(R.id.grid4_address);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(ApiService.class);

        setupViewPager();
        setupButtonListeners();
        setupGridListeners();

        // Call JobData method to fetch job data
        JobData();
    }

    private void setupViewPager() {
        mPager = findViewById(R.id.viewpager);
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);
        mIndicator = findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
        mIndicator.createIndicators(num_page, 0);
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        mPager.setCurrentItem(250);
        mPager.setOffscreenPageLimit(2);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                mIndicator.animatePageSelected(position % num_page);
            }
        });
    }

    private void setupButtonListeners() {
        btnMap.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, MapActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
    }

    private void setupGridListeners() {
        // grid1 클릭 시 CompanyActivity로 이동
        grid1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RealCompanyActivity.class);
            intent.putExtra("id", grid1_id); // grid1_id 전달
            startActivity(intent);
        });

        // grid2 클릭 시 CompanyActivity로 이동
        grid2.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RealCompanyActivity.class);
            intent.putExtra("id", grid2_id); // grid2_id 전달
            startActivity(intent);
        });

        // grid3 클릭 시 CompanyActivity로 이동
        grid3.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RealCompanyActivity.class);
            intent.putExtra("id", grid3_id); // grid3_id 전달
            startActivity(intent);
        });

        // grid4 클릭 시 CompanyActivity로 이동
        grid4.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RealCompanyActivity.class);
            intent.putExtra("id", grid4_id); // grid4_id 전달
            startActivity(intent);
        });
    }

    private void JobData() {
        apiService.getMainPageData().enqueue(new Callback<List<RealtimeJobResponseDTO>>() {
            @Override
            public void onResponse(Call<List<RealtimeJobResponseDTO>> call, Response<List<RealtimeJobResponseDTO>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<RealtimeJobResponseDTO> job = response.body();

                    // 각 grid의 id를 저장
                    grid1_id = job.get(0).getId();
                    grid2_id = job.get(1).getId();
                    grid3_id = job.get(2).getId();
                    grid4_id = job.get(3).getId();

                    grid1_name.setText(job.get(0).getName());
                    grid1_pay_type.setText(job.get(0).getSalaryType());
                    grid1_pay.setText(job.get(0).getSalary());
                    grid1_address.setText(job.get(0).getAddress());

                    grid2_name.setText(job.get(1).getName());
                    grid2_pay_type.setText(job.get(1).getSalaryType());
                    grid2_pay.setText(job.get(1).getSalary());
                    grid2_address.setText(job.get(1).getAddress());

                    grid3_name.setText(job.get(2).getName());
                    grid3_pay_type.setText(job.get(2).getSalaryType());
                    grid3_pay.setText(job.get(2).getSalary());
                    grid3_address.setText(job.get(2).getAddress());

                    grid4_name.setText(job.get(3).getName());
                    grid4_pay_type.setText(job.get(3).getSalaryType());
                    grid4_pay.setText(job.get(3).getSalary());
                    grid4_address.setText(job.get(3).getAddress());
                }
            }

            @Override
            public void onFailure(Call<List<RealtimeJobResponseDTO>> call, Throwable t) {
                Log.e("HomeActivity", "Failed to fetch job data", t);
            }
        });
    }

}
