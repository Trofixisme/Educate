import "../CSS/jobPosting.css";
import "../CSS/Roadmap.css";
import "../CSS/InternMapHomepage.css";
import "../CSS/Universal.css";
import { useState } from "react";
import { motion } from "framer-motion";
import {IndexFooter, IndexHeader} from "./fragments/IndexHeaderAndFooter";
import type {Roadmap} from "~/Model/Roadmap/Roadmap";

export default function RoadMapView({ roadmap }: { roadmap: Roadmap }) {
    const [openSkill, setOpenSkill] = useState<string | null>(null);

    // if (!roadmap?.roadmapModules) return null;

    const width = 900;
    const centerX = width / 2;
    const amplitude = 140;
    const heightStep = 200;
    const topOffset = 120;

    const n = roadmap.allModules.length;

    const points = roadmap.allModules.map((_, i) => {
        const t = n === 1 ? 0 : i / (n - 1);
        const y = i * heightStep + topOffset;
        const rawX = centerX + Math.sin(t * Math.PI * 2) * amplitude;

        const padding = 120;
        const x = Math.max(padding, Math.min(width - padding, rawX));

        return { x, y };
    });

    const totalHeight = n * heightStep + 200;

    const pathD = points
        .map((p, i) => (i === 0 ? `M ${p.x} ${p.y}` : `L ${p.x} ${p.y}`))
        .join(" ");

    return (
        <>
            <IndexHeader />
            <h1 className="text-4xl font-bold text-center mb-10">
                {roadmap.name}
            </h1>
            <div className="pl-10 pr-10">
            <div className="container-full-width">
                <div style={{ paddingTop: "90px" }}>
                    <div className="w-full flex justify-center">
                        <div className="relative" style={{width, height: totalHeight,}}>

                            <svg className="absolute top-0 left-0 w-full pointer-events-none" height={totalHeight}>
                                <path d={pathD} fill="none" stroke="var(--stroke-color)" strokeWidth="5" strokeLinecap="round" strokeDasharray="10 10"/>

                                {points.map((p, i) => (
                                    <circle key={i} cx={p.x} cy={p.y} r="6" fill="var(--node-fill)" />
                                ))}
                            </svg>

                            {roadmap.allModules.map((module, i) => {
                                const { x, y } = points[i];

                                return (
                                    <div key={module.id ?? i} className="absolute flex flex-col items-center"
                                        style={{left: x, top: y, transform: "translate(-50%, -50%)", zIndex: 10,}}>

                                        <motion.div initial={{ scale: 0.8, opacity: 0 }}
                                            animate={{ scale: 1, opacity: 1 }}
                                            className="shadow-xl rounded-2xl px-6 py-3 text-center"
                                            style={{backgroundColor: "var(--card-bg)", color: "var(--text-primary)", minWidth: "140px", maxWidth: "240px",}}>

                                            {module.name}
                                        </motion.div>

                                        <div className="mt-4 flex flex-col items-center gap-2">
                                            {module.skills?.map((skill, si) => {
                                                const key = `${i}-${si}`;
                                                const isOpen = openSkill === key;

                                                return (
                                                    <div key={key} className="flex flex-col items-center">
                                                        <div onClick={() => setOpenSkill(isOpen ? null : key)} className="cursor-pointer rounded-full px-3 py-1 text-sm" style={{backgroundColor: "var(--card-bg-secondary)",}}>{skill.name}</div>

                                                        {isOpen && (
                                                            <motion.div initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }} className="mt-2 z-50">
                                                                <div
                                                                    className="rounded-xl p-3 space-y-2 shadow-lg"
                                                                    style={{backgroundColor: "var(--card-bg)", color: "var(--text-primary)", minWidth: "200px", maxWidth: "300px", width: "max-content", wordBreak: "break-word", whiteSpace: "normal",
                                                                    }}>
                                                                    {skill.description && (
                                                                        <>
                                                                            <p className="text-xs font-medium" style={{ color: "var(--text-secondary)" }}>
                                                                                Skill Description:
                                                                            </p>
                                                                            <div className="text-xs rounded px-2 py-1" style={{ lineHeight: "1.5" }}>
                                                                                {skill.description}
                                                                            </div>
                                                                        </>
                                                                    )}

                                                                    {skill.resourceLinks &&
                                                                        skill.resourceLinks.length > 0 && (
                                                                            <>
                                                                                <p className="text-xs font-medium" style={{ color: "var(--text-secondary)" }}> Resources</p>

                                                                                {skill.resourceLinks.map((linkObj, idx) => (
                                                                                    <a key={idx} href={linkObj as string} target="_blank" rel="noreferrer" className="block text-xs"
                                                                                        style={{
                                                                                            color: "var(--primary-color)",
                                                                                            wordBreak: "break-all",
                                                                                            lineHeight: "1.4",
                                                                                        }}>
                                                                                        {linkObj.replace(/^https?:\/\//, "")}
                                                                                    </a>
                                                                                ))}
                                                                            </>
                                                                        )}
                                                                </div>
                                                            </motion.div>
                                                        )}
                                                    </div>
                                                );
                                            })}
                                        </div>
                                    </div>
                                );
                            })}
                        </div>
                    </div>
                </div>
            </div>
            </div>
            <br/>
            <br/>
            <IndexFooter/>
        </>
    );
}