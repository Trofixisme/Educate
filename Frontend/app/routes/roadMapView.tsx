import RoadMapView from "../FrontendWebpages/RoadMapView";
import type { Route } from "../+types/root";
import { Roadmap } from "~/Model/Roadmap/Roadmap";
import Loading from "~/FrontendWebpages/fragments/Loading";

//  step one delete whatever the first line was
// step two create the webpage and get the clientLoader to fetch the data from the controller and pass it to the webpage
// and then create the dashboard function that will return the webpage with the data from the clientLoader and then create the clientAction function that will handle the delete action and then create the meta function that will set the title and description of the page

export function meta() {
    return [
        { title: "InternMap" },
        { name: "description", content: "Welcome to our 4th semester project" },
    ];
}

// @ts-ignore
export async function clientLoader({ params }) {
    const res = await fetch(`http://localhost:8050/REST/${params.id}`);

    if (!res.ok) {
        throw new Response("Failed to fetch roadmap", { status: res.status });
    }

    const json = await res.json();
    console.log(json);

    return json;
}

export function HydrateFallback() {
    return <Loading/>;
}

export default function roadMapView({loaderData}: Route.ComponentProps) {
    const roadmap: Roadmap = loaderData as Roadmap;
    return <RoadMapView roadmap={roadmap} />;
}
