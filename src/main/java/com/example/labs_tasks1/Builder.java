package com.example.labs_tasks1;

import javafx.animation.Timeline;

import java.io.File;
import java.util.List;

public interface Builder {
    void setWelcomeText(String text);
    void setImageFiles(List<File> imageFiles);
    void setCurrentIndex(int index);
    void setTimeline(Timeline timeline);
    void setButtonsEnabled(boolean enabled);
    void build();
}