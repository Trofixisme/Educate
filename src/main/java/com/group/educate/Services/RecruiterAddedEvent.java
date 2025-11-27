package com.group.educate.Services;

public class RecruiterAddedEvent {
    private final Long recruiterId;
    private final Long companyId;

    public RecruiterAddedEvent(Long recruiterId, Long companyId) {
        this.recruiterId = recruiterId;
        this.companyId = companyId;
    }

    public Long getRecruiterId() { return recruiterId; }
    public Long getCompanyId() { return companyId; }
}
