import {IndexHeader} from "~/FrontendWebpages/fragments/IndexHeaderAndFooter";
import {Alert, AlertDialog, Button, Chip, CloseButton, Modal, Table, useOverlayState} from "@heroui/react";
import type {Application} from "~/Model/Application";
import CVForm from "~/FrontendWebpages/CV";
import React, {useState} from "react";
import type {User} from "~/Model/Users/User";
import type {Recruiter} from "~/Model/Users/Recruiter";
import type {Company} from "~/Model/Company";
import type {Student} from "~/Model/Users/Student";

export default function Profile({userDetails}: { userDetails: User}) {

    let referenceEmail = userDetails.email;

    const [isEditOpen, setIsEditOpen] = useState(false);
    const deleteAccountAlert = useOverlayState({defaultOpen: false});

    const [editForm, setEditForm] = useState(userDetails);
    const [editLoading, setEditLoading] = useState(false);

    const [errorMessage, setErrorMessage] = useState(null as string | null);

    async function saveProfile() {
        console.log("called it");
        setEditLoading(true);
        setErrorMessage(null);

        let sendableData;
        let to;

        if (userDetails.role == "STUDENT") {
            sendableData = JSON.stringify(editForm as unknown as Student);
            to = "student";
            console.log(sendableData);
        } else if (userDetails.role == "RECRUITER") {
            sendableData = JSON.stringify(editForm as Recruiter);
            to = "recruiter";
        } else {
            sendableData = JSON.stringify(editForm as unknown as Admin);
        }

        const response = await fetch("http://localhost:8050/api/" + to + "/update", {
            method: "POST", // ⚠️ use POST (Laravel handles file uploads better)
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: sendableData,
        });

        const json = await response.json();
        console.log(json);

        if (!response.ok) {
            setErrorMessage(json.detail);
            console.log(json.detail);
            setEditLoading(false);
            return;
        } else {
            if (referenceEmail != userDetails.email) {
                localStorage.removeItem("token");
            }
        }

        setEditLoading(false);
        setIsEditOpen(false);
        window.location.reload();
    }

    async function deleteAccount() {

        setEditLoading(true)
        setErrorMessage(null);

        const response = await fetch("http://localhost:8050/REST/user/delete", {
            method: "DELETE",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
        });

        if (response.ok) {
            localStorage.removeItem("token");
            setEditLoading(false)
            window.location.href = "/";
        } else {
            console.error("Failed to delete account");
            setEditLoading(false)
            setErrorMessage("Failed to delete account: " + (await response.json()).detail);
        }
    }

    console.log(userDetails);

    const CVFormOverlayState = useOverlayState({defaultOpen: false});

    let applicationList: Application[] = (userDetails as unknown as Student).applications ? (userDetails as unknown as Student).applications : [];

    if (applicationList != null) {
        applicationList.sort((e, f) => {
            if (e.applicationDate < f.applicationDate) {
                return 1;
            } else if (e.applicationDate === f.applicationDate) {
                return 0;
            } else {
                return -1;
            }
        });

        for (let i = 0; i < applicationList.length; i++) {
            for (let j = applicationList.length - 1; j > i; j--) {
                if (applicationList[i].jobPosting.id == applicationList[j].jobPosting.id &&
                    applicationList[i].jobPosting.company.name == applicationList[j].jobPosting.company.name) {
                    applicationList.splice(j, 1);
                }
            }
        }
    }

    console.log(applicationList);

    return (
        <>
            <IndexHeader/>

            <div className="pl-17 pt-8">
                <div className="flex items-center gap-4 flex-row">
                    <img src="/images/navi/Navi%20Beta.png"
                         style={{display: "flex", width: "100px", height: "100px", borderRadius: "100%"}} alt="Unstable Logo"/>
                    <div style={{gap: "7px", display: "flex", flexDirection: "column"}}>
                        <section>
                            <p className="auto-capitalise text-3xl font-bold">{userDetails.fname + " " + userDetails.lname}</p>
                            <p>{userDetails.email}</p>
                        </section>
                        <div className="flex items-center gap-3 flex-row">
                            <Chip size="lg" >
                                <img src="/images/assets/calendar@4x.png" alt="calendar"
                                     style={{width: "17px", filter: "invert(1)"}}/>
                                <Chip.Label>{userDetails.createdAt?.toString().substring(0, 4)}</Chip.Label>
                            </Chip>
                            <Chip size="lg">
                                <img className="chip_icon" src="/images/assets/person.fill@4x.png" alt="person"
                                     style={{width: "15px"}}/>
                                <Chip.Label>{userDetails.role.charAt(0) + userDetails.role.toLowerCase().substring(1, userDetails.role.length)}</Chip.Label>
                            </Chip>
                            {userDetails.role == "RECRUITER" && (
                            <Chip size="lg">
                              <img className="chip_icon" src="/images/assets/suitcase.fill@4x.png" alt="suitcase"
                                   style={{width: "15px"}}/>
                                <Chip.Label className="auto-capitalise">{(userDetails as Recruiter).title}</Chip.Label>
                            </Chip>
                            )}
                            <Button
                                style={{ width: "32px", height: "32px", background: "var(--container-secondary)" }}
                                className="dark"
                                isIconOnly
                                onClick={() => setIsEditOpen(true)}>
                                <img src="/images/assets/pencil@4x.png" style={{ width: "16px", filter: "invert(0.3)" }} alt="pencil"/>
                            </Button>
                        </div>
                    </div>
                </div>
            </div>
                <div>

                    <br/><br/>

                    {/*// <!-- Student Fields -->*/}
                    {userDetails.role == "STUDENT" && (
                        <>
                            <h4 className="container-label">About</h4>

                            <div className="container-padded">
                                <div>
                                    <label className="label-small">Major</label>
                                    <p className="auto-capitalise">{(userDetails as unknown as Student).studentMajor}</p>
                                </div>

                                <div className="mb-3">
                                    <label className="label-small">Year</label>
                                    <p className="auto-capitalise">{(userDetails as unknown as Student).graduatingYear}</p>
                                </div>

                                <div className="mb-3">
                                    <label className="label-small">University</label>
                                    <p className="auto-capitalise">{(userDetails as unknown as Student).uniName}</p>
                                </div>
                            </div>

                            <br />
                            <br />

                            {/*// <!-- CV Section -->*/}
                            <div style={{display: "flex", flexDirection: "row", gap: "10px", alignItems: "center"}}>
                            <h4 className="container-label">Circulmn Vitae</h4>
                                {(userDetails as unknown as Student).cv ? (<Button style={{width: "32px", height: "32px", background: "var(--secondary-background-color)"}} className="dark" isIconOnly onClick={() => CVFormOverlayState.open()}>
                                    <img src="/images/assets/pencil@4x.png" style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                                </Button>) : (<Button style={{width: "32px", height: "32px", background: "var(--secondary-background-color)"}} className="dark" isIconOnly onClick={() => CVFormOverlayState.open()}>
                                    <img src="/images/assets/plus@4x.png" style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                                </Button>)}

                            </div>

                            <div className="container-padded">
                            {(userDetails as unknown as Student).cv ? (
                                <>
                                    <div>
                                        <label className="label-small">Professional Summary</label>
                                        <p className="auto-capitalise">{(userDetails as unknown as Student).cv.description}</p>
                                    </div>

                                    <div>
                                        <label className="label-small">Past Experiences</label>
                                        <p style={{whiteSpace: "pre-wrap"}}>{(userDetails as unknown as Student).cv.pastExperiences}</p>
                                    </div>

                                    <div>
                                        <label className="label-small">Projects</label>
                                        <p style={{whiteSpace: "pre-wrap"}}>{(userDetails as unknown as Student).cv.projects}</p>
                                    </div>
                                </>
                            ) : (
                                <p className="text-muted">You don't have a CV</p>
                            )}
                            </div>

                            <br/><br/>

                            {/*// <!-- Applications -->*/}
                            <h4 className="container-label">Jobs You Applied For</h4>

                            <div className="container-padded">
                                <div className="full-width" style={{display: "grid", justifyContent: "start", gridTemplateColumns: "repeat(auto-fit, minmax(270px, 0.2fr))", gap: "50px"}}>
                            {(userDetails as unknown as Student).applications.length == 0 ? (
                                <h2 className="text-xl font-bold text-gray-400">You haven't applied for anything.</h2>
                            ): (
                                applicationList.map((application: Application) => {

                                    return (

                                <div style={{display: "grid", gap: "10px", background: "var(--component-secondary)", gridTemplateColumns: "repeat(2, 1fr)", padding: "20px", borderRadius: "25px"}}>
                                    <div style={{display: "flex", flexDirection: "column", gap: "10px", alignItems: "start"}} >
                                        {application.status == "ACCEPTED" && (
                                            <div style={{padding: '4px 12px', background: 'linear-gradient(180deg, rgba(35, 230, 77, 1), rgba(21, 183, 18, 1))', borderRadius: 75, outline: '2px rgba(35, 183, 30, 0.20) solid',  backdropFilter: 'blur(20px)', alignItems: 'center', justifyContent: 'center'}}>
                                                <span style={{color: 'white', fontSize: 13, fontFamily: 'Inter', fontWeight: '800', whiteSpace: 'nowrap'}}>
                                                    <a>Accepted</a>
                                                </span>
                                            </div>
                                        )}

                                        {application.status == "REJECTED" && (
                                            <div style={{padding: '4px 12px', background: 'linear-gradient(180deg, rgba(255, 110, 110, 1), rgba(231, 1, 5, 1))', borderRadius: 75, outline: '2px rgba(231, 5, 4, 0.20) solid',  backdropFilter: 'blur(20px)', alignItems: 'center', justifyContent: 'center'}}>
                                                <span style={{color: 'white', fontSize: 13, fontFamily: 'Inter', fontWeight: '800', whiteSpace: 'nowrap'}}>
                                                    <a>Rejected</a>
                                                </span>
                                            </div>
                                        )}

                                        {application.status == "PENDING" && (
                                            <div style={{padding: '4px 12px', background: 'linear-gradient(180deg, rgba(110, 199, 255, 1), rgba(1, 113, 231, 1))', borderRadius: 75, outline: '2px rgba(110, 113, 245, 0.20) solid',  backdropFilter: 'blur(20px)', alignItems: 'center', justifyContent: 'center'}}>
                                                <span style={{color: 'white', fontSize: 13, fontFamily: 'Inter', fontWeight: '800', whiteSpace: 'nowrap'}}>
                                                    <a>Pending</a>
                                                </span>
                                            </div>
                                        )}
                                        <p className="auto-capitalise">{application.applicationDate.toString().substring(0, 10)}</p>
                                    </div>

                                    <div className="mb-3">
                                        <label className="label-small">Job Position</label>
                                        <p className="auto-capitalise">{application.jobPosting.jobName + " - " + application.jobPosting.company?.name}</p>
                                    </div>

                                    <div className="mb-3">
                                        <label className="label-small">Phone Number</label>
                                        <p className="auto-capitalise">{application.phoneNumber}</p>
                                    </div>
                                </div>
                                )}
                            ))}
                                </div>
                            </div>
                        </>
                    )}

                    {/*// <!-- Recruiter Fields -->*/}
                    {userDetails.role == "RECRUITER" && (
                        <>
                            <h4 className="container-label">Works At</h4>

                            <div className="container-padded">
                                {/*// <!-- If a recruiter has one or more companies -->*/}
                                {(userDetails as Recruiter).companies && (userDetails as Recruiter).companies.length > 0 ? (
                                    <>
                                    <Table variant="secondary">
                                        <Table.ResizableContainer>
                                            <Table.Content aria-label="Team members" className="min-w-150">

                                                <Table.Header>
                                                    <Table.Column isRowHeader>Logo<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Name<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Industry<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Page<Table.ColumnResizer/></Table.Column>
                                                    <Table.Column>Address<Table.ColumnResizer/></Table.Column>
                                                </Table.Header>

                                                <Table.Body>
                                                    {(userDetails as Recruiter).companies.map((company: Company, index: number) => (
                                                        <Table.Row key={index}>
                                                            <Table.Cell>
                                                                <img
                                                                    src={
                                                                        company.logo
                                                                            ? `http://localhost:8080/uploads/${company.logo}`
                                                                            : "/images/navi/Navi Beta.png"
                                                                    }
                                                                    style={{ width: "40px", height: "40px", objectFit: "contain" }}
                                                                />
                                                            </Table.Cell>
                                                            <Table.Cell>{company.name}</Table.Cell>
                                                            <Table.Cell>{company.industry}</Table.Cell>
                                                            <Table.Cell>{company.websiteURL}</Table.Cell>
                                                            <Table.Cell>{company.locationOfHQ}</Table.Cell>
                                                        </Table.Row>
                                                    ))}
                                                </Table.Body>

                                            </Table.Content>
                                        </Table.ResizableContainer>
                                    </Table>
                                    </>
                                ) : (
                                    <h1 className="text-gray-400">
                                        — You're not working for any company.
                                    </h1>
                                )}
                            </div>
                        </>
                    )}

                    {userDetails.role == "ADMIN" && (
                        <div className="flex items-center justify-center" style={{height: "52vh"}}>
                        <h1 className="label-placeholder">We don't have anything else to show</h1>
                        </div>
                    )}

                    <br/><br/>
            </div>

            {/*-------------profile edit-----------------*/}
            <Modal isOpen={isEditOpen} onOpenChange={setIsEditOpen}>
                <Modal.Backdrop variant="blur" isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="max-w-xl">
                            <Modal.CloseTrigger onClick={() => setIsEditOpen(false)}/>
                            <Modal.Header>
                                <Modal.Heading>Edit Profile</Modal.Heading>
                                {errorMessage && (
                                    <>
                                        <Alert className="dark rounded-4xl" style={{background: "var(--container-secondary)"}} status="danger">
                                            <Alert.Indicator>
                                                <img src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Logo" style={{width: "20px", height: "20px", aspectRatio: "1/1"}}/>
                                            </Alert.Indicator>
                                            <Alert.Content>
                                                <Alert.Title>
                                                    <p className="font-bold" style={{marginTop: "2.2px", color: "rgb(225, 66, 69)"}}>
                                                        {errorMessage}
                                                    </p>
                                                </Alert.Title>
                                            </Alert.Content>
                                            <CloseButton style={{background: "var(--component-tertiary)", marginTop: "2.2px"}} onClick={() => setErrorMessage(null)} />
                                        </Alert>
                                    </>
                                )}
                            </Modal.Header>
                            <Modal.Body className="space-y-4" style={{paddingTop: "20px"}}>
                                <div className="full-width flex flex-col gap-6">

                                    <div className="flex flex-row gap-4">
                                        <div className="full-width">
                                            <label className="flex label-small mb-1">First Name</label>
                                            <input
                                                type="text"
                                                className="text-sm"
                                                value={editForm.fname}
                                                onChange={e => setEditForm(p => ({ ...p, fname: e.target.value }))}
                                            />
                                        </div>
                                        <div className="full-width">
                                            <label className="flex label-small mb-1">Last Name</label>
                                            <input
                                                type="text"
                                                className="text-sm"
                                                value={editForm.lname}
                                                onChange={e => setEditForm(p => ({ ...p, lname: e.target.value }))}
                                            />
                                        </div>
                                    </div>

                                    <div>
                                        <label className="flex label-small">Email</label>
                                        <input
                                            type="email"
                                            className="text-sm pb-2"
                                            value={editForm.email}
                                            onChange={e => setEditForm(p => ({ ...p, email: e.target.value }))}
                                        />
                                        <div className="text-xs pt-2">- Changing your email address will force you to be <b>logged out</b></div>
                                    </div>

                                    {userDetails.role === "STUDENT" && (
                                        <>
                                            <div>
                                                <label className="flex label-small mb-1">Major</label>
                                                <input
                                                    type="text"
                                                    value={(editForm as unknown as Student).studentMajor}
                                                    onChange={e => setEditForm(p => ({ ...p, studentMajor: e.target.value }))}
                                                />
                                            </div>
                                            <div>
                                                <label className="flex label-small mb-1">Faculty</label>
                                                <input
                                                    type="text"
                                                    value={(editForm as unknown as Student).faculty}
                                                    onChange={e => setEditForm(p => ({ ...p, faculty: e.target.value }))}
                                                />
                                            </div>
                                            <div>
                                                <label className="flex label-small mb-1">University</label>
                                                <input
                                                    type="text"
                                                    value={(editForm as unknown as Student).uniName}
                                                    onChange={e => setEditForm(p => ({ ...p, uniName: e.target.value }))}
                                                />
                                            </div>
                                            <div>
                                                <label className="flex label-small mb-1">Graduating Year</label>
                                                <input
                                                    type="text"
                                                    value={(editForm as unknown as Student).graduatingYear}
                                                    onChange={e => setEditForm(p => ({ ...p, graduatingYear: e.target.value }))}
                                                />
                                            </div>
                                        </>
                                    )}

                                    {userDetails.role === "RECRUITER" && (
                                        <div>
                                            <label className="flex label-small mb-1">Work Title</label>
                                            <input
                                                type="text"
                                                className="text-sm"
                                                value={(editForm as Recruiter).title}
                                                onChange={e => setEditForm(p => ({ ...p, title: e.target.value }))}
                                            />
                                        </div>
                                    )}

                                    <Button className="full-width p-3" variant="danger-soft" onClick={() => deleteAccountAlert.open()}>Delete Account</Button>
                                </div>
                            </Modal.Body>
                            <Modal.Footer className="flex justify-end gap-6 mt-8">
                                <Button className="full-width p-3" slot="close" variant="tertiary" onClick={() => setIsEditOpen(false)}>
                                    Cancel
                                </Button>
                                <Button className="full-width p-3" onClick={() => saveProfile()} isDisabled={editLoading}>
                                    {editLoading ? "Saving..." : "Save"}
                                </Button>
                            </Modal.Footer>
                        </Modal.Dialog>
                    </Modal.Container>
                </Modal.Backdrop>
            </Modal>

            <AlertDialog isOpen={deleteAccountAlert.isOpen && errorMessage == null}>
                <AlertDialog.Backdrop variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                    <AlertDialog.Container>
                        <AlertDialog.Dialog>
                            <AlertDialog.Header>
                                <img className="w-8" src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Warn"/>
                                <AlertDialog.Heading>Delete account?</AlertDialog.Heading>
                            </AlertDialog.Header>
                            <AlertDialog.Body>
                                <p>Are you sure you want to <b>delete your account</b>? All data associated with you will be deleted with <b>no way to recover them</b>.</p>
                            </AlertDialog.Body>
                            <AlertDialog.Footer className="flex justify-end gap-4 mt-8">
                                <Button className="full-width p-3" slot="close" variant="tertiary" onClick={() => deleteAccountAlert.close()} >
                                    Cancel
                                </Button>

                                <Button className="full-width p-3" slot="close" onClick={() => deleteAccount()} variant="danger" isDisabled={editLoading}>
                                    {editLoading ? "Deleting..." : "Delete & sign out"}
                                </Button>
                            </AlertDialog.Footer>
                        </AlertDialog.Dialog>
                    </AlertDialog.Container>
                </AlertDialog.Backdrop>
            </AlertDialog>

            <CVForm overlayState={CVFormOverlayState} student={(userDetails as unknown as Student)}/>
        </>
    )
}