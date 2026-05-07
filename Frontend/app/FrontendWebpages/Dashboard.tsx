import "~/CSS/jobPosting.css"
import "~/CSS/InternMapHomepage.css";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import {Checkbox, Tabs, Toast ,Chip } from "@heroui/react";
import {notification} from "~/FrontendWebpages/fragments/Notification";
import { Table } from '@heroui/react';
import {useState} from "react";
import {Button , Alert} from "@heroui/react";
import type {Key} from "node:readline";
import {useFetcher} from "react-router";
import {AlertDialog} from "@heroui/react";


export default function Dashboard({users , roadmaps , userDetails }: {users : User[], roadmaps : Roadmap[] , userDetails : User } ) {
    const fetcher = useFetcher()
    const [selectedKeys, setSelectedKeys] = useState<"all" | Set<Key>>(new Set());
    const [selectedRoadmapKeys, setSelectedRoadmapKeys] = useState<"all" | Set<Key>>(new Set());

    const [showAdminError, setShowAdminError] = useState(false);
    console.log(userDetails?.email);
    console.log("test")
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

            {showAdminError && (
                <Alert status="danger">
                    <Alert.Indicator />
                    <Alert.Content>
                        <Alert.Title>Cannot delete admin user</Alert.Title>
                        <Alert.Description>
                            You selected at least one ADMIN user. This action is blocked.
                        </Alert.Description>
                    </Alert.Content>
                </Alert>
            )}

            <div className="wrapper">

                <div id="bb1" >

                    <Tabs className="max-w">
                        <Tabs.ListContainer>
                            <Tabs.List aria-label="View Selector">

                                <Tabs.Tab id="Roadmap">
                                    Users
                                    <Tabs.Indicator/>
                                </Tabs.Tab>

                                <Tabs.Tab id="JobPostings">
                                    RoadMaps
                                    <Tabs.Indicator/>
                                </Tabs.Tab>
                            </Tabs.List>
                        </Tabs.ListContainer>

                        <Tabs.Panel id="Roadmap">

                            <div >


                                {users.length == 0 ?
                                    <a> No users to show </a> :
                                    <div>

                                        <div  className="flex flex-col gap-3">
                                            <Table >
                                                <Table.ScrollContainer  style={{ maxHeight: "600px", overflow: "auto"  }}>
                                                    <Table.Content

                                                        aria-label="Table with selection"
                                                        className="min-w-[600px]"
                                                        selectedKeys={selectedKeys}
                                                        selectionMode="multiple"
                                                        onSelectionChange={setSelectedKeys}>
                                                        <Table.Header>
                                                            <Table.Column className="pr-0">
                                                                <Checkbox aria-label="Select all" slot="selection">
                                                                    <Checkbox.Control>
                                                                        <Checkbox.Indicator />
                                                                    </Checkbox.Control>
                                                                </Checkbox>
                                                            </Table.Column>
                                                            <Table.Column isRowHeader>FName</Table.Column>
                                                            <Table.Column isRowHeader>LName</Table.Column>

                                                            <Table.Column>Role</Table.Column>
                                                            <Table.Column>ID</Table.Column>
                                                            <Table.Column>Email</Table.Column>
                                                        </Table.Header>
                                                        <Table.Body>
                                                            {users.map((user) => (
                                                                <Table.Row key={user.id} id={user.email}>
                                                                    <Table.Cell className="pr-0">
                                                                        <Checkbox
                                                                            aria-label={`Select ${user.name}`}
                                                                            slot="selection"
                                                                            variant="secondary"
                                                                        >
                                                                            <Checkbox.Control>
                                                                                <Checkbox.Indicator />
                                                                            </Checkbox.Control>
                                                                        </Checkbox>
                                                                    </Table.Cell>
                                                                    <Table.Cell>{user.fname}</Table.Cell>
                                                                    <Table.Cell>{user.lname}</Table.Cell>

                                                                    <Table.Cell>{user.role}</Table.Cell>
                                                                    <Table.Cell>{user.id}</Table.Cell>
                                                                    <Table.Cell>{user.email}</Table.Cell>
                                                                </Table.Row>
                                                            ))}
                                                        </Table.Body>
                                                    </Table.Content>
                                                </Table.ScrollContainer>
                                            </Table>

                                        </div>
                                    </div>
                                }




                            </div>
                            <p className="text-sm text-muted">
                                Users Selected:{" "}
                                <span className="font-medium">
                                                     {selectedKeys === "all"  ? "All"  : selectedKeys.size > 0  ? selectedKeys.size : "None"}
                                                </span>

                            </p>
                            <div>

                                <AlertDialog>
                                    <Button variant="danger">Smite</Button>
                                    <AlertDialog.Backdrop>
                                        <AlertDialog.Container>
                                            <AlertDialog.Dialog className="sm:max-w-[400px]">
                                                <AlertDialog.CloseTrigger />
                                                <AlertDialog.Header>
                                                    <AlertDialog.Icon status="danger" />
                                                    <AlertDialog.Heading>Delete project permanently?</AlertDialog.Heading>
                                                </AlertDialog.Header>
                                                <AlertDialog.Body>
                                                    <p>
                                                        This will permanently delete <strong>This user</strong> and all of his
                                                        data. This action cannot be undone.
                                                    </p>
                                                </AlertDialog.Body>
                                                <AlertDialog.Footer>
                                                    <Button slot="close" variant="tertiary">
                                                        Cancel
                                                    </Button>

                                                    <Button slot="close" variant="danger"   onPress={() => {

                                                        const selectedUsers =
                                                            selectedKeys === "all" ? users : users.filter(u => (selectedKeys as Set<string>).has(u.email));
                                                        if(selectedUsers.some (u => u.role === "ADMIN")) {
                                                            setShowAdminError(true);
                                                            setTimeout(() => {
                                                                setShowAdminError(false);
                                                            }, 3000);
                                                            return;
                                                        }

                                                        const selectedEmails = selectedKeys === "all"
                                                            ? users.map(u => <u className="email"></u>)
                                                            : Array.from(selectedKeys as Set<string>);

                                                        const formData = new FormData();
                                                        selectedEmails.forEach(email => formData.append("emails", email));
                                                        fetcher.submit(formData, { method: "POST" });

                                                        setSelectedKeys(new Set())
                                                    }}>
                                                        Smite!
                                                    </Button>
                                                </AlertDialog.Footer>
                                            </AlertDialog.Dialog>
                                        </AlertDialog.Container>
                                    </AlertDialog.Backdrop>
                                </AlertDialog>

                                <button>

                                </button>
                            </div>

                        </Tabs.Panel>

                        <Tabs.Panel id="JobPostings">

                            <div >

                                {roadmaps.length == 0 ?
                                    <a> No roadmaps to show </a> :
                                    <div>

                                        <div  className="flex flex-col gap-3">
                                            <Table >
                                                <Table.ScrollContainer  style={{ maxHeight: "600px", overflow: "auto"}}>
                                                    <Table.Content

                                                        aria-label="Table with selection"
                                                        className="min-w-[600px]"
                                                        selectedKeys={selectedRoadmapKeys}
                                                        selectionMode="multiple"
                                                        onSelectionChange={setSelectedRoadmapKeys}>
                                                        <Table.Header>
                                                            <Table.Column className="pr-0">
                                                                <Checkbox aria-label="Select all" slot="selection">
                                                                    <Checkbox.Control>
                                                                        <Checkbox.Indicator />
                                                                    </Checkbox.Control>
                                                                </Checkbox>
                                                            </Table.Column>
                                                            <Table.Column isRowHeader>Name</Table.Column>
                                                            <Table.Column isRowHeader>ID</Table.Column>
                                                        </Table.Header>
                                                        <Table.Body>
                                                            {roadmaps.map((roadmap) => (
                                                                <Table.Row key={roadmap.id} id={String(roadmap.id)}>
                                                                    <Table.Cell className="pr-0">
                                                                        <Checkbox
                                                                            aria-label={`Select ${roadmap.name}`}
                                                                            slot="selection"
                                                                            variant="secondary"
                                                                        >
                                                                            <Checkbox.Control>
                                                                                <Checkbox.Indicator />
                                                                            </Checkbox.Control>
                                                                        </Checkbox>
                                                                    </Table.Cell>
                                                                    <Table.Cell>{roadmap.name}</Table.Cell>
                                                                    <Table.Cell>{roadmap.id}</Table.Cell>
                                                                </Table.Row>
                                                            ))}
                                                        </Table.Body>
                                                    </Table.Content>
                                                </Table.ScrollContainer>
                                            </Table>

                                        </div>
                                    </div>
                                }

                            </div>
                            <p className="text-sm text-muted">
                                Roadmaps Selected:{" "}
                                <span className="font-medium">
                                    {selectedRoadmapKeys === "all" ? "All" : selectedRoadmapKeys.size > 0 ? selectedRoadmapKeys.size : "None"}
                                </span>
                            </p>
                            <div>

                                <AlertDialog>
                                    <Button variant="danger">Smite</Button>
                                    <AlertDialog.Backdrop>
                                        <AlertDialog.Container>
                                            <AlertDialog.Dialog className="sm:max-w-[400px]">
                                                <AlertDialog.CloseTrigger />
                                                <AlertDialog.Header>
                                                    <AlertDialog.Icon status="danger" />
                                                    <AlertDialog.Heading>Delete roadmap permanently?</AlertDialog.Heading>
                                                </AlertDialog.Header>
                                                <AlertDialog.Body>
                                                    <p>
                                                        This will permanently delete <strong>This roadmap</strong> and all of its
                                                        data. This action cannot be undone.
                                                    </p>
                                                </AlertDialog.Body>
                                                <AlertDialog.Footer>
                                                    <Button slot="close" variant="tertiary">
                                                        Cancel
                                                    </Button>

                                                    <Button slot="close" variant="danger" onPress={() => {
                                                        const selectedRoadmaps = selectedRoadmapKeys === "all"
                                                            ? roadmaps.map(r => String(r.id))
                                                            : Array.from(selectedRoadmapKeys as Set<string>);

                                                        const formData = new FormData();
                                                        selectedRoadmaps.forEach(id => formData.append("roadmaps", id));
                                                        fetcher.submit(formData, { method: "POST" });

                                                        setSelectedRoadmapKeys(new Set());
                                                    }}>
                                                        Smite!
                                                    </Button>
                                                </AlertDialog.Footer>
                                            </AlertDialog.Dialog>
                                        </AlertDialog.Container>
                                    </AlertDialog.Backdrop>
                                </AlertDialog>

                                <button>

                                </button>
                            </div>

                        </Tabs.Panel>
                    </Tabs>
                </div>
            </div>

            <IndexFooter/>
        </>
    );
}