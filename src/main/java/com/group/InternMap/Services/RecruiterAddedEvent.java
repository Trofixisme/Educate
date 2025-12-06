package com.group.InternMap.Services;

import java.util.UUID;

public class RecruiterAddedEvent {
    private final UUID recruiterId;
    private final String companyId;

    public RecruiterAddedEvent(UUID recruiterId, String companyId) {
        this.recruiterId = recruiterId;
        this.companyId = companyId;
    }

    public UUID getRecruiterId() { return recruiterId; }
    public String getCompanyId() { return companyId; }
}
