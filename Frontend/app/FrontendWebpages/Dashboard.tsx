import "../CSS/jobPosting.css"
import "../CSS/InternMapHomepage.css";
import {Avatar, Checkbox, Chip, cn, type Key, type SortDescriptor, Tabs, useOverlayState} from "@heroui/react";
import { Table } from '@heroui/react';
import React, {useMemo, useState} from "react";
import {Button, Alert} from "@heroui/react";
import {useFetcher} from "react-router";
import {AlertDialog} from "@heroui/react";
import type {Roadmap} from "../../Model/Roadmap";
import {Icon} from "@iconify/react";
import RoadMapEdit from "../FrontendWebpages/RoadMapUpdate";
import {IndexHeader} from "~/FrontendWebpages/fragments/IndexHeaderAndFooter";
import type {Recruiter} from "~/Model/Users/Recruiter";
import {Modal, CloseButton} from "@heroui/react";
import type { User } from "~/Model/Users/User";

function SortableColumnHeader({children, sortDirection}: { children: React.ReactNode; sortDirection?: "ascending" | "descending"; }) {
    return (
        <span className="flex items-center justify-between">
            {children}
            {!!sortDirection && (
                <Icon icon="gravity-ui:chevron-up" className={cn(
                    "size-3 transform transition-transform duration-100 ease-out",
                    sortDirection === "descending" ? "rotate-180" : "",
                )}/>
            )}
        </span>
    );
}

export default function Dashboard({users, roadmaps, userDetails}: { users: User[], roadmaps: Roadmap[], userDetails: User }) {
    const fetcher = useFetcher();
    const roadmapFormOverlayState = useOverlayState({defaultOpen: false});
    const [roadmapToSmite, setRoadmapToSmite] = useState<Roadmap | null>(null);
    const [isDialogOpen, setIsDialogOpen] = useState(false);
    const [selectedRoadmapId, setSelectedRoadmapId] = useState<number | null>(null);
    const [roadmapSortDescriptor, setRoadmapSortDescriptor] = useState<SortDescriptor>({column: "name", direction: "ascending"});
    const [isRoadmapDialogOpen, setIsRoadmapDialogOpen] = useState(false);
    const [userToSmite, setUserToSmite] = useState<User | null>(null);
    const [selectedKeys, setSelectedKeys] = useState<"all" | Set<Key>>(new Set());
    const [selectedRoadmapKeys, setSelectedRoadmapKeys] = useState<"all" | Set<Key>>(new Set());
    const [sortDescriptor, setSortDescriptor] = useState<SortDescriptor>({column: "fname", direction: "ascending"});
    const [showAdminError, setShowAdminError] = useState(false);
    const [isEditOpen, setIsEditOpen] = useState(false);
    const [editForm, setEditForm] = useState(userDetails);
    const [editLoading, setEditLoading] = useState(false);
    const [errorMessage, setErrorMessage] = useState<string | null>(null);

    async function saveProfile() {
        setEditLoading(true);
        setErrorMessage(null);

        const response = await fetch("http://localhost:8050/api/admin/update", {
            method: "POST",
            headers: {
                Authorization: `Bearer ${localStorage.getItem("token")}`,
                Accept: "application/json",
                "Content-Type": "application/json",
            },
            body: JSON.stringify(editForm),
        });

        const json = await response.json();

        if (!response.ok) {
            setErrorMessage(json.detail);
            setEditLoading(false);
            return;
        }

        setEditLoading(false);
        setIsEditOpen(false);
        window.location.reload();
    }

    const sortedUsers = useMemo(() => {
        return [...users].sort((a, b) => {
            const col = sortDescriptor.column as keyof User;
            const first = String(a[col] ?? "");
            const second = String(b[col] ?? "");
            let cmp = first.localeCompare(second);
            if (sortDescriptor.direction === "descending") cmp *= -1;
            return cmp;
        });
    }, [sortDescriptor, users]);

    const sortedRoadmaps = useMemo(() => {
        return [...roadmaps].sort((a, b) => {
            const col = roadmapSortDescriptor.column as keyof Roadmap;
            const first = String(a[col] ?? "");
            const second = String(b[col] ?? "");
            let cmp = first.localeCompare(second);
            if (roadmapSortDescriptor.direction === "descending") cmp *= -1;
            return cmp;
        });
    }, [roadmapSortDescriptor, roadmaps]);

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
                            <Chip size="lg">
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
                                style={{width: "32px", height: "32px", background: "var(--container-secondary)"}}
                                className="dark"
                                isIconOnly
                                onClick={() => setIsEditOpen(true)}>
                                <img src="/images/assets/pencil@4x.png" style={{width: "16px", filter: "invert(0.3)"}} alt="pencil"/>
                            </Button>
                        </div>
                    </div>
                </div>
            </div>

            {showAdminError && (
                <Alert status="danger">
                    <Alert.Indicator/>
                    <Alert.Content>
                        <Alert.Title>Cannot delete admin user</Alert.Title>
                        <Alert.Description>
                            You selected at least one ADMIN user. This action is blocked.
                        </Alert.Description>
                    </Alert.Content>
                </Alert>
            )}

            <div className="wrapper p-14">
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

                        {/* Users Tab */}
                        <Tabs.Panel id="Roadmap">
                            {users.length === 0 ? (
                                <a>No users to show</a>
                            ) : (
                                <Table>
                                    <Table.ScrollContainer style={{maxHeight: "600px", overflow: "auto"}}>
                                        <Table.Content
                                            aria-label="Users table"
                                            className="min-w-[800px]"
                                            selectedKeys={selectedKeys}
                                            selectionMode="multiple"
                                            sortDescriptor={sortDescriptor}
                                            onSelectionChange={setSelectedKeys}
                                            onSortChange={setSortDescriptor}>
                                            <Table.Header>
                                                <Table.Column className="pr-0">
                                                    <Checkbox aria-label="Select all" slot="selection">
                                                        <Checkbox.Control><Checkbox.Indicator/></Checkbox.Control>
                                                    </Checkbox>
                                                </Table.Column>
                                                <Table.Column allowsSorting isRowHeader className="after:hidden" id="id">
                                                    {({sortDirection}) => <SortableColumnHeader sortDirection={sortDirection}>ID</SortableColumnHeader>}
                                                </Table.Column>
                                                <Table.Column allowsSorting id="fname">
                                                    {({sortDirection}) => <SortableColumnHeader sortDirection={sortDirection}>Member</SortableColumnHeader>}
                                                </Table.Column>
                                                <Table.Column allowsSorting id="role">
                                                    {({sortDirection}) => <SortableColumnHeader sortDirection={sortDirection}>Role</SortableColumnHeader>}
                                                </Table.Column>
                                                <Table.Column className="text-end">Actions</Table.Column>
                                            </Table.Header>
                                            <Table.Body>
                                                {sortedUsers.map((user) => (
                                                    <Table.Row key={user.id} id={user.email}>
                                                        <Table.Cell className="pr-0">
                                                            <Checkbox aria-label={`Select ${user.fname}`}
                                                                      slot="selection" variant="secondary">
                                                                <Checkbox.Control><Checkbox.Indicator/></Checkbox.Control>
                                                            </Checkbox>
                                                        </Table.Cell>
                                                        <Table.Cell className="font-medium">
                                                            <div className="flex items-center gap-2">
                                                                #{user.id.toString()}
                                                                <Button isIconOnly size="sm" variant="ghost"
                                                                        onPress={() => navigator.clipboard.writeText(user.id.toString())}>
                                                                    <Icon className="size-4 text-muted" icon="gravity-ui:copy"/>
                                                                </Button>
                                                            </div>
                                                        </Table.Cell>
                                                        <Table.Cell>
                                                            <div className="flex items-center gap-3">
                                                                <Avatar size="sm">
                                                                    <Avatar.Fallback>{`${user.fname?.[0] ?? ""}${user.lname?.[0] ?? ""}`}</Avatar.Fallback>
                                                                </Avatar>
                                                                <div className="flex flex-col">
                                                                    <span className="text-xs">{user.fname} {user.lname}</span>
                                                                    <span className="text-xs text-muted">{user.email}</span>
                                                                </div>
                                                            </div>
                                                        </Table.Cell>
                                                        <Table.Cell className="min-w-52">{user.role}</Table.Cell>
                                                        <Table.Cell>
                                                            <div className="flex items-center justify-end gap-1">
                                                                <Button isIconOnly size="sm" variant="danger-soft"
                                                                        onPress={() => {
                                                                            setUserToSmite(user);
                                                                            setIsDialogOpen(true);
                                                                        }}>
                                                                    <img className="w-3.5" src="/images/assets/trash.fill@4x.png" alt="Warn"/>
                                                                </Button>
                                                            </div>
                                                        </Table.Cell>
                                                    </Table.Row>
                                                ))}
                                            </Table.Body>
                                        </Table.Content>
                                    </Table.ScrollContainer>
                                </Table>
                            )}

                            <br/>
                            <p className="text-sm text-muted font-bold">
                                Users Selected:{" "}
                                <span className="font-medium">
                                    {selectedKeys === "all" ? "All" : (selectedKeys as Set<Key>).size > 0 ? (selectedKeys as Set<Key>).size : "None"}
                                </span>
                            </p>

                            <br/>

                            {/* Bulk delete */}
                            <AlertDialog>
                                <Button className="full-width p-3.5" variant="danger" isDisabled={selectedKeys !== "all" && (selectedKeys as Set<Key>).size === 0}>
                                    Delete Selection
                                </Button>
                                <AlertDialog.Backdrop>
                                    <AlertDialog.Container>
                                        <AlertDialog.Dialog className="sm:max-w-100">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <img className="w-8" src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Warn"/>
                                                <AlertDialog.Heading>Delete selected users permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{selectedKeys === "all" ? "all users" : `${(selectedKeys as Set<Key>).size} user(s)`}</strong>
                                                    {" "}and all of their data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button className="full-width p-2" slot="close" variant="tertiary">Cancel</Button>
                                                <Button className="full-width p-2" slot="close" variant="danger" onPress={() => {
                                                    const selectedUsers = selectedKeys === "all"
                                                        ? users
                                                        : users.filter(u => (selectedKeys as Set<string>).has(u.email));
                                                    if (selectedUsers.some(u => u.role === "ADMIN")) {
                                                        setShowAdminError(true);
                                                        setTimeout(() => setShowAdminError(false), 3000);
                                                        return;
                                                    }
                                                    const formData = new FormData();
                                                    selectedUsers.forEach(u => formData.append("emails", u.email));
                                                    fetcher.submit(formData, {method: "POST", action: "/dashboard"});
                                                    setSelectedKeys(new Set());
                                                }}>Delete</Button>
                                            </AlertDialog.Footer>
                                        </AlertDialog.Dialog>
                                    </AlertDialog.Container>
                                </AlertDialog.Backdrop>
                            </AlertDialog>

                            {/* Single user delete dialog */}
                            <AlertDialog isOpen={isDialogOpen} onOpenChange={setIsDialogOpen}>
                                <AlertDialog.Backdrop>
                                    <AlertDialog.Container>
                                        <AlertDialog.Dialog className="sm:max-w-90">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <img className="w-8" src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Warn"/>
                                                <AlertDialog.Heading>Delete user permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{userToSmite?.fname} {userToSmite?.lname}</strong>
                                                    {" "}and all of their data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button className="full-width p-2" slot="close" variant="tertiary">Cancel</Button>
                                                <Button className="full-width p-2" slot="close" variant="danger" onPress={() => {
                                                    if (!userToSmite) return;
                                                    if (userToSmite.role === "ADMIN") {
                                                        setShowAdminError(true);
                                                        setTimeout(() => setShowAdminError(false), 3000);
                                                        setIsDialogOpen(false);
                                                        return;
                                                    }
                                                    const formData = new FormData();
                                                    formData.append("emails", userToSmite.email);
                                                    fetcher.submit(formData, {method: "POST", action: "/dashboard"});
                                                    setUserToSmite(null);
                                                    setIsDialogOpen(false);
                                                }}>Smite!</Button>
                                            </AlertDialog.Footer>
                                        </AlertDialog.Dialog>
                                    </AlertDialog.Container>
                                </AlertDialog.Backdrop>
                            </AlertDialog>
                        </Tabs.Panel>

                        {/* Roadmaps Tab */}
                        <Tabs.Panel id="JobPostings">
                            {roadmaps.length === 0 ? (
                                <a>No roadmaps to show</a>
                            ) : (
                                <Table>
                                    <Table.ScrollContainer style={{maxHeight: "600px", overflow: "auto"}}>
                                        <Table.Content
                                            aria-label="Table with selection"
                                            selectedKeys={selectedRoadmapKeys}
                                            selectionMode="multiple"
                                            sortDescriptor={roadmapSortDescriptor}
                                            onSelectionChange={setSelectedRoadmapKeys}
                                            onSortChange={setRoadmapSortDescriptor}>
                                            <Table.Header>
                                                <Table.Column className="pr-0">
                                                    <Checkbox aria-label="Select all" slot="selection">
                                                        <Checkbox.Control><Checkbox.Indicator/></Checkbox.Control>
                                                    </Checkbox>
                                                </Table.Column>
                                                <Table.Column allowsSorting isRowHeader className="after:hidden" id="name">
                                                    {({sortDirection}) => <SortableColumnHeader sortDirection={sortDirection}>Name</SortableColumnHeader>}
                                                </Table.Column>
                                                <Table.Column allowsSorting id="id">
                                                    {({sortDirection}) => <SortableColumnHeader sortDirection={sortDirection}>ID</SortableColumnHeader>}
                                                </Table.Column>
                                                <Table.Column className="text-end">Actions</Table.Column>
                                            </Table.Header>
                                            <Table.Body>
                                                {sortedRoadmaps.map((roadmap) => (
                                                    <Table.Row key={roadmap.id} id={String(roadmap.id)}>
                                                        <Table.Cell className="pr-0">
                                                            <Checkbox aria-label={`Select ${roadmap.name}`} slot="selection" variant="secondary">
                                                                <Checkbox.Control><Checkbox.Indicator/></Checkbox.Control>
                                                            </Checkbox>
                                                        </Table.Cell>
                                                        <Table.Cell>{roadmap.name}</Table.Cell>
                                                        <Table.Cell>{roadmap.id}</Table.Cell>
                                                        <Table.Cell>
                                                            <div className="flex items-center justify-end gap-1">
                                                                <Button isIconOnly size="sm" variant="tertiary"
                                                                        onPress={() => {
                                                                            setSelectedRoadmapId(roadmap.id);
                                                                            roadmapFormOverlayState.open();
                                                                        }}>
                                                                    <img className="w-3.5" src="/images/assets/pencil@4x.png" alt="Warn"/>
                                                                </Button>
                                                                <Button isIconOnly size="sm" variant="danger-soft"
                                                                        onPress={() => {
                                                                            setRoadmapToSmite(roadmap);
                                                                            setIsRoadmapDialogOpen(true);
                                                                        }}>
                                                                    <img className="w-3.5" src="/images/assets/trash.fill@4x.png" alt="Warn"/>
                                                                </Button>
                                                            </div>
                                                        </Table.Cell>
                                                    </Table.Row>
                                                ))}
                                            </Table.Body>
                                        </Table.Content>
                                    </Table.ScrollContainer>
                                </Table>
                            )}

                            <p className="text-sm text-muted">
                                Roadmaps Selected:{" "}
                                <span className="font-medium">
                                    {selectedRoadmapKeys === "all" ? "All" : selectedRoadmapKeys.size > 0 ? selectedRoadmapKeys.size : "None"}
                                </span>
                            </p>

                            {/* Bulk delete */}
                            <AlertDialog>
                                <Button variant="danger" isDisabled={selectedRoadmapKeys !== "all" && (selectedRoadmapKeys as Set<Key>).size === 0}>
                                    Delete selection
                                </Button>
                                <AlertDialog.Backdrop>
                                    <AlertDialog.Container>
                                        <AlertDialog.Dialog className="sm:max-w-[400px]">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <img className="w-8" src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Warn"/>
                                                <AlertDialog.Heading>Delete selected roadmaps permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{selectedRoadmapKeys === "all" ? "all roadmaps" : `${(selectedRoadmapKeys as Set<Key>).size} roadmap(s)`}</strong>
                                                    {" "}and all of their data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button className="full-width p-2" slot="close" variant="tertiary">Cancel</Button>
                                                <Button className="full-width p-2" slot="close" variant="danger" onPress={() => {
                                                    const selectedRoadmaps = selectedRoadmapKeys === "all"
                                                        ? roadmaps.map(r => String(r.id))
                                                        : Array.from(selectedRoadmapKeys as Set<string>);
                                                    const formData = new FormData();
                                                    selectedRoadmaps.forEach(id => formData.append("roadmaps", id));
                                                    fetcher.submit(formData, {method: "POST", action: "/dashboard"});
                                                    setSelectedRoadmapKeys(new Set());
                                                }}>Smite!</Button>
                                            </AlertDialog.Footer>
                                        </AlertDialog.Dialog>
                                    </AlertDialog.Container>
                                </AlertDialog.Backdrop>
                            </AlertDialog>

                            {/* Single roadmap delete dialog */}
                            <AlertDialog isOpen={isRoadmapDialogOpen} onOpenChange={setIsRoadmapDialogOpen}>
                                <AlertDialog.Backdrop>
                                    <AlertDialog.Container>
                                        <AlertDialog.Dialog className="sm:max-w-100">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <img className="w-8" src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Warn"/>
                                                <AlertDialog.Heading>Delete roadmap permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{roadmapToSmite?.name}</strong>
                                                    {" "}and all of its data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button className="full-width p-2" slot="close" variant="tertiary">Cancel</Button>
                                                <Button className="full-width p-2" slot="close" variant="danger" onPress={() => {
                                                    if (!roadmapToSmite) return;
                                                    const formData = new FormData();
                                                    formData.append("roadmaps", String(roadmapToSmite.id));
                                                    fetcher.submit(formData, {method: "POST", action: "/dashboard"});
                                                    setRoadmapToSmite(null);
                                                    setIsRoadmapDialogOpen(false);
                                                }}>Delete</Button>
                                            </AlertDialog.Footer>
                                        </AlertDialog.Dialog>
                                    </AlertDialog.Container>
                                </AlertDialog.Backdrop>
                            </AlertDialog>
                        </Tabs.Panel>
                    </Tabs>
                </div>

            {/* Edit Profile Modal */}
            <Modal isOpen={isEditOpen} onOpenChange={setIsEditOpen}>
                <Modal.Backdrop variant="blur" isDismissable={true}>
                    <Modal.Container>
                        <Modal.Dialog className="max-w-xl">
                            <Modal.CloseTrigger onClick={() => setIsEditOpen(false)}/>
                            <Modal.Header>
                                <Modal.Heading>Edit Profile</Modal.Heading>
                                {errorMessage && (
                                    <Alert className="dark rounded-4xl" style={{background: "var(--container-secondary)"}} status="danger">
                                        <Alert.Indicator>
                                            <img src="/images/assets/exclamationmark.circle.fill@4x.png" alt="Logo" style={{width: "20px", height: "20px"}}/>
                                        </Alert.Indicator>
                                        <Alert.Content>
                                            <Alert.Title>
                                                <p className="font-bold" style={{marginTop: "2.2px", color: "rgb(225, 66, 69)"}}>
                                                    {errorMessage}
                                                </p>
                                            </Alert.Title>
                                        </Alert.Content>
                                        <CloseButton style={{background: "var(--component-tertiary)", marginTop: "2.2px"}} onClick={() => setErrorMessage(null)}/>
                                    </Alert>
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
                                                onChange={e => setEditForm(p => ({...p, fname: e.target.value}))}
                                            />
                                        </div>
                                        <div className="full-width">
                                            <label className="flex label-small mb-1">Last Name</label>
                                            <input
                                                type="text"
                                                className="text-sm"
                                                value={editForm.lname}
                                                onChange={e => setEditForm(p => ({...p, lname: e.target.value}))}
                                            />
                                        </div>
                                    </div>
                                    <div>
                                        <label className="flex label-small">Email</label>
                                        <input
                                            type="email"
                                            className="text-sm pb-2"
                                            value={editForm.email}
                                            onChange={e => setEditForm(p => ({...p, email: e.target.value}))}
                                        />
                                        <div className="text-xs pt-2">- Changing your email address will force you to be <b>logged out</b></div>
                                    </div>
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

            <RoadMapEdit overlayState={roadmapFormOverlayState} roadmapId={selectedRoadmapId}/>
        </>
    );
}