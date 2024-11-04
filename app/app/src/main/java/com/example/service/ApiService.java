package com.example.service;

import com.example.model.Location;
import com.example.model.MemberDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/join")
    Call<Void> registerUser(@Body MemberDTO member);

    @POST("/login")
    Call<Void> loginUser(@Body MemberDTO member);

    @GET("/locations")
    Call<List<Location>> getLocations();

    @GET("/member")
    Call<MemberDTO> getMemberDetails();

    // Updated method to handle search queries
    @POST("/search")
    Call<Location> postSearchQuery(@Body String query);
}
