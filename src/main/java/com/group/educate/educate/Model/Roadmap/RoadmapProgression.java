// 13//11/2025 - Ziad
//Sandra, do NOT touch this file before informing me

package com.group.educate.educate.Model.Roadmap;

import ch.qos.logback.core.pattern.color.ANSIConstants;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

public class RoadmapProgression {

    private double percentage;
    private Date lastUpdated = Date.from(Instant.now());
    private Roadmap roadmapDelegate;

    private final ArrayList<Boolean> completeModuleList = new ArrayList<>();

    private double currentCompletionPercentage;
    private int completeModules;
    private int inCompleteModules;

    public RoadmapProgression(Roadmap delegate) {
        roadmapDelegate = delegate;
    }

    private void prepare() {
        for (int i = 0; i < roadmapDelegate.getAllModules().size(); i++) {
            completeModuleList.add(false);
        }
    }

    public boolean markComplete(int index) {
        if (completeModuleList.get(index) != true) {
            completeModuleList.set(index, true);
            return true;
        } else {
            return false;
        }
    }

    public boolean markComplete(RoadmapModule module) {
        if (!roadmapDelegate.getAllModules().contains(module)) {
            return false;
        } else {
            if (completeModuleList.get(roadmapDelegate.getAllModules().indexOf(module)) == true) {
                return false;
            } else {
                completeModuleList.set(roadmapDelegate.getAllModules().indexOf(module), true);
                return true;
            }
        }
    }

    public boolean markIncomplete(int index) {
        if (completeModuleList.get(index) != false) {
            completeModuleList.set(index, false);
            return true;
        } else {
            return false;
        }
    }

    public boolean markIncomplete(RoadmapModule module) {
        if (!roadmapDelegate.getAllModules().contains(module)) {
            return false;
        } else {
            if (completeModuleList.get(roadmapDelegate.getAllModules().indexOf(module)) == false) {
                return false;
            } else {
                completeModuleList.set(roadmapDelegate.getAllModules().indexOf(module), false);
                return true;
            }
        }
    }

    private void updateLastUpdated() { lastUpdated = Date.from(Instant.now()); }

    private void updateCompletePercentage() {
        int completeCounter = 0;
        int incompleteCounter = 0;

        for (int i = 0; i < ; i++) {
            
        }
    }
}
