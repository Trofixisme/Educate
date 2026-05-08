import type { Route } from "./+types/home";
import Loading from "~/FrontendWebpages/fragments/Loading";
import ComposeAJob from "~/FrontendWebpages/ComposeAJob";
import {useOverlayTriggerState} from "react-stately";

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
    return <ComposeAJob overlayState={overlayState} />;
}