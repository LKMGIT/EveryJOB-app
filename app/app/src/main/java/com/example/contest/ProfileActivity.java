package com.example.contest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.kakaomaptest.MapActivity;
import com.example.model.MemberDTO;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;

import me.relex.circleindicator.CircleIndicator3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ProfileActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView profile_name, profile_age, profile_call, profile_gender, profile_email, profile_address, profile_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        logo = findViewById(R.id.logo_imageView);
        profile_name = findViewById(R.id.profile_name);
        profile_call = findViewById(R.id.profile_call);
        profile_gender = findViewById(R.id.profile_gender);
        profile_email = findViewById(R.id.profile_email);
        profile_address = findViewById(R.id.profile_address);
        profile_intro = findViewById(R.id.profile_intro);
        profile_age = findViewById(R.id.profile_age);

        // Retrofit 인스턴스 생성
        String baseUrl = "http://your-server-url.com/"; // 서버의 URL을 입력합니다.
        Retrofit retrofit = RetrofitClient.getClient(baseUrl);

        // ApiService 인터페이스 구현
        ApiService apiService = retrofit.create(ApiService.class);
        apiService.getMemberDetails().enqueue(new Callback<MemberDTO>() {
            @Override
            public void onResponse(Call<MemberDTO> call, Response<MemberDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MemberDTO member = response.body();

                    // 데이터를 뷰에 설정
                    profile_name.setText(member.getName());
                    profile_call.setText(member.getCall());
                    profile_gender.setText(member.getGender());
                    profile_email.setText(member.getEmail());
                    profile_address.setText(member.getAddress());
                    profile_intro.setText(member.getIntro());
                    profile_age.setText(String.valueOf(member.getAge()));
                } else {
                    Log.e("MainActivity", "Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MemberDTO> call, Throwable t) {
                Log.e("MainActivity", "Failure: " + t.getMessage());
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

    }
}
