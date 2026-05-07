import {Alert, CloseButton, Spinner, Tabs} from "@heroui/react";
import React, {useState} from "react";
import type { RecruiterRegistrationDTO } from "~/Model/DTO/RecruiterRegistrationDTO";

export default function Signup() {

    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState(null as string | null);

    async function handleRecruiterRegister(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setErrorMessage(null);
        setLoading(true);
        const formData = new FormData(e.currentTarget);

        try {
            const sendableData: any = {
                user: {},
                company: {}
            };

            formData.forEach((value, key) => {
                if (key.startsWith("user.")) {
                    const field = key.split(".")[1];
                    // Mapping 'fname' and 'lname' to backend 'fName' and 'lName'
                    sendableData.user[field] = value;
                } else if (key.startsWith("company.")) {
                    const field = key.split(".")[1];
                    sendableData.company[field] = value;
                } else {
                    sendableData[key] = value;
                }
            });

            const response = await fetch("http://localhost:8050/api/recruiter/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                },
                body: JSON.stringify(sendableData),
            });

            if (!response.ok) {
                const data = await response.json();
                setErrorMessage(data.detail);
                return;
            }

            location.href = "/login";

        } catch (error) {
            console.error(error);
            setErrorMessage("Server error or connection issue");
        } finally {
            setLoading(false);
        }
    }

    async function handleStudentRegister(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setErrorMessage(null);
        setLoading(true);
        const formData = new FormData(e.currentTarget);

        try {
            const sendableData = Object.fromEntries(formData.entries()) as unknown as Student;

            const response = await fetch("http://localhost:8050/api/student/register", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                },
                body: JSON.stringify(sendableData),
            });

            if (!response.ok) {
                const data = await response.json();
                console.log(data);
                setErrorMessage(data.detail);
                return;
            }

            location.href = "/login";

        } catch (error) {
            console.error(error);
            setErrorMessage("Server error or connection issue");
        } finally {
            setLoading(false);
        }
    }

    async function handleAdminRegister( e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setErrorMessage(null);
        setLoading(true);
        const formData = new FormData(e.currentTarget);

        try {
            const sendableData = Object.fromEntries(formData.entries()) as unknown as Admin;
            sendableData.PermissionLevel = 0;

            const response = await fetch("http://localhost:8050/api/admin/", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                },
                body: JSON.stringify(sendableData),
            });

            if (!response.ok) {
                const data = await response.json();
                setErrorMessage(data.detail);
                return;
            }

            location.href = "/login";

        } catch (error) {
            console.error(error);
            setErrorMessage("Server error or connection issue");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="centered">
            <a href={"/"} style={{borderRadius: "200px"}} inert>
                <img src="/images/navi/Navi%20Unique.png" alt="Logo" width="100px" height="100px"/>
            </a>
            <br/>

            <div className="container">

                {errorMessage && (
                    <>
                        <br/>
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

                {!errorMessage && (
                    <>
                        <h1 className="font-bold text-3xl" style={{paddingTop: "25px"}}>Sign in</h1>
                    </>
                )}

                <br/><br/>
                <Tabs className="full-width " style={{margin: "-20px"}} defaultSelectedKey={"student"}>
                    <Tabs.ListContainer>
                        <Tabs.List aria-label="selection control">
                            <Tabs.Tab id="admin">
                                Admin
                                <Tabs.Indicator />
                            </Tabs.Tab>
                            <Tabs.Tab id="student">
                                Student
                                <Tabs.Indicator />
                            </Tabs.Tab>
                            <Tabs.Tab id="recruiter">
                                Recruiter
                                <Tabs.Indicator />
                            </Tabs.Tab>
                        </Tabs.List>
                    </Tabs.ListContainer>

                    <br/>

                    <Tabs.Panel id="admin" style={{padding: 0}}>

                        {/*ADMIN Sign in VIEW*/}
                        <form className="full-width" method="post" onSubmit={handleAdminRegister}>

                            <div style={{display: "grid", gridTemplateColumns: "1fr 1fr", gridGap: "20px"}}>
                                <label htmlFor="admin-first-name">First Name:</label>
                                <label htmlFor="admin-last-name">Last Name:</label>
                            </div>

                            <div style={{display: "grid", gridTemplateColumns: "1fr 1fr", gridGap: "20px"}}>
                                <input type="text" id="admin-first-name" className="text-sm" name="fname"
                                       placeholder="First name" required autoComplete="given-name"/>
                                <input type="text" id="admin-last-name" className="text-sm" name="lname"
                                       placeholder="Last name" required autoComplete="family-name"/>
                            </div>
                            <br/><br/>

                            <label htmlFor="admin-email">Email:</label>
                            <input type="email" id="admin-email" className="text-sm" name="email"
                                   placeholder="Email" required autoComplete="email"/>
                            <br/><br/>

                            <label htmlFor="admin-password">Password:</label>
                            <input type="password" id="admin-password" className="text-sm" name="password"
                                   placeholder="Password" required autoComplete="new-password"/>
                            <br/><br/>

                            { loading ? <Spinner size="lg" color="current" /> : <input className="text-lg" type="submit" value="Create Account"/>}
                        </form>

                    </Tabs.Panel>

                    <Tabs.Panel id="student" style={{padding: 0}}>

                        {/*STUDENT Sign in VIEW*/}
                        <form className="full-width" method="post" onSubmit={handleStudentRegister}>

                            <div style={{display: "grid", gridTemplateColumns: "1fr 1fr", gridGap: "20px"}}>
                                <label htmlFor="student-first-name">First Name:</label>
                                <label htmlFor="student-last-name">Last Name:</label>
                            </div>

                            <div style={{display: "grid", gridTemplateColumns: "1fr 1fr", gridGap: "20px"}}>
                                <input className="text-sm" type="text" id="student-first-name" name="fname" placeholder="Intern" required
                                       autoComplete="given-name"/>
                                <input className="text-sm" type="text" id="student-last-name" name="lname" placeholder="Map" required
                                       autoComplete="family-name"/>
                            </div>
                            <br/><br/>

                            <label htmlFor="student-email">Email:</label>
                            <input className="text-sm" type="email" id="student-email" name="email" placeholder="Email" required
                                   autoComplete="email"/>
                            <br/><br/>

                            <label htmlFor="student-password">Password:</label>
                            <input className="text-sm" type="password" id="student-password" name="password" placeholder="Password"
                                   required autoComplete="new-password"/>
                            <br/><br/>

                            <label htmlFor="graudating-year">Graduating Year:</label>
                            <input className="text-sm" type="text" id="graudating-year" name="graduatingYear" placeholder="2094"
                                   required/>
                            <br/><br/>

                            <label htmlFor="university">University:</label>
                            <input className="text-sm" type="text" id="university" name="uniName" placeholder="Harvard" required/>
                            <br/><br/>

                            <label htmlFor="major">Major:</label>
                            <input className="text-sm" type="text" id="major" name="studentMajor" placeholder="Major" required/>
                            <br/><br/>

                            <label htmlFor="faculty">Faculty:</label>
                            <input className="text-sm" type="text" id="faculty" name="faculty" placeholder="Arts & Design" required/>
                            <br/><br/>

                            { loading ? <Spinner size="lg" color="current" /> : <input className="text-lg" type="submit" value="Create Account"/>}
                        </form>

                    </Tabs.Panel>

                    <Tabs.Panel id="recruiter" style={{padding: 0}}>

                        {/*RECRUITER Sign in VIEW*/}
                        <form className="full-width" method="post" onSubmit={handleRecruiterRegister}>

                            <div style={{display: "grid", gridGap: "20px", gridTemplateColumns: "1fr 1fr"}}>
                                <label htmlFor="recruiter-first-name">First Name:</label>
                                <label htmlFor="recruiter-last-name">Last Name:</label>
                            </div>

                            <div style={{display: "grid", gridGap: "20px", gridTemplateColumns: "1fr 1fr", gap: "20px"}}>
                                <input className="text-sm" type="text" id="recruiter-first-name" name="user.fname" placeholder="Intern"
                                       required autoComplete="given-name"/>
                                <input className="text-sm" type="text" id="recruiter-last-name" name="user.lname" placeholder="Map"
                                       required autoComplete="family-name"/>
                            </div>
                            <br/><br/>

                            <label htmlFor="recruiter-email">Email:</label>
                            <input className="text-sm" type="email" id="recruiter-email" name="user.email"
                                   placeholder="example@intern.com" required autoComplete="email"/>
                            <br/><br/>

                            <label htmlFor="recruiter-password">Password:</label>
                            <input className="text-sm" type="password" id="recruiter-password" name="user.password"
                                   placeholder="Enter your password" required autoComplete="new-password"/>
                            <br/><br/>

                            <label htmlFor="Title">Job Title:</label>
                            <input className="text-sm" type="text" id="Title" name="user.title" placeholder="Chief Executive Officer"
                                   required/>
                            <br/><br/>

                            <label htmlFor="Company's Name">Company's Name:</label>
                            <input className="text-sm" type="text" id="Company's Name" name="company.name" placeholder="InternMap"/>
                            <br/><br/>

                            { loading ? <Spinner size="lg" color="current" /> : <input className="text-lg" type="submit" value="Create Account"/>}
                        </form>

                    </Tabs.Panel>
                </Tabs>

                <br/><br/>
                <p style={{justifySelf: "center", alignSelf: "center", fontSize: "14px", fontWeight: 400}}>Already
                    have an account? <a
                        style={{color: "rgb(49, 131, 254)", fontWeight: 600, borderRadius: "200px", textDecoration: "none"}}
                        href={"/login"}>Sign in</a></p>
                <br/>
            </div>
        </div>
    )
}