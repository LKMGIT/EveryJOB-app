package com.example.model;

public class MemberLoginRequestDTO {
    private String accountId;
    private String password;

    public MemberLoginRequestDTO(String accountId, String password){
        this.accountId = accountId;
        this.password = password;
    }

    public String getId(){return accountId;}
    public String getPassword(){return password;}

}
