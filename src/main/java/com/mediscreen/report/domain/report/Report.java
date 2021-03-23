package com.mediscreen.report.domain.report;

public class Report {

    // To associate a report with a patient, if reports are to be stored in a DB down the road.
    private Integer patientId;
    private int age;
    private String riskLevel;

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

}
