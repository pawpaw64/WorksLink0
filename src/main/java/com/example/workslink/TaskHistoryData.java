package com.example.workslink;

public class TaskHistoryData {
    private String spaceId;
    private String spaceTask;
    private String spaceStatus;
    private String spaceProgress;

    public TaskHistoryData( String spaceTaskname, String spaceStatus, String spaceProgress) {
       // this.spaceId = spaceId;
        this.spaceTask = spaceTaskname;
        this.spaceStatus = spaceStatus;
        this.spaceProgress = spaceProgress;
        System.out.println(spaceTaskname);
    }

    public String getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceTask() {
        return spaceTask;
    }

    public void setSpaceTask(String spaceTask) {
        this.spaceTask = spaceTask;
    }

    public String getSpaceStatus() {
        return spaceStatus;
    }

    public void setSpaceStatus(String spaceStatus) {
        this.spaceStatus = spaceStatus;
    }

    public String getSpaceProgress() {
        return spaceProgress;
    }

    public void setSpaceProgress(String spaceProgress) {
        this.spaceProgress = spaceProgress;
    }
}
