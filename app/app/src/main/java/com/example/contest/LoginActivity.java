package com.example.contest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.model.MemberLoginRequestDTO;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private EditText etId, etPw;
    private Button btnLogin;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // 사용자 ID와 패스워드를 입력받는 EditText 초기화
        etId = findViewById(R.id.et_id);
        etPw = findViewById(R.id.et_pw);

        // 로그인 버튼 초기화
        btnLogin = findViewById(R.id.btn_login);

        // Retrofit 클라이언트를 생성하고 ApiService 인터페이스 구현체 초기화
        Retrofit retrofit = RetrofitClient.getClient();
        apiService = retrofit.create(ApiService.class);

        // 로그인 버튼 클릭 이벤트 설정
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etId.getText().toString();  // 사용자 ID를 가져옴
                String pw = etPw.getText().toString();  // 사용자 비밀번호를 가져옴

                // 입력된 ID와 비밀번호를 사용해 로그인 시도
                loginUser(id, pw);
            }
        });
    }

    private void loginUser(String id, String pw){
        MemberLoginRequestDTO member = new MemberLoginRequestDTO(id, pw);  // 사용자 ID와 비밀번호를 가진 MemberDTO 객체 생성

        // ApiService의 loginUser 메서드를 호출하여 로그인 요청을 보냄
        apiService.loginUser(member).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ///////// 나중에 빼기 ////////////

                if (response.isSuccessful()) {  // 로그인 성공 시
                    Log.d("LoginActivity", "로그인 성공");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class); // 일단 넘어가기로 함
                    startActivity(intent);
                    finish(); // 여ㅣ까지ㅂㅈ
                    Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                } else {  // 로그인 실패 시
                    String errorMessage = "로그인 실패: " + response.message();
                    Log.e("LoginActivity", errorMessage);
                    Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {  // 서버 통신 실패 시
                String errorMessage = "로그인 실패: " + t.getMessage();
                Log.e("LoginActivity", errorMessage, t);

                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
