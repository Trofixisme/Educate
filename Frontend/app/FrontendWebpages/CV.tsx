import React, { useState } from "react";
import {Alert, Button, CloseButton, FieldError, FieldGroup, Fieldset, Form, Input, Label, Modal, TextField} from "@heroui/react";

// @ts-ignore
export default function CVForm({overlayState, student = null}: {overlayState: UseOverlayStateReturn, student: Student}) {

    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState(null as string | null);
    const onCVState = overlayState;

    const [cvState, setCv] = useState(student.cv)

    async function handleSubmit(e: any) {
        e.preventDefault();
        setErrorMessage(null);
        setLoading(true);

        try {
            const token = localStorage.getItem("token");
            const res = await fetch(
                "http://localhost:8050/api/cv/",
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Accept": "application/json",
                        "Authorization": `Bearer ${token}`,
                    },
                    body: JSON.stringify(cvState),
                }
            );

            setLoading(false);

            if (!res.ok) {
                const data = await res.json();
                console.log(data);
                setErrorMessage(data.message || "CV creation failed");
                return;
            } else {
                location.reload();
            }

        } catch (error) {
            console.error(error);
            setErrorMessage("Server error or connection issue");
        } finally {
            setLoading(false);
        }
    }

    return (
        <Modal isOpen={onCVState.isOpen}>
            <Modal.Backdrop className="dark" variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                <Modal.Container>
                    <Modal.Dialog className="max-w-lg rounded-4xl">
                        <Modal.CloseTrigger onClick={() => onCVState.close()} />
                        <Modal.Header>
                            <Modal.Heading>Compose a CV</Modal.Heading>
                            {errorMessage && (
                                <>
                                    <Alert className="dark rounded-4xl" style={{background: "var(--component-secondary)"}} status="danger">
                                        <Alert.Indicator className="pr-0">
                                            <img src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Logo" style={{width: "20px", height: "20px"}}/>
                                        </Alert.Indicator>
                                        <Alert.Content>
                                            <Alert.Title>
                                            <span className="font-bold" style={{marginTop: "2.2px", color: "rgb(225, 66, 69)"}}>
                                                {errorMessage}
                                            </span>
                                            </Alert.Title>
                                        </Alert.Content>
                                        <CloseButton style={{background: "var(--component-tertiary)", marginTop: "2.2px"}} onClick={() => setErrorMessage(null)} />
                                    </Alert>
                                </>
                            )}

                        </Modal.Header>
                        <Modal.Body>
                            <Form method="post" className="w-full" onSubmit={handleSubmit}>
                                <Fieldset>
                                    <FieldGroup>
                                        <TextField isRequired name="description" value={cvState?.description} validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                            <Label>About</Label>
                                            <Input placeholder="ex. Laravel & PHP expert" onChange={e => setCv((p: any) => ({...p, description: e.target.value}))}  />
                                            <FieldError />
                                        </TextField>
                                        <TextField isRequired name="past_experiences" value={cvState?.pastExperiences} validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                            <Label>Your past experiences</Label>
                                            <Input placeholder="Worked on..." onChange={e => setCv((p: any) => ({...p, pastExperiences: e.target.value}))} />
                                            <FieldError />
                                        </TextField>
                                        <TextField isRequired name="projects" value={cvState?.projects} validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                            <Label>Your projects</Label>
                                            <Input placeholder="Made InternMap" onChange={e => setCv((p: any) => ({...p, projects: e.target.value}))} />
                                            <FieldError />
                                        </TextField>
                                    </FieldGroup>
                                    <Fieldset.Actions>
                                        <Button className="full-width p-4" type="submit" isDisabled={loading}>
                                            {loading ? "Saving..." : "Save"}
                                        </Button>
                                    </Fieldset.Actions>
                                </Fieldset>
                            </Form>
                        </Modal.Body>
                    </Modal.Dialog>
                </Modal.Container>
            </Modal.Backdrop>
        </Modal>
    );
}