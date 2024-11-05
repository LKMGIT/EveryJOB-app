package com.example.service;

import com.example.model.LocationDetailResponseDTO;
import com.example.model.LocationSearchResponseDTO;
import com.example.model.MemberLoginRequestDTO;
import com.example.model.MemberRequestDTO;
import com.example.model.MemberResponseDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/member/join")
    Call<Void> registerUser(@Body MemberRequestDTO member);

    @POST("/member/login")
    Call<Void> loginUser(@Body MemberLoginRequestDTO member);

    @GET("/location/detail/{id}")
    Call<LocationDetailResponseDTO> getLocations(@Path("id") Long id);


    @GET("/member/detail")
    Call<MemberResponseDTO> getMemberDetails();

    // Updated method to handle search queries
    @GET("/location/{name}")
    Call<LocationSearchResponseDTO> postSearchQuery(@Path("name") String query);
}
