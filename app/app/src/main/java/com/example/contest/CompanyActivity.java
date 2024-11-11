package com.example.contest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.model.LocationDetailResponseDTO;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompanyActivity extends AppCompatActivity {

    private static final String TAG = "CompanyActivity";
    private TextView company_name, company_address, salary_type, company_pay, company_job;
    private TextView company_emp, company_type, company_edu, company_maj;
    private TextView call_id;
    private ImageView logo, call_button, sms_button;

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
        call_id = findViewById(R.id.call_id);
        logo = findViewById(R.id.logo_imageView);
        call_button = findViewById(R.id.call_button);
        sms_button = findViewById(R.id.sms_button);  // SMS 버튼 추가

        Long id = getIntent().getLongExtra("id", -1);
        if (id != -1) {
            Log.d(TAG, "Received ID: " + id);
            fetchCompanyDetails(id);
        } else {
            Log.e(TAG, "Invalid company ID received");
            Toast.makeText(this, "잘못된 회사 ID입니다.", Toast.LENGTH_SHORT).show();
        }

        // 전화 번호 클릭 이벤트
        call_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:01012345678"));
                startActivity(intent);
            }
        });

        // SMS 버튼 클릭 이벤트
        sms_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SMS 작성 화면으로 이동
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:01012345678"));
                intent.putExtra("sms_body", "안녕하세요, 에브리잡 공고보고 연락드립니다"); 
                startActivity(intent);
            }
        });
    }

    private void fetchCompanyDetails(Long id) {
        ApiService apiService = RetrofitClient.getClient().create(ApiService.class);

        apiService.getLocations(id).enqueue(new Callback<LocationDetailResponseDTO>() {
            @Override
            public void onResponse(Call<LocationDetailResponseDTO> call, Response<LocationDetailResponseDTO> response) {
                if (response.isSuccessful() && response.body() != null) {
                    LocationDetailResponseDTO details = response.body();

                    company_name.setText(details.getName());
                    company_address.setText(details.getAddress());
                    salary_type.setText(details.getSalary_type());
                    company_pay.setText(String.valueOf(details.getSalary()));
                    company_job.setText(details.getJob_category());
                    company_emp.setText(details.getEmployment_type());
                    company_type.setText(details.getEntry_type());
                    company_edu.setText(details.getRequired_education());
                    company_maj.setText(details.getMajor_field());
                } else {
                    Log.e(TAG, "Failed to fetch company details: Response is unsuccessful or empty");
                    Toast.makeText(CompanyActivity.this, "회사 정보를 가져오는 데 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LocationDetailResponseDTO> call, Throwable t) {
                Log.e(TAG, "Error connecting to the server: " + t.getMessage(), t);
                Toast.makeText(CompanyActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Logo clicked, navigating to HomeActivity");
                Intent intent = new Intent(CompanyActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
