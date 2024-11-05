package com.example.model;

public class LocationDetailResponseDTO {

    Long id;
    String address;
    String name;
    String recruitment_period;
    String job_category;
    String employment_type;
    String salary_type;
    Integer salary;
    String entry_type;
    String required_experience;
    String required_education;
    String major_field;
    String certifications;
    String responsible_agency;
    String call_info;

    public Long getId(){return id;}
    public String getAddress() {return address;}

    public String getName() {
        return name;
    }

    public String getRecruitment_period() {
        return recruitment_period;
    }

    public String getJob_category() {
        return job_category;
    }

    public String getEmployment_type() {
        return employment_type;
    }

    public String getSalary_type() {
        return salary_type;
    }

    public Integer getSalary() {
        return salary;
    }

    public String getEntry_type() {
        return entry_type;
    }

    public String getRequired_experience() {
        return required_experience;
    }

    public String getRequired_education() {
        return required_education;
    }

    public String getMajor_field() {
        return major_field;
    }

    public String getCertifications() {
        return certifications;
    }

    public String getResponsible_agency() {
        return responsible_agency;
    }

    public String getCall_info() {
        return call_info;
    }
}
