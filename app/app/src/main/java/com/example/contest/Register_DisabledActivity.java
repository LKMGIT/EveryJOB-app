//package com.example.contest;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.model.Disabled;
//import com.example.model.Member;
//import com.example.retrofit.RetrofitClient;
//import com.example.service.ApiService;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//
//public class Register_DisabledActivity extends AppCompatActivity {
//    public Disabled disabled;
//
//    private EditText etType, etSevere;
//    private Button btnRegister;
//    private ApiService apiService;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.account_register_disabled);
//
//        etType = findViewById(R.id.et_register_type);
//        etSevere = findViewById(R.id.et_register_severe);
//        btnRegister = findViewById(R.id.btn_register);
//
//        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080");
//        apiService = retrofit.create(ApiService.class);
//
//        btnRegister.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                disabled = new Disabled(
//                        etType.getText().toString(),
//                        etSevere.getText().toString());
//
//                registerDisabled(disabled);
//            }
//        });
//    }
//
//    private void registerDisabled(Disabled disabled) {
//        apiService.registerDisabled(disabled).enqueue(new Callback<Void>() {
//            @Override
//            public void onResponse(Call<Void> call, Response<Void> response) {
//                if (response.isSuccessful()) {
//                    Log.d("RegisterActivity", "회원가입 성공");
//                    Toast.makeText(Register_DisabledActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
//
//                    // MainActivity로 이동
//                    Intent intent = new Intent(Register_DisabledActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    String errorMessage = "회원가입 실패: " + response.message();
//                    Log.e("RegisterActivity", errorMessage);
//                    Toast.makeText(Register_DisabledActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<Void> call, Throwable t) {
//                String errorMessage = "회원가입 실패: " + t.getMessage();
//                Log.e("RegisterActivity", errorMessage, t);
//                Toast.makeText(Register_DisabledActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private String[] getTextAsLines(EditText editText) {
//        String text = editText.getText().toString();
//        return text.split("\\r?\\n");
//    }
//}
