package com.example;

public class ModelLocation {
    public String title;
    private String description;
    private String videoPath;

    public ModelLocation(String title, String description, String videoPath) {
        this.title = title;
        this.description = description;
        this.videoPath = videoPath;
    }


    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoPath() {
        return this.videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

}
