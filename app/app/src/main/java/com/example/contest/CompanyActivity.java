package com.example.contest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log; // Log 클래스 추가
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.LocationDetailResponseDTO;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CompanyActivity extends AppCompatActivity {

    private static final String TAG = "CompanyActivity"; // 태그 추가
    private TextView company_name, company_address, salary_type, company_pay, company_job, company_emp, company_type, company_edu, company_maj;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.company_info);

        company_name = findViewById(R.id.company_name);
        company_address = findViewById(R.id.company_address);
        salary_type = findViewById(R.id.salary_type);
        company_pay = findViewById(R.id.company_pay);
        company_job = findViewById(R.id.company_job);
        company_emp = findViewById(R.id.company_emp);
        company_type = findViewById(R.id.company_type);
        company_edu = findViewById(R.id.company_edu);
        company_maj = findViewById(R.id.company_maj);
        logo = findViewById(R.id.logo_imageView);

        // Intent에서 id 값 받기
        Long id = getIntent().getLongExtra("id", -1);
        if (id != -1) {
            Log.d(TAG, "Received ID: " + id); // 성공적으로 ID를 받았을 때 로그 출력
            fetchCompanyDetails(id); // Retrofit을 이용해 데이터 요청
        } else {
            Log.e(TAG, "Invalid company ID received"); // 잘못된 ID 로그 출력
            Toast.makeText(this, "잘못된 회사 ID입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchCompanyDetails(Long id) {
        // Retrofit 초기화
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        // 서버에 요청 보내기
        apiService.getLocations(id).enqueue(new Callback<LocationDetailResponseDTO>() {
            @Override
            public void onResponse(Call<LocationDetailResponseDTO> call, Response<LocationDetailResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LocationDetailResponseDTO details = response.body();

                    // 데이터 표시
                    company_name.setText(details.getName());
                    company_address.setText(details.getAddress());
                    salary_type.setText(details.getSalary_type());
                    company_pay.setText(String.valueOf(details.getSalary())); // 숫자를 문자열로 변환
                    company_job.setText(details.getJob_category());
                    company_emp.setText(details.getEmployment_type());
                    company_type.setText(details.getEntry_type());
                    company_edu.setText(details.getRequired_education());
                    company_maj.setText(details.getMajor_field());
                } else {
                    Log.e(TAG, "Failed to fetch company details: Response is unsuccessful or empty"); // 실패 로그 출력
                    Toast.makeText(CompanyActivity.this, "회사 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LocationDetailResponseDTO> call, Throwable t) {
                Log.e(TAG, "Error connecting to the server: " + t.getMessage(), t); // 실패 로그 출력
                Toast.makeText(CompanyActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
        // 로고 클릭 이벤트
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Logo clicked, navigating to HomeActivity"); // 로고 클릭 로그 출력
                Intent intent = new Intent(CompanyActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

}
