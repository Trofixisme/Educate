import type { Application } from "~/Model/Application";
import MyJobApplicants from "../FrontendWebpages/JobApplicants";
import Loading from "../FrontendWebpages/fragments/Loading";
import type { Route } from "./+types/login";

export async function clientLoader({ params }: Route.LoaderArgs) {
    const token = localStorage.getItem("token");
    if (!token) return Response.redirect("/login", 302);


    const res = await fetch(`http://localhost:8050/api/jobposting/${params.id}/applications`, {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`,
            Accept: "application/json",
        },
    });

    if (!res.ok) return { applications: [] };

    const json = await res.json();

    // Backend returns a bare array, so wrap it
    const applications: Application[] = Array.isArray(json) ? json : json.data ?? [];

    return { applications };
}

export function HydrateFallback() {
    return <Loading />;
}

export default function JobApplicants({ loaderData }: Route.ComponentProps) {
    const { applications } = loaderData as { applications: Application[] };
    return <MyJobApplicants applications={applications} />;
}
