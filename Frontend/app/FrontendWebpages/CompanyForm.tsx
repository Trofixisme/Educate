import React, { useState } from "react";
import {Alert, CloseButton, Spinner} from "@heroui/react";

// @ts-ignore
export default function RegisterCompany() {

    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState(null as string | null);

    // @ts-ignore
    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {

        e.preventDefault();
        setErrorMessage(null);
        setLoading(true);

        const formData = new FormData(e.currentTarget);

        try {
            const token = localStorage.getItem("token");
            const res = await fetch("http://localhost:8050/api/company/new", {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json",
                        "Authorization": `Bearer ${token}`,
                    },
                    body: JSON.stringify(Object.fromEntries(formData.entries())),
                }
            );

            setLoading(false);

            if (!res.ok) {
                const data = await res.json();
                console.log(data);
                setErrorMessage(data.detail || "Regitration failed");
                return;
            } else {
                history.go(-1);
            }
        } catch (error) {
            console.error(error);
            setErrorMessage("Server error or connection issue");
        } finally {
            setLoading(false);
        }
    }

    return (
        <div className="centered">
            <a href="/" style={{ borderRadius: "200px" }}>
                <img
                    src="/images/navi/Navi%20Unique.png"
                    alt="Logo"
                    style={{ width: "100px", height: "100px" }}
                />
            </a>
            <br/>

            <form className="container" onSubmit={handleSubmit}>

                {errorMessage && (
                    <>
                        <br/>
                        <Alert className="dark rounded-4xl" style={{background: "var(--component-secondary)"}} status="danger">
                            <Alert.Indicator className="pr-0">
                                <img src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Logo" style={{width: "20px", height: "20px"}}/>
                            </Alert.Indicator>
                            <Alert.Content>
                                <Alert.Title>
                                <div className="font-bold center" style={{marginTop: "2.2px", color: "rgb(225, 66, 69)"}}>
                                    {errorMessage}
                                </div>
                                </Alert.Title>
                            </Alert.Content>
                            <CloseButton style={{background: "var(--component-tertiary)", marginTop: "2.2px"}} onClick={() => setErrorMessage(null)} />
                        </Alert>
                        <br/>
                    </>
                )}

                {!errorMessage && (
                    <>
                        <h1 className="font-bold text-3xl m-2" style={{paddingTop: "12px"}}>Create a Company</h1>
                        <br/>
                    </>
                )}

                <label>Company name:</label>
                <input className="text-sm" type="text" name="name" placeholder="ex. InternMap" required/>

                <br/>
                <br/>

                <label>industry:</label>
                <input className="text-sm" type="text" placeholder="ex. tech" name="industry" required/>

                <br/>
                <br/>

                <label>Location:</label>
                <input className="text-sm" type="text" name="locationOfHQ" placeholder="ex. Zalun, Burma" required/>

                <br/>
                <br/>

                <label>Website:</label>
                <input className="text-sm" type="text" name="websiteURL" placeholder="https://InternMap.com"/>

                <br/>
                <label>Company Logo:</label>
                <input type="file" name="logo" accept="image/*" placeholder="Put your logo here"/>

                <br/>


                { loading ? <Spinner size="lg" color="current" /> : <><br /> <input className="text-lg" type="submit" value="Register Company" /></>}
                <br/>
            </form>
        </div>
    );
}
