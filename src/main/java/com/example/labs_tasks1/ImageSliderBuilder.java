package com.example.labs_tasks1;

import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.List;

public class ImageSliderBuilder implements Builder {
    private HelloController controller;
    private Label welcomeText;
    private ImageView imageView;
    private Button btnNext;
    private Button btnPrev;
    private Button btnFirst;
    private Button btnLast;
    private Button btnAuto;
    private Button btnPause;
    private Button btnChooseDir;
    private ProgressBar progressBar;
    private List<File> imageFiles;
    private int currentIndex;
    private Timeline timeline;

    public ImageSliderBuilder(HelloController controller) {
        this.controller = controller;
        this.welcomeText = controller.getWelcomeText(); // Используем геттер
        this.imageView = controller.getImageView(); // Используем геттер
        this.btnNext = controller.getBtnNext(); // Используем геттер
        this.btnPrev = controller.getBtnPrev(); // Используем геттер
        this.btnFirst = controller.getBtnFirst(); // Используем геттер
        this.btnLast = controller.getBtnLast(); // Используем геттер
        this.btnAuto = controller.getBtnAuto(); // Используем геттер
        this.btnPause = controller.getBtnPause(); // Используем геттер
        this.btnChooseDir = controller.getBtnChooseDir(); // Используем геттер
        this.progressBar = controller.getProgressBar(); // Используем геттер
    }

    @Override
    public void setWelcomeText(String text) {
        welcomeText.setText(text);
    }

    @Override
    public void setImageFiles(List<File> imageFiles) {
        this.imageFiles = imageFiles;
        controller.setImageFiles(imageFiles); // Используем сеттер
    }

    @Override
    public void setCurrentIndex(int index) {
        this.currentIndex = index;
        controller.setCurrentIndex(index); // Используем сеттер
    }

    @Override
    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
        controller.setTimeline(timeline); // Используем сеттер
    }

    @Override
    public void setButtonsEnabled(boolean enabled) {
        btnNext.setDisable(!enabled);
        btnPrev.setDisable(!enabled);
        btnFirst.setDisable(!enabled);
        btnLast.setDisable(!enabled);
        btnAuto.setDisable(!enabled);
        btnPause.setDisable(!enabled);
    }

    @Override
    public void build() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            controller.showImage(imageFiles.get(currentIndex));
            controller.updateProgressBar();
            setButtonsEnabled(true);
        } else {
            setWelcomeText("No images found in the selected directory.");
            setButtonsEnabled(false);
        }
    }
}