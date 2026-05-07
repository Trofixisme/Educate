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

            <div className="wrapper">
                <div id="bb1">
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
                                                                    <Icon className="size-4" icon="gravity-ui:trash-bin"/>
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
                                Users Selected:{" "}
                                <span className="font-medium">
                                    {selectedKeys === "all" ? "All" : (selectedKeys as Set<Key>).size > 0 ? (selectedKeys as Set<Key>).size : "None"}
                                </span>
                            </p>

                            {/* Bulk delete */}
                            <AlertDialog>
                                <Button variant="danger" isDisabled={selectedKeys !== "all" && (selectedKeys as Set<Key>).size === 0}>
                                    Delete Selection
                                </Button>
                                <AlertDialog.Backdrop>
                                    <AlertDialog.Container>
                                        <AlertDialog.Dialog className="sm:max-w-[400px]">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <AlertDialog.Icon status="danger"/>
                                                <AlertDialog.Heading>Delete selected users permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{selectedKeys === "all" ? "all users" : `${(selectedKeys as Set<Key>).size} user(s)`}</strong>
                                                    {" "}and all of their data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button slot="close" variant="tertiary">Cancel</Button>
                                                <Button slot="close" variant="danger" onPress={() => {
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
                                        <AlertDialog.Dialog className="sm:max-w-[400px]">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <AlertDialog.Icon status="danger"/>
                                                <AlertDialog.Heading>Delete user permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{userToSmite?.fname} {userToSmite?.lname}</strong>
                                                    {" "}and all of their data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button slot="close" variant="tertiary">Cancel</Button>
                                                <Button slot="close" variant="danger" onPress={() => {
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
                                            className="min-w-[600px]"
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
                                                                    <Icon className="size-4" icon="gravity-ui:pencil"/>
                                                                </Button>
                                                                <Button isIconOnly size="sm" variant="danger-soft"
                                                                        onPress={() => {
                                                                            setRoadmapToSmite(roadmap);
                                                                            setIsRoadmapDialogOpen(true);
                                                                        }}>
                                                                    <Icon className="size-4" icon="gravity-ui:trash-bin"/>
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
                                    Smite Selected
                                </Button>
                                <AlertDialog.Backdrop>
                                    <AlertDialog.Container>
                                        <AlertDialog.Dialog className="sm:max-w-[400px]">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <AlertDialog.Icon status="danger"/>
                                                <AlertDialog.Heading>Delete selected roadmaps permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{selectedRoadmapKeys === "all" ? "all roadmaps" : `${(selectedRoadmapKeys as Set<Key>).size} roadmap(s)`}</strong>
                                                    {" "}and all of their data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button slot="close" variant="tertiary">Cancel</Button>
                                                <Button slot="close" variant="danger" onPress={() => {
                                                    const selectedRoadmaps = selectedRoadmapKeys === "all"
                                                        ? roadmaps.map(r => String(r.id))
                                                        : Array.from(selectedRoadmapKeys as Set<string>);
                                                    const formData = new FormData();
                                                    selectedRoadmaps.forEach(id => formData.append("roadmaps", id));
                                                    fetcher.submit(formData, {method: "POST", action: "/profile"});
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
                                        <AlertDialog.Dialog className="sm:max-w-[400px]">
                                            <AlertDialog.CloseTrigger/>
                                            <AlertDialog.Header>
                                                <AlertDialog.Icon status="danger"/>
                                                <AlertDialog.Heading>Delete roadmap permanently?</AlertDialog.Heading>
                                            </AlertDialog.Header>
                                            <AlertDialog.Body>
                                                <p>This will permanently delete{" "}
                                                    <strong>{roadmapToSmite?.name}</strong>
                                                    {" "}and all of its data. This action cannot be undone.</p>
                                            </AlertDialog.Body>
                                            <AlertDialog.Footer>
                                                <Button slot="close" variant="tertiary">Cancel</Button>
                                                <Button slot="close" variant="danger" onPress={() => {
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
            </div>

            <RoadMapEdit overlayState={roadmapFormOverlayState} roadmapId={selectedRoadmapId}/>
        </>
    );
}