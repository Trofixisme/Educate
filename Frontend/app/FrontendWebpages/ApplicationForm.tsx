import "../CSS/jobPosting.css"
import "../CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from './fragments/IndexHeaderAndFooter';
import {
    Button,
    Description,
    FieldError,
    FieldGroup,
    Fieldset,
    Form,
    Input,
    Label,
    Modal,
    TextField, type UseOverlayStateReturn
} from "@heroui/react";
import {useParams} from "react-router";

export default function ApplicationForm({overlayState, jobId}: {overlayState: UseOverlayStateReturn, jobId: bigint | null}) {

    const onApplicationState = overlayState;


    async function handleSubmit(e: any) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);
        const params = new URLSearchParams({
            jobId: String(jobId),
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
            const errorText = await res.text();
            console.error("Failed", res.status, errorText);
            return;
        }
        console.log("Application submitted!");
    }

    return (
        <>
            <Modal isOpen={onApplicationState.isOpen}>
                <Modal.Backdrop className="dark" variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="sm:max-w-90 rounded-4xl">
                            <Modal.CloseTrigger onClick={() => onApplicationState.close()} />
                            <Modal.Header>
                                <img src="/images/navi/Navi%20Beta.png" alt="Logo" style={{height: "60px", width: "60px"}}/>
                                <Modal.Heading>Welcome to Internmap!</Modal.Heading>
                            </Modal.Header>
                            <Modal.Body>
                                <Form method="post" className="w-full max-w-96" onSubmit={handleSubmit}>
                                    <Fieldset>
                                        <Description>Apply to Application!</Description>
                                        <FieldGroup>
                                            <TextField isRequired name="f_name" validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                                <Label>First Name</Label>
                                                <Input placeholder="John" />
                                                <FieldError />
                                            </TextField>
                                            <TextField isRequired name="l_name" validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                                <Label>Last Name</Label>
                                                <Input placeholder="Doe" />
                                                <FieldError />
                                            </TextField>
                                            <TextField isRequired name="phone_number" validate={(v) => v.length < 3 ? "Min 3 characters" : null}>
                                                <Label>Phone</Label>
                                                <Input placeholder="+201xxxxxxxxx" />
                                                <FieldError />
                                            </TextField>
                                            <TextField isRequired name="email" type="email">
                                                <Label>Email</Label>
                                                <Input placeholder="john@example.com" />
                                                <FieldError />
                                            </TextField>
                                        </FieldGroup>
                                        <Fieldset.Actions>
                                            <Button type="submit" >Apply</Button>
                                            <Button type="reset" variant="secondary">Reset</Button>
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
