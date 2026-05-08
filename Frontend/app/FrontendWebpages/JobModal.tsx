import {Button, Modal, Tooltip, type UseOverlayStateReturn} from "@heroui/react";
import type {JobPosting} from "~/Model/Jobs/JobPosting";
import React from "react";
import type {Internship} from "~/Model/Jobs/Internship";
import type {FullTime} from "~/Model/Jobs/FullTime";
import type {FreelanceProject} from "~/Model/Jobs/FreelanceProject";

export default function JobModal({overlayState, job, action, role}: {overlayState:UseOverlayStateReturn, job: JobPosting|null, action: {}, role: string}) {

    return (
        <>
            <Modal isOpen={overlayState.isOpen}>
                <Modal.Backdrop className="dark" variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="max-w-7xl rounded-4xl ">
                            <Modal.CloseTrigger onClick={() => overlayState.close()} />
                            <Modal.Header>
                                <div className="flex flex-row align-middle gap-3 items-center">
                                    <img style={{width: 40, borderRadius: 40}}
                                         src={
                                             job?.company?.logo
                                                 ? `http://127.0.0.1:8000/storage/${job?.company.logo}`
                                                 : "/images/navi/Navi%20Beta.png"
                                         } alt={job?.company?.name || "Company"}/>
                                <Modal.Heading>{job?.recruiter.fname + " " + job?.recruiter.lname}</Modal.Heading>
                                </div>
                            </Modal.Header>
                            <Modal.Body>
                                <div className="p-5">
                                    <br/>
                                    <div className="flex flex-row justify-between">
                                        <div>
                                            <h1 className="font-bold text-3xl" style={{color: "var(--text-primary)"}}>{job?.jobName}</h1>
                                            <div className="flex flex-row gap-3 items-center align-middle mt-1.5 ml-2">
                                                <a className="flex font-medium self-center" style={{color: "var(--text-secondary)"}}>{job?.company.name}</a>

                                                {job?.type == "Internship" && (
                                                    <div style={{padding: '1px 10px', background: 'linear-gradient(180deg, rgba(8, 109, 250, 0.8) 0%, rgba(27, 155, 254, 0.9) 100%)', borderRadius: 75, outline: '2px rgba(255, 255, 255, 0.20) solid', outlineOffset: '-2px', backdropFilter: 'blur(20px)', alignItems: 'center', justifyContent: 'center'}}>
                                                    <span style={{color: 'white', fontSize: 11, fontFamily: 'Inter', fontWeight: '800', whiteSpace: 'nowrap'}}>
                                                        <a href={`/job?s/${job?.id}`}>{job?.type}</a>
                                                    </span>
                                                    </div>
                                                )}

                                                {job?.type == "FullTime" && (
                                                    <div style={{padding: '1px 10px', background: 'linear-gradient(180deg, rgba(254, 27, 84, 0.8), rgba(251, 8, 8, 0.9))', borderRadius: 75, outline: '2px rgba(255, 255, 255, 0.20) solid', outlineOffset: '-2px', backdropFilter: 'blur(20px)', alignItems: 'center', justifyContent: 'center'}}>
                                                    <span style={{color: 'white', fontSize: 11, fontFamily: 'Inter', fontWeight: '800', whiteSpace: 'nowrap'}}>
                                                        <a href={`/job?s/${job?.id}`}>{job?.type}</a>
                                                    </span>
                                                    </div>
                                                )}

                                                {job?.type == "FreeLanceProject" && (
                                                    <div style={{padding: '1px 10px', background: 'linear-gradient(180deg, rgba(254, 126, 27, 1), rgba(251, 128, 8, 1))', borderRadius: 75, outline: '2px rgba(255, 255, 255, 0.20) solid', outlineOffset: '-2px', backdropFilter: 'blur(20px)', alignItems: 'center', justifyContent: 'center'}}>
                                                    <span style={{color: 'white', fontSize: 11, fontFamily: 'Inter', fontWeight: '800', whiteSpace: 'nowrap'}}>
                                                        <a href={`/job?s/${job?.id}`}>Freelance</a>
                                                    </span>
                                                    </div>
                                                )}
                                        </div>
                                    </div>

                                        <div className="flex flex-col gap-3.5 items-center align-middle">
                                            <Button variant="primary" className="font-bold pl-9 pr-9" onClick={() => {// @ts-ignore
                                                action()}} isDisabled={role != "[ROLE_STUDENT]"} >Apply</Button>

                                            <Tooltip delay={0}>
                                                <Button variant="ghost" className="font-bold" style={{color: "var(--text-secondary)"}}>Quick Apply</Button>
                                                <Tooltip.Content>
                                                    <p>Feature Unavailable</p>
                                                </Tooltip.Content>
                                            </Tooltip>
                                        </div>
                                    </div>

                                    <br/><br/>

                                    <h4 className="text-2xl font-bold mb-2" style={{color: "var(--text-primary)"}}>About this position</h4>

                                    <div className="flex flex-col gap-8" style={{padding: "20px 30px", background: "var(--component-secondary)", borderRadius: 40}}>
                                        <div>
                                            <label className="label-large font-semibold">Description</label>
                                            <p className="auto-capitalise mt-2 ml-1" style={{color: "var(--text-primary)"}}>{job?.jobDescription}</p>
                                        </div>

                                        <div>
                                            <label className="label-large font-semibold">Work location</label>
                                            <p className="auto-capitalise mt-2 ml-2 font-medium" style={{color: "var(--text-primary)"}}> - {job?.jobLocation ? job?.jobLocation : <a className="text-muted">Undefined</a>}</p>
                                        </div>

                                        {job?.type == "Internship" && (
                                        <div>
                                            <label className="text-lg font-semibold">Duration {(job as Internship).duration}</label>
                                        </div>
                                        )}

                                        {job?.type == "FullTime" && (
                                            <div>
                                                <label className="label-large font-semibold">Benefits</label>
                                                <p className="auto-capitalise mt-2 ml-1 font-medium" style={{color: "var(--text-primary)"}}>{(job as FullTime).benefits}</p>
                                            </div>
                                        )}

                                        {job?.type == "FreeLanceProject" && (
                                            <div>
                                                <label className="label-large font-semibold">Payout</label>
                                                <p className="auto-capitalise mt-2 ml-1" style={{color: "var(--text-primary)"}}>{(job as FreelanceProject).payout}</p>
                                            </div>
                                        )}

                                    </div>

                                    <br/><br/>

                                    <h4 className="text-2xl font-bold mb-2" style={{color: "var(--text-primary)"}}>Requirements</h4>

                                    <div className="flex flex-col gap-8" style={{padding: "20px 30px", background: "var(--component-secondary)", borderRadius: 40}}>
                                        <div>
                                            <p className="auto-capitalise ml-2" style={{color: "var(--text-primary)"}}>{job?.jobRequirements}</p>
                                        </div>

                                    </div>
                                </div>
                            </Modal.Body>
                        </Modal.Dialog>
                    </Modal.Container>
                </Modal.Backdrop>
            </Modal>
        </>
    );
}
