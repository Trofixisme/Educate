import "../CSS/jobPosting.css"
import "../CSS/InternMapHomepage.css";
import { Button, FieldError, FieldGroup, Fieldset, Input, Label, Modal, TextField } from "@heroui/react";
import { Dropdown, Header } from "@heroui/react";
import type { UseOverlayStateReturn } from "@heroui/react";
import { useState } from "react";
import { useNavigate } from "react-router";

export default function JobPostingModal({ overlayState }: { overlayState: UseOverlayStateReturn }) {

    const [selected, setSelected] = useState<Set<string>>(new Set());
    const selectedValue = selected.values().next().value ?? "";
    const onJobPostingState = overlayState;
    const navigate = useNavigate();

    const labels: Record<string, string> = {
        intern: "Internship",
        fulltime: "Full Time",
        freelance: "FreeLance",
    };

    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const formData = new FormData(e.currentTarget);

        const typeMap: Record<string, string> = {
            intern: "Internship",
            fulltime: "FullTime",
            freelance: "FreelanceProject",
        };

        const body = {
            jobType: typeMap[Array.from(selected)[0]],
            company: { name: formData.get("company") },
            jobPosting: {
                jobName: formData.get("job_name"),
                jobDescription: formData.get("job_description"),
                jobRequirements: formData.get("job_requirements"),
                jobLocation: formData.get("job_location"),
            },
            fullTime: { benefits: formData.get("benefits") ?? "" },
            internship: {
                duration: formData.get("duration") ?? "",
                jobLocation: formData.get("job_location") ?? "",
            },
            freelanceProject: {
                duration: formData.get("duration") ?? "",
                payout: formData.get("payout") ?? "",
                jobLocation: formData.get("job_location") ?? "",
            },
        };

        try {
            const res = await fetch("http://localhost:8050/api/jobposting/new", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    Authorization: `Bearer ${localStorage.getItem("token")}`,
                },
                body: JSON.stringify(body),
            });
            if (!res.ok) {
                const errorBody = await res.json();
                console.error("Submission failed:", res.status, errorBody);
            } else {
                console.log("Success!");
                onJobPostingState.close();
                navigate("/");
            }
        } catch (err) {
            console.error("Network error:", err);
        }
    }

    return (
        <>
            <Modal isOpen={onJobPostingState.isOpen}>
                <Modal.Backdrop className="dark" variant="blur" isKeyboardDismissDisabled={false} isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="sm:max-w-90 rounded-4xl">
                            <Modal.CloseTrigger onClick={() => onJobPostingState.close()} />
                            <Modal.Header>
                                <Modal.Heading>Compose a Job</Modal.Heading>
                            </Modal.Header>
                            <Modal.Body>
                                <form className="w-full max-w-96" onSubmit={handleSubmit}>
                                    <Fieldset>
                                        <FieldGroup>
                                            <Dropdown aria-label="Job Type Selector">
                                                <Button aria-label="Menu" variant="secondary">
                                                    {labels[selectedValue] ?? "Select job type"}
                                                </Button>
                                                <Dropdown.Popover className="min-w-[256px]">
                                                    <Dropdown.Menu
                                                        aria-label="Job type"
                                                        selectedKeys={selected}
                                                        selectionMode="single"
                                                        onSelectionChange={(keys) => {
                                                            if (keys === "all") return;
                                                            setSelected(new Set(Array.from(keys).map(String)));
                                                        }}>
                                                        <Dropdown.Section>
                                                            <Header>Select job type</Header>
                                                            <Dropdown.Item id="intern" textValue="Internship">
                                                                <Dropdown.ItemIndicator />
                                                                <Label>Internship</Label>
                                                            </Dropdown.Item>
                                                            <Dropdown.Item id="fulltime" textValue="fulltime">
                                                                <Dropdown.ItemIndicator />
                                                                <Label>Full Time</Label>
                                                            </Dropdown.Item>
                                                            <Dropdown.Item id="freelance" textValue="freelance">
                                                                <Dropdown.ItemIndicator />
                                                                <Label>Freelance</Label>
                                                            </Dropdown.Item>
                                                        </Dropdown.Section>
                                                    </Dropdown.Menu>
                                                </Dropdown.Popover>
                                            </Dropdown>

                                            <TextField isRequired name="job_name"
                                                       validate={(value) => value.length < 3 ? "Name must be at least 3 characters" : null}>
                                                <Label>Job Title</Label>
                                                <Input placeholder="Professional pro player" />
                                                <FieldError />
                                            </TextField>

                                            <TextField isRequired name="job_description"
                                                       validate={(value) => value.length < 3 ? "Must be at least 3 characters" : null}>
                                                <Label>Job Description</Label>
                                                <Input placeholder="Have no life" />
                                                <FieldError />
                                            </TextField>

                                            <TextField isRequired name="job_requirements"
                                                       validate={(value) => value.length < 3 ? "Must be at least 3 characters" : null}>
                                                <Label>Job Requirement</Label>
                                                <Input placeholder="idk nothing" />
                                                <FieldError />
                                            </TextField>

                                            <TextField name="company" type="text">
                                                <Label>Company Name</Label>
                                                <Input placeholder="RIOOOOOOOOOOOT" />
                                                <FieldError />
                                            </TextField>

                                            {selectedValue === "intern" && (
                                                <>
                                                    <TextField name="duration" type="text">
                                                        <Label>Duration:</Label>
                                                        <Input placeholder="e.g. 3 months" />
                                                    </TextField>
                                                    <TextField name="job_location" type="text">
                                                        <Label>Location:</Label>
                                                        <Input placeholder="Cairo" />
                                                    </TextField>
                                                </>
                                            )}

                                            {selectedValue === "freelance" && (
                                                <>
                                                    <TextField name="duration" type="text">
                                                        <Label>Duration:</Label>
                                                        <Input placeholder="3 months" />
                                                    </TextField>
                                                    <TextField name="job_location" type="text">
                                                        <Label>Location:</Label>
                                                        <Input placeholder="Cairo" />
                                                    </TextField>
                                                    <TextField name="payout" type="text">
                                                        <Label>Pay out:</Label>
                                                        <Input placeholder="3000" />
                                                    </TextField>
                                                </>
                                            )}

                                            {selectedValue === "fulltime" && (
                                                <>
                                                    <TextField name="benefits" type="text">
                                                        <Label>Benefits:</Label>
                                                        <Input placeholder="providing insurance" />
                                                    </TextField>
                                                </>
                                            )}

                                        </FieldGroup>
                                        <Fieldset.Actions>
                                            <Button type="submit">Add</Button>
                                            <Button type="reset" variant="secondary">Reset</Button>
                                        </Fieldset.Actions>
                                    </Fieldset>
                                </form>
                            </Modal.Body>
                            <Modal.Footer />
                        </Modal.Dialog>
                    </Modal.Container>
                </Modal.Backdrop>
            </Modal>
        </>
    );
}