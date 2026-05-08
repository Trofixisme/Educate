import "../CSS/jobPosting.css"
import "../CSS/InternMapHomepage.css";
import {
    Alert, CloseButton,
    FieldError,
    FieldGroup,
    Fieldset,
    Form,
    Input,
    Label,
    Modal, Spinner,
    TextField, toast, type UseOverlayStateReturn
} from "@heroui/react";
import React, {useState} from "react";
import type {JobPosting} from "~/Model/Jobs/JobPosting";

export default function ApplicationForm({overlayState, job}: {overlayState: UseOverlayStateReturn ,job: JobPosting | null}) {

    const onApplicationState = overlayState;
    const [loading, setLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState(null as string | null);

    async function handleSubmit(e: any) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);

        setLoading(true);
        setErrorMessage(null);

        const params = new URLSearchParams({
            jobId: String(job?.id),
            fname: formData.get("f_name") as string,
            lname: formData.get("l_name") as string,
            phone: formData.get("phone_number") as string,
            email: formData.get("email") as string,
        });

        console.log("sending:", params.toString());

        const res = await fetch(`http://localhost:8050/api/application/apply/submit?${params.toString()}`, {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                "Content-Type": "application/json",
            },
        });

        if (!res.ok) {
            const errorText = await res.json();
            setErrorMessage(errorText.detail);
            console.error("Submission failed:", res.status, errorText);
            return;
        } else {
            onApplicationState.close();
            toast("Sucessfully applied!", {
                actionProps: {
                    children: "Dismiss",
                    onPress: () => toast.clear(),
                    variant: "tertiary",
                },
                indicator: <img src="/images/assets/checkmark@4x.png" alt="checkmark" width={15} height={15}/>,
                description: "successfully applied to " + job?.jobName,
                variant: "success",
            })
            console.log("Application submitted!");
        }

    }
    return (
        <>
            <Modal isOpen={onApplicationState.isOpen}>
                <Modal.Backdrop className="dark" variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="max-w-lg rounded-4xl">
                            <Modal.CloseTrigger onClick={() => onApplicationState.close()} />
                            <Modal.Header>
                                <Modal.Heading>Apply for position</Modal.Heading>
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
                                <Form method="post" onSubmit={handleSubmit}>
                                    <Fieldset>
                                        <FieldGroup className="flex flex-col gap-3 pt-7">
                                            <div className="flex flex-row justify-between gap-5">
                                                <TextField className="full-width" isRequired name="f_name" validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                                    <Label>First Name</Label>
                                                    <Input placeholder="Mina" />
                                                    <FieldError />
                                                </TextField>
                                                <TextField className="full-width" isRequired name="l_name" validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                                    <Label>Last Name</Label>
                                                    <Input placeholder="Youssef" />
                                                    <FieldError />
                                                </TextField>
                                            </div>
                                            <TextField isRequired name="email" type="email">
                                                <Label>Email</Label>
                                                <Input placeholder="Mina@InternMap.com" />
                                                <FieldError />
                                            </TextField>
                                            <TextField isRequired name="phone_number" validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                                <Label>Phone</Label>
                                                <Input placeholder="+98-012345678" />
                                                <FieldError />
                                            </TextField>
                                        </FieldGroup>
                                        <Fieldset.Actions>
                                            { loading ? <Spinner size="lg" color="current" /> : <><br /> <input className="text-lg" type="submit" value="Apply" /></>}
                                        </Fieldset.Actions>
                                    </Fieldset>
                                </Form>
                            </Modal.Body>
                        </Modal.Dialog>
                    </Modal.Container>
                </Modal.Backdrop>
            </Modal>
        </>
    );
}
