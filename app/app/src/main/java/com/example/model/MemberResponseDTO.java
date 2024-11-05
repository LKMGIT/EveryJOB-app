package com.example.model;

public class MemberResponseDTO {
    private String accountId;
    private String name;
    private String gender;
    private int age;
    private String email;
    private String call_info;
    private String address;
    private String intro;
    private String disabled_type;
    private String is_severe;

    public MemberResponseDTO(String accountId,  String name, String gender, int age,
                            String email, String call_info, String address, String intro, String disabled_type, String is_severe) {
        this.accountId = accountId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.email = email;
        this.call_info = call_info;
        this.address = address;
        this.intro = intro;
        this.disabled_type = disabled_type;
        this.is_severe = is_severe;
    }


    public String getId() {
        return accountId;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getCall() {
        return call_info;
    }

    public String getAddress() {
        return address;
    }

    public String getIntro() {
        return intro;
    }

    public String getDisabled_id() {return disabled_type;}

    public String getIs_severe(){return is_severe;}
}
