package com.group.educate.educate.Model.Roadmap.Skill;

import com.group.educate.educate.Model.User.Student.Student;

import java.time.LocalDateTime;
// need to rethink user skill status and roadmap progression relationship and how they work

public class UserSkillStatus {
   private Skill skill;
  private  Student student;
  private Status status;
  private LocalDateTime updatedTime;
    public enum Status {
        IN_PROGRESS(0),
        DONE(1),
        SKIP(1);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
    public UserSkillStatus(Skill skill, Student student, Status status) {
        this.skill = skill;
        this.student = student;
        this.status = status;
        this.updatedTime = LocalDateTime.now();
    }
    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

}
