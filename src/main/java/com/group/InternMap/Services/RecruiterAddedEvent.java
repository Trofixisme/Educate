package com.group.InternMap.Services;

public class RecruiterAddedEvent {
    private final String recruiterId;
    private final String companyId;

    public RecruiterAddedEvent( String recruiterId, String companyId) {
        this.recruiterId = recruiterId;
        this.companyId = companyId;
    }

    public String getRecruiterId() { return recruiterId; }
    public String getCompanyId() { return companyId; }
}
