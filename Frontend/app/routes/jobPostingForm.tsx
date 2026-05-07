import type { UseOverlayStateReturn } from "@heroui/react";
import type { Route } from "./+types/home";
import Loading from "~/FrontendWebpages/fragments/Loading";
import JobPostingModal from "~/FrontendWebpages/JobPostingModal";
import { useOverlayTriggerState } from "@react-stately/overlays";

export function meta({}: Route.MetaArgs) {
    return [
        { title: "InternMap" },
        { name: "description", content: "Welcome to our 4th semester project" },
    ];
}

export function HydrateFallback() {
    return <Loading />;
}

export default function JobPostingForm() {
    const overlayState = useOverlayTriggerState({ defaultOpen: true });
    return <JobPostingModal overlayState={overlayState} />;
}
//JobPostingModal({overlayState}: {overlayState: UseOverlayStateReturn})