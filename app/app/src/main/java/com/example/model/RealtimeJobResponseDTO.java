package com.example.model;

import com.google.gson.annotations.SerializedName;

public class RealtimeJobResponseDTO {

    @SerializedName("id")
    private Long id;

    @SerializedName("busplaName")
    private String name;

    @SerializedName("cntctNo")
    private String callInfo;

    @SerializedName("compAddr")
    private String address;

    @SerializedName("empType")
    private String employmentType;

    @SerializedName("enterType")
    private String entryType;

    @SerializedName("jobNm")
    private String jobCategory;

    @SerializedName("offerregDt")
    private int offerRegDt;

    @SerializedName("regDt")
    private int regDt;

    @SerializedName("regagnName")
    private String responsibleAgency;

    @SerializedName("reqCareer")
    private String requiredExperience;

    @SerializedName("reqEduc")
    private String requiredEducation;

    @SerializedName("reqLicens")
    private String certifications;

    @SerializedName("reqMajor")
    private String majorField;

    @SerializedName("rno")
    private int rno;

    @SerializedName("rnum")
    private int rnum;

    @SerializedName("salary")
    private String salary;

    @SerializedName("salaryType")
    private String salaryType;

    @SerializedName("termDate")
    private String recruitmentPeriod;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    // Getter and Setter methods
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCallInfo() { return callInfo; }
    public void setCallInfo(String callInfo) { this.callInfo = callInfo; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String employmentType) { this.employmentType = employmentType; }

    public String getEntryType() { return entryType; }
    public void setEntryType(String entryType) { this.entryType = entryType; }

    public String getJobCategory() { return jobCategory; }
    public void setJobCategory(String jobCategory) { this.jobCategory = jobCategory; }

    public int getOfferRegDt() { return offerRegDt; }
    public void setOfferRegDt(int offerRegDt) { this.offerRegDt = offerRegDt; }

    public int getRegDt() { return regDt; }
    public void setRegDt(int regDt) { this.regDt = regDt; }

    public String getResponsibleAgency() { return responsibleAgency; }
    public void setResponsibleAgency(String responsibleAgency) { this.responsibleAgency = responsibleAgency; }

    public String getRequiredExperience() { return requiredExperience; }
    public void setRequiredExperience(String requiredExperience) { this.requiredExperience = requiredExperience; }

    public String getRequiredEducation() { return requiredEducation; }
    public void setRequiredEducation(String requiredEducation) { this.requiredEducation = requiredEducation; }

    public String getCertifications() { return certifications; }
    public void setCertifications(String certifications) { this.certifications = certifications; }

    public String getMajorField() { return majorField; }
    public void setMajorField(String majorField) { this.majorField = majorField; }

    public int getRno() { return rno; }
    public void setRno(int rno) { this.rno = rno; }

    public int getRnum() { return rnum; }
    public void setRnum(int rnum) { this.rnum = rnum; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }

    public String getSalaryType() { return salaryType; }
    public void setSalaryType(String salaryType) { this.salaryType = salaryType; }

    public String getRecruitmentPeriod() { return recruitmentPeriod; }
    public void setRecruitmentPeriod(String recruitmentPeriod) { this.recruitmentPeriod = recruitmentPeriod; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
}
