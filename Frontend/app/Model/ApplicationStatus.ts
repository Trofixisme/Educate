export enum ApplicationStatus {
    ACCEPTED = "ACCEPTED",
    REJECTED = "REJECTED",
    PENDING = "PENDING",
}

export const ApplicationStatusId: Record<ApplicationStatus, number> = {
    [ApplicationStatus.ACCEPTED]: 1,
    [ApplicationStatus.REJECTED]: 2,
    [ApplicationStatus.PENDING]: 3,
};