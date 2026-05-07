import {useState} from "react";
import {IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Button, Chip} from "@heroui/react";
import "../app.css";
import "../CSS/Universal.css";
import type {Application} from "~/Model/Application";
import {ApplicationStatus} from "~/Model/ApplicationStatus";

export default function MyJobApplicants({ applications }: { applications: Application[] }) {
    const [statuses, setStatuses] = useState<Record<string, ApplicationStatus>>(
        Object.fromEntries(applications.map(a => [String(a.id), a.status ?? ApplicationStatus.PENDING]))
    );

    async function updateStatus(applicationId: string, status: ApplicationStatus) {
        console.log("applicationId being sent:", applicationId);
        const res = await fetch(`http://localhost:8050/api/application/${applicationId}/status`, {
            method: "PATCH",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({ status }),
        });
        if (!res.ok) {
            console.error("Failed to update status:", res.status, await res.text());
            return;
        }
        setStatuses(prev => ({ ...prev, [applicationId]: status }));
    }

    return (
        <>
            <IndexHeader />
            <main style={{padding: "35px", margin: "0 auto"}}>
                <h1 style={{fontSize: "32px", fontWeight: "bold", marginBottom: "4px"}}>
                    Applicants
                </h1>
                <p className="label-small" style={{fontSize: "13px", color: "var(--text-secondary)", marginBottom: "24px"}}>
                    {applications.length} Result{applications.length !== 1 ? "s" : ""}
                </p>

                {applications.length === 0 ? (
                    <div className="flex full-width" style={{height: "52vh"}}>
                        <h1 className="label-placeholder">No Applicants Yet.</h1>
                        <h6 className="label-placeholder" style={{fontSize: "18px", fontWeight: "normal"}}> They will show up soon, Hopefully.</h6>
                    </div>
                ) : (
                    applications.map((app) => {
                        const status = statuses[String(app.id)];
                        const decided = status === ApplicationStatus.ACCEPTED || status === ApplicationStatus.REJECTED;

                        return (
                            <div key={String(app.id)} style={{borderRadius: "60px", background: "var(--container-secondary)", boxShadow: "0 0 40px 0 rgba(0, 0, 0, 0.17)", backdropFilter: "blur(30px)", padding: "20px 28px", marginBottom: "12px", display: "flex", justifyContent: "space-between", gap: "16px",}}>
                                <div>
                                    <p style={{fontWeight: 600, fontSize: "16px", margin: "0 0 6px"}}>
                                        {app.fName} {app.lName}
                                    </p>
                                    <div style={{display: "flex", alignItems: "center", gap: "8px", flexWrap: "wrap"}}>
                                        <span style={{fontSize: "13px", color: "var(--text-secondary)"}}>
                                            {app.email}
                                        </span>
                                        <span style={{fontSize: "13px", color: "var(--text-secondary)"}}>
                                            {app.phoneNumber}
                                        </span>
                                        {decided && (
                                            <Chip
                                                size="sm"
                                                variant="soft"
                                                style={{
                                                    color: status === ApplicationStatus.ACCEPTED ? "#22c55e" : "#ef4444",
                                                }}
                                            >
                                                {status}
                                            </Chip>
                                        )}
                                    </div>
                                    {app.student?.cv && (
                                        <div style={{marginTop: "8px", display: "flex", flexDirection: "column", gap: "8px"}}>
                                            <div>
                                                <span style={{fontSize: "11px", fontWeight: 600, color: "var(--text-tertiary)", textTransform: "uppercase", letterSpacing: "0.08em"}}>Description</span>
                                                <p style={{margin: "2px 0", fontSize: "13px", color: "var(--text-secondary)"}}>{app.student.cv.description}</p>
                                            </div>
                                            <div>
                                                <span style={{fontSize: "11px", fontWeight: 600, color: "var(--text-tertiary)", textTransform: "uppercase", letterSpacing: "0.08em"}}>Past Experiences</span>
                                                <p style={{margin: "2px 0", fontSize: "13px", color: "var(--text-secondary)"}}>{app.student.cv.pastExperiences}</p>
                                            </div>
                                            <div>
                                                <span style={{fontSize: "11px", fontWeight: 600, color: "var(--text-tertiary)", textTransform: "uppercase", letterSpacing: "0.08em"}}>Projects</span>
                                                <p style={{margin: "2px 0", fontSize: "13px", color: "var(--text-secondary)"}}>{app.student.cv.projects}</p>
                                            </div>
                                        </div>
                                    )}
                                </div>

                                {!decided && (
                                    <div style={{display: "flex", gap: "8px", flexShrink: 0, alignItems: "center"}}>
                                        <Button onClick={() => updateStatus(String(app.id), ApplicationStatus.ACCEPTED)} style={{color: "#4ade80", background: "rgba(34,197,94,0.15)"}}>
                                            Accept
                                        </Button>
                                        <Button onClick={() => updateStatus(String(app.id), ApplicationStatus.REJECTED)} style={{color: "#f87171", background: "rgba(239,68,68,0.15)"}}>
                                            Reject
                                        </Button>
                                    </div>
                                )}
                            </div>
                        );
                    })
                )}
            </main>
        </>
    );
}