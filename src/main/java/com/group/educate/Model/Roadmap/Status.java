package com.group.educate.Model.Roadmap;

public enum Status {
    IN_PROGRESS(0), NOT_STARTED(1), DONE(2), SKIPPED(3);

    private final int code;

    Status(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
