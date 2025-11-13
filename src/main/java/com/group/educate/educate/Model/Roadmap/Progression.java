package com.group.educate.educate.Model.Roadmap;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public interface Progression<T> {

    UUID ID = UUID.randomUUID();

    Date lastUpdated = Date.from(Instant.now());

    final ArrayList<Boolean> completeModuleList = new ArrayList<>();

    double currentCompletionPercentage = 0;
    int completeModulesCount = 0;
    int inCompleteModulesCount = 0;

    //MARK: Getters
    public UUID getID();

    //MARK: Methods
    public boolean markComplete(int index);

    public boolean markComplete(T module);

    public boolean markIncomplete(int index);

    public boolean markIncomplete(T module);

}
