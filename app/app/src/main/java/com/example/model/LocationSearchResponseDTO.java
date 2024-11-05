package com.example.model;

import java.math.BigDecimal;
import java.math.BigInteger;

public class LocationSearchResponseDTO {

    private Long id;
    private BigDecimal latitude;
    private BigDecimal longitude;

    public LocationSearchResponseDTO(Long id, BigDecimal latitude, BigDecimal longitude){
        this.id = id;
        this.latitude = latitude;
        this. longitude = longitude;
    }

    public Long getId(){return id;}
    public BigDecimal getLatitude(){return latitude;}
    public BigDecimal getLongitude(){return longitude;}


}
