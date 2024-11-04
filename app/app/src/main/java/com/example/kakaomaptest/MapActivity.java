package com.example.kakaomaptest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import com.example.model.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.contest.R;
import com.example.retrofit.RetrofitClient;
import com.example.service.ApiService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraUpdate;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MapActivity extends AppCompatActivity {
    // 위치 권한 요청 코드
    private final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    // 필요한 위치 권한 목록
    private final String[] locationPermissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng startPosition = null;
    private ProgressBar progressBar;
    private MapView mapView;
    private Label centerLabel;
    private ApiService apiService;
    private KakaoMap kakaoMap; // KakaoMap 인스턴스 저장

    private EditText search_bar;
    private Button search_button;

    // KakaoMap이 준비되었을 때 호출되는 콜백
    private KakaoMapReadyCallback readyCallback = new KakaoMapReadyCallback() {
        @Override
        public void onMapReady(@NonNull KakaoMap map) {
            kakaoMap = map; // KakaoMap 인스턴스 초기화
            progressBar.setVisibility(View.GONE); // 로딩 표시 제거
            LabelLayer layer = kakaoMap.getLabelManager().getLayer();
            centerLabel = layer.addLabel(LabelOptions.from("centerLabel", startPosition)
                    .setStyles(LabelStyle.from(R.drawable.red_dot_marker).setAnchorPoint(0.5f, 0.5f))
                    .setRank(1));
            fetchLocationData(); // 위치 데이터 가져오기
        }

        @NonNull
        @Override
        public LatLng getPosition() {
            return startPosition;
        }

        @NonNull
        @Override
        public int getZoomLevel() {
            return 17;
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        // UI 요소 초기화
        mapView = findViewById(R.id.map_view);
        progressBar = findViewById(R.id.progressBar);
        search_bar = findViewById(R.id.search_bar);
        search_button = findViewById(R.id.search_button);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Retrofit을 사용하여 ApiService 생성
        Retrofit retrofit = RetrofitClient.getClient("https://8308-220-69-208-119.ngrok-free.app"); // 실제 서버 URL로 변경
        apiService = retrofit.create(ApiService.class);

        // 위치 권한 확인 및 요청
        if (ContextCompat.checkSelfPermission(this, locationPermissions[0]) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, locationPermissions[1]) == PackageManager.PERMISSION_GRANTED) {
            getStartLocation(); // 위치 권한이 있는 경우 현재 위치 가져오기
        } else {
            ActivityCompat.requestPermissions(this, locationPermissions, LOCATION_PERMISSION_REQUEST_CODE);
        }

        // 검색 버튼 클릭 이벤트 설정
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = search_bar.getText().toString().trim();
                if (!query.isEmpty()) {
                    sendSearchQuery(query); // 검색어를 서버에 전송
                } else {
                    Toast.makeText(MapActivity.this, "검색어를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 앱이 다시 시작될 때 지도 초기화
        if (startPosition == null) {
            getStartLocation();
        } else {
            mapView.start(readyCallback);
        }
    }

    // 사용자의 현재 위치 가져오기
    @SuppressLint("MissingPermission")
    private void getStartLocation() {
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        startPosition = LatLng.from(location.getLatitude(), location.getLongitude());
                        mapView.start(readyCallback); // 지도 시작
                    }
                });
    }

    // 서버에서 위치 데이터를 가져오는 메서드
    private void fetchLocationData() {
        apiService.getLocations().enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Location> locations = response.body();

                    // 위치 데이터를 이용해 마커 추가
                    for (Location location : locations) {
                        addMarker(location.getLatitude(), location.getLongitude());
                    }
                } else {
                    System.err.println("응답이 성공적이지 않습니다: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    // 검색어를 서버에 전송하고 응답으로 위치를 받아오는 메서드
    private void sendSearchQuery(String query) {
        apiService.postSearchQuery(query).enqueue(new Callback<Location>() {
            @Override
            public void onResponse(Call<Location> call, Response<Location> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Location location = response.body();
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng searchPosition = LatLng.from(latitude, longitude);

                    if (kakaoMap != null) {
                        // 카메라를 검색된 위치로 이동
                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCenterPosition(searchPosition, 17);
                        kakaoMap.moveCamera(cameraUpdate);

                        // 마커를 검색된 위치에 추가
                        addMarker(latitude, longitude);
                    }
                    Toast.makeText(MapActivity.this, "검색 위치가 성공적으로 표시되었습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapActivity.this, "검색 요청에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Location> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MapActivity.this, "서버와의 연결에 실패했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 지도에 마커 추가
    private void addMarker(double latitude, double longitude) {
        LatLng position = LatLng.from(latitude, longitude);
        if (kakaoMap != null) {
            LabelLayer layer = kakaoMap.getLabelManager().getLayer();
            layer.addLabel(LabelOptions.from("", position)
                    .setStyles(LabelStyle.from(R.drawable.red_dot_marker).setAnchorPoint(0.5f, 0.5f))
                    .setRank(1));
        }
    }

    // 위치 권한 요청 결과 처리
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getStartLocation(); // 권한이 허용된 경우 현재 위치 가져오기
            } else {
                showPermissionDeniedDialog(); // 권한이 거부된 경우 다이얼로그 표시
            }
        }
    }

    // 위치 권한이 거부된 경우 다이얼로그 표시
    private void showPermissionDeniedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("위치 권한 거부시 앱을 사용할 수 없습니다.")
                .setPositiveButton("권한 설정하러 가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getPackageName()));
                            startActivity(intent); // 앱 설정 화면으로 이동
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                            startActivity(intent); // 앱 관리 화면으로 이동
                        } finally {
                            finish();
                        }
                    }
                })
                .setNegativeButton("앱 종료하기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish(); // 앱 종료
                    }
                })
                .setCancelable(false)
                .show();
    }
}
