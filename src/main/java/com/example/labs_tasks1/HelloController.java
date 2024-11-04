package com.example.labs_tasks1;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private ImageView imageView;

    @FXML
    private Button btnNext;

    @FXML
    private Button btnPrev;

    @FXML
    private Button btnFirst;

    @FXML
    private Button btnLast;

    @FXML
    private Button btnAuto;

    @FXML
    private Button btnPause;

    @FXML
    private Button btnChooseDir;

    @FXML
    private ProgressBar progressBar;

    private List<File> imageFiles;
    private int currentIndex = 0;
    private Timeline timeline;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onNextButtonClick() {
        currentIndex = (currentIndex + 1) % imageFiles.size();
        showImage(imageFiles.get(currentIndex));
        updateProgressBar();
    }

    @FXML
    protected void onPrevButtonClick() {
        currentIndex = (currentIndex - 1 + imageFiles.size()) % imageFiles.size();
        showImage(imageFiles.get(currentIndex));
        updateProgressBar();
    }

    @FXML
    protected void onFirstButtonClick() {
        if (!imageFiles.isEmpty()) {
            currentIndex = 0;
            showImage(imageFiles.get(currentIndex));
            updateProgressBar();
        }
    }

    @FXML
    protected void onLastButtonClick() {
        if (!imageFiles.isEmpty()) {
            currentIndex = imageFiles.size() - 1;
            showImage(imageFiles.get(currentIndex));
            updateProgressBar();
        }
    }

    @FXML
    protected void onAutoButtonClick() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> onNextButtonClick()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @FXML
    protected void onPauseButtonClick() {
        if (timeline != null) {
            timeline.stop();
        }
    }

    @FXML
    protected void onChooseDirButtonClick() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());

        if (selectedDirectory != null) {
            imageFiles = loadImagesFromDirectory(selectedDirectory);
            if (!imageFiles.isEmpty()) {
                currentIndex = 0;
                showImage(imageFiles.get(currentIndex));
                updateProgressBar();
                btnNext.setDisable(false);
                btnPrev.setDisable(false);
                btnFirst.setDisable(false);
                btnLast.setDisable(false);
                btnAuto.setDisable(false);
                btnPause.setDisable(false);
            } else {
                welcomeText.setText("No images found in the selected directory.");
                btnNext.setDisable(true);
                btnPrev.setDisable(true);
                btnFirst.setDisable(true);
                btnLast.setDisable(true);
                btnAuto.setDisable(true);
                btnPause.setDisable(true);
            }
        }
    }

    private List<File> loadImagesFromDirectory(File directory) {
        List<File> images = new ArrayList<>();
        try {
            Files.walk(Paths.get(directory.getPath()))
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .filter(file -> file.getName().matches(".*\\.(png|jpg|jpeg|gif|bmp|tiff)"))
                    .forEach(images::add);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    private void showImage(File imageFile) {
        Image image = new Image(imageFile.toURI().toString());
        imageView.setImage(image);
        welcomeText.setText("Viewing: " + imageFile.getName());
    }

    private void updateProgressBar() {
        if (imageFiles != null && !imageFiles.isEmpty()) {
            double progress = (double) (currentIndex + 1) / imageFiles.size();
            progressBar.setProgress(progress);
        } else {
            progressBar.setProgress(0);
        }
    }
}