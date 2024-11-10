package com.example.contest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.model.MemberRequestDTO;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    public MemberRequestDTO member;

    String[] items = {
            "간장애",
            "국가유공",
            "뇌병변장애",
            "뇌전증장애",
            "시각장애",
            "신장장애",
            "심장장애",
            "안면장애",
            "언어장애",
            "자폐성장애",
            "장루요루장애",
            "정신장애",
            "지적장애",
            "지체장애",
            "청각장애",
            "특수교육법상 장애인",
            "호흡기장애" };

    private ConstraintLayout disabled_form;
    private EditText etId, etPw, etName, etAge, etEmail, etCall, etAddress, etIntro;
    private RadioGroup rgGender, rgSevere;
    private CheckBox cbDis;
    private Button btnRegister;
    private Spinner spType;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_register);

        disabled_form = findViewById(R.id.et_register_disabled_form);
        etId = findViewById(R.id.et_register_id);
        etPw = findViewById(R.id.et_register_pw);
        etName = findViewById(R.id.et_register_name);
        rgGender = findViewById(R.id.et_register_gender);
        rgSevere = findViewById(R.id.et_register_severe);
        etAge = findViewById(R.id.et_register_age);
        etEmail = findViewById(R.id.et_register_email);
        etCall = findViewById(R.id.et_register_call);
        etAddress = findViewById(R.id.et_register_address);
        etIntro = findViewById(R.id.et_register_intro);
        cbDis = findViewById(R.id.et_register_disabled);
        btnRegister = findViewById(R.id.btn_register);
        spType = findViewById(R.id.et_register_type);

        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(ApiService.class);

        // 장애 정보 폼 비활성화
        disabled_form.setVisibility(View.GONE);

        // 장애 여부 선택 리스트 어댑터 설정
        Spinner disabledSpinner = findViewById(R.id.et_register_type);
        ArrayAdapter<String> disabledAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, items);
        disabledAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        disabledSpinner.setAdapter(disabledAdapter);

        // 장애 여부 체크박스 리스너
        cbDis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    disabled_form.setVisibility(View.VISIBLE);
                } else {
                    disabled_form.setVisibility(View.GONE);
                }
            }
        });

        // 회원가입 버튼 리스너
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String gender, severe = null;
                    RadioButton radioButton = findViewById(rgGender.getCheckedRadioButtonId());
                    gender = radioButton.getText().toString();

                    if (cbDis.isChecked()) {
                        RadioButton radioButton2 = findViewById(rgSevere.getCheckedRadioButtonId());
                        severe = radioButton2.getText().toString(); // 타이핑 오류 수정

                        member = new MemberRequestDTO(
                                etId.getText().toString().trim(),
                                etPw.getText().toString().trim(),
                                etName.getText().toString().trim(),
                                gender,
                                Integer.parseInt(etAge.getText().toString().trim()),
                                etEmail.getText().toString().trim(),
                                etCall.getText().toString().trim(),
                                etAddress.getText().toString().trim(),
                                etIntro.getText().toString().trim(),
                                spType.getSelectedItem().toString(),
                                severe.equals("Yes")); // boolean 값으로 설정
                    } else {
                        member = new MemberRequestDTO(
                                etId.getText().toString().trim(),
                                etPw.getText().toString().trim(),
                                etName.getText().toString().trim(),
                                gender,
                                Integer.parseInt(etAge.getText().toString().trim()),
                                etEmail.getText().toString().trim(),
                                etCall.getText().toString().trim(),
                                etAddress.getText().toString().trim(),
                                etIntro.getText().toString().trim(),
                                null, // disabled_type에 null 전달
                                false); // is_severe에 false 전달
                    }

                    registerUser(member);
                } catch (NumberFormatException e) {
                    Log.e("RegisterActivity", "Invalid age format: " + e.getMessage(), e);
                    Toast.makeText(RegisterActivity.this, "올바른 나이 형식을 입력하세요.", Toast.LENGTH_SHORT).show();
                } catch (NullPointerException e) {
                    Log.e("RegisterActivity", "Required fields are missing: " + e.getMessage(), e);
                    Toast.makeText(RegisterActivity.this, "모든 필수 입력값을 확인하세요.", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e("RegisterActivity", "회원가입 데이터 생성 오류: " + e.getMessage(), e);
                    Toast.makeText(RegisterActivity.this, "회원가입 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // 회원가입 처리 함수
    private void registerUser(MemberRequestDTO member) {
        apiService.registerUser(member).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("RegisterActivity", "회원가입 성공");
                    Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMessage = "회원가입 실패: " + response.code() + " - " + response.message();
                    Log.e("RegisterActivity", errorMessage);
                    Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                String errorMessage = "회원가입 실패: " + t.getMessage();
                Log.e("RegisterActivity", errorMessage, t);
                Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
